package id.nusantarahook.lsposed.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.Shimmer
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

@Composable
fun Modifier.parallax(listState: LazyListState, rate: Int): Modifier {
    val offset = with(LocalDensity.current) { listState.firstVisibleItemScrollOffset }
    return this.graphicsLayer {
        translationY = offset.toFloat() / rate
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MainScreen() {
    val bannerHeight = 220.dp
    val listState = rememberLazyListState()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                scrollBehavior = scrollBehavior,
                title = {},
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Rounded.MoreVert, contentDescription = "Menu")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item(key = "banner", contentType = "banner") {
                BannerHeader(
                    modifier = Modifier
                        .fillMaxWidth()
                        .parallax(listState, rate = 2),
                    height = bannerHeight,
                    title = "NusantaraHook Lsposed",
                    subtitle = "Version 1.0"
                )
            }

            stickyHeader(key = "header", contentType = "header") {
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = "Developer",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(start = 16.dp, 8.dp, 16.dp, 8.dp)
                        )
                        HorizontalDivider(
                            Modifier,
                            DividerDefaults.Thickness,
                            DividerDefaults.color
                        )
                    }
                }
            }

            item(key = "app", contentType = "app") {
                ListAppItem()
                HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
            }

            item(key = "app2", contentType = "app") {
                ListAppItem()
                HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
            }

            item(key = "app3", contentType = "app") {
                ListAppItem()
                HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
            }

            stickyHeader(key = "header2", contentType = "header") {
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = "Application",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(start = 16.dp, 8.dp, 16.dp, 8.dp)
                        )
                        HorizontalDivider(
                            Modifier,
                            DividerDefaults.Thickness,
                            DividerDefaults.color
                        )
                    }
                }
            }

            items(count = 20, key = { "row_$it" }, contentType = { "row" }) { idx ->
                ListItem(
                    headlineContent = { Text("Sample row ${idx + 1}") },
                    supportingContent = { Text("Subtitle or meta for row ${idx + 1}") }
                )
                HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
            }
        }
    }
}

@Composable
private fun ListAppItem() {
    val component = rememberImageComponent {
        +ShimmerPlugin(
            Shimmer.Resonate(
                baseColor = MaterialTheme.colorScheme.surfaceVariant,
                highlightColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
            ),
        )
    }

    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxWidth()
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            val (imageRef, titleRef, subtitleRef) = createRefs()

            CoilImage(
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Gray, CircleShape)
                    .constrainAs(imageRef) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    },
                imageModel = { "https://fastly.picsum.photos/id/11/2500/1667.jpg?hmac=xxjFJtAPgshYkysU_aqx2sZir-kIOjNR9vx0te7GycQ" },
                component = component,
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                ),
            )

            Text(
                text = "Sample row 1",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.constrainAs(titleRef) {
                    start.linkTo(imageRef.end, margin = 8.dp)
                    top.linkTo(imageRef.top)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
            )

            Text(
                text = "Sample subtitle",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.constrainAs(subtitleRef) {
                    start.linkTo(imageRef.end, margin = 8.dp)
                    top.linkTo(titleRef.bottom)
                    end.linkTo(parent.end)
                    bottom.linkTo(imageRef.bottom)
                    width = Dimension.fillToConstraints
                }
            )
        }
    }
}

@Composable
private fun BannerHeader(
    modifier: Modifier = Modifier,
    height: Dp,
    title: String,
    subtitle: String,
) {
    val banners = remember {
        listOf(
            listOf(Color(0xFFFF8A00), Color(0xFFFF3D00)),
            listOf(Color(0xFF6A5AE0), Color(0xFF00BCD4)),
            listOf(Color(0xFF00C853), Color(0xFF0091EA))
        )
    }
    val pagerState = rememberPagerState(pageCount = { banners.size })
    rememberCoroutineScope()

    Box(modifier.height(height)) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            CoilImage(
                imageModel = { "https://fastly.picsum.photos/id/19/2500/1667.jpg?hmac=7epGozH4QjToGaBf_xb2HbFTXoV5o8n_cYzB7I4lt6g" },
                modifier = Modifier
                    .fillMaxSize(),
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                ),
                component = rememberImageComponent {
                    +ShimmerPlugin(
                        Shimmer.Resonate(
                            baseColor = MaterialTheme.colorScheme.surfaceVariant,
                            highlightColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                        ),
                    )
                },
                failure = {
                    Text(text = "image request failed.")
                }
            )
        }

        PagerDots(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            count = banners.size,
            current = pagerState.currentPage
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color(0x66000000), Color(0x99000000))
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 12.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 30.sp
                    ),
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.85f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun PagerDots(
    modifier: Modifier = Modifier,
    count: Int,
    current: Int,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        repeat(count) { index ->
            val active = index == current
            Box(
                modifier = Modifier
                    .padding(horizontal = 3.dp)
                    .size(if (active) 6.dp else 4.dp)
                    .clip(CircleShape)
                    .background(
                        if (active) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
            )
        }
    }
}