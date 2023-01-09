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

import com.google.android.material.divider.MaterialDividerItemDecoration;

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

        public ViewHolder(View view, Consumer<Message> onMessageClicked, Consumer<Message> onCategoryChangeClicked) {
            super(view);
            tv = view.findViewById(R.id.category_list_row_item_tv);
            rv = view.findViewById(R.id.category_list_row_item_rv);
            adapter = new MessageListAdapter(onMessageClicked, onCategoryChangeClicked);
            rv.setAdapter(adapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
            rv.setLayoutManager(linearLayoutManager);
            rv.addItemDecoration(
                    new MaterialDividerItemDecoration(
                            rv.getContext(),
                            linearLayoutManager.getOrientation()
                    )
            );
        }
    }

    List<Pair<Category, List<Message>>> categoriesToMessages;
    final Consumer<Message> onMessageClicked;
    final Consumer<Message> onCategoryChangeClicked;

    public CategoryListAdapter(Consumer<Message> onMessageClicked, Consumer<Message> onCategoryChangeClicked) {
        super();
        categoriesToMessages = Collections.emptyList();
        this.onMessageClicked = onMessageClicked;
        this.onCategoryChangeClicked = onCategoryChangeClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.category_list_row_item, parent, false);
        return new ViewHolder(view, onMessageClicked, onCategoryChangeClicked);
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
