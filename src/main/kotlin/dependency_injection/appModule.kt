package dependency_injection;

import data.CsvFileReader
import data.MealsCsvParser
import data.CsvMealsRepository
import org.berlin.logic.MealsRepository
import org.berlin.presentation.FoodChangeMoodUI
import org.koin.dsl.module
import java.io.File

val appModule = module {
        single { File("food.csv") }
        single { CsvFileReader(get()) }
        single { MealsCsvParser() }

        single<MealsRepository> { CsvMealsRepository(get(), get()) }
        single { FoodChangeMoodUI(get()) }

}
