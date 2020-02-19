package javalin.performance

import io.javalin.Javalin;
import javalin.performance.*;

object Benchmarks{

    fun run(args: Array<String>){
        val version = System.getProperty("version");
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

    private fun BenchmarkSettings.setup() {
        run<JavalinBenchmark>()
    }
}
