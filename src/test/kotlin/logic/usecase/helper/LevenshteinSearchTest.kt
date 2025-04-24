package logic.usecase.helper

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

 class LevenshteinSearchTest{
  private val levenshteinSearchTest = LevenshteinSearch()

  private companion object {
   val textWordInItWithTypoLessThanThreshold = "cheese cake"
   val wordWithTypoLessThanThreshold = "chedsecake"
   val textWhichWordTypoEqualThreshold = "goodCheese"
   val wordWithTypoEqualthreshold = "geedChoese"
   val textWhichWordTypoGreaterThanThreshold = "goodCheese"
   val wordWithTypoGreaterThanthreshold = "geedChoose"
  }

  @Test
  fun `search should return true when the word is exist in text and the typo is less than threshold`() {
   //Given
   val text = textWordInItWithTypoLessThanThreshold
   val word = wordWithTypoLessThanThreshold

   //when
   val result = levenshteinSearchTest.search(text, word)

   //Then
   assertThat(result).isTrue()

  }

  @Test
  fun `search should return true when typo in word equal threshold`() {
   //Given
   val text = textWhichWordTypoEqualThreshold
   val word = wordWithTypoEqualthreshold

   //when
   val result = levenshteinSearchTest.search(text, word)

   //Then
   assertThat(result).isTrue()
  }

  @Test
  fun `search should return false when typo in word greater than threshold`() {
   //Given
   val text = textWhichWordTypoGreaterThanThreshold
   val word = wordWithTypoGreaterThanthreshold

   //when
   val result = levenshteinSearchTest.search(text, word)

   //Then
   assertThat(result).isFalse()
  }
 }