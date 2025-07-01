package dev.nutrilivre.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// Nova paleta de cores
val PrimaryGreen = Color(0xFF22C55E) // Verde principal
val SecondaryBlue = Color(0xFF3B82F6) // Azul secundário
val AccentOrange = Color(0xFFF97316) // Laranja de destaque
val BackgroundLight = Color(0xFFF9FAFB) // Cinza claro de fundo
val TextDark = Color(0xFF1F2937) // Cinza escuro para texto
val White = Color(0xFFFFFFFF)

val LightColors = lightColorScheme(
    primary   = PrimaryGreen,
    secondary = SecondaryBlue,
    background= BackgroundLight,
    surface   = White,
    onPrimary = White,
    onSecondary = White,
    onBackground = TextDark,
    onSurface = TextDark,
    /* AccentOrange pode ser usado em componentes específicos */
)

val DarkColors = darkColorScheme(
    primary   = PrimaryGreen,
    secondary = SecondaryBlue,
    background= TextDark,
    surface   = TextDark,
    onPrimary = White,
    onSecondary = White,
    onBackground = BackgroundLight,
    onSurface = BackgroundLight,
    /* AccentOrange pode ser usado em componentes específicos */
)
