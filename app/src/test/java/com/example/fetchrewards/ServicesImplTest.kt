package com.example.fetchrewards

import com.example.fetchrewards.data.remote.network.ServicesImpl
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert.*
import org.junit.Test

class ServicesImplTest {

    private fun createMockClient(responseJson: String, status: HttpStatusCode = HttpStatusCode.OK): HttpClient {
        return HttpClient(MockEngine) {
            engine {
                addHandler {
                    respond(
                        content = responseJson,
                        status = status,
                        headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
                }
            }

            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    @Test
    fun `getItems returns valid list`() = runTest {
        val json = """
            [
                { "id": 1, "listId": 1, "name": "Item A" },
                { "id": 2, "listId": 2, "name": "Item B" }
            ]
        """.trimIndent()

        val client = createMockClient(json)
        val service = ServicesImpl(client)

        val result = service.getItems()

        assertEquals(2, result.size)
        assertEquals("Item A", result[0].name)
        assertEquals(1, result[0].listId)
    }

    @Test
    fun `getItems filters blank or null names manually`() = runTest {
        val json = """
            [
                { "id": 1, "listId": 1, "name": "Item A" },
                { "id": 2, "listId": 1, "name": null },
                { "id": 3, "listId": 1, "name": "" }
            ]
        """.trimIndent()

        val client = createMockClient(json)
        val service = ServicesImpl(client)

        val result = service.getItems()

        // Note: Service itself doesn't filter — filtering happens in ViewModel/UI
        // So this test just verifies raw data is parsed correctly
        assertEquals(3, result.size)
        assertNull(result[1].name)
        assertEquals("", result[2].name)
    }

    @Test
    fun `getItems handles bad response status`() = runTest {
        val client = createMockClient("Internal Server Error", HttpStatusCode.InternalServerError)
        val service = ServicesImpl(client)

        try {
            service.getItems()
            fail("Expected exception not thrown")
        } catch (e: Exception) {
            // Expected behavior — Ktor will throw on 500
            assertTrue(true)
        }
    }
}
