package org.berlin.logic

class InvalidInputForIngredientGameException(
    message: String = "Invalid Input Ingredient Only 3"
) : RuntimeException(message)
