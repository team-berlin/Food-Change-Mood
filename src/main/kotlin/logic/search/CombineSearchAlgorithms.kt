package org.berlin.logic.search

class CombineSearchAlgorithms(
    private val kmpAlgorithm: SearchByName,
    private val levenshteinAlgorithm: SearchByName
): SearchByName {
    override fun search(textToSearchIn: String, wordToSearchBy: String): Boolean {
        if (kmpAlgorithm.search(textToSearchIn, wordToSearchBy)) return true

        return levenshteinAlgorithm.search(textToSearchIn, wordToSearchBy)
    }
}