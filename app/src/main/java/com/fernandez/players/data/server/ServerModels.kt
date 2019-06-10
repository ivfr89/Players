package com.fernandez.players.data.server

import com.fernandez.players.core.ModelEntity
import com.fernandez.players.core.extensions.empty
import com.fernandez.players.core.extensions.fromJson
import com.fernandez.players.domain.models.Player
import com.fernandez.players.domain.models.PlayerList


data class PlayerListEntity(
    val players: List<PlayerEntity>,
    val title: String,
    val type: String
) : ModelEntity()
{

    override fun toModel(json: String): ModelEntity = json.fromJson()

    companion object {
        fun empty() = PlayerListEntity(emptyList(), String.empty(), String.empty())
    }

    fun isEmpty(): Boolean = this == empty()

    fun toDomain() = PlayerList(players.map { it.toDomain() },title,type)
}

data class PlayerEntity(
    val image: String,
    val name: String,
    val surname: String,
    val date: String?=null
): ModelEntity()
{
    override fun toModel(json: String): ModelEntity = json.fromJson()

    companion object {
        fun empty() = PlayerEntity(String.empty(), String.empty(), String.empty())
    }

    fun isEmpty(): Boolean = this == empty()

    fun toDomain() = Player(image,name,surname, date)


}