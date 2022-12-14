package dev.huyghe.morseflashlight.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

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
      * @param message the message to insert
     */
    @Insert
    void insert(Message message);

    /**
     * Get a list of messages sorted in whatever order SQLite sees fit.
     * @return an observable list of messages
     */
    @Query("select * from message")
    LiveData<List<Message>> all();

    /**
     * Get a list of messages sorted by descending times used.
     * @return an observable list of messages
     */
    @Query("select * from message order by timesUsed desc")
    LiveData<List<Message>> sortedByTimesUsed();

    /**
     * Delete a message from the DB.
     * @param id the message's id
     */
    @Query("delete from message where id = :id")
    void delete(int id);
}
