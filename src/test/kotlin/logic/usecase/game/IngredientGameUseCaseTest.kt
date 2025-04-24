package logic.usecase.game

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.berlin.logic.usecase.helper.*
import org.berlin.model.MealForIngredientGame
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class IngredientGameUseCaseTest{
  private lateinit var ingredientGameUseCase: IngredientGameUseCase
  private val randomMealsForIngredientGame= mockk<RandomMealsForIngredientGame>()

  @BeforeEach
  fun setup(){
   ingredientGameUseCase=IngredientGameUseCase(randomMealsForIngredientGame)
  }



 @Test
 fun `run should throw MealsNotEnoughException when meals is not match max questions`() {
  // Given
  every { randomMealsForIngredientGame.getMeals(15) }returns INVALID_MEALS
  // When // Then
  assertThrows<MealsNotEnoughException> {
   ingredientGameUseCase.run()
  }
 }



 @Test
 fun `run should initialize game with 15 meals and reset state`() {
  // Given
  every { randomMealsForIngredientGame.getMeals(any()) }returns VALID_MEALS
  // When
  ingredientGameUseCase.run()
  // Then
  assertThat(ingredientGameUseCase.isRunning()).isTrue()
 }


 @Test
 fun `run should initialize game with 15 meals and reset score`() {
  // Given
  every { randomMealsForIngredientGame.getMeals(any()) }returns VALID_MEALS
  // When
  ingredientGameUseCase.run()
  // Then
  assertThat(ingredientGameUseCase.getScore()).isEqualTo(0)
 }


 @Test
 fun `run should initialize game with 15 meals and reset meals index`() {
  // Given
  every { randomMealsForIngredientGame.getMeals(any()) }returns VALID_MEALS
  // When
  ingredientGameUseCase.run()
  // Then
  assertThat(ingredientGameUseCase.getCurrentMeal()).isEqualTo(VALID_MEALS[0])
 }


 @Test
 fun `submitUserAnswer should throw InvalidChoiceException when user choice 5 not in 1 , 2 , 3`() {
  // Given
  every { randomMealsForIngredientGame.getMeals(any()) }returns VALID_MEALS
  // When
  ingredientGameUseCase.run()
  // Then
  assertThrows<InvalidChoiceException> {
   ingredientGameUseCase.submitUserAnswer(5)
  }
 }


 @Test
 fun `submitUserAnswer should throw InvalidChoiceException when user choice 0 not in 1 , 2 , 3`() {
  // Given
  every { randomMealsForIngredientGame.getMeals(any()) }returns VALID_MEALS
  // When
  ingredientGameUseCase.run()
  // Then
  assertThrows<InvalidChoiceException> {
   ingredientGameUseCase.submitUserAnswer(0)
  }
 }



 @Test
 fun `submitUserAnswer should update state to lost when user choice wrong ingredient`() {
  // Given
  every { randomMealsForIngredientGame.getMeals(any()) }returns VALID_MEALS
  // When
  ingredientGameUseCase.run()
  ingredientGameUseCase.submitUserAnswer(1)
  // Then
  assertThat(ingredientGameUseCase.isRunning()).isFalse()

 }


 @Test
 fun `submitUserAnswer should increase score by 1000 when user choice correct ingredient`() {
  // Given
  every { randomMealsForIngredientGame.getMeals(any()) }returns VALID_MEALS
  // When
  ingredientGameUseCase.run()
  ingredientGameUseCase.submitUserAnswer(2)
  // Then
  assertThat(ingredientGameUseCase.getScore()).isEqualTo(1000)

 }


 @Test
 fun `submitUserAnswer should update state to won when user choice 15 times correct ingredient`() {
  // Given
  every { randomMealsForIngredientGame.getMeals(any()) }returns VALID_MEALS
  // When
  ingredientGameUseCase.run()
  choiceCorrectAnsMax(ingredientGameUseCase)
  // Then
  assertThat(ingredientGameUseCase.isRunning()).isFalse()

 }



 @Test
 fun `submitUserAnswer should update score to max score 15000 when user choice 15 times correct ingredient`() {
  // Given
  every { randomMealsForIngredientGame.getMeals(any()) }returns VALID_MEALS
  // When
  ingredientGameUseCase.run()
  choiceCorrectAnsMax(ingredientGameUseCase)
  // Then
  assertThat(ingredientGameUseCase.getScore()).isEqualTo(15000)

 }



 @Test
 fun `submitUserAnswer should throw IndexOutOfBoundsException when try to play more than max questions  `() {
  // Given
  every { randomMealsForIngredientGame.getMeals(any()) }returns VALID_MEALS
  // When // Then
  assertThrows<IndexOutOfBoundsException> {
   ingredientGameUseCase.run()
   choiceCorrectAnsMax(ingredientGameUseCase)
   choiceCorrectAnsMax(ingredientGameUseCase)
  }

 }


 @Test
 fun `submitUserAnswer should throw EmptyMealsException when try to submit user input but method run not called`() {
  // Given
  every { randomMealsForIngredientGame.getMeals(any()) }returns VALID_MEALS
  // When // Then
  assertThrows<EmptyMealsException> {
   ingredientGameUseCase.submitUserAnswer(2)
  }

 }


 @Test
 fun `getCurrentMeal should throw EmptyMealsException when try to get current meal but method run not called`() {
  // Given
  every { randomMealsForIngredientGame.getMeals(any()) }returns VALID_MEALS
  // When // Then
  assertThrows<EmptyMealsException> {
   ingredientGameUseCase.getCurrentMeal()
  }

 }


 private fun choiceCorrectAnsMax(ingredientGameUseCase: IngredientGameUseCase) {
  ingredientGameUseCase.submitUserAnswer(2)
  ingredientGameUseCase.submitUserAnswer(2)
  ingredientGameUseCase.submitUserAnswer(2)
  ingredientGameUseCase.submitUserAnswer(2)
  ingredientGameUseCase.submitUserAnswer(2)
  ingredientGameUseCase.submitUserAnswer(2)
  ingredientGameUseCase.submitUserAnswer(2)
  ingredientGameUseCase.submitUserAnswer(2)
  ingredientGameUseCase.submitUserAnswer(2)
  ingredientGameUseCase.submitUserAnswer(2)
  ingredientGameUseCase.submitUserAnswer(2)
  ingredientGameUseCase.submitUserAnswer(2)
  ingredientGameUseCase.submitUserAnswer(2)
  ingredientGameUseCase.submitUserAnswer(2)
  ingredientGameUseCase.submitUserAnswer(2)
 }



 companion object {

  val VALID_MEALS = List(15) {
   MealForIngredientGame("aaaa", "b", listOf("a", "b", "c"))
  }
  val INVALID_MEALS = List(14) {
   MealForIngredientGame("aaaa", "b", listOf("a", "b", "c"))
  }
 }

 }