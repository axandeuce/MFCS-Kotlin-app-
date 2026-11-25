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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val ResBgBlack = Color(0xFF000000)
private val ResCardDark = Color(0xFF141414)
private val ResCardInner = Color(0xFF1D1D1D)
private val ResAccentRed = Color(0xFFE50914)
private val ResDeepRed = Color(0xFF4A070B)
private val ResTextWhite = Color(0xFFFFFFFF)
private val ResTextGray = Color(0xFFA6A6A6)
private val ResTextMuted = Color(0xFF6E6E6E)
private val ResBorderGray = Color(0xFF262626)

data class ResultFighterStats(
    val name: String,
    val record: String,
    val cornerColor: Color,
    val totalStrikes: Int,
    val sigStrikes: Int,
    val knockdowns: Int,
    val takedowns: Int,
    val controlTime: String
)

data class ResultBoutDetail(
    val id: String,
    val cardSegment: String,
    val weightClass: String,
    val method: String,
    val roundAndTime: String,
    val judgesNote: String,
    val winner: ResultFighterStats,
    val loser: ResultFighterStats
)

data class ResultEventPackage(
    val id: String,
    val eventNumber: String,
    val title: String,
    val date: String,
    val venue: String,
    val mainEventHeadline: String,
    val mainEventResult: String,
    val bouts: List<ResultBoutDetail>
)

private val mfcs11FourteenFightersBouts = listOf(
    ResultBoutDetail(
        id = "b1_1",
        cardSegment = "MAIN EVENT • TITLE BOUT",
        weightClass = "LIGHTWEIGHT • 70 KG",
        method = "TKO (HEAD KICK & PUNCHES)",
        roundAndTime = "R2 • 03:18",
        judgesNote = "Referee Stoppage",
        winner = ResultFighterStats("ДЭЛГЭРМӨРӨН", "14-2-0", Color(0xFFE50914), 78, 64, 2, 2, "02:45"),
        loser = ResultFighterStats("ТЭМҮҮЖИН", "11-3-0", Color(0xFF3B82F6), 41, 31, 0, 1, "01:12")
    ),
    ResultBoutDetail(
        id = "b1_2",
        cardSegment = "CO-MAIN EVENT",
        weightClass = "WELTERWEIGHT • 77 KG",
        method = "SUBMISSION (REAR NAKED CHOKE)",
        roundAndTime = "R1 • 04:05",
        judgesNote = "Tapout",
        winner = ResultFighterStats("БАТ-ОРГИЛ", "12-1-0", Color(0xFFE50914), 29, 22, 0, 3, "03:10"),
        loser = ResultFighterStats("АНХБАЯР", "9-4-0", Color(0xFF3B82F6), 24, 19, 0, 0, "00:18")
    ),
    ResultBoutDetail(
        id = "b1_3",
        cardSegment = "MAIN CARD",
        weightClass = "MIDDLEWEIGHT • 84 KG",
        method = "UNANIMOUS DECISION",
        roundAndTime = "R3 • 05:00",
        judgesNote = "29-28, 29-28, 30-27",
        winner = ResultFighterStats("ЗОЛБОО", "10-2-0", Color(0xFFE50914), 112, 88, 1, 4, "05:32"),
        loser = ResultFighterStats("ДӨЛГӨӨН", "8-3-0", Color(0xFF3B82F6), 94, 79, 0, 2, "02:14")
    ),
    ResultBoutDetail(
        id = "b1_4",
        cardSegment = "MAIN CARD",
        weightClass = "FEATHERWEIGHT • 66 KG",
        method = "KO (LEFT CROSS)",
        roundAndTime = "R1 • 02:29",
        judgesNote = "Knockout of the Night",
        winner = ResultFighterStats("МӨНХ-ЭРДЭНЭ", "15-4-0", Color(0xFFE50914), 33, 25, 1, 1, "00:48"),
        loser = ResultFighterStats("ТӨГӨЛДӨР", "10-2-0", Color(0xFF3B82F6), 19, 14, 0, 0, "00:12")
    ),
    ResultBoutDetail(
        id = "b1_5",
        cardSegment = "PRELIMS",
        weightClass = "BANTAMWEIGHT • 61 KG",
        method = "SPLIT DECISION",
        roundAndTime = "R3 • 05:00",
        judgesNote = "29-28, 28-29, 29-28",
        winner = ResultFighterStats("БЯМБАДОРЖ", "9-1-0", Color(0xFFE50914), 96, 74, 0, 5, "06:10"),
        loser = ResultFighterStats("ЭНХТҮВШИН", "11-5-0", Color(0xFF3B82F6), 101, 77, 1, 1, "01:45")
    ),
    ResultBoutDetail(
        id = "b1_6",
        cardSegment = "PRELIMS",
        weightClass = "LIGHT HEAVYWEIGHT • 93 KG",
        method = "TKO (GROUND & POUND)",
        roundAndTime = "R2 • 01:54",
        judgesNote = "Referee Stoppage",
        winner = ResultFighterStats("АЛТАНХУЯГ", "16-3-0", Color(0xFFE50914), 61, 49, 2, 2, "03:20"),
        loser = ResultFighterStats("ЦОГТБААТАР", "12-5-0", Color(0xFF3B82F6), 34, 23, 0, 1, "00:55")
    ),
    ResultBoutDetail(
        id = "b1_7",
        cardSegment = "PRELIMS • OPENER",
        weightClass = "FLYWEIGHT • 57 KG",
        method = "SUBMISSION (ARM-TRIANGLE)",
        roundAndTime = "R2 • 03:41",
        judgesNote = "Technical Submission",
        winner = ResultFighterStats("ЭРДЭНЭБОЛД", "7-1-0", Color(0xFFE50914), 52, 38, 0, 3, "04:18"),
        loser = ResultFighterStats("МӨНХБАТ", "6-2-0", Color(0xFF3B82F6), 44, 31, 0, 1, "01:36")
    )
)

