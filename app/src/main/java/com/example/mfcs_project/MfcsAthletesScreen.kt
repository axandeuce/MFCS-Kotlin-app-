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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val AthBgBlack = Color(0xFF000000)
private val AthCardDark = Color(0xFF151515)
private val AthChipDark = Color(0xFF1A1A1A)
private val AthAccentRed = Color(0xFFE50914)
private val AthTextWhite = Color(0xFFFFFFFF)
private val AthTextGray = Color(0xFFA0A0A0)

enum class AthleteWeightFilter(val label: String) {
    ALL("Бүгд"),
    LIGHT("Хөнгөн"),
    MIDDLE("Дунд"),
    HEAVY("Хүнд")
}

data class AthleteProfileItem(
    val id: String,
    val rank: Int,
    val initial: String,
    val name: String,
    val badgeTitle: String,
    val weightClassMn: String,
    val filterCategory: AthleteWeightFilter,
    val record: String,
    val wins: Int,
    val losses: Int,
    val draws: Int,
    val winRatePercent: Int,
    val age: String,
    val weight: String,
    val height: String,
    val reach: String,
    val strikingAccuracy: String,
    val sigStrikesAttempted: String,
    val grapplingAccuracy: String,
    val takedownAvg: String,
    val takedownDefence: String,
    val submissionAvg: String
)

private val sampleAthletes = listOf(
    AthleteProfileItem(
        id = "ath_1",
        rank = 1,
        initial = "Д",
        name = "Дэлгэрмөрөн",
        badgeTitle = "АВАРГА · ХӨНГӨН ЖИН",
        weightClassMn = "Хөнгөн жин",
        filterCategory = AthleteWeightFilter.LIGHT,
        record = "12-2-0",
        wins = 12,
        losses = 2,
        draws = 0,
        winRatePercent = 86,
        age = "24",
        weight = "70 кг",
        height = "175 см",
        reach = "178 см",
        strikingAccuracy = "72.84%",
        sigStrikesAttempted = "71",
        grapplingAccuracy = "53.62%",
        takedownAvg = "3.2",
        takedownDefence = "90.91%",
        submissionAvg = "1.13"
    ),
    AthleteProfileItem(
        id = "ath_2",
        rank = 2,
        initial = "Т",
        name = "Тэмүүжин",
        badgeTitle = "#2 · ХӨНГӨН ЖИН",
        weightClassMn = "Хөнгөн жин",
        filterCategory = AthleteWeightFilter.LIGHT,
        record = "10-3-0",
        wins = 10,
        losses = 3,
        draws = 0,
        winRatePercent = 77,
        age = "23",
        weight = "70 кг",
        height = "174 см",
        reach = "176 см",
        strikingAccuracy = "58.91%",
        sigStrikesAttempted = "64",
        grapplingAccuracy = "45.92%",
        takedownAvg = "1.89",
        takedownDefence = "88.00%",
        submissionAvg = "0.65"
    ),
    AthleteProfileItem(
        id = "ath_3",
        rank = 3,
        initial = "М",
        name = "Мөнхбаяр",
        badgeTitle = "#3 · ДУНД ЖИН",
        weightClassMn = "Дунд жин",
        filterCategory = AthleteWeightFilter.MIDDLE,
        record = "8-1-0",
        wins = 8,
        losses = 1,
        draws = 0,
        winRatePercent = 89,
        age = "25",
        weight = "84 кг",
        height = "183 см",
        reach = "186 см",
        strikingAccuracy = "61.11%",
        sigStrikesAttempted = "68",
        grapplingAccuracy = "64.20%",
        takedownAvg = "2.45",
        takedownDefence = "92.86%",
        submissionAvg = "1.10"
    ),
    AthleteProfileItem(
        id = "ath_4",
        rank = 4,
        initial = "З",
        name = "Золбоо",
        badgeTitle = "#4 · ХҮНД ЖИН",
        weightClassMn = "Хүнд жин",
        filterCategory = AthleteWeightFilter.HEAVY,
        record = "9-2-0",
        wins = 9,
        losses = 2,
        draws = 0,
        winRatePercent = 82,
        age = "27",
        weight = "105 кг",
        height = "191 см",
        reach = "194 см",
        strikingAccuracy = "54.40%",
        sigStrikesAttempted = "59",
        grapplingAccuracy = "48.10%",
        takedownAvg = "1.95",
        takedownDefence = "85.00%",
        submissionAvg = "0.52"
    ),
    AthleteProfileItem(
        id = "ath_5",
        rank = 5,
        initial = "С",
        name = "Саруул",
        badgeTitle = "#5 · ХӨНГӨН ЖИН",
        weightClassMn = "Хөнгөн жин",
        filterCategory = AthleteWeightFilter.LIGHT,
        record = "7-1-0",
        wins = 7,
        losses = 1,
        draws = 0,
        winRatePercent = 88,
        age = "22",
        weight = "66 кг",
        height = "170 см",
        reach = "172 см",
        strikingAccuracy = "63.05%",
        sigStrikesAttempted = "62",
        grapplingAccuracy = "57.30%",
        takedownAvg = "2.70",
        takedownDefence = "90.00%",
        submissionAvg = "0.94"
    ),
    AthleteProfileItem(
        id = "ath_6",
        rank = 6,
        initial = "Д",
        name = "Дөлгөөн",
        badgeTitle = "#6 · ХҮНД ЖИН",
        weightClassMn = "Хүнд жин",
        filterCategory = AthleteWeightFilter.HEAVY,
        record = "11-2-0",
        wins = 11,
        losses = 2,
        draws = 0,
        winRatePercent = 85,
        age = "26",
        weight = "112 кг",
        height = "195 см",
        reach = "198 см",
        strikingAccuracy = "56.75%",
        sigStrikesAttempted = "66",
        grapplingAccuracy = "51.20%",
        takedownAvg = "2.10",
        takedownDefence = "87.50%",
        submissionAvg = "0.46"
    ),
    AthleteProfileItem(
        id = "ath_7",
        rank = 7,
        initial = "Б",
        name = "Бат-Эрдэнэ",
        badgeTitle = "#7 · ДУНД ЖИН",
        weightClassMn = "Дунд жин",
        filterCategory = AthleteWeightFilter.MIDDLE,
        record = "9-2-0",
        wins = 9,
        losses = 2,
        draws = 0,
        winRatePercent = 82,
        age = "24",
        weight = "84 кг",
        height = "181 см",
        reach = "184 см",
        strikingAccuracy = "49.80%",
        sigStrikesAttempted = "54",
        grapplingAccuracy = "66.40%",
        takedownAvg = "3.45",
        takedownDefence = "82.93%",
        submissionAvg = "1.28"
    )
)

