package com.example.mfcs_project

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
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

private val UpBgBlack = Color(0xFF000000)
private val UpCardDark = Color(0xFF141414)
private val UpCardInner = Color(0xFF1E1E1E)
private val UpAccentRed = Color(0xFFE50914)
private val UpDeepRed = Color(0xFF4A070B)
private val UpGold = Color(0xFFF59E0B)
private val UpTextWhite = Color(0xFFFFFFFF)
private val UpTextGray = Color(0xFFA6A6A6)
private val UpTextMuted = Color(0xFF6E6E6E)
private val UpBorderGray = Color(0xFF262626)

enum class UpcomingFilter(val label: String) {
    ALL("ALL UPCOMING"),
    TITLE_FIGHTS("TITLE BOUTS"),
    PPV("PPV / VIP"),
    THIS_MONTH("THIS MONTH")
}

data class UpcomingFighterProfile(
    val name: String,
    val record: String,
    val height: String,
    val reach: String,
    val rankBadge: String,
    val cornerColor: Color
)

data class UpcomingBoutMatchup(
    val id: String,
    val cardSegment: String,
    val weightClass: String,
    val rounds: String,
    val isTitleBout: Boolean,
    val redCorner: UpcomingFighterProfile,
    val blueCorner: UpcomingFighterProfile
)

data class UpcomingEventPackage(
    val id: String,
    val eventCode: String,
    val title: String,
    val monthShort: String,
    val dayNumber: String,
    val fullDateAndTime: String,
    val venue: String,
    val countdownText: String,
    val isPpv: Boolean,
    val hasTitleFight: Boolean,
    val isThisMonth: Boolean,
    val ticketPriceLabel: String,
    val bouts: List<UpcomingBoutMatchup>
)

private val mfcs12FightCard = listOf(
    UpcomingBoutMatchup(
        id = "up_b1",
        cardSegment = "MAIN EVENT • WORLD TITLE",
        weightClass = "LIGHTWEIGHT • 70 KG",
        rounds = "5 ROUNDS",
        isTitleBout = true,
        redCorner = UpcomingFighterProfile("ДЭЛГЭРМӨРӨН", "15-2-0", "178 см", "182 см", "CHAMPION", Color(0xFFE50914)),
        blueCorner = UpcomingFighterProfile("БАТ-ОРГИЛ", "13-1-0", "180 см", "184 см", "#1 CONTENDER", Color(0xFF3B82F6))
    ),
    UpcomingBoutMatchup(
        id = "up_b2",
        cardSegment = "CO-MAIN EVENT",
        weightClass = "MIDDLEWEIGHT • 84 KG",
        rounds = "3 ROUNDS",
        isTitleBout = false,
        redCorner = UpcomingFighterProfile("ЗОЛБОО", "11-2-0", "185 см", "189 см", "#2 RANKED", Color(0xFFE50914)),
        blueCorner = UpcomingFighterProfile("ГАНЗОРИГ", "13-2-0", "186 см", "191 см", "#3 RANKED", Color(0xFF3B82F6))
    ),
    UpcomingBoutMatchup(
        id = "up_b3",
        cardSegment = "MAIN CARD",
        weightClass = "FEATHERWEIGHT • 66 KG",
        rounds = "3 ROUNDS",
        isTitleBout = false,
        redCorner = UpcomingFighterProfile("МӨНХ-ЭРДЭНЭ", "16-4-0", "174 см", "177 см", "#1 RANKED", Color(0xFFE50914)),
        blueCorner = UpcomingFighterProfile("БЯМБАДОРЖ", "10-1-0", "172 см", "175 см", "#4 RANKED", Color(0xFF3B82F6))
    ),
    UpcomingBoutMatchup(
        id = "up_b4",
        cardSegment = "MAIN CARD",
        weightClass = "WELTERWEIGHT • 77 KG",
        rounds = "3 ROUNDS",
        isTitleBout = false,
        redCorner = UpcomingFighterProfile("ТҮВШИНЖАРГАЛ", "12-2-0", "182 см", "185 см", "#2 RANKED", Color(0xFFE50914)),
        blueCorner = UpcomingFighterProfile("СҮХБАТ", "9-0-0", "181 см", "183 см", "UNDEFEATED", Color(0xFF3B82F6))
    ),
    UpcomingBoutMatchup(
        id = "up_b5",
        cardSegment = "PRELIMS",
        weightClass = "LIGHT HEAVYWEIGHT • 93 KG",
        rounds = "3 ROUNDS",
        isTitleBout = false,
        redCorner = UpcomingFighterProfile("АЛТАНХУЯГ", "17-3-0", "190 см", "194 см", "#3 RANKED", Color(0xFFE50914)),
        blueCorner = UpcomingFighterProfile("ОДБАЯР", "10-4-0", "188 см", "191 см", "#5 RANKED", Color(0xFF3B82F6))
    ),
    UpcomingBoutMatchup(
        id = "up_b6",
        cardSegment = "PRELIMS",
        weightClass = "BANTAMWEIGHT • 61 KG",
        rounds = "3 ROUNDS",
        isTitleBout = false,
        redCorner = UpcomingFighterProfile("ЭНХТҮВШИН", "11-5-0", "170 см", "172 см", "#6 RANKED", Color(0xFFE50914)),
        blueCorner = UpcomingFighterProfile("ТӨГӨЛДӨР", "10-3-0", "171 см", "174 см", "#7 RANKED", Color(0xFF3B82F6))
    ),
    UpcomingBoutMatchup(
        id = "up_b7",
        cardSegment = "PRELIMS • OPENER",
        weightClass = "FLYWEIGHT • 57 KG",
        rounds = "3 ROUNDS",
        isTitleBout = false,
        redCorner = UpcomingFighterProfile("ЭРДЭНЭБОЛД", "8-1-0", "166 см", "169 см", "PROSPECT", Color(0xFFE50914)),
        blueCorner = UpcomingFighterProfile("НАРАНБААТАР", "7-3-0", "167 см", "168 см", "CONTENDER", Color(0xFF3B82F6))
    )
)

