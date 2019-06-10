package com.fernandez.players.domain.uc.players

import com.fernandez.players.core.Failure
import com.fernandez.players.domain.models.PlayerList
import com.fernandez.players.domain.repository.PlayerRepository
import com.fernandez.players.domain.uc.Either
import com.fernandez.players.domain.uc.UseCase
import kotlinx.coroutines.CoroutineScope


class GetPlayers(val playerRespository: PlayerRepository): UseCase<List<PlayerList>, GetPlayers.Params, CoroutineScope>(){


    override suspend fun run(params: Params): Either<Failure, List<PlayerList>> {


        return if(Cache.mCache.isEmpty())
        {
            val result = playerRespository.getPlayers()

            result.either({},{

                it.also {playerList->
                    Cache.mCache = playerList
                }
            })
            result

        }
        else{
            Either.Right(Cache.mCache)
        }

    }


//    it can be more complex
    object Cache
    {
        var mCache: List<PlayerList> = listOf()
    }

    class Params


}