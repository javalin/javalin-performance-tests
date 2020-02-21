package javalin.performance

import de.siegmar.fastcsv.reader.CsvReader
import de.siegmar.fastcsv.reader.CsvRow
import java.io.File
import java.nio.charset.StandardCharsets

object Benchmarks {

    fun run(args: Array<String>) {
        val mode = System.getProperty("mode")
        when (mode) {
            "benchmark" -> {
                val version = System.getProperty("version")
                runBenchmark(args)
            }
            "compareReport" -> {
                val baseline = System.getProperty("baseline")
                val target = System.getProperty("target")
                compare(baseline, target)
            }
        }
    }

    // run benchmark with version in property
    fun runBenchmark(args: Array<String>) {
        val version = System.getProperty("version")
        benchmark(args) {
            iterations = 20
            iterationTime = 10_000
            resultName = version
            setup()
        }

        benchmark(args) {
            profile("gc")
            iterations = 20
            iterationTime = 10_000
            resultName = "$version-gc"
            setup()
        }
    }

    // compare baseline version with targer version
    fun compare(baseline: String, target: String) {
        val baselineCsv = File("$baseline.csv")
        val targetCsv = File("$target.csv")
        val csvReader = CsvReader()
        val baselineRead = csvReader.read(baselineCsv, StandardCharsets.UTF_8)
        val targetRead = csvReader.read(targetCsv, StandardCharsets.UTF_8)
        for (row :CsvRow in baselineRead.getRows()) {
            System.out.println("baseline read line: " + row);
            System.out.println("First column of line: " + row.getField(0));
        }

        for (row :CsvRow in targetRead.getRows()) {
            System.out.println("target read line: " + row);
            System.out.println("First column of line: " + row.getField(0));
        }
    }

    private fun BenchmarkSettings.setup() {
        run<JavalinBenchmark>()
    }
}
