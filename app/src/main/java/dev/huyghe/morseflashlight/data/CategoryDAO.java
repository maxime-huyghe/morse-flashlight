package dev.huyghe.morseflashlight.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
import java.util.Map;

/**
 * Use to access {@link Category} objects from the DB and the associated {@link Message} objects.
 * Instanciated by {@link AppDatabase#categoryDAO()}.
 *
 * @see AppDatabase
 */
@Dao
public interface CategoryDAO {
    /**
     * Insert a Category.
     *
     * @param category the category to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCategory(Category category);

    /**
     * Get a list of all categories and associated messages (if any), unsorted.
     *
     * @return an observables map of categories to messages
     */
    @Query(
            "select * from category " +
                    "left join message on category.id = message.categoryId"
    )
    LiveData<Map<Category, List<Message>>> allWithMessages();
}
