
package com.dzidzai.next

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.dzidzai.next.ai.PredictionEngine
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var predictionCard: CardView
    private lateinit var predictionText: TextView
    private lateinit var merchantText: TextView
    private lateinit var confidenceText: TextView
    private lateinit var whisperText: TextView
    private lateinit var actionButton: Button
    private lateinit var timeText: TextView

    private lateinit var engine: PredictionEngine
    private var userTier: String = "Free"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        engine = PredictionEngine(this)

        predictionCard = findViewById(R.id.predictionCard)
        predictionText = findViewById(R.id.predictionText)
        merchantText = findViewById(R.id.merchantText)
        confidenceText = findViewById(R.id.confidenceText)
        whisperText = findViewById(R.id.whisperText)
        actionButton = findViewById(R.id.actionButton)
        timeText = findViewById(R.id.timeText)

        startLoop()
    }

    private fun startLoop() {
        CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                updatePrediction()
                updateTime()
                delay(8000)
            }
        }
    }

    private suspend fun updatePrediction() {
        val online = isOnline()
        val pred = engine.generatePrediction(
            timeOfDay = getTimeOfDay(),
            mood = getMood(),
            userTier = userTier,
            locationCode = "ZW",
            online = online
        )

        predictionText.text = "I predict: ${pred.desire}"
        merchantText.text = "Try: ${pred.merchant}"
        confidenceText.text = "Confidence: ${pred.confidence}%"
        whisperText.text = "Whisper Ads Score: ${pred.whisperScore}"
        actionButton.text = pred.action
    }

    private fun getTimeOfDay(): String {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when (hour) {
            in 5..11 -> "morning"
            in 12..16 -> "afternoon"
            else -> "evening"
        }
    }

    private fun getMood(): String {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when (hour) {
            in 5..11 -> "focused"
            in 12..16 -> "energetic"
            in 17..20 -> "calm"
            else -> "creative"
        }
    }

    private fun updateTime() {
        val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        timeText.text = "🕒 $time"
    }

    private fun isOnline(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo?.isConnected ?: false
    }
}
