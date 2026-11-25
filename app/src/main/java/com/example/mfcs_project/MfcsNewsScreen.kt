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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Schedule
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

private val NewsBgBlack = Color(0xFF000000)
private val NewsCardDark = Color(0xFF141414)
private val NewsSheetDark = Color(0xFF121212)
private val NewsAccentRed = Color(0xFFE50914)
private val NewsDeepRed = Color(0xFF4A070B)
private val NewsGold = Color(0xFFF59E0B)
private val NewsTextWhite = Color(0xFFFFFFFF)
private val NewsTextGray = Color(0xFFA6A6A6)
private val NewsTextMuted = Color(0xFF6E6E6E)
private val NewsBorderGray = Color(0xFF262626)

enum class NewsCategory(val label: String) {
    ALL("ALL NEWS"),
    ANNOUNCEMENTS("BOUTS"),
    TRAINING("CAMPS"),
    PROMO("OFFICIAL")
}

data class MfcsNewsArticle(
    val id: String,
    val badge: String,
    val title: String,
    val summary: String,
    val fullBody: String,
    val timeAgo: String,
    val readTime: String,
    val category: NewsCategory,
    val isFeatured: Boolean = false
)

private val mfcsNewsList = listOf(
    MfcsNewsArticle(
        id = "news_1",
        badge = "MAIN EVENT",
        title = "MFCS 12: ДЭЛГЭРМӨРӨН vs БАТ-ОРГИЛ ТУЛААН АЛБАН ЁСООР ЗАРЛАГДЛАА",
        summary = "Хөнгөн жингийн аваргын бүсний төлөөх 5 үеийн оргил тулаан Улаанбаатар хотод болно.",
        fullBody = "MFCS холбооноос дараагийн томоохон эвент болох MFCS 12 тэмцээний гол тулааныг албан ёсоор зарлалаа. Хурц техник, өндөр темпээрээ ялгардаг хоёр шилдэг тамирчин октагонд учраа таарч, 5 үеийн турш хүчээ үзнэ.\n\nЖин үзэх ёслол болон хэвлэлийн бага хурал тэмцээнээс нэг өдрийн өмнө олон нийтэд нээлттэй зохион байгуулагдана.",
        timeAgo = "2 ЦАГИЙН ӨМНӨ",
        readTime = "3 мин унших",
        category = NewsCategory.ANNOUNCEMENTS,
        isFeatured = true
    ),
    MfcsNewsArticle(
        id = "news_2",
        badge = "TRAINING CAMP",
        title = "ШИНЭБААТАР БЭЛТГЭЛИЙН СҮҮЛИЙН ДОЛОО ХОНОГТ ОРЛОО",
        summary = "61 кг-ийн ангиллын тулаанч спарринг болон кардио ачааллыг дээд түвшинд хүргэжээ.",
        fullBody = "Бэлтгэлийн баазад өнгөрүүлсэн долоон долоо хоногийн дараа тамирчны жингийн төлөвлөгөө яг хуваарийн дагуу явж байна. Дасгалжуулагчийн баг босоо цохилтын хамгаалалт болон клинчээс шилжих хөдөлгөөн дээр голлон ажиллаж байгаа ажээ.",
        timeAgo = "5 ЦАГИЙН ӨМНӨ",
        readTime = "2 мин унших",
        category = NewsCategory.TRAINING
    ),
    MfcsNewsArticle(
        id = "news_3",
        badge = "OFFICIAL",
        title = "MFCS FIGHT PASS ДАХЬ АРХИВ ТУЛААНУУДЫН ЧАНАР ШИНЭЧЛЭГДЛЭЭ",
        summary = "Өмнөх тэмцээнүүдийн сонгодог тулаануудыг илүү тод дүрс, цэвэр дуутайгаар үзэх боломжтой.",
        fullBody = "Фэнүүдийн хүсэлтээр MFCS-ийн өмнөх дугааруудын онцлох тулаануудыг Fight Pass сан руу үе шаттайгаар байршуулж байна. Нокаут, сабмишн болон бүтэн тулааны бичлэгүүдийг ангиллаар нь хялбар шүүж үзэх боломжтой.",
        timeAgo = "ӨЧИГДӨР",
        readTime = "2 мин унших",
        category = NewsCategory.PROMO
    ),
    MfcsNewsArticle(
        id = "news_4",
        badge = "FIGHT CARD",
        title = "MFCS 12 CO-MAIN: ХҮНД ЖИНГИЙН ШИНЭ СОРИЛТ",
        summary = "Дараалсан ялалттай хоёр тулаанч октагонд анх удаа нүүр тулна.",
        fullBody = "Хүнд жингийн ангилалд сүүлийн тулаануудаа эрт дуусгасан тамирчдын тулаан үзэгчдийн хүлээлтэд хэдийнээ ороод байна. Энэхүү тулааны ялагч дивизионы топ өрсөлдөгчтэй тулах эрхэд нэг алхам ойртох юм.",
        timeAgo = "2 ӨДРИЙН ӨМНӨ",
        readTime = "3 мин унших",
        category = NewsCategory.ANNOUNCEMENTS
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MfcsNewsScreen() {
    var selectedCategory by remember { mutableStateOf(NewsCategory.ALL) }
    var selectedArticle by remember { mutableStateOf<MfcsNewsArticle?>(null) }

    val navBarPadding = WindowInsets.navigationBars.asPaddingValues()

    val filteredArticles = remember(selectedCategory) {
        if (selectedCategory == NewsCategory.ALL) {
            mfcsNewsList
        } else {
            mfcsNewsList.filter { it.category == selectedCategory }
        }
    }

    val featuredArticle = remember(filteredArticles) {
        filteredArticles.firstOrNull { it.isFeatured } ?: filteredArticles.firstOrNull()
    }

    val secondaryArticles = remember(filteredArticles, featuredArticle) {
        if (featuredArticle == null) emptyList()
        else filteredArticles.filter { it.id != featuredArticle.id }
    }

    selectedArticle?.let { article ->
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            onDismissRequest = { selectedArticle = null },
            sheetState = sheetState,
            containerColor = NewsSheetDark,
            contentColor = NewsTextWhite,
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
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(NewsAccentRed.copy(alpha = 0.2f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = article.badge,
                            color = NewsAccentRed,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Хаах",
                        tint = NewsTextGray,
                        modifier = Modifier
                            .size(22.dp)
                            .clickable { selectedArticle = null }
                    )
                }

                Text(
                    text = article.title,
                    color = NewsTextWhite,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    lineHeight = 23.sp
                )

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = article.timeAgo,
                        color = NewsGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "• ${article.readTime}",
                        color = NewsTextMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                HorizontalDivider(color = NewsBorderGray)

                Text(
                    text = article.fullBody,
                    color = NewsTextGray,
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NewsBgBlack)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(NewsBgBlack)
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = "FIGHT WIRE & UPDATES",
                    color = NewsAccentRed,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "MFCS NEWS",
                    color = NewsTextWhite,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.5.sp
                )
            }

            Icon(
                imageVector = Icons.Default.Newspaper,
                contentDescription = null,
                tint = NewsTextGray,
                modifier = Modifier.size(22.dp)
            )
        }

        HorizontalDivider(color = NewsBorderGray.copy(alpha = 0.7f), thickness = 1.dp)

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(NewsBgBlack),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 12.dp,
                bottom = navBarPadding.calculateBottomPadding() + 24.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item(key = "news_categories") {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(NewsCategory.entries) { category ->
                        val isSelected = category == selectedCategory
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isSelected) NewsAccentRed else NewsCardDark)
                                .border(
                                    1.dp,
                                    if (isSelected) NewsAccentRed else NewsBorderGray,
                                    RoundedCornerShape(6.dp)
                                )
                                .clickable { selectedCategory = category }
                                .padding(horizontal = 13.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = category.label,
                                color = NewsTextWhite,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.4.sp
                            )
                        }
                    }
                }
            }

            featuredArticle?.let { hero ->
                item(key = "hero_${hero.id}") {
                    FeaturedNewsCard(
                        article = hero,
                        onClick = { selectedArticle = hero }
                    )
                }
            }

            items(secondaryArticles, key = { it.id }) { article ->
                NewsArticleRowCard(
                    article = article,
                    onClick = { selectedArticle = article }
                )
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}

