package org.berlin.model

import kotlinx.datetime.LocalDate

data class Meal(
    val name: String,
    val id: Int,
    val minutes: Int,
    val contributorId: Int,
    val submissionDate: LocalDate,
    val tags: List<String>,
    val nutrition: Nutrition,
    val numberOfSteps: Int,
    val steps: List<String>,
    val description: String?,
    val ingredients: List<String>,
    val numberOfIngredients: Int
)


