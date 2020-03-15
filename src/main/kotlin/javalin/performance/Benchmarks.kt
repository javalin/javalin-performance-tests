package javalin.performance

import de.vandermeer.asciitable.AsciiTable 
import de.siegmar.fastcsv.reader.CsvReader
import kotlin.math.round
import java.io.File
import java.nio.charset.StandardCharsets
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment

object Benchmarks {

    fun run(args: Array<String>) {
                runBenchmark(args)
    }

    // run benchmark with version in property
    fun runBenchmark(args: Array<String>) {
        val version = System.getProperty("version")
        val profileName = System.getProperty("profileName")
        val its = System.getProperty("iterations").toInt()
        val itTime = System.getProperty("iterationTime").toLong()

        benchmark(args) {
            if(!profileName.equals(""))
                profile(profileName)
            iterations = its 
            iterationTime = itTime 
            resultName = "$version-$profileName"
            setup()
        }
    }
    private fun BenchmarkSettings.setup() {
        run<JavalinBenchmark>()
    }
}
