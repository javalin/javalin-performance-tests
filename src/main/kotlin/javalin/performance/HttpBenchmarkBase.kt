package javalin.performance

import io.javalin.Javalin
import org.openjdk.jmh.annotations.*
import java.lang.IllegalStateException

@State(Scope.Benchmark)
abstract class HttpBenchmarkBase {
    private val httpClient = OkBenchmarkClient()

    private val port = 7000
    val origin = "http://localhost:$port"

    fun Javalin.attachEndpoints() {
        // Hello World
        this.get("/hello") { it.result("Hello World") }

        // lifecycle
        this.before("/lifecycle") { it.result("A") }
        this.get("/lifecycle") { it.result("B") }
        this.after("/lifecycle") { it.result("C") }

        // exceptions+error
        this.get("/exception") { throw IllegalStateException() }
        this.exception(Exception::class.java) { e, ctx -> ctx.status(500) }
        this.error(500) { it.result("Error") }

    }

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

    private fun load(path: String) {
        httpClient.load("${origin}${path}").use {
            val buf = ByteArray(8192)
            while (it.read(buf) != -1);
        }
    }

    @Benchmark
    fun hello() {
        load("/hello")
        load("/lifecycle")
        load("/exception")
    }
}

