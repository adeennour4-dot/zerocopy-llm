package com.gguf.zerocopy.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * ZeroCopy "Void" palette — true-AMOLED, minimal. Pure black background and
 * surface (no elevation jump; separation comes from a single subtle hairline
 * border, not a lighter fill) for genuine OLED black + a calmer, less busy
 * feel. Still just one accent (violet) for actions, green for success only,
 * cyan for AI/tech indicators. No reds/ambers/yellows by design — destructive
 * state uses a hot-magenta instead so warnings never fight for the same
 * attention as errors.
 *
 * Icon purple #8A70FF · icon green #2BE4A4 · sky cyan #46D6FF
 */
object ZcColors : ZcPalette {
  override val Bg = Color(0xFF000000)
  override val Surface = Color(0xFF000000)
  override val Card = Color(0xFF0B0B0D)
  override val CardLight = Color(0xFF141417)
  override val Border = Color(0xFF201F26)
  override val Accent = Color(0xFF7C5CFF)      // violet — PRIMARY ACCENT
  override val Accent2 = Color(0xFF2BE4A4)     // spring green — SUCCESS ONLY
  override val Cyan = Color(0xFF40CFFF)
  override val Red = Color(0xFFEB4FA8)         // hot magenta — DESTRUCTIVE/ERROR
  override val Amber = Color(0xFF40CFFF)       // cyan — ADDITIVE ACCENT
  override val Purple = Color(0xFFA78BFA)
  override val Text = Color(0xFFF5F5F8)
  override val Text2 = Color(0xFF97979F)
  override val Text3 = Color(0xFF505057)
  override val UserBg = Color(0xFF121016)
  override val ThinkBg = Color(0xFF0C0C0F)
  override val GradientStart = Color(0xFF40CFFF)   // sky → violet brand gradient
  override val GradientEnd = Color(0xFF7C5CFF)
  override val GlowAccent = Color(0x407C5CFF)
  override val GlowAccent2 = Color(0x402BE4A4)
}

object ZcLightColors : ZcPalette {
  override val Bg = Color(0xFFF7F8FC)
  override val Surface = Color(0xFFFFFFFF)
  override val Card = Color(0xFFFFFFFF)
  override val CardLight = Color(0xFFEEF0F8)
  override val Border = Color(0xFFE1E4F0)
  override val Accent = Color(0xFF6A50E8)
  override val Accent2 = Color(0xFF00B384)
  override val Cyan = Color(0xFF0899C8)
  override val Red = Color(0xFFB23BD9)
  override val Amber = Color(0xFF0899C8)
  override val Purple = Color(0xFF8B3FE8)
  override val Text = Color(0xFF15171F)
  override val Text2 = Color(0xFF555C74)
  override val Text3 = Color(0xFF8A90A6)
  override val UserBg = Color(0xFFEBE8FD)
  override val ThinkBg = Color(0xFFF0F1F7)
  override val GradientStart = Color(0xFF0899C8)
  override val GradientEnd = Color(0xFF6A50E8)
  override val GlowAccent = Color(0x356A50E8)
  override val GlowAccent2 = Color(0x3500B384)
}

/** Semantic color aliases — use these in components, NOT raw palette values. */
object ZcLightSemantic : ZcSemanticColors {
  override val Primary = ZcLightColors.Accent
  override val PrimaryContainer = ZcLightColors.Accent.copy(alpha = 0.14f)
  override val OnPrimary = ZcLightColors.Bg

  override val Success = ZcLightColors.Accent2
  override val SuccessContainer = ZcLightColors.Accent2.copy(alpha = 0.14f)
  override val OnSuccess = ZcLightColors.Bg

  override val Error = ZcLightColors.Red
  override val ErrorContainer = ZcLightColors.Red.copy(alpha = 0.14f)
  override val OnError = ZcLightColors.Bg

  override val AiAccent = ZcLightColors.Cyan
  override val AiAccentContainer = ZcLightColors.Cyan.copy(alpha = 0.14f)

