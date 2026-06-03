package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "costume_designs")
data class CostumeDesign(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val danceType: String,
    val primaryColorHex: String,
    val secondaryColorHex: String,
    val accentColorHex: String,
    val hatType: String,
    val accessoryType: String,
    val savedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "event_reminders")
data class EventReminder(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val eventType: String, // "Ensayo", "Presentación", "Festival"
    val dateString: String, // e.g. "05 Jun"
    val timeString: String, // e.g. "18:30"
    val description: String,
    val isAutoReminderEnabled: Boolean = true
)

@Entity(tableName = "practice_logs")
data class PracticeLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val danceType: String,
    val durationMinutes: Int,
    val pointsEarned: Int,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "community_posts")
data class CommunityPost(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val authorName: String,
    val authorLevel: String,
    val authorAvatarIndex: Int,
    val message: String,
    val danceLabel: String,
    val likesCount: Int = 0,
    val userHasLiked: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "post_comments")
data class PostComment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val postId: Int,
    val authorName: String,
    val message: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "favorite_steps")
data class FavoriteStep(
    @PrimaryKey val stepId: String,
    val savedAt: Long = System.currentTimeMillis()
)

