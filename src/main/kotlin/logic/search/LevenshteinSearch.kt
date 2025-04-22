package org.berlin.logic.search

class LevenshteinSearch : SearchByName {

    override fun search(textToSearchIn: String, wordToSearchBy: String): Boolean =
        evaluateLevenshteinDistance(textToSearchIn, wordToSearchBy) <= calculateLevenshteinThreshold(wordToSearchBy)

    private fun evaluateLevenshteinDistance(
        textToSearchIn: String, wordToSearchBy: String,
    ): Int {
        val distanceMatrix = buildDistanceMatrix(textToSearchIn.length, wordToSearchBy.length)
        computeDistances(distanceMatrix, textToSearchIn, wordToSearchBy)
        return distanceMatrix[textToSearchIn.length][wordToSearchBy.length]
    }

    private fun computeDistances(
        distanceMatrix: Array<IntArray>,
        textToSearchIn: String,
        wordToSearchBy: String,
    ): Array<IntArray> {
        (1..textToSearchIn.length).forEach { textIndex ->
            (1..wordToSearchBy.length).forEach { wordIndex ->
                val cost = if (textToSearchIn[textIndex - 1] == wordToSearchBy[wordIndex - 1]) 0 else 1
                distanceMatrix[textIndex][wordIndex] = minOf(
                    distanceMatrix[textIndex - 1][wordIndex] + 1,
                    distanceMatrix[textIndex][wordIndex - 1] + 1,
                    distanceMatrix[textIndex - 1][wordIndex - 1] + cost
                )
            }
        }
        return distanceMatrix
    }

    private fun buildDistanceMatrix(
        textToBeSearchLength: Int, wordToSearchByLength: Int,
    ): Array<IntArray> {
        val distanceMatrix = Array(textToBeSearchLength + 1) {
            IntArray(wordToSearchByLength + 1)
        }

        (0..textToBeSearchLength).forEach {
            distanceMatrix[it][0] = it
        }
        (0..wordToSearchByLength).forEach {
            distanceMatrix[0][it] = it
        }

        return distanceMatrix
    }

    private fun calculateLevenshteinThreshold(input: String): Int =
        (input.length * ERROR_RATE).toInt()

    companion object {
        private const val ERROR_RATE = 0.3
    }
}

