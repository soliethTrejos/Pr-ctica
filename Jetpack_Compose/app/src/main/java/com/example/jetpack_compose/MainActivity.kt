package com.example.jetpack_compose

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpack_compose.ui.theme.Jetpack_ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Jetpack_ComposeTheme {
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
    var tabSeleccionado by rememberSaveable { mutableIntStateOf(1) }
    var ocasionSeleccionada by rememberSaveable { mutableStateOf("Solo porque sí") }
    var mensajeGenerado by rememberSaveable { mutableStateOf("") }
    var imagenUri by remember { mutableStateOf<Uri?>(null) }

    val listaMensajes = remember { mutableStateListOf<String>() }

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

    val destinatarioPreview =
        if (nombrePara.isBlank()) "[Nombre de quien recibe]" else nombrePara

    val remitentePreview =
        if (nombreDe.isBlank()) "[Tu nombre]" else nombreDe

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
                        Image(
                            painter = painterResource(id = R.drawable.mensajes),
                            contentDescription = "Mensajes",
                            modifier = Modifier.size(22.dp),
                            contentScale = ContentScale.Fit
                        )
                    },
                    label = { Text("Mensajes", fontSize = 11.sp) }
                )

                NavigationBarItem(
                    selected = tabSeleccionado == 1,
                    onClick = { tabSeleccionado = 1 },
                    icon = {
                        Image(
                            painter = painterResource(id = R.drawable.galeria),
                            contentDescription = "Galería",
                            modifier = Modifier.size(22.dp),
                            contentScale = ContentScale.Fit
                        )
                    },
                    label = { Text("Galería", fontSize = 11.sp) }
                )

                NavigationBarItem(
                    selected = tabSeleccionado == 2,
                    onClick = { tabSeleccionado = 2 },
                    icon = {
                        Image(
                            painter = painterResource(id = R.drawable.ajustes),
                            contentDescription = "Ajustes",
                            modifier = Modifier.size(22.dp),
                            contentScale = ContentScale.Fit
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
                    Text(
                        text = "☰",
                        fontSize = 18.sp,
                        color = rosaOscuro
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "Mensaje Personalizado",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = rosaOscuro,
                        modifier = Modifier.weight(1f)
                    )

                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFFE0EC)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.usuario),
                            contentDescription = "Usuario",
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
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
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Personaliza tu mensaje con nombres e imágenes para una conexión más profunda.",
                            color = grisTexto,
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "De:",
                    color = grisTexto,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(6.dp))

                TextField(
                    value = nombreDe,
                    onValueChange = { nombreDe = it },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Tu nombre",
                            color = Color(0xFFC79CB1),
                            fontSize = 13.sp
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

                Text(
                    text = "Para:",
                    color = grisTexto,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(6.dp))

                TextField(
                    value = nombrePara,
                    onValueChange = { nombrePara = it },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Nombre de quien recibe",
                            color = Color(0xFFC79CB1),
                            fontSize = 13.sp
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
                    text = "Agregar foto",
                    color = grisTexto,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(100.dp)
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
                                Image(
                                    painter = painterResource(id = R.drawable.agregar_foto),
                                    contentDescription = "Agregar foto",
                                    modifier = Modifier.size(62.dp),
                                    contentScale = ContentScale.Fit
                                )
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

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(100.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1F1624))
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Image(
                                painter = painterResource(id = R.drawable.romantico),
                                contentDescription = "Romántico",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                            Text(
                                text = "Romántico",
                                color = rosaOscuro,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(8.dp)
                                    .background(
                                        Color(0xFFFBC1D8),
                                        RoundedCornerShape(10.dp)
                                    )
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
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
                            mensajeGenerado = if (nombreDe.isBlank() || nombrePara.isBlank()) {
                                "Por favor, escribe el nombre de quien envía y de quien recibe."
                            } else {
                                when (ocasionSeleccionada) {
                                    "Cumpleaños" ->
                                        "Hola $nombrePara,\nque tengas un cumpleaños muy bonito.\nAtentamente, $nombreDe"

                                    "Aniversario" ->
                                        "Hola $nombrePara,\nfeliz aniversario, deseo que pases un día especial.\nAtentamente, $nombreDe"

                                    else ->
                                        "Hola $nombrePara,\nespero que tengas un lindo día.\nAtentamente, $nombreDe"
                                }
                            }

                            if (nombreDe.isNotBlank() && nombrePara.isNotBlank()) {
                                listaMensajes.add(mensajeGenerado)
                            }
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = rosaPrincipal),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Text(
                            text = "Generar",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = {
                            nombreDe = ""
                            nombrePara = ""
                            mensajeGenerado = ""
                            imagenUri = null
                            ocasionSeleccionada = "Solo porque sí"
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC98AA5)),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Text(
                            text = "Limpiar campos",
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        listaMensajes.clear()
                        mensajeGenerado = ""
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB96C8C)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                ) {
                    Text(
                        text = "Limpiar todo",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Último mensaje generado:",
                            color = rosaOscuro,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        if (bitmapSeleccionado != null) {
                            Image(
                                bitmap = bitmapSeleccionado.asImageBitmap(),
                                contentDescription = "Foto elegida",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                                    .clip(RoundedCornerShape(14.dp)),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                        }

                        Text(
                            text = if (mensajeGenerado.isBlank()) "Aquí aparecerá el mensaje final." else mensajeGenerado,
                            color = Color.DarkGray,
                            fontSize = 13.sp,
                            lineHeight = 19.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF7FA))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Lista de mensajes guardados:",
                            color = rosaOscuro,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        if (listaMensajes.isEmpty()) {
                            Text(
                                text = "No has agregado mensajes todavía.",
                                color = Color.Gray,
                                fontSize = 13.sp
                            )
                        } else {
                            listaMensajes.forEachIndexed { index, mensaje ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 8.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text(
                                            text = "Mensaje ${index + 1}",
                                            color = rosaPrincipal,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold
                                        )

                                        Spacer(modifier = Modifier.height(6.dp))

                                        Text(
                                            text = mensaje,
                                            color = Color.DarkGray,
                                            fontSize = 13.sp,
                                            lineHeight = 18.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewPantallaMensajePersonalizado() {
    Jetpack_ComposeTheme {
        PantallaMensajePersonalizado()
    }
}