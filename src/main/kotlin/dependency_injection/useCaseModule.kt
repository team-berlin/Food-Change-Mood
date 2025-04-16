package org.berlin.dependency_injection

import org.berlin.logic.usecase.HighCalorieMealsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { HighCalorieMealsUseCase(get()) }
}