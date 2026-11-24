package com.example.mfcs_project

import android.content.Intent
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val NewsBgBlack = Color(0xFF000000)
private val NewsCardDark = Color(0xFF161616)
private val NewsSurfaceDark = Color(0xFF1E1E1E)
private val NewsAccentRed = Color(0xFFE50914)
private val NewsTextWhite = Color(0xFFFFFFFF)
private val NewsTextGray = Color(0xFFA8A8A8)

enum class NewsCategory(val label: String) {
    ALL("All"),
    MFCS("MFCS"),
    MMA("MMA"),
    FIGHTERS("Fighters"),
    EVENTS("Events"),
    RANKINGS("Rankings"),
    INTERVIEWS("Interviews")
}

enum class NewsCardStyle {
    FEATURED,
    STANDARD,
    COMPACT
}

data class NewsArticleItem(
    val id: String,
    val title: String,
    val summary: String,
    val bodyParagraphs: List<String>,
    val category: NewsCategory,
    val author: String,
    val dateLabel: String,
    val readTime: String,
    val isBreaking: Boolean = false,
    val isTrending: Boolean = false,
    val cardStyle: NewsCardStyle = NewsCardStyle.STANDARD,
    val bannerColor: Color,
    val secondaryColor: Color,
    val tags: List<String> = emptyList()
)

