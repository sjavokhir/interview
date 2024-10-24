package uz.javokhirdev.interview.data

import cafe.adriel.voyager.core.lifecycle.JavaSerializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContentModel(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("markdown") val markdown: String? = null,
): JavaSerializable