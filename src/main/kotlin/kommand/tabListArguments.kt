package kommand

import io.github.monun.kommand.KommandArgument.Companion.dynamicByMap
import io.github.monun.kommand.KommandArgument.Companion.string
import io.github.monun.kommand.StringType

object tabListArguments {
    fun requiredArgument(vararg tabList: String) = dynamicByMap(tabList.toList().associateBy { it })
    fun tabListArgument(vararg tabList: String, type: StringType) = string(type).apply { suggests { suggest(tabList.toList()) } }
}