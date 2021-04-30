package com.andrew121410.mc.ccminecraftbot.utils

import com.andrew121410.mc.ccminecraftbot.utils.ResourceManager.items
import java.util.*

object Blocks {
    fun isFood(id: Int): Boolean {
        val (_, displayName1) = items[id] ?: return false
        val displayName = displayName1.toLowerCase(Locale.ROOT)
        return when (displayName) {
            "enchanted golden apple", "golden apple", "golden carrot", "cooked mutton", "cooked porkchop", "cooked salmon", "steak", "baked potato", "bread", "cooked chicken", "melon", "cooked cod" -> true
            else -> false
        }
    }
}