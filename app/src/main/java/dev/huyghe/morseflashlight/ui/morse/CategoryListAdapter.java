package dev.huyghe.morseflashlight.ui.morse;

import android.annotation.SuppressLint;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import dev.huyghe.morseflashlight.R;
import dev.huyghe.morseflashlight.data.Category;
import dev.huyghe.morseflashlight.data.Message;

/**
 * Provides
 */
public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tv;
        final RecyclerView rv;
        final MessageListAdapter adapter;

        public ViewHolder(View view, Consumer<Message> onMessageClicked) {
            super(view);
            tv = view.findViewById(R.id.category_list_row_item_tv);
            rv = view.findViewById(R.id.category_list_row_item_rv);
            adapter = new MessageListAdapter(onMessageClicked);
            rv.setAdapter(adapter);
            rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        }
    }

    List<Pair<Category, List<Message>>> categoriesToMessages;
    final Consumer<Message> onMessageClicked;

    public CategoryListAdapter(Consumer<Message> onMessageClicked) {
        super();
        categoriesToMessages = Collections.emptyList();
        this.onMessageClicked = onMessageClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.category_list_row_item, parent, false);
        return new ViewHolder(view, onMessageClicked);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv.setText(categoriesToMessages.get(position).first.getName());
        holder.adapter.update(categoriesToMessages.get(position).second);
    }

    @Override
    public int getItemCount() {
        return categoriesToMessages.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void update(List<Pair<Category, List<Message>>> categories) {
        categoriesToMessages = categories;
        notifyDataSetChanged();
    }
}
