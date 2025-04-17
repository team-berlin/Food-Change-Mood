package org.berlin.dependency_injection

import org.berlin.logic.usecase.EasyFoodSuggestionUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { EasyFoodSuggestionUseCase(get()) }
}