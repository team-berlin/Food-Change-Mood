package org.berlin.logic.usecase

import org.berlin.model.Meal

interface EasyFoodSuggestionRepository {
    fun getEasyFoodSuggestion(): List<Meal>
}