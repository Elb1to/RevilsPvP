package eu.revils.revilspvp.kt.command.data

import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Type(val value: KClass<*>)