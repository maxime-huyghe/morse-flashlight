package dev.huyghe.morseflashlight.data;

import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import dev.huyghe.morseflashlight.ui.morse.tabs.SavedMessagesFragment;

/**
 * A repository meant to access message objects from other parts of the app.
 */
@Singleton
public class MessageRepository {
    private static final String TAG = MessageRepository.class.getSimpleName();

    private final MessageDAO messageDAO;
    private final LiveData<List<Message>> allMessages;
    private final LiveData<List<Message>> allDefaultMessages;
    private final LiveData<List<Message>> allMessagesSorted;
    private final LiveData<List<Message>> allDefaultMessagesSorted;
    private final CategoryDAO categoryDAO;
    private final LiveData<Map<Category, List<Message>>> allCategoriesWithMessages;
    private final LiveData<Map<Category, List<Message>>> allCategoriesWithDefaultMessages;
    private final LiveData<List<Category>> allCategories;

    @Inject
    public MessageRepository(AppDatabase appDatabase, MessageDAO messageDAO, CategoryDAO categoryDAO) {
        this.messageDAO = messageDAO;
        this.categoryDAO = categoryDAO;
        this.allMessages = messageDAO.all();
        this.allDefaultMessages = messageDAO.allDefault();
        this.allMessagesSorted = messageDAO.sortedByLastUsed();
        this.allDefaultMessagesSorted = messageDAO.defaultSortedByLastUsed();
        this.allCategories = categoryDAO.all();
        this.allCategoriesWithMessages = categoryDAO.allWithMessages();
        this.allCategoriesWithDefaultMessages = categoryDAO.allWithDefaultMessages();

        AppDatabase.databaseWriteExecutor.execute(() -> appDatabase.runInTransaction(() -> {
            Category help = categoryDAO.byName("Help");
            if (help == null) {
                help = new Category("Help");
                help.setId(categoryDAO.insertCategory(help));
            }
            Category greetings = categoryDAO.byName("Greetings");
            if (greetings == null) {
                greetings = new Category("Greetings");
                greetings.setId(categoryDAO.insertCategory(greetings));
            }
            messageDAO.delete("need doctor");
            messageDAO.insert(new Message("need doctor", help.getId(), false));
            messageDAO.delete("hello");
            messageDAO.insert(new Message("hello", greetings.getId(), false));
            messageDAO.delete("good bye");
            messageDAO.insert(new Message("good bye", greetings.getId(), false));
        }));
    }

    /**
     * Gets every saved message.
     *
     * @return an observable list of messages
     */
    public LiveData<List<Message>> getAllMessages(boolean includeCustom) {
        if (includeCustom) {
            return allMessages;
        } else {
            return allDefaultMessages;
        }
    }

    /**
     * Gets every saved message.
     *
     * @return an observable list of messages
     */
    public LiveData<List<Message>> getAllMessagesSorted(boolean includeCustom) {
        if (includeCustom) {
            return allMessagesSorted;
        } else {
            return allDefaultMessagesSorted;
        }
    }

    /**
     * Saves a message to be used later.
     *
     * @param message the message to save
     */
    public void saveMessage(Message message) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            if (messageDAO.insert(message) == -1) {
                messageDAO.updateLastUsed(message.getContent(), System.currentTimeMillis());
            }
        });
    }

    /**
     * Update a message.
     *
     * @param message the message to update
     */
    public void updateMessage(Message message) {
        AppDatabase.databaseWriteExecutor.execute(() -> messageDAO.update(message));
    }

    /**
     * Gets every category, sorted alphabetically.
     *
     * @return an observable list of categories
     */
    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    /**
     * Gets every category and associated messages if any.
     *
     * @return an observable map of categories to message lists
     */
    public LiveData<Map<Category, List<Message>>> getAllCategoriesWithMessages(boolean includeCustomMessages) {
        if (includeCustomMessages) {
        return allCategoriesWithMessages;
        } else {
            return allCategoriesWithDefaultMessages;
        }
    }

    public void createCategory(String name) {
        AppDatabase.databaseWriteExecutor.execute(
                () -> categoryDAO.insertCategoryWithoutReplacing(new Category(name))
        );
    }
}
