package dev.nutrilivre.model

data class AppSettings(
    val darkModeEnabled: Boolean = false,
    val notificationsEnabled: Boolean = true,
    val animationsEnabled: Boolean = true
)
