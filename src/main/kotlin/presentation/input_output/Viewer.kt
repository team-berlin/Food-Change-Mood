package org.berlin.presentation.input_output

import org.berlin.model.Meal

interface Viewer {
    fun display(message: String)
}