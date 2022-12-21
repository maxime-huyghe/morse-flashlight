package dev.huyghe.morseflashlight.data;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * A repository meant to access message objects from other parts of the app.
 */
@Singleton
public class MessageRepository {
    private final MessageDAO messageDAO;
    private final LiveData<List<Message>> allMessages;
    private final LiveData<List<Message>> allMessagesSorted;

    @Inject
    public MessageRepository(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
        this.allMessages = messageDAO.all();
        this.allMessagesSorted = messageDAO.sortedByLastUsed();
    }

    /**
     * Gets every saved message.
     * @return an observable list of messages
     */
    public LiveData<List<Message>> getAllMessages() {
        return allMessages;
    }
    /**
     * Gets every saved message.
     * @return an observable list of messages
     */
    public LiveData<List<Message>> getAllMessagesSorted() {
        return allMessagesSorted;
    }


    /**
     * Saves a message to be used later.
     * @param message the message to save
     */
    public void saveMessage(String message) {
        AppDatabase.databaseWriteExecutor.execute(() -> messageDAO.insert(new Message(message)));
    }

    /**
     * Delete a message from the list of saved messages.
     * @param message the message
     */
    public void deleteMessage(String message) {
        AppDatabase.databaseWriteExecutor.execute(() -> messageDAO.delete(message));
    }
}
