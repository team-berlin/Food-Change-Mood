package org.berlin.logic.search

class CombineSearchAlgorithms(
    private val algorithms: List<SearchByName>,
) : SearchByName {
    override fun search(
        textToSearchIn: String,
        wordToSearchBy: String
    ): Boolean = algorithms.any { algorithm ->
        algorithm.search(textToSearchIn, wordToSearchBy)
    }
}