@Composable
fun MfcsAthletesScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf(AthleteWeightFilter.ALL) }
    var selectedAthlete by remember { mutableStateOf<AthleteProfileItem?>(null) }

    val filteredAthletes = remember(searchQuery, selectedFilter) {
        sampleAthletes.filter { athlete ->
            val matchesWeight = selectedFilter == AthleteWeightFilter.ALL || athlete.filterCategory == selectedFilter
            val query = searchQuery.trim().lowercase()
            val matchesQuery = query.isEmpty() ||
                    athlete.name.lowercase().contains(query) ||
                    athlete.weightClassMn.lowercase().contains(query) ||
                    athlete.record.lowercase().contains(query)
            matchesWeight && matchesQuery
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AthBgBlack)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 8.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item(key = "athletes_header_and_filters") {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Тамирчид",
                        color = AthTextWhite,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black
                    )

                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        placeholder = {
                            Text(
                                text = "Тамирчин хайх",
                                color = AthTextGray,
                                fontSize = 13.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Хайх",
                                tint = AthTextGray
                            )
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Цэвэрлэх",
                                        tint = AthTextGray
                                    )
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = AthTextWhite,
                            unfocusedTextColor = AthTextWhite,
                            focusedContainerColor = AthCardDark,
                            unfocusedContainerColor = AthCardDark,
                            focusedBorderColor = AthAccentRed,
                            unfocusedBorderColor = Color(0xFF232323)
                        )
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(AthleteWeightFilter.entries, key = { it.name }) { filter ->
                            val isSelected = selectedFilter == filter
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(if (isSelected) AthAccentRed else AthChipDark)
                                    .clickable { selectedFilter = filter }
                                    .padding(horizontal = 16.dp, vertical = 7.dp)
                            ) {
                                Text(
                                    text = filter.label,
                                    color = AthTextWhite,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            if (filteredAthletes.isEmpty()) {
                item(key = "athletes_empty") {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 36.dp, horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Тохирох тамирчин олдсонгүй.",
                            color = AthTextGray,
                            fontSize = 14.sp
                        )
                    }
                }
            } else {
                items(filteredAthletes, key = { it.id }) { athlete ->
                    AthleteRankingRowCard(
                        athlete = athlete,
                        onClick = { selectedAthlete = athlete }
                    )
                }
            }
        }

        selectedAthlete?.let { currentAthlete ->
            AthleteProfileDetailScreen(
                athlete = currentAthlete,
                onBack = { selectedAthlete = null }
            )
        }
    }
}

