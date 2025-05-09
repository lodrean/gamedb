package lod.gamedb.app.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Custom search bar component.
 *
 * @param query The current search query.
 * @param onQueryChange Callback for when the query changes.
 * @param onSearch Callback for when the search button is clicked.
 * @param modifier Modifier for the search bar.
 */
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf(query) }
    
    Row(
        modifier = modifier.padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { 
                text = it
                onQueryChange(it)
            },
            modifier = Modifier.weight(1f),
            placeholder = { Text("Search games...") },
            singleLine = true
        )
        
        Spacer(modifier = Modifier.width(8.dp))
        
        Button(
            onClick = { onSearch(text) }
        ) {
            Text("Search")
        }
    }
}