private val upcomingEventsCatalog = listOf(
    UpcomingEventPackage(
        id = "up_ev_1",
        eventCode = "MFCS 12 • PPV",
        title = "WINTER CLASH: ULAANBAATAR",
        monthShort = "NOV",
        dayNumber = "28",
        fullDateAndTime = "NOV 28, 2026 • 19:00 ULAT",
        venue = "Steppe Arena • Ulaanbaatar",
        countdownText = "03 ӨДӨР : 07 ЦАГ : 08 МИН",
        isPpv = true,
        hasTitleFight = true,
        isThisMonth = true,
        ticketPriceLabel = "VIP PPV • 25,000₮",
        bouts = mfcs12FightCard
    ),
    UpcomingEventPackage(
        id = "up_ev_2",
        eventCode = "NOMAD 14",
        title = "NOMAD FIGHT NIGHT: STEPPE FURY",
        monthShort = "DEC",
        dayNumber = "12",
        fullDateAndTime = "DEC 12, 2026 • 20:00 ULAT",
        venue = "ASA Arena • Ulaanbaatar",
        countdownText = "17 ӨДӨР : 08 ЦАГ",
        isPpv = false,
        hasTitleFight = false,
        isThisMonth = false,
        ticketPriceLabel = "FIGHT PASS FREE",
        bouts = mfcs12FightCard.mapIndexed { idx, b -> b.copy(id = "up2_$idx") }
    ),
    UpcomingEventPackage(
        id = "up_ev_3",
        eventCode = "MFCS 13 • PPV",
        title = "NEW YEAR CHAMPIONSHIP FINALS",
        monthShort = "DEC",
        dayNumber = "27",
        fullDateAndTime = "DEC 27, 2026 • 19:30 ULAT",
        venue = "Buyant-Ukhaa Palace • UB",
        countdownText = "32 ӨДӨР : 07 ЦАГ",
        isPpv = true,
        hasTitleFight = true,
        isThisMonth = false,
        ticketPriceLabel = "VIP PPV • 30,000₮",
        bouts = mfcs12FightCard.mapIndexed { idx, b -> b.copy(id = "up3_$idx") }
    ),
    UpcomingEventPackage(
        id = "up_ev_4",
        eventCode = "DARKHAN 05",
        title = "NORTHERN WARRIORS SHOWDOWN",
        monthShort = "JAN",
        dayNumber = "16",
        fullDateAndTime = "JAN 16, 2027 • 19:00 ULAT",
        venue = "Darkhan Sports Complex",
        countdownText = "52 ӨДӨР",
        isPpv = false,
        hasTitleFight = false,
        isThisMonth = false,
        ticketPriceLabel = "FIGHT PASS FREE",
        bouts = mfcs12FightCard.mapIndexed { idx, b -> b.copy(id = "up4_$idx") }
    )
)

