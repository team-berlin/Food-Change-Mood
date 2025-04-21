package logic.usecase.game

import org.berlin.logic.repository.MealsRepository
import org.berlin.model.Meal

class GuessPreparationTimeGameUseCase(
    private val mealsRepository: MealsRepository
) {
    fun getRandomMeal(): Meal {
        return mealsRepository.getAllMeals()
            .random()
        }
}