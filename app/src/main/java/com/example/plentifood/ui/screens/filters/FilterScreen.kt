package com.example.plentifood.ui.screens.filters

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plentifood.ui.composables.MultiSelectedButton
import com.example.plentifood.ui.composables.PrimaryButton
import com.example.plentifood.ui.composables.SingleChoiceSegmentedButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(
    viewModel: FilterViewModel = viewModel(),
    onClickBack: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
                title = {
                    Text(
                        "Filters",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onClickBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go Back Icon"
                        )
                    }
                },
                actions = {
                    Text(
                        "Reset",
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable {

                            },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        ScrollContent(innerPadding)
    }
}


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ScrollContent(padding: PaddingValues) {
    val scrollState = rememberScrollState()

    val dayOptions =
        listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    val dayChecked =
        remember { mutableStateListOf(false, false, false, false, false, false, false) }

    val orgOptions = listOf("Food Bank", "Church", "Nonprofit", "Community Center", "Others")
    val orgChecked = remember { mutableStateListOf(false, false, false, false, false) }

    val serviceOptions = listOf("Food Bank", "Meal")
    var selectedServiceIndex by rememberSaveable { mutableStateOf(0) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)


        ) {
            Text("Day of Service")
            MultiSelectedButton(
                dayOptions,
                dayChecked,
                onCheckedChange = { index, value ->
                    dayChecked[index] = value
                })

            Text("Organization Type")
            MultiSelectedButton(
                orgOptions,
                orgChecked,
                onCheckedChange = { index, value ->
                    orgChecked[index] = value
                })

            Text("Service Type")
            SingleChoiceSegmentedButton(
                serviceOptions,
                selectedServiceIndex,
                onSelectedIndexChange = { selectedServiceIndex = it }
            )


            Text("Radius")
            Row() {
                Text("Within",
                    style = MaterialTheme.typography.bodyMedium
                    )

                Spacer(modifier = Modifier.width(12.dp))

                Text("miles",
                    style = MaterialTheme.typography.bodyMedium
                )
            }


            PrimaryButton("Apply Filters", onClick = {})

        }


    }
}


@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun FilterScreenPreview() {
    FilterScreen(
        onClickBack = {}
    )
}