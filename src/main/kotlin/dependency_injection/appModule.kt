package dependency_injection;

import com.berlin.data.CsvFileReader
import com.berlin.data.CsvMealsRepository
import com.berlin.data.MealsCsvParser
import org.berlin.logic.GetSeafoodMealsUseCase
import org.berlin.logic.MealsRepository
import org.berlin.presentation.FoodChangeMoodUI
import org.koin.dsl.module
import java.io.File

val appModule = module {
        single { File("food.csv") }
        single { CsvFileReader(get()) }
        single { MealsCsvParser() }

        single<MealsRepository> { CsvMealsRepository(get(), get()) }

        single{ GetSeafoodMealsUseCase(get()) }
        single{ FoodChangeMoodUI(get()) }



}
