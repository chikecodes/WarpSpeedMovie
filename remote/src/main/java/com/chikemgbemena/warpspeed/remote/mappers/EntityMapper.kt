package com.chikemgbemena.warpspeed.remote.mappers

interface EntityMapper<M, E> {

    fun mapFromModel(model: M): E
}
