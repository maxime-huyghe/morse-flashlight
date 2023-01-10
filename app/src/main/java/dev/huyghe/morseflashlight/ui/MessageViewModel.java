package dev.huyghe.morseflashlight.ui;

import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dev.huyghe.morseflashlight.data.Category;
import dev.huyghe.morseflashlight.data.Message;
import dev.huyghe.morseflashlight.data.MessageRepository;
import dev.huyghe.morseflashlight.domain.FlashlightService;

/**
 * Provides state related to messages and their categories.
 */
@HiltViewModel
public class MessageViewModel extends ViewModel {
    private final static String TAG = MessageViewModel.class.getSimpleName();

    private final MessageRepository messageRepository;
    private final LiveData<List<Message>> allMessagesSorted;
    private final LiveData<List<Message>> allDefaultMessagesSorted;
    private final FlashlightService flashlightService;
    private final MutableLiveData<Message> currentMessage;
    private final LiveData<List<Category>> allCategories;
    private final LiveData<List<Pair<Category, List<Message>>>> allCategoriesWithMessages;
    private final MutableLiveData<Boolean> unlocked;

    @Inject
    public MessageViewModel(MessageRepository messageRepository, FlashlightService flashlightService) {
        this.messageRepository = messageRepository;
        this.allMessagesSorted = messageRepository.getAllMessagesSorted(true);
        this.allDefaultMessagesSorted = messageRepository.getAllMessagesSorted(false);
        this.flashlightService = flashlightService;
        this.currentMessage = new MutableLiveData<>(new Message(""));
        this.allCategories = messageRepository.getAllCategories();
        this.unlocked = new MutableLiveData<>(false);
        this.allCategoriesWithMessages = Transformations.switchMap(
                unlocked,
                unlocked -> Transformations.map(
                        messageRepository.getAllCategoriesWithMessages(unlocked),
                        map -> {
                            List<Pair<Category, List<Message>>> list = new ArrayList<>(map.size());
                            for (Category category : map.keySet()) {
                                list.add(new Pair<>(category, map.get(category)));
                            }
                            list.sort(Comparator.comparing(pair -> pair.first.getName()));
                            Log.d(TAG, list.toString());
                            return list;
                        }
                )
        );
    }

    /**
     * A list of all messages, sorted by recency.
     */
    public LiveData<List<Message>> getAllMessages() {
        return Transformations.switchMap(
                unlocked,
                unlocked -> {
                    if (unlocked) {
                        return allMessagesSorted;
                    } else {
                        return allDefaultMessagesSorted;
                    }
                }
        );
    }

    /**
     * The message currently being flashed.
     *
     * @return an observable string
     */
    public LiveData<Message> getCurrentMessage() {
        return currentMessage;
    }

    /**
     * Gets every category sorted alphabetically.
     *
     * @return an observable list of categories
     */
    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    /**
     * Gets every category and associated messages, sorted by category name.
     *
     * @return an observable list of mappings of categories to message lists
     */
    public LiveData<List<Pair<Category, List<Message>>>> getAllCategoriesWithMessages() {
        return allCategoriesWithMessages;
    }

    /**
     * Flash a message and either save a new message or update the lastUsed time.
     *
     * @param message the message
     */
    public void flashAndSaveMessage(@NonNull Message message) {
        currentMessage.setValue(message);
        flashlightService.flashString(message.getContent());
        messageRepository.saveMessage(message);
    }

    /**
     * Sets the given message's category to the given category.
     *
     * @param message  the message, which may or may not already have a category
     * @param category the category
     */
    public void setMessageCategory(Message message, Category category) {
        message.setCategoryId(category.getId());
        messageRepository.updateMessage(message);
    }

    public void createCategory(String name) {
        messageRepository.createCategory(name);
    }

    public LiveData<Boolean> isUnlocked() {
        return unlocked;
    }

    public void unlock() {
        unlocked.setValue(true);
    }
}
