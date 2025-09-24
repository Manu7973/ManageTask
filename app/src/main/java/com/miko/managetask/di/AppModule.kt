package com.miko.managetask.di

import android.content.Context
import androidx.room.Room
import com.miko.managetask.data.dao.TaskDao
import com.miko.managetask.data.repositery.TaskRepository
import com.miko.managetask.data.source.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// AppModule class is used to provide dependencies for the application.
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext ctx: Context): TaskDatabase {
        return Room.databaseBuilder(ctx, TaskDatabase::class.java, "tasks_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideTaskDao(db: TaskDatabase): TaskDao = db.taskDao()

    @Provides
    @Singleton
    fun provideRepository(dao: TaskDao) = TaskRepository(dao)
}