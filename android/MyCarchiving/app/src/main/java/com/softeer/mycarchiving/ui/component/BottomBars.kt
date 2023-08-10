package com.softeer.mycarchiving.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.softeer.mycarchiving.R
import com.softeer.mycarchiving.ui.theme.Black
import com.softeer.mycarchiving.ui.theme.HyundaiLightSand
import com.softeer.mycarchiving.ui.theme.HyundaiSand
import com.softeer.mycarchiving.ui.theme.PrimaryBlue
import com.softeer.mycarchiving.ui.theme.White
import com.softeer.mycarchiving.ui.theme.bold18
import com.softeer.mycarchiving.ui.theme.medium12
import com.softeer.mycarchiving.util.toPriceString

@Composable
fun BottomBar(
    modifier: Modifier,
    totalPrice: Int,
    summaryText: String,
    underLineWidth: Int,
    buttonArea: @Composable () -> Unit,
    onShowSummary: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(137.dp)
            .background(HyundaiSand)
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 12.dp,
                bottom = 32.dp
            )
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = modifier
                    .clickable { onShowSummary?.invoke() }
            ) {
                Text(
                    text = summaryText,
                    style = medium12,
                )
                Spacer(modifier = modifier.height(5.dp))
                Divider(
                    modifier = modifier.width(underLineWidth.dp),
                    thickness = 2.dp,
                    color = Black
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = totalPrice.toPriceString(),
                    style = bold18
                )
                Spacer(modifier = modifier.width(3.dp))
                Text(
                    text = stringResource(id = R.string.won),
                    style = medium12
                )
            }
        }
        Spacer(modifier = modifier.height(13.dp))
        buttonArea()

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MakeCarBottomBar(
    modifier: Modifier = Modifier,
    totalPrice: Int,
    onButtonClick: () -> Unit,
    onSummaryClick: () -> Unit,
    isDone: Boolean,
) {
    var openSummarySheet by rememberSaveable { mutableStateOf(false) }
    val summarySheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    BottomBar(
        modifier = modifier,
        totalPrice = totalPrice,
        summaryText = stringResource(id = R.string.show_summary),
        underLineWidth = 61,
        buttonArea = {
            HyundaiButton(
                modifier = modifier,
                backgroundColor = PrimaryBlue,
                textColor = HyundaiLightSand,
                text = if (isDone) {
                    stringResource(id = R.string.purchase_car)
                } else {
                    stringResource(id = R.string.bottom_bar_next_step)
                },
                onClick = onButtonClick
            )
        },
        onShowSummary = { openSummarySheet = true }
    )
    if (openSummarySheet) {
        ModalBottomSheet(
            modifier = Modifier.fillMaxHeight(),
            onDismissRequest = { openSummarySheet = false },
            containerColor = White,
            sheetState = summarySheetState,
            windowInsets = WindowInsets(top = 60.dp),
            scrimColor = Color.Transparent
        ) {
            SummaryBottomSheetContent(
                totalPrice = totalPrice
            )
        }
    }
}

@Composable
fun ArchiveBottomBar(
    modifier: Modifier = Modifier,
    totalPrice: Int,
    onButtonClick: () -> Unit,
    onSaveClick: () -> Unit,
) {
    BottomBar(
        modifier = modifier,
        totalPrice = totalPrice,
        summaryText = stringResource(id = R.string.archive_total_price),
        underLineWidth = 35,
        buttonArea = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                ArchiveSaveButton(
                    modifier = modifier,
                    onSave = onSaveClick
                )
                Spacer(modifier = modifier.width(16.dp))
                HyundaiButton(
                    modifier = modifier,
                    backgroundColor = PrimaryBlue,
                    textColor = HyundaiLightSand,
                    text = stringResource(id = R.string.archive_make_my_car),
                    onClick = onButtonClick
                )
            }
        }
    )
}

@Composable
fun MyArchiveBottomBar(
    modifier: Modifier = Modifier,
    totalPrice: Int,
    onButtonClick: () -> Unit,
) {
    BottomBar(
        modifier = modifier,
        totalPrice = totalPrice,
        summaryText = stringResource(id = R.string.archive_total_price),
        underLineWidth = 35,
        buttonArea = {
            HyundaiButton(
                modifier = modifier,
                backgroundColor = PrimaryBlue,
                textColor = HyundaiLightSand,
                text = stringResource(id = R.string.purchase_car),
                onClick = onButtonClick
            )
        }
    )
}

@Preview
@Composable
fun PreviewMakeCarBottomBar() {
    MakeCarBottomBar(
        modifier = Modifier,
        totalPrice = 47720000,
        onButtonClick = {},
        onSummaryClick = {},
        isDone = false
    )
}

@Preview
@Composable
fun PreviewArchiveBottomBar() {
    ArchiveBottomBar(
        modifier = Modifier,
        totalPrice = 47720000,
        onButtonClick = {},
        onSaveClick = {}
    )
}

@Preview
@Composable
fun PreviewMyArchiveBottomBar() {
    MyArchiveBottomBar(
        modifier = Modifier,
        totalPrice = 47720000,
        onButtonClick = {},
    )
}


