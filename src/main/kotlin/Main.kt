package org.berlin

import dependencyInjection.appModule
import org.berlin.presentation.FoodChangeMoodUI
import org.koin.core.context.startKoin

fun main() {
    startKoin {
        modules(appModule)
    }

    val ui = FoodChangeMoodUI()
    ui.start()
}