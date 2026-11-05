package com.example.mfcs_mongolianfightingchampionshipstatus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mfcs_mongolianfightingchampionshipstatus.ui.theme.MFCSMongolianFightingChampionshipStatusTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MFCSMongolianFightingChampionshipStatusTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    MainAppNavigator()
                }
            }
        }
    }
}

@Composable
fun MainAppNavigator() {
    var currentScreen by remember { mutableStateOf("splash") }
    var selectedEvent by remember { mutableStateOf<String?>(null) }
    var selectedImageRes by remember { mutableStateOf(R.drawable.fight_poster_1) }
    var selectedTimerSeconds by remember { mutableStateOf(604800L) }
    var selectedResultTitle by remember { mutableStateOf("MFCS 333") }

    when (currentScreen) {
        "splash" -> SplashScreen(
            onTimeout = { currentScreen = "home" }
        )
        else -> {
            Scaffold(
                bottomBar = {
                    MfcsBottomBar(
                        currentTab = currentScreen,
                        onTabSelected = { tab ->
                            currentScreen = tab
                        }
                    )
                },
                containerColor = Color.Black
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    when (currentScreen) {
                        "home" -> HomeScreen(
                            onEventClick = { eventTitle, imageRes, timerSeconds ->
                                selectedEvent = eventTitle
                                selectedImageRes = imageRes
                                selectedTimerSeconds = timerSeconds
                                currentScreen = "detail"
                            },
                            onLiveClick = { currentScreen = "live" }
                        )
                        "results" -> ResultsScreen(
                            onResultClick = { resultTitle ->
                                selectedResultTitle = resultTitle
                                currentScreen = "result_detail"
                            },
                            onLiveClick = { currentScreen = "live" }
                        )
                        "fight_pass" -> PlaceholderScreen(title = "FIGHT PASS")
                        "news" -> PlaceholderScreen(title = "NEWS")
                        "athletes" -> PlaceholderScreen(title = "ATHLETES")
                        "detail" -> EventDetailScreen(
                            eventTitle = selectedEvent ?: "MFCS FIGHT WEEK",
                            imageRes = selectedImageRes,
                            initialTimeSeconds = selectedTimerSeconds,
                            onBack = { currentScreen = "home" }
                        )
                        "result_detail" -> EventResultDetailScreen(
                            eventTitle = selectedResultTitle,
                            onBack = { currentScreen = "results" }
                        )
                        "live" -> LiveVideosScreen(
                            onBack = { currentScreen = "home" }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    LaunchedEffect(key1 = true) {
        delay(3000)
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "MFCS",
            color = Color.Red,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun MfcsBottomBar(currentTab: String, onTabSelected: (String) -> Unit) {
    NavigationBar(
        containerColor = Color.Black,
        contentColor = Color.White
    ) {
        NavigationBarItem(
            selected = currentTab == "home",
            onClick = { onTabSelected("home") },
            icon = { Text(text = "📅", fontSize = 20.sp) },
            label = { Text(text = "UPCOMING", fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Red,
                unselectedIconColor = Color.Gray,
                selectedTextColor = Color.Red,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.DarkGray
            )
        )
        NavigationBarItem(
            selected = currentTab == "results",
            onClick = { onTabSelected("results") },
            icon = { Text(text = "📋", fontSize = 20.sp) },
            label = { Text(text = "RESULTS", fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Red,
                unselectedIconColor = Color.Gray,
                selectedTextColor = Color.Red,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.DarkGray
            )
        )
        NavigationBarItem(
            selected = currentTab == "fight_pass",
            onClick = { onTabSelected("fight_pass") },
            icon = { Text(text = "💻", fontSize = 20.sp) },
            label = { Text(text = "FIGHT PASS", fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Red,
                unselectedIconColor = Color.Gray,
                selectedTextColor = Color.Red,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.DarkGray
            )
        )
        NavigationBarItem(
            selected = currentTab == "news",
            onClick = { onTabSelected("news") },
            icon = { Text(text = "📰", fontSize = 20.sp) },
            label = { Text(text = "NEWS", fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Red,
                unselectedIconColor = Color.Gray,
                selectedTextColor = Color.Red,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.DarkGray
            )
        )
        NavigationBarItem(
            selected = currentTab == "athletes",
            onClick = { onTabSelected("athletes") },
            icon = { Text(text = "🥊", fontSize = 20.sp) },
            label = { Text(text = "ATHLETES", fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Red,
                unselectedIconColor = Color.Gray,
                selectedTextColor = Color.Red,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.DarkGray
            )
        )
    }
}

@Composable
fun PlaceholderScreen(title: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ResultsScreen(onResultClick: (String) -> Unit, onLiveClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "☰", color = Color.White, fontSize = 24.sp)
            Text(text = "MFCS", color = Color.Red, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Button(
                onClick = onLiveClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(text = "LIVE (1)", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Text(
                    text = "UP NEXT",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                ResultCard(
                    title = "MFCS Fight Night",
                    match = "Temuujin vs. Delgermurun",
                    onClick = { onResultClick("MFCS Fight Night: Temuujin vs. Delgermurun") }
                )
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "PAST EVENTS",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                ResultCard(
                    title = "MFCS 333",
                    match = "Volkanovski vs. Evloev",
                    onClick = { onResultClick("MFCS 333: Volkanovski vs. Evloev") }
                )
            }
            item {
                ResultCard(
                    title = "MFCS Fight Night",
                    match = "Buckley vs. Malott",
                    onClick = { onResultClick("MFCS Fight Night: Buckley vs. Malott") }
                )
            }
            item {
                ResultCard(
                    title = "MFCS Fight Night",
                    match = "Allen vs. Duncan",
                    onClick = { onResultClick("MFCS Fight Night: Allen vs. Duncan") }
                )
            }
            item {
                ResultCard(
                    title = "MFCS 332",
                    match = "Silva vs. Wang",
                    onClick = { onResultClick("MFCS 332: Silva vs. Wang") }
                )
            }
        }
    }
}

@Composable
fun ResultCard(title: String, match: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = match,
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun EventResultDetailScreen(eventTitle: String, onBack: () -> Unit) {
    var selectedTab by remember { mutableStateOf("MAIN CARD") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "< Back",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onBack() }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Дээд хэсэг: Тулаанчдын зураг болон текстийг томоор харуулах
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(75.dp)
                        .background(Color.DarkGray, shape = MaterialTheme.shapes.small),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.fight_poster_1),
                        contentDescription = "Fighter 1",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = "ALEXANDER", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, maxLines = 1)
                Text(text = "VOLKANOVSKI", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, maxLines = 1)
                Text(text = "29-4-0", color = Color.Gray, fontSize = 10.sp)
            }

            Column(
                modifier = Modifier
                    .weight(1.4f)
                    .padding(horizontal = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = eventTitle,
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Abu Dhabi, UAE",
                    color = Color.Gray,
                    fontSize = 10.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                    modifier = Modifier.height(30.dp)
                ) {
                    Text(text = "FIGHT STATS", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(75.dp)
                        .background(Color.DarkGray, shape = MaterialTheme.shapes.small),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.fight_poster_2),
                        contentDescription = "Fighter 2",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = "MOVSAR", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, maxLines = 1)
                Text(text = "EVLOEV", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, maxLines = 1)
                Text(text = "20-1-0", color = Color.Gray, fontSize = 10.sp)
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "FIGHTCARD",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            TabButton(title = "MAIN CARD", isSelected = selectedTab == "MAIN CARD") { selectedTab = "MAIN CARD" }
            TabButton(title = "PRELIM", isSelected = selectedTab == "PRELIM") { selectedTab = "PRELIM" }
            TabButton(title = "EARLY PRELIM", isSelected = selectedTab == "EARLY PRELIM") { selectedTab = "EARLY PRELIM" }
        }

        Divider(color = Color.DarkGray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                UfcFightCardRow(
                    fighter1Name = "ALEXANDER VOLKANOVSKI",
                    fighter2Name = "MOVSAR EVLOEV",
                    resultText = "KO - TKO\n4:54 - ROUND 2",
                    winnerImageRes = R.drawable.fight_poster_1,
                    loserImageRes = R.drawable.fight_poster_2
                )
            }
            item {
                UfcFightCardRow(
                    fighter1Name = "PETR YAN",
                    fighter2Name = "MERAB DVALISHVILI",
                    resultText = "UNANIMOUS DECISION\n5:00 - ROUND 5",
                    winnerImageRes = R.drawable.fight_poster_3,
                    loserImageRes = R.drawable.fight_poster_4
                )
            }
            item {
                UfcFightCardRow(
                    fighter1Name = "LONE'ER KAVANAGH",
                    fighter2Name = "RAMAZAN TEMIROV",
                    resultText = "KO - TKO\n4:28 - ROUND 1",
                    winnerImageRes = R.drawable.fight_poster_5,
                    loserImageRes = R.drawable.fight_poster_6
                )
            }
        }
    }
}

@Composable
fun TabButton(title: String, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = title,
            color = if (isSelected) Color.White else Color.Gray,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        if (isSelected) {
            Box(
                modifier = Modifier
                    .width(35.dp)
                    .height(2.dp)
                    .background(Color.Red)
            )
        }
    }
}

@Composable
fun UfcFightCardRow(
    fighter1Name: String,
    fighter2Name: String,
    resultText: String,
    winnerImageRes: Int,
    loserImageRes: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1.2f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = fighter1Name,
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(3.dp))
            Surface(
                color = Color.Red,
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = "WINNER",
                    color = Color.White,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color.DarkGray, shape = MaterialTheme.shapes.small),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = winnerImageRes),
                contentDescription = "Winner",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.width(6.dp))

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = resultText,
                color = Color.Gray,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                maxLines = 2
            )
        }

        Spacer(modifier = Modifier.width(6.dp))

        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color.DarkGray, shape = MaterialTheme.shapes.small),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = loserImageRes),
                contentDescription = "Loser",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier.weight(1.2f),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = fighter2Name,
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                textAlign = androidx.compose.ui.text.style.TextAlign.End
            )
        }
    }
}

