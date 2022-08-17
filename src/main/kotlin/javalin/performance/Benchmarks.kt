package javalin.performance

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
            if (!profileName.equals(""))
                profile(profileName)
            iterations = its
            iterationTime = itTime
            resultName =
                if (profileName.isBlank())
                    version
                else
                    "$version-$profileName"
            setup()
        }
    }

    private fun BenchmarkSettings.setup() {
        run<JavalinBenchmark>()
    }
}
