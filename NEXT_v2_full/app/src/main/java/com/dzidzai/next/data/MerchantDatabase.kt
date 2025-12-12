
package com.dzidzai.next.data

import android.content.Context

class MerchantDatabase(private val context: Context) {

    private val merchantsZW = mapOf(
        "coffee" to listOf("Cafe Nescafe", "Kombi Coffee", "Four Brothers"),
        "breakfast" to listOf("Baker's Inn", "Chicken Inn", "Pariah State"),
        "lunch" to listOf("Nando's", "RocoMamas", "Chicken Slice"),
        "dinner" to listOf("Spur", "Ocean Basket", "Vanilla Moon"),
        "shopping" to listOf("OK Zimbabwe", "TM Pick n Pay", "Food Lovers Market"),
        "movie" to listOf("Ster-Kinekor Sam Levy's", "Netflix ZW"),
        "transport" to listOf("Hwindi Taxi", "VAYA Taxi", "ZUPCO Express")
    )

    private val merchantsZA = mapOf(
        "coffee" to listOf("Seattle Coffee Co", "Mugg & Bean"),
        "breakfast" to listOf("Spur Breakfast", "Wimpy"),
        "lunch" to listOf("Nando's SA", "Chicken Licken"),
        "dinner" to listOf("Ocean Basket", "RocoMamas"),
        "shopping" to listOf("Checkers", "Pick n Pay", "Shoprite"),
        "movie" to listOf("Ster-Kinekor", "Nu Metro Ghana"),
        "transport" to listOf("Uber SA", "Bolt SA")
    )

    fun getMerchantForNeed(need: String, region: String): String {
        val db = when (region) {
            "ZW" -> merchantsZW
            "ZA" -> merchantsZA
            else -> merchantsZW
        }
        return db[need]?.random() ?: "Local Store"
    }
}