@Composable
private fun AthleteRankingRowCard(
    athlete: AthleteProfileItem,
    onClick: () -> Unit
) {
    val progressFraction = (athlete.winRatePercent.coerceIn(0, 100)) / 100f

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(AthCardDark)
            .border(1.dp, Color(0xFF222222), RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = athlete.rank.toString(),
                color = AthTextGray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF121212))
                    .border(1.5.dp, AthAccentRed, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = athlete.initial,
                    color = AthTextWhite,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = athlete.name,
                    color = AthTextWhite,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = athlete.weightClassMn,
                    color = AthTextGray,
                    fontSize = 12.sp
                )
            }

            Text(
                text = athlete.record,
                color = AthAccentRed,
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.5.sp
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color(0xFF2C2C2C))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progressFraction)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(AthAccentRed)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Ялалт ${athlete.winRatePercent}%",
                    color = AthTextGray,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
private fun AthleteProfileDetailScreen(
    athlete: AthleteProfileItem,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AthBgBlack)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 18.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Буцах",
                    tint = AthTextWhite
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "MFCS",
                color = AthAccentRed,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.size(48.dp))
        }

        Spacer(modifier = Modifier.height(14.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = athlete.name.uppercase(),
                color = AthTextWhite,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = athlete.badgeTitle,
                color = AthAccentRed,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.6.sp
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AthleteRecordStatColumn(label = "ЯЛАЛТ", value = athlete.wins.toString())
            AthleteRecordStatColumn(label = "ЯЛАГДАЛ", value = athlete.losses.toString())
            AthleteRecordStatColumn(label = "ТЭНЦЭЭ", value = athlete.draws.toString())
        }

        Spacer(modifier = Modifier.height(28.dp))

        AthleteSectionHeader(title = "ҮНДСЭН МЭДЭЭЛЭЛ")
        AthleteDetailMetricRow(label = "НАС", value = athlete.age)
        AthleteDetailMetricRow(label = "ЖИН", value = athlete.weight)
        AthleteDetailMetricRow(label = "ӨНДӨР", value = athlete.height)
        AthleteDetailMetricRow(label = "ДАЛАЙЦ", value = athlete.reach)

        Spacer(modifier = Modifier.height(24.dp))

        AthleteSectionHeader(title = "ЦОХИЛТ")
        AthleteDetailMetricRow(label = "НАРИЙВЧЛАЛ", value = athlete.strikingAccuracy)
        AthleteDetailMetricRow(label = "ОРОЛДСОН ХҮЧТЭЙ ЦОХИЛТ", value = athlete.sigStrikesAttempted)

        Spacer(modifier = Modifier.height(24.dp))

        AthleteSectionHeader(title = "БАРИЛДААН / УНАЛТ")
        AthleteDetailMetricRow(label = "НАРИЙВЧЛАЛ", value = athlete.grapplingAccuracy)
        AthleteDetailMetricRow(label = "УНАЛТЫН ДУНДАЖ", value = athlete.takedownAvg)
        AthleteDetailMetricRow(label = "УНАЛТ ХАМГААЛАЛТ", value = athlete.takedownDefence)
        AthleteDetailMetricRow(label = "ХУУРАХ МЭХИЙН ДУНДАЖ", value = athlete.submissionAvg)

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun AthleteRecordStatColumn(
    label: String,
    value: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            color = AthTextGray,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.5.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            color = AthTextWhite,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun AthleteSectionHeader(title: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            color = AthTextWhite,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.8.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        HorizontalDivider(color = Color(0xFF282828), thickness = 1.dp)
    }
}

@Composable
private fun AthleteDetailMetricRow(
    label: String,
    value: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                color = AthTextGray,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.4.sp
            )
            Text(
                text = value,
                color = AthTextWhite,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }
        HorizontalDivider(color = Color(0xFF1A1A1A), thickness = 1.dp)
    }
}