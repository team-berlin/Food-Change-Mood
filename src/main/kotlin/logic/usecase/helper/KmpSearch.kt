package logic.usecase.helper

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

        return generateSequence(Pair(0, 0)) { (textIndex, wordIndex) ->
            when {
                textIndex >= textToSearchIn.length -> null
                wordToSearchBy[wordIndex] == textToSearchIn[textIndex] ->
                    Pair(textIndex + 1, wordIndex + 1)
                wordIndex != 0 -> Pair(textIndex, lps[wordIndex - 1])
                else -> Pair(textIndex + 1, 0)
            }
        }.any { (_, wordIndex) -> wordIndex == wordToSearchBy.length }
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

                prefixLength != 0 -> prefixLength = lsp[prefixLength - 1]
                else -> {
                    lsp[currentIndex] = 0
                    currentIndex += 1
                }
            }
        }
        return lsp
    }
}