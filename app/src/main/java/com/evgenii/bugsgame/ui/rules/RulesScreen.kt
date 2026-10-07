package com.evgenii.bugsgame.ui.rules

import android.webkit.WebView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.evgenii.bugsgame.R

@Composable
fun RulesScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val html = remember {
        context.resources.openRawResource(R.raw.rules)
            .bufferedReader()
            .use { it.readText() }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.rules_title),
            style = MaterialTheme.typography.headlineMedium
        )

        AndroidView(
            modifier = Modifier.weight(1f),
            factory = { ctx ->
                WebView(ctx).apply {
                    loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)
                }
            }
        )
    }
}