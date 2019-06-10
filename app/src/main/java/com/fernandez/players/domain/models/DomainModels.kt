package com.fernandez.players.domain.models

import android.os.Parcel
import android.os.Parcelable
import com.fernandez.players.core.extensions.empty
import com.fernandez.players.data.server.PlayerEntity
import com.fernandez.players.data.server.PlayerListEntity
import com.google.gson.Gson


data class PlayerList(
    val players: List<Player>,
    val title: String,
    val type: String
) : Parcelable
{

    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(CREATOR) ?: listOf<Player>(),
        parcel.readString() ?: String.empty(),
        parcel.readString() ?: String.empty()

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(players)
        parcel.writeString(title)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object
    {
        fun empty() = PlayerList(listOf(), String.empty(), String.empty())


        @JvmField
        val CREATOR: Parcelable.Creator<Player> = object : Parcelable.Creator<Player> {
            override fun createFromParcel(parcel: Parcel): Player {
                return Player(parcel)
            }

            override fun newArray(size: Int): Array<Player?> {
                return arrayOfNulls(size)
            }

        }
    }

    fun isEmpty(): Boolean = this == empty()

    fun toEntity() = PlayerListEntity(players.map { it.toEntity() },title,type)
    fun toJson() = Gson().toJson(this)

}


data class Player(
    val image: String,
    val name: String,
    val surname: String,
    val date: String?
) : Parcelable
{

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: String.empty(),
        parcel.readString() ?: String.empty(),
        parcel.readString() ?: String.empty(),
        parcel.readString() ?: String.empty()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(image)
        parcel.writeString(name)
        parcel.writeString(surname)
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }
    companion object
    {
        fun empty() = Player(String.empty(), String.empty(), String.empty(), null)

        @JvmField
        val CREATOR: Parcelable.Creator<Player> = object : Parcelable.Creator<Player> {
            override fun createFromParcel(parcel: Parcel): Player {
                return Player(parcel)
            }

            override fun newArray(size: Int): Array<Player?> {
                return arrayOfNulls(size)
            }

        }

    }



    fun isEmpty(): Boolean = this == empty()

    fun toEntity() = PlayerEntity(image,name,surname)
    fun toJson() = Gson().toJson(this)

}