@Composable
fun HomeScreen(onEventClick: (String, Int, Long) -> Unit, onLiveClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "☰", color = Color.White, fontSize = 24.sp)
            Text(text = "MFCS", color = Color.Red, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Button(
                onClick = onLiveClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(text = "LIVE (2)", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                DaySection(
                    dateHeader = "FRIDAY, OCTOBER 30TH",
                    events = listOf(
                        Quad("MFCS BJJ 13: TACKETT VS RODRIGUEZ", "6:00 AM, 10/30/26", R.drawable.fight_poster_1, 172800L)
                    ),
                    onEventClick = onEventClick
                )
            }
            item {
                DaySection(
                    dateHeader = "SATURDAY, OCTOBER 31ST",
                    events = listOf(
                        Quad("MFCS FIGHT NIGHT PREVIEW", "6:00 AM, 10/31/26", R.drawable.fight_poster_2, 259200L),
                        Quad("ETERNAL MMA 111", "8:00 PM, 10/31/26", R.drawable.fight_poster_3, 345600L)
                    ),
                    onEventClick = onEventClick
                )
            }
            item {
                DaySection(
                    dateHeader = "SUNDAY, NOVEMBER 1ST",
                    events = listOf(
                        Quad("ARES 44", "1:30 AM, 11/01/26", R.drawable.fight_poster_4, 432000L),
                        Quad("MFCS FIGHT NIGHT: MAIN CARD", "5:00 AM, 11/01/26", R.drawable.fight_poster_5, 518400L)
                    ),
                    onEventClick = onEventClick
                )
            }
            item {
                DaySection(
                    dateHeader = "MONDAY, NOVEMBER 2ND",
                    events = listOf(
                        Quad("MFCS CONTENDER SERIES: WEEK 1", "7:00 PM, 11/02/26", R.drawable.fight_poster_6, 604800L),
                        Quad("ROAD TO MFCS: EPISODE 4", "9:30 PM, 11/02/26", R.drawable.fight_poster_7, 691200L)
                    ),
                    onEventClick = onEventClick
                )
            }
            item {
                DaySection(
                    dateHeader = "TUESDAY, NOVEMBER 3RD",
                    events = listOf(
                        Quad("MFCS CHAMPIONSHIP PRESS CONFERENCE", "4:00 PM, 11/03/26", R.drawable.fight_poster_8, 777600L),
                        Quad("MFCS NIGHT: DULGUUN VS TUGULDUR", "10:00 PM, 11/03/26", R.drawable.fight_poster_9, 864000L)
                    ),
                    onEventClick = onEventClick
                )
            }
        }
    }
}

