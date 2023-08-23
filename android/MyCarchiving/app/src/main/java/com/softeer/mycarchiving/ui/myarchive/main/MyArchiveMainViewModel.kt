package com.softeer.mycarchiving.ui.myarchive.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.softeer.domain.model.MyArchiveFeed
import com.softeer.domain.usecase.myarchive.DeleteMadeCarFeedUseCase
import com.softeer.domain.usecase.myarchive.GetMadeCarFeedUseCase
import com.softeer.mycarchiving.mapper.asUiModel
import com.softeer.mycarchiving.model.common.CarFeedUiModel
import com.softeer.mycarchiving.model.myarchive.MadeCarUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyArchiveMainViewModel @Inject constructor(
    getMadeCarFeedUseCase: GetMadeCarFeedUseCase,
    private val deleteMadeCarFeedUseCase: DeleteMadeCarFeedUseCase,
): ViewModel() {

    private val _selectedIndex = mutableIntStateOf(0)
    val selectedIndex: State<Int> = _selectedIndex

    val madeCarFeedPagingData = getMadeCarFeedUseCase()
        .map { pagingData -> pagingData.map(MyArchiveFeed::asUiModel) }
        .cachedIn(viewModelScope)

    private val _detailCar = mutableStateOf<MadeCarUiModel?>(null)
    val detailCar: State<MadeCarUiModel?> = _detailCar

    private val _savedCars = MutableStateFlow(
        listOf(
            CarFeedUiModel(
                id = 1,
                model = "팰리세이드",
                isPurchase = false,
                creationDate = "2023-08-21",
                trim = "Le Blanc",
                trimOptions = listOf("디젤 2.2", "4WD", "7인승"),
                interiorColor = "퀄팅 천연(블랙)",
                exteriorColor = "어비스 블랙펄",
                selectedOptions = listOf(),
                review = "승차감이 좋아요 차가 크고 운전하는 시야도 높아서 좋았어요 저는 13개월 아들이 있는데 뒤에 차시트 달아도 널널할 것 같습니다. 다른 주차 관련 옵션도 괜찮아요.",
                tags = listOf("편리해요😉", "이것만 있으면 나도 주차고수🚘", "대형견도 문제 없어요🐶")
            ),
        )
    )
    val savedCars: StateFlow<List<CarFeedUiModel>> = _savedCars

    fun updateSelectedIndex(index: Int) {
        _selectedIndex.intValue = index
    }

    fun onCarDetail(madeCar: MadeCarUiModel) {
        _detailCar.value = madeCar
    }

    fun deleteMadeCar(feedId: Long) {
        viewModelScope.launch {
            deleteMadeCarFeedUseCase(feedId)
        }
    }

    fun deleteSavedCar(feedId: Int) {

    }

}