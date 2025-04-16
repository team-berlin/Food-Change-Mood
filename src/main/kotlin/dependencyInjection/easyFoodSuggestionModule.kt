package org.berlin.dependencyInjection

import org.berlin.logic.usecase.EasyFoodSuggestionRepository
import org.berlin.presentation.FoodChangeMoodUI
import org.koin.dsl.module

val easyFoodSuggestionModule= module{
    single { FoodChangeMoodUI(get()) }
    single { EasyFoodSuggestionRepository(get()) }
}