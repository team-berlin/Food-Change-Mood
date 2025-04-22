package org.berlin

import dependency_injection.appModule
import org.berlin.dependency_injection.useCaseModule
import org.berlin.presentation.MainMenuUI
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin
import dependency_injection.uiModule

fun main() {

    startKoin{
        modules(appModule, useCaseModule, uiModule)
    }

    val ui: MainMenuUI = getKoin().get()
    ui.run()
}

