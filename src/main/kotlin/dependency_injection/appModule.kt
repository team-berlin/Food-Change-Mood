package dependency_injection
import data.CsvFileReader
import data.CsvMealsRepository
import data.MealsCsvParser
import org.berlin.logic.MealsRepository
import org.berlin.logic.usecase.IdentifyIraqiMealsUseCase
import org.berlin.logic.usecase.QuickHealthyMealsUseCase
import org.berlin.logic.usecase.SuggestEggFreeSweetUseCase
import org.berlin.presentation.FoodChangeMoodUI
import org.berlin.logic.usecase.HighCalorieMealsUseCase
import org.koin.dsl.module
import java.io.File

val appModule = module {
        single { File("food.csv") }
        single { CsvFileReader(get()) }
        single { MealsCsvParser() }
        single<MealsRepository> { CsvMealsRepository(get(), get()) }
        single { QuickHealthyMealsUseCase(get()) }
        single { SuggestEggFreeSweetUseCase(get()) }
        single { IdentifyIraqiMealsUseCase(get()) }
        single { HighCalorieMealsUseCase(get()) }

        single { FoodChangeMoodUI(get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get() , get()) }

}
