package desktop.todo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import desktop.todo.model.ContentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContentDao {

    @Query("SELECT * FROM Content")
    fun selectAll() : Flow<List<ContentEntity>>  // Flow로 구독해서 insert, delete, update될 때 자동으로 갱신

    @Insert(onConflict = OnConflictStrategy.REPLACE)  // 충돌이 발생할 때 교체되게 설정 (수정)
    suspend fun insert(item: ContentEntity)

    @Delete
    suspend fun delete(item: ContentEntity)
}