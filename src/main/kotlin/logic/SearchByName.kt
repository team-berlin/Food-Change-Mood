package org.berlin.logic

interface SearchByName {
    fun search(textToSearchIn: String,
               wordToSearchBy: String): Boolean
}