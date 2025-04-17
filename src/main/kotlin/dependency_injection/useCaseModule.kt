package org.berlin.dependency_injection
import org.berlin.logic.ExploreFoodCultureUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { ExploreFoodCultureUseCase(get()) }
}

