package org.berlin.dependency_injection

import org.berlin.logic.GetSeafoodMealsUseCase
import org.berlin.logic.usecase.*
import org.berlin.logic.usecase.IngredientGameMealsMapper
import org.berlin.logic.usecase.IngredientGameUseCase
import org.berlin.presentation.IngredientGameInteractor
import org.berlin.logic.usecase.SearchMealsByNameUseCase
import org.berlin.logic.usecase.EasyFoodSuggestionUseCase
import org.berlin.logic.usecase.SuggestKetoMealUseCase
import org.berlin.logic.usecase.QuickHealthyMealsUseCase
import org.berlin.logic.usecase.SuggestItalianFoodForLargeGroupUseCase
import org.berlin.logic.usecase.GetMealsContainsPotatoUseCase
import org.berlin.logic.usecase.GuessPreparationTimeGameUseCase
import org.koin.dsl.module


val useCaseModule = module {
    single { SearchMealsByNameUseCase(get()) }
    single { ExploreFoodCultureUseCase(get()) }
    single { SuggestKetoMealUseCase(get()) }
    single { EasyFoodSuggestionUseCase(get()) }
    single { SuggestEggFreeSweetUseCase(get()) }
    single { IdentifyIraqiMealsUseCase(get()) }
    single { IngredientGameMealsMapper() }
    single { IngredientGameUseCase(get(),get()) }
    single { IngredientGameInteractor(get()) }
    single { QuickHealthyMealsUseCase(get()) }
    single { SuggestItalianFoodForLargeGroupUseCase(get()) }
    single { GetMealsContainsPotatoUseCase(get()) }
    single { GuessPreparationTimeGameUseCase(get()) }
    single{ GetSeafoodMealsUseCase(get()) }
}