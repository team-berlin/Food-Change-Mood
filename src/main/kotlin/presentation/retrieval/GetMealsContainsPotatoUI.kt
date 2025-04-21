package org.berlin.presentation.retrieval

import org.berlin.logic.usecase.retrieval.GetMealsContainsPotatoUseCase
import org.berlin.presentation.UiRunner

class GetMealsContainsPotatoUI(
    private val getMealsContainsPotatoUseCase: GetMealsContainsPotatoUseCase
): UiRunner {
    override val id: Int = 12
    override val label: String = "Get names of 10 meals that contains potatoes in its ingredients"

    override fun run() {
        getMealsContainsPotatoUseCase.getMealsContainsPotato().forEach { println(it) }
    }

}