@Composable
fun MfcsEventsScreen(
    onNavigateBack: (() -> Unit)? = null,
    onNavigateToFightPass: (() -> Unit)? = null
) {
    var selectedFilter by remember { mutableStateOf(UpcomingFilter.ALL) }
    var selectedEvent by remember { mutableStateOf<UpcomingEventPackage?>(null) }
    val reminderStates = remember { mutableStateMapOf<String, Boolean>() }

    BackHandler(enabled = selectedEvent != null) {
        selectedEvent = null
    }

    val filteredEvents = remember(selectedFilter) {
        when (selectedFilter) {
            UpcomingFilter.ALL -> upcomingEventsCatalog
            UpcomingFilter.TITLE_FIGHTS -> upcomingEventsCatalog.filter { it.hasTitleFight }
            UpcomingFilter.PPV -> upcomingEventsCatalog.filter { it.isPpv }
            UpcomingFilter.THIS_MONTH -> upcomingEventsCatalog.filter { it.isThisMonth }
        }
    }

    AnimatedContent(
        targetState = selectedEvent,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "upcoming_event_transition"
    ) { currentEvent ->
        if (currentEvent == null) {
            UpcomingEventsOverview(
                events = filteredEvents,
                selectedFilter = selectedFilter,
                onSelectFilter = { selectedFilter = it },
                reminderStates = reminderStates,
                onToggleReminder = { id ->
                    reminderStates[id] = !(reminderStates[id] ?: false)
                },
                onOpenEvent = { selectedEvent = it },
                onNavigateBack = onNavigateBack
            )
        } else {
            UpcomingEventFightCardDetail(
                event = currentEvent,
                isReminded = reminderStates[currentEvent.id] ?: false,
                onToggleReminder = {
                    reminderStates[currentEvent.id] = !(reminderStates[currentEvent.id] ?: false)
                },
                onBack = { selectedEvent = null },
                onNavigateToFightPass = onNavigateToFightPass
            )
        }
    }
}