@Composable
private fun FeaturedNewsCard(
    article: MfcsNewsArticle,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(NewsDeepRed, NewsCardDark)
                )
            )
            .border(1.dp, NewsAccentRed.copy(alpha = 0.75f), RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(NewsAccentRed)
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(
                    text = article.badge,
                    color = NewsTextWhite,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.5.sp
                )
            }

            Text(
                text = article.timeAgo,
                color = NewsGold,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = article.title,
            color = NewsTextWhite,
            fontSize = 17.sp,
            fontWeight = FontWeight.Black,
            lineHeight = 22.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = article.summary,
            color = NewsTextGray,
            fontSize = 12.sp,
            lineHeight = 17.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            modifier = Modifier.padding(top = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "ДЭЛГЭРЭНГҮЙ УНШИХ",
                color = NewsTextWhite,
                fontSize = 11.sp,
                fontWeight = FontWeight.Black
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = NewsAccentRed,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

@Composable
private fun NewsArticleRowCard(
    article: MfcsNewsArticle,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(NewsCardDark)
            .border(1.dp, NewsBorderGray, RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(NewsAccentRed.copy(alpha = 0.16f))
                    .padding(horizontal = 7.dp, vertical = 3.dp)
            ) {
                Text(
                    text = article.badge,
                    color = NewsAccentRed,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Black
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    tint = NewsTextMuted,
                    modifier = Modifier.size(12.dp)
                )
                Text(
                    text = "${article.timeAgo} • ${article.readTime}",
                    color = NewsTextMuted,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Text(
            text = article.title,
            color = NewsTextWhite,
            fontSize = 14.sp,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 19.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = article.summary,
            color = NewsTextGray,
            fontSize = 12.sp,
            lineHeight = 17.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}