package org.berlin

import org.berlin.dependency_injection.appModule
import org.koin.core.context.startKoin


fun main() {

    startKoin{
        modules(appModule)
    }

}

