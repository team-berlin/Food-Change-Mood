package org.berlin

import dependencyInjection.appModule
import org.koin.core.context.startKoin


fun main() {

    startKoin{
        modules(appModule)
    }

}

