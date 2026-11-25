package com.example.mfcs_project

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Hd
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val FpBgBlack = Color(0xFF000000)
private val FpCardDark = Color(0xFF141414)
private val FpCardInner = Color(0xFF1E1E1E)
private val FpAccentRed = Color(0xFFE50914)
private val FpDeepRed = Color(0xFF4A070B)
private val FpGold = Color(0xFFF59E0B)
private val FpGreen = Color(0xFF10B981)
private val FpTextWhite = Color(0xFFFFFFFF)
private val FpTextGray = Color(0xFFA6A6A6)
private val FpTextMuted = Color(0xFF6E6E6E)
private val FpBorderGray = Color(0xFF262626)

enum class FightPassCategory(val label: String) {
    ALL("ALL VIDEOS"),
    FULL_FIGHTS("FULL FIGHTS"),
    KNOCKOUTS("KNOCKOUTS"),
    SUBMISSIONS("SUBMISSIONS"),
    ORIGINALS("BACKSTAGE")
}

data class FightPassVideoItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val eventTag: String,
    val duration: String,
    val viewsLabel: String,
    val category: FightPassCategory,
    val isVipOnly: Boolean,
    val youtubeUrl: String,
    val accentGradientStart: Color
)

private val fightPassVideos = listOf(
    FightPassVideoItem(
        id = "fp_1",
        title = "ШИНЭБААТАР vs ОДСҮРЭН — 61KG TITLE BOUT",
        subtitle = "MGL-1 FC #22 • Аваргын бүсний төлөөх тулаан (2025.06.14)",
        eventTag = "MGL-1 #22",
        duration = "21:35",
        viewsLabel = "2.5K+ VIEWS",
        category = FightPassCategory.FULL_FIGHTS,
        isVipOnly = false,
        youtubeUrl = "https://youtu.be/jf2DsdSyug0?si=LquL64RUmOV3A8Gm",
        accentGradientStart = Color(0xFF5C0A0E)
    ),
    FightPassVideoItem(
        id = "fp_2",
        title = "ДЭЛГЭРМӨРӨН vs ТЭМҮҮЖИН — FULL TITLE FIGHT",
        subtitle = "MFCS 11: Main Event • Lightweight Championship",
        eventTag = "MFCS 11",
        duration = "14:28",
        viewsLabel = "48.2K VIEWS",
        category = FightPassCategory.FULL_FIGHTS,
        isVipOnly = true,
        youtubeUrl = "https://youtu.be/jf2DsdSyug0?si=LquL64RUmOV3A8Gm",
        accentGradientStart = Color(0xFF3B1315)
    ),
    FightPassVideoItem(
        id = "fp_3",
        title = "БАТ-ОРГИЛ vs АНХБАЯР — REAR NAKED CHOKE",
        subtitle = "MFCS 11: Co-Main Event • Full Fight Replay",
        eventTag = "VIP REPLAY",
        duration = "06:12",
        viewsLabel = "21.9K VIEWS",
        category = FightPassCategory.SUBMISSIONS,
        isVipOnly = true,
        youtubeUrl = "https://youtu.be/jf2DsdSyug0?si=LquL64RUmOV3A8Gm",
        accentGradientStart = Color(0xFF1E293B)
    ),
    FightPassVideoItem(
        id = "fp_4",
        title = "MFCS 12: COUNTDOWN & TRAINING CAMP",
        subtitle = "Бэлтгэлийн баримтат нэвтрүүлэг ба ярилцлага",
        eventTag = "ORIGINAL",
        duration = "22:10",
        viewsLabel = "34.1K VIEWS",
        category = FightPassCategory.ORIGINALS,
        isVipOnly = false,
        youtubeUrl = "https://youtu.be/jf2DsdSyug0?si=LquL64RUmOV3A8Gm",
        accentGradientStart = Color(0xFF2D1B08)
    )
)

