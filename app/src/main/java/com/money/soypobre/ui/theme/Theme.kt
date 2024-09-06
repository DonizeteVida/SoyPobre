package com.money.soypobre.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color.Red,
    secondary = Color.Green,
    tertiary = Color.Blue
)

private val LightColorScheme = lightColorScheme(
    primary = MountainMeadow,
    onPrimary = Color.White,
    primaryContainer = Color.Red,
    onPrimaryContainer = Color.Red,
    inversePrimary = Color.Red,
    secondary = Color.Red,
    onSecondary = Color.Red,
    secondaryContainer = Color.Red,
    onSecondaryContainer = Color.Red,
    tertiary = Color.Red,
    onTertiary = Color.Red,
    tertiaryContainer = Color.Red,
    onTertiaryContainer = Color.Red,
    background = Color.White,
    onBackground = Color.Red,
    surface = Color.White,
    onSurface = MountainMeadow,
    surfaceVariant = Color.Red,
    onSurfaceVariant = Color.Gray, // TextField text color
    surfaceTint = Color.Red,
    inverseSurface = Color.Red,
    inverseOnSurface = Color.Red,
    error = Color.Red,
    onError = Color.Red,
    errorContainer = Color.Red,
    onErrorContainer = Color.Red,
    outline = Color.Red,
    outlineVariant = Color.Red,
    scrim = Color.Red,
    surfaceBright = Color.Red,
    surfaceContainer = Color.Red,
    surfaceContainerHigh = Color.Red,
    surfaceContainerHighest = Color.Transparent, // TextField background color
    surfaceContainerLow = Color.Red,
    surfaceContainerLowest = Color.Red,
    surfaceDim = Color.Red,
)

@Composable
fun SoyPobreTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}