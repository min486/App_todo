package desktop.todo

import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import desktop.todo.databinding.ItemContentBinding
import desktop.todo.model.ContentEntity

class ContentViewHolder(
    private val binding: ItemContentBinding,
    private val handler: MainActivity.Handler
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ContentEntity) {
        binding.item = item
        binding.handler = handler

        binding.contentCheckBox.paintFlags = if (item.isDone) {
            Paint.STRIKE_THRU_TEXT_FLAG  // 취소선
        } else {
            0
        }
    }

}