package dev.nutrilivre.ui.components

import androidx.compose.ui.draw.clip

import androidx.compose.ui.draw.clip

import androidx.compose.ui.draw.clip

import androidx.compose.ui.draw.clip

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerRecipeCard(modifier: Modifier = Modifier) {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition(label = "shimmerTransition")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = "shimmerTranslateAnim"
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(10f, 10f),
        end = Offset(translateAnim.value, translateAnim.value)
    )

    Column(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(brush)
            .height(200.dp) // Approximate height of a recipe card
    ) {
        // Placeholder for image
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .background(Color.LightGray.copy(alpha = 0.3f))
        )
        // Placeholder for text
        Column(modifier = Modifier.padding(16.dp)) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(20.dp)
                    .background(Color.LightGray.copy(alpha = 0.3f))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(16.dp)
                    .background(Color.LightGray.copy(alpha = 0.3f))
            )
        }
    }
}
