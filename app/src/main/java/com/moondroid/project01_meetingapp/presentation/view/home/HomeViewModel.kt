package com.moondroid.project01_meetingapp.presentation.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moondroid.project01_meetingapp.base.BaseViewModel
import com.moondroid.project01_meetingapp.data.common.onError
import com.moondroid.project01_meetingapp.data.common.onSuccess
import com.moondroid.project01_meetingapp.data.response.BaseResponse
import com.moondroid.project01_meetingapp.delegrate.MutableEventFlow
import com.moondroid.project01_meetingapp.delegrate.asEventFlow
import com.moondroid.project01_meetingapp.domain.model.User
import com.moondroid.project01_meetingapp.domain.usecase.user.InterestUseCase
import com.moondroid.project01_meetingapp.domain.usecase.user.UserUseCase
import com.moondroid.project01_meetingapp.network.Repository
import com.moondroid.project01_meetingapp.network.UseCaseResult
import com.moondroid.project01_meetingapp.utils.ResponseCode
import com.moondroid.project01_meetingapp.utils.SingleLiveEvent
import com.moondroid.project01_meetingapp.utils.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
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
            userUseCase().collect { result ->
                result.onSuccess {
                    setProfile(it)
                }
            }
        }
    }

    fun updateInterest(id: String, interest: String) {
        viewModelScope.launch {
            interestUseCase(interest).collect {result ->
                result.onSuccess {
                    updateInterest(it.code == ResponseCode.SUCCESS)
                }.onError { logException(it) }
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

    private fun setProfile(user: User) = event(Event.Profile(user))
    private fun updateInterest(b: Boolean) = event(Event.UpdateInterest(b))
    fun toMyProfile() = event(Event.MyProfile)
    fun share() = event(Event.Share)

    sealed class Event {
        data class Profile(val user: User) : Event()
        data class UpdateInterest(val b: Boolean) : Event()
        object MyProfile :Event()
        object Share: Event()
    }
}