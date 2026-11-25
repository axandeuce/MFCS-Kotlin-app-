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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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

private val AthBgBlack = Color(0xFF000000)
private val AthCardDark = Color(0xFF141414)
private val AthCardInner = Color(0xFF1E1E1E)
private val AthSheetDark = Color(0xFF121212)
private val AthAccentRed = Color(0xFFE50914)
private val AthDeepRed = Color(0xFF4A070B)
private val AthGold = Color(0xFFF59E0B)
private val AthTextWhite = Color(0xFFFFFFFF)
private val AthTextGray = Color(0xFFA6A6A6)
private val AthTextMuted = Color(0xFF6E6E6E)
private val AthBorderGray = Color(0xFF262626)

enum class AthleteWeightClass(val filterLabel: String, val divisionTitle: String) {
    FLY_57("57KG", "57 KG • FLYWEIGHT"),
    BANTAM_61("61KG", "61 KG • BANTAMWEIGHT"),
    FEATHER_66("66KG", "66 KG • FEATHERWEIGHT"),
    LIGHT_70("70KG", "70 KG • LIGHTWEIGHT"),
    WELTER_77("77KG", "77 KG • WELTERWEIGHT"),
    HEAVY_84_PLUS("84+KG", "84+ KG • MIDDLE / HEAVY")
}

data class MfcsAthlete(
    val id: String,
    val name: String,
    val nickname: String,
    val weightClass: AthleteWeightClass,
    val record: String,
    val winsByKo: Int,
    val winsBySub: Int,
    val style: String,
    val height: String,
    val reach: String,
    val team: String,
    val bio: String,
    val isChampion: Boolean = false
)

private fun buildDivisionAthletes(
    weightClass: AthleteWeightClass,
    prefixId: String,
    baseHeightCm: Int,
    baseReachCm: Int,
    rosterSeed: List<Triple<String, String, String>>
): List<MfcsAthlete> {
    return rosterSeed.mapIndexed { index, (name, nickname, style) ->
        val wins = 13 - (index / 2)
        val losses = (index % 3) + (index / 4)
        val draws = if (index == 8) 1 else 0
        val koWins = (wins * 0.45).toInt().coerceAtLeast(1)
        val subWins = (wins * 0.30).toInt().coerceAtLeast(0)
        val height = "${baseHeightCm + ((index % 3) - 1) * 2} см"
        val reach = "${baseReachCm + ((index % 4) - 1) * 2} см"
        val teams = listOf(
            "Garuda Fight Club",
            "Khan MMA Academy",
            "Nomad Combat Team",
            "Ulaanbaatar Grappling",
            "Altai Warriors"
        )
        MfcsAthlete(
            id = "${prefixId}_${index + 1}",
            name = name,
            nickname = nickname,
            weightClass = weightClass,
            record = "$wins - $losses - $draws",
            winsByKo = koWins,
            winsBySub = subWins,
            style = style,
            height = height,
            reach = reach,
            team = teams[index % teams.size],
            bio = "$name нь ${weightClass.divisionTitle} ангилалд $style хэв маягаар тоглодог, бэлтгэлийн өндөр сахилга баттай тамирчин.",
            isChampion = index == 0
        )
    }
}

