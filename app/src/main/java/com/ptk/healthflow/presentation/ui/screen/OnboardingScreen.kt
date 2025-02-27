package com.ptk.healthflow.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ptk.healthflow.R
import com.ptk.healthflow.domain.model.OnboardingPage
import com.ptk.healthflow.presentation.ui.components.Indicator
import com.ptk.healthflow.presentation.ui.components.OnboardingPageContent
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    val pages = listOf(
        OnboardingPage("Stay Healthy & Active", R.drawable.onboarding1),
        OnboardingPage("Monitor Your Well-being", R.drawable.onboarding2),
        OnboardingPage("Get Medical Support", R.drawable.onboarding3),
        OnboardingPage("Stay Motivated Every Day", R.drawable.onboarding4),
    )

    val pagerState = rememberPagerState { pages.size }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
                .padding(16.dp)
        ) {
            HorizontalPager(state = pagerState) { page ->
                OnboardingPageContent(
                    page = pages[page], Modifier
                        .fillMaxSize()
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(64.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1F)
            ) {
                pages.forEachIndexed { index, _ ->
                    Indicator(isSelected = index == pagerState.currentPage)
                }
            }
            val myScope = rememberCoroutineScope()
            TextButton(
                onClick = {
                    if (pagerState.currentPage == pages.lastIndex) {
                        onFinish()
                    } else {
                        myScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                shape = RoundedCornerShape(32.dp),
                modifier = Modifier
                    .defaultMinSize(100.dp)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(32.dp)
                    )
            ) {
                Text(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    text = if (pagerState.currentPage == pages.lastIndex) "Get Started" else "Skip"
                )
            }
        }
    }
}