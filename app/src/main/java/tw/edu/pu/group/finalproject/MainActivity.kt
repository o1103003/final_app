@file:OptIn(ExperimentalMaterial3Api::class)

package tw.edu.pu.group.finalproject

import android.content.ClipData
import android.media.MediaPlayer
import android.media.browse.MediaBrowser
import android.net.Uri
import android.os.Bundle
import android.provider.SyncStateContract.Columns
import android.text.Layout
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.Util
import androidx.media3.datasource.DefaultDataSourceFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.actions.ItemListIntents
import com.google.common.collect.Table.Cell
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import tw.edu.pu.group.finalproject.ui.theme.FinalTheme
import kotlin.concurrent.fixedRateTimer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinalTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable

fun Greeting(name: String) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val showMenu = remember {

        mutableStateOf(false)

    }
    Column {

        TopAppBar(

            title = { Text(text = "Travel in Indonesia") },

            actions = {

                IconButton(

                    onClick = {
                        Toast.makeText(
                            context, "作者：鍾愛麗，鍾愛美，鍾永豐，黃植達，木内陽士，洪友錦",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                ) {

                    Icon(Icons.Rounded.AccountBox, contentDescription = "author")

                }

                IconButton(

                    onClick = { showMenu.value = true }

                ) {

                    Icon(Icons.Default.MoreVert, contentDescription = null)

                }
                DropdownMenu(

                    expanded = showMenu.value, onDismissRequest = { showMenu.value = false }

                ) {

                    DropdownMenuItem(text = {Text( "Home" )}, onClick = { navController.navigate("JumpFirst") })
                    DropdownMenuItem(text = { Text("Jakarta" )}, onClick = { navController.navigate("JumpSecond")})
                    DropdownMenuItem(text = { Text("Bali" )}, onClick = { navController.navigate("JumpThird") })
                    DropdownMenuItem(text = {Text( "Bandung") }, onClick = { navController.navigate("JumpFourth")})
                    DropdownMenuItem(text = {Text("Jogjakarta") }, onClick = { navController.navigate("JumpFifth") })
                    DropdownMenuItem(text = {Text("Currency") }, onClick = { navController.navigate("JumpSixth")})
                    DropdownMenuItem(text = { Text("Weather") }, onClick = { navController.navigate("JumpSeventh") })
                    DropdownMenuItem(text = { Text("Sentences") }, onClick = { navController.navigate("JumpEighth")})
                }
            }
        )

        NavHost(navController = navController, startDestination = "JumpFirst") {

            composable("JumpFirst") {

                FirstScreen(navController = navController)

            }

            composable("JumpSecond") {

                SecondScreen(navController = navController)

            }
            composable("JumpThird") {

                ThirdScreen(navController = navController)

            }

            composable("JumpFourth") {

                FourthScreen(navController = navController)

            }
            composable("JumpFifth") {

                FifthScreen(navController = navController)

            }

            composable("JumpSixth") {

                SixthScreen(navController = navController)

            }
            composable("JumpSeventh") {

                SeventhScreen(navController = navController)

            }

            composable("JumpEighth") {

                EighthScreen(navController = navController)

            }

        }
    }

}
@Composable
fun VideoPlayer(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val link = "https://rr3---sn-o097znsd.googlevideo.com/videoplayback?expire=1685830639&ei=j2d7ZPPPNsbukgbXmJDwDg&ip=198.199.101.47&id=o-AIQ5rmabo-TFAIVAl6ANSbov3dC038vx6YjW35dnx77w&itag=18&source=youtube&requiressl=yes&mh=1n&mm=31,26&mn=sn-o097znsd,sn-a5mekn6d&ms=au,onr&mv=m&mvi=3&pl=24&initcwndbps=185000&spc=qEK7B_HusfEk0HB_nLSsacmhsGwPNSyqeVBvdB7Zig&vprv=1&svpuc=1&mime=video/mp4&ns=W_FVKYTzu5ZlYkTwpsQ13k0N&gir=yes&clen=247392943&ratebypass=yes&dur=3933.808&lmt=1685005162575480&mt=1685808760&fvip=4&fexp=24007246,51000023&c=WEB_EMBEDDED_PLAYER&txp=5319224&n=692hMskl0uZEbeD&sparams=expire,ei,ip,id,itag,source,requiressl,spc,vprv,svpuc,mime,ns,gir,clen,ratebypass,dur,lmt&sig=AOq0QJ8wRAIgagGXrQu7IGDk9nsF2WKuLy_d5dUwCc94IX7t19-TDK0CIFd3D6MFrwLndHGLeEen-ICe8TiQNf9-ZOrzLr4stxAu&lsparams=mh,mm,mn,ms,mv,mvi,pl,initcwndbps&lsig=AG3C_xAwRQIhAOWdL1nrJXNvgHKFQIaSyNMunIxhFKNl0vK0nppo3YmrAiB9Q8RwMwQvwH8pFFqZMBX3oLjWc9LAcMlAIqMqMZeOhg==&title=INDONESIAN%20PARADISE%20-%20SERENE%20SCENERY%20AND%20SOOTHING%20MEDITATION%20MUSIC"

    val exoPlayer = ExoPlayer.Builder(context).build()

    val mediaItem = MediaItem.fromUri(android.net.Uri.parse(link))

    exoPlayer.setMediaItem(mediaItem)

    val playerView = PlayerView(context)

    playerView.player = exoPlayer
    DisposableEffect(AndroidView(factory = { playerView })) {

        exoPlayer.prepare()

        exoPlayer.playWhenReady = true

        onDispose {

            exoPlayer.release()

        }

    }
}
@Composable
fun EighthScreen(navController: NavHostController) {
    var Phrases = arrayListOf(
        "多少钱? — Berapa harganya?",
        "厕所在哪里? — Di mana kamar mandinya?",
        "谢谢 — Terima kasih",
        "不客气 — Sama-sama",
        "我听不懂 — Saya tidak mengerti",

        "早上好 — Selamat pagi",
        "午安 — Selamat siang",
        "晚安 — Selamat malam",
        "………在哪? — ………di mana?",
        "能幫我拍張照片嗎? — Apakah bisa bantu saya mengambil foto?"
    )
    Column(
        modifier = Modifier

            .fillMaxSize(),

        verticalArrangement = Arrangement.Top,

        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Phrases")

        LazyColumn {

            items(10) { index ->

                Text(text = Phrases[index % 10])

            }
        }
    }
}
@Composable
fun SeventhScreen(navController: NavHostController) {
    Column(modifier = Modifier

        .fillMaxSize(),

        verticalArrangement = Arrangement.Top,

        horizontalAlignment = Alignment.CenterHorizontally){
        // Declare a string that contains a url
        val mUrl = "https://www.accuweather.com/en/id/indonesia-weather"

        // Adding a WebView inside AndroidView
        // with layout as full screen
        AndroidView(factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()
                loadUrl(mUrl)
            }
        }, update = {
            it.loadUrl(mUrl)
        })
    }
    }


