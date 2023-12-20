package desktop.todo.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import desktop.todo.data.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesDataBase(@ApplicationContext context: Context) : AppDatabase {  // Singleton이기 때문에 ApplicationContext 사용
        // 데이터베이스 인스턴스 생성
        return Room.databaseBuilder(context, AppDatabase::class.java, "toto.db")
            .fallbackToDestructiveMigration()  // 이전 경로가 누락될 때, 기존 데이터베이스 삭제하고 다시 생성
            .build()
    }

}