package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass


@Composable
fun ProfileView(classes: WindowSizeClass, onClick: () -> Unit) {
    // Récupérer la taille de la fenêtre pour déterminer la disposition de l'écran
    val windowHeightClass = classes.windowHeightSizeClass

    // Déterminer si la disposition doit être compacte ou régulière en fonction de la taille de la fenêtre
    when (windowHeightClass) {
        WindowHeightSizeClass.COMPACT -> {
            CompactProfileLayout(onClick) // Utiliser la disposition compacte pour les petits écrans
        }
        else -> {
            RegularProfileLayout(onClick) // Utiliser la disposition régulière pour les écrans plus grands
        }
    }
}

@Composable
fun RegularProfileLayout(onClick: () -> Unit) {
    // Disposition régulière pour les écrans plus grands
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        ProfileImage() // Afficher l'image de profil
        Spacer(Modifier.height(10.dp)) // Espacement après l'image
        UserInfo() // Afficher les informations de l'utilisateur
        Spacer(Modifier.height(20.dp)) // Espacement
        ContactInfo() // Afficher les informations de contact
        Spacer(Modifier.height(20.dp)) // Espacement
        StartButton(onClick = onClick) // Afficher le bouton "Démarrer"
    }
}

@Composable
fun CompactProfileLayout(onClick: () -> Unit) {
    // Disposition compacte pour les petits écrans
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            // Colonne gauche avec l'image de profil et les informations de l'utilisateur
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ProfileImage()
                Spacer(Modifier.height(10.dp))
                UserInfo()
            }
            Spacer(Modifier.width(100.dp)) // Espacement entre les deux colonnes
            // Colonne droite avec les informations de contact et le bouton
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ContactInfo()
                Spacer(Modifier.height(20.dp))
                StartButton(onClick = onClick)
            }
        }
    }
}

@Composable
fun ProfileImage() {
    // Afficher l'image de profil avec une forme rectangulaire
    Image(
        painter = painterResource(R.drawable.sans_titre),
        contentDescription = "Profile Image",
        modifier = Modifier.clip(RectangleShape) // Appliquer une découpe rectangulaire à l'image
    )
}

@Composable
fun UserInfo() {
    // Afficher les informations utilisateur (nom, profession et école)
    Text(
        text = "Paul GARDIEN",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold
    )
    Text(
        text = "Data Engineer",
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(Modifier.height(20.dp)) // Espacement entre les informations
    Text(
        text = "Ecole d'ingénieur ISIS",
        fontStyle = FontStyle.Italic // Appliquer une police italique pour l'école
    )
}

@Composable
fun ContactInfo() {
    // Afficher les informations de contact (email et LinkedIn)
    Row {
        Image(
            painter = painterResource(R.drawable.mail),
            contentDescription = "Email",
            modifier = Modifier
                .size(30.dp)
                .padding(end = 8.dp) // Espacement à droite de l'icône email
        )
        Text(text = "paul.gardien63@gmail.com") // Afficher l'email
    }
    Spacer(Modifier.height(10.dp)) // Espacement entre les informations
    Row {
        Image(
            painter = painterResource(R.drawable.li),
            contentDescription = "LinkedIn",
            modifier = Modifier
                .size(30.dp)
                .padding(end = 8.dp) // Espacement à droite de l'icône LinkedIn
        )
        Text(text = "www.linkedin.com") // Afficher le lien LinkedIn
    }
}

@Composable
fun StartButton(onClick: () -> Unit) {
    // Afficher un bouton "Démarrer" qui exécute l'action fournie lors du clic
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors() // Utiliser les couleurs par défaut du bouton
    ) {
        Text("Démarrer") // Texte du bouton
    }
}