@Composable
private fun UpcomingEventsOverview(
    events: List<UpcomingEventPackage>,
    selectedFilter: UpcomingFilter,
    onSelectFilter: (UpcomingFilter) -> Unit,
    reminderStates: Map<String, Boolean>,
    onToggleReminder: (String) -> Unit,
    onOpenEvent: (UpcomingEventPackage) -> Unit,
    onNavigateBack: (() -> Unit)?
) {
    val heroEvent = events.firstOrNull()
    val otherEvents = events.drop(1)
    val navBarPadding = WindowInsets.navigationBars.asPaddingValues()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(UpBgBlack)
    ) {
        // Гүйлгэх үед дээд статус самбар/камер дээр ямар ч текст давхцахгүй байлгах хөдөлгөөнгүй толгой хэсэг
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(UpBgBlack)
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (onNavigateBack != null) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(UpCardDark)
                        .border(1.dp, UpBorderGray, RoundedCornerShape(8.dp))
                        .clickable { onNavigateBack() }
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = UpTextWhite,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = "BACK",
                        color = UpTextWhite,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = "FIGHT CALENDAR & LINEUPS",
                    color = UpAccentRed,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "UPCOMING EVENTS",
                    color = UpTextWhite,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.5.sp
                )
            }
        }

        HorizontalDivider(color = UpBorderGray.copy(alpha = 0.7f), thickness = 1.dp)

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(UpBgBlack),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 12.dp,
                bottom = navBarPadding.calculateBottomPadding() + 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item(key = "upcoming_filters") {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(UpcomingFilter.entries) { filter ->
                        val isSelected = filter == selectedFilter
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isSelected) UpAccentRed else UpCardDark)
                                .border(
                                    1.dp,
                                    if (isSelected) UpAccentRed else UpBorderGray,
                                    RoundedCornerShape(6.dp)
                                )
                                .clickable { onSelectFilter(filter) }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = filter.label,
                                color = UpTextWhite,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }
                }
            }

            if (heroEvent != null) {
                item(key = "hero_upcoming_${heroEvent.id}") {
                    HeroUpcomingCard(
                        event = heroEvent,
                        isReminded = reminderStates[heroEvent.id] ?: false,
                        onToggleReminder = { onToggleReminder(heroEvent.id) },
                        onOpenFightCard = { onOpenEvent(heroEvent) }
                    )
                }
            }

            if (otherEvents.isNotEmpty()) {
                item(key = "more_events_title") {
                    Text(
                        text = "SCHEDULED SHOWS",
                        color = UpTextGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.8.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                items(otherEvents, key = { it.id }) { event ->
                    ScheduledEventRowCard(
                        event = event,
                        isReminded = reminderStates[event.id] ?: false,
                        onToggleReminder = { onToggleReminder(event.id) },
                        onClick = { onOpenEvent(event) }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun HeroUpcomingCard(
    event: UpcomingEventPackage,
    isReminded: Boolean,
    onToggleReminder: () -> Unit,
    onOpenFightCard: () -> Unit
) {
    val mainBout = event.bouts.first()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(UpDeepRed, UpCardDark, UpBgBlack)
                )
            )
            .border(1.dp, UpAccentRed, RoundedCornerShape(16.dp))
            .clickable { onOpenFightCard() }
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(UpAccentRed)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = event.eventCode,
                        color = UpTextWhite,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                if (event.hasTitleFight) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = null,
                            tint = UpGold,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "TITLE FIGHT",
                            color = UpGold,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    tint = UpAccentRed,
                    modifier = Modifier.size(13.dp)
                )
                Text(
                    text = event.countdownText,
                    color = UpTextWhite,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = event.title,
                color = UpTextWhite,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = UpTextGray,
                    modifier = Modifier.size(13.dp)
                )
                Text(
                    text = "${event.fullDateAndTime}  •  ${event.venue}",
                    color = UpTextGray,
                    fontSize = 11.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Black.copy(alpha = 0.45f))
                .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(12.dp))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FaceOffFighterMini(
                fighter = mainBout.redCorner,
                modifier = Modifier.weight(1f)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "VS",
                    color = UpAccentRed,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = mainBout.weightClass.substringBefore("•").trim(),
                    color = UpTextGray,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            FaceOffFighterMini(
                fighter = mainBout.blueCorner,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(UpAccentRed)
                    .clickable { onOpenFightCard() }
                    .padding(vertical = 11.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "FULL FIGHT CARD (${event.bouts.size * 2} FIGHTERS)",
                    color = UpTextWhite,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.4.sp
                )
            }

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isReminded) UpCardInner else Color.Black.copy(alpha = 0.6f))
                    .border(
                        1.dp,
                        if (isReminded) UpGold else UpBorderGray,
                        RoundedCornerShape(8.dp)
                    )
                    .clickable { onToggleReminder() }
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = if (isReminded) Icons.Default.NotificationsActive else Icons.Default.NotificationsNone,
                    contentDescription = null,
                    tint = if (isReminded) UpGold else UpTextWhite,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = if (isReminded) "SAVED" else "REMIND",
                    color = if (isReminded) UpGold else UpTextWhite,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@Composable
private fun ScheduledEventRowCard(
    event: UpcomingEventPackage,
    isReminded: Boolean,
    onToggleReminder: () -> Unit,
    onClick: () -> Unit
) {
    val mainBout = event.bouts.first()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(UpCardDark)
            .border(1.dp, UpBorderGray, RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            modifier = Modifier
                .width(54.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(UpCardInner)
                .border(1.dp, UpBorderGray, RoundedCornerShape(10.dp))
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = event.monthShort,
                color = UpAccentRed,
                fontSize = 10.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = event.dayNumber,
                color = UpTextWhite,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = event.eventCode,
                    color = UpAccentRed,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "• ${event.bouts.size} BOUTS",
                    color = UpTextMuted,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = event.title,
                color = UpTextWhite,
                fontSize = 15.sp,
                fontWeight = FontWeight.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "${mainBout.redCorner.name} vs ${mainBout.blueCorner.name}",
                color = UpTextGray,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = event.venue,
                color = UpTextMuted,
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = if (isReminded) Icons.Default.NotificationsActive else Icons.Default.NotificationsNone,
                contentDescription = "Remind",
                tint = if (isReminded) UpGold else UpTextGray,
                modifier = Modifier
                    .size(20.dp)
                    .clickable { onToggleReminder() }
            )
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = UpAccentRed,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun UpcomingEventFightCardDetail(
    event: UpcomingEventPackage,
    isReminded: Boolean,
    onToggleReminder: () -> Unit,
    onBack: () -> Unit,
    onNavigateToFightPass: (() -> Unit)?
) {
    val navBarPadding = WindowInsets.navigationBars.asPaddingValues()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(UpBgBlack)
    ) {
        // Дэлгэрэнгүй хэсгийн дээд тогтвортой хэсэг
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(UpBgBlack)
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(UpCardDark)
                    .border(1.dp, UpBorderGray, RoundedCornerShape(8.dp))
                    .clickable { onBack() }
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = UpTextWhite,
                    modifier = Modifier.size(15.dp)
                )
                Text(
                    text = "BACK TO UPCOMING",
                    color = UpTextWhite,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isReminded) UpCardInner else UpCardDark)
                    .border(1.dp, if (isReminded) UpGold else UpBorderGray, RoundedCornerShape(8.dp))
                    .clickable { onToggleReminder() }
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Icon(
                    imageVector = if (isReminded) Icons.Default.NotificationsActive else Icons.Default.NotificationsNone,
                    contentDescription = null,
                    tint = if (isReminded) UpGold else UpTextWhite,
                    modifier = Modifier.size(15.dp)
                )
                Text(
                    text = if (isReminded) "REMINDER ON" else "NOTIFY ME",
                    color = if (isReminded) UpGold else UpTextWhite,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }

        HorizontalDivider(color = UpBorderGray.copy(alpha = 0.7f), thickness = 1.dp)

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(UpBgBlack),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 12.dp,
                bottom = navBarPadding.calculateBottomPadding() + 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item(key = "up_detail_header") {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "${event.eventCode} • ${event.bouts.size * 2} FIGHTERS LINEUP",
                            color = UpAccentRed,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.8.sp
                        )
                        Text(
                            text = event.title,
                            color = UpTextWhite,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = null,
                                tint = UpTextGray,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "${event.fullDateAndTime} • ${event.venue}",
                                color = UpTextGray,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(UpDeepRed, UpCardDark)
                                )
                            )
                            .border(1.dp, UpAccentRed.copy(alpha = 0.6f), RoundedCornerShape(10.dp))
                            .then(
                                if (onNavigateToFightPass != null) {
                                    Modifier.clickable { onNavigateToFightPass() }
                                } else {
                                    Modifier
                                }
                            )
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ConfirmationNumber,
                                contentDescription = null,
                                tint = UpAccentRed,
                                modifier = Modifier.size(18.dp)
                            )
                            Column {
                                Text(
                                    text = "BROADCAST & ACCESS",
                                    color = UpTextGray,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = event.ticketPriceLabel,
                                    color = UpTextWhite,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }
                        }

                        Text(
                            text = if (onNavigateToFightPass != null) "GET ACCESS ›" else "STARTS IN ${event.countdownText.substringBefore(":")}",
                            color = UpAccentRed,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }
            }

            items(event.bouts, key = { it.id }) { bout ->
                UpcomingMatchupCard(bout = bout)
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun UpcomingMatchupCard(bout: UpcomingBoutMatchup) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(UpCardDark)
            .border(
                width = if (bout.isTitleBout) 1.2.dp else 1.dp,
                color = if (bout.isTitleBout) UpGold.copy(alpha = 0.7f) else UpBorderGray,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = bout.cardSegment,
                color = if (bout.isTitleBout) UpGold else UpAccentRed,
                fontSize = 10.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 0.6.sp
            )
            Text(
                text = "${bout.weightClass} • ${bout.rounds}",
                color = UpTextGray,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FaceOffFighterMini(
                fighter = bout.redCorner,
                modifier = Modifier.weight(1f)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "VS",
                    color = UpTextWhite,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black
                )
            }

            FaceOffFighterMini(
                fighter = bout.blueCorner,
                modifier = Modifier.weight(1f)
            )
        }

        HorizontalDivider(color = Color.White.copy(alpha = 0.08f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(UpCardInner)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${bout.redCorner.height} / ${bout.redCorner.reach}",
                color = UpTextWhite,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "ӨНДӨР / АЛД",
                color = UpTextMuted,
                fontSize = 10.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "${bout.blueCorner.height} / ${bout.blueCorner.reach}",
                color = UpTextWhite,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun FaceOffFighterMini(
    fighter: UpcomingFighterProfile,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            fighter.cornerColor.copy(alpha = 0.35f),
                            UpCardInner
                        )
                    )
                )
                .border(1.5.dp, fighter.cornerColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = fighter.name,
                tint = UpTextWhite,
                modifier = Modifier.size(36.dp)
            )
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(fighter.cornerColor.copy(alpha = 0.2f))
                .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
            Text(
                text = fighter.rankBadge,
                color = UpTextWhite,
                fontSize = 8.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        Text(
            text = fighter.name,
            color = UpTextWhite,
            fontSize = 13.sp,
            fontWeight = FontWeight.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Text(
            text = fighter.record,
            color = UpTextGray,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}