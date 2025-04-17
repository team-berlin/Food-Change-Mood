package org.berlin.dependency_injection

import org.berlin.logic.usecases.SearchMealsByNameUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { SearchMealsByNameUseCase(get()) }
}