@Composable
fun SixthScreen(navController: NavHostController) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    Column() {
        TextField(
            value = text,
            label = { Text(text = "IDR") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { it ->
                text = it
            })
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {}) {
                Text(text = "=")
            }
            Button(onClick = {}) {
                Text(text = "0")
            }
        }
    }
}
@Composable
fun FifthScreen(navController: NavHostController) {
    var Places = arrayListOf(
        R.drawable.borobudur,

        R.drawable.prambanan, R.drawable.merapi,

        R.drawable.kalibiru, R.drawable.tamansari,

        R.drawable.malioboro
    )

   var PlacesName = arrayListOf(
        "Candi Borobudur",
        "Candi Prambanan",
        "Gunung Merapi",
        "Kalibiru National Park",
        "Taman Sari",
        "Malioboro Street",
    )
    Column(modifier = Modifier.fillMaxHeight()) {
        Text(text = "Where To Go")
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(6) { index ->

                Card() {
                    Column()
                    {
                        Image(

                            painter = painterResource(id = Places[index % 6]),

                            contentDescription = "手掌圖片",
                            modifier = Modifier.padding(bottom = 20.dp)
                        )
                    }
                    Text(text = PlacesName[index % 6])
                }
            }
        }}
}
@Composable
fun FourthScreen(navController: NavHostController) {
    var Places = arrayListOf(
        R.drawable.tangkuban,

        R.drawable.tebingkeraton, R.drawable.kawahputih,

        R.drawable.trans, R.drawable.dusunbambu,

        R.drawable.curugpelangi
    )

    var PlacesName = arrayListOf(
        "Gunung Tangkuban Perahu",
        "Tebing Keraton",
        "Kawah Putih",
        "Trans Studio Bandung",
        "Dusun Bambu",
        "Curug Pelangi",
    )
    Column(modifier = Modifier.fillMaxHeight()) {
        Text(text = "Where To Go")
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(6) { index ->

                Card() {
                    Column()
                    {
                        Image(

                            painter = painterResource(id = Places[index % 6]),

                            contentDescription = "手掌圖片",
                            modifier = Modifier.padding(bottom = 20.dp)
                        )
                    }
                    Text(text = PlacesName[index % 6])
                }
            }
        }}
}

