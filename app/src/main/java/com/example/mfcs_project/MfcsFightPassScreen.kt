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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val FpBgBlack = Color(0xFF000000)
private val FpCardDark = Color(0xFF141414)
private val FpCardElevated = Color(0xFF1C1C1C)
private val FpAccentRed = Color(0xFFE50914)
private val FpDarkRed = Color(0xFF420609)
private val FpTextWhite = Color(0xFFFFFFFF)
private val FpTextGray = Color(0xFFA6A6A6)

enum class FightPassTier(
    val titleMn: String,
    val subtitleMn: String,
    val priceText: String,
    val includesAllPpvFree: Boolean
) {
    NONE(
        titleMn = "Идэвхтэй багцгүй",
        subtitleMn = "Энгийн хэрэглэгч",
        priceText = "0₮",
        includesAllPpvFree = false
    ),
    MONTHLY(
        titleMn = "САРЫН ЭРХ (MONTHLY)",
        subtitleMn = "Хэдийд ч цуцлах боломжтой",
        priceText = "29,900₮ / сар",
        includesAllPpvFree = false
    ),
    ANNUAL_VIP(
        titleMn = "ЖИЛИЙН ЭРХ (ANNUAL VIP)",
        subtitleMn = "12 сар + PPV тусгай эвентүүд үнэгүй",
        priceText = "249,000₮ / жил",
        includesAllPpvFree = true
    )
}

data class PpvEventItem(
    val id: String,
    val code: String,
    val title: String,
    val mainEvent: String,
    val dateLabel: String,
    val standalonePrice: String,
    val monthlyDiscountPrice: String,
    val isPpvExclusive: Boolean,
    val accentColor: Color
)

private val samplePpvEvents = listOf(
    PpvEventItem(
        id = "ppv_1",
        code = "MFCS 12 • PPV",
        title = "ULAANBAATAR CHAMPIONSHIP NIGHT",
        mainEvent = "Дэлгэрмөрөн vs Тэмүүжин • Хөнгөн жингийн аваргын бүс",
        dateLabel = "11/28/26 • 8:00 PM",
        standalonePrice = "29,900₮",
        monthlyDiscountPrice = "14,900₮ (-50%)",
        isPpvExclusive = true,
        accentColor = Color(0xFF7D0910)
    ),
    PpvEventItem(
        id = "ppv_2",
        code = "MFCS 13 • PPV",
        title = "STEPPE KINGS: HEAVYWEIGHT WAR",
        mainEvent = "Золбоо vs Дөлгөөн • Хүнд жингийн халз тулаан",
        dateLabel = "12/19/26 • 9:00 PM",
        standalonePrice = "29,900₮",
        monthlyDiscountPrice = "14,900₮ (-50%)",
        isPpvExclusive = true,
        accentColor = Color(0xFF4A0E12)
    ),
    PpvEventItem(
        id = "ppv_3",
        code = "FIGHT NIGHT",
        title = "MFCS FIGHT NIGHT: NOMAD RISING",
        mainEvent = "Мөнхбаяр vs Бат-Эрдэнэ • Дунд жин",
        dateLabel = "11/21/26 • 8:00 PM",
        standalonePrice = "19,900₮",
        monthlyDiscountPrice = "ҮНЭГҮЙ (Багцад орсон)",
        isPpvExclusive = false,
        accentColor = Color(0xFF1F2937)
    )
)

