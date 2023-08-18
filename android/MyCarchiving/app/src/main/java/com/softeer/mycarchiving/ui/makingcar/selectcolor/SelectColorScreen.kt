package com.softeer.mycarchiving.ui.makingcar.selectcolor

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import com.softeer.data.CarColorType
import com.softeer.mycarchiving.model.makingcar.ColorOptionUiModel
import com.softeer.mycarchiving.ui.component.CarColorSelectItem
import com.softeer.mycarchiving.ui.component.OptionHeadText
import com.softeer.mycarchiving.ui.component.OptionInfoDivider
import com.softeer.mycarchiving.ui.component.OptionRegular14Text
import com.softeer.mycarchiving.ui.component.OptionSelectedInfo
import com.softeer.mycarchiving.ui.component.RotateCarImage
import com.softeer.mycarchiving.ui.makingcar.MakingCarViewModel
import com.softeer.mycarchiving.ui.makingcar.loading.LoadingScreen
import com.softeer.mycarchiving.ui.theme.HyundaiLightSand
import com.softeer.mycarchiving.util.TRIM_COLOR
import com.softeer.mycarchiving.util.TRIM_EXTERIOR
import com.softeer.mycarchiving.util.TRIM_EXTRA
import com.softeer.mycarchiving.util.TRIM_INTERIOR
import com.softeer.mycarchiving.util.TRIM_OPTION
import com.softeer.mycarchiving.util.fadeInAndOut

@Composable
fun SelectColorRoute(
    modifier: Modifier = Modifier,
    mainProgress: Int,
    screenProgress: Int,
    viewModelStoreOwner: ViewModelStoreOwner,
    selectColorViewModel: SelectColorViewModel = hiltViewModel(),
    makingCarViewModel: MakingCarViewModel = hiltViewModel(viewModelStoreOwner)
) {
    val carImageUrls by selectColorViewModel.imageUrls.collectAsStateWithLifecycle()
    val interiorImageUrls by selectColorViewModel.interiorImageUrls.collectAsStateWithLifecycle()
    val category by selectColorViewModel.category.collectAsStateWithLifecycle()
    val topImageIndex by selectColorViewModel.topImageIndex.collectAsStateWithLifecycle()
    val selectedIndex by selectColorViewModel.selectedIndex.collectAsStateWithLifecycle()
    val exteriors by selectColorViewModel.exteriors.collectAsStateWithLifecycle()
    val interiors by selectColorViewModel.interiors.collectAsStateWithLifecycle()
    val selectedColor by makingCarViewModel.selectedColor.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = screenProgress, key2 = exteriors, key3 = interiors) {
        val colorOptions = when (mainProgress to screenProgress) {
            TRIM_COLOR to TRIM_EXTERIOR -> exteriors
            TRIM_COLOR to TRIM_INTERIOR, TRIM_OPTION to TRIM_EXTRA -> interiors
            else -> emptyList()
        }

        val isInitial = selectedColor.getOrNull(screenProgress) == null
        if (isInitial) { // 화면 처음 진입 시 첫 색상 자동 선택 및 등록
            selectColorViewModel.changeSelectedColor(0)
            makingCarViewModel.updateSelectedColorOption(
                colorOptions.firstOrNull(),
                screenProgress,
                isInitial
            )
        } else { // 이미 진입했던 화면이면 이전에 선택한 데이터 로드
            colorOptions
                .indexOfFirst { it.optionName == selectedColor.getOrNull(screenProgress)?.optionName }
                .takeIf { index -> index >= 0 }
                ?.let { savedIndex ->
                    selectColorViewModel.changeSelectedColor(savedIndex)
                }
        }

        selectColorViewModel.changeCategory(screenProgress)
    }

    val context = LocalContext.current
    LaunchedEffect(interiorImageUrls) {
        interiorImageUrls.forEach { imageUrl ->
            context.imageLoader.execute(
                ImageRequest.Builder(context)
                    .data(imageUrl)
                    .memoryCacheKey(imageUrl)
                    .build()
            )
        }
    }

    SelectColorScreen(
        modifier = modifier,
        mainProgress = mainProgress,
        screenProgress = screenProgress,
        topImagePath = when (mainProgress to screenProgress) {
            TRIM_COLOR to TRIM_EXTERIOR -> carImageUrls.getOrNull(selectedIndex) ?: ""
            TRIM_COLOR to TRIM_INTERIOR,
            TRIM_OPTION to TRIM_EXTRA -> interiorImageUrls.getOrNull(selectedIndex) ?: ""
            else -> ""
        },
        topImageIndex = topImageIndex,
        category = category,
        selectedIndex = selectedIndex,
        colorOptions = when (mainProgress to screenProgress) {
            TRIM_COLOR to TRIM_EXTERIOR -> exteriors
            TRIM_COLOR to TRIM_INTERIOR,
            TRIM_OPTION to TRIM_EXTRA -> interiors
            else -> emptyList()
        },
        isInitial = selectedColor.getOrNull(screenProgress) == null,
        onLeftClick = { selectColorViewModel.changeTopImageIndex(false) },
        onRightClick = { selectColorViewModel.changeTopImageIndex(true) },
        onColorSelect = selectColorViewModel::changeSelectedColor,
        onSaveColor = makingCarViewModel::updateSelectedColorOption,
        onSaveImageUrl = makingCarViewModel::updateCarImageUrl
    )
}

