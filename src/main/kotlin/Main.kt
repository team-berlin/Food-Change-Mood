package org.berlin

import org.berlin.dependency_injection.appModule
import org.berlin.dependency_injection.useCaseModule
import org.berlin.presentation.FoodChangeMoodUI
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin


fun main() {

    startKoin{
        modules(appModule, useCaseModule)
    }

    val ui : FoodChangeMoodUI = getKoin().get()
    ui.start()

}

