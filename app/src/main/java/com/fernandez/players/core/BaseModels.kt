package com.fernandez.players.core

abstract class ModelEntity
{
    abstract fun toModel(json: String): ModelEntity

}

abstract class Model
{
    abstract fun empty(): Model
}