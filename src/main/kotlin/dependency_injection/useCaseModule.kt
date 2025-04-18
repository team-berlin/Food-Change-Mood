package org.berlin.dependency_injection

import org.berlin.logic.ingredient_game.IngredientGameMealsMapper
import org.berlin.logic.ingredient_game.IngredientGameUseCase
import org.berlin.presentation.IngredientGameInteractor
import org.koin.dsl.module

val useCaseModule = module {

    single { IngredientGameMealsMapper() }
    single { IngredientGameUseCase(get(),get()) }
    single { IngredientGameInteractor(get()) }
}