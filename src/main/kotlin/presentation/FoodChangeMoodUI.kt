package org.berlin.presentation

import kotlinx.datetime.LocalDate
import org.berlin.logic.SearchMealsByDateUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FoodChangeMoodUI : KoinComponent {

    private val searchMealsByDateUseCase: SearchMealsByDateUseCase by inject()
import logic.usecase.GetSeafoodMealsUseCase
import org.berlin.logic.InvalidInputForIngredientGameException
import org.berlin.logic.usecase.GetMealsContainsPotatoUseCase
import org.berlin.logic.usecase.GuessPreparationTimeGameUseCase
import org.berlin.logic.usecase.*
import org.berlin.model.Meal

class FoodChangeMoodUI(
    private val ingredientGame: IngredientGameInteractor,
    private val identifyIraqiMealsUseCase : IdentifyIraqiMealsUseCase,
    private val suggestEggFreeSweetUseCase: SuggestEggFreeSweetUseCase,
    private val suggestKetoMealUseCase: SuggestKetoMealUseCase,
    private val easyFoodSuggestionRepository: EasyFoodSuggestionUseCase,
    private val exploreFoodCultureUseCase: ExploreFoodCultureUseCase,
    private val suggestItalianFoodForLargeGroupUseCase: SuggestItalianFoodForLargeGroupUseCase,
    private val searchMealsByNameUseCase: SearchMealsByNameUseCase,
    private val getMealsContainsPotatoUseCase: GetMealsContainsPotatoUseCase,
    private val guessPreparationTimeGameUseCase: GuessPreparationTimeGameUseCase,
    private val quickHealthyMealsUseCase: QuickHealthyMealsUseCase,
    private val highCalorieMealsUseCase: HighCalorieMealsUseCase,
    private val getSeafoodMealsUseCase: GetSeafoodMealsUseCase
) {
    fun start() {
        showWelcome()
        presentFeatures()
    }

    private fun presentFeatures() {
        showOptions()
        val input = getStringUserInput()

        when (input) {
            1 -> printFakeUseCase()
            2 -> searchMealsByDate()
            "1" -> launchQuickHealthyMeals()
            "2" -> launchSearchMealsByName()
            "3" -> launchIdentifyIraqiMeals()
            "4" -> launchEasyFoodSuggestion()
            "5" -> launchGuessPreparationTimeGame()
            "6" -> launchSuggestEggFreeSweet()
            "7" -> launchSuggestionKetoMeal()
            "10" -> launchExploreFoodCulture()
            "11"-> launchIngredientGameUseCase()
            "12" -> launchRandomPotatoesMeals()
            "13" -> launchHighCalorieMeal()
            "14" -> launchSeafoodMealsUseCase()
            "15" -> launchGetItalianMealsForLargeGroup()
            else -> println("Invalid Input")
        }

        presentFeatures()
    }

    private fun searchMealsByDate() {
        println("\n=== Search Meals by Date ===")
        println("Please enter a date in the format YYYY-MM-DD:")
        val dateInput = readLine()

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
                val mealIdInput = readLine()

                if (mealIdInput?.lowercase() != "no") {
                    try {
                        val mealId = mealIdInput?.toInt()
                        val selectedMeal = meals.find { it.id == mealId }

                        if (selectedMeal != null) {
                            displayMealDetails(selectedMeal)
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

    private fun displayMealDetails(meal: org.berlin.model.Meal) {
        println("\n=== Meal Details ===")
        println("Name: ${meal.name}")
        println("ID: ${meal.id}")
        println("Preparation Time: ${meal.minutes} minutes")
        println("Submission Date: ${meal.submissionDate}")
        println("Tags: ${meal.tags.joinToString(", ")}")
        println("Number of Steps: ${meal.nSteps}")
        println("Number of Ingredients: ${meal.nIngredients}")

        println("\nNutrition Information:")
        println("Calories: ${meal.nutrition.calories}")
        println("Total Fat: ${meal.nutrition.totalFat}g")
        println("Sugar: ${meal.nutrition.sugar}g")
        println("Sodium: ${meal.nutrition.sodium}mg")
        println("Protein: ${meal.nutrition.protein}g")
        println("Saturated Fat: ${meal.nutrition.saturatedFat}g")
        println("Carbohydrates: ${meal.nutrition.carbohydrates}g")

        if (!meal.description.isNullOrBlank()) {
            println("\nDescription:")
            println(meal.description)
        }

        println("\nIngredients:")
        meal.ingredients.forEachIndexed { index, ingredient ->
            println("${index + 1}. $ingredient")
        }

        println("\nSteps:")
        meal.steps.forEachIndexed { index, step ->
            println("${index + 1}. $step")
        }
    }

    private fun printFakeUseCase() {
        println("UseCase successfully done...!")
    private fun launchSeafoodMealsUseCase() {
        getSeafoodMealsUseCase.getSeafoodMeals().forEachIndexed { index, seafoodMeal ->
            println("${index + 1} ,Name:${seafoodMeal.name}, has protein value:${seafoodMeal.protein}")
        }
    }

    private fun launchHighCalorieMeal() {
        println("\n=== High Calorie Meal Suggestion ===")

        highCalorieMealsUseCase.suggestHighCalorieMeal()
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

    private fun launchSearchMealsByName() {
        println("===Search meals by name===\n")
        println("please enter meal name or part of it: ")
        val searchWord = readlnOrNull() ?: ""
        val meals = searchMealsByNameUseCase.searchMealsByName(searchWord)
        if (meals.isEmpty()) {
            println("Search word is empty, please enter something to search.")
        } else {
            println("there is ${meals.size} founded for $searchWord: \n")
            meals.forEach { meal ->
                println(meal.name)
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

        meals.forEachIndexed { index, meal ->
            println("\n[${index + 1}] ${meal.name}")
            displayMeal(meal)
        }

        println("\nTotal meals found: ${meals.size}")
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
        println("\n--- Sweet Details ---")
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
            when(readlnOrNull()?.lowercase()){
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
                ingredientGame.submitAnswer(getUserInput() ?: return)
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

    private fun showWelcome() {
        println("Welcome to Food Change Mood app")
    }

    private fun showOptions() {
        println("\n\n=== Please enter one of the following numbers ===")
        println("1 - Get fake UseCase for testing")
        println("2 - Search Foods by Add Date")
        println("1 - Find fast healthy meals that can be prepared in 15 minutes and under")
        println("2 - Search meals by name")
        println("3 - Identify Iraqi Meals")
        println("4 - Get easy food suggestion")
        println("5 - Guess preparation time game")
        println("6 - Suggest Egg FreeSweet")
        println("7 - Get friendly keto meal suggestion")
        println("10 - Explore food culture by country")
        println("11 - Ingredient Game")
        println("12- Get names of 10 meals that contains potatoes in its ingredients")
        println("13 - Do you want a suggestion for a meal with more than 700 calories")
        println("14 - Get a list of all seafood meals")
        println("15 - Get Italian Meals For Large Group")
        print("Here: ")
    }

    private fun launchGuessPreparationTimeGame(){
        var isCorrect = false
        val meal = guessPreparationTimeGameUseCase.guessPreparationTime()
        val minutes = meal.minutes
        val mealName = meal.name
        println("Guess the preparation time of $mealName meal")
        var preparationTime: Int?
        var attempts = 3
        while (attempts-- > 0){
            try {
                preparationTime = getUserInput()
                if (minutes > preparationTime!!) {
                    println("too low, try again")
                } else if (minutes < preparationTime) {
                    println("too high, try again")
                } else {
                    println("Great Job!, it takes $preparationTime minutes")
                    isCorrect = true
                    break
                }
            }catch (exception: Exception){
                println("Please enter a valid input")
            }
        }
        if (!isCorrect){
            println("The time it takes to prepare $mealName meal is $minutes minutes")
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

    private companion object {
        val ALL_SPACES_VALUE = "\\s+".toRegex()
    }

    private fun launchRandomPotatoesMeals(){
        getMealsContainsPotatoUseCase.getMealsContainsPotato().forEach { println(it) }
    }

    private fun getUserInput(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }

    private fun getStringUserInput(): String? {
        return readlnOrNull()
    }
}