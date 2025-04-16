package com.berlin.data

import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReader
import com.opencsv.CSVReaderBuilder
import com.opencsv.CSVWriter
import java.io.File
import java.io.FileReader

class CsvFileReader(
    private val csvFile: File
) {
    fun readLinesFromFile(): List<Array<String>> {
        val parser = CSVParserBuilder()
            .withSeparator(',')
            .withQuoteChar('"')
            .withEscapeChar(CSVWriter.NO_ESCAPE_CHARACTER)
            .build()

        CSVReaderBuilder(FileReader(csvFile))
            .withCSVParser(parser)
            .build().use { reader ->
                val allRows = reader.readAll()
                val dataRows = allRows.drop(1)
                return dataRows

            }
    }
}