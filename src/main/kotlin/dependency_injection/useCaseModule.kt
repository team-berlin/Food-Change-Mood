package org.berlin.dependency_injection
import org.berlin.logic.usecase.ExploreFoodCultureUseCase

import org.berlin.logic.usecase.SearchMealsByNameUseCase
import org.berlin.logic.usecase.EasyFoodSuggestionUseCase
import org.berlin.logic.usecase.SuggestKetoMealUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { SearchMealsByNameUseCase(get()) }
    single { ExploreFoodCultureUseCase(get()) }
    single { SuggestKetoMealUseCase(get()) }
    single { EasyFoodSuggestionUseCase(get()) }
}



