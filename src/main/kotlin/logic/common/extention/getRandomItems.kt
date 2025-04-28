package org.berlin.logic.common.extention


fun <T> List<T>.getRandomItems(count: Int): List<T> {
    val uniqueItems = this.toSet().toList()
    if (uniqueItems.isEmpty()) return emptyList()

    val limitedCount = count.coerceAtMost(uniqueItems.size)
    val selectedItems = mutableListOf<T>()

    while (selectedItems.size < limitedCount) {
        val randomItem = uniqueItems.random()
        selectedItems.add(randomItem)
    }

    return selectedItems.toList()
}
