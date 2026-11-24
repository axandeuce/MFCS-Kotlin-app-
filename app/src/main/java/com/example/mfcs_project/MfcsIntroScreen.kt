package com.example.mfcs_project

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// Дизайн дээрх өнгөнүүд
private val PitchBlack = Color(0xFF000000)
private val MfcsRed = Color(0xFFFF0000)
private val SubtleGray = Color(0xFFB3B3B3)

@Composable
fun MfcsIntroScreen(
    onFinished: () -> Unit
) {
    // Анимацийн төлөвүүд
    var startLogoAnim by remember { mutableStateOf(false) }
    var expandRedBg by remember { mutableStateOf(false) }

    // 1. MFCS лого томрох болон тодрох анимаци
    val logoScale by animateFloatAsState(
        targetValue = if (startLogoAnim) 1f else 0.72f,
        animationSpec = tween(durationMillis = 650, easing = FastOutSlowInEasing),
        label = "logoScale"
    )
    val logoAlpha by animateFloatAsState(
        targetValue = if (startLogoAnim) 1f else 0f,
        animationSpec = tween(durationMillis = 550, easing = FastOutSlowInEasing),
        label = "logoAlpha"
    )

    // 2. Дэвсгэр хар өнгөнөөс улаан өнгө рүү шилжих анимаци (2 дахь дэлгэц шиг)
    val backgroundColor by animateColorAsState(
        targetValue = if (expandRedBg) MfcsRed else PitchBlack,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "bgColor"
    )

    // Улаан дэвсгэр рүү ороход лого цагаан болж тодрох
    val logoColor by animateColorAsState(
        targetValue = if (expandRedBg) Color.White else MfcsRed,
        animationSpec = tween(durationMillis = 400),
        label = "logoColor"
    )

    // Бага зэргийн амьсгалах (pulse) эффект
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (startLogoAnim && !expandRedBg) 1.03f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    // Дараалсан цаг хугацааны хөтөлбөр
    LaunchedEffect(Unit) {
        delay(120)
        startLogoAnim = true
        delay(1150)
        expandRedBg = true
        delay(650)
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .scale(logoScale * pulseScale)
                .alpha(logoAlpha)
        ) {
            Text(
                text = "MFCS",
                color = logoColor,
                fontSize = 54.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 6.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "MONGOLIAN FIGHTING CHAMPIONSHIP",
                color = if (expandRedBg) Color.White.copy(alpha = 0.9f) else SubtleGray,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 2.2.sp
            )
        }
    }
}