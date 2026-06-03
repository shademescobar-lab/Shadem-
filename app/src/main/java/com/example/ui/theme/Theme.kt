package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.ui.FolkThemeColor

private val DefaultDarkColorScheme =
  darkColorScheme(
    primary = FolkRed,
    secondary = FolkYellow,
    tertiary = FolkGreen,
    background = FolkDarkBg,
    surface = FolkDarkSurface,
    surfaceVariant = FolkDarkSurfaceVariant,
    onPrimary = OnFolkPrimary,
    onBackground = OnFolkDarkSurface,
    onSurface = OnFolkDarkSurface,
  )

fun getDynamicColorScheme(theme: FolkThemeColor, isDark: Boolean = true): androidx.compose.material3.ColorScheme {
  val primaryColor = Color(android.graphics.Color.parseColor(theme.primaryHex))
  val secondaryColor = Color(android.graphics.Color.parseColor(theme.secondaryHex))
  val tertiaryColor = Color(android.graphics.Color.parseColor(theme.tertiaryHex))

  return if (isDark) {
    val backgroundColor = Color(android.graphics.Color.parseColor(theme.primaryBgHex))
    val surfaceColor = Color(android.graphics.Color.parseColor(theme.surfaceHex))
    darkColorScheme(
      primary = primaryColor,
      secondary = secondaryColor,
      tertiary = tertiaryColor,
      background = backgroundColor,
      surface = surfaceColor,
      surfaceVariant = surfaceColor,
      onPrimary = Color.White,
      onBackground = Color(0xFFECEFF1),
      onSurface = Color(0xFFECEFF1),
    )
  } else {
    // Vibrant light theme colors!
    val backgroundColor = Color(0xFFFAFAFA)
    val surfaceColor = Color(0xFFFFFFFF)
    lightColorScheme(
      primary = primaryColor,
      secondary = secondaryColor,
      tertiary = tertiaryColor,
      background = backgroundColor,
      surface = surfaceColor,
      surfaceVariant = Color(0xFFF2F4F7),
      onPrimary = Color.White,
      onBackground = Color(0xFF1C1B1F),
      onSurface = Color(0xFF1C1B1F),
    )
  }
}

@Composable
fun MyApplicationTheme(
  activeTheme: FolkThemeColor = FolkThemeColor.BOLIVIA,
  darkTheme: Boolean = true,
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme = getDynamicColorScheme(activeTheme, darkTheme)

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}

