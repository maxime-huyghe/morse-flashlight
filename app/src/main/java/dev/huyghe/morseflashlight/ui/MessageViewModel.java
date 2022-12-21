package dev.huyghe.morseflashlight.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dev.huyghe.morseflashlight.data.Message;
import dev.huyghe.morseflashlight.data.MessageRepository;

/**
 * Provides state related to messages.
 */
@HiltViewModel
public class MessageViewModel extends ViewModel {
    private final MessageRepository messageRepository;
    private final LiveData<List<Message>> allMessagesSorted;

    @Inject
    public MessageViewModel(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
        this.allMessagesSorted = messageRepository.getAllMessagesSorted();
    }

    /**
     * A list of all messages, sorted by recency.
     */
    public LiveData<List<Message>> getAllMessages() {
        return allMessagesSorted;
    }

    /**
     * Called when a new message is flashed.
     *
     * @param message the message's content
     */
    public void onMessageFlashed(String message) {
        messageRepository.saveMessage(message);
    }
}
