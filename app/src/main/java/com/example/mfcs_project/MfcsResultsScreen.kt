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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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

private val ResultsBgBlack = Color(0xFF000000)
private val ResultsCardDark = Color(0xFF171717)
private val ResultsAccentRed = Color(0xFFE50914)
private val ResultsTextWhite = Color(0xFFFFFFFF)
private val ResultsTextGray = Color(0xFFA8A8A8)

enum class FightCardSegment(val title: String) {
    MAIN_CARD("MAIN CARD"),
    PRELIM("PRELIM"),
    EARLY_PRELIM("EARLY PRELIM")
}

data class BoutResultItem(
    val id: String,
    val fighterLeft: String,
    val fighterRight: String,
    val leftRecord: String,
    val rightRecord: String,
    val weightClass: String,
    val winnerSide: Int, // 0 = left winner, 1 = right winner
    val method: String,
    val roundAndTime: String,
    val roundNumber: String,
    val clockTime: String,
    val stats: List<FightStatRow>,
    val segment: FightCardSegment
)

data class FightStatRow(
    val leftValue: String,
    val label: String,
    val rightValue: String
)

data class EventResultSummary(
    val id: String,
    val groupLabel: String,
    val weekLabel: String,
    val matchupTitle: String,
    val eventTitle: String,
    val location: String,
    val bouts: List<BoutResultItem>
) {
    val totalFightersCount: Int
        get() = bouts.size * 2
}

private fun buildStats(
    kdL: String, kdR: String,
    tsL: String, tsR: String,
    ssL: String, ssR: String,
    tdL: String, tdR: String,
    subL: String, subR: String,
    revL: String, revR: String,
    ctL: String, ctR: String
): List<FightStatRow> = listOf(
    FightStatRow(kdL, "Knockdowns", kdR),
    FightStatRow(tsL, "Total Strikes", tsR),
    FightStatRow(ssL, "Significant Strikes", ssR),
    FightStatRow(tdL, "Takedowns", tdR),
    FightStatRow(subL, "Submission Attempts", subR),
    FightStatRow(revL, "Reversals", revR),
    FightStatRow(ctL, "Control Time", ctR)
)

