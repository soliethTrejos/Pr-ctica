package com.example.tarjeta_presentacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.ContactMail
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFFFE4F1)
                ) {
                    PerfilRosadoGrande()
                }
            }
        }
    }
}

@Composable
fun PerfilRosadoGrande() {
    var mostrarContacto by remember { mutableStateOf(false) }

    val fondo = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFFD6EA),
            Color(0xFFFFB8D8),
            Color(0xFFFFEEF7)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
            .padding(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(42.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFF7FB)
            ),
            elevation = CardDefaults.cardElevation(10.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeaderPerfilGrande()

                Spacer(modifier = Modifier.height(24.dp))

                SeguidoresCard()

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    InfoCardGrande(
                        modifier = Modifier.weight(1f),
                        titulo = "+10",
                        texto = "Proyectos",
                        icono = Icons.Default.Work
                    )

                    InfoCardGrande(
                        modifier = Modifier.weight(1f),
                        titulo = "+5",
                        texto = "Años de experiencia",
                        icono = Icons.Default.Star
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ImagenCard(
                        modifier = Modifier.weight(1f),
                        imagen = R.drawable.img1
                    )

                    ImagenCard(
                        modifier = Modifier.weight(1f),
                        imagen = R.drawable.img2
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ImagenCard(
                        modifier = Modifier.weight(1f),
                        imagen = R.drawable.img3
                    )

                    ImagenCard(
                        modifier = Modifier.weight(1f),
                        imagen = R.drawable.img4
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                Button(
                    onClick = { mostrarContacto = !mostrarContacto },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    shape = RoundedCornerShape(22.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE7549A)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ContactMail,
                        contentDescription = "Contacto",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = "Info de contacto",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                if (mostrarContacto) {
                    Spacer(modifier = Modifier.height(18.dp))
                    ContactoCard()
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Composable
fun HeaderPerfilGrande() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.perfil),
            contentDescription = "Imagen de perfil",
            modifier = Modifier
                .size(140.dp)
                .clip(CircleShape)
                .border(
                    width = 5.dp,
                    color = Color(0xFFE7549A),
                    shape = CircleShape
                ),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(20.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Alejandra ",
                color = Color(0xFF7A244E),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = "Altamirano",
                color = Color(0xFFC04B82),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Diseñadora de interfaces y desarrolladora móvil",
                color = Color(0xFF9A3E6A),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun SeguidoresCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(92.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFD7EA)
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            DatoSocial("1.2K", "Seguidores")
            DatoSocial("530", "Siguiendo")
            DatoSocial("48", "Publicaciones")
        }
    }
}

@Composable
fun DatoSocial(
    numero: String,
    texto: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = numero,
            color = Color(0xFF7A244E),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraBold
        )

        Text(
            text = texto,
            color = Color(0xFF9A5B78),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun InfoCardGrande(
    modifier: Modifier = Modifier,
    titulo: String,
    texto: String,
    icono: ImageVector
) {
    Card(
        modifier = modifier.height(145.dp),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFD7EA)
        ),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icono,
                contentDescription = texto,
                tint = Color(0xFFE7549A),
                modifier = Modifier.size(34.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = titulo,
                color = Color(0xFF7A244E),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = texto,
                color = Color(0xFF9A5B78),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun ImagenCard(
    modifier: Modifier = Modifier,
    imagen: Int
) {
    Card(
        modifier = modifier.height(150.dp),
        shape = RoundedCornerShape(26.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Image(
            painter = painterResource(id = imagen),
            contentDescription = "Imagen del perfil",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ContactoCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFD7EA)
        ),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = "Información de contacto",
                color = Color(0xFF7A244E),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Teléfono",
                    tint = Color(0xFFE7549A)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "+505 8888 8888",
                    color = Color(0xFF7A244E),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AlternateEmail,
                    contentDescription = "Correo",
                    tint = Color(0xFFE7549A)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "AlejandraAlt34@gmail.com",
                    color = Color(0xFF7A244E),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Work,
                    contentDescription = "Trabajo",
                    tint = Color(0xFFE7549A)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "Diseñadora UX / Desarrolladora móvil",
                    color = Color(0xFF7A244E),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewPerfilRosadoGrande() {
    MaterialTheme {
        PerfilRosadoGrande()
    }
}