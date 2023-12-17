package repository

import datasource.ApiDataSource
import kotlinx.serialization.json.JsonElement

interface DictionaryRepository {

    suspend fun getWordInfo(word: String): JsonElement?
}

class DictionaryRepositoryImpl(private val apiDataSource: ApiDataSource) : DictionaryRepository {
    override suspend fun getWordInfo(word: String): JsonElement? {
        return apiDataSource.getWordInfo(word)
    }

}