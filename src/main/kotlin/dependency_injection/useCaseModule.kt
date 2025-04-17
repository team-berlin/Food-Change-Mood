package org.berlin.dependency_injection

import org.berlin.logic.usecase.SuggestItalianFoodForLargeGroupUseCase
import org.koin.dsl.module

val useCaseModule = module {

    single { SuggestItalianFoodForLargeGroupUseCase(get()) }

}