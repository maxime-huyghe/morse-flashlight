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
     * Insert a Category without replacing a previous category with the same name.
     *
     * @param category the category to insert
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCategoryWithoutReplacing(Category category);

    /**
     * Get a category by name.
     *
     * @param name the case-sensitive name.
     * @return the category
     */
    @Query("select * from category where category.name = :name")
    Category byName(String name);

    /**
     * Get a list of all categories, sorted alphabetically.
     *
     * @return an observables list of categories
     */
    @Query("select * from category order by category.name")
    LiveData<List<Category>> all();

    /**
     * Get a list of all categories and associated messages (if any).
     *
     * @return an observables map of categories to messages
     */
    @Query(
            "select * from category " +
                    "left join message on category.id = message.categoryId"
    )
    LiveData<Map<Category, List<Message>>> allWithMessages();

    /**
     * Get a list of all categories and associated messages (if any).
     *
     * @return an observables map of categories to messages
     */
    @Query(
            "select * from category " +
                    "left join message on category.id = message.categoryId " +
                    "where message.custom = 0"
    )
    LiveData<Map<Category, List<Message>>> allWithDefaultMessages();
}
