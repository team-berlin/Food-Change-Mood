package org.berlin.logic.search

class KmpSearch : SearchByName {

    override fun search(
        textToSearchIn: String, wordToSearchBy: String,
    ): Boolean = knuthMorrisPrattSearch(textToSearchIn, wordToSearchBy)

    private fun knuthMorrisPrattSearch(
        textToSearchIn: String, wordToSearchBy: String,
    ): Boolean = when {
            (wordToSearchBy.isEmpty()) -> true
            (textToSearchIn.isEmpty()) -> false
            else -> searchWordInText(textToSearchIn, wordToSearchBy)
        }

    private fun searchWordInText(textToSearchIn: String, wordToSearchBy: String): Boolean {
        val lps = computeLPSArray(wordToSearchBy)
        var textIndex = 0
        var wordIndex = 0
        while (textIndex < textToSearchIn.length) {
            if (wordToSearchBy[wordIndex] == textToSearchIn[textIndex]) {
                textIndex += 1
                wordIndex += 1
            }

            if ((wordIndex == wordToSearchBy.length)) return true
            else if (textIndex < textToSearchIn.length && wordToSearchBy[wordIndex] != textToSearchIn[textIndex]) {
                wordIndex = if (wordIndex != 0) lps[wordIndex - 1] else {
                    textIndex += 1
                    0
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
            when {
                wordToSearchBy[currentIndex] == wordToSearchBy[prefixLength] -> {
                    prefixLength += 1
                    lsp[currentIndex] = prefixLength
                    currentIndex += 1
                }

                prefixLength != 0 ->
                    prefixLength = lsp[prefixLength - 1]

                else -> {
                    lsp[currentIndex] = 0
                    currentIndex += 1
                }
            }
        }
        return lsp
    }
}