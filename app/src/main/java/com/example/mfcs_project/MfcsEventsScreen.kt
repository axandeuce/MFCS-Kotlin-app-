package com.example.mfcs_project

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Cast
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.SportsMma
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

private val BgBlack = Color(0xFF000000)
private val CardDark = Color(0xFF161616)
private val DrawerDark = Color(0xFF121212)
private val AccentRed = Color(0xFFE50914)
private val TextWhite = Color(0xFFFFFFFF)
private val TextGray = Color(0xFFB0B0B0)

data class FightEventItem(
    val id: String,
    val title: String,
    val promotion: String,
    val dateLabel: String,
    val timeLabel: String,
    val isPastOrLocked: Boolean,
    val startsInSeconds: Long,
    val accentColor: Color
)

data class EventDateGroup(
    val sectionHeader: String,
    val isPastSection: Boolean = false,
    val events: List<FightEventItem>
)

data class LiveStreamItem(
    val id: String,
    val category: String,
    val title: String,
    val scheduleLabel: String,
    val accentColor: Color
)

enum class BottomNavTab(val label: String, val icon: ImageVector) {
    UPCOMING("UPCOMING", Icons.Default.CalendarMonth),
    RESULTS("RESULTS", Icons.Default.SportsMma),
    FIGHT_PASS("FIGHT PASS", Icons.Default.PlayCircle),
    NEWS("NEWS", Icons.Default.Newspaper),
    ATHLETES("ATHLETES", Icons.Default.Person)
}

private val drawerMenuItems = listOf(
    "SEARCH",
    "FIGHT LIBRARY",
    "FIGHT PASS SCHEDULE",
    "FIGHT PROMOTIONS",
    "BRAZILIAN JIU-JITSU",
    "SHOWS",
    "CAN'T MISS",
    "HISTORY",
    "MFCS STORE",
    "PREFERENCES"
)

