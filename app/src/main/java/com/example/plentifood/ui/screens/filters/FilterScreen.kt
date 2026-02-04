package com.example.plentifood.ui.screens.filters

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.plentifood.ui.composables.MultiChoiceSegmentedButton
import com.example.plentifood.ui.composables.MultiSelectedButton
import com.example.plentifood.ui.composables.PrimaryButton
import com.example.plentifood.ui.screens.search.SearchResultViewModel
import com.example.plentifood.ui.utils.toSnakeCase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(
    searchViewModel: SearchResultViewModel,
    filterViewModel: FilterViewModel,
    onClickBack: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var reset by remember { mutableStateOf(false) }
    LaunchedEffect(reset) {
        if (reset) reset = false
    }


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
                    IconButton(onClick = {
                        onClickBack()
                        if (!filterViewModel.AreFiltersApplied) reset = true

                    }) {
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
                                reset = true

                                searchViewModel.onUpdateNumOfFilters(0)
                                searchViewModel.onUpdateRadius("5")
                                searchViewModel.onDaySelected(emptyList())
                                searchViewModel.onOrganizationTypeSelected(emptyList())
                                searchViewModel.onServiceSelected(emptyList())

                            },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        ScrollContent(
            padding = innerPadding,
            searchViewModel = searchViewModel,
            filterViewModel = filterViewModel,
            onClickBack = onClickBack,
            reset
        )
    }
}


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ScrollContent(
    padding: PaddingValues,
    searchViewModel: SearchResultViewModel,
    filterViewModel: FilterViewModel,
    onClickBack: () -> Unit,
    reset: Boolean = false
) {

    val scrollState = rememberScrollState()
    var radiusText by remember { mutableStateOf("") }


    LaunchedEffect(reset) {
        if (reset) {
            filterViewModel.resetUi()
        }
    }

    fun getSelectedItems(options: List<String>, checked: List<Boolean>): List<String> {
        val checkedItems = mutableListOf<String>()

        checked.forEachIndexed { index, isChecked ->
            if (isChecked) {
                checkedItems.add(options[index].toSnakeCase())
            }
        }
        return checkedItems
    }

    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween

    ) {
        Text("Day of Service")
        MultiSelectedButton(
            filterViewModel.dayOptions,
            filterViewModel.dayChecked,
            onCheckedChange = { index, value ->
                filterViewModel.dayChecked[index] = value
            })

        Text("Organization Type")
        MultiSelectedButton(
            filterViewModel.orgOptions,
            filterViewModel.orgChecked,
            onCheckedChange = { index, value ->
                filterViewModel.orgChecked[index] = value
            })

        Text("Service Type")

        MultiChoiceSegmentedButton(
            filterViewModel.serviceOptions,
            filterViewModel.serviceChecked,
            onCheckedChange = { index, value ->
                filterViewModel.serviceChecked[index] = value
            })


        Text("Radius")

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Within",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.width(12.dp))

            OutlinedTextField(
                radiusText,
                onValueChange = {
                    radiusText = it
                },
                modifier = Modifier
                    .weight(1f),
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    textAlign = TextAlign.Center
                ),
                label = {
                    Text(
                        "Enter mile range",
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                trailingIcon = {
                    Text(
                        "Miles",
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
            )

        }
        PrimaryButton(
            "Apply Filters",
            onButtonClick = {
                searchViewModel.onUpdateNumOfFilters(filterViewModel.getNumOfFilters(radiusText.isNotEmpty()))
                searchViewModel.onUpdateRadius(radiusText)
                searchViewModel.onDaySelected(
                    getSelectedItems(
                        filterViewModel.dayOptions,
                        filterViewModel.dayChecked
                    )
                )
                searchViewModel.onOrganizationTypeSelected(
                    getSelectedItems(
                        filterViewModel.orgOptions,
                        filterViewModel.orgChecked
                    )
                )
                searchViewModel.onServiceSelected(
                    getSelectedItems(
                        filterViewModel.serviceOptions,
                        filterViewModel.serviceChecked
                    )
                )
                filterViewModel.updateFiltersStatus(true)
                onClickBack()
            })
    }

}


//@Preview(
//    showBackground = true,
//    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO
//)
//@Composable
//fun FilterScreenPreview() {
//    FilterScreen(
//        onClickBack = {}
//    )
//}
