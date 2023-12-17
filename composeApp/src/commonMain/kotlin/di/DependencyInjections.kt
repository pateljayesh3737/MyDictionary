package di

import datasource.ApiDataSource
import datasource.ApiDataSourceImpl
import io.ktor.client.HttpClient
import network.buildKtorHttpClient
import org.koin.dsl.module
import repository.DictionaryRepository
import repository.DictionaryRepositoryImpl

val httpClientModule = module {
    single<HttpClient> {
        buildKtorHttpClient
    }
}

val networkModule = module {
    single<ApiDataSource> {
        ApiDataSourceImpl(httpClient = get<HttpClient>())
    }

    single<DictionaryRepository> {
        DictionaryRepositoryImpl(apiDataSource = get())
    }
}

val modules = listOf(httpClientModule, networkModule)