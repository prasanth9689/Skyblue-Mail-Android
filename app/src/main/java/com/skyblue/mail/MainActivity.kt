package com.skyblue.mail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skyblue.mail.inbox.EmailItem
import com.skyblue.mail.inbox.EmailViewModel
import com.skyblue.mail.ui.theme.SkyblueMailAndroidTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SkyblueMailAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BasicCustomNavigation()
                }
            }
        }
    }
}


fun screenName(screen: String): String {
    return when (screen) {
        Screens.InboxScreen -> "Inbox"
        Screens.SentScreen -> "Sent"
        Screens.DraftsScreen -> "Drafts"
        Screens.ImportantScreen -> "Important"
        Screens.SpamScreen -> "Spam"
        Screens.TrashScreen -> "Trash"
        Screens.CalendarScreen -> "Calendar"
        Screens.SettingsScreen -> "Settings"
        Screens.LogoutScreen -> "Logout"
        else -> ""
    }
}

@Composable
fun Screen(title: String) {
    Column(
        modifier = Modifier
            .background(color = colorResource(R.color.black))
            .padding(20.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InboxScreen(emailViewModel: EmailViewModel = viewModel()) {
    val messages by emailViewModel.messageState.collectAsState()
            LazyColumn(
                modifier = Modifier
                    .padding(0.dp)
                    .background(colorResource(R.color.white))
                    .fillMaxSize(),
                contentPadding = PaddingValues(vertical = 0.dp),
            ) {
                itemsIndexed(
                    items = messages,
                    // Provide a unique key based on the email content
                    key = { _, item -> item.hashCode() }
                ) { _, emailContent ->
                    // Display each email item
                    EmailItem(emailContent, onRemove = emailViewModel::removeItem)
                }
            }
        }



@Composable
fun SentScreen() {
   // Screen(screenName(Screens.SentScreen))

    Column(
        modifier = Modifier
            .background(color = colorResource(R.color.sent))
            .padding(20.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = Screens.SentScreen,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun DraftScreen() {
   // Screen(screenName(Screens.DraftsScreen))

    Column(
        modifier = Modifier
            .background(color = colorResource(R.color.drafts))
            .padding(20.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = Screens.DraftsScreen,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun MainScreen(currentScreen: String) {
    if (currentScreen == Screens.InboxScreen) {
        InboxScreen()
        return
    }

    if (currentScreen == Screens.SentScreen) {
        SentScreen()
        return
    }

    if (currentScreen == Screens.DraftsScreen) {
        DraftScreen()
        return
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun BasicCustomNavigation() {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var currentScreen by remember { mutableStateOf(Screens.InboxScreen) }
    var previousScreen by remember { mutableStateOf("") }

    NavigationDrawer(
        drawerState = drawerState,
        scaffoldContent = {
            NavigationScaffold(
                currentScreen = currentScreen,
                previousScreen = previousScreen,
                onBackPressed = {
                    currentScreen = previousScreen
                    previousScreen = ""
                },
                onOpenDrawerPressed = {
                    scope.launch { drawerState.open() }
                }
            )
        },
        onScreenChanged = {
            previousScreen = currentScreen
            currentScreen = it

        }
    )
}

@Composable
fun GoBackButton(onClick: () -> Unit) {
    IconButton(onClick = { onClick() }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Back",
            tint = colorResource(R.color.black)
        )
    }
}

@Composable
fun OpenMenuButton(onClick: () -> Unit) {
    IconButton(onClick = { onClick() }) {
        Icon(
            imageVector = Icons.Filled.Menu,
            contentDescription = "Menu",
            tint = colorResource(R.color.black)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    showPreviousButton: Boolean,
    onBackPressed: () -> Unit,
    onOpenDrawerPressed: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = colorResource(R.color.white),
            titleContentColor = colorResource(R.color.black),
        ),
        title = { Text("Skyblue Mail") },
        actions = {
            // Replace with your image resource
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 8.dp)
            )
        },
        navigationIcon = {
            if (showPreviousButton) {
                GoBackButton(onClick = { onBackPressed() })
            } else {
                OpenMenuButton(onClick = { onOpenDrawerPressed() })
            }

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationScaffold(
    currentScreen: String,
    previousScreen: String,
    onBackPressed: () -> Unit,
    onOpenDrawerPressed: () -> Unit
) {
    Scaffold(
        // Here is the top bar
        topBar = {
            TopBar(
                showPreviousButton = previousScreen.isNotEmpty(),
                onBackPressed = { onBackPressed() },
                onOpenDrawerPressed = { onOpenDrawerPressed() }
            )
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            MainScreen(currentScreen)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(
    drawerState: DrawerState,
    scaffoldContent: @Composable () -> Unit,
    onScreenChanged: (String) -> Unit
) {

    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.requiredWidth(300.dp)) {

                // OnScreenChanged events
                DrawerItem(
                    screen = Screens.InboxScreen,
                    icon = {Icon(painter = painterResource(id = R.drawable.inbox),
                                                           contentDescription = "Inbox",
                                                           modifier = Modifier.size(25.dp))}

                ) {
                    onScreenChanged(Screens.InboxScreen)
                    scope.launch { drawerState.close() }
                }

                Divider()

                DrawerItem(
                    Screens.SentScreen,
                    icon = {Icon(painter = painterResource(id = R.drawable.ic_sent),
                                                           contentDescription = "Sent",
                                                           modifier = Modifier.size(25.dp))}
                ) {
                    onScreenChanged(Screens.SentScreen)
                    scope.launch { drawerState.close() }
                }

                Divider()

                DrawerItem(
                    Screens.DraftsScreen,
                    icon = {Icon(painter = painterResource(id = R.drawable.ic_draft),
                                                           contentDescription = "Draft",
                                                           modifier = Modifier.size(25.dp))}
                ) {
                    onScreenChanged(Screens.DraftsScreen)
                    scope.launch { drawerState.close() }
                }
                Divider()

                DrawerItem(
                    Screens.ImportantScreen,
                    icon = {Icon(painter = painterResource(id = R.drawable.ic_important),
                        contentDescription = "Important",
                        modifier = Modifier.size(25.dp))}
                ) {
                    onScreenChanged(Screens.ImportantScreen)
                    scope.launch { drawerState.close() }
                }
                Divider()

                // new to
                DrawerItem(
                    Screens.SpamScreen,
                    icon = {Icon(painter = painterResource(id = R.drawable.ic_spam),
                        contentDescription = "Spam",
                        modifier = Modifier.size(25.dp))}
                ) {
                    onScreenChanged(Screens.SpamScreen)
                    scope.launch { drawerState.close() }
                }
                Divider()

                DrawerItem(
                    Screens.TrashScreen,
                    icon = {Icon(painter = painterResource(id = R.drawable.ic_trash),
                        contentDescription = "Trash",
                        modifier = Modifier.size(25.dp))}
                ) {
                    onScreenChanged(Screens.TrashScreen)
                    scope.launch { drawerState.close() }
                }
                Divider()

                DrawerItem(
                    Screens.CalendarScreen,
                    icon = {Icon(painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = "Calendar",
                        modifier = Modifier.size(25.dp))}
                ) {
                    onScreenChanged(Screens.CalendarScreen)
                    scope.launch { drawerState.close() }
                }
                Divider()

                DrawerItem(
                    Screens.CalendarScreen,
                    icon = {Icon(painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = "Calendar",
                        modifier = Modifier.size(25.dp))}
                ) {
                    onScreenChanged(Screens.CalendarScreen)
                    scope.launch { drawerState.close() }
                }
                Divider()

                DrawerItem(
                    Screens.SettingsScreen,
                    icon = {Icon(painter = painterResource(id = R.drawable.ic_settings),
                        contentDescription = "Settings",
                        modifier = Modifier.size(25.dp))}
                ) {
                    onScreenChanged(Screens.SettingsScreen)
                    scope.launch { drawerState.close() }
                }
                Divider()

                DrawerItem(
                    Screens.LogoutScreen,
                    icon = {Icon(painter = painterResource(id = R.drawable.ic_settings),
                        contentDescription = "Logout",
                        modifier = Modifier.size(25.dp))}
                ) {
                    onScreenChanged(Screens.LogoutScreen)
                    scope.launch { drawerState.close() }
                }
                Divider()
            }
        },
    ) {
        scaffoldContent()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerItem(screen: String, icon: @Composable () -> Unit, onClick: () -> Unit) {
    NavigationDrawerItem(

        label = { Text(screenName(screen)) },
        onClick = {
            // Click event
            onClick()
        },
        icon = { icon() },
        selected = false
    )
}

