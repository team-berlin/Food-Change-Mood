package dependency_injection;

import com.berlin.data.CsvFileReader
import com.berlin.data.CsvMealsRepository
import com.berlin.data.MealsCsvParser
import org.berlin.logic.MealsRepository
import org.berlin.logic.usecase.HighCalorieMealsUseCase
import org.berlin.presentation.FoodChangeMoodUI
import org.koin.dsl.module
import java.io.File

val appModule = module {
        // Data Layer
        single { File("food.csv") }
        single { CsvFileReader(get()) }
        single { MealsCsvParser() }
        single<MealsRepository> { CsvMealsRepository(get(), get()) }

        // Logic Layer
        single { HighCalorieMealsUseCase(get()) }

        // Presentation Layer
        single { FoodChangeMoodUI(get()) }
}
