package org.berlin.dependency_injection
import org.berlin.logic.usecase.*

import org.berlin.logic.usecase.EasyFoodSuggestionUseCase
import org.berlin.logic.usecase.SuggestKetoMealUseCase
import org.berlin.logic.IngredientGameMealsMapper
import org.berlin.logic.usecase.IngredientGameUseCase
import org.berlin.presentation.IngredientGameInteractor
import org.koin.dsl.module


val useCaseModule = module {
    single { ExploreFoodCultureUseCase(get()) }
    single { SuggestKetoMealUseCase(get()) }
    single { EasyFoodSuggestionUseCase(get()) }
    single { SuggestEggFreeSweetUseCase(get()) }
    single { IdentifyIraqiMealsUseCase(get()) }
    single { IngredientGameMealsMapper() }
    single { IngredientGameUseCase(get(),get()) }
    single { IngredientGameInteractor(get()) }
}



