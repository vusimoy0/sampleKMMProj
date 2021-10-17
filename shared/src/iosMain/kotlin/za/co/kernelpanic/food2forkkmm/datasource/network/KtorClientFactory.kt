package za.co.kernelpanic.food2forkkmm.datasource.network

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.engine.ios.*

actual class KtorClientFactory {

    actual fun build(): HttpClient {
        return HttpClient(Ios) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }
}