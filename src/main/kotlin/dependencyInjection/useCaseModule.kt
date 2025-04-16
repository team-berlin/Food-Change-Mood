package org.berlin.dependencyInjection


import org.berlin.logic.GetMealsContainsPotatoUseCase
import org.berlin.presentation.FoodChangeMoodUI
import org.koin.dsl.module

val useCaseModule = module {
    single { GetMealsContainsPotatoUseCase(get()) }
    single { FoodChangeMoodUI(get()) }
}
