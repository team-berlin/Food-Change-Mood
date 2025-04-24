package org.berlin.logic.usecase.helper


class EmptyMealsException(message: String="list of meals is empty"):Exception(message)
class EmptyIngredientsException(message: String="there is invalid meal ingredients"):Exception(message)
class CantFindWrongIngredientException(message: String="failed to find two wrong ingredients after 500 attempts"):Exception(message)

