package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ArohaStandardColorScheme = lightColorScheme(
    primary = PrimaryTeal,
    onPrimary = Color.White,
    primaryContainer = TealLight,
    onPrimaryContainer = PrimaryTealDark,
    secondary = SecondaryOcean,
    onSecondary = Color.White,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    background = CanvasDefault,
    onBackground = TextPrimary,
    surface = SurfaceCard,
    onSurface = TextPrimary,
    surfaceVariant = CanvasSubtle,
    onSurfaceVariant = TextSecondary,
    outline = CardBorder,
    outlineVariant = InputBorder,
    error = SosDanger,
    onError = Color.White,
    errorContainer = SosBg,
    onErrorContainer = SosBorder
)

private val ArohaHighContrastColorScheme = lightColorScheme(
    primary = PrimaryTealDark,
    onPrimary = Color.White,
    primaryContainer = TealLight,
    onPrimaryContainer = Color.Black,
    secondary = SecondaryOcean,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFBAE6FD),
    onSecondaryContainer = Color.Black,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = Color.Black,
    outline = HighContrastBorder,
    outlineVariant = HighContrastBorder,
    error = SosDanger,
    onError = Color.White,
    errorContainer = SosBg,
    onErrorContainer = Color.Black
)

@Composable
fun ArohaHealthTheme(
    isHighContrast: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (isHighContrast) ArohaHighContrastColorScheme else ArohaStandardColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = ArohaTypography,
        content = content
    )
}