private val lastFiveEventsData = listOf(
    ResultEventPackage(
        id = "ev_1",
        eventNumber = "MFCS 11",
        title = "ULAANBAATAR SHOWDOWN",
        date = "NOV 14, 2026",
        venue = "Steppe Arena • Ulaanbaatar",
        mainEventHeadline = "ДЭЛГЭРМӨРӨН def. ТЭМҮҮЖИН",
        mainEventResult = "TKO (Head Kick) • R2 03:18",
        bouts = mfcs11FourteenFightersBouts
    ),
    ResultEventPackage(
        id = "ev_2",
        eventNumber = "NOMAD 13",
        title = "NOMAD FIGHT NIGHT 13",
        date = "NOV 02, 2026",
        venue = "ASA Arena • Ulaanbaatar",
        mainEventHeadline = "МӨНХ-ЭРДЭНЭ def. ТӨГӨЛДӨР",
        mainEventResult = "KO (Right Hook) • R1 01:47",
        bouts = mfcs11FourteenFightersBouts.mapIndexed { idx, bout ->
            bout.copy(id = "b2_${idx + 1}")
        }
    ),
    ResultEventPackage(
        id = "ev_3",
        eventNumber = "MFCS 10",
        title = "STEPPE WARRIORS CHAMPIONSHIP",
        date = "OCT 24, 2026",
        venue = "Buyant-Ukhaa Palace • UB",
        mainEventHeadline = "ГАНЗОРИГ def. ОДБАЯР",
        mainEventResult = "SUBMISSION (ARM-TRIANGLE) • R3 02:51",
        bouts = mfcs11FourteenFightersBouts.mapIndexed { idx, bout ->
            bout.copy(id = "b3_${idx + 1}")
        }
    ),
    ResultEventPackage(
        id = "ev_4",
        eventNumber = "DARKHAN 04",
        title = "DARKHAN COMBAT NIGHT",
        date = "OCT 10, 2026",
        venue = "Darkhan Sports Complex",
        mainEventHeadline = "ТҮВШИНЖАРГАЛ def. БАЯРСАЙХАН",
        mainEventResult = "UNANIMOUS DECISION • R3 05:00",
        bouts = mfcs11FourteenFightersBouts.mapIndexed { idx, bout ->
            bout.copy(id = "b4_${idx + 1}")
        }
    ),
    ResultEventPackage(
        id = "ev_5",
        eventNumber = "MFCS 09",
        title = "AUTUMN BRAWL: ERDENET",
        date = "SEP 26, 2026",
        venue = "Khangarid Palace • Erdenet",
        mainEventHeadline = "АЛТАНХУЯГ def. ЦОГТБААТАР",
        mainEventResult = "KO (FLYING KNEE) • R1 00:54",
        bouts = mfcs11FourteenFightersBouts.mapIndexed { idx, bout ->
            bout.copy(id = "b5_${idx + 1}")
        }
    )
)

