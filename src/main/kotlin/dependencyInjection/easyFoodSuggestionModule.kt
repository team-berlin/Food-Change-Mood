package org.berlin.dependencyInjection

import org.berlin.logic.usecase.EasyFoodSuggestionUseCase
import org.berlin.presentation.FoodChangeMoodUI
import org.koin.dsl.module

val easyFoodSuggestionModule= module{
    single { FoodChangeMoodUI(get()) }
    single { EasyFoodSuggestionUseCase(get()) }
}