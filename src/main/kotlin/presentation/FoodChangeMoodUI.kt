package org.berlin.presentation

import org.berlin.logic.usecases.SearchMealsByNameUseCase


class FoodChangeMoodUI(
    private val searchMealsByNameUseCase: SearchMealsByNameUseCase
) {
    fun start() {
        showWelcome()
        presentFeatures()
    }

    private fun presentFeatures() {
        showOptions()
        val input = getUserInput()

        when (input) {
            1 -> printFakeUseCase()
            2 -> searchMealsByName()
            else -> println("Invalid Input")
        }

        presentFeatures()
    }

    private fun searchMealsByName() {
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

    private fun printFakeUseCase() {
        println("UseCase successfully done...!")
    }

    private fun showWelcome() {
        println("Welcome to cost of living app")
    }

    private fun showOptions() {
        println("\n\n=== Please enter one of the following numbers ===")
        println("1 - Get fake UseCase for testing")
        println("2 - Search meals by name")
        print("Here: ")
    }

    private fun getUserInput(): Int? {
        return readlnOrNull()?.toIntOrNull()
    }
}
