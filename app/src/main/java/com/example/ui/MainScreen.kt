package com.example.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.*
import androidx.compose.ui.text.TextStyle
import com.example.ui.theme.*

// Mock data structure for Bolivian Dance info
data class DanceFolkInfo(
    val id: Int,
    val name: String,
    val level: String,
    val region: String,
    val description: String,
    val keySteps: List<String>,
    val difficultyColor: Color,
    val primaryColorHex: String,
    val customTriviaFact: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    val systemTab = viewModel.currentTab
    
    // Core states collected reactively from Room DB
    val costumeDesigns by viewModel.costumeDesigns.collectAsStateWithLifecycle()
    val eventReminders by viewModel.eventReminders.collectAsStateWithLifecycle()
    val practiceLogs by viewModel.practiceLogs.collectAsStateWithLifecycle()
    val communityPosts by viewModel.communityPosts.collectAsStateWithLifecycle()
    val commentsList by viewModel.postComments.collectAsStateWithLifecycle()

    // Traditional Bolivian dance static list
    val dancesInfo = remember {
        listOf(
            DanceFolkInfo(
                id = 0,
                name = "Caporales",
                level = "Básico / Intermedio",
                region = "La Paz (Surgimiento urbano)",
                description = "Fusión rítmica inspirada en el capataz mayor (Caporal). Destaca por sus saltos atléticos de infarto, sacudidas de hombros, espuelas ruidosas y enérgico charango de acompañamiento.",
                keySteps = listOf("Paso básico cruzado", "Salto de compás alto", "Giro de bota y sombrero", "Ataque del capataz"),
                difficultyColor = FolkRed,
                primaryColorHex = "#E53935",
                customTriviaFact = "Los cascabeles de bota hacen referencia poética al choque de cadenas coloniales de la población afroboliviana."
            ),
            DanceFolkInfo(
                id = 1,
                name = "Morenada",
                level = "Básico",
                region = "Oruro y La Paz (Lago Titicaca)",
                description = "Marcha rítmica, pesada e inolvidable. Representa el agotamiento físico de los pisadores coloniales en condiciones duras. Se baila con matracas talladas y máscaras ornamentadas.",
                keySteps = listOf("Paso pesado marcante", "Giro pausado de hombro", "Matraqueo constante", "Despliegue lateral"),
                difficultyColor = FolkYellow,
                primaryColorHex = "#FFB300",
                customTriviaFact = "Los pollerines de moreno pueden pesar hasta 15 kilos debido a sus miles de hilos de plata fina de potosí."
            ),
            DanceFolkInfo(
                id = 2,
                name = "Tinku",
                level = "Avanzado",
                region = "Norte de Potosí",
                description = "Encuentro y combate alegre ritual de fuerza. Es un baile vigoroso, cargado de vitalidad telúrica, patadas rítmicas al suelo y plumas altas de paraba multicolores.",
                keySteps = listOf("Zapateo de potencia", "Inclinación de guerra", "Finta de ataque puño", "Vuelta en círculo bajo"),
                difficultyColor = FolkTinkuPurple,
                primaryColorHex = "#673AB7",
                customTriviaFact = "Se realiza como ofrenda a la Pachamama (Madre Tierra) para pedir cosechas prósperas."
            ),
            DanceFolkInfo(
                id = 3,
                name = "Diablada",
                level = "Avanzado",
                region = "Oruro (Capital del Folklore)",
                description = "El espectacular enfrentamiento teatralizado entre el Arcángel San Miguel y las legiones del Tío del Inframundo. Sobresalen máscaras con dragones y espectaculares pecheras.",
                keySteps = listOf("Paso saltado celestial", "Cruces de diablo", "Salto del arcángel", "Sway del demonio"),
                difficultyColor = FolkRed,
                primaryColorHex = "#E53935",
                customTriviaFact = "Nació como un sincretismo religioso dedicado a la Virgen del Socavón dentro de las minas."
            ),
            DanceFolkInfo(
                id = 4,
                name = "Saya",
                level = "Básico",
                region = "Los Yungas (La Paz)",
                description = "Danza genuina de origen afroboliviano guiada por el bombo mayor (Caja). Sus cantos responsoriales relatan la historia de superación y su identidad viva.",
                keySteps = listOf("Sway de cadera saya", "Doble paso balanceado", "Canto responsorial guiado", "Ronda de polleras"),
                difficultyColor = FolkGreen,
                primaryColorHex = "#43A047",
                customTriviaFact = "El tambor mayor ('caja') y la guancha dictan el ritmo de avance de toda la tropa."
            ),
            DanceFolkInfo(
                id = 5,
                name = "Tobas",
                level = "Avanzado",
                region = "Gran Chaco / Oruro",
                description = "Homenaje gimnástico a los indómitos guerreros amazónicos. Se caracteriza por pasos extremadamente acrobáticos con lanzas talladas de chonta y penachos de plumas altísimas.",
                keySteps = listOf("Salto acrobático doble", "Paso de avance agachado", "Brinco de flecha veloz", "Salto de defensa"),
                difficultyColor = FolkPinkAccent,
                primaryColorHex = "#E91E63",
                customTriviaFact = "Representa la admiración incaica por la altivez de las etnias del pie de monte."
            ),
            DanceFolkInfo(
                id = 6,
                name = "Llamerada",
                level = "Intermedio",
                region = "Altiplano Central",
                description = "Mímica del pastoreo y arreo de rebaños de auquénidos andinos. Se danza portando hermosas hondas tejidas multicolor y silbando al ritmo de las zampoñas.",
                keySteps = listOf("Paso trotado rítmico", "Giro de la honda de lana", "Simulación de arreo", "Cruce de pastores"),
                difficultyColor = Color(0xFF03A9F4),
                primaryColorHex = "#03A9F4",
                customTriviaFact = "Los sombreros en forma de cono imitan las coronas que usaban los nobles caciques aymaras."
            ),
            DanceFolkInfo(
                id = 7,
                name = "Cueca",
                level = "Intermedio",
                region = "Todo el territorio boliviano",
                description = "Elegante pieza de salón, poesía en movimiento y flirteo con pañuelos blancos de algodón. Varía su ritmo y picardía según el departamento (Paceña, Cochala, Tarijeña).",
                keySteps = listOf("Invitación inicial", "El paseo elegante", "La quimba íntima", "Zapateo final"),
                difficultyColor = FolkGoldAccent,
                primaryColorHex = "#FFC107",
                customTriviaFact = "Termina con un zapateo rápido que simula la aceptación del galanteo amoroso."
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.MusicNote,
                                contentDescription = "Logo Folk",
                                tint = MaterialTheme.colorScheme.background,
                                modifier = Modifier.padding(6.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "FolklorDance",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                ),
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Text(
                                text = "Bolivia Cultural",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    }
                },
                actions = {
                    // Global Dark Theme Switch toggle button in the header actions!
                    IconButton(
                        onClick = { viewModel.toggleDarkLightMode() },
                        modifier = Modifier.testTag("mode_toggle_header")
                    ) {
                        Icon(
                            imageVector = if (viewModel.isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Cambiar modo de pantalla",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                    
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.EmojiEvents,
                            contentDescription = "Puntos",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${viewModel.userPoints} pts",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                modifier = Modifier.navigationBarsPadding(),
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = systemTab == FolkTab.INICIO,
                    onClick = { viewModel.currentTab = FolkTab.INICIO },
                    label = { Text("Inicio", style = MaterialTheme.typography.labelSmall) },
                    icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Inicio") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.surfaceVariant,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray
                    ),
                    modifier = Modifier.testTag("nav_inicio")
                )
                NavigationBarItem(
                    selected = systemTab == FolkTab.TUTORIALES,
                    onClick = { viewModel.currentTab = FolkTab.TUTORIALES },
                    label = { Text("Tutoriales", style = MaterialTheme.typography.labelSmall) },
                    icon = { Icon(imageVector = Icons.Default.PlayCircle, contentDescription = "Tutoriales") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.secondary,
                        selectedTextColor = MaterialTheme.colorScheme.secondary,
                        indicatorColor = MaterialTheme.colorScheme.surfaceVariant,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray
                    ),
                    modifier = Modifier.testTag("nav_tutoriales")
                )
                NavigationBarItem(
                    selected = systemTab == FolkTab.RETOS,
                    onClick = { viewModel.currentTab = FolkTab.RETOS },
                    label = { Text("Retos", style = MaterialTheme.typography.labelSmall) },
                    icon = { Icon(imageVector = Icons.Default.EmojiEvents, contentDescription = "Retos") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.tertiary,
                        selectedTextColor = MaterialTheme.colorScheme.tertiary,
                        indicatorColor = MaterialTheme.colorScheme.surfaceVariant,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray
                    ),
                    modifier = Modifier.testTag("nav_retos")
                )
                NavigationBarItem(
                    selected = systemTab == FolkTab.COMUNIDAD,
                    onClick = { viewModel.currentTab = FolkTab.COMUNIDAD },
                    label = { Text("Comunidad", style = MaterialTheme.typography.labelSmall) },
                    icon = { Icon(imageVector = Icons.Default.Forum, contentDescription = "Comunidad") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.surfaceVariant,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray
                    ),
                    modifier = Modifier.testTag("nav_comunidad")
                )
                NavigationBarItem(
                    selected = systemTab == FolkTab.PERFIL,
                    onClick = { viewModel.currentTab = FolkTab.PERFIL },
                    label = { Text("Perfil", style = MaterialTheme.typography.labelSmall) },
                    icon = { Icon(imageVector = Icons.Default.Person, contentDescription = "Perfil") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.secondary,
                        selectedTextColor = MaterialTheme.colorScheme.secondary,
                        indicatorColor = MaterialTheme.colorScheme.surfaceVariant,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray
                    ),
                    modifier = Modifier.testTag("nav_perfil")
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (systemTab) {
                FolkTab.INICIO -> TabInicio(viewModel, dancesInfo)
                FolkTab.TUTORIALES -> TabTutoriales(viewModel, dancesInfo)
                FolkTab.RETOS -> TabRetos(viewModel, practiceLogs)
                FolkTab.COMUNIDAD -> TabComunidad(viewModel, communityPosts, commentsList)
                FolkTab.PERFIL -> TabPerfil(viewModel, costumeDesigns, eventReminders)
            }

            // Overlay panel if practice timer is active
            if (viewModel.isPracticeTimerActive) {
                PracticeTimerOverlay(viewModel = viewModel)
            }
        }
    }
}

// -------------------------------------------------------------
// TAB 1: INICIO SCREEN
// -------------------------------------------------------------
@Composable
fun TabInicio(viewModel: MainViewModel, dances: List<DanceFolkInfo>) {
    val currentTheme = viewModel.activeAppTheme
    val primaryColor = Color(android.graphics.Color.parseColor(currentTheme.primaryHex))
    val secondaryColor = Color(android.graphics.Color.parseColor(currentTheme.secondaryHex))
    val tertiaryColor = Color(android.graphics.Color.parseColor(currentTheme.tertiaryHex))

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Welcome and Folk quote banner (High Visual Impact)
        item {
            Card(
                modifier = Modifier.fillMaxWidth().testTag("welcome_card"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .drawBehind {
                            // Artistic corner glow inspired by active theme color scheme
                            drawCircle(
                                brush = Brush.radialGradient(
                                    colors = listOf(primaryColor.copy(alpha = 0.25f), Color.Transparent),
                                    radius = 200.dp.toPx()
                                ),
                                center = Offset(size.width, size.height * 0.2f)
                            )
                            drawCircle(
                                brush = Brush.radialGradient(
                                    colors = listOf(tertiaryColor.copy(alpha = 0.25f), Color.Transparent),
                                    radius = 180.dp.toPx()
                                ),
                                center = Offset(0f, size.height * 0.8f)
                            )
                        }
                        .padding(20.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Selected Avatar Preview small
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .background(Color(android.graphics.Color.parseColor(viewModel.avatarOutfitColor)))
                                    .border(2.dp, secondaryColor, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = currentTheme.emoji,
                                    fontSize = 28.sp
                                )
                            }
                            Column {
                                Text(
                                    text = "¡Jallalla, Bailarín!",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.LocalFireDepartment,
                                        contentDescription = "Racha",
                                        tint = primaryColor,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "${viewModel.userStreak} días de racha de baile",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = secondaryColor
                                    )
                                }
                            }
                        }

                        Divider(color = Color.Gray.copy(alpha = 0.3f), thickness = 1.dp)

                        // Quote
                        Column {
                            Icon(
                                imageVector = Icons.Default.FormatQuote,
                                contentDescription = "Quote Icon",
                                tint = secondaryColor,
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "“Baila, aprende y vive la cultura de tu tierra”",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    fontStyle = FontStyle.Italic,
                                    lineHeight = 28.sp,
                                    fontFamily = FontFamily.Serif
                                ),
                                color = secondaryColor,
                                modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 4.dp)
                            )
                            Text(
                                text = "- Orgullo de la Danza Boliviana",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }
            }
        }

        // Color theme switcher palette picker in TabInicio (que se pueda cambiar de colores)
        item {
            Card(
                modifier = Modifier.fillMaxWidth().testTag("theme_selector_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, secondaryColor.copy(alpha = 0.2f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = primaryColor.copy(alpha = 0.15f),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Palette,
                                contentDescription = "Paleta de colores",
                                tint = secondaryColor,
                                modifier = Modifier.padding(8.dp).size(20.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Colores de mi Tierra",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Presiona para vestir la app con tonos folclóricos",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Gray
                            )
                        }
                    }

                    // Horizontal / Wrapped list of beautiful theme selectors
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        FolkThemeColor.values().forEach { themeOption ->
                            val isSelected = currentTheme == themeOption
                            val optPrimary = Color(android.graphics.Color.parseColor(themeOption.primaryHex))
                            val optSecondary = Color(android.graphics.Color.parseColor(themeOption.secondaryHex))
                            val optTertiary = Color(android.graphics.Color.parseColor(themeOption.tertiaryHex))

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (isSelected) Color.White.copy(alpha = 0.08f) else Color.Transparent)
                                    .border(
                                        width = if (isSelected) 2.dp else 1.dp,
                                        color = if (isSelected) secondaryColor else Color.Gray.copy(alpha = 0.15f),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable { viewModel.changeAppTheme(themeOption) }
                                    .padding(6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = themeOption.emoji,
                                    fontSize = 18.sp
                                )

                                // Color preview circles
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(optPrimary))
                                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(optSecondary))
                                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(optTertiary))
                                }

                                Text(
                                    text = themeOption.displayName.replace("Estilo ", "").replace("Tinku ", ""),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 8.5.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = if (isSelected) secondaryColor else Color.Gray,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }

                    Divider(color = Color.Gray.copy(alpha = 0.15f), thickness = 1.dp)

                    // Theme Mode Switch (Modo Claro vs Modo Oscuro para Teatros / Ensayos)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White.copy(alpha = 0.04f))
                            .clickable { viewModel.toggleDarkLightMode() }
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = if (viewModel.isDarkMode) Icons.Default.NightsStay else Icons.Default.WbSunny,
                                contentDescription = "Modo de Luz",
                                tint = secondaryColor,
                                modifier = Modifier.size(22.dp)
                            )
                            Column {
                                Text(
                                    text = if (viewModel.isDarkMode) "🌙 Modo Oscuro (Teatros)" else "☀️ Modo Claro (Brillante)",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                    color = if (viewModel.isDarkMode) Color.White else Color.Black
                                )
                                Text(
                                    text = if (viewModel.isDarkMode) "Evita destellos en camerinos o ensayos" else "Colores folclóricos de gran contraste",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Gray
                                )
                            }
                        }
                        Switch(
                            checked = viewModel.isDarkMode,
                            onCheckedChange = { viewModel.toggleDarkLightMode() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = secondaryColor,
                                checkedTrackColor = secondaryColor.copy(alpha = 0.3f),
                                uncheckedThumbColor = Color.Gray,
                                uncheckedTrackColor = Color.Gray.copy(alpha = 0.2f)
                            ),
                            modifier = Modifier.testTag("mode_toggle_switch")
                        )
                    }
                }
            }
        }

        // Daily Reward Card (Fun Interactive Element)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.15f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = if (viewModel.isDailyClaimed) Color.Gray else tertiaryColor,
                            modifier = Modifier.size(44.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Redeem,
                                contentDescription = "Premios",
                                tint = Color.White,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Práctica Diaria",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                            Text(
                                text = if (viewModel.isDailyClaimed) "Recompensa cobrada hoy" else "+50 puntos por bailar hoy",
                                style = MaterialTheme.typography.bodySmall,
                                color = if (viewModel.isDailyClaimed) Color.Gray else secondaryColor
                            )
                        }
                    }

                    Button(
                        onClick = { viewModel.claimDailyReward() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (viewModel.isDailyClaimed) Color.DarkGray else primaryColor,
                            contentColor = Color.White
                        ),
                        enabled = !viewModel.isDailyClaimed,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("claim_daily_button")
                    ) {
                        Text(
                            text = if (viewModel.isDailyClaimed) "Listo ✓" else "Cobrar",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Quick navigation shortcuts header
        item {
            Text(
                text = "Acceso Rápido Folclórico",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        // Quick links inside horizontal layout grid
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Tutoriales
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { viewModel.currentTab = FolkTab.TUTORIALES },
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.15f))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            modifier = Modifier.size(42.dp),
                            shape = CircleShape,
                            color = primaryColor.copy(alpha = 0.2f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.MenuBook,
                                contentDescription = "Tutoriales",
                                tint = primaryColor,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Tutoriales",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Text(
                            text = "${dances.size} Danzas",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }

                // Retos
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { viewModel.currentTab = FolkTab.RETOS },
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.15f))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            modifier = Modifier.size(42.dp),
                            shape = CircleShape,
                            color = secondaryColor.copy(alpha = 0.2f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Celebration,
                                contentDescription = "Retos",
                                tint = secondaryColor,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Retos",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Text(
                            text = "Top Rango",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }

                // Perfil
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { viewModel.currentTab = FolkTab.PERFIL },
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.15f))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            modifier = Modifier.size(42.dp),
                            shape = CircleShape,
                            color = tertiaryColor.copy(alpha = 0.2f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Palette,
                                contentDescription = "Diseño de trajes",
                                tint = tertiaryColor,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Mis Trajes",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Text(
                            text = "Diseñador",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        // Live cultural news or banner of the week
        item {
            Text(
                text = "Danza Destacada de Hoy",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.15f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "La Diablada de Oruro",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = primaryColor
                        )
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = primaryColor.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = "Patrimonio",
                                color = primaryColor,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Representación única del bien contra el mal, plasmada en espectaculares saltos de tropas completas de diablos liderados por el Arcángel San Miguel.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            viewModel.currentTab = FolkTab.TUTORIALES
                            viewModel.simulatedPlayingDanceIndex = 3 // Index for Diablada in our list
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = secondaryColor),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Bailar",
                            tint = Color.Black,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Ver Tutorial",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// TAB 2: TUTORIALS SCREEN
// -------------------------------------------------------------
@Composable
fun TabTutoriales(viewModel: MainViewModel, dances: List<DanceFolkInfo>) {
    val activeDanceIdx = viewModel.simulatedPlayingDanceIndex
    val activeDance = dances.getOrNull(activeDanceIdx) ?: dances[0]
    var selectedSubTab by remember { mutableStateOf(0) } // 0: Videotutoriales, 1: Diccionario, 2: Audios

    Column(modifier = Modifier.fillMaxSize()) {
        // High fidelity selector tabs for Video, Dictionary or Audios
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(FolkDarkSurface)
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (selectedSubTab == 0) FolkYellow else Color.Transparent)
                    .clickable { selectedSubTab = 0 }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🎥 Pasos",
                    fontWeight = FontWeight.Bold,
                    color = if (selectedSubTab == 0) FolkDarkBg else Color.LightGray,
                    fontSize = 11.sp
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (selectedSubTab == 1) FolkYellow else Color.Transparent)
                    .clickable { selectedSubTab = 1 }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "📖 Diccionario",
                    fontWeight = FontWeight.Bold,
                    color = if (selectedSubTab == 1) FolkDarkBg else Color.LightGray,
                    fontSize = 11.sp
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (selectedSubTab == 2) FolkYellow else Color.Transparent)
                    .clickable { selectedSubTab = 2 }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🎶 Ritmos",
                    fontWeight = FontWeight.Bold,
                    color = if (selectedSubTab == 2) FolkDarkBg else Color.LightGray,
                    fontSize = 11.sp
                )
            }
        }

        when (selectedSubTab) {
            1 -> {
                DanceStepsDictionaryView(viewModel = viewModel)
            }
            2 -> {
                TabAudioPlayerView(viewModel = viewModel)
            }
            else -> {
            // Video Simulator Container (Animated)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(Color.Black)
            ) {
                // Simulated artwork in video
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Top elements of header player
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = FolkRed,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "VÍDEO TUTORIAL PASO A PASO",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        Row {
                            IconButton(onClick = {}) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "Compartir",
                                    tint = Color.White
                                )
                            }
                            IconButton(onClick = {}) {
                                Icon(
                                    imageVector = Icons.Default.Subtitles,
                                    contentDescription = "Subtítulos",
                                    tint = Color.White
                                )
                            }
                        }
                    }

                    // Interactive Animated Figure to mimic dancing
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Left and right arrows to simulate dancer rotation or jump
                        val infiniteTransition = rememberInfiniteTransition()
                        val translationY by infiniteTransition.animateFloat(
                            initialValue = 0f,
                            targetValue = if (viewModel.isSimulatedVideoPlaying) -30f else 0f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(400, easing = LinearEasing),
                                repeatMode = RepeatMode.Reverse
                            )
                        )
                        
                        val rotationZ by infiniteTransition.animateFloat(
                            initialValue = -5f,
                            targetValue = if (viewModel.isSimulatedVideoPlaying) 5f else -5f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(250, easing = LinearEasing),
                                repeatMode = RepeatMode.Reverse
                            )
                        )

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .graphicsLayer {
                                    this.translationY = translationY
                                    this.rotationZ = rotationZ
                                }
                        ) {
                            Text(
                                text = "🕺",
                                fontSize = 48.sp
                            )
                            Text(
                                text = "¡Zapateo Vivo!",
                                style = MaterialTheme.typography.labelSmall,
                                color = FolkGoldAccent
                            )
                        }

                        if (viewModel.isSimulatedVideoPlaying) {
                            Column(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.White.copy(alpha = 0.15f))
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = "Reproduciendo ritmos...",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Rango: ${viewModel.selectedTutorialLevel}",
                                    color = FolkYellow,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }

                    // Bottom player sliders & controls
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "01:24",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.LightGray
                            )
                            Slider(
                                value = viewModel.simulatedPlaybackProgress,
                                onValueChange = { viewModel.simulatedPlaybackProgress = it },
                                modifier = Modifier.weight(1f),
                                colors = SliderDefaults.colors(
                                    activeTrackColor = FolkRed,
                                    inactiveTrackColor = Color.DarkGray,
                                    thumbColor = FolkRed
                                )
                            )
                            Text(
                                text = "03:45",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.LightGray
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = {
                                val nextI = if (viewModel.simulatedPlayingDanceIndex > 0) viewModel.simulatedPlayingDanceIndex - 1 else dances.size - 1
                                viewModel.simulatedPlayingDanceIndex = nextI
                            }) {
                                Icon(
                                    imageVector = Icons.Default.SkipPrevious,
                                    contentDescription = "Prev",
                                    tint = Color.White
                                )
                            }

                            IconButton(
                                onClick = { viewModel.isSimulatedVideoPlaying = !viewModel.isSimulatedVideoPlaying },
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(FolkYellow)
                            ) {
                                Icon(
                                    imageVector = if (viewModel.isSimulatedVideoPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = "Play/Pause",
                                    tint = FolkDarkBg
                                )
                            }

                            IconButton(onClick = {
                                val nextI = if (viewModel.simulatedPlayingDanceIndex < dances.size - 1) viewModel.simulatedPlayingDanceIndex + 1 else 0
                                viewModel.simulatedPlayingDanceIndex = nextI
                            }) {
                                Icon(
                                    imageVector = Icons.Default.SkipNext,
                                    contentDescription = "Next",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            }

            // Selected tutorial breakdown and actions
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Selected Dance core info block
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = FolkDarkSurface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = activeDance.name,
                                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                        color = FolkYellow
                                    )
                                    Text(
                                        text = activeDance.region,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = FolkGreen
                                    )
                                }

                                // Level tags selector
                                Row(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(FolkDarkSurfaceVariant)
                                ) {
                                    listOf("Básico", "Intermedio", "Avanzado").forEach { lvl ->
                                        val isSelected = viewModel.selectedTutorialLevel == lvl
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(if (isSelected) FolkRed else Color.Transparent)
                                                .clickable { viewModel.selectedTutorialLevel = lvl }
                                                .padding(horizontal = 8.dp, vertical = 6.dp)
                                        ) {
                                            Text(
                                                text = lvl,
                                                fontSize = 11.sp,
                                                color = if (isSelected) Color.White else Color.Gray,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = activeDance.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            // Step-by-step list
                            Text(
                                text = "Explicación Paso a Paso (Grado ${viewModel.selectedTutorialLevel})",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = FolkYellow
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            activeDance.keySteps.forEachIndexed { i, step ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clip(CircleShape)
                                            .background(FolkGreen),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "${i + 1}",
                                            color = Color.White,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Text(
                                        text = step,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.White
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = { viewModel.startPracticeTimer(activeDance.name) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .testTag("start_practice_${activeDance.name}"),
                                colors = ButtonDefaults.buttonColors(containerColor = FolkYellow),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Timer,
                                    contentDescription = "Entrenar",
                                    tint = FolkDarkBg
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "¡Iniciar ciclo de ensayo guiado con cronómetro!",
                                    color = FolkDarkBg,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }

                // Quick Category selector row with beautiful horizontal scroll
                item {
                    Text(
                        text = "Explorar Categorías y Ritmos",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                }

                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(dances) { d ->
                            val isSelected = activeDance.id == d.id
                            Card(
                                modifier = Modifier
                                    .width(130.dp)
                                    .height(100.dp)
                                    .clickable {
                                        viewModel.simulatedPlayingDanceIndex = d.id
                                        // Match current level if compatible
                                        viewModel.selectedTutorialLevel = if (d.level.contains("Básico")) "Básico" else "Intermedio"
                                    },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) d.difficultyColor else FolkDarkSurface
                                )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(10.dp),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Icon(
                                        imageVector = if (isSelected) Icons.Default.PlayCircleFilled else Icons.Default.MusicNote,
                                        contentDescription = d.name,
                                        tint = if (isSelected) Color.White else d.difficultyColor
                                    )
                                    Column {
                                        Text(
                                            text = d.name,
                                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                            color = Color.White,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = d.level,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = if (isSelected) Color.White.copy(alpha = 0.8f) else Color.Gray,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
}

@Composable
fun TabAudioPlayerView(viewModel: MainViewModel) {
    val currentTrackIdx = viewModel.currentPlayingTrackIndex
    val track = viewModel.audioTracks.getOrNull(currentTrackIdx) ?: viewModel.audioTracks[0]
    val isPlaying = viewModel.isAudioPlaying
    val progress = viewModel.audioProgress

    var searchQuery by remember { mutableStateOf("") }

    val filteredTracks = remember(searchQuery) {
        viewModel.audioTracks.filter {
            it.title.contains(searchQuery, ignoreCase = true) ||
            it.danceType.contains(searchQuery, ignoreCase = true) ||
            it.artist.contains(searchQuery, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Vinyl Player Widget (Premium Design)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            colors = CardDefaults.cardColors(containerColor = FolkDarkSurface),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Vinyl Disk Animation
                    val infiniteTransition = rememberInfiniteTransition()
                    val rotationAngle by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = 360f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(durationMillis = 6000, easing = LinearEasing),
                            repeatMode = RepeatMode.Restart
                        )
                    )

                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                            .background(Color.Black)
                            .graphicsLayer {
                                if (isPlaying) {
                                    this.rotationZ = rotationAngle
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        // Outer Grooves
                        Box(
                            modifier = Modifier
                                .size(82.dp)
                                .clip(CircleShape)
                                .border(1.dp, Color.DarkGray.copy(alpha = 0.5f), CircleShape)
                        )
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .border(1.dp, Color.DarkGray.copy(alpha = 0.5f), CircleShape)
                        )
                        // Center label
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(FolkYellow),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "🎶",
                                fontSize = 14.sp
                            )
                        }
                    }

                    // Track information
                    Column(modifier = Modifier.weight(1f)) {
                        Surface(
                            color = FolkRed,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = track.danceType.uppercase(),
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = track.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = track.artist,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.LightGray,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = track.description,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Progress Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "0:00",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .weight(1f)
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = FolkYellow,
                        trackColor = Color.DarkGray
                    )
                    Text(
                        text = track.duration,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Control panel
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    IconButton(
                        onClick = { viewModel.prevAudioTrack() },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.SkipPrevious,
                            contentDescription = "Anterior",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    FloatingActionButton(
                        onClick = { viewModel.toggleAudioPlay() },
                        containerColor = FolkYellow,
                        contentColor = FolkDarkBg,
                        modifier = Modifier.size(48.dp),
                        shape = CircleShape
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = "Reproducir/Pausar",
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    IconButton(
                        onClick = { viewModel.nextAudioTrack() },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.SkipNext,
                            contentDescription = "Siguiente",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            placeholder = { Text("Buscar danza o canción...", color = Color.Gray) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar", tint = Color.Gray) },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Limpiar", tint = Color.Gray)
                    }
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = FolkDarkSurface,
                unfocusedContainerColor = FolkDarkSurface,
                disabledContainerColor = FolkDarkSurface,
                focusedIndicatorColor = FolkYellow,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Tracks List
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            itemsIndexed(filteredTracks) { idx, t ->
                val isSelected = viewModel.audioTracks.indexOf(t) == currentTrackIdx
                val isTrackPlaying = isSelected && isPlaying

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.selectAudioTrack(viewModel.audioTracks.indexOf(t)) },
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) FolkDarkSurfaceVariant else FolkDarkSurface
                    ),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (isSelected) FolkYellow.copy(alpha = 0.5f) else Color.Transparent
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Music icon or animation
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    if (isSelected) FolkYellow.copy(alpha = 0.2f) else Color.White.copy(
                                        alpha = 0.05f
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isTrackPlaying) {
                                // Live Equalizer Lines Simulation
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                                    verticalAlignment = Alignment.Bottom,
                                    modifier = Modifier.height(20.dp)
                                ) {
                                    val infiniteTransition = rememberInfiniteTransition()
                                    val height1 by infiniteTransition.animateFloat(
                                        initialValue = 4f, targetValue = 20f,
                                        animationSpec = infiniteRepeatable(tween(400), RepeatMode.Reverse)
                                    )
                                    val height2 by infiniteTransition.animateFloat(
                                        initialValue = 18f, targetValue = 6f,
                                        animationSpec = infiniteRepeatable(tween(300), RepeatMode.Reverse)
                                    )
                                    val height3 by infiniteTransition.animateFloat(
                                        initialValue = 8f, targetValue = 16f,
                                        animationSpec = infiniteRepeatable(tween(550), RepeatMode.Reverse)
                                    )
                                    Box(modifier = Modifier.width(3.dp).height(height1.dp).background(FolkYellow))
                                    Box(modifier = Modifier.width(3.dp).height(height2.dp).background(FolkYellow))
                                    Box(modifier = Modifier.width(3.dp).height(height3.dp).background(FolkYellow))
                                }
                            } else {
                                Icon(
                                    imageVector = Icons.Default.MusicNote,
                                    contentDescription = "Nota musical",
                                    tint = if (isSelected) FolkYellow else Color.Gray,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }

                        // Title, subtitle & details
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = t.title,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) FolkYellow else Color.White,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = t.danceType,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.LightGray
                                )
                                Text(
                                    text = "•",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Gray
                                )
                                Text(
                                    text = t.artist,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Gray,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                        // Duration text
                        Text(
                            text = t.duration,
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DanceStepsDictionaryView(viewModel: MainViewModel) {
    val favoriteStepIds by viewModel.favoriteStepIds.collectAsStateWithLifecycle()
    var searchQuery by remember { mutableStateOf("") }
    var selectedDanceFilter by remember { mutableStateOf("Todos") }
    var onlyShowFavorites by remember { mutableStateOf(false) }

    // List of unique dances in dictionary steps to build filter chips
    val danceCategories = remember {
        listOf("Todos") + viewModel.danceStepsDic.map { it.danceType }.distinct()
    }

    // Filtered list of steps
    val filteredSteps = remember(searchQuery, selectedDanceFilter, onlyShowFavorites, favoriteStepIds) {
        viewModel.danceStepsDic.filter { step ->
            val matchesSearch = step.name.contains(searchQuery, ignoreCase = true) || 
                                 step.description.contains(searchQuery, ignoreCase = true)
            val matchesFilter = selectedDanceFilter == "Todos" || step.danceType == selectedDanceFilter
            val matchesFavorites = !onlyShowFavorites || favoriteStepIds.contains(step.id)
            matchesSearch && matchesFilter && matchesFavorites
        }
    }

    // Get currently playing step
    val activeStep = remember(viewModel.activeDictionaryStepId) {
        viewModel.danceStepsDic.firstOrNull { it.id == viewModel.activeDictionaryStepId } ?: viewModel.danceStepsDic.first()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 0.dp)
    ) {
        // Selected step video player & interactive loop
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(Brush.verticalGradient(listOf(Color(0xFF1F1C18), FolkDarkBg)))
                .padding(12.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Header inside the player
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Surface(
                            color = FolkRed,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "DEMO DICCIONARIO",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Surface(
                            color = when(activeStep.difficulty) {
                                "Básico" -> FolkGreen
                                "Intermedio" -> FolkYellow
                                else -> FolkRed
                            },
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = activeStep.difficulty.uppercase(),
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (activeStep.difficulty == "Intermedio") Color.Black else Color.White,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                    
                    // Favorite heart toggle button in the previewer
                    val isFavorite = favoriteStepIds.contains(activeStep.id)
                    IconButton(
                        onClick = { viewModel.toggleFavoriteStep(activeStep.id) },
                        modifier = Modifier.size(36.dp).testTag("fav_button_${activeStep.id}")
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorito",
                            tint = if (isFavorite) FolkRed else Color.LightGray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                // Video player animated graphics representation
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val infiniteTransition = rememberInfiniteTransition()
                    
                    var playSpeedMultiplier by remember { mutableStateOf(1.0f) }
                    val loopDuration = (350 / playSpeedMultiplier).toInt()

                    val translationY by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = if (viewModel.dictionaryVideoIsPlaying) -25f else 0f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(loopDuration, easing = LinearEasing),
                            repeatMode = RepeatMode.Reverse
                        )
                    )

                    val rotationZ by infiniteTransition.animateFloat(
                        initialValue = -8f,
                        targetValue = if (viewModel.dictionaryVideoIsPlaying) 8f else -8f,
                        animationSpec = infiniteRepeatable(
                            animation = tween((loopDuration * 0.75f).toInt(), easing = LinearEasing),
                            repeatMode = RepeatMode.Reverse
                        )
                    )

                    // Left dancer
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .graphicsLayer {
                                if (viewModel.dictionaryVideoIsPlaying) {
                                    this.translationY = translationY
                                    this.rotationZ = rotationZ
                                }
                            }
                            .padding(end = 16.dp)
                    ) {
                        Text(
                            text = activeStep.animationEmoji,
                            fontSize = 44.sp
                        )
                        Text(
                            text = "Paso Activo",
                            style = MaterialTheme.typography.labelSmall,
                            color = FolkGoldAccent
                        )
                    }

                    // Simulated live playback notes / cues
                    Column(
                        modifier = Modifier
                            .width(180.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White.copy(alpha = 0.08f))
                            .padding(8.dp)
                    ) {
                        Text(
                            text = if (viewModel.dictionaryVideoIsPlaying) "▶ Demostración Activa" else "⏸ Demo Pausada",
                            color = if (viewModel.dictionaryVideoIsPlaying) FolkGreen else FolkYellow,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Baile: ${activeStep.danceType}",
                            color = Color.White,
                            fontSize = 10.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Ritmo: ${activeStep.rhythmCount}",
                            color = FolkGoldAccent,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Speed Selector & Play Pause bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Play Pause trigger button
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        IconButton(
                            onClick = { viewModel.dictionaryVideoIsPlaying = !viewModel.dictionaryVideoIsPlaying },
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(FolkYellow)
                                .testTag("dictionary_play_pause")
                        ) {
                            Icon(
                                imageVector = if (viewModel.dictionaryVideoIsPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = "Play/Pause",
                                tint = FolkDarkBg,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Text(
                            text = activeStep.name,
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.width(150.dp)
                        )
                    }

                    // Speed controls: "Lento 0.5x" and "Normal 1.0x"
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(FolkDarkSurfaceVariant)
                            .padding(2.dp)
                    ) {
                        listOf(0.5f, 1.0f).forEach { spd ->
                            val isSpdSel = (spd == 0.5f && viewModel.dictionaryVideoProgress == 0.5f) || (spd == 1.0f && viewModel.dictionaryVideoProgress == 1.0f)
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(if (isSpdSel) FolkRed else Color.Transparent)
                                    .clickable { viewModel.dictionaryVideoProgress = spd }
                                    .padding(horizontal = 6.dp, vertical = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (spd == 0.5f) "0.5x 🐢" else "1.0x ⚡",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSpdSel) Color.White else Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }

        // Search and Filter section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            colors = CardDefaults.cardColors(containerColor = FolkDarkSurface),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                // Search Input Field
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Buscar paso...", color = Color.Gray, fontSize = 13.sp) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar", tint = Color.Gray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("step_search_input"),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = FolkYellow,
                        unfocusedBorderColor = Color.DarkGray,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Favorite Checkbox and label in horizontal row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Filtrar por danza de origen:",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.LightGray
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (onlyShowFavorites) FolkRed.copy(alpha = 0.25f) else Color.Transparent)
                            .clickable { onlyShowFavorites = !onlyShowFavorites }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .testTag("toggle_favorites_only")
                    ) {
                        Icon(
                            imageVector = if (onlyShowFavorites) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favoritos",
                            tint = if (onlyShowFavorites) FolkRed else Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Ver Favoritos",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (onlyShowFavorites) FolkRed else Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Dance Category Filter Chips
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(danceCategories) { cat ->
                        val isSelected = selectedDanceFilter == cat
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) FolkYellow else FolkDarkSurfaceVariant,
                            modifier = Modifier.clickable { selectedDanceFilter = cat }.testTag("filter_chip_$cat")
                        ) {
                            Text(
                                text = cat,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) FolkDarkBg else Color.LightGray,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        // LazyColumn displaying Steps List and current detailed written guide
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Selected Step Detailed Written Guide Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = FolkDarkSurfaceVariant),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Guía Técnica: ${activeStep.name}",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = FolkYellow
                        )
                        
                        Spacer(modifier = Modifier.height(6.dp))
                        
                        Text(
                            text = activeStep.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                        
                        Spacer(modifier = Modifier.height(10.dp))
                        
                        Text(
                            text = "Instrucciones de Ejecución Coreográfica:",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                            color = FolkGreen
                        )
                        Text(
                            text = activeStep.movementInstruction,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.LightGray
                        )
                        
                        Spacer(modifier = Modifier.height(10.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Conteo Musical: ${activeStep.rhythmCount}",
                                fontSize = 11.sp,
                                color = FolkGoldAccent,
                                fontWeight = FontWeight.Bold
                            )
                            
                            Button(
                                onClick = { viewModel.startPracticeTimer(activeStep.name) },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = FolkGreen),
                                modifier = Modifier.height(32.dp).testTag("practice_step_${activeStep.id}")
                            ) {
                                Text("¡Ensayar Paso!", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }
                    }
                }
            }

            // Section Header
            item {
                Text(
                    text = "Listado de Pasos (${filteredSteps.size})",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White,
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                )
            }

            if (filteredSteps.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No se encontraron pasos correspondientes.",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            } else {
                items(filteredSteps) { step ->
                    val isSelected = step.id == activeStep.id
                    val isFavorite = favoriteStepIds.contains(step.id)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { 
                                viewModel.activeDictionaryStepId = step.id
                                viewModel.dictionaryVideoIsPlaying = true
                            }
                            .testTag("step_item_${step.id}"),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) FolkDarkSurfaceVariant else FolkDarkSurface
                        ),
                        shape = RoundedCornerShape(10.dp),
                        border = if (isSelected) BorderStroke(1.dp, FolkYellow) else null
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                // Animated emoji / icon indicator
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isSelected) FolkRed else FolkDarkSurfaceVariant),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = step.animationEmoji,
                                        fontSize = 20.sp
                                    )
                                }

                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        Text(
                                            text = step.name,
                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                            color = if (isSelected) FolkYellow else Color.White,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier.padding(top = 2.dp)
                                    ) {
                                        Surface(
                                            color = FolkGreen.copy(alpha = 0.2f),
                                            shape = RoundedCornerShape(4.dp)
                                        ) {
                                            Text(
                                                text = step.danceType,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = FolkGreen,
                                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                            )
                                        }
                                        Text(
                                            text = "Rigor: ${step.difficulty}",
                                            fontSize = 9.sp,
                                            color = Color.LightGray
                                        )
                                    }
                                }
                            }

                            // Favorite toggle heart in row list item
                            IconButton(
                                onClick = { viewModel.toggleFavoriteStep(step.id) },
                                modifier = Modifier
                                    .size(48.dp)
                                    .testTag("fav_toggle_${step.id}")
                            ) {
                                Icon(
                                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Añadir a favoritos",
                                    tint = if (isFavorite) FolkRed else Color.Gray,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// TAB 3: RETOS (CHALLENGES) & LEADERBOARDS
// -------------------------------------------------------------
@Composable
fun TabRetos(viewModel: MainViewModel, practiceLogs: List<PracticeLog>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Points summary and Badges Gallery Row
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = FolkDarkSurface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Sistema de Logros y Medallas Unlocked",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = FolkYellow
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        viewModel.badgesList.forEachIndexed { idx, badge ->
                            viewModel.userPoints
                            val isUnlocked = viewModel.userPoints >= (idx * 100)
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isUnlocked) FolkDarkSurfaceVariant else Color.Transparent)
                                    .padding(6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(if (isUnlocked) FolkGoldAccent else Color.DarkGray),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = when (idx) {
                                            0 -> Icons.Default.SelfImprovement
                                            1 -> Icons.Default.Palette
                                            2 -> Icons.Default.Info
                                            3 -> Icons.Default.DirectionsRun
                                            else -> Icons.Default.MilitaryTech
                                        },
                                        contentDescription = badge,
                                        tint = if (isUnlocked) FolkDarkBg else Color.Gray,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = badge.substringBefore(" "),
                                    fontSize = 9.sp,
                                    color = if (isUnlocked) Color.White else Color.Gray,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = if (isUnlocked) "Activo" else "${idx * 100} pt",
                                    fontSize = 8.sp,
                                    color = if (isUnlocked) FolkGreen else Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }

        // Active Challenges
        item {
            Text(
                text = "Retos Semanales de Baile",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        }

        // Challenge 1
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = FolkDarkSurface)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = FolkRed
                            ) {
                                Text(
                                    "ALTA INTENSIDAD",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Text(
                                text = "Reto Saya: Sabor Afro",
                                style = MaterialTheme.typography.bodySmall,
                                color = FolkYellow
                            )
                        }
                        Text(
                            text = "Practica saya de forma continua durante 1 sesión para liberar el golpe síncope de bombo.",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        Text(
                            text = "Premio: +40 pts y Medalla Explorador",
                            fontSize = 11.sp,
                            color = FolkGreen
                        )
                    }

                    Button(
                        onClick = { viewModel.startPracticeTimer("Saya") },
                        colors = ButtonDefaults.buttonColors(containerColor = FolkYellow),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.testTag("challenge_saya_start")
                    ) {
                        Text("Iniciar", color = FolkDarkBg, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    }
                }
            }
        }

        // Challenge 2
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = FolkDarkSurface)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = FolkTinkuPurple
                            ) {
                                Text(
                                    "ZAPATEO RITMO",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Text(
                                text = "Reto Tinku: Tierra Madre",
                                style = MaterialTheme.typography.bodySmall,
                                color = FolkYellow
                            )
                        }
                        Text(
                            text = "Consigue simular una sesión de Tinku nivel Avanzado para demostrar tus saltos rituales.",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        Text(
                            text = "Premio: +60 pts y Insignia Maestro del Stomp",
                            fontSize = 11.sp,
                            color = FolkGreen
                        )
                    }

                    Button(
                        onClick = { viewModel.startPracticeTimer("Tinku") },
                        colors = ButtonDefaults.buttonColors(containerColor = FolkYellow),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.testTag("challenge_tinku_start")
                    ) {
                        Text("Iniciar", color = FolkDarkBg, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    }
                }
            }
        }

        // Leaderboard title
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tabla de Clasificación Nacional",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = FolkGreen.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = "VIVO ACTIVO",
                        color = FolkGreen,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }

        // Simulated Leaderboard list
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = FolkDarkSurface)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    // Prepopulate 4 top dancers where the user is dynamically slotted in the 3rd or 4th position
                    val leadersList = listOf(
                        Triple("1. Jhocelyn Vargas (Oruro)", "1250 pts", "🏆 Rey Caporal"),
                        Triple("2. Carlos Mendoza (Potosí)", "980 pts", "⚡ Guerrero Tinku"),
                        Triple("Tú (Bailarín Estrella)", "${viewModel.userPoints} pts", "🏅 Danzante Activo"),
                        Triple("4. Valentina Roca (Santa Cruz)", "410 pts", "🌸 Flor Saya"),
                        Triple("5. Daniel Siles (Cochabamba)", "320 pts", "👞 Cueca Lover")
                    )

                    leadersList.forEachIndexed { i, leader ->
                        val isUser = leader.first.startsWith("Tú")
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isUser) FolkRed.copy(alpha = 0.15f) else Color.Transparent)
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(if (i == 0) FolkGoldAccent else if (isUser) FolkYellow else Color.DarkGray),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = if (i == 0) "🥇" else if (i == 1) "🥈" else "${i + 1}",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = FolkDarkBg
                                    )
                                }
                                Column {
                                    Text(
                                        text = leader.first,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                        color = if (isUser) FolkYellow else Color.White
                                    )
                                    Text(
                                        text = leader.third,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color.Gray
                                    )
                                }
                            }

                            Text(
                                text = leader.second,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                color = if (isUser) FolkYellow else Color.White
                            )
                        }
                        if (i < leadersList.size - 1) {
                            Divider(color = Color.Gray.copy(alpha = 0.2f), thickness = 0.5.dp)
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// TAB 4: COMUNIDAD TIMELINE
// -------------------------------------------------------------
@Composable
fun TabComunidad(
    viewModel: MainViewModel,
    posts: List<CommunityPost>,
    comments: List<PostComment>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Form panel to create new posts
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = FolkDarkSurface)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Comparte con la Hermandad Dancística",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = FolkYellow
                    )

                    OutlinedTextField(
                        value = viewModel.postInputMessage,
                        onValueChange = { viewModel.postInputMessage = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .testTag("community_post_field"),
                        placeholder = { Text("¿Qué dancística estás ensayando hoy? Escribe aquí...", color = Color.Gray, fontSize = 13.sp) },
                        textStyle = TextStyle(color = Color.White, fontSize = 13.sp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = FolkYellow,
                            unfocusedBorderColor = Color.DarkGray
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Tag selection for post
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text("Danza:", fontSize = 11.sp, color = Color.Gray)
                            val tags = listOf("Caporales", "Tinku", "Morenada", "Cueca")
                            tags.forEach { tag ->
                                val isSelected = viewModel.postInputDanceLabel == tag
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isSelected) FolkRed else FolkDarkSurfaceVariant)
                                        .clickable { viewModel.postInputDanceLabel = tag }
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = tag,
                                        fontSize = 10.sp,
                                        color = if (isSelected) Color.White else Color.Gray,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        Button(
                            onClick = { viewModel.createCommunityPost() },
                            colors = ButtonDefaults.buttonColors(containerColor = FolkYellow),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .height(36.dp)
                                .testTag("publish_post_button")
                        ) {
                            Text("Publicar", color = FolkDarkBg, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        }
                    }
                }
            }
        }

        // Heading title
        item {
            Text(
                text = "Muro de Publicaciones Recientes",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        }

        // Post list retrieved from Room
        items(posts) { post ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = FolkDarkSurface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Profile info of author
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when (post.authorAvatarIndex) {
                                            0 -> FolkRed
                                            1 -> FolkTinkuPurple
                                            2 -> FolkGreen
                                            3 -> FolkYellow
                                            else -> FolkPinkAccent
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = when (post.authorAvatarIndex) {
                                        0 -> "💃"
                                        1 -> "🕺"
                                        2 -> "🎭"
                                        3 -> "📯"
                                        else -> "🌟"
                                    },
                                    fontSize = 18.sp
                                )
                            }
                            Column {
                                Text(
                                    text = post.authorName,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                                Text(
                                    text = "Bailarín ${post.authorLevel}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = FolkYellow
                                )
                            }
                        }

                        // Dance label tag
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = FolkGreen.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = post.danceLabel,
                                color = FolkGreen,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = post.message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Divider(color = Color.Gray.copy(alpha = 0.2f), thickness = 0.5.dp)

                    // Reactions row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { viewModel.toggleLikeOnPost(post) },
                                modifier = Modifier.testTag("like_post_${post.id}")
                            ) {
                                Icon(
                                    imageVector = if (post.userHasLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Me gusta",
                                    tint = if (post.userHasLiked) FolkRed else Color.Gray,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Text(
                                text = "${post.likesCount}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White
                            )
                        }

                        // Comment button
                        TextButton(
                            onClick = { viewModel.selectActivePostComments(post.id) },
                            modifier = Modifier.testTag("open_comments_${post.id}")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Comment,
                                contentDescription = "Comentarios",
                                tint = FolkYellow,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Comentar",
                                color = FolkYellow,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Collapsible comments subsection
                    if (viewModel.activeCommentingPostId == post.id) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(FolkDarkSurfaceVariant)
                                .padding(12.dp)
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(
                                    text = "Comentarios de la Fraternidad:",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = FolkYellow
                                )

                                if (comments.isEmpty()) {
                                    Text(
                                        text = "Aún no hay comentarios. ¡Sé el primero!",
                                        fontSize = 11.sp,
                                        color = Color.Gray
                                    )
                                } else {
                                    comments.forEach { comment ->
                                        Column(modifier = Modifier.fillMaxWidth()) {
                                            Text(
                                                text = comment.authorName,
                                                fontSize = 10.sp,
                                                color = FolkGreen,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = comment.message,
                                                fontSize = 11.sp,
                                                color = Color.LightGray
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                        }
                                    }
                                }

                                Divider(color = Color.Gray.copy(alpha = 0.2f))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    OutlinedTextField(
                                        value = viewModel.commentInputText,
                                        onValueChange = { viewModel.commentInputText = it },
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(40.dp)
                                            .testTag("comment_field_${post.id}"),
                                        placeholder = { Text("Escribe una sugerencia o respuesta...", color = Color.Gray, fontSize = 10.sp) },
                                        textStyle = TextStyle(color = Color.White, fontSize = 11.sp),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = FolkYellow,
                                            unfocusedBorderColor = Color.DarkGray
                                        )
                                    )

                                    IconButton(
                                        onClick = { viewModel.addCommentToActivePost() },
                                        modifier = Modifier
                                            .size(32.dp)
                                            .clip(CircleShape)
                                            .background(FolkYellow)
                                            .testTag("submit_comment_${post.id}")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Send,
                                            contentDescription = "Enviar",
                                            tint = FolkDarkBg,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// TAB 5: PERFIL (AVATARS, CLOTHES CUSTOMIZATION & MINIGAMES)
// -------------------------------------------------------------
@Composable
fun TabPerfil(
    viewModel: MainViewModel,
    favorites: List<CostumeDesign>,
    events: List<EventReminder>
) {
    var calendarExpandForm by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Upper stats and Avatar Customizer preview box
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = FolkDarkSurface)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Configuración del Avatar Dancístico",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = FolkYellow,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )

                    // Avatar custom rendering silhouette
                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                            .background(Color(android.graphics.Color.parseColor(viewModel.avatarOutfitColor)))
                            .border(3.dp, FolkYellow, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "🤠", // Hat
                                fontSize = 34.sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "🥋", // Poncho/Dress
                                fontSize = 34.sp
                            )
                        }
                        
                        // Small accessory sign overlay if cape is on
                        if (viewModel.avatarCapeEnabled) {
                            Surface(
                                shape = CircleShape,
                                color = FolkGoldAccent,
                                modifier = Modifier
                                    .size(24.dp)
                                    .align(Alignment.BottomEnd)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Capa",
                                    tint = FolkDarkBg,
                                    modifier = Modifier.padding(4.dp)
                                )
                            }
                        }
                    }

                    // Quick Customize form sliders for avatar
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Color Traje Corporal:",
                            fontSize = 11.sp,
                            color = Color.LightGray
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            listOf("#E53935", "#FFFFB300", "#43A047", "#673AB7", "#E91E63").forEach { hex ->
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(Color(android.graphics.Color.parseColor(hex)))
                                        .border(
                                            width = if (viewModel.avatarOutfitColor == hex) 3.dp else 0.dp,
                                            color = Color.White,
                                            shape = CircleShape
                                        )
                                        .clickable { viewModel.avatarOutfitColor = hex }
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Capa Folclórica Brillo:",
                                fontSize = 11.sp,
                                color = Color.LightGray
                            )
                            Switch(
                                checked = viewModel.avatarCapeEnabled,
                                onCheckedChange = { viewModel.avatarCapeEnabled = it },
                                colors = SwitchDefaults.colors(checkedThumbColor = FolkYellow)
                            )
                        }
                    }

                    // Level label and points tracker
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(FolkDarkSurfaceVariant)
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("EXPERIENCIA", fontSize = 10.sp, color = Color.Gray)
                            Text(
                                text = when (viewModel.userExperienceLevel) {
                                    FolkLevel.BASICO -> "Básico Corriente"
                                    FolkLevel.INTERMEDIO -> "Bailarín de Plata"
                                    FolkLevel.AVANZADO -> "Capataz de Oruro"
                                },
                                fontWeight = FontWeight.Bold,
                                color = FolkYellow
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text("MEDALLAS", fontSize = 10.sp, color = Color.Gray)
                            Text(
                                text = "${viewModel.userPoints / 100} Unlocks",
                                fontWeight = FontWeight.Bold,
                                color = FolkGreen
                            )
                        }
                    }
                }
            }
        }

        // TALLER VIRTUAL DE DISEÑO DE VESTUARIO FOLCLÓRICO (Requested feature)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = FolkDarkSurface)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(imageVector = Icons.Default.Palette, contentDescription = "Sastre", tint = FolkYellow)
                        Text(
                            text = "Taller de Costura Virtual",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = FolkYellow
                        )
                    }

                    Text(
                        text = "Elige la danza, diseña la paleta del bordado y guarda tus trajes tradicionales predilectos para tu ballet.",
                        fontSize = 11.sp,
                        color = Color.LightGray
                    )

                    // Text Field for Design name
                    OutlinedTextField(
                        value = viewModel.designTitle,
                        onValueChange = { viewModel.designTitle = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("design_title_input"),
                        label = { Text("Nombre del Traje (ej. Diablesa Imperial)", color = Color.Gray, fontSize = 12.sp) },
                        textStyle = TextStyle(color = Color.White),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = FolkYellow,
                            unfocusedBorderColor = Color.DarkGray
                        )
                    )

                    // Dance selection trigger
                    Text("Selecciona una danza boliviana:", fontSize = 11.sp, color = Color.Gray)
                    val dances = listOf("Caporales", "Morenada", "Tinku", "Diablada", "Tobas", "Saya", "Llamerada", "Cueca")
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(dances) { d ->
                            val isSel = viewModel.designDanceType == d
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSel) FolkYellow else FolkDarkSurfaceVariant)
                                    .clickable { viewModel.designDanceType = d }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = d,
                                    fontSize = 10.sp,
                                    color = if (isSel) FolkDarkBg else Color.Gray,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    // Swatches for tailor bordados
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Color Bordado Primario:", fontSize = 11.sp, color = Color.Gray)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf("#E53935", "#FFFFFF", "#FFFFB300", "#43A047", "#E91E63").forEach { c ->
                                val isChosen = viewModel.designPrimaryColorHex == c
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(Color(android.graphics.Color.parseColor(c)))
                                        .border(
                                            width = if (isChosen) 2.dp else 0.dp,
                                            color = Color.White,
                                            shape = RoundedCornerShape(6.dp)
                                        )
                                        .clickable { viewModel.designPrimaryColorHex = c }
                                )
                            }
                        }
                    }

                    // Accessories settings dropdown or list
                    Text("Sombrerería tradicional y adornos:", fontSize = 11.sp, color = Color.Gray)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        listOf("Sombrero Planchado", "Montera Plumas", "Máscara Fiera", "Chore de lana").forEach { hat ->
                            val isSelected = viewModel.designHatType == hat
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) FolkGreen else FolkDarkSurfaceVariant)
                                    .clickable { viewModel.designHatType = hat }
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = hat,
                                    fontSize = 9.sp,
                                    color = if (isSelected) Color.White else Color.Gray,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    // Custom Visual Preview of created costume
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(FolkDarkSurfaceVariant)
                            .border(1.dp, FolkYellow.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "PREVENTA DE TRAJE DIGITAL",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = FolkYellow
                                )
                                Text(
                                    text = viewModel.designDanceType.uppercase(),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = FolkGreen
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                // Draw stylized color bar on left side representing traditional aguayo
                                Box(
                                    modifier = Modifier
                                        .width(28.dp)
                                        .height(70.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(
                                            Brush.verticalGradient(
                                                colors = listOf(
                                                    Color(android.graphics.Color.parseColor(viewModel.designPrimaryColorHex)),
                                                    Color(android.graphics.Color.parseColor(viewModel.designSecondaryColorHex)),
                                                    Color(android.graphics.Color.parseColor(viewModel.designAccentColorHex))
                                                )
                                            )
                                        )
                                )

                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = viewModel.designTitle,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                        color = Color.White
                                    )
                                    Text(
                                        text = "Tocado superior: ${viewModel.designHatType}",
                                        fontSize = 11.sp,
                                        color = Color.LightGray
                                    )
                                    Text(
                                        text = "Calzado: Cascabeles y espuelas mágicas",
                                        fontSize = 10.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }

                    Button(
                        onClick = { viewModel.saveCurrentCostumeDesign() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("save_costume_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = FolkGreen),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = "Guardar", tint = Color.White)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Guardar Traje en Favoritos (+25 pts)", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Favorites gallery shelf
        if (favorites.isNotEmpty()) {
            item {
                Text(
                    text = "Tus Trajes Guardados",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
            }

            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(favorites) { design ->
                        Card(
                            modifier = Modifier
                                .width(180.dp)
                                .height(130.dp),
                            colors = CardDefaults.cardColors(containerColor = FolkDarkSurface)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(10.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = design.danceType,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = FolkYellow
                                    )

                                    IconButton(
                                        onClick = { viewModel.deleteCostumeDesign(design) },
                                        modifier = Modifier
                                            .size(24.dp)
                                            .testTag("delete_design_${design.id}")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Cancelar",
                                            tint = FolkRed,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = design.title,
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    listOf(design.primaryColorHex, design.secondaryColorHex, design.accentColorHex).forEach { h ->
                                        Box(
                                            modifier = Modifier
                                                .size(16.dp)
                                                .clip(CircleShape)
                                                .background(Color(android.graphics.Color.parseColor(h)))
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = design.hatType.take(13),
                                        fontSize = 9.sp,
                                        color = Color.Gray,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // MINIJUEGOS CULTURALES: MULTI-JUEGOS HUB (Requested features: Trivia, Adivina, Trajes, Ritmo)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = FolkDarkSurface)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    // Header of Minigames Hub
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(imageVector = Icons.Default.Quiz, contentDescription = "Minijuegos", tint = FolkYellow)
                            Text(
                                text = "🕹️ Minijuegos Folclóricos",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = FolkYellow
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = FolkRed.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = "NUEVO HUB",
                                color = FolkRed,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Text(
                        text = "Pon a prueba tu pasión dancística con retos sobre significados, misterios, indumentarias y la percusión rítmica boliviana.",
                        fontSize = 11.sp,
                        color = Color.LightGray
                    )

                    // 4-Game Menu Selector Pills
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(FolkDarkSurfaceVariant)
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        val games = listOf(
                            "TRIVIA" to "🎓 Trivia",
                            "ADIVINA" to "🕵️‍♂️ Adivina",
                            "TRAJES" to "🎭 Traje",
                            "RITMO" to "🥁 Ritmo"
                        )
                        games.forEach { (type, label) ->
                            val isSel = viewModel.activeMinigameType == type
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (isSel) FolkYellow else Color.Transparent)
                                    .clickable { viewModel.activeMinigameType = type }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = label,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSel) FolkDarkBg else Color.LightGray
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Dynamic Sub-Screen rendering based on selection
                    when (viewModel.activeMinigameType) {
                        "TRIVIA" -> {
                            // ----------------- GAME 1: TRIVIA DE CULTURA -----------------
                            if (viewModel.isTriviaFinished) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(FolkDarkSurfaceVariant)
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                        Text("🎓", fontSize = 48.sp)
                                        Text(
                                            text = "¡Reto Trivia Concluido!",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = FolkYellow
                                        )
                                        Text(
                                            text = "Ganaste un total de +${viewModel.triviaPointsEarned} pts folclóricos.",
                                            fontSize = 13.sp,
                                            color = Color.White
                                        )

                                        Button(
                                            onClick = { viewModel.restartTriviaGame() },
                                            colors = ButtonDefaults.buttonColors(containerColor = FolkGreen),
                                            modifier = Modifier.testTag("trivia_restart")
                                        ) {
                                            Text("Jugar de nuevo")
                                        }
                                    }
                                }
                            } else {
                                val activeQ = viewModel.triviaQuestions[viewModel.currentTriviaQuestionIndex]
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(FolkDarkSurfaceVariant)
                                        .padding(14.dp)
                                ) {
                                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = "Trivia: Pregunta ${viewModel.currentTriviaQuestionIndex + 1} de ${viewModel.triviaQuestions.size}",
                                                fontSize = 10.sp,
                                                color = Color.Gray,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = "Puntos: ${viewModel.triviaPointsEarned} pts",
                                                fontSize = 10.sp,
                                                color = FolkYellow,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }

                                        Text(
                                            text = activeQ.question,
                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                            color = Color.White
                                        )

                                        Spacer(modifier = Modifier.height(4.dp))

                                        activeQ.options.forEachIndexed { optIdx, option ->
                                            val isSelected = viewModel.triviaSelectedOptionIndex == optIdx
                                            val isCorrect = optIdx == activeQ.correctIndex
                                            val optionColor = if (viewModel.isTriviaAnswered) {
                                                if (isCorrect) FolkGreen else if (isSelected) FolkRed else FolkDarkSurface
                                            } else {
                                                if (isSelected) FolkYellow else FolkDarkSurface
                                            }

                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .background(optionColor)
                                                    .clickable { viewModel.selectTriviaOption(optIdx) }
                                                    .padding(12.dp)
                                                    .testTag("trivia_option_${optIdx}")
                                            ) {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text(
                                                        text = option,
                                                        fontSize = 12.sp,
                                                        color = if (isSelected || (viewModel.isTriviaAnswered && isCorrect)) Color.White else Color.LightGray,
                                                        fontWeight = if (isSelected || (viewModel.isTriviaAnswered && isCorrect)) FontWeight.Bold else FontWeight.Normal
                                                    )
                                                    
                                                    if (viewModel.isTriviaAnswered) {
                                                        if (isCorrect) {
                                                            Icon(imageVector = Icons.Default.Check, contentDescription = "Correcto", tint = Color.White)
                                                        } else if (isSelected) {
                                                            Icon(imageVector = Icons.Default.Close, contentDescription = "Incorrecto", tint = Color.White)
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        if (viewModel.isTriviaAnswered) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clip(RoundedCornerShape(6.dp))
                                                    .background(Color.Black.copy(alpha = 0.2f))
                                                    .padding(10.dp)
                                            ) {
                                                Text(
                                                    text = "Sabías que: ${activeQ.explanation}",
                                                    fontSize = 11.sp,
                                                    color = FolkYellow,
                                                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                                                )
                                            }

                                            Button(
                                                onClick = { viewModel.nextTriviaQuestion() },
                                                modifier = Modifier
                                                    .align(Alignment.End)
                                                    .testTag("trivia_next"),
                                                colors = ButtonDefaults.buttonColors(containerColor = FolkYellow)
                                            ) {
                                                Text(
                                                    text = if (viewModel.currentTriviaQuestionIndex == viewModel.triviaQuestions.size - 1) "Terminar" else "Siguiente ➔",
                                                    color = FolkDarkBg,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 11.sp
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        "ADIVINA" -> {
                            // ----------------- GAME 2: ADIVINA LA DANZA -----------------
                            if (viewModel.isGuessFinished) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(FolkDarkSurfaceVariant)
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                        Text("🕵️‍♂️", fontSize = 48.sp)
                                        Text(
                                            text = "¡Adivina la Danza Concluido!",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = FolkYellow
                                        )
                                        Text(
                                            text = "Ganaste un total de +${viewModel.guessPointsEarned} pts folclóricos.",
                                            fontSize = 13.sp,
                                            color = Color.White
                                        )

                                        Button(
                                            onClick = { viewModel.restartGuessGame() },
                                            colors = ButtonDefaults.buttonColors(containerColor = FolkGreen),
                                            modifier = Modifier.testTag("guess_restart")
                                        ) {
                                            Text("Jugar de nuevo")
                                        }
                                    }
                                }
                            } else {
                                val activeQ = viewModel.guessQuestions[viewModel.currentGuessQuestionIndex]
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(FolkDarkSurfaceVariant)
                                        .padding(14.dp)
                                ) {
                                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = "Adivina: Danza Mágica ${viewModel.currentGuessQuestionIndex + 1} de ${viewModel.guessQuestions.size}",
                                                fontSize = 10.sp,
                                                color = Color.Gray,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = "Puntos: ${viewModel.guessPointsEarned} pts",
                                                fontSize = 10.sp,
                                                color = FolkYellow,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }

                                        Text(
                                            text = "Adivina cuál es la danza que tiene estas características coreográficas:",
                                            fontSize = 10.sp,
                                            color = FolkGoldAccent,
                                            fontWeight = FontWeight.Bold
                                        )

                                        Text(
                                            text = activeQ.description,
                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                            color = Color.White
                                        )

                                        Spacer(modifier = Modifier.height(4.dp))

                                        activeQ.options.forEachIndexed { optIdx, option ->
                                            val isSelected = viewModel.guessSelectedOptionIndex == optIdx
                                            val isCorrect = optIdx == activeQ.correctIndex
                                            val optionColor = if (viewModel.isGuessAnswered) {
                                                if (isCorrect) FolkGreen else if (isSelected) FolkRed else FolkDarkSurface
                                            } else {
                                                if (isSelected) FolkYellow else FolkDarkSurface
                                            }

                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .background(optionColor)
                                                    .clickable { viewModel.selectGuessOption(optIdx) }
                                                    .padding(12.dp)
                                                    .testTag("guess_option_${optIdx}")
                                            ) {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text(
                                                        text = option,
                                                        fontSize = 12.sp,
                                                        color = if (isSelected || (viewModel.isGuessAnswered && isCorrect)) Color.White else Color.LightGray,
                                                        fontWeight = if (isSelected || (viewModel.isGuessAnswered && isCorrect)) FontWeight.Bold else FontWeight.Normal
                                                    )
                                                    
                                                    if (viewModel.isGuessAnswered) {
                                                        if (isCorrect) {
                                                            Icon(imageVector = Icons.Default.Check, contentDescription = "Correcto", tint = Color.White)
                                                        } else if (isSelected) {
                                                            Icon(imageVector = Icons.Default.Close, contentDescription = "Incorrecto", tint = Color.White)
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        if (viewModel.isGuessAnswered) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clip(RoundedCornerShape(6.dp))
                                                    .background(Color.Black.copy(alpha = 0.2f))
                                                    .padding(10.dp)
                                            ) {
                                                Text(
                                                    text = "Pista: ${activeQ.hint}",
                                                    fontSize = 11.sp,
                                                    color = FolkYellow,
                                                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                                                )
                                            }

                                            Button(
                                                onClick = { viewModel.nextGuessQuestion() },
                                                modifier = Modifier
                                                    .align(Alignment.End)
                                                    .testTag("guess_next"),
                                                colors = ButtonDefaults.buttonColors(containerColor = FolkYellow)
                                            ) {
                                                Text(
                                                    text = if (viewModel.currentGuessQuestionIndex == viewModel.guessQuestions.size - 1) "Terminar" else "Siguiente ➔",
                                                    color = FolkDarkBg,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 11.sp
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        "TRAJES" -> {
                            // ----------------- GAME 3: RELACIONA EL TRAJE -----------------
                            if (viewModel.isMatchFinished) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(FolkDarkSurfaceVariant)
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                        Text("🎭", fontSize = 48.sp)
                                        Text(
                                            text = "¡Relaciona Trajes Concluido!",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = FolkYellow
                                        )
                                        Text(
                                            text = "Ganaste un total de +${viewModel.matchPointsEarned} pts folclóricos.",
                                            fontSize = 13.sp,
                                            color = Color.White
                                        )

                                        Button(
                                            onClick = { viewModel.restartMatchGame() },
                                            colors = ButtonDefaults.buttonColors(containerColor = FolkGreen),
                                            modifier = Modifier.testTag("match_restart")
                                        ) {
                                            Text("Jugar de nuevo")
                                        }
                                    }
                                }
                            } else {
                                val activeQ = viewModel.matchQuestions[viewModel.currentMatchQuestionIndex]
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(FolkDarkSurfaceVariant)
                                        .padding(14.dp)
                                ) {
                                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = "Trajes: Reto ${viewModel.currentMatchQuestionIndex + 1} de ${viewModel.matchQuestions.size}",
                                                fontSize = 10.sp,
                                                color = Color.Gray,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = "Puntos: ${viewModel.matchPointsEarned} pts",
                                                fontSize = 10.sp,
                                                color = FolkYellow,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Text(activeQ.emoji, fontSize = 32.sp)
                                            Text(
                                                text = activeQ.costumeName,
                                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                                color = FolkYellow
                                            )
                                        }

                                        Text(
                                            text = activeQ.costumeDetails,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color.White
                                        )

                                        Spacer(modifier = Modifier.height(4.dp))

                                        activeQ.options.forEachIndexed { optIdx, option ->
                                            val isSelected = viewModel.matchSelectedOptionIndex == optIdx
                                            val isCorrect = optIdx == activeQ.correctIndex
                                            val optionColor = if (viewModel.isMatchAnswered) {
                                                if (isCorrect) FolkGreen else if (isSelected) FolkRed else FolkDarkSurface
                                            } else {
                                                if (isSelected) FolkYellow else FolkDarkSurface
                                            }

                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .background(optionColor)
                                                    .clickable { viewModel.selectMatchOption(optIdx) }
                                                    .padding(12.dp)
                                                    .testTag("match_option_${optIdx}")
                                            ) {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text(
                                                        text = option,
                                                        fontSize = 12.sp,
                                                        color = if (isSelected || (viewModel.isMatchAnswered && isCorrect)) Color.White else Color.LightGray,
                                                        fontWeight = if (isSelected || (viewModel.isMatchAnswered && isCorrect)) FontWeight.Bold else FontWeight.Normal
                                                    )
                                                    
                                                    if (viewModel.isMatchAnswered) {
                                                        if (isCorrect) {
                                                            Icon(imageVector = Icons.Default.Check, contentDescription = "Correcto", tint = Color.White)
                                                        } else if (isSelected) {
                                                            Icon(imageVector = Icons.Default.Close, contentDescription = "Incorrecto", tint = Color.White)
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        if (viewModel.isMatchAnswered) {
                                            Button(
                                                onClick = { viewModel.nextMatchQuestion() },
                                                modifier = Modifier
                                                    .align(Alignment.End)
                                                    .testTag("match_next"),
                                                colors = ButtonDefaults.buttonColors(containerColor = FolkYellow)
                                            ) {
                                                Text(
                                                    text = if (viewModel.currentMatchQuestionIndex == viewModel.matchQuestions.size - 1) "Terminar" else "Siguiente ➔",
                                                    color = FolkDarkBg,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 11.sp
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        "RITMO" -> {
                            // ----------------- GAME 4: COMPLETA EL RITMO -----------------
                            if (viewModel.isRhythmFinished) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(FolkDarkSurfaceVariant)
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                        Text("🥁", fontSize = 48.sp)
                                        Text(
                                            text = "¡Rítmica Boliviana Concluida!",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = FolkYellow
                                        )
                                        Text(
                                            text = "Ganaste un total de +${viewModel.rhythmPointsEarned} pts folclóricos.",
                                            fontSize = 13.sp,
                                            color = Color.White
                                        )

                                        Button(
                                            onClick = { viewModel.restartRhythmGame() },
                                            colors = ButtonDefaults.buttonColors(containerColor = FolkGreen),
                                            modifier = Modifier.testTag("rhythm_restart")
                                        ) {
                                            Text("Jugar de nuevo")
                                        }
                                    }
                                }
                            } else {
                                val activeQ = viewModel.rhythmQuestions[viewModel.currentRhythmQuestionIndex]
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(FolkDarkSurfaceVariant)
                                        .padding(14.dp)
                                ) {
                                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = "Ritmo: Deletreo Rítmico ${viewModel.currentRhythmQuestionIndex + 1} de ${viewModel.rhythmQuestions.size}",
                                                fontSize = 10.sp,
                                                color = Color.Gray,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = "Puntos: ${viewModel.rhythmPointsEarned} pts",
                                                fontSize = 10.sp,
                                                color = FolkYellow,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }

                                        Text(
                                            text = "Danza: ${activeQ.danceName}",
                                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                            color = FolkYellow
                                        )

                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(Color.Black.copy(alpha = 0.4f))
                                                .padding(12.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                Text(
                                                    text = activeQ.sequence,
                                                    fontSize = 20.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = FolkGoldAccent
                                                )
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = "Sonido sim: ${activeQ.soundEffectDescription}",
                                                    fontSize = 10.sp,
                                                    color = Color.LightGray,
                                                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                                                )
                                            }
                                        }

                                        Text(
                                            text = activeQ.missingPartHint,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color.White
                                        )

                                        Spacer(modifier = Modifier.height(4.dp))

                                        activeQ.options.forEachIndexed { optIdx, option ->
                                            val isSelected = viewModel.rhythmSelectedOptionIndex == optIdx
                                            val isCorrect = optIdx == activeQ.correctIndex
                                            val optionColor = if (viewModel.isRhythmAnswered) {
                                                if (isCorrect) FolkGreen else if (isSelected) FolkRed else FolkDarkSurface
                                            } else {
                                                if (isSelected) FolkYellow else FolkDarkSurface
                                            }

                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .background(optionColor)
                                                    .clickable { viewModel.selectRhythmOption(optIdx) }
                                                    .padding(12.dp)
                                                    .testTag("rhythm_option_${optIdx}")
                                            ) {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text(
                                                        text = option,
                                                        fontSize = 12.sp,
                                                        color = if (isSelected || (viewModel.isRhythmAnswered && isCorrect)) Color.White else Color.LightGray,
                                                        fontWeight = if (isSelected || (viewModel.isRhythmAnswered && isCorrect)) FontWeight.Bold else FontWeight.Normal
                                                    )
                                                    
                                                    if (viewModel.isRhythmAnswered) {
                                                        if (isCorrect) {
                                                            Icon(imageVector = Icons.Default.Check, contentDescription = "Correcto", tint = Color.White)
                                                        } else if (isSelected) {
                                                            Icon(imageVector = Icons.Default.Close, contentDescription = "Incorrecto", tint = Color.White)
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        if (viewModel.isRhythmAnswered) {
                                            Button(
                                                onClick = { viewModel.nextRhythmQuestion() },
                                                modifier = Modifier
                                                    .align(Alignment.End)
                                                    .testTag("rhythm_next"),
                                                colors = ButtonDefaults.buttonColors(containerColor = FolkYellow)
                                            ) {
                                                Text(
                                                    text = if (viewModel.currentRhythmQuestionIndex == viewModel.rhythmQuestions.size - 1) "Terminar" else "Siguiente ➔",
                                                    color = FolkDarkBg,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 11.sp
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // CALENDAR SCREEN (Ensayo, Presentación, Festivales) - Requested
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(imageVector = Icons.Default.CalendarToday, contentDescription = "Calendario", tint = FolkYellow)
                    Text(
                        text = "Calendario de Ensayos",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                }

                IconButton(
                    onClick = { calendarExpandForm = !calendarExpandForm },
                    modifier = Modifier.testTag("toggle_calendar_form")
                ) {
                    Icon(
                        imageVector = if (calendarExpandForm) Icons.Default.Close else Icons.Default.Add,
                        contentDescription = "Expandir calendario",
                        tint = FolkYellow
                    )
                }
            }
        }

        // Calendar Expandable Entry Form
        if (calendarExpandForm) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = FolkDarkSurface)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Agendar Actividad Folclórica",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = FolkYellow
                        )

                        OutlinedTextField(
                            value = viewModel.newEventTitle,
                            onValueChange = { viewModel.newEventTitle = it },
                            placeholder = { Text("Ej. Ensayo general saya") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .testTag("calendar_title_input"),
                            label = { Text("Nombre de la actividad") },
                            textStyle = TextStyle(color = Color.White),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FolkYellow)
                        )

                        // Selector for Activity Type
                        Text("Tipo de Evento:", fontSize = 11.sp, color = Color.Gray)
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            listOf("Ensayo", "Presentación", "Festival").forEach { type ->
                                val isSelected = viewModel.newEventType == type
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isSelected) FolkRed else FolkDarkSurfaceVariant)
                                        .clickable { viewModel.newEventType = type }
                                        .padding(10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = type,
                                        fontSize = 11.sp,
                                        color = if (isSelected) Color.White else Color.Gray,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedTextField(
                                value = viewModel.newEventDateString,
                                onValueChange = { viewModel.newEventDateString = it },
                                placeholder = { Text("Ej: 08 Jun") },
                                modifier = Modifier
                                    .weight(1.5f)
                                    .testTag("calendar_date_input"),
                                label = { Text("Fecha", fontSize = 11.sp) },
                                textStyle = TextStyle(color = Color.White),
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FolkYellow)
                            )

                            OutlinedTextField(
                                value = viewModel.newEventTimeString,
                                onValueChange = { viewModel.newEventTimeString = it },
                                placeholder = { Text("Ej: 19:30") },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("calendar_time_input"),
                                label = { Text("Hora", fontSize = 11.sp) },
                                textStyle = TextStyle(color = Color.White),
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FolkYellow)
                            )
                        }

                        OutlinedTextField(
                            value = viewModel.newEventDescription,
                            onValueChange = { viewModel.newEventDescription = it },
                            placeholder = { Text("Detalles del salón, requerimiento de calzado...") },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Detalles (Opcional)") },
                            textStyle = TextStyle(color = Color.White),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = FolkYellow)
                        )

                        Button(
                            onClick = {
                                viewModel.saveEventReminder()
                                calendarExpandForm = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("save_event_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = FolkYellow)
                        ) {
                            Text("Guardar Actividad (+15 pts)", color = FolkDarkBg, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // List scheduled events
        if (events.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = FolkDarkSurfaceVariant)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay actividades próximas agendadas.",
                            color = Color.Gray,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        } else {
            items(events) { event ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = when (event.eventType) {
                            "Presentación" -> FolkTinkuPurple.copy(alpha = 0.15f)
                            "Festival" -> FolkRed.copy(alpha = 0.15f)
                            else -> FolkDarkSurface
                        }
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            // Date circle indicator
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        when (event.eventType) {
                                            "Presentación" -> FolkTinkuPurple
                                            "Festival" -> FolkRed
                                            else -> FolkGreen
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = event.dateString.substringBefore(" "),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Text(
                                        text = event.dateString.substringAfter(" "),
                                        fontSize = 8.sp,
                                        color = Color.White.copy(alpha = 0.8f)
                                    )
                                }
                            }

                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = event.title,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                        color = Color.White
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = Color.White.copy(alpha = 0.1f)
                                    ) {
                                        Text(
                                            text = event.eventType,
                                            fontSize = 8.sp,
                                            color = FolkYellow,
                                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }

                                Text(
                                    text = "Hora: ${event.timeString}",
                                    fontSize = 11.sp,
                                    color = FolkYellow
                                )

                                Text(
                                    text = event.description,
                                    fontSize = 11.sp,
                                    color = Color.Gray,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                        IconButton(
                            onClick = { viewModel.deleteEventReminder(event) },
                            modifier = Modifier.testTag("delete_event_${event.id}")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Cancelar",
                                tint = Color.Gray,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// TIMER OVERLAY REUSABLE COMPONENT
// -------------------------------------------------------------
@Composable
fun PracticeTimerOverlay(viewModel: MainViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.85f))
            .clickable(enabled = false) {}, // absorb clicks underneath
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .width(320.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = FolkDarkSurface),
            border = BorderStroke(2.dp, FolkYellow)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "¡EN ENSAYO ACTIVO!",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    color = FolkRed
                )

                Text(
                    text = viewModel.activePracticeDanceName,
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )

                // Large beautifully spinning or pulsing layout representing the time remaining
                Box(
                    modifier = Modifier
                        .size(130.dp)
                        .clip(CircleShape)
                        .background(FolkDarkSurfaceVariant)
                        .border(3.dp, FolkGreen, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "00:${if (viewModel.practiceTimerSecondsLeft < 10) "0" else ""}${viewModel.practiceTimerSecondsLeft}",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                        Text(
                            text = "segundos",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                }

                // Dynamic practice instructions
                Text(
                    text = viewModel.practiceProgressMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    color = FolkYellow,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.height(44.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { viewModel.stopPracticeTimer() },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("cancel_practice_timer"),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                        border = BorderStroke(1.dp, Color.Gray)
                    ) {
                        Text("Cancelar", fontSize = 11.sp)
                    }
                }
            }
        }
    }
}
