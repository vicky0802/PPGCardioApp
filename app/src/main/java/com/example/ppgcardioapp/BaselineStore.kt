package com.example.ppgcardioapp

import android.content.Context
import android.content.SharedPreferences

data class Baseline(
    val hr: Float,
    val rmssd: Float,
    val sqi: Float,
    val count: Int
)

class BaselineStore(ctx: Context) {
    private val prefs: SharedPreferences = ctx.getSharedPreferences("baseline_store", Context.MODE_PRIVATE)

    fun load(): Baseline? {
        val hr = prefs.getFloat("hr", -1f)
        val rmssd = prefs.getFloat("rmssd", -1f)
        val sqi = prefs.getFloat("sqi", -1f)
        val count = prefs.getInt("count", 0)
        if (hr < 0f || rmssd < 0f || sqi < 0f || count <= 0) return null
        return Baseline(hr, rmssd, sqi, count)
    }

    fun update(hr: Float, rmssd: Float, sqi: Float) {
        val current = load()
        val n = (current?.count ?: 0) + 1
        val alpha = 0.2f
        val newHr = if (current == null) hr else (alpha * hr + (1 - alpha) * current.hr)
        val newRmssd = if (current == null) rmssd else (alpha * rmssd + (1 - alpha) * current.rmssd)
        val newSqi = if (current == null) sqi else (alpha * sqi + (1 - alpha) * current.sqi)
        prefs.edit()
            .putFloat("hr", newHr)
            .putFloat("rmssd", newRmssd)
            .putFloat("sqi", newSqi)
            .putInt("count", n)
            .apply()
    }
}