private val sampleEventGroups = listOf(
    EventDateGroup(
        sectionHeader = "FRIDAY, NOV 20 • NEXT 7 DAYS",
        events = listOf(
            FightEventItem(
                id = "1",
                title = "MFCS PRO SERIES: ULAANBAATAR",
                promotion = "MFCS",
                dateLabel = "11/20/26",
                timeLabel = "8:00 PM",
                isPastOrLocked = false,
                startsInSeconds = 25_400L,
                accentColor = Color(0xFF8B0000)
            ),
            FightEventItem(
                id = "2",
                title = "NOMAD FIGHT NIGHT 14",
                promotion = "NOMAD FC",
                dateLabel = "11/20/26",
                timeLabel = "10:30 PM",
                isPastOrLocked = false,
                startsInSeconds = 34_400L,
                accentColor = Color(0xFF2B2D42)
            )
        )
    ),
    EventDateGroup(
        sectionHeader = "SATURDAY, NOV 21",
        events = listOf(
            FightEventItem(
                id = "3",
                title = "MGL KICKBOXING GRAND PRIX",
                promotion = "MGL KICK",
                dateLabel = "11/21/26",
                timeLabel = "6:00 PM",
                isPastOrLocked = true,
                startsInSeconds = 80_000L,
                accentColor = Color(0xFF4A1515)
            ),
            FightEventItem(
                id = "4",
                title = "STEPPE WARRIORS CHAMPIONSHIP",
                promotion = "STEPPE MMA",
                dateLabel = "11/21/26",
                timeLabel = "9:00 PM",
                isPastOrLocked = false,
                startsInSeconds = 92_000L,
                accentColor = Color(0xFF1F2937)
            ),
            FightEventItem(
                id = "5",
                title = "KHAN GRAPPLING OPEN",
                promotion = "KHAN BJJ",
                dateLabel = "11/21/26",
                timeLabel = "11:00 PM",
                isPastOrLocked = true,
                startsInSeconds = 100_000L,
                accentColor = Color(0xFF3B1C1C)
            )
        )
    ),
    EventDateGroup(
        sectionHeader = "SUNDAY, NOV 22",
        events = listOf(
            FightEventItem(
                id = "6",
                title = "MFCS FINALS: HEAVYWEIGHT CLASH",
                promotion = "MFCS",
                dateLabel = "11/22/26",
                timeLabel = "7:30 PM",
                isPastOrLocked = false,
                startsInSeconds = 165_000L,
                accentColor = Color(0xFF6E0D12)
            ),
            FightEventItem(
                id = "7",
                title = "DARKHAN COMBAT NIGHT",
                promotion = "DARKHAN FC",
                dateLabel = "11/22/26",
                timeLabel = "9:30 PM",
                isPastOrLocked = true,
                startsInSeconds = 172_000L,
                accentColor = Color(0xFF262626)
            )
        )
    ),
    EventDateGroup(
        sectionHeader = "PAST EVENTS • LAST 3 MONTHS (OCT 2026)",
        isPastSection = true,
        events = listOf(
            FightEventItem(
                id = "101",
                title = "MFCS 09: CAPITAL SHOWDOWN",
                promotion = "MFCS",
                dateLabel = "10/18/26",
                timeLabel = "REPLAY",
                isPastOrLocked = true,
                startsInSeconds = 0L,
                accentColor = Color(0xFF3A1010)
            ),
            FightEventItem(
                id = "102",
                title = "ERDENET STRIKE LEAGUE 07",
                promotion = "ERDENET FC",
                dateLabel = "10/04/26",
                timeLabel = "REPLAY",
                isPastOrLocked = true,
                startsInSeconds = 0L,
                accentColor = Color(0xFF1C252C)
            )
        )
    ),
    EventDateGroup(
        sectionHeader = "PAST EVENTS • LAST 3 MONTHS (AUG – SEP 2026)",
        isPastSection = true,
        events = listOf(
            FightEventItem(
                id = "103",
                title = "MFCS SUMMER CAGE INVITATIONAL",
                promotion = "MFCS",
                dateLabel = "09/12/26",
                timeLabel = "REPLAY",
                isPastOrLocked = true,
                startsInSeconds = 0L,
                accentColor = Color(0xFF451212)
            ),
            FightEventItem(
                id = "104",
                title = "GOBI FIGHT NIGHT 05",
                promotion = "GOBI MMA",
                dateLabel = "08/29/26",
                timeLabel = "REPLAY",
                isPastOrLocked = true,
                startsInSeconds = 0L,
                accentColor = Color(0xFF232323)
            )
        )
    )
)

private val sampleLiveStreams = listOf(
    LiveStreamItem(
        id = "live_1",
        category = "MFCS Live 24/7",
        title = "MFCS Ulaanbaatar: Main Card & Weigh-In",
        scheduleLabel = "1:00 PM - 5:00 PM",
        accentColor = Color(0xFF7A1212)
    ),
    LiveStreamItem(
        id = "live_2",
        category = "Mongol MMA Promotions",
        title = "Nomad FC vs Steppe MMA Prelims",
        scheduleLabel = "5:15 PM - 8:30 PM",
        accentColor = Color(0xFF1E293B)
    )
)

