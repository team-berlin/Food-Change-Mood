package org.berlin.presentation.retrieval

import org.berlin.logic.usecase.retrieval.GetMealsContainsPotatoUseCase
import org.berlin.presentation.UiRunner
import org.berlin.presentation.input_output.Viewer

class GetMealsContainsPotatoUI(
    private val getMealsContainsPotatoUseCase: GetMealsContainsPotatoUseCase,
    private val viewer: Viewer
): UiRunner {
    override val id: Int = 12
    override val label: String = "Get names of 10 meals that contains potatoes in its ingredients"

    override fun run() {
        getMealsContainsPotatoUseCase.getMealsContainsPotato().forEach {
            viewer.display(it)
        }
    }

}