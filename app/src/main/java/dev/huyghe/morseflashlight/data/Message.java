package dev.huyghe.morseflashlight.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;

/**
 * DB object to represent a message that a user may want to communicate using the app.
 */
@Entity(indices = {@Index("lastUsed")})
public class Message {
    @PrimaryKey
    @NonNull
    private final String content;
    private long lastUsed;

    public Message(@NonNull String content) {
        this.content = content;
        this.lastUsed = System.currentTimeMillis(); // Creating == never used.
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return getLastUsed() == message.getLastUsed() && getContent().equals(message.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContent(), getLastUsed());
    }

    @NonNull
    @Override
    public String toString() {
        return "Message{" +
                ", content='" + content + '\'' +
                ", lastUsed=" + lastUsed +
                '}';
    }
}
