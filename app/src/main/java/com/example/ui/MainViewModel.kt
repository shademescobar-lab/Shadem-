package com.example.ui

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// Screen Enumeration conforming to the requested navigation structure
enum class FolkTab {
    INICIO,
    TUTORIALES,
    RETOS,
    COMUNIDAD,
    PERFIL
}

// Experience Level for dancers
enum class FolkLevel {
    BASICO,
    INTERMEDIO,
    AVANZADO
}

enum class FolkThemeColor(
    val id: String,
    val displayName: String,
    val primaryHex: String,
    val secondaryHex: String,
    val tertiaryHex: String,
    val emoji: String,
    val primaryBgHex: String,
    val surfaceHex: String
) {
    BOLIVIA("bolivia", "Estilo Tricolor", "#E53935", "#FFFFB300", "#43A047", "🇧🇴", "#0F1115", "#181C24"),
    CAPORALES("caporales", "Caporales Chic", "#E91E63", "#FFFFC107", "#00BCD4", "✨", "#140A10", "#21111B"),
    TINKU("tinku", "Tinku Eléctrico", "#9C27B0", "#FF5722", "#00E676", "⚡", "#0A0814", "#1F112B"),
    VALLE("valle", "Valle Chapaco", "#D32F2F", "#8BC34A", "#FF9800", "🍇", "#120808", "#240F0F"),
    AMAZONAS("amazonas", "Amazonas Moxos", "#00838F", "#FFB300", "#009688", "🦜", "#051214", "#0C2326")
}

