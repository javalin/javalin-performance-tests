package javalin.performance

import ch.qos.logback.classic.Level
import org.openjdk.jmh.annotations.*
import org.slf4j.*
import org.slf4j.Logger

@State(Scope.Benchmark)
abstract class HttpBenchmarkBase {
    private val httpClient = OkBenchmarkClient()

    private val port = 7000
    public val helloPath = "/hello"
    public val helloResult = "Hello World"

    abstract fun startServer(port: Int)
    abstract fun stopServer()

    @Setup
    fun configureServer() {
        startServer(port)
    }

    @TearDown
    fun shutdownServer() {
        stopServer()
    }

    @Setup
    fun configureClient() {
        httpClient.setup()
    }

    @TearDown
    fun shutdownClient() {
        httpClient.shutdown()
    }

    private fun load(url: String) {
        httpClient.load(url).use {
            val buf = ByteArray(8192)
            while (it.read(buf) != -1);
        }
    }

    @Benchmark
    fun hello() {
        load("http://localhost:$port/hello")
    }
}

