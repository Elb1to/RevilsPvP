package eu.revils.revilspvp.kt.command.data.parameter

@Retention
@Target

annotation class Param(val name: String,
                       val defaultValue: String = "",
                       val tabCompleteFlags: Array<String> = [],
                       val wildcard: Boolean = false)