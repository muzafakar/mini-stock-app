package com.yildiz.ministockapp

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import com.yildiz.ministockapp.api.ArticleApi
import com.yildiz.ministockapp.model.ArticleResponse
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStreamReader
import java.io.Reader
import java.nio.charset.StandardCharsets


@ExperimentalSerializationApi
class ArticleApiTest {
    private lateinit var service: ArticleApi
    private lateinit var server: MockWebServer

    @Before
    fun setup() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl("https://saurav.tech/NewsAPI/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ArticleApi::class.java)
    }

    private fun enqueueMockResponse() {
        javaClass.classLoader?.let {
            val ins = it.getResourceAsStream("news_response.json")
            val source = ins.source().buffer()
            val mockResponse = MockResponse().apply {
                setBody(source.readString(Charsets.UTF_8))
                setResponseCode(200)
            }
            server.enqueue(mockResponse)
        }
    }

    private fun getReflectedResponse(): ArticleResponse? {
        var articleResponse: ArticleResponse? = null
        javaClass.classLoader?.let {
            val ins = it.getResourceAsStream("news_response.json")
            val reader: Reader = InputStreamReader(ins, StandardCharsets.UTF_8)
            val jsonReader = JsonReader(reader)
            articleResponse =
                Gson().fromJson<ArticleResponse>(jsonReader, ArticleResponse::class.java)
        }

        return articleResponse
    }

    @Test
    fun `getNews request should received expected`() = runBlocking {
        enqueueMockResponse()

        val response = service.getArticles()

        assertEquals(response.isSuccessful, true)
        assertEquals(response.body(), getReflectedResponse())
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}