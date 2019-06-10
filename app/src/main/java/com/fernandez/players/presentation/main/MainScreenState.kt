package com.fernandez.players.presentation.main

import com.fernandez.players.domain.models.PlayerList

sealed class MainScreenState
{
    class GetPlayersFromServer(val listPlayers: List<PlayerList>): MainScreenState()
}