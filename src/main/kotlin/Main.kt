package org.berlin

import dependency_injection.appModule
import org.berlin.dependency_injection.useCaseModule
import org.berlin.presentation.FoodChangeMoodUI
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin


fun main() {

    startKoin{
        modules(appModule, useCaseModule)
    }


    val foodUI:FoodChangeMoodUI= getKoin().get()
    foodUI.start()
}

