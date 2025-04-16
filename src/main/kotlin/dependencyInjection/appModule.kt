package dependencyInjection

import com.berlin.data.CsvFileReader
import com.berlin.data.CsvMealsRepository
import com.berlin.data.MealsCsvParser
import org.berlin.logic.MealsRepository
import org.berlin.logic.SearchMealsByDateUseCase
import org.berlin.logic.SearchMealsByDateUseCaseImpl
import org.koin.dsl.module
import java.io.File

val appModule = module {
        single { File("food.csv") }
        single { CsvFileReader(get()) }
        single { MealsCsvParser() }

        single<MealsRepository> { CsvMealsRepository(get(), get()) }
        single<SearchMealsByDateUseCase> { SearchMealsByDateUseCaseImpl(get()) }
}