private val sampleNewsArticles = listOf(
    NewsArticleItem(
        id = "n1",
        title = "MFCS-ийн дараагийн том эвент албан ёсоор зарлагдлаа",
        summary = "Улаанбаатар хотод болох MFCS 12 эвентийн гол тулаанчид болон хэвлэлийн бага хурлын тов зарлагдлаа.",
        bodyParagraphs = listOf(
            "Монголын тулааны спортын хөгжилд шинэ хуудас нээх MFCS 12 эвент Улаанбаатар хотын их танхимд зохион байгуулагдахаар албан ёсоор зарлагдлаа.",
            "Үндсэн тулаанд хөнгөн жингийн шилдэг хоёр тулаанч Делгэрмөрөн болон Тэмүүжин нар октагонд учран зодолдох бөгөөд урьдчилсан болон үндсэн кардад нийт 20 тамирчин хүч үзнэ.",
            "Хэвлэлийн бага хурал болон жин үзэх ёслол тэмцээний өмнөх өдөр шууд дамжуулалтаар үзэгчдэд хүрнэ."
        ),
        category = NewsCategory.MFCS,
        author = "MFCS Editorial Desk",
        dateLabel = "November 20",
        readTime = "3 min read",
        isBreaking = true,
        isTrending = true,
        cardStyle = NewsCardStyle.FEATURED,
        bannerColor = Color(0xFF7D0A10),
        secondaryColor = Color(0xFF1A1C24),
        tags = listOf("MFCS 12", "Delgermurun", "Temuujin")
    ),
    NewsArticleItem(
        id = "n2",
        title = "Аваргын дараагийн өрсөлдөгч тодорлоо",
        summary = "Хүнд ба хагас хүнд жингийн ангилалд сүүлийн 3 тулаанд дараалан ялалт байгуулсан тулаанч бүсийн төлөө өрсөлдөнө.",
        bodyParagraphs = listOf(
            "Сүүлийн хоёр эвентийн турш өрсөлдөгчөө эхний болон хоёрдугаар раундад буулган авсан тулаанч аваргын бүсний төлөөх албан ёсны саналыг хүлээн авлаа.",
            "Дасгалжуулагчийн багийн мэдээлснээр бэлтгэл төлөвлөгөө эрчимтэй үргэлжилж байгаа бөгөөд энэ удаагийн бэлтгэлд дэвжээний барилдаан болон богино зайн цохилтын нарийвчлалд онцгой анхаарч байна."
        ),
        category = NewsCategory.FIGHTERS,
        author = "Batbayar G.",
        dateLabel = "2 hours ago",
        readTime = "4 min read",
        isTrending = true,
        cardStyle = NewsCardStyle.STANDARD,
        bannerColor = Color(0xFF4A1313),
        secondaryColor = Color(0xFF141414),
        tags = listOf("Title Contender", "Fighters", "MFCS")
    ),
    NewsArticleItem(
        id = "n3",
        title = "MFCS 15 Fight Card шинэчлэгдлээ",
        summary = "Main Card болон Prelim тулаануудын жингийн ангилал, шинэ нэрс нэмэгдлээ.",
        bodyParagraphs = listOf(
            "MFCS 15 тэмцээний тулааны хуваарьт шинэ тулаанууд баталгаажлаа.",
            "Үзэгчдийн хүлээлт өндөр байгаа тулаануудад залуу тамирчид болон туршлагатай ахмад тулаанчдын халз тулаан багтсан байна."
        ),
        category = NewsCategory.EVENTS,
        author = "Matchmaking Team",
        dateLabel = "30 min ago",
        readTime = "2 min read",
        isTrending = true,
        cardStyle = NewsCardStyle.COMPACT,
        bannerColor = Color(0xFF1F2937),
        secondaryColor = Color(0xFF121212),
        tags = listOf("Fight Card", "Events", "MFCS 15")
    ),
    NewsArticleItem(
        id = "n4",
        title = "Pound-for-Pound болон жингийн зэрэглэлийн шинэчлэл",
        summary = "Сүүлийн тэмцээний үр дүнгийн дараах тамирчдын эрэмбэ хэрхэн өөрчлөгдсөнийг харна уу.",
        bodyParagraphs = listOf(
            "Энэ долоо хоногийн үр дүнгээс хамааран хөнгөн болон хагас дунд жингийн эхний тавд өөрчлөлт орлоо.",
            "Шинжээчдийн зөвлөл тулааны идэвх, ялалтын арга болон өрсөлдөгчийн чансааг харгалзан шинэ жагсаалтыг гаргасан байна."
        ),
        category = NewsCategory.RANKINGS,
        author = "Analytics Desk",
        dateLabel = "5 hours ago",
        readTime = "5 min read",
        cardStyle = NewsCardStyle.STANDARD,
        bannerColor = Color(0xFF2B2D42),
        secondaryColor = Color(0xFF121212),
        tags = listOf("Rankings", "Pound-for-Pound", "MMA")
    ),
    NewsArticleItem(
        id = "n5",
        title = "Ярилцлага: 'Би өдөр бүр илүү хурдан, илүү ухаалаг болохын төлөө ажилладаг'",
        summary = "Октагоны өмнөх онцгой ярилцлагад тулаанч сэтгэл зүйн бэлтгэл болон жин хасалтын талаар ярилаа.",
        bodyParagraphs = listOf(
            "Тулааны өмнөх сүүлийн долоо хоногт тамирчин сэтгэл зүйн төвлөрөл хамгийн чухал гэдгийг онцоллоо.",
            "'Октагонд орсон мөчөөс эхлэн бүх зүйл тодорхой болдог. Бид бэлтгэл дээр хийсэн бүхнээ харуулахад бэлэн' хэмээн тэрээр хэлсэн юм."
        ),
        category = NewsCategory.INTERVIEWS,
        author = "Saruul E.",
        dateLabel = "Yesterday",
        readTime = "6 min read",
        cardStyle = NewsCardStyle.STANDARD,
        bannerColor = Color(0xFF5C1616),
        secondaryColor = Color(0xFF151515),
        tags = listOf("Interview", "Mindset", "Fighters")
    ),
    NewsArticleItem(
        id = "n6",
        title = "Олон улсын MMA тэмцээнүүдийн энэ долоо хоногийн тойм",
        summary = "Ази болон дэлхийн MMA дэвжээнд өрнөсөн онцлох үйл явдлууд болон нокаутуудын тойм.",
        bodyParagraphs = listOf(
            "Энэ амралтын өдрүүдэд дэлхийн хэд хэдэн томоохон MMA холбооны тэмцээнүүд болж, сонирхолтой шийдвэрүүд гарлаа.",
            "Босоо тулаан болон партерын шилжилт хурдассан нь сүүлийн үеийн тулаануудын гол онцлог болж байна."
        ),
        category = NewsCategory.MMA,
        author = "Combat Wire",
        dateLabel = "2 days ago",
        readTime = "4 min read",
        cardStyle = NewsCardStyle.COMPACT,
        bannerColor = Color(0xFF3A1C1C),
        secondaryColor = Color(0xFF111111),
        tags = listOf("MMA", "World MMA", "Recap")
    ),
    NewsArticleItem(
        id = "n7",
        title = "Бэлтгэлийн баазын шинэ тоног төхөөрөмж ба нөхөн сэргээлт",
        summary = "Тамирчдын гэмтэл бэртлээс сэргийлэх, сэргэлтийг хурдасгах шинэ лаборатори ашиглалтад орлоо.",
        bodyParagraphs = listOf(
            "Орчин үеийн нөхөн сэргээх тоног төхөөрөмжүүд нь тамирчдын ачааллыг зөв хуваарилахад тусална.",
            "Тэмцээний дараах сэргэлт сайжирснаар тамирчид илүү тогтвортой формтой тулалдах боломж бүрдэж байна."
        ),
        category = NewsCategory.MFCS,
        author = "MFCS Performance",
        dateLabel = "3 days ago",
        readTime = "3 min read",
        cardStyle = NewsCardStyle.COMPACT,
        bannerColor = Color(0xFF1E3A5F),
        secondaryColor = Color(0xFF111111),
        tags = listOf("MFCS", "Recovery", "Training")
    )
)

