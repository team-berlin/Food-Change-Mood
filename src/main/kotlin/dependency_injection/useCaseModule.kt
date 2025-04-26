package org.berlin.dependency_injection

import logic.usecase.game.GuessPreparationTimeGameUseCase
import logic.usecase.game.IngredientGameUseCase
import logic.usecase.helper.KmpSearch
import logic.usecase.helper.LevenshteinSearch
import logic.usecase.helper.SelectionOfSearchAlgorithms
//import org.berlin.logic.IngredientGameMealsMapper
import org.berlin.logic.usecase.helper.IngredientGameMealsMapper
import org.berlin.logic.usecase.helper.RandomMealsForIngredientGame
import org.berlin.logic.usecase.retrieval.GetMealsContainsPotatoUseCase
import org.berlin.logic.usecase.retrieval.GetSeafoodMealsUseCase
import org.berlin.logic.usecase.retrieval.GetIraqiMealsUseCase
import org.berlin.logic.usecase.retrieval.GetQuickHealthyMealsUseCase
import org.berlin.logic.usecase.search.SearchFoodByCultureUseCase
import org.berlin.logic.usecase.search.SearchGymFriendlyMealsUseCase
import org.berlin.logic.usecase.search.SearchMealsByDateUseCase
import org.berlin.logic.usecase.search.SearchMealsByNameUseCase
import org.berlin.logic.usecase.suggest.*
//import org.berlin.presentation.IngredientGameInteractor
import org.koin.dsl.module

val useCaseModule = module {
    single { SuggestHighCalorieMealsUseCase(get()) }
    single { SearchMealsByNameUseCase(get(), get()) }
    single { SearchFoodByCultureUseCase(get()) }
    single { SuggestKetoMealUseCase(get()) }
    single { SuggestEasyFoodUseCase(get()) }
    single { SuggestEggFreeSweetUseCase(get()) }
    single { GetIraqiMealsUseCase(get()) }
    single { IngredientGameMealsMapper() }
    single { RandomMealsForIngredientGame(get(),get()) }
    single { IngredientGameUseCase(get()) }
    single { GetQuickHealthyMealsUseCase(get()) }
    single { SuggestItalianFoodForLargeGroupUseCase(get()) }
    single { GetMealsContainsPotatoUseCase(get()) }
    single { GuessPreparationTimeGameUseCase(get()) }
    single { GetSeafoodMealsUseCase(get()) }
    single { SearchMealsByDateUseCase(get()) }
    single { SearchGymFriendlyMealsUseCase(get()) }

    single {
        SelectionOfSearchAlgorithms(
            listOf(KmpSearch(), LevenshteinSearch())
        )
    }
}