package dependency_injection

import data.CsvFileReader
import data.CsvMealsRepository
import data.MealsCsvParser
import org.berlin.logic.repository.MealsRepository
import org.koin.dsl.module
import java.io.File

val appModule = module {
    single { File("food.csv") }
    single { CsvFileReader(get()) }
    single { MealsCsvParser() }
    single<MealsRepository> { CsvMealsRepository(get(), get()) }
}
