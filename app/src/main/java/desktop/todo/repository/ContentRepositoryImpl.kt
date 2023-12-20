package desktop.todo.repository

import desktop.todo.data.dao.ContentDao
import desktop.todo.model.ContentEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContentRepositoryImpl @Inject constructor(private val contentDao: ContentDao) :
    ContentRepository {

    override fun loadList() = contentDao.selectAll()

    override suspend fun insert(item: ContentEntity) {
        contentDao.insert(item)
    }

    override suspend fun modify(item: ContentEntity) {
        contentDao.insert(item)  // 충돌이 발생할 때 덮어쓰기 설정을 해놓아서 insert 활용
    }

    override suspend fun delete(item: ContentEntity) {
        contentDao.delete(item)
    }

}