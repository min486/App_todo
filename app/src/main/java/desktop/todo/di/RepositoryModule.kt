package desktop.todo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import desktop.todo.data.dao.ContentDao
import desktop.todo.repository.ContentRepository
import desktop.todo.repository.ContentRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun providesContentRepository(contentDao: ContentDao): ContentRepository =
        ContentRepositoryImpl(contentDao)  // ContentDao 주입
}