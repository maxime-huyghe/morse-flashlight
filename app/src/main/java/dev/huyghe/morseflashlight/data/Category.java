package dev.huyghe.morseflashlight.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;

/**
 * Represents a category used to sort messages.
 */
@Entity(indices = {@Index(value = "name", unique = true)})
public class Category {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    private final String name;

    public Category(@NonNull String name) {
        this.name = name;
    }

    /**
     * @return the database id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the database id.
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the category's name
     */
    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return getId() == category.getId() && getName().equals(category.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
