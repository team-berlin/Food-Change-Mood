package org.berlin.dependencyInjection

import org.berlin.logic.usecase.EasyFoodSuggestionUseCase
import org.koin.dsl.module

val easyFoodSuggestionModule= module{
    single { EasyFoodSuggestionUseCase(get()) }
}