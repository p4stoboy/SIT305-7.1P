package sit305.a71p

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LostAndFoundViewModel(private val itemRepo: LostAndFoundItemRepo) : ViewModel() {
    val items: StateFlow<List<LostAndFoundItem>> = itemRepo.allItems

    fun getItem(itemId: Int): LostAndFoundItem? {
        return items.value.find { it.id == itemId }
    }

    fun insertItem(item: LostAndFoundItem) = viewModelScope.launch {
        itemRepo.insert(item)
    }

    fun removeItem(item: LostAndFoundItem) = viewModelScope.launch {
        itemRepo.delete(item)
    }
}