package org.berlin.dependency_injection

import org.berlin.logic.usecase.QuickHealthyMealsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { QuickHealthyMealsUseCase(get()) }
}