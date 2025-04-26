package logic.usecase.helper

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SelectionOfSearchAlgorithmsTest {
    private lateinit var searchAlgorithm1: SearchByName
    private lateinit var searchAlgorithm2: SearchByName
    private lateinit var selectionOfSearchAlgorithms: SelectionOfSearchAlgorithms

    @BeforeEach
    fun setup() {
        searchAlgorithm1 = mockk()
        searchAlgorithm2 = mockk()
        selectionOfSearchAlgorithms = SelectionOfSearchAlgorithms(listOf(searchAlgorithm1, searchAlgorithm2))
    }

    @Test
    fun `search should return true when any algorithm return true`() {
        //Given
        every { searchAlgorithm1.search(any(), any()) } returns false
        every { searchAlgorithm2.search(any(), any()) } returns true

        //when
        val result = selectionOfSearchAlgorithms.search("hello", "helio")

        //Then
        assertThat(result).isTrue()

    }

    @Test
    fun `search should return true when the first algorithm return true`() {
        //Given
        every { searchAlgorithm1.search(any(), any()) } returns true
        every { searchAlgorithm2.search(any(), any()) } returns false

        //when
        val result = selectionOfSearchAlgorithms.search("hello", "helloWorld")

        //Then
        assertThat(result).isTrue()

    }

    @Test
    fun `search should return false when all algorithms return false`() {
        //Given
        every { searchAlgorithm1.search(any(), any()) } returns false
        every { searchAlgorithm2.search(any(), any()) } returns false

        //when
        val result = selectionOfSearchAlgorithms.search("hello", "goodMorning")

        //Then
        assertThat(result).isFalse()

    }
}
