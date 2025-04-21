package org.berlin.presentation

import kotlinx.datetime.LocalDate
import org.berlin.logic.usecase.search.SearchMealsByDateUseCase
import org.berlin.logic.InvalidInputForIngredientGameException
import logic.usecase.game.GuessPreparationTimeGameUseCase
import org.berlin.logic.usecase.retrieval.*
import org.berlin.logic.usecase.search.ExploreFoodCultureUseCase
import org.berlin.logic.usecase.search.GymHelperUseCase
import org.berlin.logic.usecase.search.SearchMealsByNameUseCase
import org.berlin.logic.usecase.suggest.*
import org.berlin.model.CaloriesAndProteinTolerance
import org.berlin.model.GymHelperInput
import org.berlin.model.Meal

class FoodChangeMoodUI(
    private val ingredientGame: IngredientGameInteractor,//
    private val identifyIraqiMealsUseCase: IdentifyIraqiMealsUseCase,
    private val suggestEggFreeSweetUseCase: SuggestEggFreeSweetUseCase,
    private val suggestKetoMealUseCase: SuggestKetoMealUseCase,
    private val easyFoodSuggestionRepository: SuggestEasyFoodUseCase,
    private val exploreFoodCultureUseCase: ExploreFoodCultureUseCase,//
    private val suggestItalianFoodForLargeGroupUseCase: SuggestItalianFoodForLargeGroupUseCase,//
    private val searchMealsByNameUseCase: SearchMealsByNameUseCase,//
    private val getMealsContainsPotatoUseCase: GetMealsContainsPotatoUseCase,
    private val guessPreparationTimeGameUseCase: GuessPreparationTimeGameUseCase,
    private val quickHealthyMealsUseCase: QuickHealthyMealsUseCase,
    private val suggestHighCalorieMealsUseCase: SuggestHighCalorieMealsUseCase,
    private val getSeafoodMealsUseCase: GetSeafoodMealsUseCase,
    private val searchMealsByDateUseCase: SearchMealsByDateUseCase,//
    private val gymHelperUseCase: GymHelperUseCase
) {
    fun start() {
        showWelcome()
        presentFeatures()
    }

    private fun presentFeatures() {
        showOptions()
        val input = getStringUserInput()

        when (input) {
            "1" -> launchQuickHealthyMeals()
            "2" -> launchSearchMealsByName()
            "3" -> launchIdentifyIraqiMeals()
            "4" -> launchEasyFoodSuggestion()
            "5" -> launchGuessPreparationTimeGame()
            "6" -> launchSuggestEggFreeSweet()
            "7" -> launchSuggestionKetoMeal()
            "8" -> launchSearchMealsByDate()
            "9" -> launchGymHelper()
            "10" -> launchExploreFoodCulture()
            "11" -> launchIngredientGameUseCase()
            "12" -> launchRandomPotatoesMeals()
            "13" -> launchHighCalorieMeal()
            "14" -> launchSeafoodMealsUseCase()
            "15" -> launchGetItalianMealsForLargeGroup()
            else -> println("Invalid Input")
        }

        presentFeatures()
    }

    private fun launchSeafoodMealsUseCase() {
        getSeafoodMealsUseCase.getSeafoodMeals().forEachIndexed { index, seafoodMeal ->
            println("${index + 1} ,Name:${seafoodMeal.name}, has protein value:${seafoodMeal.protein}")
        }
    }

    private fun launchHighCalorieMeal() {
        println("\n=== High Calorie Meal Suggestion ===")

        suggestHighCalorieMealsUseCase.suggestHighCalorieMeal()
            .fold(
                onSuccess = { meal ->
                    println("\nSuggested high-calorie meal:")
                    displayMeal(meal)
                },
                onFailure = { error ->
                    println("Error: ${error.message}")
                }
            )
    }

    private fun launchSearchMealsByDate() {
        println("\n=== Search Meals by Date ===")
        println("Please enter a date in the format YYYY-MM-DD:")
        val dateInput = getStringUserInput()

        if (dateInput.isNullOrBlank()) {
            println("Error: Date cannot be empty.")
            return
        }

        try {
            val date = LocalDate.parse(dateInput)
            println("Successfully parsed date: $date")

            println("Fetching meals...")
            val meals = searchMealsByDateUseCase.searchMealsByDate(date)

            if (meals.isEmpty()) {
                println("No meals found for date: $date")
            } else {
                println("\nFound ${meals.size} meals added on $date:")
                meals.forEach { meal ->
                    println("ID: ${meal.id}, Name: ${meal.name}")
                }

                println("\nWould you like to see details of a specific meal? (Enter meal ID or 'no'):")
                val mealIdInput = getStringUserInput()

                if (mealIdInput?.lowercase() != "no") {
                    try {
                        val mealId = mealIdInput?.toInt()
                        val selectedMeal = meals.find { it.id == mealId }

                        if (selectedMeal != null) {
                            showMealDetails(selectedMeal)
                        } else {
                            println("Meal with ID $mealId not found in the results.")
                        }
                    } catch (e: NumberFormatException) {
                        println("Invalid meal ID format.")
                    }
                }
            }
        } catch (e: Exception) {
            println("Error: ${e.javaClass.simpleName} - ${e.message}")
        }
    }

    private fun launchSearchMealsByName() {
        println("=== Search Meals by Name ===\n")
        print("Type the name or part of the meal you're looking for: ")

        val searchWord = readlnOrNull()?.trim().orEmpty()

        if (searchWord.isBlank()) {
            println("You didn't type anything. Please enter a word to search")
            return
        }

        val meals = searchMealsByNameUseCase.searchMealsByName(searchWord)

        if (meals.isEmpty()) {
            println("No meals found for \"$searchWord\".")
        } else {
            println("Found ${meals.size} result(s) for \"$searchWord\":\n")
            meals.forEachIndexed { index, meal ->
                println("${index + 1}. ${meal.name}")
            }
        }

    }

    private fun launchQuickHealthyMeals() {
        println("\n=== Quick & Healthy Meals ===")

        val meals = quickHealthyMealsUseCase.getQuickHealthyMeals()

        if (meals.isEmpty()) {
            println("No quick and healthy meals found.")
            return
        }

        displayMeals(meals)
    }

    private fun launchExploreFoodCulture() {
        print("Enter Country name:")
        readlnOrNull()?.takeIf { it.isNotBlank() }?.let { countryName ->
            try {
                val meals = exploreFoodCultureUseCase.exploreFoodByCountry(country = countryName)
                if (meals.isEmpty()) {
                    println(" \"$countryName\" is not found in any meal tags.")
                } else {
                    println("\nFound ${meals.size} meals related to \"$countryName\":")
                    meals.forEachIndexed { index, meal ->
                        println("${index + 1}. ${meal.name}")
                    }
                }
            } catch (e: Exception) {
                println("️ Something went wrong while searching for \"$countryName\".")
            }
        } ?: println(" Please enter a valid country name.")
    }

    private fun launchEasyFoodSuggestion() {
        val meals = easyFoodSuggestionRepository.getEasyFoodSuggestion()

        if (meals.isEmpty()) {
            println("No easy food meals")
        }
        meals.forEach { meal ->
            showEasyMealsDetails(meal)
        }
    }

    private fun showEasyMealsDetails(meal: Meal) {
        println(
            """
                    ${meal.name}
                    Time: ${meal.minutes} minutes
                    Ingredients: ${meal.nIngredients}
                    Steps: ${meal.nSteps}
                    ---------------------
                """.trimIndent()
        )
    }

    private fun launchSuggestionKetoMeal() {
        val shuffledMeals = suggestKetoMealUseCase.suggestKetoMeal()

        if (shuffledMeals.isEmpty()) {
            println("No keto‑friendly meals available. Goodbye!")
            return
        }

        for (selectedMeal in shuffledMeals) {
            println("\n---  Keto Meal Suggestion: ---")
            println("Name: ${selectedMeal.name}")
            println("Description: ${selectedMeal.description}")
            println("---------------------------")
            println("Like it? (type 'y' for show all details or 'n' for suggest another meal or 'e' for exit)")

            when (getStringUserInput().toString().lowercase()) {
                "y" -> {
                    showMealDetails(selectedMeal)
                    break
                }

                "n" -> {
                    println("Alright, let's try another meal.")
                    continue
                }

                "e" -> break
                else -> {
                    println("Invalid input, please try again...")
                    break
                }
            }
        }
        println("No more keto‑friendly meals available. Goodbye!")
    }

    private fun showMealDetails(meal: Meal) {
        println("Name: ${meal.name}")
        println("Description: ${meal.description ?: "No description provided."}")
        println("Preparation Time (minutes): ${meal.minutes}")
        println("Tags: ${meal.tags.joinToString(", ")}")

        with(meal.nutrition) {
            println("Nutrition:")
            println("  Calories: $calories kcal")
            println("  Total Fat: $totalFat g")
            println("  Saturated Fat: $saturatedFat g")
            println("  Carbohydrates: $carbohydrates g")
            println("  Sugar: $sugar g")
            println("  Protein: $protein g")
            println("  Sodium: $sodium mg")
        }

        println("Steps:")
        meal.steps.forEachIndexed { index, step ->
            println("  ${index + 1}. $step")
        }

        println("Ingredients (${meal.nIngredients}): ${meal.ingredients.joinToString(", ")}")
    }


    private fun launchSuggestEggFreeSweet() {
        val suggestion = suggestEggFreeSweetUseCase.suggestEggFreeSweet()
        if (suggestion != null) {
            println("\n--- Suggested Free Sweet ---")
            println("Name: ${suggestion.name}")
            println("Description: ${suggestion.description}")
            println("---------------------------")
            println("Like it? (yes/no/exit)")
            when (readlnOrNull()?.lowercase()) {
                "yes" -> showSweetDetails(suggestion)
                "no" -> {
                    println("Disliked. Getting another suggestion.")
                    launchSuggestEggFreeSweet()
                }

                "exit" -> presentFeatures()
                else -> println("Invalid Input")
            }
        } else {
            println("No more egg-free sweets to suggest :( ")
        }
    }

    private fun showSweetDetails(meal: Meal) {
        println("\n--- Sweet Details ---")
        println("Name: ${meal.name}")
        println("Description: ${meal.description}")
        println("Ingredients: ${meal.ingredients.joinToString(", ")}")
        meal.steps.let {
            println("Steps:")
            it.forEachIndexed { index, step ->
                println("${index + 1}. $step")
            }
        }
    }

    private fun launchIdentifyIraqiMeals() {
        val iraqiMeals = identifyIraqiMealsUseCase.identifyIraqiMeals()
        if (iraqiMeals.isNotEmpty()) {
            println("\n--- Iraqi Meals ---")
            iraqiMeals.forEach { meal ->
                println("Name: ${meal.name}")
                println("ID: ${meal.id}")
                println("Description: ${meal.description ?: "No description available"}")
                println("Tags: ${meal.tags.joinToString(", ")}")
                println("Ingredients: ${meal.ingredients.joinToString(", ")}")
                println("---")
            }
            println("--- End of Iraqi Meals ---")
        } else {
            println("No Iraqi meals found.")
        }
    }

    private fun launchIngredientGameUseCase() {
        try {
            ingredientGame.run()
            while (ingredientGame.isRunning()) {
                println("Meal Name : ${ingredientGame.getCurrentMealName()}")
                println("Ingredients : ")
                ingredientGame.getCurrentIngredients()
                    .forEachIndexed { i, ingredient -> println("${i + 1}--> $ingredient") }
                print("Choose The Number Of Correct Ingredient : ")
                ingredientGame.submitUserAnswer(getUserInput() ?: return)
                println()
            }
            println(ingredientGame.getTurnResult())
        } catch (e: InvalidInputForIngredientGameException) {
            println(e.message)
        } catch (e: Exception) {
            println(e.message)
        }
    }

    private fun displayMeal(meal: Meal) {
        println(meal.name)
        println("    Preparation Time: ${meal.minutes} minutes")
        println("    Tags: ${meal.tags.joinToString(", ")}")
        println("    Nutrition:")
        println("      - Calories: ${meal.nutrition.calories}")
        println("      - Protein: ${meal.nutrition.protein}g")
        println("      - Total Fat: ${meal.nutrition.totalFat}g")
        println("      - Saturated Fat: ${meal.nutrition.saturatedFat}g")
        println("      - Carbohydrates: ${meal.nutrition.carbohydrates}g")
        println("      - Sugar: ${meal.nutrition.sugar}g")
        println("      - Sodium: ${meal.nutrition.sodium}mg")
        println("    Ingredients: ${meal.nIngredients}")
        println("    Steps: ${meal.nSteps}")
    }

    private fun launchGymHelper() {
        println("Please enter the number of calories:")
        val caloriesInput = readlnOrNull()?.toDoubleOrNull()
        println("Please enter the amount of protein:")
        val proteinInput = readlnOrNull()?.toDoubleOrNull()
        println("Enter calories tolerance if you want:")
        val caloriesToleranceInput = readlnOrNull()?.toIntOrNull()
        println("Enter protein tolerance if you want:")
        val proteinToleranceInput = readlnOrNull()?.toIntOrNull()

        if (caloriesInput == null || proteinInput == null) {
            println("Invalid input. Please enter numeric values.")
            return
        }

        val meals = gymHelperUseCase.getMealsByCaloriesAndProtein(
            calorieAndProteinValues = GymHelperInput(
                calories = caloriesInput,
                protein = proteinInput,
                caloriesAndProteinTolerance = CaloriesAndProteinTolerance(
                    caloriesToleranceInput ?: 30,
                    proteinToleranceInput ?: 10
                )
            ),
        )

        if (meals.isEmpty()) {
            println("No meals found matching meals.")
        } else {
            displayMeals(meals)
        }
    }

    private fun showWelcome() {
        println("Welcome to Food Change Mood app")
    }

    private fun showOptions() {
        println("\n\n=== Please enter one of the following numbers ===")
        println("1 - Find fast healthy meals that can be prepared in 15 minutes and under")
        println("2 - Search meals by name")
        println("3 - Identify Iraqi Meals")
        println("4 - Get easy food suggestion")
        println("5 - Guess preparation time game")
        println("6 - Suggest Egg FreeSweet")
        println("7 - Get friendly keto meal suggestion")
        println("8 - Search Foods by Add Date")
        println("9 - Use gym helper to search for meals by calories and proteins ")
        println("10 - Explore food culture by country")
        println("11 - Select the Ingredient Game")
        println("12 - Get names of 10 meals that contains potatoes in its ingredients")
        println("13 - Do you want a suggestion for a meal with more than 700 calories")
        println("14 - Get a list of all seafood meals")
        println("15 - Get Italian Meals For Large Group")
        print("Here: ")
    }

    private fun launchGuessPreparationTimeGame() {
        var isCorrect = false
        val randomMeal = guessPreparationTimeGameUseCase.getRandomMeal()
        val mealName = randomMeal.name
        val correctPreparationTime = randomMeal.minutes
        var guessPreparationTime: Int?
        var attempts = 3
        println("Guess the preparation time of $mealName meal")
        while (attempts-- > 0) {
            try {
                guessPreparationTime = getUserInput()
                if (correctPreparationTime > guessPreparationTime!!) {
                    println("too low, try again")
                } else if (correctPreparationTime < guessPreparationTime) {
                    println("too high, try again")
                } else {
                    println("Great Job!, it takes $guessPreparationTime minutes")
                    isCorrect = true
                    break
                }
            } catch (exception: Exception) {
                attempts++
                println("Please enter a valid input")
            }
        }
        if (!isCorrect) {
            println("The time it takes to prepare $mealName meal is $correctPreparationTime minutes")
        }
    }

    private fun removeAllSpaces(input: String): String {
        return input.replace(ALL_SPACES_VALUE, " ")
    }

    private fun launchGetItalianMealsForLargeGroup() {

        val meals = suggestItalianFoodForLargeGroupUseCase
            .suggestItalianMealsForLargeGroup()
        meals.forEachIndexed { index, meal ->
            println(
                "${index + 1}. Meal Name: " +
                        "${removeAllSpaces(meal.name)}\n"
            )
        }
    }

    private fun displayMeals(meals: List<Meal>) {
        meals.forEachIndexed { index, meal ->
            println("\n[${index + 1}] ${meal.name}")
            displayMeal(meal)
        }

        println("\nTotal meals found: ${meals.size}")
    }

    private companion object {
        val ALL_SPACES_VALUE = "\\s+".toRegex()
    }

    private fun launchRandomPotatoesMeals() {
        getMealsContainsPotatoUseCase.getMealsContainsPotato().forEach { println(it) }
    }

    private fun getUserInput(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }

    private fun getStringUserInput(): String? {
        return readlnOrNull()
    }
}