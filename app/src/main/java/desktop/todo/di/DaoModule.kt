package desktop.todo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import desktop.todo.data.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Singleton
    @Provides
    fun providesContentDao(appDatabase: AppDatabase) = appDatabase.contentDao()
}