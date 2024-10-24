package uz.javokhirdev.interview.data

import interview.composeapp.generated.resources.Res
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi

object DataSource {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    @OptIn(ExperimentalResourceApi::class)
    suspend fun getContents(category: CategoryMode): Flow<List<ContentModel>> {
        return flow {
            val contentPath = when (category) {
                CategoryMode.Kotlin -> "files/contents_kotlin.json"
                CategoryMode.Android -> "files/contents_android.json"
                CategoryMode.Compose -> "files/contents_compose.json"
            }
            val contentsJson = Res.readBytes(contentPath).decodeToString()

            val contents = json.decodeFromString<List<ContentModel>>(contentsJson).map {
                val markdownPath = when (category) {
                    CategoryMode.Kotlin -> "files/kotlin/content_${it.id}.md"
                    CategoryMode.Android -> "files/android/content_${it.id}.md"
                    CategoryMode.Compose -> "files/compose/content_${it.id}.md"
                }
                val markdown = Res.readBytes(markdownPath).decodeToString()

                it.copy(markdown = markdown)
            }

            emit(contents)
        }.catch {
            emit(emptyList())
        }.flowOn(Dispatchers.IO)
    }
}