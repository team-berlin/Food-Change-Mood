package org.berlin.dependencyInjection

import org.berlin.logic.ExploreFoodCultureUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { ExploreFoodCultureUseCase(get()) }
}

