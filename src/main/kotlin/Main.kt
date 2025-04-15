package org.berlin

import com.berlin.data.CsvMealsRepository
import dependency_injection.appModule
import org.berlin.logic.MealsRepository
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin


fun main() {

    startKoin{
        modules(appModule)
    }

    val mealsRepository: MealsRepository = getKoin().get()
    mealsRepository.getAllMeals().forEach() {
        print(it.id)
    }
}

