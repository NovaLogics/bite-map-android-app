package novalogics.android.bitemap.app.ui.theme

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = OrangeBlazeColors.Dark.primary,
    onPrimary = OrangeBlazeColors.Dark.onPrimary,
    primaryContainer = OrangeBlazeColors.Dark.primaryContainer,
    onPrimaryContainer = OrangeBlazeColors.Dark.onPrimaryContainer,
    secondary = OrangeBlazeColors.Dark.secondary,
    onSecondary = OrangeBlazeColors.Dark.onSecondary,
    secondaryContainer = OrangeBlazeColors.Dark.secondaryContainer,
    onSecondaryContainer = OrangeBlazeColors.Dark.onSecondaryContainer,
    tertiary = OrangeBlazeColors.Dark.tertiary,
    onTertiary = OrangeBlazeColors.Dark.onTertiary,
    tertiaryContainer = OrangeBlazeColors.Dark.tertiaryContainer,
    onTertiaryContainer = OrangeBlazeColors.Dark.onTertiaryContainer,
    error = OrangeBlazeColors.Dark.error,
    errorContainer = OrangeBlazeColors.Dark.errorContainer,
    onError = OrangeBlazeColors.Dark.onError,
    onErrorContainer = OrangeBlazeColors.Dark.onErrorContainer,
    background = OrangeBlazeColors.Dark.background,
    onBackground = OrangeBlazeColors.Dark.onBackground,
    surface = OrangeBlazeColors.Dark.surface,
    onSurface = OrangeBlazeColors.Dark.onSurface,
    surfaceVariant = OrangeBlazeColors.Dark.surfaceVariant,
    onSurfaceVariant = OrangeBlazeColors.Dark.onSurfaceVariant,
    outline = OrangeBlazeColors.Dark.outline,
    inverseOnSurface = OrangeBlazeColors.Dark.inverseOnSurface,
    inverseSurface = OrangeBlazeColors.Dark.inverseSurface,
    inversePrimary = OrangeBlazeColors.Dark.inversePrimary,
    surfaceTint = OrangeBlazeColors.Dark.surfaceTint,
    outlineVariant = OrangeBlazeColors.Dark.outlineVariant,
    scrim = OrangeBlazeColors.Dark.scrim
)

private val LightColorScheme = lightColorScheme(
    primary = OrangeBlazeColors.Light.primary,
    onPrimary = OrangeBlazeColors.Light.onPrimary,
    primaryContainer = OrangeBlazeColors.Light.primaryContainer,
    onPrimaryContainer = OrangeBlazeColors.Light.onPrimaryContainer,
    secondary = OrangeBlazeColors.Light.secondary,
    onSecondary = OrangeBlazeColors.Light.onSecondary,
    secondaryContainer = OrangeBlazeColors.Light.secondaryContainer,
    onSecondaryContainer = OrangeBlazeColors.Light.onSecondaryContainer,
    tertiary = OrangeBlazeColors.Light.tertiary,
    onTertiary = OrangeBlazeColors.Light.onTertiary,
    tertiaryContainer = OrangeBlazeColors.Light.tertiaryContainer,
    onTertiaryContainer = OrangeBlazeColors.Light.onTertiaryContainer,
    error = OrangeBlazeColors.Light.error,
    errorContainer = OrangeBlazeColors.Light.errorContainer,
    onError = OrangeBlazeColors.Light.onError,
    onErrorContainer = OrangeBlazeColors.Light.onErrorContainer,
    background = OrangeBlazeColors.Light.background,
    onBackground = OrangeBlazeColors.Light.onBackground,
    surface = OrangeBlazeColors.Light.surface,
    onSurface = OrangeBlazeColors.Light.onSurface,
    surfaceVariant = OrangeBlazeColors.Light.surfaceVariant,
    onSurfaceVariant = OrangeBlazeColors.Light.onSurfaceVariant,
    outline = OrangeBlazeColors.Light.outline,
    inverseOnSurface = OrangeBlazeColors.Light.inverseOnSurface,
    inverseSurface = OrangeBlazeColors.Light.inverseSurface,
    inversePrimary = OrangeBlazeColors.Light.inversePrimary,
    surfaceTint = OrangeBlazeColors.Light.surfaceTint,
    outlineVariant = OrangeBlazeColors.Light.outlineVariant,
    scrim = OrangeBlazeColors.Light.scrim
)

@Composable
fun BiteMapTheme(
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

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            setUpEdgeToEdge(view, darkTheme)
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = Shapes,
        typography = Typography,
        content = content
    )
}


/**
 * Sets up edge-to-edge for the window of this [view]. The system icon colors are set to either
 * light or dark depending on whether the [isDarkTheme] is enabled or not.
 */
private fun setUpEdgeToEdge(view: View, isDarkTheme: Boolean) {
    val window = (view.context as Activity).window
    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.statusBarColor = Color.Transparent.toArgb()
    val navigationBarColor = when {
        Build.VERSION.SDK_INT >= 29 -> Color.Transparent.toArgb()
        Build.VERSION.SDK_INT >= 26 -> Color(0xFF, 0xFF, 0xFF, 0x63).toArgb()
        // Min sdk version for this app is 24, this block is for SDK versions 24 and 25
        else -> Color(0x00, 0x00, 0x00, 0x50).toArgb()
    }
    window.navigationBarColor = navigationBarColor
    val controller = WindowCompat.getInsetsController(window, view)
    controller.isAppearanceLightStatusBars = !isDarkTheme
    controller.isAppearanceLightNavigationBars = !isDarkTheme
}
