package org.berlin.presentation.input_output

class ConsoleReader: Reader {
    override fun getUserInput(): String? {
        return readlnOrNull()
    }
}