// Trivia Question definition for Minigames
data class TriviaQuestion(
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

// Dance Step item for the dictionary
data class FolkStep(
    val id: String,
    val name: String,
    val danceType: String,
    val description: String,
    val movementInstruction: String,
    val rhythmCount: String,
    val difficulty: String,
    val animationEmoji: String
)

// "Adivina la danza" structure
data class GuessQuestion(
    val description: String,
    val options: List<String>,
    val correctIndex: Int,
    val hint: String
)

// "Relaciona el traje con la danza" structure
data class MatchQuestion(
    val costumeName: String,
    val costumeDetails: String,
    val options: List<String>,
    val correctIndex: Int,
    val emoji: String
)

// "Completa el ritmo musical" structure
data class RhythmQuestion(
    val danceName: String,
    val sequence: String,
    val missingPartHint: String,
    val options: List<String>,
    val correctIndex: Int,
    val soundEffectDescription: String
)

// Traditional Audio Track structure
data class FolkAudioTrack(
    val id: String,
    val title: String,
    val danceType: String,
    val artist: String = "Tradicional",
    val duration: String = "3:00",
    val description: String = ""
)

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val repository = FolkRepository(db.folkDao())

    // Complete static dictionary of Bolivia's traditional and modern dance steps
    val danceStepsDic = listOf(
        FolkStep(
            id = "cap_salto",
            name = "El Salto del Caporal",
            danceType = "Caporales",
            description = "Un brinco majestuoso y atlético donde el bailarín levanta una rodilla casi al pecho y extiende el otro pie hacia atrás, cayendo firmemente sobre un pie con energía.",
            movementInstruction = "1. Impúlsate con el pie izquierdo. 2. Levanta la rodilla derecha con fuerza. 3. Extiende el brazo izquierdo hacia el cielo con el sombrero en mano. 4. Cae doblando la rodilla.",
            rhythmCount = "¡Un! - Dos - Tres",
            difficulty = "Avanzado",
            animationEmoji = "🦘"
        ),
        FolkStep(
            id = "cap_hombro",
            name = "Sacudida de Hombros y Cascabeles",
            danceType = "Caporales",
            description = "Paso de marcación y coquetería. Mientras se avanza con soltura, se sacuden rítmicamente los hombros y las botas para hacer relucir el vibrar metálico de los cascabeles de bronce.",
            movementInstruction = "1. Da pasos cortos de balanceo con los metatarsos. 2. Mueve los hombros adelante y atrás de manera alternada de acuerdo al bombo. 3. Sacude con decisión tus botas.",
            rhythmCount = "Y-Uno-Y-Dos",
            difficulty = "Básico",
            animationEmoji = "🕺"
        ),
        FolkStep(
            id = "mor_tropa",
            name = "El Paso del Moreno Pesado",
            danceType = "Morenada",
            description = "Simula el lento caminar de los esclavos cargados con lingotes de plata fina. Es un marchar pausado y elegante que transmite imponencia, marcando con firmeza cada compás.",
            movementInstruction = "1. Pisa arrastrando levemente el taco. 2. Balancea los hombros de izquierda a derecha. 3. Sincroniza el repique de tu fono/matraca de forma circular.",
            rhythmCount = "Tan... Tan... ¡Pam!",
            difficulty = "Básico",
            animationEmoji = "⚖️"
        ),
        FolkStep(
            id = "mor_giro",
            name = "Giro Señorial de Pollerín",
            danceType = "Morenada",
            description = "Este magnífico giro muestra el esplendor del pesado traje de chola o moreno. Se hace con calma, estirando los brazos de manera protectora y majestuosa.",
            movementInstruction = "1. Levanta un poco el fono o baja tu manta. 2. Da un giro complete de 360 grados sobre tu eje trasero en tres tiempos lentos. 3. Detente con el pecho erguido.",
            rhythmCount = "La.. Sol.. Do",
            difficulty = "Intermedio",
            animationEmoji = "🌀"
        ),
        FolkStep(
            id = "tink_zapateo",
            name = "Zapateado Guerrero",
            danceType = "Tinku",
            description = "El paso central, de contacto directo con la tierra. Se golpea vigorosamente la planta del pie contra el suelo para ofrendar toda la vibración física a la Pachamama.",
            movementInstruction = "1. Levanta la pierna flexionando la cadera. 2. Golpea con toda la suela plana el suelo. 3. Alterna de inmediato al otro pie con el torso levemente inclinado hacia el frente.",
            rhythmCount = "¡GOLPE! ... ¡GOLPE!",
            difficulty = "Avanzado",
            animationEmoji = "⚡"
        ),
        FolkStep(
            id = "tink_amague",
            name = "El Amague de Puño Cruzado",
            danceType = "Tinku",
            description = "Simulación ritual del combate tradicional. Los saltos defensivos van acompañados de amagues vigorosos cruzando los brazos revestidos de gruesos guantes de cuero.",
            movementInstruction = "1. Salta sobre los lados con paso de péndulo. 2. Lanza el puño derecho de forma cruzada hacia el frente mientras encorvas la espalda para evadir golpes simulados.",
            rhythmCount = "Uno-Amague-Dos",
            difficulty = "Intermedio",
            animationEmoji = "👊"
        ),
        FolkStep(
            id = "diab_salto",
            name = "Paso del Diablo Saltón",
            danceType = "Diablada",
            description = "Fascinante salto que simula a las huestes del Tío saltando de roca en roca en el averno. Elevaciones atléticas cruzando los pies y sacudiendo el tridente con asombrosa soberbia.",
            movementInstruction = "1. Haz un pre-brinco sobre el pie izquierdo. 2. Salta cruzando las piernas in el aire. 3. Eleva el brazo al tope agitando el pañuelo o tridente.",
            rhythmCount = "¡Salto! ... Cruzo ... Cae",
            difficulty = "Avanzado",
            animationEmoji = "👹"
        ),
        FolkStep(
            id = "diab_cruce",
            name = "El Cruce del Ángel de Luz",
            danceType = "Diablada",
            description = "Elegante paso de coreografía donde los bailarines cruzan filas, inspirados en el Arcángel San Miguel mandando la retirada de los pecados capitales.",
            movementInstruction = "1. Avanza en zigzag alternando brincos ligeros de puntitas. 2. Extiende los brazos horizontalmente como si fueran las alas celestiales.",
            rhythmCount = "Un-Dos-Tres-Cambio",
            difficulty = "Básico",
            animationEmoji = "👼"
        ),
        FolkStep(
            id = "cue_cepillado_tar",
            name = "El Cepillado Tarijeño con Pañuelo",
            danceType = "Cueca Tarijeña",
            description = "Paso de cortejo súper alegre y pícaro. El bailarín cepilla el suelo con giros rápidos del pie de adelante y del pañuelo al aire, de carácter sumamente festivo.",
            movementInstruction = "1. Apoya el metatarso y arrástralo rítmicamente dibujando medias lunas. 2. Mueve el pañuelo chapaco de forma circular sobre el nivel de los ojos.",
            rhythmCount = "Rápi-do... Rápi-do",
            difficulty = "Intermedio",
            animationEmoji = "🧣"
        ),
        FolkStep(
            id = "cue_rueda_tar",
            name = "La Vuelta de Rueda Chapaca",
            danceType = "Cueca Tarijeña",
            description = "Un paso grupal o en pareja donde bailan en círculo cerrado cantando coplas, denotando la hermandad del fértil valle tarijeño.",
            movementInstruction = "1. Toma de la mano indirectamente a tu pareja alzando el pañuelo. 2. Da pasos trotaditos rápidos siguiendo el compás del violín local.",
            rhythmCount = "1, 2, 3, Gira!",
            difficulty = "Básico",
            animationEmoji = "🎡"
        ),
        FolkStep(
            id = "cue_cepillado_coch",
            name = "El Cepillado de Galanteo Cochabambino",
            danceType = "Cochabambina",
            description = "La cueca cochabambina es más pausada y expresiva. El cepillado se hace con profunda distinción y cortejo tierno entre dama y caballero.",
            movementInstruction = "1. Cepilla delicadamente el pie de adelante rozando el zapato. 2. Sostén el pañuelo con sutil galantería a nivel del pecho del compañero.",
            rhythmCount = "Suave... Pausado... Listo",
            difficulty = "Intermedio",
            animationEmoji = "🌸"
        ),
        FolkStep(
            id = "cue_zapateo_coch",
            name = "El Zapateado Fuerte Final",
            danceType = "Cochabambina",
            description = "El final apoteósico ('La Segundita') en la Cueca Cochabambina donde se descarga toda el alma para sellar el amor.",
            movementInstruction = "1. Intercala golpes de talón rítmicos. 2. Cruza con rapidez los pañuelos en todo lo alto y realiza un zapateado de remate con palmadas coordinadas.",
            rhythmCount = "¡Zapa-tea-cochalo!",
            difficulty = "Avanzado",
            animationEmoji = "🔨"
        ),
        FolkStep(
            id = "cha_escobillado",
            name = "El Escobillado Chaqueño",
            danceType = "Chaqueña",
            description = "Paso estrella de la Cueca Chaqueña y Chacarera. Pies veloces que cepillan hacia atrás y adelante rozando el suelo arenoso con agilidad inigualable.",
            movementInstruction = "1. Planta el pie izquierdo fijándolo como pivote. 2. Cepilla de forma rítmica con el empeine del derecho dibujando rápidos arcos flotantes.",
            rhythmCount = "Un-Dos-Tres-Cuatro-Cinco",
            difficulty = "Avanzado",
            animationEmoji = "🔥"
        ),
        FolkStep(
            id = "cha_repique",
            name = "El Repique con Bota de Potro",
            danceType = "Chaqueña",
            description = "Zapateo recio del bailarín del Chaco que busca lucirse. Golpazos del taco y de punta, demostrando hombría de los gauchos bolivianos de Tarija.",
            movementInstruction = "1. Golpea con fuerza extrema con espuela o talón. 2. Sincroniza un veloz golpe cruzado del metatarso. 3. Sostén tus manos sobre el cinturón chaqueño.",
            rhythmCount = "¡TAC! ... ¡PUM! ... ¡TAC!",
            difficulty = "Avanzado",
            animationEmoji = "👢"
        ),
        FolkStep(
            id = "cue_quimba_pac",
            name = "La Quimba Paceña de Salón",
            danceType = "Cueca Paceña",
            description = "El momento de máxima sensualidad y timidez de la cueca de salón de La Paz. Consiste en movimientos pausados inclinando el hombro y bajando levemente el pañuelo de forma sutil.",
            movementInstruction = "1. Avanza casi flotando. 2. Ladea el hombro derecho de la dama hacia abajo. 3. Mira discretamente al suelo rindiendo tributo a la elegancia paceña.",
            rhythmCount = "Lento-Dulce-Cadencia",
            difficulty = "Intermedio",
            animationEmoji = "🎩"
        ),
        FolkStep(
            id = "cue_sombrero_pac",
            name = "El Saludo de Sombrero de Copa",
            danceType = "Cueca Paceña",
            description = "Gesto teatral suntuoso del cholo paceño sacándose el elegante sombrero borsalino o de copa para rendir un saludo devoto a su apreciada cholita.",
            movementInstruction = "1. Detén el paso cepillando suavemente. 2. Quítate el sombrero con un arco suave de la mano derecha. 3. Haz una reverencia delicada.",
            rhythmCount = "Saludo... Reverencia... Sigo",
            difficulty = "Básico",
            animationEmoji = "👒"
        ),
        FolkStep(
            id = "mal_repique",
            name = "El Repique de Malambo de Fuego",
            danceType = "Malambo",
            description = "Un zapateado de altísima intensidad rítmica proveniente del sur boliviano de influencia tarijeña y chaqueña. Es destreza purísima y resistencia.",
            movementInstruction = "1. Golpea alternando punta, planta y talón a velocidades de ráfaga metálica. 2. Mantén los brazos cruzados detrás con rígido orgullo gaucho.",
            rhythmCount = "¡Tac-ta-ca-tac-ta-ca-tac!",
            difficulty = "Avanzado",
            animationEmoji = "👞"
        ),
        FolkStep(
            id = "tund_ritmo",
            name = "Paso Rítmico Tundiki Afro",
            danceType = "Tundiki",
            description = "Paso cadencioso que rinde homenaje a los ritmos traídos por las poblaciones afrobolivianas. Pasos semitrotados con flexión pronunciada de rodillas.",
            movementInstruction = "1. Dobla tus rodillas inclinando el centro de gravedad. 2. Avanza con pasos cortos intercalados levantando las palmas pidiendo bendición.",
            rhythmCount = "Tundiqui-tundiqui-PAM",
            difficulty = "Básico",
            animationEmoji = "🥁"
        ),
        FolkStep(
            id = "pot_salto_charango",
            name = "El Salto del Charango Potolos",
            danceType = "Potolos",
            description = "Paso representative de Potolos. Se brinca imitando las travesuras de los pobladores andinos buscando llamar la atención amorosa de la dama de pollera.",
            movementInstruction = "1. Junta los pies encogiendo los codos. 2. Salta alternando piernas replegando de costado el sombrero en un juego divertido de desequilibrio controlado.",
            rhythmCount = "¡Salta! ... ¡Salta! ... ¡Gira!",
            difficulty = "Intermedio",
            animationEmoji = "👒"
        ),
        FolkStep(
            id = "pot_cadera",
            name = "El Sacudidor de Cadera y Titi",
            danceType = "Potolos",
            description = "Movimiento picaresco en Potolos donde los bailarines se sacuden cómicamente, moviendo las caderas cubiertas por la faja tejida tradicional de lana.",
            movementInstruction = "1. Separa los pies apoyándote rítmicamente en los talones. 2. Balancea rítmicamente las caderas hacia atrás emulando el jugueteo andino.",
            rhythmCount = "Chis-Chas-Chis-Chas",
            difficulty = "Básico",
            animationEmoji = "💃"
        ),
        FolkStep(
            id = "sal_zapateo_rap",
            name = "El Zapateo Ultra-Veloz del Salay",
            danceType = "Salay",
            description = "Frenesí de piernas. Se zapatea levantando los tacones de los zapatos de forma alternada, con un ritmo vertiginoso propio del valle de Cochabamba.",
            movementInstruction = "1. Flexiona cadera y rodillas rápidamente. 2. Golpea con las puntas cruzándolas de adentro hacia afuera de forma ágil y coordinada.",
            rhythmCount = "Rápido-Rápido-¡Zapatea!",
            difficulty = "Avanzado",
            animationEmoji = "🏃"
        ),
        FolkStep(
            id = "sal_cepillado_coq",
            name = "El Cepillado Cruzado del Salay",
            danceType = "Salay",
            description = "Paso de transición en el Salay donde el caballero o dama 'coquetean' en semicírculos, rozando el calzado de forma ágil.",
            movementInstruction = "1. Gira un semicírculo sobre puntas de zapato. 2. Cepilla el asfalto de forma vigorosa con el taco hacia adelante intercambiando hombro.",
            rhythmCount = "Coqueteo-Cepillo-Listo",
            difficulty = "Intermedio",
            animationEmoji = "👟"
        ),
        FolkStep(
            id = "jal_salto",
            name = "Salto del Jalq\'a",
            danceType = "Jalkas",
            description = "Danza originaria del norte del departamento de Potosí. Es un salto ceremonioso alternando pies mientras se viste la almilla bordada.",
            movementInstruction = "1. Da un leve impulso. 2. Salta balanceando los brazos hacia adelante. 3. Cae con los pies semiexpandidos.",
            rhythmCount = "¡Salta! - ¡Toca! - Dos",
            difficulty = "Intermedio",
            animationEmoji = "🦅"
        ),
        FolkStep(
            id = "phu_marchar",
            name = "La Marcha Sagrada Phuna",
            danceType = "Phunas",
            description = "Ritmo ancestral de carácter sutil en el que se avanza tocando flautas típicas de la serranía andina con paso coordinado y marcha solemne.",
            movementInstruction = "1. Eleva el pie derecho levemente. 2. Da el paso mientras ladeas tu torso. 3. Sostén imaginariamente el instrumento de viento.",
            rhythmCount = "Tan - Tan - Pa",
            difficulty = "Básico",
            animationEmoji = "🌾"
        ),
        FolkStep(
            id = "tob_salto",
            name = "El Salto del Jaguar Tobas",
            danceType = "Tobas",
            description = "Increíbles saltos atléticos que imitan los movimientos de los cazadores y felinos de la cuenca amazónica y chaqueña boliviana.",
            movementInstruction = "1. Flexiona las rodillas. 2. Pega un salto alto cruzando las piernas en el aire. 3. Extiende los brazos con lanzas rítmicamente.",
            rhythmCount = "¡Brinco! ... ¡Dos! ... ¡Cae!",
            difficulty = "Avanzado",
            animationEmoji = "🏹"
        ),
        FolkStep(
            id = "sur_marcha",
            name = "Paso Planchado del Suri Sicuri",
            danceType = "Suri sicuri",
            description = "Simula el elegante caminar del avestruz andino (el Suri). Movimiento lento con grandes giros mostrando la montera gigante de plumas hermosas.",
            movementInstruction = "1. Camina a paso lento deslizando la suela del zapato. 2. Da un giro suave extendiendo los brazos a los costados.",
            rhythmCount = "Giro... Pausado... Avanzo",
            difficulty = "Básico",
            animationEmoji = "🪶"
        ),
        FolkStep(
            id = "wit_giro",
            name = "El Vuelo Alegre Wititi",
            danceType = "Wititis",
            description = "Hermosa danza del amor. Giros rápidos donde el bailarín gira agitando la colorida falda tradicional para esquivar los azotes rituales.",
            movementInstruction = "1. Realiza giros con el pie de apoyo. 2. Alza la pollera/falda a la altura de la rodilla con un movimiento alegre de cadera.",
            rhythmCount = "¡Gira, gira, Wititi!",
            difficulty = "Intermedio",
            animationEmoji = "🍥"
        ),
        FolkStep(
            id = "puj_trote",
            name = "La Marcha de Espuelas Pujllay",
            danceType = "Pujllay",
            description = "Marcha imponente calzando altísimas ojotas o zuecos de madera (con espuelas metálicas ruidosas) para celebrar la gran victoria de Tarabuco.",
            movementInstruction = "1. Levanta la pesada ojota de madera de 10 cm dintelando la pierna. 2. Pisa con fuerza tremenda en seco. 3. Sincroniza el sonido metálico de las espuelas.",
            rhythmCount = "¡TAN! ... ¡Clank! ... ¡TAN!",
            difficulty = "Avanzado",
            animationEmoji = "🎖️"
        ),
        FolkStep(
            id = "sam_cortejo",
            name = "El Paseo Elegante del Pañuelo Samba",
            danceType = "Samba argentina",
            description = "Paso de cortejo folclórico sureño e hispano-andino donde se dibuja un ocho imaginario en el aire con un pañuelo de seda blanco.",
            movementInstruction = "1. Sostén el pañuelo arriba de tu cabeza. 2. Avanza con pasitos cruzados cortos arrastrando el pie interno.",
            rhythmCount = "Uno - Dos - Ocho",
            difficulty = "Intermedio",
            animationEmoji = "🤍"
        ),
        FolkStep(
            id = "say_cadencia",
            name = "La Cadencia Afro Saya",
            danceType = "Saya",
            description = "Ritmo y cadencia africana del Yungas paceño. Movimientos ondulantes marcados por el sonar de la caja mayor y los cantos rítmicos correlativos.",
            movementInstruction = "1. Da un paso al lado ladeando la cadera. 2. Sacude rítmicamente los hombros con golpes sutiles. 3. Canta en coro con soltura.",
            rhythmCount = "TUM - TUM - SAYA - SAYA",
            difficulty = "Intermedio",
            animationEmoji = "🥁"
        ),
        FolkStep(
            id = "wak_embiste",
            name = "La Embestida del Torito Waka Waka",
            danceType = "Waka waka",
            description = "Paso picaresco y satírico. El bailarín, revestido con un cuerpo de toro hecho de cuero rígido, embiste humorísticamente a las cholas.",
            movementInstruction = "1. Toma los mangos laterales del torito. 2. Da pequeños trotes inclinando la cabeza simulando cornadas traviesas.",
            rhythmCount = "¡Brinco! ... ¡Toro!",
            difficulty = "Básico",
            animationEmoji = "🐂"
        ),
        FolkStep(
            id = "chu_zapateo",
            name = "Zapateo Sarcástico del Chuta",
            danceType = "Chutas",
            description = "Danza súper alegre y carnavalera de La Paz. Consiste en saltos veloces de pies juntos imitando de forma satírica y alegre al patrón colonial.",
            movementInstruction = "1. Junta tus talones. 2. Brinca de lado moviendo exageradamente las caderas sujetando la mano de tu pareja con euforia.",
            rhythmCount = "¡Brinca chuta, brinca!",
            difficulty = "Intermedio",
            animationEmoji = "👺"
        ),
        FolkStep(
            id = "lla_arreo",
            name = "El Arreo de la Llama con Honda",
            danceType = "Llamerada",
            description = "Simula el pastoreo de las llamas en las altas cumbres. Se avanza con paso ligero haciendo girar la korawa (honda de lana) al cielo.",
            movementInstruction = "1. Da pequeños saltos alternados de metatarso. 2. Gira tu honda sobre la cabeza rítmicamente al son de la melodía de bronce.",
            rhythmCount = "Arrea... Dos... Tres",
            difficulty = "Básico",
            animationEmoji = "🦙"
        ),
        FolkStep(
            id = "kul_hilado",
            name = "El Paso de la Hilandera Kullawa",
            danceType = "Kullawada",
            description = "Simula el laborioso hilado de prendas tradicionales del altiplano con garbo excelsior y profunda elegancia de alcurnia aymara.",
            movementInstruction = "1. Da pasos ladeados elegantes cruzando un pie. 2. Mueve tus manos simulando girar una rueca o sostener un largo hilo.",
            rhythmCount = "Hilando... Giro... Pasito",
            difficulty = "Básico",
            animationEmoji = "🧶"
        ),
        FolkStep(
            id = "mod_pasarela",
            name = "Pasarela Elegante de la Chola",
            danceType = "Modelada",
            description = "Danza y desfile estilizado que resalta la elegancia y la finura de la Chola Paceña y de las distintas danzas de alta costura folclórica.",
            movementInstruction = "1. Camina a paso lento pausado erguida. 2. Mueve los flecos de la lujosa manta de vicuña con giros imperiales de cadera.",
            rhythmCount = "Garbo... Elegancia... Giro",
            difficulty = "Básico",
            animationEmoji = "✨"
        ),
        FolkStep(
            id = "tak_salto",
            name = "Brinco del Oriente Taquirari",
            danceType = "Takirari",
            description = "El ritmo más popular del oriente cruceño y beniano, de paso saltado alegre y contorneado que contagia jolgorio instantáneamente.",
            movementInstruction = "1. Salta sobre un pie. 2. Flexiona y apoya el metatarso con un alegre péndulo de caderas con los brazos extendidos.",
            rhythmCount = "Pasito-Salto-Y-Giro",
            difficulty = "Básico",
            animationEmoji = "🌴"
        ),
        FolkStep(
            id = "bri_salto",
            name = "El Brincao Oriental Cruzado",
            danceType = "Brincao",
            description = "Vibrante paso tropical de saltos ágiles de un lado a otro con un ritmo acelerado que representa vitalidad y fiesta selvática.",
            movementInstruction = "1. Salta con pies juntos. 2. Abre velozmente las piernas cayendo con ligereza. 3. Mueve tus hombros con gozo.",
            rhythmCount = "¡Salto! ¡Abro! ¡Cierro!",
            difficulty = "Intermedio",
            animationEmoji = "🦜"
        ),
        FolkStep(
            id = "mac_guerra",
            name = "La Ofrenda de la Victoria Macheteros",
            danceType = "Macheteros",
            description = "Danza beniana de los guerreros de luz de Moxos, portando un machete de madera y un inmenso tocado de plumas de paraba imitando el sol naciente.",
            movementInstruction = "1. Da pasos solemnes de marcha pausada agachando el lomo rítmicamente. 2. Haz una profunda reverencia sagrada elevando el machete de madera.",
            rhythmCount = "Paso... Saludo... Sigo",
            difficulty = "Intermedio",
            animationEmoji = "☀️"
        )
    )

    // Current State for playing item in the Dictionary
    var activeDictionaryStepId by mutableStateOf("cap_salto")
    var dictionaryVideoIsPlaying by mutableStateOf(false)
    var dictionaryVideoProgress by mutableStateOf(0.15f)

    // Traditional Audio Tracks data and player state
    var currentPlayingTrackIndex by mutableStateOf(0)
    var isAudioPlaying by mutableStateOf(false)
    var audioProgress by mutableStateOf(0.0f)

    val audioTracks = listOf(
        FolkAudioTrack("aud_cap", "Caporales de Corazón", "Caporales", "Los Kjarkas", "3:42", "Sincopado enérgico con charango y vientos de bronce."),
        FolkAudioTrack("aud_mor", "La Aromeñita (Clásico)", "Morenada", "Banda Real Imperial", "4:15", "Marcha lenta señorial con repique de pesadas matracas."),
        FolkAudioTrack("aud_tin", "Tinku Jallalla", "Tinku", "K\'ala Marka", "3:10", "Fuerza telúrica andina con rítmicos charangos veloces."),
        FolkAudioTrack("aud_dia", "Diablada del Socavón", "Diablada", "Banda Pagador de Oruro", "3:30", "Saltos soberbios con platillos y trompetas del Oruro."),
        FolkAudioTrack("aud_coch", "Cochabambinita de Oro", "Cochabambina", "Tupay", "3:25", "Cueca romántica de los valles con mandolinas criollas."),
        FolkAudioTrack("aud_tar", "La Caraqueña (Clásica)", "Cueca Tarijeña", "Nilo Soruco", "3:02", "Pícara cueca saltada con violines chapacos alegres."),
        FolkAudioTrack("aud_cha", "Chacarera de la Sierra", "Chaqueña", "El Chaqueño Palavecino", "2:50", "Violines veloces y escobillado chaqueño tradicional."),
        FolkAudioTrack("aud_pac", "Amor Paceño", "Paceña", "Los Peregrinos", "3:12", "Elegante cueca de salón de compás pausado y señorial."),
        FolkAudioTrack("aud_mal", "Malambo de las Pampas", "Malambo", "Tradicional Chaqueño", "3:35", "Vigorosa y vertiginosa guitarra rítmica criolla."),
        FolkAudioTrack("aud_tun", "Tundiki Tradicional", "Tundiki", "Afroboliviano Tradicional", "2:55", "Percusión con bombo de raíces afrobolivianas directas."),
        FolkAudioTrack("aud_pot", "El Brinco de Potolos", "Potolos", "Grupo Tupay", "2:40", "Charango andino juguetón e imitación de travesuras."),
        FolkAudioTrack("aud_sal", "Salay de mis Amores", "Salay", "Grupo Proyección", "3:05", "Zapateo ultra veloz de los valles de Cochabamba."),
        FolkAudioTrack("aud_jal", "Vientos de Jalq'a", "Jalkas", "Música Autóctona del Potosí", "3:20", "Místicos soplos de quenas y zampoñas ancestrales."),
        FolkAudioTrack("aud_phu", "Soplo Sagrado Phuna", "Phunas", "Cantares de la Sierra", "2:35", "Marchar ceremonioso de flautas típicas phunas."),
        FolkAudioTrack("aud_tob", "Fuerza y Compás Jaguar", "Tobas", "Grupo Alaxpacha", "3:40", "Chontas y percusiones enérgicas de la selva virgen."),
        FolkAudioTrack("aud_sur", "Sicuris del Suri", "Suri sicuri", "Grupo Awatiñas", "3:18", "Aire místico de zampoñada en ronda ceremonial."),
        FolkAudioTrack("aud_wit", "Giro de Amor Wititi", "Wititis", "Grupo Llajtaymanta", "2:50", "Ritmo de cortejo dulce y picaresco con vientos."),
        FolkAudioTrack("aud_puj", "Sonidos del Pujllay Tarabuco", "Pujllay", "Tarabuco Auténtico", "4:00", "Sonido rítmico y seco de tockos (ojotas de madera)."),
        FolkAudioTrack("aud_sam", "Samba de mi Esperanza", "Samba argentina", "Tradicional Gaucho", "3:45", "Guitarra pausada y nostálgica de pañuelo libre."),
        FolkAudioTrack("aud_say", "Saya del Yungas original", "Saya", "Saya Afroboliviana", "3:15", "Compás continuo de cajas mayores y coros rítmicos."),
        FolkAudioTrack("aud_wak", "Waka Tokori con Trompeta", "Waka waka", "Los Norteños", "2:45", "Trombones, platillos y pasos pícaros de embestida."),
        FolkAudioTrack("aud_chu", "Chuta Paceño Cholero", "Chutas", "Chutas de Caquiaviri", "2:30", "Fuerza y algarabía desatada del carnaval paceño."),
        FolkAudioTrack("aud_lla", "Llamero Hermoso", "Llamerada", "Grupo Kalamarka", "3:08", "Marchar con campanas y quenas de los pastores altiplánicos."),
        FolkAudioTrack("aud_kul", "Hilanderas de Kullawa", "Kullawada", "Grupo Savia Andina", "3:10", "Giro con bronce y mandolinas de las hilanderas."),
        FolkAudioTrack("aud_mod", "Moreno de Pasarela", "Modelada", "Morenada Real de La Paz", "3:50", "Gala y elegancia suntuosa sobre el escenario."),
        FolkAudioTrack("aud_tak", "Cunumi Oriental", "Takirari", "Gladys Moreno", "3:00", "Bello taquirari cruceño con guitarras y flauta."),
        FolkAudioTrack("aud_bri", "Brincao del Chaco", "Brincao", "Trío Oriental", "2:20", "Vibrante ritmo cruceño tropical de panderos y timbal."),
        FolkAudioTrack("aud_mac", "Macheteros de San Ignacio", "Macheteros", "Ensamble Moxos", "4:10", "Solemne silbato, flauta rústica y cascabel de semillas.")
    )

    fun toggleAudioPlay() {
        isAudioPlaying = !isAudioPlaying
    }

    fun selectAudioTrack(index: Int) {
        if (index in audioTracks.indices) {
            currentPlayingTrackIndex = index
            audioProgress = 0.0f
            isAudioPlaying = true
        }
    }

    fun nextAudioTrack() {
        currentPlayingTrackIndex = (currentPlayingTrackIndex + 1) % audioTracks.size
        audioProgress = 0.0f
    }

    fun prevAudioTrack() {
        currentPlayingTrackIndex = if (currentPlayingTrackIndex - 1 < 0) audioTracks.size - 1 else currentPlayingTrackIndex - 1
        audioProgress = 0.0f
    }

    // Flow of favorited step IDs loaded from database
    val favoriteStepIds: StateFlow<List<String>> = repository.favoriteSteps
        .map { favoriteList -> favoriteList.map { it.stepId } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Active bottom navigation tab selection
    var currentTab by mutableStateOf(FolkTab.INICIO)

    // User Profile in-memory state (points, streak, level, customizable avatar)
    var userPoints by mutableStateOf(450)
    var userStreak by mutableStateOf(5) // 5 days streak
    var userExperienceLevel by mutableStateOf(FolkLevel.INTERMEDIO)
    var isDailyClaimed by mutableStateOf(false)

    // Dynamic color theme state chosen by the user
    var activeAppTheme by mutableStateOf(FolkThemeColor.BOLIVIA)
    var isDarkMode by mutableStateOf(true) // Default to dark mode for theaters or dark rehearsal halls

    fun changeAppTheme(theme: FolkThemeColor) {
        activeAppTheme = theme
    }

    fun toggleDarkLightMode() {
        isDarkMode = !isDarkMode
    }

    // Avatar Customizable Settings
    var avatarHatType by mutableStateOf("Montera de Tinku") // Options: "Montera de Tinku", "Sombrero Caporal", "Chura Diablada", "Ninguno"
    var avatarOutfitColor by mutableStateOf("#E53935") // Active color chosen
    var avatarCapeEnabled by mutableStateOf(true)
    var avatarAccessoryType by mutableStateOf("Cascabeles") // Options: "Cascabeles", "Plumas", "Pañuelo", "Ninguno"

    // Room Persistent state flows (Flow mapped to StateFlow with subscription sharing)
    val costumeDesigns: StateFlow<List<CostumeDesign>> = repository.costumeDesigns
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val eventReminders: StateFlow<List<EventReminder>> = repository.eventReminders
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val practiceLogs: StateFlow<List<PracticeLog>> = repository.practiceLogs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val communityPosts: StateFlow<List<CommunityPost>> = repository.communityPosts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // UI Input fields for creating new events (Calendar)
    var newEventTitle by mutableStateOf("")
    var newEventType by mutableStateOf("Ensayo") // Ensayo, Presentación, Festival
    var newEventDateString by mutableStateOf("")
    var newEventTimeString by mutableStateOf("")
    var newEventDescription by mutableStateOf("")

    // UI Input fields for creating community posts
    var postInputMessage by mutableStateOf("")
    var postInputDanceLabel by mutableStateOf("Caporales")

    // Active post being commented on (if any)
    var activeCommentingPostId by mutableStateOf<Int?>(null)
    var commentInputText by mutableStateOf("")
    private val _postComments = MutableStateFlow<List<PostComment>>(emptyList())
    val postComments = _postComments.asStateFlow()

    // Costume virtual sewing design board dynamic states
    var designTitle by mutableStateOf("Traje Carnaval Real")
    var designDanceType by mutableStateOf("Caporales")
    var designPrimaryColorHex by mutableStateOf("#E53935")
    var designSecondaryColorHex by mutableStateOf("#FFFFB300")
    var designAccentColorHex by mutableStateOf("#43A047")
    var designHatType by mutableStateOf("Sombrero de Lujo")
    var designAccessoryType by mutableStateOf("Cascabeles Brillantes")

    // Video Player Simulator State
    var simulatedPlayingDanceIndex by mutableStateOf(0) // active index in dance tutorial database
    var isSimulatedVideoPlaying by mutableStateOf(false)
    var simulatedPlaybackProgress by mutableStateOf(0.4f) // timer percentage
    var selectedTutorialLevel by mutableStateOf("Básico") // Básico, Intermedio, Avanzado

    // Training state and countdown timer for dynamic practice
    var isPracticeTimerActive by mutableStateOf(false)
    var practiceTimerSecondsLeft by mutableStateOf(60) // 1 minute simulation practice
    var activePracticeDanceName by mutableStateOf("Caporales")
    var practiceProgressMessage by mutableStateOf("")

    // Trivia Game Minigame States
    val triviaQuestions = listOf(
        TriviaQuestion(
            question = "¿De qué departamento boliviano es originaria la danza ancestral del Tinku?",
            options = listOf("La Paz", "Potosí", "Santa Cruz", "Tarija"),
            correctIndex = 1,
            explanation = "El Tinku es un encuentro ritual originario de la región del norte de Potosí, donde las comunidades se reúnen para ofrendar energía a la Pachamama."
        ),
        TriviaQuestion(
            question = "¿Qué elemento y sonido representan los cascabeles en las botas del Caporal?",
            options = listOf("Las nubes y truenos", "El sonido del viento", "El golpeo de las cadenas de los esclavos", "Piedras preciosas rodando"),
            correctIndex = 2,
            explanation = "Representan el sonido metálico de los grilletes y las cadenas que arrastraban los africanos esclavizados durante la época colonial en las minas."
        ),
        TriviaQuestion(
            question = "¿Cuál es la emblemática danza de galanteo boliviana donde el uso sutil del pañuelo es imprescindible?",
            options = listOf("Saya Afroboliviana", "La Cueca", "Kullawada", "Diablada de Oruro"),
            correctIndex = 1,
            explanation = "La Cueca es la danza nacional de cortejo por excelencia en Bolivia, donde los bailarines se comunican elegantemente agitando un pañuelo blanco."
        ),
        TriviaQuestion(
            question = "¿Qué simulan las coreografías y brincos de la vibrante danza de los Tobas?",
            options = listOf("El pastoreo de vicuñas", "La pisada de uvas coloniales", "Movimientos de caza y combate de guerreros amazónicos", "La pesca en el lago Titicaca"),
            correctIndex = 2,
            explanation = "Tobas simula con saltos ágiles y asombrosas acrobacias la cacería de las valientes tribus selváticas del Chaco boliviano."
        ),
        TriviaQuestion(
            question = "¿En qué año fue declarada la Diablada de Oruro y su carnaval como Obra Maestra por la UNESCO?",
            options = listOf("1995", "2001", "2010", "2018"),
            correctIndex = 1,
            explanation = "El Carnaval de Oruro fue proclamado Obra Maestra del Patrimonio Oral e Intangible de la Humanidad por la UNESCO en mayo de 2001."
        )
    )

    var currentTriviaQuestionIndex by mutableStateOf(0)
    var triviaSelectedOptionIndex by mutableStateOf<Int?>(null)
    var isTriviaAnswered by mutableStateOf(false)
    var triviaPointsEarned by mutableStateOf(0)
    var isTriviaFinished by mutableStateOf(false)

    // Game Selection
    var activeMinigameType by mutableStateOf("TRIVIA") // TRIVIA, ADIVINA, TRAJES, RITMO

    // 1. "Adivina la Danza" Game States
    val guessQuestions = listOf(
        GuessQuestion(
            description = "Consiste en un zapateo ultra veloz originario de los valles cochabambinos, caracterizado por la coquetería impecable, cepillado ágil con calzado ligero y coqueteo de pareja enérgico.",
            options = listOf("Salay", "Morenada", "Tinku", "Malambo"),
            correctIndex = 0,
            hint = "Se asocia fuertemente con el zapateado cochabambino con trajes de colores encendidos sobre el escenario."
        ),
        GuessQuestion(
            description = "Fusión rítmica urbana inspirada en el capataz mayor del periodo colonial. Destaca por saltos atléticos impresionantes, sacudidas de hombros coordinadas y cascabeles de bronce pesados.",
            options = listOf("Tundiki", "Caporales", "Morenada", "Potolos"),
            correctIndex = 1,
            hint = "El bailarín lleva un sombrero de solapa ancha y una bota alta con espuelas."
        ),
        GuessQuestion(
            description = "Bailarín con pasos semitrotados inclinados tocando el charango y haciendo graciosas travesuras brincando de lado para conquistar a la cholita de pollera.",
            options = listOf("Cueca Cochabambina", "Salay", "Potolos", "Tundiki"),
            correctIndex = 2,
            hint = "Imita los movimientos juguetones del agua y de la fauna con un sombrero cónico."
        ),
        GuessQuestion(
            description = "Encuentro y combate ritual de fuerza proveniente del Norte de Potosí. Es un marchar cargado de vitalidad telúrica extrema, con patadas rítmicas profundas al suelo.",
            options = listOf("Cueca Tarijeña", "Morenada", "Tinku", "Diablada"),
            correctIndex = 2,
            hint = "Hacen ofrendas con los puños cubiertos de cuero para apaciguar a la Pachamama."
        )
    )

    var currentGuessQuestionIndex by mutableStateOf(0)
    var guessSelectedOptionIndex by mutableStateOf<Int?>(null)
    var isGuessAnswered by mutableStateOf(false)
    var guessPointsEarned by mutableStateOf(0)
    var isGuessFinished by mutableStateOf(false)

    // 2. "Relaciona el traje con la danza" Game States
    val matchQuestions = listOf(
        MatchQuestion(
            costumeName = "La Montera de Toro Rígida",
            costumeDetails = "Hecha de cuero fuerte repujado con plumas altas de paraba multicolores, llicllas tejidas de lana de oveja multicolor y chalecos de cuero.",
            options = listOf("Morenada", "Tinku", "Salay", "Malambo"),
            correctIndex = 1,
            emoji = "🛡️"
        ),
        MatchQuestion(
            costumeName = "Cascabeles de Bronce al Taco",
            costumeDetails = "Botas altas de cuero de gran calidad con decenas de ruidosos cascabeles que suenan con cada salto vigoroso y sacudimiento.",
            options = listOf("Cueca Tarijeña", "Diablada", "Caporales", "Tundiki"),
            correctIndex = 2,
            emoji = "🔔"
        ),
        MatchQuestion(
            costumeName = "Pesada Máscara de Yeso con Sierpes",
            costumeDetails = "Ojos globulares destellantes, cuernos enroscados, pechera bordada con hilos de oro y plata fina deslumbrante, y un tridente metálico.",
            options = listOf("Morenada", "Diablada", "Tobas", "Caporales"),
            correctIndex = 1,
            emoji = "👹"
        ),
        MatchQuestion(
            costumeName = "Pollerines de Moreno y Matraca de Madera",
            costumeDetails = "Trajes bordados con perlas que pesan más de 15 kilos simulan lingotes de plata, complementados con matracas talladas a mano con formas peculiares.",
            options = listOf("Morenada", "Tobas", "Salay", "Llamerada"),
            correctIndex = 0,
            emoji = "⚖️"
        )
    )

    var currentMatchQuestionIndex by mutableStateOf(0)
    var matchSelectedOptionIndex by mutableStateOf<Int?>(null)
    var isMatchAnswered by mutableStateOf(false)
    var matchPointsEarned by mutableStateOf(0)
    var isMatchFinished by mutableStateOf(false)

    // 3. "Completa el ritmo musical" Game States
    val rhythmQuestions = listOf(
        RhythmQuestion(
            danceName = "Caporales",
            sequence = "Y - Uno - Y - [ ? ]",
            missingPartHint = "Ritmo sincopado de compás de bombo y sacudida de cascabeles vibrantes.",
            options = listOf("Tres", "Dos", "Giro", "Salto"),
            correctIndex = 1,
            soundEffectDescription = "¡BOM! ... ¡CRASH! ... ¡BOM! ... ¡CRASH!"
        ),
        RhythmQuestion(
            danceName = "Morenada",
            sequence = "Tan ... Tan ... [ ? ]",
            missingPartHint = "Marcha pesada señorial dictada por el compás estricto de los tamboriles y matracas sordas de madera.",
            options = listOf("¡PAM!", "¡ZAS!", "¡TRAC!", "¡BOOM!"),
            correctIndex = 0,
            soundEffectDescription = "¡Tac... tac... PAM! ¡Tac... tac... PAM!"
        ),
        RhythmQuestion(
            danceName = "Tundiki",
            sequence = "Tundiqui - tundiqui - [ ? ]",
            missingPartHint = "Gesta rítmica afroboliviana sincopada con percursión de tambor alegre constante de bombos medianos.",
            options = listOf("¡PAM!", "¡TINKU!", "¡GOLPE!", "¡CASCABEL!"),
            correctIndex = 0,
            soundEffectDescription = "¡Tum-dum... Tum-dum... PAM!"
        ),
        RhythmQuestion(
            danceName = "Salay",
            sequence = "Rápido - Rápido - [ ? ]",
            missingPartHint = "Zapateo ultra alegre de charango cochabambino con el palpitar vivo del bombo.",
            options = listOf("¡Giro!", "¡Zapatea!", "¡Quimba!", "¡Tridente!"),
            correctIndex = 1,
            soundEffectDescription = "¡Kiti-plap... Kiti-plap... ¡ZAPATEA!"
        )
    )

    var currentRhythmQuestionIndex by mutableStateOf(0)
    var rhythmSelectedOptionIndex by mutableStateOf<Int?>(null)
    var isRhythmAnswered by mutableStateOf(false)
    var rhythmPointsEarned by mutableStateOf(0)
    var isRhythmFinished by mutableStateOf(false)

    // Unlocked Achievements Checklist (computed based on user points & state)
    val badgesList = listOf(
        "Novato del Carnaval",
        "Diseñador de Trajes",
        "Explorador Cultural",
        "Ritmo Conquistador",
        "Maestro del Stomp"
    )

    init {
        viewModelScope.launch {
            // Seed database with beautiful high-fidelity community posts if clean install
            repository.seedInitialPostsIfEmpty()
        }
        viewModelScope.launch {
            // Auto-increment audio progress or dictionary progress if playing
            while (true) {
                kotlinx.coroutines.delay(1000)
                if (isAudioPlaying) {
                    audioProgress += 0.05f
                    if (audioProgress >= 1.0f) {
                        audioProgress = 0.0f
                        nextAudioTrack()
                    }
                }
                if (dictionaryVideoIsPlaying) {
                    dictionaryVideoProgress += 0.1f
                    if (dictionaryVideoProgress >= 1.0f) {
                        dictionaryVideoProgress = 0.0f
                    }
                }
            }
        }
    }

    // Daily Claim Points Action
    fun claimDailyReward() {
        if (!isDailyClaimed) {
            userPoints += 50
            userStreak += 1
            isDailyClaimed = true
        }
    }

    // Practice Timer Operations
    fun startPracticeTimer(danceName: String) {
        if (!isPracticeTimerActive) {
            activePracticeDanceName = danceName
            isPracticeTimerActive = true
            practiceTimerSecondsLeft = 20 // 20-seconds condensed timer for fast UI interactivity
            practiceProgressMessage = "¡Calentando cuerpo!"

            viewModelScope.launch {
                while (practiceTimerSecondsLeft > 0 && isPracticeTimerActive) {
                    kotlinx.coroutines.delay(1000)
                    practiceTimerSecondsLeft--

                    if (practiceTimerSecondsLeft == 15) {
                        practiceProgressMessage = "¡Zapateando con fuerza!"
                    } else if (practiceTimerSecondsLeft == 8) {
                        practiceProgressMessage = "¡Ensayo coreográfico!"
                    } else if (practiceTimerSecondsLeft == 3) {
                        practiceProgressMessage = "¡Cierre espectacular!"
                    }
                }
                if (isPracticeTimerActive) {
                    completePracticeSession()
                }
            }
        }
    }

    fun stopPracticeTimer() {
        isPracticeTimerActive = false
        practiceProgressMessage = "Práctica cancelada."
    }

    private suspend fun completePracticeSession() {
        isPracticeTimerActive = false
        val earned = 30
        userPoints += earned
        // Add practice session log in Room!
        repository.addPracticeLog(
            PracticeLog(
                danceType = activePracticeDanceName,
                durationMinutes = 15,
                pointsEarned = earned
            )
        )
        practiceProgressMessage = "¡Excelente práctica de dancística! Ganaste +$earned puntos foklóricos."
    }

    // Costume Design Operations
    fun saveCurrentCostumeDesign() {
        viewModelScope.launch {
            repository.addCostumeDesign(
                CostumeDesign(
                    title = designTitle.ifBlank { "Diseño Sin Nombre" },
                    danceType = designDanceType,
                    primaryColorHex = designPrimaryColorHex,
                    secondaryColorHex = designSecondaryColorHex,
                    accentColorHex = designAccentColorHex,
                    hatType = designHatType,
                    accessoryType = designAccessoryType
                )
            )
            // Award bonus points for designing
            userPoints += 25
            // Reset fields
            designTitle = "Otro diseño de la pollera / traje"
        }
    }

    fun deleteCostumeDesign(design: CostumeDesign) {
        viewModelScope.launch {
            repository.removeCostumeDesign(design)
        }
    }

    // Calendar Reminder Operations
    fun saveEventReminder() {
        if (newEventTitle.isNotBlank() && newEventDateString.isNotBlank()) {
            viewModelScope.launch {
                repository.addEventReminder(
                    EventReminder(
                        title = newEventTitle,
                        eventType = newEventType,
                        dateString = newEventDateString,
                        timeString = newEventTimeString.ifBlank { "Todo el día" },
                        description = newEventDescription.ifBlank { "Sin descripción adicional" }
                    )
                )
                // Award organization points
                userPoints += 15
                // Reset inputs
                newEventTitle = ""
                newEventDateString = ""
                newEventTimeString = ""
                newEventDescription = ""
            }
        }
    }

    fun deleteEventReminder(event: EventReminder) {
        viewModelScope.launch {
            repository.removeEventReminder(event)
        }
    }

    // Community Posting & Comments
    fun selectActivePostComments(postId: Int) {
        activeCommentingPostId = postId
        viewModelScope.launch {
            repository.getCommentsForPost(postId).collect { comments ->
                _postComments.value = comments
            }
        }
    }

    fun addCommentToActivePost() {
        val postId = activeCommentingPostId ?: return
        if (commentInputText.isNotBlank()) {
            viewModelScope.launch {
                repository.addComment(
                    PostComment(
                        postId = postId,
                        authorName = "Tú (${avatarAccessoryType.take(8)})",
                        message = commentInputText
                    )
                )
                commentInputText = ""
                // Refresh flow manually
                repository.getCommentsForPost(postId).collect { comments ->
                    _postComments.value = comments
                }
            }
        }
    }

    fun createCommunityPost() {
        if (postInputMessage.isNotBlank()) {
            viewModelScope.launch {
                val authorLvl = when (userExperienceLevel) {
                    FolkLevel.BASICO -> "Básico"
                    FolkLevel.INTERMEDIO -> "Intermedio"
                    FolkLevel.AVANZADO -> "Avanzado"
                }
                repository.addCommunityPost(
                    CommunityPost(
                        authorName = "Bailarín Estrella",
                        authorLevel = authorLvl,
                        authorAvatarIndex = 4, // unique user avatar representation
                        message = postInputMessage,
                        danceLabel = postInputDanceLabel,
                        likesCount = 0,
                        userHasLiked = false
                    )
                )
                postInputMessage = ""
                userPoints += 20 // Community participation reward
            }
        }
    }

    fun toggleLikeOnPost(post: CommunityPost) {
        viewModelScope.launch {
            val updated = post.copy(
                userHasLiked = !post.userHasLiked,
                likesCount = if (post.userHasLiked) post.likesCount - 1 else post.likesCount + 1
            )
            repository.updateCommunityPost(updated)
        }
    }

    // Trivia Game Quiz Methods
    fun selectTriviaOption(optionIndex: Int) {
        if (!isTriviaAnswered) {
            triviaSelectedOptionIndex = optionIndex
            isTriviaAnswered = true
            val activeQuestion = triviaQuestions[currentTriviaQuestionIndex]
            if (optionIndex == activeQuestion.correctIndex) {
                triviaPointsEarned += 20
                userPoints += 20
            }
        }
    }

    fun nextTriviaQuestion() {
        if (currentTriviaQuestionIndex < triviaQuestions.size - 1) {
            currentTriviaQuestionIndex++
            triviaSelectedOptionIndex = null
            isTriviaAnswered = false
        } else {
            isTriviaFinished = true
        }
    }

    fun restartTriviaGame() {
        currentTriviaQuestionIndex = 0
        triviaSelectedOptionIndex = null
        isTriviaAnswered = false
        triviaPointsEarned = 0
        isTriviaFinished = false
    }

    // "Adivina la Danza" Methods
    fun selectGuessOption(optionIndex: Int) {
        if (!isGuessAnswered) {
            guessSelectedOptionIndex = optionIndex
            isGuessAnswered = true
            val activeQuestion = guessQuestions[currentGuessQuestionIndex]
            if (optionIndex == activeQuestion.correctIndex) {
                guessPointsEarned += 20
                userPoints += 20
            }
        }
    }

    fun nextGuessQuestion() {
        if (currentGuessQuestionIndex < guessQuestions.size - 1) {
            currentGuessQuestionIndex++
            guessSelectedOptionIndex = null
            isGuessAnswered = false
        } else {
            isGuessFinished = true
        }
    }

    fun restartGuessGame() {
        currentGuessQuestionIndex = 0
        guessSelectedOptionIndex = null
        isGuessAnswered = false
        guessPointsEarned = 0
        isGuessFinished = false
    }

    // "Relaciona el traje con la danza" Methods
    fun selectMatchOption(optionIndex: Int) {
        if (!isMatchAnswered) {
            matchSelectedOptionIndex = optionIndex
            isMatchAnswered = true
            val activeQuestion = matchQuestions[currentMatchQuestionIndex]
            if (optionIndex == activeQuestion.correctIndex) {
                matchPointsEarned += 20
                userPoints += 20
            }
        }
    }

    fun nextMatchQuestion() {
        if (currentMatchQuestionIndex < matchQuestions.size - 1) {
            currentMatchQuestionIndex++
            matchSelectedOptionIndex = null
            isMatchAnswered = false
        } else {
            isMatchFinished = true
        }
    }

    fun restartMatchGame() {
        currentMatchQuestionIndex = 0
        matchSelectedOptionIndex = null
        isMatchAnswered = false
        matchPointsEarned = 0
        isMatchFinished = false
    }

    // "Completa el ritmo musical" Methods
    fun selectRhythmOption(optionIndex: Int) {
        if (!isRhythmAnswered) {
            rhythmSelectedOptionIndex = optionIndex
            isRhythmAnswered = true
            val activeQuestion = rhythmQuestions[currentRhythmQuestionIndex]
            if (optionIndex == activeQuestion.correctIndex) {
                rhythmPointsEarned += 20
                userPoints += 20
            }
        }
    }

    fun nextRhythmQuestion() {
        if (currentRhythmQuestionIndex < rhythmQuestions.size - 1) {
            currentRhythmQuestionIndex++
            rhythmSelectedOptionIndex = null
            isRhythmAnswered = false
        } else {
            isRhythmFinished = true
        }
    }

    fun restartRhythmGame() {
        currentRhythmQuestionIndex = 0
        rhythmSelectedOptionIndex = null
        isRhythmAnswered = false
        rhythmPointsEarned = 0
        isRhythmFinished = false
    }

    // Toggle favorite dance step
    fun toggleFavoriteStep(stepId: String) {
        viewModelScope.launch {
            val favorites = favoriteStepIds.value
            if (favorites.contains(stepId)) {
                repository.removeFavoriteStep(stepId)
            } else {
                repository.addFavoriteStep(stepId)
            }
        }
    }
}
