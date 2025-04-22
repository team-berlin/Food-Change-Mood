package fake

import createMeals
import org.berlin.logic.repository.MealsRepository
import org.berlin.model.Meal

class FakeMealsRepository: MealsRepository {
    override fun getAllMeals(): List<Meal> {
        return listOf(
            createMeals(1, "Meal 1", 20, 3, 3),
            createMeals(2, "Meal 2", 25, 4, 4),
            createMeals(3, "Meal 3", 15, 2, 2),
            createMeals(4, "Meal 4", 30, 5, 5)
        )
    }

}