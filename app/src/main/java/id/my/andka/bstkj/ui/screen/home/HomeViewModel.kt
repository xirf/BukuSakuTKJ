package id.my.andka.bstkj.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.my.andka.bstkj.data.Article
import id.my.andka.bstkj.data.ArticleRepository
import id.my.andka.bstkj.ui.common.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ArticleRepository
) : ViewModel() {

    data class ArticleState(
        val articleResult: UiState<List<Article>> = UiState.Loading(),
        val groupResult: UiState<List<String>> = UiState.Loading()
    )

    private val _viewModelResult = MutableStateFlow(ArticleState())
    val viewModelResult: StateFlow<ArticleState> = _viewModelResult

    init {
        fetchArticles()
        fetchGroups()
    }

fun fetchArticles() {
    viewModelScope.launch {
        repository.getArticles().collect { articles ->
            if (articles.isNotEmpty()) {
                // Check if each article is valid
                Log.d("HomeViewModel", "Articles: ${articles[0]}")
                val validArticles = articles.filter {
                    it.id.isNotEmpty() &&
                    it.slug.isNotEmpty() &&
                    it.title.isNotEmpty() &&
                    it.groupName.isNotEmpty() &&
                    it.body.isNotEmpty() &&
                    it.updatedDate.isNotEmpty()
                }

                repository.clearArticles()
                repository.insertArticles(validArticles)
                _viewModelResult.update { it.copy(articleResult = UiState.Success(validArticles)) }
            }
        }
    }
}
    fun clearArticles() {
        _viewModelResult.update { it.copy(groupResult = UiState.Loading()) }

        viewModelScope.launch {
            repository.clearArticles()
            fetchArticles()
            delay(100) // Just wait idk wait for what
            fetchGroups()
        }
    }

    fun getArticlesByGroup(group: String) {
        viewModelScope.launch {
            try {
                repository.getArticlesByGroup(group).catch { e ->
                    _viewModelResult.update { it.copy(articleResult = UiState.Error(e.message.toString())) }
                }.collect { articles ->
                    _viewModelResult.update { it.copy(articleResult = UiState.Success(articles)) }
                }
            } catch (e: Exception) {
                _viewModelResult.update { it.copy(articleResult = UiState.Error(e.message.toString())) }
            }
        }
    }

    fun fetchGroups(tries: Int = 5) {
        viewModelScope.launch {
            try {
                repository.getGroups().collect { groups ->
                    if (groups.isEmpty() && tries > 0) {
                        fetchArticles()
                        fetchGroups(tries - 1)
                    } else {
                        Log.d("HomeViewModel", "Groups: $groups")
                        _viewModelResult.update { it.copy(groupResult = UiState.Success(groups)) }
                    }
                }
            } catch (e: Exception) {
                if (tries > 0) {
                    fetchGroups(tries - 1)
                } else {
                    Log.e("HomeViewModel", "Error fetching groups", e)
                    _viewModelResult.update { it.copy(groupResult = UiState.Error(e.message.toString())) }
                }
            }
        }
    }
}