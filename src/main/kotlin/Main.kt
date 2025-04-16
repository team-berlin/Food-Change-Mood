package org.berlin

import dependencyInjection.appModule
import org.berlin.presentation.FoodChangeMoodUI
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin


fun main() {

    startKoin{
        modules(appModule)
    }

    val foodChangeMoodUI: FoodChangeMoodUI = getKoin().get()
    foodChangeMoodUI.start()
}

