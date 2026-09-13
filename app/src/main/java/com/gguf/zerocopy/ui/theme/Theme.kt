package com.gguf.zerocopy.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.gguf.zerocopy.data.local.SettingsManager

object ThemeState {
    var isDark by mutableStateOf(SettingsManager.isDarkTheme)
    var themeMode by mutableStateOf(SettingsManager.themeMode)
}

object ZcShape {
    val Xs = RoundedCornerShape(10.dp)
    val Sm = RoundedCornerShape(14.dp)
    val Md = RoundedCornerShape(18.dp)
    val Lg = RoundedCornerShape(24.dp)
    val Xl = RoundedCornerShape(30.dp)
    val Pill = RoundedCornerShape(50)
    val Circle = RoundedCornerShape(50)
}

object ZcSpace {
    val Xxs = 2.dp
    val Xs  = 4.dp
    val Sm  = 8.dp
    val Md  = 12.dp
    val Lg  = 16.dp
    val Xl  = 24.dp
    val Xxl = 32.dp
    val Xxxl = 48.dp
}

interface ZcPalette {
    val Bg: Color
    val Surface: Color
    val Card: Color
    val CardLight: Color
    val Border: Color
    val Accent: Color
    val Accent2: Color
    val Cyan: Color
    val Red: Color
    val Amber: Color
    val Purple: Color
    val Text: Color
    val Text2: Color
    val Text3: Color
    val UserBg: Color
    val ThinkBg: Color
    val GradientStart: Color
    val GradientEnd: Color
    val GlowAccent: Color
    val GlowAccent2: Color
}

@Composable
fun ZeroCopyTheme(
    darkTheme: Boolean = ThemeState.isDark,
    content: @Composable () -> Unit
) {
    SideEffect { ThemeState.isDark = darkTheme }
    val colorScheme = if (darkTheme) DarkScheme else LightScheme
    MaterialTheme(colorScheme = colorScheme, typography = ZcTypography, shapes = Shapes(
        extraSmall = ZcShape.Xs,
        small = ZcShape.Sm,
        medium = ZcShape.Md,
        large = ZcShape.Lg,
        extraLarge = ZcShape.Xl
    )) { content() }
}

private val DarkScheme = darkColorScheme(
    background = ZcColors.Bg,
    surface = ZcColors.Surface,
    surfaceVariant = ZcColors.Card,
    surfaceTint = ZcColors.Accent,
    primary = ZcColors.Accent,
    secondary = ZcColors.Accent2,
    tertiary = ZcColors.Purple,
    onBackground = ZcColors.Text,
    onSurface = ZcColors.Text,
    onSurfaceVariant = ZcColors.Text2,
    onPrimary = ZcColors.Bg,
    onSecondary = ZcColors.Bg,
    outline = ZcColors.Border,
    outlineVariant = ZcColors.Border.copy(alpha = 0.5f),
    error = ZcColors.Red,
    errorContainer = ZcColors.Red.copy(alpha = 0.16f),
    onError = ZcColors.Bg,
    onErrorContainer = ZcColors.Red,
)

private val LightScheme = lightColorScheme(
    background = ZcLightColors.Bg,
    surface = ZcLightColors.Surface,
    surfaceVariant = ZcLightColors.Card,
    surfaceTint = ZcLightColors.Accent,
    primary = ZcLightColors.Accent,
    secondary = ZcLightColors.Accent2,
    tertiary = ZcLightColors.Purple,
    onBackground = ZcLightColors.Text,
    onSurface = ZcLightColors.Text,
    onSurfaceVariant = ZcLightColors.Text2,
    onPrimary = ZcLightColors.Bg,
    onSecondary = ZcLightColors.Bg,
    outline = ZcLightColors.Border,
    outlineVariant = ZcLightColors.Border.copy(alpha = 0.5f),
    error = ZcLightColors.Red,
    errorContainer = ZcLightColors.Red.copy(alpha = 0.16f),
    onError = ZcLightColors.Bg,
    onErrorContainer = ZcLightColors.Red,
)

val ZcTypography = Typography(
    displayLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 42.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = (-1).sp, lineHeight = 48.sp),
    displayMedium = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 34.sp, fontWeight = FontWeight.Bold, letterSpacing = (-0.5).sp, lineHeight = 40.sp),
    displaySmall = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 27.sp, fontWeight = FontWeight.Bold, letterSpacing = (-0.25).sp, lineHeight = 33.sp),
    headlineLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 23.sp, fontWeight = FontWeight.Bold, letterSpacing = (-0.25).sp, lineHeight = 30.sp),
    headlineMedium = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 20.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 0.sp, lineHeight = 27.sp),
    headlineSmall = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 17.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 0.sp, lineHeight = 24.sp),
    titleLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 0.sp, lineHeight = 22.sp),
    titleMedium = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 0.1.sp, lineHeight = 20.sp),
    titleSmall = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 12.sp, fontWeight = FontWeight.Medium, letterSpacing = 0.2.sp, lineHeight = 17.sp),
    bodyLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 16.sp, lineHeight = 25.sp, fontWeight = FontWeight.Normal, letterSpacing = 0.2.sp),
    bodyMedium = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 14.sp, lineHeight = 22.sp, fontWeight = FontWeight.Normal, letterSpacing = 0.15.sp),
    bodySmall = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 12.sp, lineHeight = 19.sp, fontWeight = FontWeight.Normal, letterSpacing = 0.1.sp),
    labelLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 0.2.sp),
    labelMedium = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 12.sp, fontWeight = FontWeight.Medium, letterSpacing = 0.4.sp),
    labelSmall = TextStyle(fontFamily = FontFamily.SansSerif, fontSize = 10.sp, fontWeight = FontWeight.Medium, letterSpacing = 0.7.sp),
)

@Composable
fun currentPalette(): ZcPalette = if (ThemeState.isDark) ZcColors else ZcLightColors