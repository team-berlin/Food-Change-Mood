package logic.usecase.helper

interface SearchByName {
    fun search(
        textToSearchIn: String,
        wordToSearchBy: String,
    ): Boolean
}