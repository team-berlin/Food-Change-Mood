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
        var length = 0
        var index = 1
        while (index < wordToSearchBy.length) {
            if (wordToSearchBy[index] == wordToSearchBy[length]) {
                length++
                lsp[index] = length
                index++
            } else {
                if (length != 0) {
                    length = lsp[length - 1]
                } else {
                    lsp[index] = 0
                    index++
                }
            }
        }
        return lsp
    }
}