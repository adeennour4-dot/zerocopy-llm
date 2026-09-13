package com.gguf.zerocopy.ui.theme

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import kotlin.math.min

/**
 * ZeroCopy motion language — precise, warm, responsive.
 * Base 100ms grid (×1, ×1.6, ×2.2, ×3.4, ×4.5) for rhythmic consistency.
 * Easings stay Material-standard so motion feels native, with a slightly
 * longer decelerate for the warmer "ember" settle.
 */
object ZcMotion {
  val xs  = 100   // micro: ripple, press
  val sm  = 160   // small: chip expand, tooltip
  val md  = 220   // standard: dialog, sheet, navigation
  val lg  = 340   // large: screen transition, modal
  val xl  = 450   // hero: full-screen enter

  val standard = CubicBezierEasing(0.4f, 0f, 0.2f, 1f)    // Material standard
  val decelerate = CubicBezierEasing(0.05f, 0.7f, 0.08f, 1f) // soft warm enter
  val accelerate = CubicBezierEasing(0.3f, 0f, 0.8f, 0.15f) // crisp expressive exit
  val emphasis = CubicBezierEasing(0.2f, 0f, 0f, 1f)      // snappy press response
}

/**
 * Staggered mount entrance — fade + slide, geometrically staggered by index.
 * Uses the motion grid: 100ms base, 36ms stagger.
 */
@Composable
fun ZcEnter(
    index: Int = 0,
    vertical: Boolean = true,
    staggerMs: Int = 36,
    content: @Composable () -> Unit
) {
    val delayMs = min(index, 12) * staggerMs
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(tween(ZcMotion.sm, delayMillis = delayMs, easing = ZcMotion.decelerate)) +
                if (vertical) expandVertically(tween(ZcMotion.sm, delayMillis = delayMs, easing = ZcMotion.decelerate))
                else expandHorizontally(tween(ZcMotion.sm, delayMillis = delayMs, easing = ZcMotion.decelerate)),
        content = { content() }
    )
}

/**
 * Animated brand gradient field — slow, geometric rotation (9s cycle).
 * Oversized 1.5× + clipped so rotation never exposes edges.
 * Use ONLY for active states (generating, loading) — no glow at rest.
 */
@Composable
fun ZcGradientField(
    modifier: Modifier = Modifier,
    alpha: Float = 0.16f
) {
    val transition = rememberInfiniteTransition(label = "zcGradientField")
    val angle by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(9000, easing = LinearEasing))
    )
    val palette = currentPalette()
    Box(
        modifier
            .graphicsLayer { scaleX = 1.5f; scaleY = 1.5f; rotationZ = angle }
            .background(
                Brush.linearGradient(
                    listOf(palette.GradientStart.copy(alpha = alpha), palette.GradientEnd.copy(alpha = alpha))
                )
            )
    ) {}
}