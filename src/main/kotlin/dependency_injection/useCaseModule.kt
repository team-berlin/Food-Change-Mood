package org.berlin.dependency_injection
import org.berlin.logic.usecase.*

import org.koin.dsl.module

val useCaseModule = module {
    single { ExploreFoodCultureUseCase(get()) }
    single { SuggestKetoMealUseCase(get()) }
    single { EasyFoodSuggestionUseCase(get()) }
    single { SuggestEggFreeSweetUseCase(get()) }
    single { IdentifyIraqiMealsUseCase(get()) }
}



