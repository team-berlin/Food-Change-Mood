package org.berlin

import dependencyInjection.appModule
import org.berlin.dependencyInjection.easyFoodSuggestionModule
import org.berlin.logic.usecase.EasyFoodSuggestionRepository
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin


fun main() {
    startKoin{
        modules(appModule, easyFoodSuggestionModule)
    }
    val easyFoodSuggestionRepository: EasyFoodSuggestionRepository = getKoin().get()
    easyFoodSuggestionRepository.getEasyFoodSuggestion().also {
        println(it)
    }
}

