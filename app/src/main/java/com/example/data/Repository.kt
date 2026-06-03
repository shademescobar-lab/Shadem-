package com.example.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class FolkRepository(private val dao: FolkDao) {

    val costumeDesigns: Flow<List<CostumeDesign>> = dao.getCostumeDesigns()
    val eventReminders: Flow<List<EventReminder>> = dao.getEventReminders()
    val practiceLogs: Flow<List<PracticeLog>> = dao.getPracticeLogs()
    val communityPosts: Flow<List<CommunityPost>> = dao.getCommunityPosts()
    val favoriteSteps: Flow<List<FavoriteStep>> = dao.getFavoriteSteps()

    suspend fun addFavoriteStep(stepId: String) {
        dao.insertFavoriteStep(FavoriteStep(stepId))
    }

    suspend fun removeFavoriteStep(stepId: String) {
        dao.deleteFavoriteStep(FavoriteStep(stepId))
    }

    suspend fun addCostumeDesign(design: CostumeDesign) {
        dao.insertCostumeDesign(design)
    }

    suspend fun removeCostumeDesign(design: CostumeDesign) {
        dao.deleteCostumeDesign(design)
    }

    suspend fun addEventReminder(event: EventReminder) {
        dao.insertEventReminder(event)
    }

    suspend fun removeEventReminder(event: EventReminder) {
        dao.deleteEventReminder(event)
    }

    suspend fun addPracticeLog(log: PracticeLog) {
        dao.insertPracticeLog(log)
    }

    suspend fun addCommunityPost(post: CommunityPost) {
        dao.insertCommunityPost(post)
    }

    suspend fun updateCommunityPost(post: CommunityPost) {
        dao.updateCommunityPost(post)
    }

    fun getCommentsForPost(postId: Int): Flow<List<PostComment>> {
        return dao.getCommentsForPost(postId)
    }

    suspend fun addComment(comment: PostComment) {
        dao.insertComment(comment)
    }

    suspend fun seedInitialPostsIfEmpty() {
        val existing = dao.getCommunityPosts().firstOrNull()
        if (existing.isNullOrEmpty()) {
            val initialPosts = listOf(
                CommunityPost(
                    authorName = "Alejandro Colque",
                    authorLevel = "Avanzado",
                    authorAvatarIndex = 1,
                    message = "Preparando los saltos mortales para la entrada del Carnaval de Oruro. ¡Ensayos de Caporales a tope este fin de semana! Fuerza, tropa.",
                    danceLabel = "Caporales",
                    likesCount = 28,
                    userHasLiked = false
                ),
                CommunityPost(
                    authorName = "Mariana Quispe",
                    authorLevel = "Intermedio",
                    authorAvatarIndex = 2,
                    message = "Diseñé un traje de morenada con polleras doradas en la sección de vestidos de la app. ¿Qué accesorios me recomiendan añadir?",
                    danceLabel = "Morenada",
                    likesCount = 19,
                    userHasLiked = false
                ),
                CommunityPost(
                    authorName = "Brayan Choque",
                    authorLevel = "Avanzado",
                    authorAvatarIndex = 3,
                    message = "El ritmo del Tinku te estremece todo el cuerpo. Gran presentación hoy en el festival de danzas. ¡Jallalla Bolivia!",
                    danceLabel = "Tinku",
                    likesCount = 42,
                    userHasLiked = false
                ),
                CommunityPost(
                    authorName = "Yola Arteaga",
                    authorLevel = "Básico",
                    authorAvatarIndex = 0,
                    message = "Bailar cueca es un arte de cortejo único. Practicando el pañuelo y las miradas paso a paso para el examen de rango de mi escuela de ballet.",
                    danceLabel = "Cueca",
                    likesCount = 14,
                    userHasLiked = false
                )
            )
            for (post in initialPosts) {
                dao.insertCommunityPost(post)
            }
        }

        val existingEvents = dao.getEventReminders().firstOrNull()
        if (existingEvents.isNullOrEmpty()) {
            val initialEvents = listOf(
                EventReminder(
                    title = "Ensayo de Tinku General",
                    eventType = "Ensayo",
                    dateString = "05 Jun",
                    timeString = "18:30",
                    description = "Ensayo de coreografía en Plaza Murillo con uniforme completo para ajuste de distancias."
                ),
                EventReminder(
                    title = "Presentación - Tea Fest",
                    eventType = "Presentación",
                    dateString = "12 Jun",
                    timeString = "19:00",
                    description = "Gran gala folclórica del ballet dancístico boliviano en el Teatro de Bellas Artes."
                ),
                EventReminder(
                    title = "Entrada Folclórica Oruro",
                    eventType = "Festival",
                    dateString = "25 Jun",
                    timeString = "08:00",
                    description = "Participación oficial con la fraternidad de Diablada San José. ¡Orgullo boliviano!"
                )
            )
            for (event in initialEvents) {
                dao.insertEventReminder(event)
            }
        }
    }
}