@Composable
fun MfcsFightPassScreen(
    selectedTier: FightPassTier = FightPassTier.NONE,
    onSelectTier: (FightPassTier) -> Unit = {}
) {
    var localTier by remember(selectedTier) { mutableStateOf(selectedTier) }
    var activeWatchingPpv by remember { mutableStateOf<PpvEventItem?>(null) }
    var statusBannerMessage by remember { mutableStateOf<String?>(null) }

    fun updateTier(newTier: FightPassTier) {
        localTier = newTier
        onSelectTier(newTier)
        statusBannerMessage = when (newTier) {
            FightPassTier.ANNUAL_VIP -> "ЖИЛИЙН VIP ЭРХ идэвхэжлээ! Бүх PPV тусгай эвентүүд 100% үнэгүй нээгдлээ."
            FightPassTier.MONTHLY -> "САРЫН ЭРХ идэвхэжлээ! Fight Night болон архив нээгдэж, PPV эвентүүд 50% хөнгөлөлттэй боллоо."
            FightPassTier.NONE -> null
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FpBgBlack)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // 1. VIP STREAMING & STATS HERO CARD (Зураг дээрх шиг)
            item(key = "fp_hero_card") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(FpDarkRed, FpCardDark, FpBgBlack)
                            )
                        )
                        .border(1.dp, Color(0xFF4A1215), RoundedCornerShape(14.dp))
                        .padding(18.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFF2B090B))
                                .border(1.dp, FpAccentRed, RoundedCornerShape(4.dp))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "VIP STREAMING & STATS",
                                color = FpAccentRed,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 0.8.sp
                            )
                        }

                        Text(
                            text = "MFCS FIGHT PASS",
                            color = FpTextWhite,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 0.8.sp
                        )

                        Text(
                            text = "Монголын холимог тулааны бүх шууд дамжуулалт, 4K камерын сонголт болон тулаанчдын нарийвчилсан статистик датаг хязгааргүй үзээрэй.",
                            color = FpTextGray,
                            fontSize = 12.sp,
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        FightPassBenefitRow("Бүх MFCS Fight Night болон Аваргын тулаан шууд")
                        FightPassBenefitRow("Үе тус бүрийн бодит цагийн цохилт, барилдааны дата")
                        FightPassBenefitRow("Өмнөх бүх тэмцээний бүрэн архив (1080p / 60fps)")
                    }
                }
            }

            // Идэвхтэй багцын төлөвийн мэдэгдэл
            statusBannerMessage?.let { msg ->
                item(key = "fp_status_banner") {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF1F0A0B))
                            .border(1.dp, FpAccentRed, RoundedCornerShape(10.dp))
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = FpAccentRed,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = msg,
                            color = FpTextWhite,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // 2. БАГЦ СОНГОХ ГАРЧИГ
            item(key = "fp_choose_plan_title") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "БАГЦ СОНГОХ",
                        color = FpTextGray,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.8.sp
                    )
                    if (localTier != FightPassTier.NONE) {
                        Text(
                            text = "Идэвхтэй: ${localTier.titleMn}",
                            color = FpAccentRed,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // 3. САРЫН ЭРХ (MONTHLY) CARD
            item(key = "fp_monthly_card") {
                val isCurrent = localTier == FightPassTier.MONTHLY
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(FpCardDark)
                        .border(
                            width = if (isCurrent) 1.5.dp else 1.dp,
                            color = if (isCurrent) FpAccentRed else Color(0xFF262626),
                            shape = RoundedCornerShape(14.dp)
                        )
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "САРЫН ЭРХ (MONTHLY)",
                                color = FpTextWhite,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Хэдийд ч цуцлах боломжтой • PPV 50% хөнгөлөлттэй",
                                color = FpTextGray,
                                fontSize = 12.sp
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "29,900₮",
                                color = FpTextWhite,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Black
                            )
                            Text(
                                text = "/ сар",
                                color = FpTextGray,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Button(
                        onClick = { updateTier(FightPassTier.MONTHLY) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isCurrent) Color(0xFF2E2E2E) else Color(0xFF242424)
                        )
                    ) {
                        Text(
                            text = if (isCurrent) "ИДЭВХТЭЙ БАГЦ (САРЫН ЭРХ)" else "ЭНЭ БАГЦЫГ СОНГОХ",
                            color = FpTextWhite,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }

            // 4. ЖИЛИЙН ЭРХ (ANNUAL VIP) CARD + "ХАМГИЙН ХЭМНЭЛТТЭЙ • -30%" BADGE
            item(key = "fp_annual_vip_card") {
                val isCurrent = localTier == FightPassTier.ANNUAL_VIP
                Box(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(FpCardDark)
                            .border(
                                width = if (isCurrent) 2.dp else 1.5.dp,
                                color = FpAccentRed,
                                shape = RoundedCornerShape(14.dp)
                            )
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "ЖИЛИЙН ЭРХ (ANNUAL VIP)",
                                    color = FpTextWhite,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Black
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "12 сар + PPV тусгай эвентүүд үнэгүй",
                                    color = FpTextGray,
                                    fontSize = 12.sp
                                )
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "249,000₮",
                                    color = FpAccentRed,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Black
                                )
                                Text(
                                    text = "358,800₮",
                                    color = FpTextGray,
                                    fontSize = 11.sp,
                                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
                                )
                            }
                        }

                        Button(
                            onClick = { updateTier(FightPassTier.ANNUAL_VIP) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = FpAccentRed)
                        ) {
                            Text(
                                text = if (isCurrent) "VIP ЭРХ ИДЭВХЖСЭН БАЙНА ✓" else "VIP ЭРХ ИДЭВХЖҮҮЛЭХ",
                                color = FpTextWhite,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 0.6.sp
                            )
                        }
                    }

                    // Баруун дээд улаан хэмнэлтийн badge
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(end = 14.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(FpAccentRed)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "ХАМГИЙН ХЭМНЭЛТТЭЙ • -30%",
                            color = FpTextWhite,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }

            // 5. БАГЦААС ХАМААРЧ НЭЭГДЭХ PPV ТУСГАЙ ЭВЕНТҮҮД
            item(key = "fp_ppv_header") {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    HorizontalDivider(color = Color(0xFF222222), modifier = Modifier.padding(vertical = 4.dp))
                    Text(
                        text = "PPV ТУСГАЙ ЭВЕНТҮҮД БА ЭРХИЙН ТӨЛӨВ",
                        color = FpTextWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.6.sp
                    )
                    Text(
                        text = when (localTier) {
                            FightPassTier.ANNUAL_VIP -> "Таны ANNUAL VIP багцад доорх бүх PPV эвентүүд 100% үнэгүй багтсан."
                            FightPassTier.MONTHLY -> "Таны MONTHLY багцад Fight Night үнэгүй, PPV тусгай эвентүүд 50% хөнгөлөлттэй."
                            FightPassTier.NONE -> "Багц сонгосноор PPV эвентүүдийг үнэгүй эсвэл хөнгөлөлттэй үзэх эрх нээгдэнэ."
                        },
                        color = FpTextGray,
                        fontSize = 12.sp
                    )
                }
            }

            items(samplePpvEvents, key = { it.id }) { ppvEvent ->
                val isUnlockedForUser = when {
                    localTier == FightPassTier.ANNUAL_VIP -> true
                    localTier == FightPassTier.MONTHLY && !ppvEvent.isPpvExclusive -> true
                    else -> false
                }

                val accessBadgeText = when {
                    localTier == FightPassTier.ANNUAL_VIP -> "ANNUAL VIP • ҮНЭГҮЙ НЭЭЛТТЭЙ"
                    localTier == FightPassTier.MONTHLY && !ppvEvent.isPpvExclusive -> "MONTHLY • БАГЦАД ОРСОН"
                    localTier == FightPassTier.MONTHLY -> "MONTHLY ХӨНГӨЛӨЛТ: ${ppvEvent.monthlyDiscountPrice}"
                    else -> "PPV ҮНЭ: ${ppvEvent.standalonePrice} (VIP-Д ҮНЭГҮЙ)"
                }

                PpvWorthCard(
                    event = ppvEvent,
                    isUnlocked = isUnlockedForUser,
                    accessBadgeText = accessBadgeText,
                    onActionClick = {
                        if (isUnlockedForUser) {
                            activeWatchingPpv = ppvEvent
                        } else {
                            // PPV үзэхийн тулд Annual VIP багцыг идэвхжүүлэх санал
                            updateTier(FightPassTier.ANNUAL_VIP)
                        }
                    }
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }

        // PPV Нээгдсэн үед үзэх тоглуулагч
        activeWatchingPpv?.let { ppv ->
            PpvPlayerOverlay(
                event = ppv,
                tier = localTier,
                onBack = { activeWatchingPpv = null }
            )
        }
    }
}

