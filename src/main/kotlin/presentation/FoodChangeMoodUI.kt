package org.berlin.presentation

import org.berlin.logic.GetMealsContainsPotatoUseCase
import org.berlin.logic.usecase.*
import org.berlin.model.Meal


class FoodChangeMoodUI(
    private val identifyIraqiMealsUseCase: IdentifyIraqiMealsUseCase,
    private val suggestEggFreeSweetUseCase: SuggestEggFreeSweetUseCase,
    private val suggestKetoMealUseCase: SuggestKetoMealUseCase,
    private val easyFoodSuggestionRepository: EasyFoodSuggestionUseCase,
    private val exploreFoodCultureUseCase: ExploreFoodCultureUseCase,
    private val suggestItalianFoodForLargeGroupUseCase: SuggestItalianFoodForLargeGroupUseCase,
    private val searchMealsByNameUseCase: SearchMealsByNameUseCase,
    private val getMealsContainsPotatoUseCase: GetMealsContainsPotatoUseCase,
) {
    fun start() {
        showWelcome()
        presentFeatures()
    }

    private fun presentFeatures() {
        showOptions()
        val input = getStringUserInput()

        when (input) {
            "2" -> launchSearchMealsByName()
            "3" -> launchSuggestEggFreeSweet()
            "4" -> launchEasyFoodSuggestion()
            "5" -> launchIdentifyIraqiMeals()
            "7" -> launchSuggestionKetoMeal()
            "10" -> launchExploreFoodCulture()
            "12" -> launchRandomPotatoesMeals()
            "15" -> launchGetItalianMealsForLargeGroup()
            else -> println("Invalid Input")
        }

        presentFeatures()
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
            when(readLine()?.lowercase()){
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

    private fun showWelcome() {
        println("Welcome to cost of living app")
    }

    private fun showOptions() {
        println("\n\n=== Please enter one of the following numbers ===")
        println("7 - Get friendly keto meal suggestion")
        println("10 - Explore food culture by country")
        println("1 - Get fake UseCase for testing")
        println("2 - Search meals by name")
        println("3 - Suggest Egg FreeSweet")
        println("4 - Get easy food suggestion")
        println("15 - Get Italian Meals For Large Group")
        println("5 - Identify Iraqi Meals")

        println("12- Get names of 10 meals that contains potatoes in its ingredients")
        print("Here: ")
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
        return readLine()?.toIntOrNull()
    }

    private fun getStringUserInput(): String? {
        return readlnOrNull()
    }
}