@Composable
fun MfcsNewsScreen() {
    var selectedCategory by remember { mutableStateOf(NewsCategory.ALL) }
    var searchQuery by remember { mutableStateOf("") }
    var showSearchBar by remember { mutableStateOf(false) }
    var showBookmarkedOnly by remember { mutableStateOf(false) }
    var bookmarkedIds by remember { mutableStateOf(setOf("n1")) }
    var selectedArticle by remember { mutableStateOf<NewsArticleItem?>(null) }
    var visibleLatestCount by remember { mutableIntStateOf(5) }

    val filteredArticles = remember(selectedCategory, searchQuery, showBookmarkedOnly, bookmarkedIds) {
        sampleNewsArticles.filter { article ->
            val matchesCategory = selectedCategory == NewsCategory.ALL || article.category == selectedCategory
            val matchesBookmark = !showBookmarkedOnly || bookmarkedIds.contains(article.id)
            val query = searchQuery.trim().lowercase()
            val matchesSearch = query.isEmpty() ||
                    article.title.lowercase().contains(query) ||
                    article.summary.lowercase().contains(query) ||
                    article.category.label.lowercase().contains(query) ||
                    article.tags.any { it.lowercase().contains(query) }
            matchesCategory && matchesBookmark && matchesSearch
        }
    }

    val featuredArticle = remember(filteredArticles) {
        filteredArticles.firstOrNull { it.isBreaking || it.cardStyle == NewsCardStyle.FEATURED }
            ?: filteredArticles.firstOrNull()
    }

    val trendingArticles = remember {
        sampleNewsArticles.filter { it.isTrending }
    }

    val latestArticles = remember(filteredArticles, featuredArticle, visibleLatestCount) {
        val base = if (featuredArticle != null && filteredArticles.size > 1) {
            filteredArticles.filterNot { it.id == featuredArticle.id }
        } else {
            filteredArticles
        }
        base.take(visibleLatestCount)
    }

    fun toggleBookmark(articleId: String) {
        bookmarkedIds = if (bookmarkedIds.contains(articleId)) {
            bookmarkedIds - articleId
        } else {
            bookmarkedIds + articleId
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NewsBgBlack)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 8.dp, bottom = 28.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // News Header: MFCS logo, NEWS title, Search, Notification / Bookmarks
            item(key = "news_header") {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(NewsAccentRed)
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "MFCS",
                                    color = NewsTextWhite,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.2.sp
                                )
                            }
                            Text(
                                text = "NEWS",
                                color = NewsTextWhite,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = {
                                    showSearchBar = !showSearchBar
                                    if (!showSearchBar) searchQuery = ""
                                }
                            ) {
                                Icon(
                                    imageVector = if (showSearchBar) Icons.Default.Close else Icons.Default.Search,
                                    contentDescription = "Search News",
                                    tint = if (showSearchBar) NewsAccentRed else NewsTextWhite
                                )
                            }

                            IconButton(
                                onClick = { showBookmarkedOnly = !showBookmarkedOnly }
                            ) {
                                Icon(
                                    imageVector = if (showBookmarkedOnly) Icons.Default.Bookmark else Icons.Default.Notifications,
                                    contentDescription = "Bookmarks & Alerts",
                                    tint = if (showBookmarkedOnly) NewsAccentRed else NewsTextWhite
                                )
                            }
                        }
                    }

                    if (showSearchBar) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            singleLine = true,
                            placeholder = {
                                Text(
                                    text = "Search fighter, event, news...",
                                    color = NewsTextGray,
                                    fontSize = 13.sp
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    tint = NewsTextGray
                                )
                            },
                            trailingIcon = {
                                if (searchQuery.isNotEmpty()) {
                                    IconButton(onClick = { searchQuery = "" }) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Clear",
                                            tint = NewsTextGray
                                        )
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = NewsTextWhite,
                                unfocusedTextColor = NewsTextWhite,
                                focusedContainerColor = NewsCardDark,
                                unfocusedContainerColor = NewsCardDark,
                                focusedBorderColor = NewsAccentRed,
                                unfocusedBorderColor = Color(0xFF2B2B2B)
                            )
                        )
                    }

                    if (showBookmarkedOnly) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .background(NewsSurfaceDark)
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Showing saved / bookmarked articles (${bookmarkedIds.size})",
                                color = NewsTextWhite,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Show All",
                                color = NewsAccentRed,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.clickable { showBookmarkedOnly = false }
                            )
                        }
                    }
                }
            }

            // Category filter chips
            item(key = "news_categories") {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(NewsCategory.entries, key = { it.name }) { category ->
                        val isSelected = selectedCategory == category
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(if (isSelected) NewsTextWhite else NewsSurfaceDark)
                                .border(
                                    1.dp,
                                    if (isSelected) NewsTextWhite else Color(0xFF2A2A2A),
                                    RoundedCornerShape(20.dp)
                                )
                                .clickable { selectedCategory = category }
                                .padding(horizontal = 14.dp, vertical = 7.dp)
                        ) {
                            Text(
                                text = category.label,
                                color = if (isSelected) NewsBgBlack else NewsTextWhite,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Featured / Breaking News
            featuredArticle?.let { hero ->
                item(key = "news_featured_${hero.id}") {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = if (hero.isBreaking) "BREAKING NEWS" else "FEATURED STORY",
                            color = NewsAccentRed,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp
                        )

                        FeaturedNewsHeroCard(
                            article = hero,
                            isBookmarked = bookmarkedIds.contains(hero.id),
                            onReadClick = { selectedArticle = hero },
                            onBookmarkClick = { toggleBookmark(hero.id) }
                        )
                    }
                }
            }

            // Trending News
            if (selectedCategory == NewsCategory.ALL && searchQuery.isBlank() && !showBookmarkedOnly) {
                item(key = "news_trending") {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalFireDepartment,
                                contentDescription = null,
                                tint = NewsAccentRed,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "TRENDING",
                                color = NewsTextWhite,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 0.8.sp
                            )
                        }

                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(trendingArticles, key = { "tr_${it.id}" }) { article ->
                                TrendingNewsCard(
                                    article = article,
                                    onClick = { selectedArticle = article }
                                )
                            }
                        }
                    }
                }
            }

            // Latest News section
            item(key = "news_latest_header") {
                Text(
                    text = "LATEST NEWS",
                    color = NewsTextWhite,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.8.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            if (latestArticles.isEmpty()) {
                item(key = "news_empty") {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Тохирох мэдээ олдсонгүй.",
                            color = NewsTextGray,
                            fontSize = 14.sp
                        )
                    }
                }
            } else {
                items(latestArticles, key = { "latest_${it.id}" }) { article ->
                    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                        when (article.cardStyle) {
                            NewsCardStyle.FEATURED -> FeaturedNewsHeroCard(
                                article = article,
                                isBookmarked = bookmarkedIds.contains(article.id),
                                onReadClick = { selectedArticle = article },
                                onBookmarkClick = { toggleBookmark(article.id) }
                            )
                            NewsCardStyle.STANDARD -> StandardNewsCard(
                                article = article,
                                isBookmarked = bookmarkedIds.contains(article.id),
                                onClick = { selectedArticle = article },
                                onBookmarkClick = { toggleBookmark(article.id) }
                            )
                            NewsCardStyle.COMPACT -> CompactNewsCard(
                                article = article,
                                isBookmarked = bookmarkedIds.contains(article.id),
                                onClick = { selectedArticle = article },
                                onBookmarkClick = { toggleBookmark(article.id) }
                            )
                        }
                    }
                }

                if (visibleLatestCount < sampleNewsArticles.size) {
                    item(key = "news_load_more") {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 6.dp)
                        ) {
                            OutlinedButton(
                                onClick = { visibleLatestCount += 4 },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(44.dp),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "Load more",
                                    color = NewsTextWhite,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        // News Detail Screen
        selectedArticle?.let { article ->
            val relatedArticles = remember(article.id) {
                sampleNewsArticles
                    .filterNot { it.id == article.id }
                    .sortedByDescending { if (it.category == article.category) 1 else 0 }
                    .take(3)
            }

            NewsArticleDetailScreen(
                article = article,
                relatedArticles = relatedArticles,
                isBookmarked = bookmarkedIds.contains(article.id),
                onToggleBookmark = { toggleBookmark(article.id) },
                onSelectRelated = { selectedArticle = it },
                onBack = { selectedArticle = null }
            )
        }
    }
}

