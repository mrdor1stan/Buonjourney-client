package com.mrdor1stan.buonjourney.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrdor1stan.buonjourney.R
import com.mrdor1stan.buonjourney.ui.common.BodyText
import com.mrdor1stan.buonjourney.ui.common.Headline
import com.mrdor1stan.buonjourney.ui.screens.login.LoginScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.seconds

@Composable
fun ProfileScreen(
    viewModel: ProfileScreenViewModel = viewModel(factory = ProfileScreenViewModel.Factory),
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    Box(modifier = modifier) {
        if (state.isLoaderShown)
            CircularProgressIndicator(
                modifier = Modifier
                    .background(color = Color.DarkGray.copy(alpha = 0.2f))
                    .fillMaxSize()
                    .align(Alignment.Center)
            )

        Column(
            Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Headline(text = state.name)
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_profile),
                contentDescription = null,
                modifier = Modifier.size(160.dp)
            )
            Button(onClick = {
                    viewModel.showLoader()
                scope.launch {
                    withContext(Dispatchers.IO) {
                        viewModel.eraseDatabase()
                    }
                }.invokeOnCompletion {
                    scope.launch {
                        viewModel.onLogOutClick()
                    }
                }

            }) {
                BodyText(text = "Log out")
            }
        }
    }
}