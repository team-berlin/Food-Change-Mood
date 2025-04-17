package org.berlin

import dependency_injection.appModule
import org.berlin.dependency_injection.useCaseModule
import org.berlin.logic.usecase.EasyFoodSuggestionUseCase
import org.berlin.presentation.FoodChangeMoodUI
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin


fun main() {
    startKoin{
        modules(appModule, useCaseModule)
    }

    val easyFoodSuggestionRepository: EasyFoodSuggestionUseCase = getKoin().get()
    easyFoodSuggestionRepository.getEasyFoodSuggestion().also {
        println(it)
    }
    val ui: FoodChangeMoodUI = getKoin().get()
    ui.start()
}

