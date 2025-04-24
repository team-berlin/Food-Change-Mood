package presentation.search

import io.mockk.mockk
import io.mockk.verify
import org.berlin.logic.usecase.search.SearchMealsByNameUseCase
import org.berlin.presentation.input_output.Reader
import org.berlin.presentation.input_output.Viewer
import org.berlin.presentation.search.SearchMealsByNameUI
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class SearchMealsByNameUITest{
  private lateinit var searchMealsByNameUseCase: SearchMealsByNameUseCase
  private  var viewer: Viewer= mockk(relaxed = true)
  private var reader: Reader= mockk(relaxed = true)
  private lateinit var searchMealsByNameUI: SearchMealsByNameUI
  @BeforeEach
  fun setup(){
   searchMealsByNameUseCase=mockk(relaxed=true)
   searchMealsByNameUI=SearchMealsByNameUI(searchMealsByNameUseCase,viewer,reader)

  }
    @Test
    fun `run should call viewer when the presentation start`() {

        // When
        searchMealsByNameUI.run()

        // Then
       verify { viewer.display(any()) }
    }
    @Test
    fun `run should call reader when the presentation start and the viewer display`() {

        // When
        searchMealsByNameUI.run()

        // Then
        verify { reader.getUserInput() }
    }
 }