@Composable
fun MfcsResultsScreen() {
    var selectedEvent by remember { mutableStateOf<ResultEventPackage?>(null) }

    BackHandler(enabled = selectedEvent != null) {
        selectedEvent = null
    }

    AnimatedContent(
        targetState = selectedEvent,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "results_event_transition"
    ) { currentEvent ->
        if (currentEvent == null) {
            EventPackagesListView(
                events = lastFiveEventsData,
                onSelectEvent = { selectedEvent = it }
            )
        } else {
            EventBoutsDetailView(
                event = currentEvent,
                onBack = { selectedEvent = null }
            )
        }
    }
}

@Composable
private fun EventPackagesListView(
    events: List<ResultEventPackage>,
    onSelectEvent: (ResultEventPackage) -> Unit
) {
    val navBarPadding = WindowInsets.navigationBars.asPaddingValues()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ResBgBlack)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(ResBgBlack)
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = "EVENT ARCHIVE & SCORECARDS",
                color = ResAccentRed,
                fontSize = 10.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
            )
            Text(
                text = "PAST 5 EVENTS",
                color = ResTextWhite,
                fontSize = 21.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 0.5.sp
            )
        }

        HorizontalDivider(color = ResBorderGray.copy(alpha = 0.7f), thickness = 1.dp)

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(ResBgBlack),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 12.dp,
                bottom = navBarPadding.calculateBottomPadding() + 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(events.indices.toList(), key = { events[it].id }) { index ->
                val event = events[index]
                EventSummaryPackageCard(
                    event = event,
                    isLatest = index == 0,
                    onClick = { onSelectEvent(event) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun EventSummaryPackageCard(
    event: ResultEventPackage,
    isLatest: Boolean,
    onClick: () -> Unit
) {
    val fighterCount = event.bouts.size * 2
    val cardModifier = if (isLatest) {
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(ResDeepRed, ResCardDark, ResBgBlack)
                )
            )
            .border(1.dp, ResAccentRed, RoundedCornerShape(14.dp))
    } else {
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(ResCardDark)
            .border(1.dp, ResBorderGray, RoundedCornerShape(14.dp))
    }

    Column(
        modifier = cardModifier
            .clickable { onClick() }
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(if (isLatest) ResAccentRed else ResCardInner)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = event.eventNumber,
                        color = ResTextWhite,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black
                    )
                }

                if (isLatest) {
                    Text(
                        text = "LATEST EVENT",
                        color = ResAccentRed,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.6.sp
                    )
                }
            }

            Text(
                text = event.date,
                color = ResTextGray,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Text(
            text = event.title,
            color = ResTextWhite,
            fontSize = 18.sp,
            fontWeight = FontWeight.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = ResTextGray,
                modifier = Modifier.size(13.dp)
            )
            Text(
                text = event.venue,
                color = ResTextGray,
                fontSize = 12.sp
            )
        }

        HorizontalDivider(color = Color.White.copy(alpha = 0.08f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = event.mainEventHeadline,
                    color = ResTextWhite,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${event.mainEventResult}  •  ${event.bouts.size} BOUTS ($fighterCount FIGHTERS)",
                    color = ResAccentRed,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "STATS",
                    color = ResTextWhite,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = ResAccentRed,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun EventBoutsDetailView(
    event: ResultEventPackage,
    onBack: () -> Unit
) {
    val totalFighters = event.bouts.size * 2
    val navBarPadding = WindowInsets.navigationBars.asPaddingValues()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ResBgBlack)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(ResBgBlack)
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(ResCardDark)
                    .border(1.dp, ResBorderGray, RoundedCornerShape(8.dp))
                    .clickable { onBack() }
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = ResTextWhite,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "ALL 5 EVENTS",
                    color = ResTextWhite,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }

        HorizontalDivider(color = ResBorderGray.copy(alpha = 0.7f), thickness = 1.dp)

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(ResBgBlack),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 12.dp,
                bottom = navBarPadding.calculateBottomPadding() + 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item(key = "detail_event_meta") {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "${event.eventNumber} • ${event.date} • $totalFighters FIGHTERS",
                        color = ResAccentRed,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.8.sp
                    )
                    Text(
                        text = event.title,
                        color = ResTextWhite,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = event.venue,
                        color = ResTextGray,
                        fontSize = 12.sp
                    )
                }
            }

            items(event.bouts, key = { it.id }) { bout ->
                FullBoutScorecard(bout = bout)
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun FullBoutScorecard(bout: ResultBoutDetail) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(ResCardDark)
            .border(1.dp, ResBorderGray, RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = bout.cardSegment,
                color = ResAccentRed,
                fontSize = 10.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 0.6.sp
            )
            Text(
                text = bout.weightClass,
                color = ResTextGray,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FighterAvatarColumn(
                fighter = bout.winner,
                isWinner = true,
                modifier = Modifier.weight(1f)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "VS",
                    color = ResTextMuted,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = bout.roundAndTime,
                    color = ResTextWhite,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            FighterAvatarColumn(
                fighter = bout.loser,
                isWinner = false,
                modifier = Modifier.weight(1f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(ResDeepRed, ResCardInner)
                    )
                )
                .border(1.dp, ResAccentRed.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = null,
                    tint = ResAccentRed,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = "WINNER BY ${bout.method}",
                    color = ResTextWhite,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )
            }
            Text(
                text = "${bout.roundAndTime}  •  ${bout.judgesNote}",
                color = ResTextGray,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(ResCardInner)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "FIGHT ANALYTICS (ЦОХИЛТ & УНАГАЛТ)",
                color = ResTextGray,
                fontSize = 10.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.6.sp
            )

            StatComparisonBarRow(
                label = "НИЙТ ЦОХИЛТ (TOTAL STRIKES)",
                leftValue = bout.winner.totalStrikes,
                rightValue = bout.loser.totalStrikes
            )
            StatComparisonBarRow(
                label = "ХҮЧТЭЙ ЦОХИЛТ (SIG. STRIKES)",
                leftValue = bout.winner.sigStrikes,
                rightValue = bout.loser.sigStrikes
            )
            StatComparisonBarRow(
                label = "ЦОХИЛТООР УНАГАСАН (KNOCKDOWNS)",
                leftValue = bout.winner.knockdowns,
                rightValue = bout.loser.knockdowns
            )
            StatComparisonBarRow(
                label = "БӨХЧИЛЖ УНАГАСАН (TAKEDOWNS)",
                leftValue = bout.winner.takedowns,
                rightValue = bout.loser.takedowns
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = bout.winner.controlTime,
                    color = ResTextWhite,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "ХЯНАЛТЫН ХУГАЦАА (CONTROL)",
                    color = ResTextGray,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = bout.loser.controlTime,
                    color = ResTextGray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun FighterAvatarColumn(
    fighter: ResultFighterStats,
    isWinner: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(contentAlignment = Alignment.BottomCenter) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                fighter.cornerColor.copy(alpha = 0.35f),
                                ResCardInner
                            )
                        )
                    )
                    .border(
                        width = if (isWinner) 2.5.dp else 1.5.dp,
                        color = if (isWinner) ResAccentRed else ResBorderGray,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = fighter.name,
                    tint = if (isWinner) ResTextWhite else ResTextGray,
                    modifier = Modifier.size(40.dp)
                )
            }

            if (isWinner) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(ResAccentRed)
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "WIN",
                        color = ResTextWhite,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }

        Text(
            text = fighter.name,
            color = if (isWinner) ResTextWhite else ResTextGray,
            fontSize = 13.sp,
            fontWeight = if (isWinner) FontWeight.Black else FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )

        Text(
            text = fighter.record,
            color = ResTextMuted,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun StatComparisonBarRow(
    label: String,
    leftValue: Int,
    rightValue: Int
) {
    val total = (leftValue + rightValue).coerceAtLeast(1)
    val leftFraction = (leftValue.toFloat() / total.toFloat()).coerceIn(0.08f, 0.92f)
    val rightFraction = (rightValue.toFloat() / total.toFloat()).coerceIn(0.08f, 0.92f)

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = leftValue.toString(),
                color = ResTextWhite,
                fontSize = 12.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text = label,
                color = ResTextGray,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = rightValue.toString(),
                color = ResTextGray,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(ResBgBlack),
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(leftFraction)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(3.dp))
                    .background(ResAccentRed)
            )
            Box(
                modifier = Modifier
                    .weight(rightFraction)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(3.dp))
                    .background(Color(0xFF4A4A4A))
            )
        }
    }
}