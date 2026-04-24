package com.example.mensaje

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mensaje.ui.theme.MensajeTheme

data class MensajeGuardado(
    val texto: String,
    val imagen: Bitmap?
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MensajeTheme {
                PantallaMensajePersonalizado()
            }
        }
    }
}

@Composable
fun PantallaMensajePersonalizado() {
    val context = LocalContext.current

    var nombreDe by rememberSaveable { mutableStateOf("") }
    var nombrePara by rememberSaveable { mutableStateOf("") }
    var ocasionSeleccionada by rememberSaveable { mutableStateOf("Solo porque sí") }
    var tabSeleccionado by rememberSaveable { mutableIntStateOf(1) }
    var imagenUri by remember { mutableStateOf<Uri?>(null) }

    val listaMensajes = remember { mutableStateListOf<MensajeGuardado>() }

    val launcherGaleria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imagenUri = uri
    }

    val bitmapSeleccionado = remember(imagenUri) {
        imagenUri?.let { loadBitmapFromUri(context, it) }
    }

    val fondo = Color(0xFFFFF8FB)
    val rosaPrincipal = Color(0xFFF48BB8)
    val rosaOscuro = Color(0xFFD95F98)
    val rosaSuave = Color(0xFFFFEEF5)
    val grisTexto = Color(0xFF7D6873)

    val destinatarioPreview = if (nombrePara.isBlank()) "[Nombre de quien recibe]" else nombrePara
    val remitentePreview = if (nombreDe.isBlank()) "[Tu nombre]" else nombreDe

    val mensajePreview = when (ocasionSeleccionada) {
        "Cumpleaños" -> "Hola $destinatarioPreview,\nque tengas un cumpleaños muy bonito.\nAtentamente, $remitentePreview"
        "Aniversario" -> "Hola $destinatarioPreview,\nfeliz aniversario, deseo que pases un día especial.\nAtentamente, $remitentePreview"
        else -> "Hola $destinatarioPreview,\nespero que tengas un lindo día.\nAtentamente, $remitentePreview"
    }

    Scaffold(
        containerColor = fondo,
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(
                    selected = tabSeleccionado == 0,
                    onClick = { tabSeleccionado = 0 },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Chat,
                            contentDescription = "Mensajes",
                            tint = rosaOscuro
                        )
                    },
                    label = { Text("Mensajes", fontSize = 11.sp) }
                )

                NavigationBarItem(
                    selected = tabSeleccionado == 1,
                    onClick = { tabSeleccionado = 1 },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Image,
                            contentDescription = "Galería",
                            tint = rosaOscuro
                        )
                    },
                    label = { Text("Galería", fontSize = 11.sp) }
                )

                NavigationBarItem(
                    selected = tabSeleccionado == 2,
                    onClick = { tabSeleccionado = 2 },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Ajustes",
                            tint = rosaOscuro
                        )
                    },
                    label = { Text("Ajustes", fontSize = 11.sp) }
                )
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .navigationBarsPadding(),
            color = fondo
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menú",
                        tint = rosaOscuro,
                        modifier = Modifier.size(26.dp)
                    )

                    Text(
                        text = "  Mensaje Personalizado",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = rosaOscuro,
                        modifier = Modifier.weight(1f)
                    )

                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFFE0EC)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "Perfil",
                            tint = rosaOscuro,
                            modifier = Modifier.size(34.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = rosaSuave)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Crea algo especial",
                            color = rosaOscuro,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Personaliza mensajes con nombres, ocasiones e imágenes desde la galería.",
                            color = grisTexto,
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("De:", color = grisTexto, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)

                Spacer(modifier = Modifier.height(6.dp))

                TextField(
                    value = nombreDe,
                    onValueChange = { nombreDe = it },
                    singleLine = true,
                    placeholder = {
                        Text("Tu nombre", color = Color(0xFFC79CB1), fontSize = 13.sp)
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Remitente",
                            tint = rosaOscuro
                        )
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = rosaSuave,
                        unfocusedContainerColor = rosaSuave,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = rosaOscuro
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text("Para:", color = grisTexto, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)

                Spacer(modifier = Modifier.height(6.dp))

                TextField(
                    value = nombrePara,
                    onValueChange = { nombrePara = it },
                    singleLine = true,
                    placeholder = {
                        Text("Nombre de quien recibe", color = Color(0xFFC79CB1), fontSize = 13.sp)
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Destinatario",
                            tint = rosaOscuro
                        )
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = rosaSuave,
                        unfocusedContainerColor = rosaSuave,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = rosaOscuro
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Agregar imagen",
                    color = grisTexto,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .clickable { launcherGaleria.launch("image/*") },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEFF4)),
                    border = BorderStroke(1.dp, Color(0xFFFFD6E5))
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (bitmapSeleccionado == null) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.Filled.AddPhotoAlternate,
                                    contentDescription = "Agregar imagen",
                                    tint = rosaOscuro,
                                    modifier = Modifier.size(46.dp)
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = "Toca aquí para elegir una imagen",
                                    color = rosaOscuro,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        } else {
                            Image(
                                bitmap = bitmapSeleccionado.asImageBitmap(),
                                contentDescription = "Imagen seleccionada",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Vista previa del mensaje",
                    color = grisTexto,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF0F5))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "\"$mensajePreview\"",
                            color = Color(0xFF9A6A80),
                            fontSize = 13.sp,
                            fontStyle = FontStyle.Italic,
                            lineHeight = 19.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "✦ BORRADOR EN TIEMPO REAL",
                            color = rosaPrincipal,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ChipOcasion(
                        texto = "Cumpleaños",
                        seleccionado = ocasionSeleccionada == "Cumpleaños",
                        onClick = { ocasionSeleccionada = "Cumpleaños" },
                        modifier = Modifier.weight(1f)
                    )

                    ChipOcasion(
                        texto = "Aniversario",
                        seleccionado = ocasionSeleccionada == "Aniversario",
                        onClick = { ocasionSeleccionada = "Aniversario" },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                ChipOcasion(
                    texto = "Solo porque sí",
                    seleccionado = ocasionSeleccionada == "Solo porque sí",
                    onClick = { ocasionSeleccionada = "Solo porque sí" },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = {
                            if (nombreDe.isNotBlank() && nombrePara.isNotBlank()) {
                                listaMensajes.add(
                                    MensajeGuardado(
                                        texto = mensajePreview,
                                        imagen = bitmapSeleccionado
                                    )
                                )
                            }
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = rosaPrincipal),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Text("Guardar", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = {
                            nombreDe = ""
                            nombrePara = ""
                            imagenUri = null
                            ocasionSeleccionada = "Solo porque sí"
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC98AA5)),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Text("Limpiar", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = { listaMensajes.clear() },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB96C8C)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                ) {
                    Text("Limpiar lista", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Mensajes guardados:",
                    color = rosaOscuro,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                if (listaMensajes.isEmpty()) {
                    Text("Aún no has guardado mensajes.", color = Color.Gray, fontSize = 13.sp)
                } else {
                    listaMensajes.forEachIndexed { index, item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "Mensaje ${index + 1}",
                                    color = rosaPrincipal,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                item.imagen?.let { bitmap ->
                                    Image(
                                        bitmap = bitmap.asImageBitmap(),
                                        contentDescription = "Imagen guardada",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(150.dp)
                                            .clip(RoundedCornerShape(12.dp)),
                                        contentScale = ContentScale.Crop
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))
                                }

                                Text(
                                    text = item.texto,
                                    color = Color.DarkGray,
                                    fontSize = 13.sp,
                                    lineHeight = 19.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun ChipOcasion(
    texto: String,
    seleccionado: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colorBorde = if (seleccionado) Color(0xFFF48BB8) else Color(0xFFFFD6E5)
    val colorFondo = if (seleccionado) Color(0xFFFFE6F0) else Color.White
    val colorTexto = if (seleccionado) Color(0xFFD95F98) else Color(0xFFCD88A7)

    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(38.dp),
        shape = RoundedCornerShape(50),
        border = BorderStroke(1.dp, colorBorde),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = colorFondo)
    ) {
        Text(
            text = texto,
            color = colorTexto,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Suppress("DEPRECATION")
fun loadBitmapFromUri(context: Context, uri: Uri): Bitmap? {
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }
    } catch (_: Exception) {
        null
    }
}