@Composable
private fun FightPassBenefitRow(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = FpAccentRed,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = text,
            color = FpTextWhite.copy(alpha = 0.92f),
            fontSize = 12.sp
        )
    }
}

@Composable
private fun PpvWorthCard(
    event: PpvEventItem,
    isUnlocked: Boolean,
    accessBadgeText: String,
    onActionClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(FpCardDark)
            .border(
                1.dp,
                if (isUnlocked) Color(0xFF2E7D32) else Color(0xFF262626),
                RoundedCornerShape(12.dp)
            )
            .clickable { onActionClick() }
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(event.accentColor)
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(
                    text = event.code,
                    color = FpTextWhite,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = if (isUnlocked) Icons.Default.LockOpen else Icons.Default.Lock,
                    contentDescription = null,
                    tint = if (isUnlocked) Color(0xFF4CAF50) else FpAccentRed,
                    modifier = Modifier.size(15.dp)
                )
                Text(
                    text = if (isUnlocked) "ҮЗЭХ ЭРХТЭЙ" else "ТҮГЖЭЭТЭЙ",
                    color = if (isUnlocked) Color(0xFF4CAF50) else FpAccentRed,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Text(
            text = event.title,
            color = FpTextWhite,
            fontSize = 15.sp,
            fontWeight = FontWeight.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = event.mainEvent,
            color = FpTextGray,
            fontSize = 12.sp
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = accessBadgeText,
                color = if (isUnlocked) Color(0xFF81C784) else FpTextWhite,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = onActionClick,
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isUnlocked) Color(0xFF1E3A24) else FpAccentRed
                ),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Icon(
                    imageVector = if (isUnlocked) Icons.Default.PlayArrow else Icons.Default.Star,
                    contentDescription = null,
                    tint = FpTextWhite,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (isUnlocked) "ШУУД ҮЗЭХ" else "VIP-ЭЭР НЭЭХ",
                    color = FpTextWhite,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun PpvPlayerOverlay(
    event: PpvEventItem,
    tier: FightPassTier,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FpBgBlack)
            .statusBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Буцах",
                    tint = FpTextWhite
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = event.code,
                color = FpAccentRed,
                fontSize = 14.sp,
                fontWeight = FontWeight.Black
            )
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(48.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(event.accentColor, FpCardElevated, FpBgBlack)
                    )
                )
                .border(1.dp, FpAccentRed, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = FpTextWhite,
                    modifier = Modifier.size(52.dp)
                )
                Text(
                    text = "4K 60FPS STREAMING • ${tier.titleMn}",
                    color = FpTextWhite,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Text(
            text = event.title,
            color = FpTextWhite,
            fontSize = 20.sp,
            fontWeight = FontWeight.Black
        )
        Text(
            text = event.mainEvent,
            color = FpTextGray,
            fontSize = 13.sp
        )
        Text(
            text = "Таны сонгосон ${tier.titleMn} багцын хүрээнд энэхүү тусгай эвентийг үзэх эрх бүрэн нээгдсэн байна.",
            color = Color(0xFF81C784),
            fontSize = 12.sp
        )

        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Буцах", color = FpTextWhite)
        }
    }
}