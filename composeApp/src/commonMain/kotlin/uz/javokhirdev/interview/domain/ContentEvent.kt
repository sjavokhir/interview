package uz.javokhirdev.interview.domain

import uz.javokhirdev.interview.data.CategoryMode

sealed interface ContentEvent {
    data class FetchContents(val category: CategoryMode) : ContentEvent
}