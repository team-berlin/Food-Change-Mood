package org.berlin.dependency_injection

import com.berlin.data.CsvFileReader
import com.berlin.data.CsvMealsRepository
import data.MealsCsvParser
import org.berlin.logic.MealsRepository
import org.berlin.logic.usecase.QuickHealthyMealsUseCase
import org.berlin.presentation.FoodChangeMoodUI
import org.koin.dsl.module
import java.io.File

val appModule = module {
        single { File("food.csv") }
        single { CsvFileReader(get()) }
        single { MealsCsvParser() }

        single<MealsRepository> { CsvMealsRepository(get(), get()) }

        // Logic Layer
        single { QuickHealthyMealsUseCase(get()) }

        // Presentation Layer
        single { FoodChangeMoodUI(get() , get(), get(), get(), get()) }
}
