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
    private final LiveData<List<Message>> allMessagesSorted;
    private final LiveData<Map<Category, List<Message>>> allCategoriesWithMessages;

    @Inject
    public MessageRepository(AppDatabase appDatabase, MessageDAO messageDAO, CategoryDAO categoryDAO) {
        this.messageDAO = messageDAO;
        this.allMessages = messageDAO.all();
        this.allMessagesSorted = messageDAO.sortedByLastUsed();
        this.allCategoriesWithMessages = categoryDAO.allWithMessages();

        AppDatabase.databaseWriteExecutor.execute(() -> appDatabase.runInTransaction(() -> {
            long help = categoryDAO.insertCategory(new Category("Help"));
            long greetings = categoryDAO.insertCategory(new Category("Greetings"));
            categoryDAO.insertCategory(new Category("Test"));
            messageDAO.delete("need doctor");
            messageDAO.insert(new Message("need doctor", help));
            messageDAO.delete("hello");
            messageDAO.insert(new Message("hello", greetings));
            messageDAO.delete("good bye");
            messageDAO.insert(new Message("good bye", greetings));
        }));
    }

    /**
     * Gets every saved message.
     *
     * @return an observable list of messages
     */
    public LiveData<List<Message>> getAllMessages() {
        return allMessages;
    }

    /**
     * Gets every saved message.
     *
     * @return an observable list of messages
     */
    public LiveData<List<Message>> getAllMessagesSorted() {
        return allMessagesSorted;
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
     * Gets every category and associated messages if any.
     *
     * @return an observable map of categories to message lists
     */
    public LiveData<Map<Category, List<Message>>> getAllCategoriesWithMessages() {
        return allCategoriesWithMessages;
    }
}
