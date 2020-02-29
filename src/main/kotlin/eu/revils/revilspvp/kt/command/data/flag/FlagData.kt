package eu.revils.revilspvp.kt.command.data.flag

import eu.revils.revilspvp.kt.command.data.Data

data class FlagData(val names: List<String>,
                    val description: String,
                    val defaultValue: Boolean,
                    val methodIndex: Int) : Data