data class Quad(val title: String, val time: String, val imageRes: Int, val timerSeconds: Long)

@Composable
fun DaySection(dateHeader: String, events: List<Quad>, onEventClick: (String, Int, Long) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = dateHeader,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            events.forEach { event ->
                UfcStyleCard(
                    title = event.title,
                    time = event.time,
                    imageRes = event.imageRes,
                    onClick = { onEventClick(event.title, event.imageRes, event.timerSeconds) }
                )
            }
        }
    }
}

@Composable
fun UfcStyleCard(title: String, time: String, imageRes: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(260.dp)
            .height(140.dp)
            .background(Color.DarkGray, shape = MaterialTheme.shapes.medium)
            .clickable { onClick() },
        contentAlignment = Alignment.BottomStart
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Event Poster",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
                .padding(10.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            Surface(
                color = Color.Black.copy(alpha = 0.7f),
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = time,
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.6f))
                .padding(8.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }
    }
}

@Composable
fun EventDetailScreen(eventTitle: String, imageRes: Int, initialTimeSeconds: Long, onBack: () -> Unit) {
    var timeLeft by remember { mutableStateOf(initialTimeSeconds) }
    var isReminderSet by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = initialTimeSeconds) {
        timeLeft = initialTimeSeconds
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft--
        }
    }

    val days = timeLeft / (24 * 3600)
    val hours = (timeLeft % (24 * 3600)) / 3600
    val minutes = (timeLeft % 3600) / 60
    val seconds = timeLeft % 60

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "< Back",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onBack() }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "MFCS", color = Color.Red, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text(text = eventTitle, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)

        Spacer(modifier = Modifier.height(15.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.DarkGray, shape = MaterialTheme.shapes.medium),
            contentAlignment = Alignment.BottomStart
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Event Poster",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
            )
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Surface(
                    color = Color.Red,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = "PPV / LIVE EVENT",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Steppe Arena, Ulaanbaatar",
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Text(text = "EVENT STARTS IN", color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            TimerBox(value = String.format("%02d", days), label = "DAYS")
            TimerBox(value = String.format("%02d", hours), label = "HOURS")
            TimerBox(value = String.format("%02d", minutes), label = "MINUTES")
            TimerBox(value = String.format("%02d", seconds), label = "SECONDS")
        }

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = { isReminderSet = !isReminderSet },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isReminderSet) Color.DarkGray else Color.Red
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = if (isReminderSet) "✔ REMINDER SET" else "SET REMINDER",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        if (isReminderSet) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Тулааныг ойртоход танд сануулах болно",
                color = Color.Green,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun TimerBox(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = label, color = Color.Gray, fontSize = 11.sp)
    }
}

@Composable
fun LiveVideosScreen(onBack: () -> Unit) {
    val figmaVideos = listOf(
        Pair("MFCS WEEK: Delgermurun vs Temuujin", true),
        Pair("MFCS WEEK: Dulguun vs Tuguldur", false),
        Pair("MFCS WEEK: Anar vs Anand", false),
        Pair("MFCS WEEK: Dorjoo vs Lkhagvaa", false)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "< Back", color = Color.White, modifier = Modifier.clickable { onBack() })
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "LIVE",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(figmaVideos) { video ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp)
                        .background(Color.DarkGray)
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .width(90.dp)
                            .fillMaxHeight()
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        if (video.second) {
                            Text(text = "🔒", fontSize = 18.sp)
                        } else {
                            Text(text = "▶", color = Color.White, fontSize = 18.sp)
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(text = video.first, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text(text = "8:24AM-11:43AM", color = Color.Gray, fontSize = 10.sp)
                    }
                }
            }
        }
    }
}