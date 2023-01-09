package dev.huyghe.morseflashlight.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Use to access {@link Message} objects from the DB.
 * Instanciated by {@link AppDatabase#messageDAO()}.
 *
 * @see AppDatabase
 */
@Dao
public interface MessageDAO {
    /**
     * Insert a Message in the DB.
     *
     * @param message the message to insert
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Message message);

    /**
     * Update a Message's lastUsed.
     *
     * @param content  the message to update
     * @param lastUsed the new lastUsed
     */
    @Query("update message " +
            "set lastUsed = :lastUsed " +
            "where message.content = :content")
    void updateLastUsed(String content, long lastUsed);

    /**
     * Get a list of messages sorted in whatever order SQLite sees fit.
     *
     * @return an observable list of messages
     */
    @Query("select * from message")
    LiveData<List<Message>> all();

    /**
     * Get a list of messages sorted by descending times used.
     *
     * @return an observable list of messages
     */
    @Query("select * from message order by lastUsed desc")
    LiveData<List<Message>> sortedByLastUsed();

    /**
     * Delete a message from the DB.
     *
     * @param message the message
     */
    @Query("delete from message where content = :message")
    void delete(String message);

    /**
     * Update a message in the db.
     *
     * @param message the message with new values.
     */
    @Update
    void update(Message message);
}
