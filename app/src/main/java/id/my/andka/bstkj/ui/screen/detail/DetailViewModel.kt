package id.my.andka.bstkj.ui.screen.detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.my.andka.bstkj.data.Article
import androidx.lifecycle.viewModelScope
import id.my.andka.bstkj.data.ArticleRepository
import id.my.andka.bstkj.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: ArticleRepository
): ViewModel() {

    data class ArticleState(
        val articleResult: UiState<Article> = UiState.Loading()
    )

    private val _viewModelResult = MutableStateFlow(ArticleState())
    val viewModelResult: StateFlow<ArticleState> =  _viewModelResult

    fun fetchArticle(slug: String) {
        viewModelScope.launch{
            repository.getArticleBySlug(slug).collect { article ->
                _viewModelResult.value = ArticleState(articleResult = UiState.Success(article))
            }
        }
    }

}