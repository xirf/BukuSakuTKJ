package id.my.andka.bstkj.ui.screen.home

import android.util.Log
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
            try {
                articles = repository.getCachedArticles()
                if (articles.isEmpty()) {
                    articles = repository.getArticles()
                }
                fetchGroups()
            } catch (e: Exception) {
                Log.e("VIEWMODEL", e.message, e)
                articles = emptyList()
                groups = emptyList()
            }
        }
    }

    private fun fetchGroups(){
        viewModelScope.launch {
            groups = repository.getGroup()
        }
    }
}