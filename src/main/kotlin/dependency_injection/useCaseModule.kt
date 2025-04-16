package org.berlin.dependency_injection

import org.berlin.logic.usecase.SuggestKetoMealUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { SuggestKetoMealUseCase(get()) }
}