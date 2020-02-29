package eu.revils.revilspvp.kt.command

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Command(val names: Array<String>,
                         val permission: String = "",
                         val hidden: Boolean = false,
                         val async: Boolean = false,
                         val description: String = "",
                         val logToConsole: Boolean = true)
