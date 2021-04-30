package com.andrew121410.mc.ccminecraftbot.objects

data class Item(
    val id: Int,
    val displayName: String,
    val name: String,
    val stackSize: Int,
    val durability: Int? = null,
    val enchantCategories: Array<String>? = null,
    val repairWith: Array<String>? = null,
    val maxDurability: Int? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Item

        if (id != other.id) return false
        if (displayName != other.displayName) return false
        if (name != other.name) return false
        if (stackSize != other.stackSize) return false
        if (durability != other.durability) return false
        if (enchantCategories != null) {
            if (other.enchantCategories == null) return false
            if (!enchantCategories.contentEquals(other.enchantCategories)) return false
        } else if (other.enchantCategories != null) return false
        if (repairWith != null) {
            if (other.repairWith == null) return false
            if (!repairWith.contentEquals(other.repairWith)) return false
        } else if (other.repairWith != null) return false
        if (maxDurability != other.maxDurability) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + displayName.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + stackSize
        result = 31 * result + (durability ?: 0)
        result = 31 * result + (enchantCategories?.contentHashCode() ?: 0)
        result = 31 * result + (repairWith?.contentHashCode() ?: 0)
        result = 31 * result + (maxDurability ?: 0)
        return result
    }

    override fun toString(): String {
        return "Item(id=$id, displayName='$displayName', name='$name', stackSize=$stackSize, durability=$durability, enchantCategories=${enchantCategories?.contentToString()}, repairWith=${repairWith?.contentToString()}, maxDurability=$maxDurability)"
    }
}