@Composable
fun ThirdScreen(navController: NavHostController) {
    var Places = arrayListOf(
        R.drawable.tanahlot,

        R.drawable.tulamben, R.drawable.tegalalang,

        R.drawable.monkeyforest, R.drawable.batur,

        R.drawable.nusapenida
    )

    var PlacesName = arrayListOf(
        "Tanah Lot Temple",
        "Tulamben",
        "Tegalalang Rice Terraces",
        "Monkey Forest",
        "Mount Batur",
        "Nusa Penida",
    )
    Column(modifier = Modifier.fillMaxHeight()) {
        Text(text = "Where To Go")
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(6) { index ->

                Card() {
                    Column()
                    {
                        Image(

                            painter = painterResource(id = Places[index % 6]),

                            contentDescription = "手掌圖片",
                            modifier = Modifier.padding(bottom = 20.dp)
                        )
                    }
                    Text(text = PlacesName[index % 6])
                }
            }
    }}
}

@Composable
fun SecondScreen(navController: NavHostController) {
    var Places = arrayListOf(
        R.drawable.jkt1,

        R.drawable.jkt2, R.drawable.jkt3,

        R.drawable.jkt4, R.drawable.jkt5,

        R.drawable.jkt6, R.drawable.jkt7,

        R.drawable.jkt8, R.drawable.jkt9, R.drawable.jkt10
    )

    var PlacesName = arrayListOf(
        "The National Monument – Iconic Landmark",
        "Istiqlal Mosque – A Marble Marvel",
        "Thousand Islands – Escape From Fast Running Life",
        "Taman Mini Indonesia Park – Cultural Tour",
        "Ancol Dreamland – Theme Park For All Ages",

        "Jakarta Cathedral – Roman Architectural Wonder",
        "Glodok Chinatown – All About Historical Treasures",
        "Pasar Seni Ancol – Cute Art Market ",
        "Kota Tua – Dutch-Inspired Architecture ",
        "SeaWorld Ancol – Home To Largest Aquarium"
    )
    var Food = arrayListOf(
        R.drawable.sotobetawi, R.drawable.keraktelor,
        R.drawable.ketoprak, R.drawable.asinanbetawi
    )
    var Foodname = arrayListOf("Soto Betawi", "Kerak Telor", "Ketoprak", "Asinan Betawi")
    Column(modifier = Modifier.fillMaxHeight()) {
        Text(text = "Where To Go")
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(10) { index ->

                Card() {
                    Column()
                    {
                        Image(

                            painter = painterResource(id = Places[index % 10]),

                            contentDescription = "手掌圖片",
                            modifier = Modifier.padding(bottom = 20.dp)
                        )
                    }
                    Text(text = PlacesName[index % 10])
                }
            }
            item {
              Row(modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.Center) {
                Text(text = "What to Eat") }}
            items(4){ index ->
                Card() {
                    Column()
                    {
                        Image(

                            painter = painterResource(id = Food[index % 4]),

                            contentDescription = "手掌圖片",
                            modifier = Modifier.padding(bottom = 20.dp)
                        )
                    }
                    Text(text = Foodname[index % 4])
                }
            }
        }
    }
 }
    @Composable

    fun FirstScreen(navController: NavController) {
        val db = Firebase.firestore
        var msg = remember { mutableStateOf("Please insert your email address.") }
        val context = LocalContext.current
        var mper = MediaPlayer()
        LazyColumn(
            modifier = Modifier

                .fillMaxSize(),

            verticalArrangement = Arrangement.Top,

            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            item {
                Row(modifier = Modifier.height(200.dp)) {
                    VideoPlayer(
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .weight(1f, fill = true)
                            .background(Color.Black)
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.weight(1f)
                    ) {
                        Image(
                            painterResource(id = R.drawable.jakarta), contentDescription = "null",
                            contentScale = ContentScale.Fit, modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .size(150.dp)
                        )
                        Button(
                            onClick = { navController.navigate("JumpSecond") },
                            colors = ButtonDefaults.buttonColors(Color.Transparent)
                        ) {
                            Text("Jakarta")
                        }
                    }

                    Box(contentAlignment = Alignment.Center, modifier = Modifier.weight(1f)) {
                        Image(
                            painterResource(id = R.drawable.bali), contentDescription = "null",
                            contentScale = ContentScale.Fit, modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .size(150.dp)
                        )
                        Button(
                            onClick = { navController.navigate("JumpThird") },
                            colors = ButtonDefaults.buttonColors(Color.Transparent)
                        ) {
                            Text(text = "Bali")
                        }
                    }


                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, top = 0.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.weight(1f)
                    ) {
                        Image(
                            painterResource(id = R.drawable.bandung),
                            contentDescription = "null",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .size(150.dp)
                        )
                        Button(
                            onClick = { navController.navigate("JumpFourth") },
                            colors = ButtonDefaults.buttonColors(Color.Transparent)

                        ) { Text(text = "Bandung") }
                    }
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.weight(1f)
                    ) {
                        Image(
                            painterResource(id = R.drawable.jogja),
                            contentDescription = "null",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .size(150.dp)
                        )
                        Button(
                            onClick = { navController.navigate("JumpFifth") },
                            colors = ButtonDefaults.buttonColors(Color.Transparent)
                        )
                        {
                            Text(text = "Jogjakarta")
                        }

                    }
                }
            }
            item {
                Row(
                    modifier = Modifier.padding(2.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        contentPadding = PaddingValues(10.dp), onClick = {

                            navController.navigate("JumpSixth")

                        },
                        shape = RoundedCornerShape(6.dp)
                    ) {

                        Text(text = "Currency ")
                        Image(

                            painterResource(id = R.drawable.currency),

                            contentDescription = "currency icon",

                            modifier = Modifier.size(15.dp)
                        )

                    }

                    Button(
                        contentPadding = PaddingValues(10.dp), onClick = {

                            navController.navigate("JumpSeventh")

                        },
                        shape = RoundedCornerShape(6.dp)
                    ) {

                        Text(text = "Weather ")
                        Image(

                            painterResource(id = R.drawable.weather),

                            contentDescription = "weather icon",

                            modifier = Modifier.size(20.dp)
                        )

                    }

                    Button(
                        contentPadding = PaddingValues(10.dp), onClick = {

                            navController.navigate("JumpEighth")

                        },
                        shape = RoundedCornerShape(6.dp)
                    ) {

                        Text(text = "Sentences ")
                        Image(

                            painterResource(id = R.drawable.sentences),

                            contentDescription = "sentences icon",

                            modifier = Modifier.size(20.dp)
                        )

                    }
                }
            }
            item {
                Column(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .background(Color.LightGray),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Subscribe to receive more information about other magnificent places to visit in Indonesia!",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(20.dp)
                    )
                    TextField(
                        value = msg.value,
                        onValueChange = { newText ->
                            msg.value = newText
                        },
                        label = { Text(text = "email") },

                        placeholder = { Text(text = "ex:example@gmail.com") },

                        )
                    Button(onClick = {

                        val user = hashMapOf(

                            "email" to msg.value,

                            )
                        db.collection("users")

                            .add(user)

                            .addOnSuccessListener { documentReference ->

                                Toast.makeText(
                                    context,
                                    "Thank you, you have successfully subscribed to our app.",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }

                            .addOnFailureListener { e ->

                                Toast.makeText(
                                    context,
                                    "Sorry, an error has occurred. Please try again next time.",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }

                    }) {

                        Text(text = "Subscribe")

                    }
                }
            }
        }
    }


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FinalTheme {
        Greeting("Android")
    }
}