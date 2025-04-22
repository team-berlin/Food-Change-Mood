package org.berlin.logic.common.extention

fun <T> List<T>.getRandomItems(count: Int): List<T> {
    if (this.isEmpty()) return emptyList()

    val limitedCount = count.coerceAtMost(size)

    val availableIndices = indices.toMutableList()
    val selectedItems = mutableSetOf<T>()

    while (selectedItems.size < limitedCount) {
        val randomIndex = availableIndices.random()
        selectedItems.add(this[randomIndex])
    }
    return selectedItems.toList()
}