private val sampleResultEvents = listOf(
    // 10 тулаан = 20 тамирчин (MAIN CARD 6, PRELIM 6, EARLY PRELIM 8)
    EventResultSummary(
        id = "res_1",
        groupLabel = "UP NEXT",
        weekLabel = "MFCS Fight week",
        matchupTitle = "DELGERMURUN VS TEMUUJIN",
        eventTitle = "MFCS 12: Delgermurun vs. Temuujin",
        location = "Ulaanbaatar, Mongolia",
        bouts = listOf(
            // MAIN CARD (6 тамирчин)
            BoutResultItem(
                id = "b1",
                fighterLeft = "DELGERMURUN",
                fighterRight = "TEMUUJIN",
                leftRecord = "14-2-0",
                rightRecord = "11-1-0",
                weightClass = "LIGHTWEIGHT",
                winnerSide = 0,
                method = "UNANIMOUS DECISION",
                roundAndTime = "5:00 - ROUND 5",
                roundNumber = "5",
                clockTime = "00:00",
                stats = buildStats("1", "0", "246", "198", "174", "132", "3", "2", "2", "1", "1", "0", "3:42", "2:18"),
                segment = FightCardSegment.MAIN_CARD
            ),
            BoutResultItem(
                id = "b2",
                fighterLeft = "BAT-ERDENE",
                fighterRight = "SUNDUI",
                leftRecord = "12-1-0",
                rightRecord = "10-2-0",
                weightClass = "WELTERWEIGHT",
                winnerSide = 0,
                method = "SUBMISSION",
                roundAndTime = "2:41 - ROUND 3",
                roundNumber = "3",
                clockTime = "02:41",
                stats = buildStats("0", "1", "118", "104", "82", "75", "4", "1", "3", "0", "1", "0", "4:15", "1:50"),
                segment = FightCardSegment.MAIN_CARD
            ),
            BoutResultItem(
                id = "b3",
                fighterLeft = "KHUSLEN",
                fighterRight = "NOMIN",
                leftRecord = "9-2-0",
                rightRecord = "11-1-0",
                weightClass = "FEATHERWEIGHT",
                winnerSide = 1,
                method = "KO - TKO",
                roundAndTime = "1:54 - ROUND 2",
                roundNumber = "2",
                clockTime = "01:54",
                stats = buildStats("0", "2", "64", "97", "49", "78", "0", "1", "0", "0", "0", "0", "0:32", "2:10"),
                segment = FightCardSegment.MAIN_CARD
            ),
            // PRELIM (6 тамирчин)
            BoutResultItem(
                id = "b4",
                fighterLeft = "GAN-ERDENE",
                fighterRight = "TUGULDUR",
                leftRecord = "8-1-0",
                rightRecord = "7-2-0",
                weightClass = "MIDDLEWEIGHT",
                winnerSide = 0,
                method = "KO - TKO",
                roundAndTime = "2:54 - ROUND 3",
                roundNumber = "3",
                clockTime = "02:54",
                stats = buildStats("2", "0", "134", "88", "96", "61", "1", "2", "0", "1", "0", "0", "2:04", "1:44"),
                segment = FightCardSegment.PRELIM
            ),
            BoutResultItem(
                id = "b5",
                fighterLeft = "MUNKH-ERDENE",
                fighterRight = "BATTULGA",
                leftRecord = "7-3-0",
                rightRecord = "9-1-0",
                weightClass = "BANTAMWEIGHT",
                winnerSide = 1,
                method = "UNANIMOUS DECISION",
                roundAndTime = "5:00 - ROUND 3",
                roundNumber = "3",
                clockTime = "00:00",
                stats = buildStats("0", "1", "141", "176", "98", "129", "2", "3", "1", "1", "0", "1", "2:19", "4:02"),
                segment = FightCardSegment.PRELIM
            ),
            BoutResultItem(
                id = "b6",
                fighterLeft = "TULGA",
                fighterRight = "ERDENEBAT",
                leftRecord = "6-1-0",
                rightRecord = "6-2-0",
                weightClass = "LIGHTWEIGHT",
                winnerSide = 0,
                method = "SUBMISSION",
                roundAndTime = "3:12 - ROUND 2",
                roundNumber = "2",
                clockTime = "03:12",
                stats = buildStats("0", "0", "77", "69", "52", "48", "3", "1", "4", "1", "1", "0", "3:51", "1:20"),
                segment = FightCardSegment.PRELIM
            ),
            // EARLY PRELIM (8 тамирчин)
            BoutResultItem(
                id = "b7",
                fighterLeft = "ZORIGT",
                fighterRight = "ENKHBAATAR",
                leftRecord = "5-1-0",
                rightRecord = "5-2-0",
                weightClass = "FEATHERWEIGHT",
                winnerSide = 0,
                method = "SUBMISSION",
                roundAndTime = "3:23 - ROUND 1",
                roundNumber = "1",
                clockTime = "03:23",
                stats = buildStats("1", "0", "45", "29", "31", "19", "2", "0", "2", "0", "0", "0", "2:40", "0:35"),
                segment = FightCardSegment.EARLY_PRELIM
            ),
            BoutResultItem(
                id = "b8",
                fighterLeft = "AMGALAN",
                fighterRight = "OTGONBAYAR",
                leftRecord = "4-2-0",
                rightRecord = "6-1-0",
                weightClass = "WELTERWEIGHT",
                winnerSide = 1,
                method = "UNANIMOUS DECISION",
                roundAndTime = "5:00 - ROUND 3",
                roundNumber = "3",
                clockTime = "00:00",
                stats = buildStats("0", "1", "112", "158", "74", "115", "1", "4", "0", "2", "0", "0", "1:45", "5:12"),
                segment = FightCardSegment.EARLY_PRELIM
            ),
            BoutResultItem(
                id = "b9",
                fighterLeft = "BATERDENE",
                fighterRight = "SUKHBAT",
                leftRecord = "5-0-0",
                rightRecord = "4-1-0",
                weightClass = "FLYWEIGHT",
                winnerSide = 0,
                method = "UNANIMOUS DECISION",
                roundAndTime = "5:00 - ROUND 3",
                roundNumber = "3",
                clockTime = "00:00",
                stats = buildStats("1", "0", "163", "128", "118", "89", "2", "1", "1", "0", "0", "0", "3:10", "2:08"),
                segment = FightCardSegment.EARLY_PRELIM
            ),
            BoutResultItem(
                id = "b10",
                fighterLeft = "MONKHBAT",
                fighterRight = "BAYARJARGAL",
                leftRecord = "3-1-0",
                rightRecord = "4-2-0",
                weightClass = "MIDDLEWEIGHT",
                winnerSide = 1,
                method = "KO - TKO",
                roundAndTime = "0:58 - ROUND 2",
                roundNumber = "2",
                clockTime = "00:58",
                stats = buildStats("0", "2", "38", "62", "27", "51", "0", "1", "0", "0", "0", "0", "0:22", "1:49"),
                segment = FightCardSegment.EARLY_PRELIM
            )
        )
    ),
    EventResultSummary(
        id = "res_2",
        groupLabel = "PAST EVENTS",
        weekLabel = "MFCS Fight week",
        matchupTitle = "DULGUUN VS TUGULDUR",
        eventTitle = "MFCS 11: Dulguun vs. Tuguldur",
        location = "Ulaanbaatar, Mongolia",
        bouts = listOf(
            BoutResultItem("b201", "DULGUUN", "TUGULDUR", "12-3-0", "9-2-0", "WELTERWEIGHT", 0, "KO - TKO", "1:18 - ROUND 3", "3", "01:18",
                buildStats("2", "0", "164", "112", "119", "78", "1", "2", "0", "1", "0", "0", "1:50", "2:05"), FightCardSegment.MAIN_CARD),
            BoutResultItem("b202", "CHINGUN", "BAYARAA", "10-2-0", "8-3-0", "LIGHTWEIGHT", 1, "SUBMISSION", "4:09 - ROUND 2", "2", "04:09",
                buildStats("0", "1", "84", "96", "58", "71", "1", "3", "1", "3", "0", "1", "1:30", "3:20"), FightCardSegment.MAIN_CARD),
            BoutResultItem("b203", "GANKHUU", "BOLD", "7-1-0", "6-2-0", "FEATHERWEIGHT", 0, "UNANIMOUS DECISION", "5:00 - ROUND 3", "3", "00:00",
                buildStats("1", "0", "148", "121", "102", "84", "2", "1", "1", "0", "0", "0", "2:55", "1:40"), FightCardSegment.PRELIM),
            BoutResultItem("b204", "NARMANAKH", "TSOGT", "6-2-0", "7-1-0", "BANTAMWEIGHT", 1, "KO - TKO", "2:14 - ROUND 1", "1", "02:14",
                buildStats("0", "2", "32", "54", "24", "43", "0", "0", "0", "0", "0", "0", "0:15", "1:10"), FightCardSegment.PRELIM),
            BoutResultItem("b205", "KHANGAI", "ALTANGEREL", "5-1-0", "4-2-0", "MIDDLEWEIGHT", 0, "SUBMISSION", "3:36 - ROUND 2", "2", "03:36",
                buildStats("0", "0", "79", "63", "51", "44", "3", "1", "3", "1", "1", "0", "4:01", "1:12"), FightCardSegment.EARLY_PRELIM),
            BoutResultItem("b206", "BYAMBA", "PUREV", "4-1-0", "4-3-0", "LIGHTWEIGHT", 0, "UNANIMOUS DECISION", "5:00 - ROUND 3", "3", "00:00",
                buildStats("1", "0", "130", "109", "91", "76", "2", "2", "0", "1", "0", "0", "2:44", "2:11"), FightCardSegment.EARLY_PRELIM)
        )
    ),
    EventResultSummary(
        id = "res_3",
        groupLabel = "PAST EVENTS",
        weekLabel = "MFCS Fight week",
        matchupTitle = "ANAR VS ANAND",
        eventTitle = "MFCS 10: Anar vs. Anand",
        location = "Darkhan, Mongolia",
        bouts = listOf(
            BoutResultItem("b301", "ANAR", "ANAND", "10-1-0", "8-2-0", "FEATHERWEIGHT", 1, "UNANIMOUS DECISION", "5:00 - ROUND 5", "5", "00:00",
                buildStats("0", "1", "188", "211", "124", "158", "2", "4", "1", "2", "0", "1", "2:12", "4:48"), FightCardSegment.MAIN_CARD),
            BoutResultItem("b302", "MUNKHBAYAR", "GANZORIG", "8-2-0", "7-2-0", "WELTERWEIGHT", 0, "KO - TKO", "1:47 - ROUND 2", "2", "01:47",
                buildStats("2", "0", "76", "52", "58", "39", "1", "0", "0", "0", "0", "0", "1:20", "0:48"), FightCardSegment.MAIN_CARD),
            BoutResultItem("b303", "TSEVEEN", "DAVAADORJ", "6-1-0", "5-2-0", "LIGHTWEIGHT", 0, "SUBMISSION", "2:59 - ROUND 1", "1", "02:59",
                buildStats("0", "0", "41", "28", "27", "18", "2", "0", "2", "0", "0", "0", "2:18", "0:29"), FightCardSegment.PRELIM),
            BoutResultItem("b304", "ODON", "CHULUUN", "5-2-0", "6-1-0", "BANTAMWEIGHT", 1, "UNANIMOUS DECISION", "5:00 - ROUND 3", "3", "00:00",
                buildStats("0", "1", "119", "147", "80", "106", "1", "3", "0", "1", "0", "0", "1:54", "3:37"), FightCardSegment.PRELIM),
            BoutResultItem("b305", "SHARAV", "LKHAGVASUREN", "4-1-0", "3-2-0", "FEATHERWEIGHT", 0, "KO - TKO", "4:02 - ROUND 3", "3", "04:02",
                buildStats("1", "0", "125", "98", "88", "69", "1", "1", "0", "0", "0", "0", "2:06", "1:51"), FightCardSegment.EARLY_PRELIM),
            BoutResultItem("b306", "UUGANBAYAR", "SOLONGO", "3-0-0", "3-1-0", "WELTERWEIGHT", 0, "SUBMISSION", "1:35 - ROUND 2", "2", "01:35",
                buildStats("0", "0", "62", "49", "42", "35", "2", "1", "2", "0", "1", "0", "2:49", "1:04"), FightCardSegment.EARLY_PRELIM)
        )
    ),
    EventResultSummary(
        id = "res_4",
        groupLabel = "PAST EVENTS",
        weekLabel = "MFCS Fight week",
        matchupTitle = "DORJOO VS LKHAGAA",
        eventTitle = "MFCS 09: Dorjoo vs. Lkhagaa",
        location = "Erdenet, Mongolia",
        bouts = listOf(
            BoutResultItem("b401", "DORJOO", "LKHAGAA", "13-4-0", "11-3-0", "MIDDLEWEIGHT", 0, "SUBMISSION", "3:45 - ROUND 2", "2", "03:45",
                buildStats("1", "0", "115", "89", "82", "61", "3", "1", "3", "0", "1", "0", "3:15", "1:10"), FightCardSegment.MAIN_CARD),
            BoutResultItem("b402", "SANJAA", "BATSAIKHAN", "9-3-0", "8-2-0", "LIGHTWEIGHT", 1, "UNANIMOUS DECISION", "5:00 - ROUND 3", "3", "00:00",
                buildStats("0", "1", "132", "161", "91", "118", "1", "3", "0", "1", "0", "0", "1:42", "3:58"), FightCardSegment.MAIN_CARD),
            BoutResultItem("b403", "KHUREL", "ARSLAN", "7-2-0", "6-1-0", "FEATHERWEIGHT", 1, "KO - TKO", "2:28 - ROUND 2", "2", "02:28",
                buildStats("0", "2", "68", "94", "47", "72", "0", "1", "0", "0", "0", "0", "0:55", "2:12"), FightCardSegment.PRELIM),
            BoutResultItem("b404", "MUNKHZAYA", "TUVSHIN", "6-1-0", "5-2-0", "WELTERWEIGHT", 0, "UNANIMOUS DECISION", "5:00 - ROUND 3", "3", "00:00",
                buildStats("1", "0", "144", "117", "103", "81", "2", "1", "1", "0", "0", "0", "3:04", "1:52"), FightCardSegment.PRELIM),
            BoutResultItem("b405", "GALBADRAKH", "NARAN", "4-2-0", "5-1-0", "BANTAMWEIGHT", 1, "SUBMISSION", "3:19 - ROUND 1", "1", "03:19",
                buildStats("0", "0", "36", "44", "24", "31", "0", "2", "0", "2", "0", "1", "0:41", "2:27"), FightCardSegment.EARLY_PRELIM),
            BoutResultItem("b406", "SOYol", "CHIMED", "3-1-0", "3-2-0", "LIGHTWEIGHT", 0, "KO - TKO", "1:12 - ROUND 3", "3", "01:12",
                buildStats("2", "1", "127", "105", "89", "74", "1", "1", "0", "0", "0", "0", "1:59", "2:03"), FightCardSegment.EARLY_PRELIM)
        )
    ),
    EventResultSummary(
        id = "res_5",
        groupLabel = "PAST EVENTS",
        weekLabel = "MFCS Fight week",
        matchupTitle = "ANAR VS TEMUUJIN",
        eventTitle = "MFCS 08: Anar vs. Temuujin",
        location = "Ulaanbaatar, Mongolia",
        bouts = listOf(
            BoutResultItem("b501", "ANAR", "TEMUUJIN", "9-1-0", "10-1-0", "LIGHTWEIGHT", 0, "KO - TKO", "4:12 - ROUND 3", "3", "04:12",
                buildStats("2", "1", "142", "118", "96", "84", "1", "2", "0", "1", "0", "0", "1:40", "2:25"), FightCardSegment.MAIN_CARD),
            BoutResultItem("b502", "BAATAR", "MUNKHTULGA", "8-1-0", "7-2-0", "WELTERWEIGHT", 0, "UNANIMOUS DECISION", "5:00 - ROUND 3", "3", "00:00",
                buildStats("1", "0", "156", "129", "112", "90", "2", "1", "1", "0", "0", "0", "3:22", "1:47"), FightCardSegment.MAIN_CARD),
            BoutResultItem("b503", "JARGAL", "SUKH", "6-2-0", "7-1-0", "FEATHERWEIGHT", 1, "SUBMISSION", "2:06 - ROUND 2", "2", "02:06",
                buildStats("0", "0", "59", "78", "39", "55", "0", "2", "0", "2", "0", "0", "0:49", "2:44"), FightCardSegment.PRELIM),
            BoutResultItem("b504", "ORLON", "KHURELSUKH", "5-1-0", "5-2-0", "MIDDLEWEIGHT", 0, "KO - TKO", "3:08 - ROUND 1", "1", "03:08",
                buildStats("1", "0", "48", "31", "36", "22", "1", "0", "0", "0", "0", "0", "1:33", "0:28"), FightCardSegment.PRELIM),
            BoutResultItem("b505", "TUMUR", "BATSUREN", "4-1-0", "4-2-0", "BANTAMWEIGHT", 0, "UNANIMOUS DECISION", "5:00 - ROUND 3", "3", "00:00",
                buildStats("0", "0", "138", "114", "95", "79", "2", "1", "1", "0", "0", "0", "2:51", "1:56"), FightCardSegment.EARLY_PRELIM),
            BoutResultItem("b506", "NYAM", "DASHDORJ", "3-1-0", "3-2-0", "LIGHTWEIGHT", 1, "SUBMISSION", "4:19 - ROUND 2", "2", "04:19",
                buildStats("0", "1", "81", "93", "54", "67", "1", "2", "0", "2", "0", "0", "1:18", "3:05"), FightCardSegment.EARLY_PRELIM)
        )
    ),
    EventResultSummary(
        id = "res_6",
        groupLabel = "PAST EVENTS",
        weekLabel = "MFCS Fight week",
        matchupTitle = "JACK VS ANAR",
        eventTitle = "MFCS 07: Jack vs. Anar",
        location = "Ulaanbaatar, Mongolia",
        bouts = listOf(
            BoutResultItem("b601", "JACK", "ANAR", "8-2-0", "8-1-0", "FEATHERWEIGHT", 1, "UNANIMOUS DECISION", "5:00 - ROUND 3", "3", "00:00",
                buildStats("0", "1", "121", "156", "79", "114", "1", "3", "1", "1", "0", "0", "1:15", "3:52"), FightCardSegment.MAIN_CARD),
            BoutResultItem("b602", "DAVAA", "ENKH", "7-2-0", "6-1-0", "LIGHTWEIGHT", 1, "KO - TKO", "1:44 - ROUND 2", "2", "01:44",
                buildStats("0", "2", "53", "82", "37", "63", "0", "1", "0", "0", "0", "0", "0:38", "2:01"), FightCardSegment.MAIN_CARD),
            BoutResultItem("b603", "BOLORMAA", "SARUUL", "6-0-0", "5-1-0", "STRAWWEIGHT", 0, "UNANIMOUS DECISION", "5:00 - ROUND 3", "3", "00:00",
                buildStats("1", "0", "149", "116", "104", "82", "2", "1", "0", "1", "0", "0", "2:47", "1:59"), FightCardSegment.PRELIM),
            BoutResultItem("b604", "GANBOLD", "UNUR", "5-2-0", "6-1-0", "WELTERWEIGHT", 1, "SUBMISSION", "2:37 - ROUND 1", "1", "02:37",
                buildStats("0", "0", "34", "47", "22", "33", "0", "2", "0", "2", "0", "0", "0:29", "2:14"), FightCardSegment.PRELIM),
            BoutResultItem("b605", "MUNKHDALAI", "ZOLBOO", "4-1-0", "3-2-0", "FEATHERWEIGHT", 0, "KO - TKO", "3:51 - ROUND 2", "2", "03:51",
                buildStats("2", "0", "92", "67", "68", "46", "1", "0", "0", "0", "0", "0", "1:44", "1:09"), FightCardSegment.EARLY_PRELIM),
            BoutResultItem("b606", "OYUN", "CHIMEG", "3-1-0", "3-2-0", "BANTAMWEIGHT", 0, "UNANIMOUS DECISION", "5:00 - ROUND 3", "3", "00:00",
                buildStats("0", "0", "126", "102", "87", "71", "1", "1", "1", "0", "0", "0", "2:15", "2:07"), FightCardSegment.EARLY_PRELIM)
        )
    )
)

