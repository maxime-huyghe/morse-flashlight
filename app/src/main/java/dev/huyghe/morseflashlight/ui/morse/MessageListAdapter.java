package dev.huyghe.morseflashlight.ui.morse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import dev.huyghe.morseflashlight.R;
import dev.huyghe.morseflashlight.data.Category;
import dev.huyghe.morseflashlight.data.Message;
import dev.huyghe.morseflashlight.ui.MessageViewModel;

/**
 * Provides
 */
public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {
    private static final String TAG = MessageListAdapter.class.getSimpleName();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tv;
        final ImageView button;

        public ViewHolder(View view) {
            super(view);
            tv = view.findViewById(R.id.message_list_row_item_tv);
            button = view.findViewById(R.id.message_list_row_item_button);
        }
    }

    List<Message> messages;
    final Consumer<Message> onMessageClicked;
    final Consumer<Message> onCategoryChangeClicked;

    public MessageListAdapter(Consumer<Message> onMessageClicked, Consumer<Message> onCategoryChangeClicked) {
        super();
        messages = new ArrayList<>();
        this.onMessageClicked = onMessageClicked;
        this.onCategoryChangeClicked = onCategoryChangeClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.message_list_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.tv.setText(message.getContent());
        holder.button.setOnClickListener(
                button -> onCategoryChangeClicked.accept(message)
        );
        holder.itemView.setOnClickListener(
                itemView -> onMessageClicked.accept(message)
        );
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void update(List<Message> _messages) {
        messages = _messages;
        notifyDataSetChanged();
    }

    public static void openDialog(Context ctx, MessageViewModel messageViewModel, Message message) {
        final List<Category> categories = Objects.requireNonNull(
                messageViewModel
                        .getAllCategories()
                        .getValue()
        );
        final AtomicInteger checkedItem = new AtomicInteger(0);
        // Pre-select the message's category
        if (message.getCategoryId() != null) {
            for (int i = 0; i < categories.size(); ++i) {
                if (categories.get(i).getId() == message.getCategoryId()) {
                    checkedItem.set(i);
                }
            }
        }

        new MaterialAlertDialogBuilder(
                ctx,
                com.google.android.material.R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog
        )
                .setTitle(R.string.message_list_save_dialog_title)
                .setSingleChoiceItems(
                        categories
                                .stream()
                                .map(Category::getName)
                                .toArray(CharSequence[]::new),
                        checkedItem.get(),
                        (dialog, which) -> checkedItem.set(which)
                )
                .setNeutralButton(R.string.dialog_cancel, (dialog, which) -> dialog.cancel())
                .setPositiveButton(
                        R.string.dialog_ok,
                        (dialog, which) -> messageViewModel
                                .setMessageCategory(message, categories.get(checkedItem.get()))
                )
                .show();
    }
}
