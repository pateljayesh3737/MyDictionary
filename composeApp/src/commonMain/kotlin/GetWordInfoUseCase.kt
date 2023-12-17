import kotlinx.serialization.json.JsonElement
import repository.DictionaryRepository

class GetWordInfoUseCase(private val dictionaryRepository: DictionaryRepository) {

    suspend operator fun invoke(word: String ): JsonElement? {
        return dictionaryRepository.getWordInfo(word)
    }
}