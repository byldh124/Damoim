package com.moondroid.project01_meetingapp.presentation.ui.home

import androidx.lifecycle.viewModelScope
import com.moondroid.damoim.common.Extension.logException
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.usecase.profile.InterestUseCase
import com.moondroid.damoim.domain.usecase.profile.ProfileUseCase
import com.moondroid.project01_meetingapp.presentation.base.BaseViewModel
import com.moondroid.project01_meetingapp.presentation.common.MutableEventFlow
import com.moondroid.project01_meetingapp.presentation.common.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
    private val interestUseCase: InterestUseCase,
) : BaseViewModel() {

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

    fun updateInterest(interest: String) {
        viewModelScope.launch {
            interestUseCase(interest).collect { result ->
                result.onSuccess {
                    event(Event.UpdateInterest)
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

    private fun setProfile(profile: Profile) = event(Event.SetProfile(profile))
    fun toMyProfile() = event(Event.MyProfile)

    sealed interface Event {
        data class SetProfile(val profile: Profile) : Event
        data object UpdateInterest : Event
        data object MyProfile : Event
    }
}