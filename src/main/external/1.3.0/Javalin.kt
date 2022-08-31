package javalin.performance

import io.javalin.Javalin;

fun main(args: Array<String>) {
    Benchmarks.run(args)
}

open class JavalinBenchmark : HttpBenchmarkBase() {
    var app: Javalin? = null
    override fun startServer(port: Int) {
        app = Javalin.start(port)
        app!!.attachEndpoints()
    }

    override fun stopServer() {
        app!!.stop()
    }
}
