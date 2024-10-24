package uz.javokhirdev.interview.domain

import uz.javokhirdev.interview.data.ContentModel

data class ContentState(
    val contents: List<ContentModel> = emptyList(),
    val markdown: String? = null
)