@Composable
fun MfcsResultsScreen() {
    var selectedEvent by remember { mutableStateOf<EventResultSummary?>(null) }
    var selectedBoutForStats by remember { mutableStateOf<BoutResultItem?>(null) }
    var showFightStats by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ResultsBgBlack)
    ) {
        ResultsEventListView(
            events = sampleResultEvents,
            onSelectEvent = { event ->
                selectedEvent = event
                selectedBoutForStats = event.bouts.firstOrNull()
                showFightStats = false
            }
        )

        selectedEvent?.let { currentEvent ->
            val activeBout = selectedBoutForStats ?: currentEvent.bouts.first()
            if (showFightStats) {
                FightStatsDetailScreen(
                    bout = activeBout,
                    onBack = { showFightStats = false }
                )
            } else {
                EventFightcardDetailScreen(
                    event = currentEvent,
                    selectedBout = activeBout,
                    onSelectBout = { bout -> selectedBoutForStats = bout },
                    onBack = {
                        selectedEvent = null
                        selectedBoutForStats = null
                    },
                    onOpenFightStats = { showFightStats = true }
                )
            }
        }
    }
}

@Composable
private fun ResultsEventListView(
    events: List<EventResultSummary>,
    onSelectEvent: (EventResultSummary) -> Unit
) {
    val upNextEvents = events.filter { it.groupLabel == "UP NEXT" }
    val pastEvents = events.filter { it.groupLabel == "PAST EVENTS" }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (upNextEvents.isNotEmpty()) {
            item(key = "header_up_next") {
                Text(
                    text = "UP NEXT",
                    color = ResultsTextWhite,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.6.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
            items(upNextEvents, key = { it.id }) { event ->
                ResultSummaryRowCard(
                    event = event,
                    onClick = { onSelectEvent(event) }
                )
            }
        }

        if (pastEvents.isNotEmpty()) {
            item(key = "header_past_events") {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "PAST EVENTS",
                    color = ResultsTextWhite,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.6.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
            items(pastEvents, key = { it.id }) { event ->
                ResultSummaryRowCard(
                    event = event,
                    onClick = { onSelectEvent(event) }
                )
            }
        }
    }
}

@Composable
private fun ResultSummaryRowCard(
    event: EventResultSummary,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(ResultsCardDark)
            .border(1.dp, Color(0xFF242424), RoundedCornerShape(4.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = event.weekLabel,
                color = ResultsTextGray,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = "${event.totalFightersCount} FIGHTERS",
                color = ResultsAccentRed,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = event.matchupTitle,
            color = ResultsTextWhite,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun EventFightcardDetailScreen(
    event: EventResultSummary,
    selectedBout: BoutResultItem,
    onSelectBout: (BoutResultItem) -> Unit,
    onBack: () -> Unit,
    onOpenFightStats: () -> Unit
) {
    var selectedSegment by remember(event.id) { mutableStateOf(FightCardSegment.MAIN_CARD) }
    val filteredBouts = event.bouts.filter { it.segment == selectedSegment }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF2A0909), ResultsBgBlack, ResultsBgBlack)
                )
            )
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 28.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = ResultsTextWhite
                )
            }
            Text(
                text = "${event.totalFightersCount} ATHLETES",
                color = ResultsTextGray,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 16.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FighterHeroPortraitBox(
                isChampionStyle = selectedBout.winnerSide == 0,
                jerseyColor = Color(0xFF154360),
                modifier = Modifier.weight(1f)
            )

            Column(
                modifier = Modifier
                    .weight(1.3f)
                    .padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = event.eventTitle,
                    color = ResultsTextWhite,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = event.location,
                    color = ResultsTextGray,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(14.dp))
                Button(
                    onClick = onOpenFightStats,
                    shape = RoundedCornerShape(3.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ResultsAccentRed),
                    contentPadding = PaddingValues(horizontal = 18.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "FIGHT STATS",
                        color = ResultsTextWhite,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp
                    )
                }
            }

            FighterHeroPortraitBox(
                isChampionStyle = selectedBout.winnerSide == 1,
                jerseyColor = Color(0xFF2C2C2C),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = selectedBout.fighterLeft.replace(" ", "\n"),
                    color = ResultsTextWhite,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = selectedBout.weightClass,
                    color = ResultsTextGray,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = selectedBout.leftRecord,
                    color = ResultsTextGray,
                    fontSize = 11.sp
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = selectedBout.fighterRight.replace(" ", "\n"),
                    color = ResultsTextWhite,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = selectedBout.weightClass,
                    color = ResultsTextGray,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = selectedBout.rightRecord,
                    color = ResultsTextGray,
                    fontSize = 11.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(26.dp))

        Text(
            text = "FIGHTCARD",
            color = ResultsTextWhite,
            fontSize = 16.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FightCardSegment.entries.forEach { segment ->
                val isSelected = selectedSegment == segment
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            selectedSegment = segment
                            event.bouts.firstOrNull { it.segment == segment }?.let(onSelectBout)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = segment.title,
                        color = if (isSelected) ResultsTextWhite else ResultsTextGray,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.75f)
                            .height(2.dp)
                            .background(if (isSelected) ResultsAccentRed else Color.Transparent)
                    )
                }
            }
        }

        HorizontalDivider(color = Color(0xFF222222))

        Spacer(modifier = Modifier.height(14.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            filteredBouts.forEach { bout ->
                BoutResultCardRow(
                    bout = bout,
                    isSelected = bout.id == selectedBout.id,
                    onClick = { onSelectBout(bout) }
                )
            }
        }
    }
}

