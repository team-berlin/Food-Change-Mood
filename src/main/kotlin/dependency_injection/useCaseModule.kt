package org.berlin.dependency_injection

import org.berlin.logic.IngredientGameMealsMapper
import logic.usecase.game.GuessPreparationTimeGameUseCase
import org.berlin.logic.game.GymHelperUseCase
import logic.usecase.game.IngredientGameUseCase
import org.berlin.logic.usecase.retrieval.*
import org.berlin.logic.usecase.search.ExploreFoodCultureUseCase
import org.berlin.logic.usecase.search.SearchMealsByDateUseCase
import org.berlin.logic.usecase.search.SearchMealsByNameUseCase
import org.berlin.logic.usecase.suggest.SuggestEasyFoodUseCase
import org.berlin.logic.usecase.suggest.SuggestEggFreeSweetUseCase
import org.berlin.logic.usecase.suggest.SuggestItalianFoodForLargeGroupUseCase
import org.berlin.logic.usecase.suggest.SuggestKetoMealUseCase
import org.berlin.presentation.IngredientGameInteractor
import org.koin.dsl.module

val useCaseModule = module {
    single { HighCalorieMealsUseCase(get()) }
    single {
        SearchMealsByNameUseCase(
            get(), get()
        )
    }
    single { ExploreFoodCultureUseCase(get()) }
    single { SuggestKetoMealUseCase(get()) }
    single { SuggestEasyFoodUseCase(get()) }
    single { SuggestEggFreeSweetUseCase(get()) }
    single { IdentifyIraqiMealsUseCase(get()) }
    single { IngredientGameMealsMapper() }
    single { IngredientGameUseCase(get(), get()) }
    single { IngredientGameInteractor(get()) }
    single { QuickHealthyMealsUseCase(get()) }
    single { SuggestItalianFoodForLargeGroupUseCase(get()) }
    single { GetMealsContainsPotatoUseCase(get()) }
    single { GuessPreparationTimeGameUseCase(get()) }
    single { GetSeafoodMealsUseCase(get()) }
    single { SearchMealsByDateUseCase(get()) }
    single { GymHelperUseCase(get()) }
}