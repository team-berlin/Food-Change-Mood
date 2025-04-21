package org.berlin.logic.search

class LevenshteinSearch : SearchByName {

    override fun search(textToSearchIn: String, wordToSearchBy: String): Boolean {
        val distance = evaluateLevenshteinDistance(textToSearchIn, wordToSearchBy)
        return distance <= calculateLevenshteinThreshold(wordToSearchBy)
    }

    private fun evaluateLevenshteinDistance(
        textToSearchIn: String, wordToSearchBy: String
    ): Int {
        val distanceMatrix = buildDistanceMatrix(textToSearchIn.length, wordToSearchBy.length)
        computeDistances(distanceMatrix, textToSearchIn, wordToSearchBy)
        return distanceMatrix[textToSearchIn.length][wordToSearchBy.length]
    }

    private fun computeDistances(
        distanceMatrix: Array<IntArray>,
        textToSearchIn: String,
        wordToSearchBy: String
    ): Array<IntArray> {
        for (textIndex in 1..textToSearchIn.length) {
            for (wordIndex in 1..wordToSearchBy.length) {
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
        textToBeSearchLength: Int, wordToSearchByLength: Int
    ): Array<IntArray> {
        val distanceMatrix = Array(textToBeSearchLength + 1) {
            IntArray(wordToSearchByLength + 1)
        }

        (0..textToBeSearchLength).forEach { distanceMatrix[it][0] = it }
        (0..wordToSearchByLength).forEach { distanceMatrix[0][it] = it }

        return distanceMatrix
    }

    private fun calculateLevenshteinThreshold(inputMealName: String): Int {
        return (inputMealName.length * 0.3).toInt()
    }
}
