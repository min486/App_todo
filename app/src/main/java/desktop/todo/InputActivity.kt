package desktop.todo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import desktop.todo.databinding.ActivityInputBinding
import desktop.todo.model.ContentEntity
import desktop.todo.viewModel.InputViewModel

@AndroidEntryPoint
class InputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputBinding

    // 뷰모델 추가
    private val viewModel : InputViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater).apply {
            setContentView(root)
            // LiveData는 앱 구성요소의 생명주기를 인지하기 때문에
            // LiveData의 lifecycleOwner가 현재 activity라고 명시 (LiveData를 DataBinding에서 사용할 때)
            lifecycleOwner = this@InputActivity
            // 뷰모델 연결
            viewModel = this@InputActivity.viewModel
        }

        // 액션바 좌측상단에 뒤로가기 활성화
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 아이템을 받으면 데이터 넣어주기
        (intent.getSerializableExtra(ITEM) as? ContentEntity)?.let {
            viewModel.initData(it)
        }

        // 입력완료가 끝나면 (viewModel의 LiveData 값이 바뀔때마다 UI가 갱신)
        viewModel.doneEvent.observe(this) {
            Toast.makeText(this, "입력 완료", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    // 활성화된 부분 연결
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    // 데이터를 받아오는 부분 (수정을 위해)
    companion object {
        private const val ITEM = "item"

        fun start(context: Context, item: ContentEntity? = null) {
            Intent(context, InputActivity::class.java).apply {
                putExtra(ITEM, item)
            }.run {
                context.startActivity(this)
            }
        }
    }

}