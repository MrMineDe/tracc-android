package mrmine.mminl.tracc

import android.app.LauncherActivity
import android.graphics.pdf.models.ListItem
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Main()
        }
    }
}

@Composable
fun Main() {
    val navController = rememberNavController()
    val items = listOf("Tracc", "Habits")

    val colors = if(isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    MaterialTheme(colorScheme = colors) {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    items.forEach { route ->
                        val icon =
                            if (route == "Tracc") Icons.Default.DateRange else Icons.AutoMirrored.Filled.List
                        NavigationBarItem(
                            icon = { Icon(icon, contentDescription = route) },
                            label = { Text(route) },
                            selected = navController.currentBackStackEntryAsState().value?.destination?.route == route,
                            onClick = {
                                if (navController.currentDestination?.route != route) {
                                    navController.navigate(route) {
                                        popUpTo(navController.graph.startDestinationId)
                                        launchSingleTop = true
                                    }
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(navController, startDestination = "Tracc", Modifier.padding(innerPadding)) {
                composable("Tracc") {
                    WindowTracc()
                }
                composable("Habits") {
                    WindowHabits()
                }
            }
        }
    }
}


@Composable
fun WindowTracc() {
    val items_activity = remember { mutableStateListOf( *IntArray(24).toTypedArray()) }
    val selected_activity = 1
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(24) { item ->
            val bgcolor = if(items_activity[item] == 1) Color.Red else Color.Unspecified
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { items_activity[item] = selected_activity }
                    .padding(1.dp)
                    .background(bgcolor)
                    .padding(15.dp)
            ) {
                Text (
                    text = "Hour #$item",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun WindowHabits() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Build your Habits")
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Main()
}