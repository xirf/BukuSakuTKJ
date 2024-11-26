package id.my.andka.bstkj.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.my.andka.bstkj.data.Article
import id.my.andka.bstkj.data.ArticleRepository
import id.my.andka.bstkj.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ArticleRepository
) : ViewModel() {

    private val _articles: MutableStateFlow<UiState<List<Article>>> =
        MutableStateFlow(UiState.Idle())
    private val _groups: MutableStateFlow<UiState<List<String>>> = MutableStateFlow(UiState.Idle())
    val articles: StateFlow<UiState<List<Article>>> get() = _articles
    val groups: StateFlow<UiState<List<String>>> get() = _groups

    init {
        _groups.value = UiState.Loading()
        _articles.value = UiState.Loading()
        fetchArticles()
    }

    private fun fetchArticles() {
        viewModelScope.launch {
            repository.getArticles().collect { uiState ->
                _articles.value = uiState
                Log.d("HomeViewModel", "fetchArticles: ${uiState.data}")
            }
        }
    }

    private fun fetchGroups() {
        viewModelScope.launch {
            repository.getGroup().catch {
                _groups.value = UiState.Error("Failed to fetch data: ${it.message}")
            }.collect {
                _groups.value = it
            }
        }
    }
}