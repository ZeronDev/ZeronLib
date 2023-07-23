package io.github.ZeronDev.util

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.CompletableFuture

// 각별님의 tap
object GitHubSupport {
    private const val GITHUB_API = "https://api.github.com"

    private const val KEY_ASSETS = "assets"
    private const val KEY_NAME = "name"
    private const val KEY_TAG_NAME = "tag_name"
    private const val KEY_BROWSER_DOWNLOAD_URL = "browser_download_url"

    private const val REQUEST_ACCEPT = "application/vnd.github.v3+json"

    fun generateUrlGitHubLatestRelease(owner: String, project: String): String {
        return "$GITHUB_API/repos/$owner/$project/releases/latest"
    }
    fun downloadLatestRelease(
        file: File,
        owner: String,
        project: String,
        version: String,
        asset: String,
        callback: (Result<String>.() -> Unit)? = null
    ) {
        kotlin.runCatching {
            file.parentFile?.mkdirs()

            val latestReleaseURL = URL(generateUrlGitHubLatestRelease(owner, project))
            val releaseInfo = latestReleaseURL.httpRequest {
                requestMethod = "GET"
                addRequestProperty("Accept", REQUEST_ACCEPT)
                inputStream.bufferedReader().use { JsonParser.parseReader(it) as JsonObject }
            }
            releaseInfo[KEY_TAG_NAME].asString.also {
                if (version.compareVersion(it) >= 0) throw UpToDateException("UP TO DATE")
            }
            val foundAsset = releaseInfo.getAsJsonArray(KEY_ASSETS)
                .filterIsInstance(JsonObject::class.java)
                .find { it[KEY_NAME].asString == asset }
                ?: throw IllegalArgumentException("Not found asset for $asset")
            val downloadURL = URL(foundAsset[KEY_BROWSER_DOWNLOAD_URL].asString)
            downloadURL.downloadTo(file)
            downloadURL.path
        }.onSuccess {
            callback?.invoke(Result.success(it))
        }.onFailure {
            callback?.invoke(Result.failure(it))
        }
    }
}

class UpToDateException(message: String) : RuntimeException(message)

fun <T> URL.httpRequest(requester: (HttpURLConnection.() -> T)): T {
    return with(openConnection() as HttpURLConnection) { requester.invoke(this) }
}

fun URL.downloadTo(file: File) {
    val temp = File("${file.path}.tmp")

    openStream().buffered().use { input ->
        temp.outputStream().buffered().use { output ->
            val data = ByteArray(1024)

            while (true) {
                val count = input.read(data)
                if (count == -1) break

                output.write(data, 0, count)
            }
            output.close()
            temp.renameTo(file)
        }
    }
}
fun JavaPlugin.updateFromGitHub(
    owner: String,
    project: String,
    asset: String,
    callback: (Result<String>.() -> Unit)? = null
) {
    GitHubSupport.downloadLatestRelease(updateFile, owner, project, description.version, asset, callback)
}

private val JavaPlugin.updateFile: File
    get() {
        val file = JavaPlugin::class.java.getDeclaredMethod("getFile").apply {
            isAccessible = true
        }.invoke(this) as File

        return File(file.parentFile, "update/${file.name}")
    }

@DelicateCoroutinesApi
fun JavaPlugin.updateFromGitHubMagically(
    owner: String,
    project: String,
    asset: String,
    reciever: ((String) -> Unit)? = null
): CompletableFuture<File> {
    val future = CompletableFuture<File>()
    reciever?.invoke("Attempt to update.")

    GlobalScope.launch {
        GitHubSupport.downloadLatestRelease(updateFile, owner, project, description.version, asset) {
            onSuccess { url ->
                reciever?.run {
                    invoke("Updated successfully. Applies after the server restarts.")
                    invoke(url)
                    future.complete(updateFile)
                }
            }
            onFailure { t ->
                if (t is UpToDateException) reciever?.invoke("UP TO DATE")
                else {
                    reciever?.invoke("Update failed. Check the stacktrace.")
                    t.printStackTrace()
                }
            }
        }
    }
    return future
}