@Composable
fun SelectColorScreen(
    modifier: Modifier,
    mainProgress: Int,
    screenProgress: Int,
    topImagePath: String,
    topImageIndex: Int,
    category: String,
    selectedIndex: Int,
    colorOptions: List<ColorOptionUiModel>,
    isInitial: Boolean,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
    onColorSelect: (Int) -> Unit,
    onSaveColor: (ColorOptionUiModel, Int, Boolean) -> Unit,
    onSaveImageUrl: (String) -> Unit,
) {
    val selectedColor = colorOptions.getOrNull(selectedIndex)
    LaunchedEffect(topImagePath) {
        if (mainProgress == TRIM_COLOR && screenProgress == TRIM_EXTERIOR) {
            // 내차만들기 완성에서 보여줄 URL
            onSaveImageUrl(topImagePath)
        }
    }

    AnimatedContent(
        targetState = colorOptions,
        transitionSpec = { fadeInAndOut() },
        label = ""
    ) {
        when {
            it.isNotEmpty() -> {
                Column(
                    modifier = modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (selectedColor != null) {
                        SelectColorTopArea(
                            mainProgress = mainProgress,
                            screenProgress = screenProgress,
                            topImagePath = topImagePath,
                            topImageIndex = topImageIndex,
                            onLeftClick = onLeftClick,
                            onRightClick = onRightClick,
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OptionHeadText(optionName = category)
                                OptionRegular14Text(optionName = selectedColor.optionName)
                            }
                            LazyRow {
                                itemsIndexed(colorOptions) { idx, item ->
                                    CarColorSelectItem(
                                        onItemClick = {
                                            onColorSelect(idx)
                                            onSaveColor(item, screenProgress, isInitial)
                                        },
                                        imageUrl = item.imageUrl,
                                        selected = selectedColor.imageUrl == item.imageUrl,
                                    )
                                }
                            }
                            OptionInfoDivider(thickness = 4.dp, color = HyundaiLightSand)
                            OptionSelectedInfo(
                                optionName = selectedColor.optionName,
                                optionTags = selectedColor.tags
                            )
                        }
                    }
                }
            }

            else -> LoadingScreen {}
        }
    }
}

@Composable
fun SelectColorTopArea(
    modifier: Modifier = Modifier,
    mainProgress: Int,
    screenProgress: Int,
    topImagePath: String,
    topImageIndex: Int,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
) {
    when (mainProgress to screenProgress) {
        TRIM_COLOR to TRIM_EXTERIOR -> {
            RotateCarImage(
                modifier = modifier,
                imagePath = topImagePath,
                selectedIndex = topImageIndex,
                onLeftClick = onLeftClick,
                onRightClick = onRightClick,
            )
        }

        TRIM_COLOR to TRIM_INTERIOR, TRIM_OPTION to TRIM_EXTRA -> {
            AsyncImage(
                modifier = modifier
                    .fillMaxWidth()
                    .height(200.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(topImagePath)
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )
        }
    }
}

@Preview(widthDp = 375, heightDp = 646)
@Composable
fun PreviewSelectColorScreen() {
    SelectColorScreen(
        modifier = Modifier,
        mainProgress = 0,
        screenProgress = 0,
        topImagePath = "",
        topImageIndex = 0,
        category = "외장 색상",
        selectedIndex = 0,
        colorOptions = listOf(
            ColorOptionUiModel(
                category = CarColorType.EXTERIOR,
                optionName = "어비스 블랙펄",
                imageUrl = "",
                price = 0,
                matchingColors = listOf(1, 2, 3),
                tags = listOf(
                    "어린이\uD83E\uDDD2",
                    "이것만 있으면 나도 주차 고수🚘",
                    "편리해요😉",
                    "대형견도 문제 없어요🐶",
                    "큰 짐도 OK💼"
                ),
            )
        ),
        isInitial = false,
        onLeftClick = {},
        onRightClick = {},
        onColorSelect = {},
        onSaveColor = { _, _, _ -> },
        onSaveImageUrl = {}
    )
}