@Composable
fun MfcsFightPassScreen() {
    var selectedCategory by remember { mutableStateOf(FightPassCategory.ALL) }
    var activeVideo by remember { mutableStateOf(fightPassVideos.first()) }
    var isVipUnlocked by remember { mutableStateOf(false) }
    var currentUser by remember { mutableStateOf<AuthUserSession?>(null) }
    var showAuthSheet by remember { mutableStateOf(false) }
    var openPaymentAfterAuth by remember { mutableStateOf(false) }
    var showCheckoutSheet by remember { mutableStateOf(false) }

    val navBarPadding = WindowInsets.navigationBars.asPaddingValues()
    val canPlayActiveVideo = !activeVideo.isVipOnly || isVipUnlocked

    val requestVipCheckout = {
        if (currentUser != null) {
            showCheckoutSheet = true
        } else {
            openPaymentAfterAuth = true
            showAuthSheet = true
        }
    }

    val vipPlan = remember {
        CheckoutPlan(
            code = "VIP_MONTHLY",
            title = "MFCS VIP FIGHT PASS",
            description = "Бүх шууд дамжуулалт (PPV), 1080p HD давталт ба архив",
            priceFormatted = "19,900₮ / САР"
        )
    }

    val filteredVideos = remember(selectedCategory) {
        if (selectedCategory == FightPassCategory.ALL) {
            fightPassVideos
        } else {
            fightPassVideos.filter { it.category == selectedCategory }
        }
    }

    if (showAuthSheet) {
        MfcsAuthBottomSheet(
            onDismiss = {
                showAuthSheet = false
                openPaymentAfterAuth = false
            },
            onAuthSuccess = { session ->
                currentUser = session
                showAuthSheet = false
                if (openPaymentAfterAuth) {
                    openPaymentAfterAuth = false
                    showCheckoutSheet = true
                }
            }
        )
    }

    if (showCheckoutSheet) {
        MfcsPaymentBottomSheet(
            plan = vipPlan,
            onDismiss = { showCheckoutSheet = false },
            onPaymentSuccess = { _, _ ->
                isVipUnlocked = true
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FpBgBlack)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(FpBgBlack)
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = "LIVE STREAM & ON-DEMAND",
                    color = FpAccentRed,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "MFCS FIGHT PASS",
                    color = FpTextWhite,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.5.sp
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(FpCardDark)
                        .border(1.dp, FpBorderGray, RoundedCornerShape(8.dp))
                        .clickable {
                            if (currentUser == null) {
                                openPaymentAfterAuth = false
                                showAuthSheet = true
                            } else {
                                currentUser = null
                                isVipUnlocked = false
                            }
                        }
                        .padding(horizontal = 9.dp, vertical = 6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = if (currentUser != null) FpGold else FpTextGray,
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = currentUser?.displayName ?: "НЭВТРЭХ",
                            color = FpTextWhite,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isVipUnlocked) FpGreen.copy(alpha = 0.18f) else FpDeepRed)
                        .border(
                            1.dp,
                            if (isVipUnlocked) FpGreen else FpAccentRed,
                            RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            if (isVipUnlocked) {
                                isVipUnlocked = false
                            } else {
                                requestVipCheckout()
                            }
                        }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = if (isVipUnlocked) Icons.Default.CheckCircle else Icons.Default.Star,
                            contentDescription = null,
                            tint = if (isVipUnlocked) FpGreen else FpGold,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = if (isVipUnlocked) "VIP ACTIVE" else "GO VIP",
                            color = FpTextWhite,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
            }
        }

        HorizontalDivider(color = FpBorderGray.copy(alpha = 0.7f), thickness = 1.dp)

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(FpBgBlack),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 12.dp,
                bottom = navBarPadding.calculateBottomPadding() + 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item(key = "featured_player") {
                FeaturedYouTubePlayerCard(
                    video = activeVideo,
                    canPlay = canPlayActiveVideo,
                    isLoggedIn = currentUser != null,
                    onUnlockClick = { requestVipCheckout() }
                )
            }

            item(key = "fp_categories") {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(FightPassCategory.entries) { category ->
                        val isSelected = category == selectedCategory
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isSelected) FpAccentRed else FpCardDark)
                                .border(
                                    1.dp,
                                    if (isSelected) FpAccentRed else FpBorderGray,
                                    RoundedCornerShape(6.dp)
                                )
                                .clickable { selectedCategory = category }
                                .padding(horizontal = 13.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = category.label,
                                color = FpTextWhite,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.4.sp
                            )
                        }
                    }
                }
            }

            item(key = "library_heading") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "FIGHT LIBRARY (${filteredVideos.size})",
                        color = FpTextGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.8.sp
                    )
                    Text(
                        text = "YOUTUBE HD",
                        color = FpAccentRed,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            items(filteredVideos, key = { it.id }) { video ->
                FightPassVideoRowCard(
                    video = video,
                    isCurrent = video.id == activeVideo.id,
                    isVipUnlocked = isVipUnlocked,
                    onClick = {
                        activeVideo = video
                        if (video.isVipOnly && !isVipUnlocked) {
                            requestVipCheckout()
                        }
                    }
                )
            }

            item(key = "vip_membership_banner") {
                VipPassSubscriptionCard(
                    isVipUnlocked = isVipUnlocked,
                    isLoggedIn = currentUser != null,
                    onActivateClick = {
                        if (!isVipUnlocked) {
                            requestVipCheckout()
                        }
                    }
                )
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}

@Composable
private fun FeaturedYouTubePlayerCard(
    video: FightPassVideoItem,
    canPlay: Boolean,
    isLoggedIn: Boolean,
    onUnlockClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(FpCardDark)
            .border(1.dp, FpAccentRed.copy(alpha = 0.7f), RoundedCornerShape(16.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .background(
                    Brush.linearGradient(
                        colors = listOf(video.accentGradientStart, Color(0xFF0D0D0D), FpBgBlack)
                    )
                )
        ) {
            if (canPlay) {
                YouTubeFightPlayer(
                    youtubeUrl = video.youtubeUrl,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { onUnlockClick() }
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(FpGold.copy(alpha = 0.2f))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "VIP LOCKED • ${video.eventTag}",
                                color = FpGold,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Hd,
                                contentDescription = null,
                                tint = FpTextWhite,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = video.duration,
                                color = FpTextWhite,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(FpGold)
                                .border(2.dp, Color.White.copy(alpha = 0.85f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Unlock VIP",
                                tint = Color.Black,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Text(
                            text = if (isLoggedIn) "ТӨЛБӨР ТӨЛӨӨД ҮЗЭХ" else "НЭВТРЭЭД ТӨЛБӨР ТӨЛӨХ",
                            color = FpTextWhite,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 0.6.sp
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = video.title,
                color = FpTextWhite,
                fontSize = 15.sp,
                fontWeight = FontWeight.Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = video.subtitle,
                color = FpTextGray,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun FightPassVideoRowCard(
    video: FightPassVideoItem,
    isCurrent: Boolean,
    isVipUnlocked: Boolean,
    onClick: () -> Unit
) {
    val locked = video.isVipOnly && !isVipUnlocked

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (isCurrent) FpCardInner else FpCardDark)
            .border(
                1.dp,
                if (isCurrent) FpAccentRed else FpBorderGray,
                RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .width(108.dp)
                .height(66.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(video.accentGradientStart, FpBgBlack)
                    )
                )
                .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                .padding(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.Center)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.65f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (locked) Icons.Default.Lock else Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = if (locked) FpGold else FpTextWhite,
                    modifier = Modifier.size(16.dp)
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(3.dp))
                    .background(Color.Black.copy(alpha = 0.8f))
                    .padding(horizontal = 4.dp, vertical = 1.dp)
            ) {
                Text(
                    text = video.duration,
                    color = FpTextWhite,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(3.dp))
                        .background(if (video.isVipOnly) FpGold.copy(alpha = 0.2f) else FpAccentRed.copy(alpha = 0.2f))
                        .padding(horizontal = 5.dp, vertical = 1.dp)
                ) {
                    Text(
                        text = if (video.isVipOnly) "VIP" else "FREE",
                        color = if (video.isVipOnly) FpGold else FpAccentRed,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = null,
                        tint = FpTextMuted,
                        modifier = Modifier.size(11.dp)
                    )
                    Text(
                        text = video.viewsLabel,
                        color = FpTextMuted,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Text(
                text = video.title,
                color = FpTextWhite,
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = video.subtitle,
                color = FpTextGray,
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun VipPassSubscriptionCard(
    isVipUnlocked: Boolean,
    isLoggedIn: Boolean,
    onActivateClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(FpDeepRed, FpCardDark)
                )
            )
            .border(1.dp, FpGold.copy(alpha = 0.65f), RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "MFCS VIP FIGHT PASS",
                color = FpGold,
                fontSize = 12.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 0.8.sp
            )
            Text(
                text = "19,900₮ / САР",
                color = FpTextWhite,
                fontSize = 14.sp,
                fontWeight = FontWeight.Black
            )
        }

        Text(
            text = if (isLoggedIn) {
                "Бүх шууд дамжуулалт (PPV), 1080p HD давталт, болон тамирчдын хөшигний арын нэвтрүүлгийг хязгааргүй үзэх эрх."
            } else {
                "VIP эрх худалдан авахын тулд эхлээд бүртгэлээрээ нэвтэрнэ үү."
            },
            color = FpTextGray,
            fontSize = 12.sp
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(if (isVipUnlocked) FpGreen else FpAccentRed)
                .clickable { onActivateClick() }
                .padding(vertical = 11.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = when {
                    isVipUnlocked -> "✓ VIP MEMBERSHIP ACTIVE"
                    isLoggedIn -> "ACTIVATE VIP FIGHT PASS"
                    else -> "НЭВТРЭЭД VIP ИДЭВХЖҮҮЛЭХ"
                },
                color = FpTextWhite,
                fontSize = 12.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 0.5.sp
            )
        }
    }
}