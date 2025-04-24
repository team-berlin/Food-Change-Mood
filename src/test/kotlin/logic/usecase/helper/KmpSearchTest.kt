package logic.usecase.helper

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class KmpSearchTest {
    private val kmpSearch = KmpSearch()

    @Test
    fun `kmpSearch returns true when word is found on text `() {
        //Given
        val text = textThatWordOnIt
        val word = wordThatInText

        //when
        val result = kmpSearch.search(text, word)

        //Then
        assertThat(result).isTrue()
    }

    @Test
    fun `kmpSearch returns true when word is empty`() {
        //Given
        val text = textThatWordOnIt
        val word = emptyWord

        //when
        val result = kmpSearch.search(text, word)

        //Then
        assertThat(result).isTrue()
    }

    @Test
    fun `kmpSearch returns false when text is empty`() {
        //Given
        val text = emptyText
        val word = wordNotInEmptytext

        //when
        val result = kmpSearch.search(text, word)

        //Then
        assertThat(result).isFalse()
    }

    @Test
    fun `kmpSearch returns true when word and text is empty`() {
        //Given
        val text = emptyText
        val word = emptyWord

        //when
        val result = kmpSearch.search(text, word)

        //Then
        assertThat(result).isTrue()
    }

    @Test
    fun `kmpSearch returns false when word is totally not exist in text`() {
        //Given
        val text = textNotHasWordTotally
        val word = wordTotallyNotInText

        //when
        val result = kmpSearch.search(text, word)

        //Then
        assertThat(result).isFalse()
    }

    @Test
    fun `kmpSearch returns true when word is found on text and prefix equal suffix`() {
        //Given
        val text = textPrefixEqualSuffix
        val word = wordPrefixEqualsuffix

        //when
        val result = kmpSearch.search(text, word)

        //Then
        assertThat(result).isTrue()
    }

    @Test
    fun `kmpSearch returns true when word is found on text and there is mismatch in lsp`() {
        //Given
        val text = textWithMismatch
        val word = wordWithMismatch

        //when
        val result = kmpSearch.search(text, word)

        //Then
        assertThat(result).isFalse()
    }

    private companion object {
        val textThatWordOnIt = "cheese cake"
        val wordThatInText = "cheese"
        val emptyWord = ""
        val emptyText = ""
        val wordNotInEmptytext = "cheese"
        val textNotHasWordTotally = "fried chicken"
        val wordTotallyNotInText = "pizza"
        val textPrefixEqualSuffix = "abababababa"
        val wordPrefixEqualsuffix = "abab"
        val textWithMismatch = "ababababc"
        val wordWithMismatch = "ababac"
    }

}
