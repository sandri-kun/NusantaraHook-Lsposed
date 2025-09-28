package id.nusantarahook.lsposed.screen.debug

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrashScreen(
    errorMessage: String,
    onBack: () -> Unit = {},
    onShare: () -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    scrolledContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                ),
                title = { Text("Crash report") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onShare) {
                        Icon(Icons.Filled.Share, contentDescription = "Share")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            CrashScreenLazy(errorMessage)
        }
    }
}

@Composable
fun CrashScreenLazy(errorMessage: String) {
    val chunks = remember(errorMessage) {
        errorMessage.lineSequence().chunked(200).map { it.joinToString("\n") }.toList()
    }

    val listState = rememberSaveable(saver = LazyListState.Saver) { LazyListState() }
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize(),
    ) {
        items(chunks.size, contentType = { "text" }) { i ->
            SelectionContainer {
                Text(chunks[i], style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    CrashScreen("Lorem ipsum team")
}

@Composable
fun CrashesScreen(errorMessage: String, innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(start = 16.dp, end = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        SelectionContainer {
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}