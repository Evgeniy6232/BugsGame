package com.evgenii.bugsgame.ui.records

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evgenii.bugsgame.data.repository.RecordRepository
import com.evgenii.bugsgame.domain.model.RecordRow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class RecordsViewModel(repository: RecordRepository) : ViewModel() {

    val records: StateFlow<List<RecordRow>> = repository.top
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
}
