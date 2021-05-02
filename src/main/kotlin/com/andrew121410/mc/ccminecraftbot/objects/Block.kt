package com.andrew121410.mc.ccminecraftbot.objects

enum class BoundingBox {
    block,
    empty
}

data class State(val name: String, val type: String, val num_values: Int, val values: Array<String>? = null) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as State

        if (name != other.name) return false
        if (type != other.type) return false
        if (num_values != other.num_values) return false
        if (values != null) {
            if (other.values == null) return false
            if (!values.contentEquals(other.values)) return false
        } else if (other.values != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + num_values
        result = 31 * result + (values?.contentHashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "State(name='$name', type='$type', num_values=$num_values, values=${values?.contentToString()})"
    }
}

data class Block(
    val id: Int,
    val displayName: String,
    val name: String,
    val hardness: Float? = null,
    val minStateId: Int,
    val maxStateId: Int,
    val states: Array<State>? = null,
    val drops: Array<Int>,
    val diggable: Boolean,
    val transparent: Boolean,
    val filterLight: Int,
    val emitLight: Int,
    val boundingBox: BoundingBox,
    val stackSize: Int,
    val material: String? = null,
    val harvestTools: HashMap<String, Boolean>? = null,
    val defaultState: Int,
    val resistance: Int? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Block

        if (id != other.id) return false
        if (displayName != other.displayName) return false
        if (name != other.name) return false
        if (hardness != other.hardness) return false
        if (minStateId != other.minStateId) return false
        if (maxStateId != other.maxStateId) return false
        if (states != null) {
            if (other.states == null) return false
            if (!states.contentEquals(other.states)) return false
        } else if (other.states != null) return false
        if (!drops.contentEquals(other.drops)) return false
        if (diggable != other.diggable) return false
        if (transparent != other.transparent) return false
        if (filterLight != other.filterLight) return false
        if (emitLight != other.emitLight) return false
        if (boundingBox != other.boundingBox) return false
        if (stackSize != other.stackSize) return false
        if (material != other.material) return false
        if (harvestTools != other.harvestTools) return false
        if (defaultState != other.defaultState) return false
        if (resistance != other.resistance) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + displayName.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + (hardness?.hashCode() ?: 0)
        result = 31 * result + (minStateId ?: 0)
        result = 31 * result + (maxStateId ?: 0)
        result = 31 * result + (states?.contentHashCode() ?: 0)
        result = 31 * result + drops.contentHashCode()
        result = 31 * result + diggable.hashCode()
        result = 31 * result + transparent.hashCode()
        result = 31 * result + filterLight
        result = 31 * result + emitLight
        result = 31 * result + boundingBox.hashCode()
        result = 31 * result + stackSize
        result = 31 * result + (material?.hashCode() ?: 0)
        result = 31 * result + (harvestTools?.hashCode() ?: 0)
        result = 31 * result + (defaultState ?: 0)
        result = 31 * result + (resistance ?: 0)
        return result
    }

    override fun toString(): String {
        return "Block(id=$id, displayName='$displayName', name='$name', hardness=$hardness, minStateId=$minStateId, maxStateId=$maxStateId, states=${states?.contentToString()}, drops=${drops.contentToString()}, diggable=$diggable, transparent=$transparent, filterLight=$filterLight, emitLight=$emitLight, boundingBox=$boundingBox, stackSize=$stackSize, material=$material, harvestTools=$harvestTools, defaultState=$defaultState, resistance=$resistance)"
    }
}