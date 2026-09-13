package com.gguf.zerocopy.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * ZeroCopy "Aurora Ember" palette — deep midnight navy surfaces with a warm
 * sunrise accent. Deliberately NOT pure black: surfaces are ink-blue with a
 * subtle elevation lift (Card > Surface > Bg), so layers read through tone,
 * not just hairlines. Warm coral is the single interactive accent; amber is
 * the additive/secondary accent; cyan is reserved for AI/tech indicators;
 * emerald for success; rose for destructive/error — no semantic overlap.
 *
 * Sunrise coral #FF7A59 · amber #FFC24B · AI cyan #38C6FF
 */
object ZcColors : ZcPalette {
  override val Bg = Color(0xFF0A0E18)
  override val Surface = Color(0xFF0D1220)
  override val Card = Color(0xFF141B2E)
  override val CardLight = Color(0xFF1D2740)
  override val Border = Color(0xFF273350)
  override val Accent = Color(0xFFFF7A59)      // coral — PRIMARY ACCENT
  override val Accent2 = Color(0xFF34D399)     // emerald — SUCCESS ONLY
  override val Cyan = Color(0xFF38C6FF)        // AI / tech indicator
  override val Red = Color(0xFFFF4D6D)         // rose — DESTRUCTIVE/ERROR
  override val Amber = Color(0xFFFFC24B)       // additive accent
  override val Purple = Color(0xFF9D8CFF)
  override val Text = Color(0xFFF2F4FA)
  override val Text2 = Color(0xFF9AA6C3)
  override val Text3 = Color(0xFF5A6688)
  override val UserBg = Color(0xFF221812)      // warm charcoal for user bubbles
  override val ThinkBg = Color(0xFF10141F)
  override val GradientStart = Color(0xFFFF8A5C)   // sunrise: coral → amber
  override val GradientEnd = Color(0xFFFFC24B)
  override val GlowAccent = Color(0x40FF7A59)
  override val GlowAccent2 = Color(0x40FFC24B)
}

object ZcLightColors : ZcPalette {
  override val Bg = Color(0xFFFAF6F0)          // warm paper
  override val Surface = Color(0xFFFFFFFF)
  override val Card = Color(0xFFFFFFFF)
  override val CardLight = Color(0xFFF3EDE3)
  override val Border = Color(0xFFE7DECD)
  override val Accent = Color(0xFFE85D3F)      // deeper coral for contrast on paper
  override val Accent2 = Color(0xFF0FA968)
  override val Cyan = Color(0xFF0E9FD8)
  override val Red = Color(0xFFE53E5C)
  override val Amber = Color(0xFFE8A020)
  override val Purple = Color(0xFF7C5CF0)
  override val Text = Color(0xFF221B13)
  override val Text2 = Color(0xFF6B6153)
  override val Text3 = Color(0xFFA29481)
  override val UserBg = Color(0xFFFBE9E1)
  override val ThinkBg = Color(0xFFF4EFE6)
  override val GradientStart = Color(0xFFF26B4F)
  override val GradientEnd = Color(0xFFF2A93B)
  override val GlowAccent = Color(0x35E85D3F)
  override val GlowAccent2 = Color(0x35E8A020)
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
 * Single brand gradient for the whole app (sunrise coral → amber), derived
 * from the active palette so dark and light themes stay coherent. Use for
 * ACTIVE states only (generating, loading, focus) — no glow at rest.
 */
fun ZcPalette.gradient(vertical: Boolean = false): Brush =
  if (vertical) Brush.verticalGradient(listOf(GradientStart, GradientEnd))
  else Brush.linearGradient(listOf(GradientStart, GradientEnd))


object ZcSemantic : ZcSemanticColors {
  // Primary actions, selection, focus — THE ONE ACCENT (coral)
  override val Primary = ZcColors.Accent
  override val PrimaryContainer = ZcColors.Accent.copy(alpha = 0.14f)
  override val OnPrimary = ZcColors.Bg

  // Success states only — emerald
  override val Success = ZcColors.Accent2
  override val SuccessContainer = ZcColors.Accent2.copy(alpha = 0.14f)
  override val OnSuccess = ZcColors.Bg

  // Destructive / error — rose
  override val Error = ZcColors.Red
  override val ErrorContainer = ZcColors.Red.copy(alpha = 0.14f)
  override val OnError = ZcColors.Bg

  // AI/tech indicator — cyan
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

  // Brand gradient (coral → amber) — active states only
  override val GradientStart = ZcColors.GradientStart
  override val GradientEnd = ZcColors.GradientEnd
}
/** Semantic colors resolved for the current theme (dark or light). */
@Composable
fun currentSemantic(): ZcSemanticColors =
  if (ThemeState.isDark) ZcSemantic else ZcLightSemantic

interface ZcSemanticColors {
  // Primary actions, selection, focus — THE ONE ACCENT (coral)
  val Primary: Color
  val PrimaryContainer: Color
  val OnPrimary: Color

  // Success states only — emerald
  val Success: Color
  val SuccessContainer: Color
  val OnSuccess: Color

  // Destructive / error — rose
  val Error: Color
  val ErrorContainer: Color
  val OnError: Color

  // AI/tech indicator — cyan
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

  // Brand gradient (coral → amber) — active states only
  val GradientStart: Color
  val GradientEnd: Color
}