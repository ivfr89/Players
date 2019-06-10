package com.fernandez.players.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fernandez.players.core.BaseViewModel
import com.fernandez.players.core.ScreenState
import com.fernandez.players.domain.models.PlayerList
import com.fernandez.players.domain.uc.players.GetPlayers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class MainActivityViewModel(val getPlayers: GetPlayers): BaseViewModel()
{
    private val _state: MutableLiveData<ScreenState<MainScreenState>> = MutableLiveData()

    val state: LiveData<ScreenState<MainScreenState>>
        get()= _state

    private var job = Job()

    private var coroutineScope = CoroutineScope(Dispatchers.Default + job)
    fun playersFromServer()
    {
        this._state.value = ScreenState.Loading

        getPlayers.execute({it.either(::handleFailure, ::handlePlayers)},GetPlayers.Params() ,coroutineScope)
    }

    private fun handlePlayers(list: List<PlayerList>) {

        this._state.value = ScreenState.Render(MainScreenState.GetPlayersFromServer(list))

    }


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }


}