@Composable
private fun FighterHeroPortraitBox(
    isChampionStyle: Boolean,
    jerseyColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.height(148.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(Color(0xFF242424)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = ResultsTextWhite.copy(alpha = 0.85f),
                modifier = Modifier.size(38.dp)
            )
            if (isChampionStyle) {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = null,
                    tint = Color(0xFFFFD54F),
                    modifier = Modifier
                        .size(16.dp)
                        .align(Alignment.TopEnd)
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .width(86.dp)
                .height(68.dp)
                .clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(jerseyColor, ResultsBgBlack)
                    )
                )
        )
    }
}

@Composable
private fun BoutResultCardRow(
    bout: BoutResultItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val leftWon = bout.winnerSide == 0
    val rightWon = bout.winnerSide == 1

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(if (isSelected) Color(0xFF1A1212) else Color(0xFF101010))
            .border(
                width = 1.dp,
                color = if (isSelected) ResultsAccentRed.copy(alpha = 0.7f) else Color(0xFF1E1E1E),
                shape = RoundedCornerShape(6.dp)
            )
            .clickable { onClick() }
            .padding(vertical = 10.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1.1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = bout.fighterLeft.replace(" ", "\n"),
                color = ResultsTextWhite,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            if (leftWon) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = bout.method,
                    color = ResultsTextWhite,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = bout.roundAndTime,
                    color = ResultsTextGray,
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Row(
            modifier = Modifier.weight(1.2f),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BoutMiniAvatar(
                isWinner = leftWon,
                modifier = Modifier.weight(1f)
            )
            BoutMiniAvatar(
                isWinner = rightWon,
                modifier = Modifier.weight(1f)
            )
        }

        Column(
            modifier = Modifier.weight(1.1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = bout.fighterRight.replace(" ", "\n"),
                color = ResultsTextWhite,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            if (rightWon) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = bout.method,
                    color = ResultsTextWhite,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = bout.roundAndTime,
                    color = ResultsTextGray,
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun BoutMiniAvatar(
    isWinner: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF222222)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = ResultsTextWhite.copy(alpha = 0.8f),
                modifier = Modifier.size(32.dp)
            )
        }
        if (isWinner) {
            Box(
                modifier = Modifier
                    .padding(top = 3.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(ResultsAccentRed)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "WINNER",
                    color = ResultsTextWhite,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}

@Composable
private fun FightStatsDetailScreen(
    bout: BoutResultItem,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ResultsBgBlack)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 28.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = ResultsTextWhite
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FighterHeroPortraitBox(
                isChampionStyle = bout.winnerSide == 0,
                jerseyColor = Color(0xFF7A1212),
                modifier = Modifier.weight(1f)
            )

            Column(
                modifier = Modifier.weight(1.2f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Round",
                    color = ResultsTextWhite,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = bout.roundNumber,
                    color = ResultsTextWhite,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = bout.clockTime,
                    color = ResultsTextWhite,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = bout.method.replace(" ", "\n"),
                    color = ResultsTextWhite,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

            FighterHeroPortraitBox(
                isChampionStyle = bout.winnerSide == 1,
                jerseyColor = Color(0xFF2E2E2E),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (bout.winnerSide == 0) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(ResultsAccentRed)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "WINNER",
                            color = ResultsTextWhite,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
                Text(
                    text = bout.fighterLeft.replace(" ", "\n"),
                    color = ResultsTextWhite,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (bout.winnerSide == 1) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(ResultsAccentRed)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "WINNER",
                            color = ResultsTextWhite,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
                Text(
                    text = bout.fighterRight.replace(" ", "\n"),
                    color = ResultsTextWhite,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        bout.stats.forEach { row ->
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = row.leftValue,
                        color = ResultsTextWhite,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Start
                    )
                    Text(
                        text = row.label,
                        color = ResultsTextWhite,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.weight(1.5f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = row.rightValue,
                        color = ResultsTextWhite,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End
                    )
                }
                HorizontalDivider(
                    color = ResultsAccentRed.copy(alpha = 0.65f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        }
    }
}