@Composable
fun MfcsEventsScreen() {
    var selectedTab by remember { mutableStateOf(BottomNavTab.UPCOMING) }
    var isAuthenticated by remember { mutableStateOf(false) }
    var selectedPassTier by remember { mutableStateOf(FightPassTier.NONE) }

    val hasFullAccess = isAuthenticated || selectedPassTier != FightPassTier.NONE

    var showBurgerMenu by remember { mutableStateOf(false) }
    var showCastSheet by remember { mutableStateOf(false) }
    var selectedCastIndex by remember { mutableIntStateOf(0) }
    var showLiveScreen by remember { mutableStateOf(false) }
    var activeCountdownEvent by remember { mutableStateOf<FightEventItem?>(null) }

    var showAuthModal by remember { mutableStateOf(false) }
    var authStartInLoginMode by remember { mutableStateOf(true) }
    var pendingLiveOpenAfterAuth by remember { mutableStateOf(false) }

    fun toggleBurgerMenu() {
        val nextState = !showBurgerMenu
        showBurgerMenu = nextState
        if (nextState) {
            showCastSheet = false
            showLiveScreen = false
            activeCountdownEvent = null
        }
    }

    fun toggleCastSheet() {
        val nextState = !showCastSheet
        showCastSheet = nextState
        if (nextState) {
            showBurgerMenu = false
            showLiveScreen = false
            activeCountdownEvent = null
        }
    }

    fun toggleLiveScreen() {
        val nextState = !showLiveScreen
        showLiveScreen = nextState
        if (nextState) {
            showBurgerMenu = false
            showCastSheet = false
            activeCountdownEvent = null
        }
    }

    Scaffold(
        containerColor = BgBlack,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BgBlack)
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { toggleBurgerMenu() },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = if (showBurgerMenu) AccentRed else TextWhite,
                        modifier = Modifier.size(26.dp)
                    )
                }

                Text(
                    text = "MFCS",
                    color = TextWhite,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 3.sp
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = { toggleCastSheet() },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Cast,
                            contentDescription = "Cast Devices",
                            tint = if (showCastSheet) AccentRed else TextWhite,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(if (showLiveScreen) TextWhite else AccentRed)
                            .clickable { toggleLiveScreen() }
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = "LIVE (2)",
                            color = if (showLiveScreen) BgBlack else TextWhite,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF0C0C0C),
                contentColor = TextWhite
            ) {
                BottomNavTab.entries.forEach { tab ->
                    val isSelected = selectedTab == tab
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            selectedTab = tab
                            showBurgerMenu = false
                            showCastSheet = false
                            showLiveScreen = false
                        },
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.label,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        label = {
                            Text(
                                text = tab.label,
                                fontSize = 9.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AccentRed,
                            selectedTextColor = AccentRed,
                            unselectedIconColor = TextGray,
                            unselectedTextColor = TextGray,
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(BgBlack)
        ) {
            when (selectedTab) {
                BottomNavTab.UPCOMING -> {
                    UpcomingEventsList(
                        groups = sampleEventGroups,
                        isAuthenticated = hasFullAccess,
                        onEventClick = { event ->
                            showBurgerMenu = false
                            showCastSheet = false
                            showLiveScreen = false
                            if (event.isPastOrLocked && !hasFullAccess) {
                                authStartInLoginMode = false
                                pendingLiveOpenAfterAuth = false
                                showAuthModal = true
                            } else {
                                activeCountdownEvent = event
                            }
                        }
                    )
                }

                BottomNavTab.RESULTS -> {
                    MfcsResultsScreen()
                }

                BottomNavTab.FIGHT_PASS -> {
                    MfcsFightPassScreen(
                        selectedTier = selectedPassTier,
                        onSelectTier = { newTier ->
                            selectedPassTier = newTier
                            if (newTier != FightPassTier.NONE) {
                                isAuthenticated = true
                            }
                        }
                    )
                }

                BottomNavTab.NEWS -> {
                    MfcsNewsScreen()
                }

                BottomNavTab.ATHLETES -> {
                    MfcsAthletesScreen()
                }
            }

            if (showLiveScreen) {
                LiveStreamsScreen(
                    streams = sampleLiveStreams,
                    isUnlocked = hasFullAccess,
                    onBack = { showLiveScreen = false },
                    onLockedStreamClick = {
                        authStartInLoginMode = false
                        pendingLiveOpenAfterAuth = true
                        showAuthModal = true
                    }
                )
            }

            activeCountdownEvent?.let { event ->
                EventCountdownOverlay(
                    event = event,
                    onClose = { activeCountdownEvent = null }
                )
            }

            if (showCastSheet) {
                CastDevicesSheet(
                    selectedIndex = selectedCastIndex,
                    onSelectIndex = { selectedCastIndex = it },
                    onClose = { showCastSheet = false }
                )
            }

            AnimatedVisibility(
                visible = showBurgerMenu,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.65f))
                        .clickable { showBurgerMenu = false }
                )
            }

            AnimatedVisibility(
                visible = showBurgerMenu,
                enter = slideInHorizontally(initialOffsetX = { -it }),
                exit = slideOutHorizontally(targetOffsetX = { -it })
            ) {
                BurgerDrawerMenu(
                    isAuthenticated = isAuthenticated,
                    onItemClick = { showBurgerMenu = false },
                    onLoginClick = {
                        showBurgerMenu = false
                        if (isAuthenticated) {
                            isAuthenticated = false
                            selectedPassTier = FightPassTier.NONE
                        } else {
                            authStartInLoginMode = true
                            pendingLiveOpenAfterAuth = false
                            showAuthModal = true
                        }
                    },
                    onClose = { showBurgerMenu = false }
                )
            }

            if (showAuthModal) {
                AuthOverlayScreen(
                    initialLoginMode = authStartInLoginMode,
                    onAuthSuccess = {
                        isAuthenticated = true
                        showAuthModal = false
                        if (pendingLiveOpenAfterAuth) {
                            pendingLiveOpenAfterAuth = false
                            showLiveScreen = true
                        }
                    },
                    onClose = {
                        pendingLiveOpenAfterAuth = false
                        showAuthModal = false
                    }
                )
            }
        }
    }
}

