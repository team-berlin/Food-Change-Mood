package org.berlin.logic.search

interface SearchByName {
    fun search(
        textToSearchIn: String,
        wordToSearchBy: String,
    ): Boolean
}