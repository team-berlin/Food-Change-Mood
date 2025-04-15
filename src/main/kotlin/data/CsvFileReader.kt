package com.berlin.data

import com.opencsv.CSVReader
import java.io.File
import java.io.FileReader

class CsvFileReader(
    private val csvFile: File
) {
    fun readLinesFromFile(): List<Array<String>> {
        CSVReader(FileReader(csvFile)).use { reader ->
            val allRows = reader.readAll()
            val dataRows = allRows.drop(1)
            return dataRows
        }
    }
}