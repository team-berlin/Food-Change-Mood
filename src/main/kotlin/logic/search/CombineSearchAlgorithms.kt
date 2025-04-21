package org.berlin.logic.search

class CombineSearchAlgorithms(
    private val kmpAlgorithm: KmpSearch,
    private val levenshteinAlgorithm: LevenshteinSearch
): SearchByName {
    override fun search(textToSearchIn: String, wordToSearchBy: String): Boolean {
        if (kmpAlgorithm.search(textToSearchIn, wordToSearchBy)) return true

        return levenshteinAlgorithm.search(textToSearchIn, wordToSearchBy)
    }
}