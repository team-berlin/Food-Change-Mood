package org.berlin.dependency_injection

import org.berlin.logic.GuessPreparationTimeGameUseCase
import org.berlin.presentation.FoodChangeMoodUI
import org.koin.dsl.module

val useCaseModule = module {
    single { GuessPreparationTimeGameUseCase(get()) }
    single { FoodChangeMoodUI(get()) }
}