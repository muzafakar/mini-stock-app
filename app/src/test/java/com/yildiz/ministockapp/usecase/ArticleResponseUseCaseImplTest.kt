package com.yildiz.ministockapp.usecase

import com.yildiz.ministockapp.api.ArticleApi
import com.yildiz.ministockapp.model.Article
import com.yildiz.ministockapp.model.ArticleResponse
import com.yildiz.ministockapp.model.Source
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class ArticleUseCaseImplTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var articleApi: ArticleApi

    private lateinit var articleUseCase: ArticleUseCase

    @Before
    fun setUp() {
        articleUseCase = ArticleUseCaseImpl(articleApi)
    }


    @Test
    fun `should return articles`() = runTest {
        val dummyArticles = listOf(
            newArticle(withTitle = "Investing is good"),
            newArticle(withTitle = "Smart investment"),
        )
        coEvery { articleApi.getArticles() } returns Response.success(
            newNews(
                withArticles = dummyArticles,
                withTotalResult = dummyArticles.size
            )
        )
        val articles = articleUseCase.getArticles()

        assertEquals(dummyArticles, articles)
    }

    private fun newSource(
        withId: String = "",
        withName: String = "",
    ) = Source(
        withId,
        withName
    )

    private fun newArticle(
        withAuthor: String = "",
        withContent: String = "",
        withDescription: String = "",
        withPublishedAt: String = "",
        withSource: Source = newSource(),
        withTitle: String = "",
        withUrl: String = "",
        withUrlToImage: String = "",
    ) = Article(
        withAuthor,
        withContent,
        withDescription,
        withPublishedAt,
        withSource,
        withTitle,
        withUrl,
        withUrlToImage
    )

    private fun newNews(
        withArticles: List<Article> = emptyList(),
        withStatus: String = "",
        withTotalResult: Int = 0,
    ) = ArticleResponse(
        withArticles,
        withStatus,
        withTotalResult
    )
}