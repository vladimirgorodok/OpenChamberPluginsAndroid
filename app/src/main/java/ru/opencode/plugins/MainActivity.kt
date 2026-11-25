package ru.opencode.plugins

import android.app.Activity
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import org.json.JSONObject
import java.util.concurrent.Executors

/**
 * MainActivity — каталог плагинов и дополнений OpenChamber (Android WebView).
 *
 * Web-интерфейс (app/src/main/assets/index.html) обращается через
 * window.Android.run(cmd) — выполняет команду оболочки и возвращает
 * вывод (stdout+stderr, exit code дописывается в конец, если не ноль).
 */
class MainActivity : Activity() {

    private lateinit var webView: WebView
    private val executor = Executors.newFixedThreadPool(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        webView = WebView(this)
        setContentView(webView)

        webView.setBackgroundColor(android.graphics.Color.BLACK)
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            allowFileAccess = true
            cacheMode = WebSettings.LOAD_NO_CACHE
            userAgentString = "$userAgentString OpenChamberPlugins/1.0"
        }
        webView.addJavascriptInterface(this, "Android")
        webView.webViewClient = WebViewClient()
        webView.loadUrl("file:///android_asset/index.html")
    }

    override fun onDestroy() {
        executor.shutdownNow()
        super.onDestroy()
    }

    /** Синхронное выполнение команды оболочки. */
    @JavascriptInterface
    fun run(command: String): String {
        return try {
            val process = Runtime.getRuntime().exec(arrayOf("sh", "-c", command))
            val output = process.inputStream.bufferedReader().use { it.readText() }
            val error = process.errorStream.bufferedReader().use { it.readText() }
            val exit = process.waitFor()
            val parts = mutableListOf<String>()
            if (output.isNotEmpty()) parts.add(output.trimEnd())
            if (error.isNotEmpty()) parts.add("[stderr]\n" + error.trimEnd())
            if (exit != 0) parts.add("[exit=$exit]")
            parts.joinToString("\n")
        } catch (e: Exception) {
            "Ошибка выполнения: ${e.message}"
        }
    }

    /** Асинхронное выполнение: результат приходит на window.onCommandResult(json). */
    @JavascriptInterface
    fun runAsync(command: String) {
        executor.execute {
            val result = JSONObject()
                .put("command", command)
                .put("output", run(command))
            callJs("onCommandResult", result.toString())
        }
    }

    private fun callJs(functionName: String, payload: String) {
        val quoted = JSONObject.quote(payload)
        runOnUiThread { webView.evaluateJavascript("window.$functionName($quoted)", null) }
    }
}
