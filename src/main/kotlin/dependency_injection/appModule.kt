package dependency_injection

import data.CsvFileReader
import data.CsvMealsRepository
import data.MealsCsvParser
import org.berlin.logic.repository.MealsRepository
import org.berlin.logic.search.CombineSearchAlgorithms
import org.berlin.logic.search.KmpSearch
import org.berlin.logic.search.LevenshteinSearch
import org.berlin.logic.usecase.retrieval.HighCalorieMealsUseCase
import org.berlin.logic.usecase.retrieval.IdentifyIraqiMealsUseCase
import org.berlin.logic.usecase.retrieval.QuickHealthyMealsUseCase
import org.berlin.logic.usecase.suggest.SuggestEggFreeSweetUseCase
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
    single { HighCalorieMealsUseCase(get()) }
    single {
        CombineSearchAlgorithms(
            listOf(KmpSearch(),LevenshteinSearch())
        )
    }

    single {
        FoodChangeMoodUI(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }

}
