package org.berlin.dependencyInjection

import org.berlin.data.EasyFoodSuggestionRepositoryImp
import org.berlin.logic.usecase.EasyFoodSuggestionRepository
import org.berlin.presentation.FoodChangeMoodUI
import org.koin.dsl.module

val easyFoodSuggestionModule= module{
    single { FoodChangeMoodUI(get()) }
    single<EasyFoodSuggestionRepository> { EasyFoodSuggestionRepositoryImp(get(), get()) }
}