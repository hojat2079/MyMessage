package com.application.ebcom.feature.message.data.remote

import com.application.ebcom.feature.message.data.remote.dto.MessageDto
import com.application.ebcom.feature.message.data.remote.dto.MessageDtoList
import retrofit2.http.GET

interface MessageApiService {
    @GET("729e846c-80db-4c52-8765-9a762078bc82/")
    suspend fun getInformationData(
    ): MessageDtoList

    companion object {
        const val BASE_URL = "https://run.mocky.io/v3/"
    }
}