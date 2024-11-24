package id.my.andka.bstkj.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.my.andka.bstkj.data.Article
import id.my.andka.bstkj.data.ArticleRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ArticleRepository
) : ViewModel() {

    var articles: List<Article> = emptyList()
    var groups: List<String> = emptyList()
    private set

    init {
        fetchArticles()
    }

    private fun fetchArticles() {
        viewModelScope.launch {
            articles = repository.getCachedArticles()
            if (articles.isEmpty()) {
                articles = repository.getArticles()
            }
            fetchGroups()
        }
    }

    private fun fetchGroups(){
        viewModelScope.launch {
            groups = repository.getGroup()
        }
    }
}