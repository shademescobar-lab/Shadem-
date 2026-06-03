package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FolkDao {
    // Costume Designs
    @Query("SELECT * FROM costume_designs ORDER BY savedAt DESC")
    fun getCostumeDesigns(): Flow<List<CostumeDesign>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCostumeDesign(design: CostumeDesign)

    @Delete
    suspend fun deleteCostumeDesign(design: CostumeDesign)

    // Events
    @Query("SELECT * FROM event_reminders ORDER BY id DESC")
    fun getEventReminders(): Flow<List<EventReminder>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEventReminder(event: EventReminder)

    @Delete
    suspend fun deleteEventReminder(event: EventReminder)

    // Practice Logs
    @Query("SELECT * FROM practice_logs ORDER BY timestamp DESC")
    fun getPracticeLogs(): Flow<List<PracticeLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPracticeLog(log: PracticeLog)

    // Community Posts
    @Query("SELECT * FROM community_posts ORDER BY timestamp DESC")
    fun getCommunityPosts(): Flow<List<CommunityPost>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommunityPost(post: CommunityPost)

    @Update
    suspend fun updateCommunityPost(post: CommunityPost)

    // Post Comments
    @Query("SELECT * FROM post_comments WHERE postId = :postId ORDER BY timestamp ASC")
    fun getCommentsForPost(postId: Int): Flow<List<PostComment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: PostComment)

    // Favorite Steps
    @Query("SELECT * FROM favorite_steps ORDER BY savedAt DESC")
    fun getFavoriteSteps(): Flow<List<FavoriteStep>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteStep(favoriteStep: FavoriteStep)

    @Delete
    suspend fun deleteFavoriteStep(favoriteStep: FavoriteStep)
}