@Composable
private fun FeaturedNewsHeroCard(
    article: NewsArticleItem,
    isBookmarked: Boolean,
    onReadClick: () -> Unit,
    onBookmarkClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(NewsCardDark)
            .border(1.dp, Color(0xFF262626), RoundedCornerShape(10.dp))
            .clickable { onReadClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(195.dp)
                .background(
                    Brush.linearGradient(
                        colors = listOf(article.bannerColor, article.secondaryColor, NewsBgBlack)
                    )
                )
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopStart),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    if (article.isBreaking) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(NewsAccentRed)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "BREAKING NEWS",
                                color = NewsTextWhite,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Black.copy(alpha = 0.7f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = article.category.label.uppercase(),
                            color = NewsTextWhite,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                IconButton(
                    onClick = onBookmarkClick,
                    modifier = Modifier.size(34.dp)
                ) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark",
                        tint = if (isBookmarked) NewsAccentRed else NewsTextWhite
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
            ) {
                Text(
                    text = "MFCS EDITORIAL",
                    color = NewsAccentRed,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = article.title,
                    color = NewsTextWhite,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    lineHeight = 22.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = article.summary,
                color = NewsTextGray,
                fontSize = 12.sp,
                lineHeight = 17.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${article.dateLabel} · ${article.readTime}",
                    color = NewsTextGray,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Button(
                    onClick = onReadClick,
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NewsAccentRed),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Read article",
                        color = NewsTextWhite,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun StandardNewsCard(
    article: NewsArticleItem,
    isBookmarked: Boolean,
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(NewsCardDark)
            .border(1.dp, Color(0xFF222222), RoundedCornerShape(8.dp))
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(142.dp)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(article.bannerColor, article.secondaryColor)
                    )
                )
                .padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .clip(RoundedCornerShape(3.dp))
                    .background(NewsAccentRed)
                    .padding(horizontal = 7.dp, vertical = 3.dp)
            ) {
                Text(
                    text = "${article.category.label.uppercase()} NEWS",
                    color = NewsTextWhite,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            IconButton(
                onClick = onBookmarkClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(32.dp)
            ) {
                Icon(
                    imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = "Bookmark",
                    tint = if (isBookmarked) NewsAccentRed else NewsTextWhite,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = article.title,
                color = NewsTextWhite,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 20.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = article.summary,
                color = NewsTextGray,
                fontSize = 12.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${article.dateLabel} · ${article.readTime}",
                color = NewsTextGray,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
private fun CompactNewsCard(
    article: NewsArticleItem,
    isBookmarked: Boolean,
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(NewsCardDark)
            .border(1.dp, Color(0xFF202020), RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(76.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(article.bannerColor, article.secondaryColor)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = article.category.label.take(3).uppercase(),
                color = NewsTextWhite,
                fontSize = 12.sp,
                fontWeight = FontWeight.Black
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = article.title,
                color = NewsTextWhite,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 17.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${article.category.label} · ${article.dateLabel} · ${article.readTime}",
                color = NewsTextGray,
                fontSize = 11.sp
            )
        }

        IconButton(
            onClick = onBookmarkClick,
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                contentDescription = "Bookmark",
                tint = if (isBookmarked) NewsAccentRed else NewsTextGray,
                modifier = Modifier.size(19.dp)
            )
        }
    }
}

@Composable
private fun TrendingNewsCard(
    article: NewsArticleItem,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(220.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(NewsCardDark)
            .border(1.dp, Color(0xFF242424), RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = article.category.label.uppercase(),
                color = NewsAccentRed,
                fontSize = 10.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = article.readTime,
                color = NewsTextGray,
                fontSize = 10.sp
            )
        }

        Text(
            text = article.title,
            color = NewsTextWhite,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 17.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = article.dateLabel,
            color = NewsTextGray,
            fontSize = 10.sp
        )
    }
}

@Composable
private fun NewsArticleDetailScreen(
    article: NewsArticleItem,
    relatedArticles: List<NewsArticleItem>,
    isBookmarked: Boolean,
    onToggleBookmark: () -> Unit,
    onSelectRelated: (NewsArticleItem) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NewsBgBlack)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
    ) {
        // Back button болон зөвхөн Share + Bookmark action
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = NewsTextWhite
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, "MFCS News: ${article.title}")
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Share Article"))
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = NewsTextWhite
                    )
                }

                IconButton(onClick = onToggleBookmark) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark",
                        tint = if (isBookmarked) NewsAccentRed else NewsTextWhite
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = article.category.label.uppercase(),
                color = NewsAccentRed,
                fontSize = 12.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp
            )

            Text(
                text = article.title,
                color = NewsTextWhite,
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                lineHeight = 28.sp
            )

            Text(
                text = article.summary,
                color = NewsTextGray,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )

            Text(
                text = "By ${article.author} · ${article.dateLabel} · ${article.readTime}",
                color = NewsTextGray,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )

            // Hero image box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(205.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(article.bannerColor, article.secondaryColor, NewsBgBlack)
                        )
                    )
                    .border(1.dp, Color(0xFF262626), RoundedCornerShape(10.dp))
                    .padding(14.dp)
            ) {
                Text(
                    text = "MFCS · ${article.category.label.uppercase()}",
                    color = NewsTextWhite.copy(alpha = 0.85f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.BottomStart)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Article body paragraphs
            article.bodyParagraphs.forEach { paragraph ->
                Text(
                    text = paragraph,
                    color = NewsTextWhite.copy(alpha = 0.92f),
                    fontSize = 15.sp,
                    lineHeight = 24.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color(0xFF242424))
            Spacer(modifier = Modifier.height(8.dp))

            // Related articles
            Text(
                text = "RELATED ARTICLES",
                color = NewsTextWhite,
                fontSize = 14.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 0.8.sp
            )

            relatedArticles.forEach { related ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(NewsCardDark)
                        .clickable { onSelectRelated(related) }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(64.dp)
                            .height(48.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(related.bannerColor, related.secondaryColor)
                                )
                            )
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = related.title,
                            color = NewsTextWhite,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${related.category.label} · ${related.readTime}",
                            color = NewsTextGray,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}