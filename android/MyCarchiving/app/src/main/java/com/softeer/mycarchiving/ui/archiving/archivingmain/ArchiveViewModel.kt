package com.softeer.mycarchiving.ui.archiving.archivingmain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.softeer.domain.model.CarFeed
import com.softeer.domain.usecase.archiving.GetAbleOptionsUseCase
import com.softeer.domain.usecase.archiving.GetCarFeedsUseCase
import com.softeer.mycarchiving.enums.ArchiveSearchPage
import com.softeer.mycarchiving.enums.ArchiveSearchPage.*
import com.softeer.mycarchiving.mapper.asUiModel
import com.softeer.mycarchiving.model.archiving.SearchOption
import com.softeer.mycarchiving.model.archiving.SearchOptionUiModel
import com.softeer.mycarchiving.model.common.CarFeedUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

private val TAG = ArchiveViewModel::class.simpleName

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ArchiveViewModel @Inject constructor(
    getCarFeedsUseCase: GetCarFeedsUseCase,
    getAbleOptionsUseCase: GetAbleOptionsUseCase
) : ViewModel() {

    private val feed = CarFeedUiModel(
        id = 123,
        model = "팰리세이드",
        isPurchase = false,
        creationDate = "2023-07-19",
        trim = "Le Blanc",
        trimOptions = listOf("디젤 2.2", "4WD", "7인승"),
        interiorColor = "문라이트 블루 펄",
        exteriorColor = "퀄팅 천연(블랙)",
        selectedOptions = listOf("컴포트 || 패키지", "듀얼 와이드 선루프"),
        review = "승차감이 좋아요 차가 크고 운전하는 시야도 높아서 좋았어요 저는 13개월 아들이 있는데 뒤에 차시트 달아도 널널할 것 같습니다. 다른 주차 관련 옵션도 괜찮아요.",
        tags = listOf("편리해요😉", "이것만 있으면 나도 주차고수🚘", "대형견도 문제 없어요🐶")
    )

    private val _showSearchSheet = MutableStateFlow(false)
    val showSearchSheet: StateFlow<Boolean> = _showSearchSheet

    private val _currentSheetPage = MutableStateFlow(SEARCH_CONDITION)
    val currentSheetPage: StateFlow<ArchiveSearchPage> = _currentSheetPage

    private val _ableCars = MutableStateFlow(
        listOf(
            SearchOptionUiModel(
                category = "수소 / 전기차",
                options = listOf(
                    SearchOption(name = "넥쏘"),
                    SearchOption(name = "디 올 뉴 코나 Electric"),
                    SearchOption(name = "아이오닉 6"),
                    SearchOption(name = "포터 I Electric"),
                    SearchOption(name = "포터 II Electric"),
                    SearchOption(name = "포터 II Electric 특장차")
                )
            ),
            SearchOptionUiModel(
                category = "승용차",
                options = listOf(
                    SearchOption(name = "쏘나타 디 엣지"),
                    SearchOption(name = "쏘나타 디 엣지 Hybrid"),
                    SearchOption(name = "더 뉴 아반떼"),
                    SearchOption(name = "더 뉴 아반떼 Hybrid"),
                    SearchOption(name = "디 올 뉴 그랜저"),
                    SearchOption(name = "디 올 뉴 그랜저 Hybrid")
                )
            ),
            SearchOptionUiModel(
                category = "SUV",
                options = listOf(
                    SearchOption(name = "팰리세이드", isSelect = true),
                    SearchOption(name = "베뉴"),
                    SearchOption(name = "디 올 뉴 코나"),
                    SearchOption(name = "디 올 뉴 코나 Hybrid"),
                    SearchOption(name = "투싼"),
                    SearchOption(name = "투싼 Hybrid")
                )
            ),
        )
    )
    val ableCars: StateFlow<List<SearchOptionUiModel>> = _ableCars

    private val _selectedCar = MutableStateFlow(SearchOption(name = "팰리세이드", isSelect = true))
    val selectedCar: StateFlow<SearchOption> = _selectedCar

    private val _pendingCar = MutableStateFlow(_selectedCar.value)
    val pendingCar: StateFlow<SearchOption> = _pendingCar

    val ableOptions = getAbleOptionsUseCase()
        .map { it.asUiModel() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    val totalOptionsSize = ableOptions.value.sumOf { it.options.size }

    private val _appliedOptions = MutableStateFlow(emptyList<SearchOption>())
    val appliedOptions: StateFlow<List<SearchOption>> = _appliedOptions

    private val _selectedOptions = MutableStateFlow(emptyList<SearchOption>())
    val selectedOptions: StateFlow<List<SearchOption>> = _selectedOptions

    private val _pendingOptions = MutableStateFlow(emptyList<SearchOption>())
    val pendingOptions: StateFlow<List<SearchOption>> = _pendingOptions

    private val _carFeeds = MutableStateFlow(listOf(feed, feed, feed, feed, feed, feed, feed))
    val carFeeds: StateFlow<List<CarFeedUiModel>> = _carFeeds

    val carFeedPagingData = _showSearchSheet.flatMapLatest { show ->
        if (show.not()) {
            getCarFeedsUseCase(_selectedOptions.value.map { it.id ?: "" })
        } else {
            flow {  }
        }
    }.map { pagingData -> pagingData.map(CarFeed::asUiModel) }
        .cachedIn(viewModelScope)


    fun openSearchSheet() {
        _selectedOptions.value = _appliedOptions.value
        _currentSheetPage.value = SEARCH_CONDITION
        _showSearchSheet.value = true
    }

    fun closeSearchSheet() {
        _showSearchSheet.value = false
    }

    fun onSheetBackClick() {
        _currentSheetPage.value = SEARCH_CONDITION
    }

    fun moveSetCarSheet() {
        _currentSheetPage.value = SET_CAR
    }

    fun moveSetOptionSheet() {
        initAbleOptions()
        _pendingOptions.value = _selectedOptions.value
        _currentSheetPage.value = SET_OPTION
    }

    fun onSheetButtonClick() {
        when (currentSheetPage.value) {
            SEARCH_CONDITION -> {
                /*검색 API에 적용*/
                _appliedOptions.value = _selectedOptions.value
                closeSearchSheet()
            }

            SET_CAR -> {
                onSheetBackClick()
            }

            SET_OPTION -> {
                _selectedOptions.value = _pendingOptions.value
                onSheetBackClick()
            }
        }
    }

    private fun initAbleOptions() {
        ableOptions.value.forEach { ableOption ->
            ableOption.options.forEach { option ->
                option.isSelect = option in _appliedOptions.value
            }
        }
    }

    fun onOptionChipClick(option: SearchOption) {
        if (_pendingOptions.value.contains(option)) {
            _pendingOptions.value = _pendingOptions.value.toMutableList().also {
                option.isSelect = false
                it.remove(option)
            }
        } else {
            _pendingOptions.value = _pendingOptions.value.toMutableList().also {
                option.isSelect = true
                it.add(option)
            }
        }
    }

    fun deleteAppliedOption(option: SearchOption) {
        _appliedOptions.value = _appliedOptions.value.toMutableList().also {
            option.isSelect = false
            it.remove(option)
        }
    }

    fun deleteSelectedOption(option: SearchOption) {
        _selectedOptions.value = _selectedOptions.value.toMutableList().also {
            option.isSelect = false
            it.remove(option)

        }
    }

    fun deletePendingOption(option: SearchOption) {
        _pendingOptions.value = _pendingOptions.value.toMutableList().also {
            option.isSelect = false
            it.remove(option)
        }
    }

}