package datasource

import com.jayesh.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.JsonElement

interface ApiDataSource {
    suspend fun getWordInfo(word: String): JsonElement?
}

class ApiDataSourceImpl(
    private val httpClient: HttpClient,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ApiDataSource {

    override suspend fun getWordInfo(word: String): JsonElement? {
        return withContext(ioDispatcher) {
            val httpResponse = httpClient.get(urlString = baseUrl) {
                url {
                    appendPathSegments(word)
                    parameters.append("key", BuildConfig.API_KEY)
                }
            }
            if (httpResponse.status.value in 200..299) {
                println("Successful response!")
                return@withContext httpResponse.body<JsonElement>()
            } else {
                null
            }
        }
    }
}

// https://dictionaryapi.com/api/v3/references/collegiate/json/test?key=api_key
const val baseUrl = "https://dictionaryapi.com/api/v3/references/collegiate/json/"
