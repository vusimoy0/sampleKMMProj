package za.co.kernelpanic.food2forkkmm.datasource.network

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*

actual class KtorClientFactory {

    actual fun build(): HttpClient {
        return HttpClient(OkHttp) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }
}