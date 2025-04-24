package org.berlin.dependency_injection

import logic.usecase.game.GuessPreparationTimeGameUseCase
import logic.usecase.game.IngredientGameUseCase
import org.berlin.logic.IngredientGameMealsMapper
import org.berlin.logic.search.SelectionOfSearchAlgorithms
import org.berlin.logic.search.KmpSearch
import org.berlin.logic.search.LevenshteinSearch
import org.berlin.logic.usecase.retrieval.GetMealsContainsPotatoUseCase
import org.berlin.logic.usecase.retrieval.GetSeafoodMealsUseCase
import org.berlin.logic.usecase.retrieval.GetIraqiMealsUseCase
import org.berlin.logic.usecase.retrieval.GetQuickHealthyMealsUseCase
import org.berlin.logic.usecase.search.ExploreFoodCultureUseCase
import org.berlin.logic.usecase.search.GymHelperUseCase
import org.berlin.logic.usecase.search.SearchMealsByDateUseCase
import org.berlin.logic.usecase.search.SearchMealsByNameUseCase
import org.berlin.logic.usecase.suggest.*
import org.berlin.presentation.IngredientGameInteractor
import org.koin.dsl.module

val useCaseModule = module {
    single { SuggestHighCalorieMealsUseCase(get()) }
    single { SearchMealsByNameUseCase(get(), get()) }
    single { ExploreFoodCultureUseCase(get()) }
    single { SuggestKetoMealUseCase(get()) }
    single { SuggestEasyFoodUseCase(get()) }
    single { SuggestEggFreeSweetUseCase(get()) }
    single { GetIraqiMealsUseCase(get()) }
    single { IngredientGameMealsMapper() }
    single { IngredientGameUseCase(get(), get()) }
    single { IngredientGameInteractor(get()) }
    single { GetQuickHealthyMealsUseCase(get()) }
    single { SuggestItalianFoodForLargeGroupUseCase(get()) }
    single { GetMealsContainsPotatoUseCase(get()) }
    single { GuessPreparationTimeGameUseCase(get()) }
    single { GetSeafoodMealsUseCase(get()) }
    single { SearchMealsByDateUseCase(get()) }
    single { GymHelperUseCase(get()) }

    single {
        SelectionOfSearchAlgorithms(
            listOf(KmpSearch(), LevenshteinSearch())
        )
    }
}