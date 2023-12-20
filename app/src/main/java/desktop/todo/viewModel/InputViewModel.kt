package desktop.todo.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import desktop.todo.model.ContentEntity
import desktop.todo.repository.ContentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class InputViewModel @Inject constructor(
    private val contentRepository: ContentRepository
) : ViewModel() {

    // 입력이 완료됐을 때 viewmodel -> InputActivity 신호를 주는 역할 (activity 닫기 위해)
    private val _doneEvent = MutableLiveData<Unit>()
    val doneEvent : LiveData<Unit> = _doneEvent

    var content = MutableLiveData<String>()
    var memo = MutableLiveData<String?>()  // 빈 값 가능

    // 아이템 들고있는 용도 (수정을 위해)
    var item : ContentEntity? = null

    // activity에서 intent에 데이터가 존재할때만 실행
    fun initData(item: ContentEntity) {
        this.item = item
        content.value = item.content
        memo.value = item.memo
    }

    fun insertData() {
        content.value?.let { content ->
            viewModelScope.launch(Dispatchers.IO) {
                contentRepository.insert(
                    // 바뀐부분만 추가 (수정)
                    item?.copy(
                        content = content,
                        memo = memo.value
                    ) ?: ContentEntity(content = content, memo = memo.value)  // 처음 만들때 (생성)
                )
                _doneEvent.postValue(Unit)  // IO scope 이기 때문에 Main Thread에 데이터 보내기 위해 사용
            }
        }
    }

}