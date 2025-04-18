package org.berlin.logic.usecase

import org.berlin.logic.MealsRepository
import org.berlin.model.Meal

class SearchMealsByNameUseCase(
    private val mealsRepository: MealsRepository
) {
    fun searchMealsByName(inputMealName: String): List<Meal> {
        return mealsRepository.getAllMeals().filter { meal ->
            kmpSearch(meal.name.lowercase(), inputMealName.lowercase())
        }
    }

    private fun kmpSearch(text: String, wordToSearchBy: String): Boolean {
        if (wordToSearchBy.isEmpty()) return true
        if (text.isEmpty()) return false
        val lps = computeLPSArray(wordToSearchBy)
        var textIndex = 0
        var wordIndex = 0
        while (textIndex < text.length) {
            if (wordToSearchBy[wordIndex] == text[textIndex]) {
                textIndex++
                wordIndex++
            }
            if (wordIndex == wordToSearchBy.length) return true
            else if (textIndex < text.length && wordToSearchBy[wordIndex] != text[textIndex]) {
                if (wordIndex != 0) {
                    wordIndex = lps[wordIndex - 1]

                } else {
                    textIndex++
                }
            }

        }
        return false
    }

    private fun computeLPSArray(wordToSearchBy: String): IntArray {
        val lsp = IntArray(wordToSearchBy.length)
        var prefixLength = 0
        var currentIndex = 1
        while (currentIndex < wordToSearchBy.length) {
            if (wordToSearchBy[currentIndex] == wordToSearchBy[prefixLength]) {
                prefixLength++
                lsp[currentIndex] = prefixLength
                currentIndex++
            } else {
                if (prefixLength != 0) {
                    prefixLength = lsp[prefixLength - 1]
                } else {
                    lsp[currentIndex] = 0
                    currentIndex++
                }
            }
        }
        return lsp
    }
}