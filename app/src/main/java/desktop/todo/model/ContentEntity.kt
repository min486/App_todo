package desktop.todo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Content")
data class ContentEntity(
    @PrimaryKey(true)
    val id: Int = 0,
    val content: String,
    val memo: String? = null,
    val isDone: Boolean = false

) : Serializable