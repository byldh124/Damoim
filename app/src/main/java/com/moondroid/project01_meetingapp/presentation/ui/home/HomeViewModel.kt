package com.moondroid.project01_meetingapp.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moondroid.damoim.common.Constants
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.damoim.common.ResponseCode
import com.moondroid.damoim.data.response.BaseResponse
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onNetworkError
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.profile.InterestUseCase
import com.moondroid.damoim.domain.usecase.profile.ProfileUseCase
import com.moondroid.project01_meetingapp.presentation.base.BaseViewModel
import com.moondroid.project01_meetingapp.presentation.common.MutableEventFlow
import com.moondroid.project01_meetingapp.presentation.common.asEventFlow
import com.moondroid.project01_meetingapp.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
    private val interestUseCase: InterestUseCase,
) : BaseViewModel() {
    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean> get() = _showLoading

    private val _showError = MutableLiveData<Int>()
    val showError: LiveData<Int> get() = _showError

    private val _interestResponse = SingleLiveEvent<BaseResponse>()
    val interestResponse: LiveData<BaseResponse> get() = _interestResponse

    init {
        getUser()
    }

    fun getUser() {
        viewModelScope.launch {
            profileUseCase().collect { result ->
                result.onSuccess {
                    setProfile(it)
                }
            }
        }
    }

    fun updateInterest( interest: String) {
        viewModelScope.launch {
            interestUseCase(interest).collect { result ->
                result.onSuccess {
                    updateInterest(it.code == ResponseCode.SUCCESS)
                }.onError { it.logException() }
            }
        }
    }

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    private fun setProfile(profile: Profile) = event(Event.SetProfile(profile))
    private fun updateInterest(b: Boolean) = event(Event.UpdateInterest(b))
    fun toMyProfile() = event(Event.MyProfile)
    fun share() = event(Event.Share)

    sealed class Event {
        data class SetProfile(val profile: Profile) : Event()
        data class UpdateInterest(val b: Boolean) : Event()
        object MyProfile : Event()
        object Share : Event()
    }
}