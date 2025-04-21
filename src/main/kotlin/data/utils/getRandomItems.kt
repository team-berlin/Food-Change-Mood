package org.berlin.data.utils

object Utils {
    fun <T> List<T>.getRandomItems(number: Int): List<T> {
        if (this.isEmpty()) return emptyList()

        val indices = this.indices.toMutableList()
        val randomMeals = mutableSetOf<T>()

        while (number != randomMeals.size) {
            val randomIndex = indices.random()
            randomMeals.add(this[randomIndex])
        }
        return randomMeals.toList()
    }
}