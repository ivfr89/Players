package com.fernandez.players.domain.repository

import com.fernandez.players.core.BaseRepository
import com.fernandez.players.core.Failure
import com.fernandez.players.core.extensions.empty
import com.fernandez.players.data.server.*
import com.fernandez.players.domain.models.PlayerList
import com.fernandez.players.domain.uc.Either

interface PlayerRepository
{

    fun getPlayers(): Either<Failure,List<PlayerList>>



    class Network
    constructor(val networkHandler: NetworkHandler,
                val service: ApiService,
                val serverMapper: ServerResponseMapper
    ) : BaseRepository(), PlayerRepository {

        override fun getPlayers(): Either<Failure, List<PlayerList>> {

            return if(networkHandler.isConnected)
            {
                request(service.getPlayers(),{ response->
                    serverMapper.parseArrayResponse<PlayerListEntity>(response).map { it.toDomain() }
                },String.empty())
            }else
                Either.Left(Failure.NetworkConnection())
        }



    }


}