private val mfcsAthletesList: List<MfcsAthlete> = listOf(
    // 1. 57KG (10 тамирчин)
    buildDivisionAthletes(
        weightClass = AthleteWeightClass.FLY_57,
        prefixId = "w57",
        baseHeightCm = 165,
        baseReachCm = 169,
        rosterSeed = listOf(
            Triple("Б. МӨНХ-ОРГИЛ", "\"SWIFT ARROW\"", "Striker & Counter"),
            Triple("Г. БАТХҮҮ", "\"POCKET ROCKET\"", "Pressure Boxer"),
            Triple("Э. ТЭМҮҮЛЭН", "\"SHADOW\"", "Submission Grappler"),
            Triple("Н. АМАРСАНАА", "\"BLITZ\"", "Kickboxer"),
            Triple("Д. ЭНХБАЯР", "\"VIPER\"", "Wrestler"),
            Triple("Ц. СҮХБААТАР", "\"HAWK\"", "All-Rounder"),
            Triple("Б. ЗОЛБОО", "\"FLASH\"", "Counter Striker"),
            Triple("О. ТӨГСЖАРГАЛ", "\"COIL\"", "Grappler"),
            Triple("М. БАЯРЖАРГАЛ", "\"SPARROW\"", "Volume Striker"),
            Triple("С. ХАНГАЙ", "\"EDGE\"", "All-Rounder")
        )
    ),
    // 2. 61KG (10 тамирчин)
    buildDivisionAthletes(
        weightClass = AthleteWeightClass.BANTAM_61,
        prefixId = "w61",
        baseHeightCm = 171,
        baseReachCm = 175,
        rosterSeed = listOf(
            Triple("Б. ШИНЭБААТАР", "\"STEPPE VIPER\"", "Striking & Counter"),
            Triple("Ш. ОДСҮРЭН", "\"IRON JAW\"", "Pressure Boxer"),
            Triple("А. БОЛД-ЭРДЭНЭ", "\"FANG\"", "All-Rounder"),
            Triple("Г. ЭРДЭНЭБАТ", "\"STORM\"", "Wrestler & GnP"),
            Triple("Д. МӨНХБАЯР", "\"BLADE\"", "Muay Thai"),
            Triple("Т. ДАВААДОРЖ", "\"LYNX\"", "Submission Grappler"),
            Triple("Б. ГАНБААТАР", "\"HAMMER\"", "Power Striker"),
            Triple("Х. АЛТАНГЭРЭЛ", "\"FALCON\"", "Counter Boxer"),
            Triple("З. ЦЭНД-АЮУШ", "\"KNOT\"", "Grappler"),
            Triple("У. БАТСАЙХАН", "\"RAZOR\"", "All-Rounder")
        )
    ),
    // 3. 66KG (10 тамирчин)
    buildDivisionAthletes(
        weightClass = AthleteWeightClass.FEATHER_66,
        prefixId = "w66",
        baseHeightCm = 175,
        baseReachCm = 180,
        rosterSeed = listOf(
            Triple("Э. АЛТАНХУЯГ", "\"WIND RUNNER\"", "Striker & Grappler"),
            Triple("Б. ТЭМҮҮЖИН", "\"WOLF\"", "All-Rounder"),
            Triple("Г. ДОРЖПАЛАМ", "\"ANVIL\"", "Heavy Hands"),
            Triple("Д. ГАНЗОРИГ", "\"TIGER\"", "Wrestler"),
            Triple("Н. БАТМӨНХ", "\"SPECTRE\"", "Submission Artist"),
            Triple("С. МӨНХ-ЭРДЭНЭ", "\"STRIKE\"", "Kickboxer"),
            Triple("Ц. ОЮУНБААТАР", "\"EAGLE\"", "Pressure Fighter"),
            Triple("Б. ХҮРЭЛБААТАР", "\"IRONCLAD\"", "All-Rounder"),
            Triple("М. ЭНХТАЙВАН", "\"CLAW\"", "Grappler"),
            Triple("Р. БАЯРМӨНХ", "\"SPARK\"", "Counter Striker")
        )
    ),
    // 4. 70KG (10 тамирчин)
    buildDivisionAthletes(
        weightClass = AthleteWeightClass.LIGHT_70,
        prefixId = "w70",
        baseHeightCm = 178,
        baseReachCm = 183,
        rosterSeed = listOf(
            Triple("ДЭЛГЭРМӨРӨН", "\"NOMAD\"", "All-Rounder"),
            Triple("БАТ-ОРГИЛ", "\"CHOKE ARTIST\"", "Submission Grappler"),
            Triple("Б. АНХБАЯР", "\"VORTEX\"", "Striker & Takedowns"),
            Triple("Г. ЦЭРЭНДОРЖ", "\"BOULDER\"", "Pressure Boxer"),
            Triple("Э. МӨНХТУЛГА", "\"SABER\"", "Kickboxer"),
            Triple("Д. СОДНОМ", "\"GRIP\"", "Freestyle Wrestler"),
            Triple("Т. БАТЗОРИГ", "\"APEX\"", "All-Rounder"),
            Triple("Х. ГАНТУЛГА", "\"PHANTOM\"", "Counter Striker"),
            Triple("З. АМАРБАЯР", "\"PYTHON\"", "Grappler"),
            Triple("О. ЭРДЭНЭМӨНХ", "\"CRUSHER\"", "Heavy Striker")
        )
    ),
    // 5. 77KG (10 тамирчин)
    buildDivisionAthletes(
        weightClass = AthleteWeightClass.WELTER_77,
        prefixId = "w77",
        baseHeightCm = 182,
        baseReachCm = 187,
        rosterSeed = listOf(
            Triple("Б. ОДБАЯР", "\"STEEL\"", "Wrestler & Striker"),
            Triple("Г. БАТТУЛГА", "\"THUNDER\"", "Power Boxer"),
            Triple("Д. ЧИНЗОРИГ", "\"BEAR\"", "Grappler"),
            Triple("Н. ГАН-ЭРДЭНЭ", "\"LANCE\"", "All-Rounder"),
            Triple("С. ТӨМӨРБААТАР", "\"SLEDGE\"", "Heavy Striker"),
            Triple("Ц. БАТБОЛД", "\"SHIELD\"", "Defensive Counter"),
            Triple("М. ДОРЖСҮРЭН", "\"COBRA\"", "Submission Grappler"),
            Triple("Б. СУМЪЯАБАЗАР", "\"MONOLITH\"", "Power & Clinch"),
            Triple("Э. ХАСБААТАР", "\"STRIDER\"", "Kickboxer"),
            Triple("З. МӨНХДАЛАЙ", "\"TEMPEST\"", "All-Rounder")
        )
    ),
    // 6. 84+KG (10 тамирчин)
    buildDivisionAthletes(
        weightClass = AthleteWeightClass.HEAVY_84_PLUS,
        prefixId = "w84",
        baseHeightCm = 188,
        baseReachCm = 193,
        rosterSeed = listOf(
            Triple("ГАНЗОРИГ", "\"TITAN\"", "Heavy Striker"),
            Triple("Б. НАРАНБААТАР", "\"COLOSSUS\"", "Power & Wrestling"),
            Triple("Д. БАТ-ЭРДЭНЭ", "\"MAMMOTH\"", "Heavy Boxer"),
            Triple("Г. ХҮЧТЭН", "\"GOLIATH\"", "Grappler & GnP"),
            Triple("Н. ОТГОНБААТАР", "\"CANNON\"", "KO Artist"),
            Triple("Т. ЭРДЭНЭДАЛАЙ", "\"FORTRESS\"", "All-Rounder"),
            Triple("Ц. ГАНХҮЛЭГ", "\"JUGGERNAUT\"", "Pressure Fighter"),
            Triple("Б. САЙХАНБАЯР", "\"APEX HEAVY\"", "Striker"),
            Triple("М. БАТЖАРГАЛ", "\"CRAG\"", "Wrestler"),
            Triple("Э. ДӨЛГӨӨН", "\"MONSTER\"", "Heavy All-Rounder")
        )
    )
).flatten()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MfcsAthletesScreen() {
    var selectedWeight by remember { mutableStateOf(AthleteWeightClass.FLY_57) }
    var selectedAthlete by remember { mutableStateOf<MfcsAthlete?>(null) }

    val navBarPadding = WindowInsets.navigationBars.asPaddingValues()

    val filteredAthletes = remember(selectedWeight) {
        mfcsAthletesList.filter { it.weightClass == selectedWeight }
    }

    selectedAthlete?.let { athlete ->
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            onDismissRequest = { selectedAthlete = null },
            sheetState = sheetState,
            containerColor = AthSheetDark,
            contentColor = AthTextWhite,
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = 18.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(
                            text = athlete.weightClass.divisionTitle,
                            color = AthAccentRed,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = athlete.name,
                            color = AthTextWhite,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = athlete.nickname,
                            color = AthGold,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Хаах",
                        tint = AthTextGray,
                        modifier = Modifier
                            .size(22.dp)
                            .clickable { selectedAthlete = null }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StatBox(
                        label = "RECORD",
                        value = athlete.record,
                        highlightColor = AthTextWhite,
                        modifier = Modifier.weight(1f)
                    )
                    StatBox(
                        label = "KO / TKO",
                        value = athlete.winsByKo.toString(),
                        highlightColor = AthAccentRed,
                        modifier = Modifier.weight(1f)
                    )
                    StatBox(
                        label = "SUBMISSION",
                        value = athlete.winsBySub.toString(),
                        highlightColor = AthGold,
                        modifier = Modifier.weight(1f)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(AthCardDark)
                        .border(1.dp, AthBorderGray, RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    InfoRow(label = "STYLE", value = athlete.style)
                    InfoRow(label = "HEIGHT / REACH", value = "${athlete.height}  •  ${athlete.reach}")
                    InfoRow(label = "TEAM", value = athlete.team)
                }

                Text(
                    text = athlete.bio,
                    color = AthTextGray,
                    fontSize = 13.sp,
                    lineHeight = 19.sp
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AthBgBlack)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(AthBgBlack)
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = "6 DIVISIONS • 60 FIGHTERS",
                    color = AthAccentRed,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "MFCS ATHLETES",
                    color = AthTextWhite,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.5.sp
                )
            }

            Icon(
                imageVector = Icons.Default.Groups,
                contentDescription = null,
                tint = AthTextGray,
                modifier = Modifier.size(22.dp)
            )
        }

        HorizontalDivider(color = AthBorderGray.copy(alpha = 0.7f), thickness = 1.dp)

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(AthBgBlack),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 12.dp,
                bottom = navBarPadding.calculateBottomPadding() + 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item(key = "athlete_weights") {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(AthleteWeightClass.entries) { weightClass ->
                        val isSelected = weightClass == selectedWeight
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isSelected) AthAccentRed else AthCardDark)
                                .border(
                                    1.dp,
                                    if (isSelected) AthAccentRed else AthBorderGray,
                                    RoundedCornerShape(6.dp)
                                )
                                .clickable { selectedWeight = weightClass }
                                .padding(horizontal = 13.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = weightClass.filterLabel,
                                color = AthTextWhite,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.4.sp
                            )
                        }
                    }
                }
            }

            item(key = "division_header") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedWeight.divisionTitle,
                        color = AthTextGray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.6.sp
                    )
                    Text(
                        text = "${filteredAthletes.size} FIGHTERS",
                        color = AthAccentRed,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            items(filteredAthletes, key = { it.id }) { athlete ->
                AthleteRosterCard(
                    athlete = athlete,
                    onClick = { selectedAthlete = athlete }
                )
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}

@Composable
private fun AthleteRosterCard(
    athlete: MfcsAthlete,
    onClick: () -> Unit
) {
    val initials = athlete.name
        .split(" ")
        .mapNotNull { it.firstOrNull()?.toString() }
        .take(2)
        .joinToString("")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(
                if (athlete.isChampion) {
                    Brush.horizontalGradient(listOf(AthDeepRed, AthCardDark))
                } else {
                    Brush.horizontalGradient(listOf(AthCardDark, AthCardDark))
                }
            )
            .border(
                1.dp,
                if (athlete.isChampion) AthGold.copy(alpha = 0.7f) else AthBorderGray,
                RoundedCornerShape(14.dp)
            )
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(if (athlete.isChampion) AthGold.copy(alpha = 0.2f) else AthCardInner)
                .border(
                    1.5.dp,
                    if (athlete.isChampion) AthGold else AthAccentRed.copy(alpha = 0.6f),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = initials,
                color = if (athlete.isChampion) AthGold else AthTextWhite,
                fontSize = 15.sp,
                fontWeight = FontWeight.Black
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                if (athlete.isChampion) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = null,
                        tint = AthGold,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = "CHAMPION",
                        color = AthGold,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black
                    )
                } else {
                    Text(
                        text = athlete.weightClass.filterLabel,
                        color = AthAccentRed,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            Text(
                text = athlete.name,
                color = AthTextWhite,
                fontSize = 15.sp,
                fontWeight = FontWeight.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "${athlete.nickname} • ${athlete.style}",
                color = AthTextGray,
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = athlete.record,
                color = AthTextWhite,
                fontSize = 13.sp,
                fontWeight = FontWeight.Black
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = AthTextMuted,
                modifier = Modifier.size(15.dp)
            )
        }
    }
}

@Composable
private fun StatBox(
    label: String,
    value: String,
    highlightColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(AthCardDark)
            .border(1.dp, AthBorderGray, RoundedCornerShape(10.dp))
            .padding(vertical = 10.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = value,
            color = highlightColor,
            fontSize = 15.sp,
            fontWeight = FontWeight.Black
        )
        Text(
            text = label,
            color = AthTextMuted,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = AthTextMuted,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = value,
            color = AthTextWhite,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}