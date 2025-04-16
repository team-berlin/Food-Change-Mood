package org.berlin

import dependencyInjection.appModule
import org.berlin.presentation.FoodChangeMoodUI
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin


fun main() {

    startKoin{
        modules(appModule)
    }
   val ui: FoodChangeMoodUI= getKoin().get()
    ui.start()
}