@Composable
private fun UpcomingEventsList(
    groups: List<EventDateGroup>,
    isAuthenticated: Boolean,
    onEventClick: (FightEventItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 8.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(groups, key = { it.sectionHeader }) { group ->
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = group.sectionHeader,
                    color = if (group.isPastSection) TextGray else TextWhite,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(group.events, key = { it.id }) { event ->
                        HorizontalEventCard(
                            event = event,
                            isUnlocked = !event.isPastOrLocked || isAuthenticated,
                            onClick = { onEventClick(event) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HorizontalEventCard(
    event: FightEventItem,
    isUnlocked: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(235.dp)
            .height(145.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(event.accentColor, CardDark, BgBlack)
                )
            )
            .border(1.dp, Color(0xFF262626), RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = event.promotion,
                color = AccentRed,
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "${event.timeLabel}, ${event.dateLabel}",
                color = TextWhite,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.Black.copy(alpha = 0.65f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }

        if (event.isPastOrLocked) {
            Icon(
                imageVector = if (isUnlocked) Icons.Default.LockOpen else Icons.Default.Lock,
                contentDescription = if (isUnlocked) "Unlocked" else "Locked",
                tint = TextWhite.copy(alpha = 0.9f),
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(26.dp)
            )
        }

        Text(
            text = event.title,
            color = TextWhite,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
        )
    }
}

@Composable
private fun LiveStreamsScreen(
    streams: List<LiveStreamItem>,
    isUnlocked: Boolean,
    onBack: () -> Unit,
    onLockedStreamClick: () -> Unit
) {
    var selectedUnlockedStream by remember { mutableStateOf<LiveStreamItem?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgBlack)
            .statusBarsPadding()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = TextWhite
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "LIVE",
                color = TextWhite,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.5.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(48.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!isUnlocked) {
            Text(
                text = "Бүртгэл үүсгэсний дараа LIVE дамжуулалтын түгжээ нээгдэнэ.",
                color = TextGray,
                fontSize = 12.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        streams.forEach { stream ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(CardDark)
                    .clickable {
                        if (isUnlocked) {
                            selectedUnlockedStream = stream
                        } else {
                            onLockedStreamClick()
                        }
                    }
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(124.dp)
                        .height(74.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(stream.accentColor, BgBlack)
                            )
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(6.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(AccentRed)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "Live",
                            color = TextWhite,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Icon(
                        imageVector = if (isUnlocked) Icons.Default.LockOpen else Icons.Default.Lock,
                        contentDescription = null,
                        tint = TextWhite,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stream.category,
                        color = TextGray,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = stream.title,
                        color = TextWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = stream.scheduleLabel,
                        color = TextGray,
                        fontSize = 11.sp
                    )
                }
            }
        }

        selectedUnlockedStream?.let { activeStream ->
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF1B1B1B))
                    .border(1.dp, AccentRed, RoundedCornerShape(8.dp))
                    .padding(14.dp)
            ) {
                Column {
                    Text(
                        text = "NOW PLAYING (UNLOCKED)",
                        color = AccentRed,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = activeStream.title,
                        color = TextWhite,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "MFCS Live Stream • ${activeStream.scheduleLabel}",
                        color = TextGray,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun CastDevicesSheet(
    selectedIndex: Int,
    onSelectIndex: (Int) -> Unit,
    onClose: () -> Unit
) {
    val castOptions = listOf(
        Triple("Android Phone", Icons.Default.Smartphone, 0),
        Triple("Living Room TV", Icons.Default.Tv, 1),
        Triple("Screen Mirror / Cast", Icons.Default.Cast, 2)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.72f))
            .clickable { onClose() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp))
                .background(DrawerDark)
                .clickable(enabled = false, onClick = {})
                .padding(horizontal = 20.dp, vertical = 18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onClose, modifier = Modifier.size(32.dp)) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = TextWhite
                    )
                }
                Text(
                    text = "Cast Devices",
                    color = TextWhite,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(32.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            castOptions.forEach { (title, icon, idx) ->
                val isSelected = selectedIndex == idx
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectIndex(idx) }
                        .padding(vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = TextWhite,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = title,
                        color = TextWhite,
                        fontSize = 15.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier.weight(1f)
                    )
                    if (isSelected) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Selected",
                            tint = TextWhite,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                if (idx < castOptions.lastIndex) {
                    HorizontalDivider(color = Color(0xFF242424))
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun BurgerDrawerMenu(
    isAuthenticated: Boolean,
    onItemClick: (String) -> Unit,
    onLoginClick: () -> Unit,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.76f)
            .background(DrawerDark)
            .statusBarsPadding()
            .clickable(enabled = false, onClick = {})
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "MFCS",
                    color = AccentRed,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp
                )
                IconButton(onClick = onClose, modifier = Modifier.size(28.dp)) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Menu",
                        tint = TextGray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                items(drawerMenuItems) { menuTitle ->
                    val isSubtle = menuTitle == "PREFERENCES"
                    Text(
                        text = menuTitle,
                        color = if (isSubtle) TextGray else TextWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.6.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onItemClick(menuTitle) }
                            .padding(vertical = 2.dp)
                    )
                }
            }
        }

        Column {
            HorizontalDivider(color = Color(0xFF262626), modifier = Modifier.padding(bottom = 12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isAuthenticated) "LOG OUT" else "LOGIN",
                    color = TextWhite,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp,
                    modifier = Modifier.clickable { onLoginClick() }
                )
                Text(
                    text = "20.0.0 (069)",
                    color = TextGray,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun AuthOverlayScreen(
    initialLoginMode: Boolean,
    onAuthSuccess: () -> Unit,
    onClose: () -> Unit
) {
    var isLoginMode by remember(initialLoginMode) { mutableStateOf(initialLoginMode) }
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1A1A1A), Color.Black.copy(alpha = 0.96f))
                )
            )
            .statusBarsPadding()
            .padding(20.dp)
    ) {
        IconButton(
            onClick = onClose,
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = TextWhite,
                modifier = Modifier.size(28.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "MFCS",
                color = TextWhite,
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 3.sp
            )
            Text(
                text = if (isLoginMode) "Login" else "Sign Up",
                color = TextWhite,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            if (!isLoginMode) {
                AuthInputField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    placeholder = "FULL NAME"
                )
            }

            AuthInputField(
                value = email,
                onValueChange = { email = it },
                placeholder = "EMAIL"
            )

            AuthInputField(
                value = password,
                onValueChange = { password = it },
                placeholder = if (isLoginMode) "PASSWORD" else "CREATE PASSWORD",
                isPassword = true
            )

            if (!isLoginMode) {
                AuthInputField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    placeholder = "CONFIRM PASSWORD",
                    isPassword = true
                )
            }

            Button(
                onClick = { onAuthSuccess() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF222222))
            ) {
                Text(
                    text = "SUBMIT",
                    color = TextWhite,
                    fontWeight = FontWeight.Bold
                )
            }

            if (isLoginMode) {
                TextButton(onClick = { /* Forgot password flow */ }) {
                    Text(
                        text = "Forgot password?",
                        color = TextWhite,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = if (isLoginMode) "Don't have an account? " else "Already have an account? ",
                    color = TextGray,
                    fontSize = 13.sp
                )
                Text(
                    text = if (isLoginMode) "SIGN UP" else "LOGIN",
                    color = TextWhite,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { isLoginMode = !isLoginMode }
                )
            }

            TextButton(onClick = onClose) {
                Text(text = "Cancel", color = TextGray, fontSize = 14.sp)
            }
        }
    }
}

