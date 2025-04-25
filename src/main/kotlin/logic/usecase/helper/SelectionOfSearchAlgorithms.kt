package logic.usecase.helper

class SelectionOfSearchAlgorithms(
    private val algorithms: List<SearchByName>,
) : SearchByName {
    override fun search(
        textToSearchIn: String, wordToSearchBy: String
    ): Boolean = algorithms.any { algorithm ->
        algorithm.search(textToSearchIn, wordToSearchBy)
    }
}