  override val Surface = ZcLightColors.Surface
  override val SurfaceVariant = ZcLightColors.Card
  override val SurfaceBright = ZcLightColors.CardLight
  override val Border = ZcLightColors.Border
  override val BorderSubtle = ZcLightColors.Border.copy(alpha = 0.5f)

  override val OnSurface = ZcLightColors.Text
  override val OnSurfaceVariant = ZcLightColors.Text2
  override val OnSurfaceMuted = ZcLightColors.Text3

  override val UserBubble = ZcLightColors.UserBg
  override val ThinkBubble = ZcLightColors.ThinkBg

  override val GradientStart = ZcLightColors.GradientStart
  override val GradientEnd = ZcLightColors.GradientEnd
}

/**
 * Single brand gradient for the whole app (sky → violet), derived from the
 * active palette so dark and light themes stay coherent. Use for ACTIVE states
 * only (generating, loading, focus) — no glow at rest.
 */
fun ZcPalette.gradient(vertical: Boolean = false): Brush =
  if (vertical) Brush.verticalGradient(listOf(GradientStart, GradientEnd))
  else Brush.linearGradient(listOf(GradientStart, GradientEnd))


object ZcSemantic : ZcSemanticColors {
  // Primary actions, selection, focus — THE ONE ACCENT (violet)
  override val Primary = ZcColors.Accent
  override val PrimaryContainer = ZcColors.Accent.copy(alpha = 0.14f)
  override val OnPrimary = ZcColors.Bg

  // Success states only — green
  override val Success = ZcColors.Accent2
  override val SuccessContainer = ZcColors.Accent2.copy(alpha = 0.14f)
  override val OnSuccess = ZcColors.Bg

  // Destructive / error — hot violet
  override val Error = ZcColors.Red
  override val ErrorContainer = ZcColors.Red.copy(alpha = 0.14f)
  override val OnError = ZcColors.Bg

  // AI/tech indicator — sky cyan
  override val AiAccent = ZcColors.Cyan
  override val AiAccentContainer = ZcColors.Cyan.copy(alpha = 0.14f)

  // Surfaces
  override val Surface = ZcColors.Surface
  override val SurfaceVariant = ZcColors.Card
  override val SurfaceBright = ZcColors.CardLight
  override val Border = ZcColors.Border
  override val BorderSubtle = ZcColors.Border.copy(alpha = 0.5f)

  // Text hierarchy
  override val OnSurface = ZcColors.Text
  override val OnSurfaceVariant = ZcColors.Text2
  override val OnSurfaceMuted = ZcColors.Text3

  // Chat-specific
  override val UserBubble = ZcColors.UserBg
  override val ThinkBubble = ZcColors.ThinkBg

  // Brand gradient (cyan → violet) — active states only
  override val GradientStart = ZcColors.GradientStart
  override val GradientEnd = ZcColors.GradientEnd
}
/** Semantic colors resolved for the current theme (dark or light). */
@Composable
fun currentSemantic(): ZcSemanticColors =
  if (ThemeState.isDark) ZcSemantic else ZcLightSemantic

interface ZcSemanticColors {
  // Primary actions, selection, focus — THE ONE ACCENT (violet)
  val Primary: Color
  val PrimaryContainer: Color
  val OnPrimary: Color

  // Success states only — green
  val Success: Color
  val SuccessContainer: Color
  val OnSuccess: Color

  // Destructive / error — hot violet
  val Error: Color
  val ErrorContainer: Color
  val OnError: Color

  // AI/tech indicator — sky cyan
  val AiAccent: Color
  val AiAccentContainer: Color

  // Surfaces
  val Surface: Color
  val SurfaceVariant: Color
  val SurfaceBright: Color
  val Border: Color
  val BorderSubtle: Color

  // Text hierarchy
  val OnSurface: Color
  val OnSurfaceVariant: Color
  val OnSurfaceMuted: Color

  // Chat-specific
  val UserBubble: Color
  val ThinkBubble: Color

  // Brand gradient (cyan → violet) — active states only
  val GradientStart: Color
  val GradientEnd: Color
}