@Composable
private fun AuthInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        placeholder = { Text(text = placeholder, color = TextGray, fontSize = 12.sp) },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = TextWhite,
            unfocusedTextColor = TextWhite,
            focusedContainerColor = Color(0xFF1A1A1A).copy(alpha = 0.85f),
            unfocusedContainerColor = Color(0xFF1A1A1A).copy(alpha = 0.75f),
            focusedBorderColor = TextWhite,
            unfocusedBorderColor = TextGray
        )
    )
}

@Composable
private fun EventCountdownOverlay(
    event: FightEventItem,
    onClose: () -> Unit
) {
    val context = LocalContext.current
    var remainingSeconds by remember(event.id) { mutableLongStateOf(event.startsInSeconds) }

    LaunchedEffect(event.id) {
        while (remainingSeconds > 0) {
            delay(1000L)
            remainingSeconds -= 1L
        }
    }

    val days = TimeUnit.SECONDS.toDays(remainingSeconds)
    val hours = TimeUnit.SECONDS.toHours(remainingSeconds) % 24
    val minutes = TimeUnit.SECONDS.toMinutes(remainingSeconds) % 60
    val seconds = remainingSeconds % 60

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(event.accentColor.copy(alpha = 0.85f), BgBlack, BgBlack)
                )
            )
            .statusBarsPadding()
            .padding(20.dp)
    ) {
        IconButton(
            onClick = onClose,
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = TextWhite,
                modifier = Modifier.size(28.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "EVENT STARTS IN",
                color = TextWhite,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )
            Spacer(modifier = Modifier.height(14.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CountdownUnitBox(value = days, label = "DAYS")
                CountdownUnitBox(value = hours, label = "HOURS")
                CountdownUnitBox(value = minutes, label = "MINUTES")
                CountdownUnitBox(value = seconds, label = "SECONDS")
            }

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = event.title,
                color = TextWhite,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${event.promotion} • ${event.dateLabel} • ${event.timeLabel}",
                color = TextGray,
                fontSize = 13.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, "MFCS: ${event.title} (${event.dateLabel})")
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Share Event"))
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(46.dp),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CardDark)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = null,
                        tint = TextWhite,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Share", color = TextWhite)
                }

                Button(
                    onClick = {
                        val calIntent = Intent(Intent.ACTION_INSERT).apply {
                            data = android.provider.CalendarContract.Events.CONTENT_URI
                            putExtra(android.provider.CalendarContract.Events.TITLE, event.title)
                        }
                        runCatching { context.startActivity(calIntent) }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(46.dp),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CardDark)
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = null,
                        tint = TextWhite,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Add to Calendar", color = TextWhite, maxLines = 1)
                }
            }
        }
    }
}

@Composable
private fun CountdownUnitBox(value: Long, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = String.format("%02d", value),
            color = TextWhite,
            fontSize = 30.sp,
            fontWeight = FontWeight.Black
        )
        Text(
            text = label,
            color = TextGray,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}