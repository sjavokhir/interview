package uz.javokhirdev.interview.domain

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.javokhirdev.interview.data.CategoryMode
import uz.javokhirdev.interview.data.DataSource

class ContentScreenModel : StateScreenModel<ContentState>(ContentState()) {

    fun onEvent(event: ContentEvent) {
        when (event) {
            is ContentEvent.FetchContents -> fetchContents(event.category)
        }
    }

    private fun fetchContents(category: CategoryMode) {
        screenModelScope.launch {
            DataSource.getContents(category).collectLatest { contents ->
                mutableState.update { it.copy(contents = contents) }
            }
        }
    }
}