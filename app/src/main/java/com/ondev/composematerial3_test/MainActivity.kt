package com.ondev.composematerial3_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ondev.composematerial3_test.ui.theme.ComposeMaterial3_TestTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeMaterial3_TestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}

//This is only for test
data class Person(var name: String = "", var address: String = "", var age: Int = 0) {
    private val listChars = ('a'..'z').toList()

    init {
        if (name.isEmpty()) name = randomText(6, 10)
        if (address.isEmpty()) address = "${randomText(8, 15)} ${randomText(8, 15)}"
        if (age == 0) age = Random.nextInt(6, 100)
    }

    private fun randomText(start: Byte, end: Byte): String {
        var result = ""
        for (i in 1..Random.nextInt(start.toInt(), end.toInt())) {
            result += listChars[Random.nextInt(listChars.size - 1)]
        }
        return result
    }
}

fun generateFakePersonData(size: Int): List<Person> {
    val list = mutableListOf<Person>()

    for (i in 1..size) {
        list.add(Person())
    }

    return list
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {

    val listPersons = generateFakePersonData(size = 200)

    val showDialog = remember {
        mutableStateOf(false)
    }

    val textShow = remember {
        mutableStateOf("")
    }

    val scaffoldState = rememberScaffoldState()
    val lazyState = rememberLazyListState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MediumTopAppBar(
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior { true },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                title = { Text(text = "App title") },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.AccountBox, contentDescription = null)
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.AccountBox, contentDescription = null)
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.AccountBox, contentDescription = null)
                    }

                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog.value = true }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        },
    ) {
        Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
            LazyColumn(
                state = lazyState,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(listPersons) { currentPerson ->
                    PersonItem(currentPerson)
                }

                item { Text(text = textShow.value) }
                item {
                    Button(onClick = {

                    }) {
                        Icon(Icons.Default.MailOutline, contentDescription = null)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "Click me")
                    }
                }
            }

            AnimatedVisibility(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 47.dp),
                visible = showDialog.value,
                enter = slideInVertically(
                    initialOffsetY = { -50 }
                ) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { -50 }) + fadeOut()
            ) {
                AlertDialog(
                    icon = { Icon(Icons.Default.AccountBox, contentDescription = null) },
                    title = { Text(text = "Titulo") },
                    text = {
                        Text(text = "Testing Dialog")
                    },
                    confirmButton = {
                        Button(onClick = {
                            showDialog.value = false
                            textShow.value = "Confiramdo"
                        }) {
                            Text(text = "OK")

                        }
                    }, dismissButton = {
                        Button(onClick = {
                            textShow.value = "Cancelado"
                            showDialog.value = false
                        }) {
                            Text(text = "Cancel")
                        }
                    }, onDismissRequest = {
                        showDialog.value = true
                    }
                )
            }
        }
    }

}

@Composable
fun PersonItem(currentPerson: Person) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(20)

    ) {
        Column(Modifier.fillMaxSize()) {
            Text(text = "Name: ${currentPerson.name}")
            Text(text = "Address: ${currentPerson.address}")
            Text(text = "Age: ${currentPerson.age}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PersonItemPreview() {
    ComposeMaterial3_TestTheme {
        PersonItem(currentPerson = Person())
    }
}
