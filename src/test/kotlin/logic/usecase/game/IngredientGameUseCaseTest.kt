package logic.usecase.game

import com.google.common.truth.Truth.assertThat
import createMeal
import io.mockk.every
import io.mockk.mockk
import org.berlin.logic.repository.MealsRepository
import org.berlin.logic.usecase.helper.IngredientGameMealsMapper
import org.berlin.logic.usecase.helper.MealsNotEnoughException
import org.berlin.model.MealForIngredientGame
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class IngredientGameUseCaseTest{
  private lateinit var ingredientGameUseCase: IngredientGameUseCase
  private val mapper:IngredientGameMealsMapper= mockk(relaxed = true)
  private val repository:MealsRepository= mockk(relaxed = true)

  @BeforeEach
  fun setup(){
   every { repository.getAllMeals() }returns LIST_OF_VALID_NORMAL_MEALS
  }

 @Test
 fun `getFifteenMeals should return 15 random meals when repo mapper return 15 meals or more`(){
  //Given
  every { mapper.map(LIST_OF_VALID_NORMAL_MEALS) }returns LIST_OF_VALID_15_MEALS
  ingredientGameUseCase=IngredientGameUseCase(repository,mapper)
  //When
  val result =ingredientGameUseCase.getFifteenMeals()
  //Then
  assertThat(result.size).isEqualTo(15)

 }

 @Test
 fun `getFifteenMeals should throw MealsNotEnoughException when mapped meals less than 15`(){
  //Given
  every { mapper.map(LIST_OF_VALID_NORMAL_MEALS) }returns LIST_OF_VALID_MEALS_FOR_INGREDIENT_GAME.take(10)
  ingredientGameUseCase=IngredientGameUseCase(repository,mapper)
  //When //Then
  assertThrows<MealsNotEnoughException> {
   ingredientGameUseCase.getFifteenMeals()
  }

 }



 companion object {
  val LIST_OF_VALID_MEALS_FOR_INGREDIENT_GAME=listOf(
   MealForIngredientGame(mealName = "Meal 1", correctIngredient = "a1", threeIngredientOnlyOneCorrect = listOf("a1", "b1", "c1")),
   MealForIngredientGame(mealName = "Meal 2", correctIngredient = "a2", threeIngredientOnlyOneCorrect = listOf("a2", "b2", "c2")),
   MealForIngredientGame(mealName = "Meal 3", correctIngredient = "a3", threeIngredientOnlyOneCorrect = listOf("a3", "b3", "c3")),
   MealForIngredientGame(mealName = "Meal 4", correctIngredient = "a4", threeIngredientOnlyOneCorrect = listOf("a4", "b4", "c4")),
   MealForIngredientGame(mealName = "Meal 5", correctIngredient = "a5", threeIngredientOnlyOneCorrect = listOf("a5", "b5", "c5")),
   MealForIngredientGame(mealName = "Meal 6", correctIngredient = "a6", threeIngredientOnlyOneCorrect = listOf("a6", "b6", "c6")),
   MealForIngredientGame(mealName = "Meal 7", correctIngredient = "a7", threeIngredientOnlyOneCorrect = listOf("a7", "b7", "c7")),
   MealForIngredientGame(mealName = "Meal 8", correctIngredient = "a8", threeIngredientOnlyOneCorrect = listOf("a8", "b8", "c8")),
   MealForIngredientGame(mealName = "Meal 9", correctIngredient = "a9", threeIngredientOnlyOneCorrect = listOf("a9", "b9", "c9")),
   MealForIngredientGame(mealName = "Meal 10", correctIngredient = "a10", threeIngredientOnlyOneCorrect = listOf("a10", "b10", "c10")),
   MealForIngredientGame(mealName = "Meal 11", correctIngredient = "a11", threeIngredientOnlyOneCorrect = listOf("a11", "b11", "c11")),
   MealForIngredientGame(mealName = "Meal 12", correctIngredient = "a12", threeIngredientOnlyOneCorrect = listOf("a12", "b12", "c12")),
   MealForIngredientGame(mealName = "Meal 13", correctIngredient = "a13", threeIngredientOnlyOneCorrect = listOf("a13", "b13", "c13")),
   MealForIngredientGame(mealName = "Meal 14", correctIngredient = "a14", threeIngredientOnlyOneCorrect = listOf("a14", "b14", "c14")),
   MealForIngredientGame(mealName = "Meal 15", correctIngredient = "a15", threeIngredientOnlyOneCorrect = listOf("a15", "b15", "c15")),
   MealForIngredientGame(mealName = "Meal 16", correctIngredient = "a16", threeIngredientOnlyOneCorrect = listOf("a16", "b16", "c16")),
   MealForIngredientGame(mealName = "Meal 17", correctIngredient = "a17", threeIngredientOnlyOneCorrect = listOf("a17", "b17", "c17")),
   MealForIngredientGame(mealName = "Meal 18", correctIngredient = "a18", threeIngredientOnlyOneCorrect = listOf("a18", "b18", "c18")),
   MealForIngredientGame(mealName = "Meal 19", correctIngredient = "a19", threeIngredientOnlyOneCorrect = listOf("a19", "b19", "c19")),
   MealForIngredientGame(mealName = "Meal 20", correctIngredient = "a20", threeIngredientOnlyOneCorrect = listOf("a20", "b20", "c20")),
   MealForIngredientGame(mealName = "Meal 21", correctIngredient = "a21", threeIngredientOnlyOneCorrect = listOf("a21", "b21", "c21")),
   MealForIngredientGame(mealName = "Meal 22", correctIngredient = "a22", threeIngredientOnlyOneCorrect = listOf("a22", "b22", "c22")),
   MealForIngredientGame(mealName = "Meal 23", correctIngredient = "a23", threeIngredientOnlyOneCorrect = listOf("a23", "b23", "c23")),
   MealForIngredientGame(mealName = "Meal 24", correctIngredient = "a24", threeIngredientOnlyOneCorrect = listOf("a24", "b24", "c24")),
   MealForIngredientGame(mealName = "Meal 25", correctIngredient = "a25", threeIngredientOnlyOneCorrect = listOf("a25", "b25", "c25")),
   MealForIngredientGame(mealName = "Meal 26", correctIngredient = "a26", threeIngredientOnlyOneCorrect = listOf("a26", "b26", "c26")),
   MealForIngredientGame(mealName = "Meal 27", correctIngredient = "a27", threeIngredientOnlyOneCorrect = listOf("a27", "b27", "c27")),
   MealForIngredientGame(mealName = "Meal 28", correctIngredient = "a28", threeIngredientOnlyOneCorrect = listOf("a28", "b28", "c28")),
   MealForIngredientGame(mealName = "Meal 29", correctIngredient = "a29", threeIngredientOnlyOneCorrect = listOf("a29", "b29", "c29")),
   MealForIngredientGame(mealName = "Meal 30", correctIngredient = "a30", threeIngredientOnlyOneCorrect = listOf("a30", "b30", "c30")),
   MealForIngredientGame(mealName = "Meal 31", correctIngredient = "a31", threeIngredientOnlyOneCorrect = listOf("a31", "b31", "c31")),
   MealForIngredientGame(mealName = "Meal 32", correctIngredient = "a32", threeIngredientOnlyOneCorrect = listOf("a32", "b32", "c32")),
   MealForIngredientGame(mealName = "Meal 33", correctIngredient = "a33", threeIngredientOnlyOneCorrect = listOf("a33", "b33", "c33")),
   MealForIngredientGame(mealName = "Meal 34", correctIngredient = "a34", threeIngredientOnlyOneCorrect = listOf("a34", "b34", "c34")),
   MealForIngredientGame(mealName = "Meal 35", correctIngredient = "a35", threeIngredientOnlyOneCorrect = listOf("a35", "b35", "c35")),
   MealForIngredientGame(mealName = "Meal 36", correctIngredient = "a36", threeIngredientOnlyOneCorrect = listOf("a36", "b36", "c36")),
   MealForIngredientGame(mealName = "Meal 37", correctIngredient = "a37", threeIngredientOnlyOneCorrect = listOf("a37", "b37", "c37")),
   MealForIngredientGame(mealName = "Meal 38", correctIngredient = "a38", threeIngredientOnlyOneCorrect = listOf("a38", "b38", "c38")),
   MealForIngredientGame(mealName = "Meal 39", correctIngredient = "a39", threeIngredientOnlyOneCorrect = listOf("a39", "b39", "c39")),
   MealForIngredientGame(mealName = "Meal 40", correctIngredient = "a40", threeIngredientOnlyOneCorrect = listOf("a40", "b40", "c40")),
   MealForIngredientGame(mealName = "Meal 41", correctIngredient = "a41", threeIngredientOnlyOneCorrect = listOf("a41", "b41", "c41")),
   MealForIngredientGame(mealName = "Meal 42", correctIngredient = "a42", threeIngredientOnlyOneCorrect = listOf("a42", "b42", "c42")),
   MealForIngredientGame(mealName = "Meal 43", correctIngredient = "a43", threeIngredientOnlyOneCorrect = listOf("a43", "b43", "c43")),
   MealForIngredientGame(mealName = "Meal 44", correctIngredient = "a44", threeIngredientOnlyOneCorrect = listOf("a44", "b44", "c44")),
   MealForIngredientGame(mealName = "Meal 45", correctIngredient = "a45", threeIngredientOnlyOneCorrect = listOf("a45", "b45", "c45")),
   MealForIngredientGame(mealName = "Meal 46", correctIngredient = "a46", threeIngredientOnlyOneCorrect = listOf("a46", "b46", "c46")),
   MealForIngredientGame(mealName = "Meal 47", correctIngredient = "a47", threeIngredientOnlyOneCorrect = listOf("a47", "b47", "c47")),
   MealForIngredientGame(mealName = "Meal 48", correctIngredient = "a48", threeIngredientOnlyOneCorrect = listOf("a48", "b48", "c48")),
   MealForIngredientGame(mealName = "Meal 49", correctIngredient = "a49", threeIngredientOnlyOneCorrect = listOf("a49", "b49", "c49")),
   MealForIngredientGame(mealName = "Meal 50", correctIngredient = "a50", threeIngredientOnlyOneCorrect = listOf("a50", "b50", "c50"))
  )
  val LIST_OF_VALID_15_MEALS = LIST_OF_VALID_MEALS_FOR_INGREDIENT_GAME.take(15)
  val LIST_OF_VALID_NORMAL_MEALS=listOf(
   createMeal(numberOfIngredients = 6, ingredients = listOf("x1", "y2", "z3", "w4", "q5", "p6")),
   createMeal(numberOfIngredients = 3, ingredients = listOf("k1", "k2", "k3")),
   createMeal(numberOfIngredients = 4, ingredients = listOf("m1", "m2", "m3", "m4")),
   createMeal(numberOfIngredients = 5, ingredients = listOf("n1", "n2", "n3", "n4", "n5")),
   createMeal(numberOfIngredients = 6, ingredients = listOf("o1", "o2", "o3", "o4", "o5", "o6")),
   createMeal(numberOfIngredients = 2, ingredients = listOf("p1", "p2")),
   createMeal(numberOfIngredients = 3, ingredients = listOf("q1", "q2", "q3")),
   createMeal(numberOfIngredients = 4, ingredients = listOf("r1", "r2", "r3", "r4")),
   createMeal(numberOfIngredients = 5, ingredients = listOf("s1", "s2", "s3", "s4", "s5")),
   createMeal(numberOfIngredients = 6, ingredients = listOf("t1", "t2", "t3", "t4", "t5", "t6")),
   createMeal(numberOfIngredients = 7, ingredients = listOf("u1", "u2", "u3", "u4", "u5", "u6", "u7")),
   createMeal(numberOfIngredients = 3, ingredients = listOf("v1", "v2", "v3")),
   createMeal(numberOfIngredients = 5, ingredients = listOf("w1", "w2", "w3", "w4", "w5")),
   createMeal(numberOfIngredients = 4, ingredients = listOf("x2", "x3", "x4", "x5")),
   createMeal(numberOfIngredients = 6, ingredients = listOf("y3", "y4", "y5", "y6", "y7", "y8")),
   createMeal(numberOfIngredients = 2, ingredients = listOf("z1", "z2")),
   createMeal(numberOfIngredients = 3, ingredients = listOf("a11", "b11", "c11")),
   createMeal(numberOfIngredients = 5, ingredients = listOf("d11", "e11", "f11", "g11", "h11")),
   createMeal(numberOfIngredients = 4, ingredients = listOf("i11", "j11", "k11", "l11")),
   createMeal(numberOfIngredients = 6, ingredients = listOf("m11", "n11", "o11", "p11", "q11", "r11")),
   createMeal(numberOfIngredients = 7, ingredients = listOf("s11", "t11", "u11", "v11", "w11", "x11", "y11")),
   createMeal(numberOfIngredients = 3, ingredients = listOf("z11", "a12", "b12")),
   createMeal(numberOfIngredients = 5, ingredients = listOf("c12", "d12", "e12", "f12", "g12")),
   createMeal(numberOfIngredients = 4, ingredients = listOf("h12", "i12", "j12", "k12")),
   createMeal(numberOfIngredients = 6, ingredients = listOf("l12", "m12", "n12", "o12", "p12", "q12")),
   createMeal(numberOfIngredients = 2, ingredients = listOf("r12", "s12")),
   createMeal(numberOfIngredients = 3, ingredients = listOf("t12", "u12", "v12")),
   createMeal(numberOfIngredients = 4, ingredients = listOf("w12", "x12", "y12", "z12")),
   createMeal(numberOfIngredients = 5, ingredients = listOf("a13", "b13", "c13", "d13", "e13")),
   createMeal(numberOfIngredients = 6, ingredients = listOf("f13", "g13", "h13", "i13", "j13", "k13")),
   createMeal(numberOfIngredients = 7, ingredients = listOf("l13", "m13", "n13", "o13", "p13", "q13", "r13")),
   createMeal(numberOfIngredients = 3, ingredients = listOf("s13", "t13", "u13")),
   createMeal(numberOfIngredients = 5, ingredients = listOf("v13", "w13", "x13", "y13", "z13")),
   createMeal(numberOfIngredients = 4, ingredients = listOf("a14", "b14", "c14", "d14")),
   createMeal(numberOfIngredients = 6, ingredients = listOf("e14", "f14", "g14", "h14", "i14", "j14")),
   createMeal(numberOfIngredients = 2, ingredients = listOf("k14", "l14")),
   createMeal(numberOfIngredients = 3, ingredients = listOf("m14", "n14", "o14")),
   createMeal(numberOfIngredients = 4, ingredients = listOf("p14", "q14", "r14", "s14")),
   createMeal(numberOfIngredients = 5, ingredients = listOf("t14", "u14", "v14", "w14","x14"))
  )
 }

 }