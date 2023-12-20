package desktop.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import desktop.todo.databinding.ActivityMainBinding
import desktop.todo.model.ContentEntity
import desktop.todo.viewModel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // 뷰모델 추가
    private val viewModel: MainViewModel by viewModels()

    private val adapter by lazy { ListAdapter(Handler()) }  // Handler 연결

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            view = this@MainActivity
            recyclerView.adapter = adapter
            // 구분선
            val decoration = DividerItemDecoration(this@MainActivity, LinearLayout.VERTICAL)
            recyclerView.addItemDecoration(decoration)
        }

        lifecycleScope.launch {
            viewModel.contentList
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    binding.emptyTextView.isVisible = it.isEmpty()
                    binding.recyclerView.isVisible = it.isNotEmpty()
                    adapter.submitList(it)
                }
        }
    }

    // floatinButton 눌렀을 때
    fun onclickAdd() {
        InputActivity.start(this)  // 아이템 안 넘겨줌
    }

    // 데이터 수정
    inner class Handler {
        // 아이템을 클릭하면
        fun onClickItem(item: ContentEntity) {
            InputActivity.start(this@MainActivity, item)  // 아이템 넘겨주기
        }

        fun onCheckedItem(item: ContentEntity, checked: Boolean) {
            viewModel.updateItem(item.copy(isDone = checked))
        }

        fun onLongClickItem(item: ContentEntity): Boolean {
            viewModel.deleteItem(item)
            Toast.makeText(this@MainActivity, "삭제 완료", Toast.LENGTH_SHORT).show()
            return false
        }
    }

}