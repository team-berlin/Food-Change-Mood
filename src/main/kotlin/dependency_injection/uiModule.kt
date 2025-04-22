import org.berlin.presentation.MainMenuUI
import org.berlin.presentation.UiRunner
import org.berlin.presentation.game.GuessPreparationTimeGameUI
import org.berlin.presentation.game.IngredientGameInteractorUI
import org.berlin.presentation.input_output.ConsoleReader
import org.berlin.presentation.input_output.ConsoleViewer
import org.berlin.presentation.input_output.Reader
import org.berlin.presentation.input_output.Viewer
import org.berlin.presentation.retrieval.*
import org.berlin.presentation.search.ExploreFoodCultureUI
import org.berlin.presentation.search.GymHelperUI
import org.berlin.presentation.search.SearchMealsByDateUI
import org.berlin.presentation.search.SearchMealsByNameUI
import org.berlin.presentation.suggest.SuggestEggFreeSweetUI
import org.koin.dsl.bind
import org.koin.dsl.module
import presentation.suggest.SuggestKetoMealUI

val uiModule = module {
    single<Viewer> { ConsoleViewer() }
    single<Reader> { ConsoleReader() }

    single { IdentifyIraqiMealsUI(get(), get()) } bind UiRunner::class
    single { GetMealsContainsPotatoUI(get(), get()) } bind UiRunner::class
    single { GetSeafoodMealsUseCaseUI(get(), get()) } bind UiRunner::class
    single { SearchMealsByNameUI(get(), get()) } bind UiRunner::class
    single { SearchMealsByDateUI(get(), get(), get()) } bind UiRunner::class
    single { ExploreFoodCultureUI(get(), get()) } bind UiRunner::class
    single { GymHelperUI(get(), get(), get()) } bind UiRunner::class
    single { SuggestionEasyFoodUI(get(), get()) } bind UiRunner::class
    single { SuggestEggFreeSweetUI(get(), get(), get()) } bind UiRunner::class
    single { SuggestKetoMealUI(get(), get(), get()) } bind UiRunner::class
    single { SuggestItalianFoodForLargeGroupUI(get(), get()) } bind UiRunner::class
    single { GuessPreparationTimeGameUI(get(), get(), get()) } bind UiRunner::class
    single { IngredientGameInteractorUI (get(), get(), get()) } bind UiRunner::class

    single {
        MainMenuUI(
            runners = getAll<UiRunner>(),
            viewer  = get(),
            reader  = get()
        )
    }
}
