package com.application.ebcom.di

import android.content.Context
import androidx.room.Room
import com.application.ebcom.feature.message.data.local.MessageDao
import com.application.ebcom.feature.message.data.local.MessageDatabase
import com.application.ebcom.feature.message.data.remote.MessageApiService
import com.application.ebcom.feature.message.data.remote.MessageApiService.Companion.BASE_URL
import com.application.ebcom.feature.message.data.repository.MessageRepositoryImpl
import com.application.ebcom.feature.message.domain.repository.MessageRepository
import com.application.ebcom.feature.message.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteUseCases(
        repository: MessageRepository
    ): MessageUseCases {
        return MessageUseCases(
            getAllMessageLocal = GetAllMessageLocal(repository = repository),
            getAllMessageRemote = GetAllMessageRemote(repository = repository),
            addMessage = AddMessage(repository = repository),
            deleteMessage = DeleteMessage(repository = repository),
            saveMessage = SaveMessage(repository = repository),
            addAllMessage = AddAllMessage(repository = repository),
            unSaveMessage = UnSaveMessage(repository = repository),
            readMessage = ReadMessage(repository = repository),
            deleteMessages = DeleteMessages(repository = repository)
        )
    }

    @Singleton
    @Provides
    fun provideMessageRepository(
        api: MessageApiService,
        dao: MessageDao
    ): MessageRepository {
        return MessageRepositoryImpl(api = api, dao = dao)
    }

    @Singleton
    @Provides
    fun provideMessageDatabase(
        @ApplicationContext context: Context
    ): MessageDatabase {
        return Room.databaseBuilder(context, MessageDatabase::class.java, "message_db")
            .build()
    }

    @Singleton
    @Provides
    fun provideMessageDao(
        database: MessageDatabase
    ): MessageDao = database.dao

    @Singleton
    @Provides
    fun provideApi(): MessageApiService =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(MessageApiService::class.java)


}