package dependency_injection

import data.CsvFileReader
import data.CsvMealsRepository
import data.MealsCsvParser
import org.berlin.logic.repository.MealsRepository
import org.berlin.logic.search.CombineSearchAlgorithms
import org.berlin.logic.search.KmpSearch
import org.berlin.logic.search.LevenshteinSearch
import org.berlin.logic.search.SearchByName
import org.berlin.logic.usecase.retrieval.IdentifyIraqiMealsUseCase
import org.berlin.logic.usecase.retrieval.QuickHealthyMealsUseCase
import org.berlin.logic.usecase.suggest.SuggestEggFreeSweetUseCase
import org.berlin.logic.usecase.suggest.SuggestHighCalorieMealsUseCase
import org.berlin.presentation.FoodChangeMoodUI
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.io.File

val appModule = module {
    single { File("D:\\TheChance\\food.csv") }
    single { CsvFileReader(get()) }
    single { MealsCsvParser() }
    single<MealsRepository> { CsvMealsRepository(get(), get()) }
    single { QuickHealthyMealsUseCase(get()) }
    single { SuggestEggFreeSweetUseCase(get()) }
    single { IdentifyIraqiMealsUseCase(get()) }
    single { SuggestHighCalorieMealsUseCase(get()) }
    single<SearchByName>(qualifier = named("kmp")) { KmpSearch() }
    single<SearchByName>(qualifier = named("levenshtein")) { LevenshteinSearch() }
    single {
        CombineSearchAlgorithms(
            get<SearchByName>(qualifier = named("kmp"))
                ,get<SearchByName>(qualifier = named("levenshtein"))
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
