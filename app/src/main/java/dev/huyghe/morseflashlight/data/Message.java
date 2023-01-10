package dev.huyghe.morseflashlight.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;

/**
 * DB object to represent a message that a user may want to communicate using the app.
 */
@Entity(
        indices = {@Index("lastUsed")},
        foreignKeys = {@ForeignKey(entity = Category.class, parentColumns = "id", childColumns = "categoryId", onDelete = ForeignKey.SET_NULL)}
)
public class Message {
    @PrimaryKey
    @NonNull
    private final String content;
    private long lastUsed;
    private boolean custom;
    private Long categoryId;

    public Message(@NonNull String content) {
        this.content = content;
        this.lastUsed = System.currentTimeMillis(); // Creating == never used.
        this.custom = true;
    }

    @Ignore
    public Message(@NonNull String content, boolean custom) {
        this.content = content;
        this.lastUsed = System.currentTimeMillis(); // Creating == never used.
        this.custom = custom;
    }

    @Ignore
    public Message(@NonNull String content, long categoryId) {
        this.content = content;
        this.lastUsed = System.currentTimeMillis(); // Creating == never used.
        this.categoryId = categoryId;
        this.custom = true;
    }

    @Ignore
    public Message(@NonNull String content, long categoryId, boolean custom) {
        this.content = content;
        this.lastUsed = System.currentTimeMillis(); // Creating == never used.
        this.categoryId = categoryId;
        this.custom = custom;
    }

    /**
     * The message itself.
     */
    @NonNull
    public String getContent() {
        return content;
    }

    /**
     * The timestamp this message was last used at.
     * For sorting purposes.
     */
    public long getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(long timestampLastUsed) {
        lastUsed = timestampLastUsed;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isCustom() {
        return custom;
    }

    public void setCustom(boolean custom) {
        this.custom = custom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return getLastUsed() == message.getLastUsed() && getContent().equals(message.getContent()) && Objects.equals(getCategoryId(), message.getCategoryId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContent(), getLastUsed(), getCategoryId());
    }

    @NonNull
    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", lastUsed=" + lastUsed +
                ", categoryId=" + categoryId +
                '}';
    }
}
