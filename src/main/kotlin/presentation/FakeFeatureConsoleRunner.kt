package org.berlin.presentation

import org.berlin.logic.ConsoleRunner

class FakeFeatureConsoleRunner:ConsoleRunner {
    override fun run() {
        println("its fake runner")
    }

}