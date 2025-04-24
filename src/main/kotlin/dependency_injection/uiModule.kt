package dependency_injection

import org.berlin.presentation.MainMenuUI
import org.berlin.presentation.UiRunner
import org.berlin.presentation.game.GuessPreparationTimeGameUI
import org.berlin.presentation.game.IngredientGameUI
import org.berlin.presentation.input_output.ConsoleReader
import org.berlin.presentation.input_output.ConsoleViewer
import org.berlin.presentation.input_output.Reader
import org.berlin.presentation.input_output.Viewer
import org.berlin.presentation.retrieval.GetMealsContainsPotatoUI
import org.berlin.presentation.retrieval.GetSeafoodMealsUI
import org.berlin.presentation.retrieval.GetIraqiMealsUI
import org.berlin.presentation.retrieval.GetQuickHealthyMealsUI
import org.berlin.presentation.search.SearchFoodByCultureUI
import org.berlin.presentation.search.SearchGymFriendlyMealsUI
import org.berlin.presentation.search.SearchMealsByDateUI
import org.berlin.presentation.search.SearchMealsByNameUI
import org.berlin.presentation.suggest.SuggestEggFreeSweetUI
import org.berlin.presentation.suggest.SuggestHighCalorieMealsUI
import org.berlin.presentation.suggest.SuggestItalianFoodForLargeGroupUI
import org.berlin.presentation.suggest.SuggestionEasyFoodUI
import org.koin.dsl.bind
import org.koin.dsl.module
import presentation.suggest.SuggestKetoMealUI

val uiModule = module {
    single<Viewer> { ConsoleViewer() }
    single<Reader> { ConsoleReader() }

    single { GetIraqiMealsUI(get(), get()) } bind UiRunner::class
    single { GetMealsContainsPotatoUI(get(), get()) } bind UiRunner::class
    single { GetSeafoodMealsUI(get(), get()) } bind UiRunner::class
    single { SearchMealsByNameUI(get(), get(), get()) } bind UiRunner::class
    single { SearchMealsByDateUI(get(), get(), get()) } bind UiRunner::class
    single { SearchFoodByCultureUI(get(), get()) } bind UiRunner::class
    single { SearchGymFriendlyMealsUI(get(), get(), get()) } bind UiRunner::class
    single { SuggestionEasyFoodUI(get(), get()) } bind UiRunner::class
    single { SuggestEggFreeSweetUI(get(), get(), get()) } bind UiRunner::class
    single { SuggestKetoMealUI(get(), get(), get()) } bind UiRunner::class
    single { SuggestItalianFoodForLargeGroupUI(get(), get()) } bind UiRunner::class
    single { GuessPreparationTimeGameUI(get(), get(), get()) } bind UiRunner::class
    single { IngredientGameUI(get(), get(), get()) } bind UiRunner::class
    single { GetQuickHealthyMealsUI(get(), get()) } bind UiRunner::class
    single { SuggestHighCalorieMealsUI(get(), get()) } bind UiRunner::class

    single {
        MainMenuUI(
            runners = getAll<UiRunner>(), viewer = get(), reader = get()
        )
    }
}
