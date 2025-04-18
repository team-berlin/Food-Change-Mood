package dependency_injection
import MealsCsvParser
import data.CsvFileReader
import com.berlin.data.CsvMealsRepository
import org.berlin.logic.MealsRepository
import org.berlin.logic.usecase.IdentifyIraqiMealsUseCase
import org.berlin.logic.usecase.QuickHealthyMealsUseCase
import org.berlin.logic.usecase.SuggestEggFreeSweetUseCase
import org.berlin.presentation.FoodChangeMoodUI
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

        single { FoodChangeMoodUI(get() , get(), get(), get(), get(), get(), get(), get()) }
}
