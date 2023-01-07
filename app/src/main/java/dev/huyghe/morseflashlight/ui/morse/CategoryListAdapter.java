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

        public ViewHolder(View view, View.OnClickListener onItemClickListener) {
            super(view);
            tv = view.findViewById(R.id.category_list_row_item_tv);
            rv = view.findViewById(R.id.category_list_row_item_rv);
            adapter = new MessageListAdapter(onItemClickListener);
            rv.setAdapter(adapter);
            rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        }
    }

    List<Pair<Category, List<Message>>> categoriesToMessages;
    View.OnClickListener onItemClickListener;

    public CategoryListAdapter(View.OnClickListener onItemClickListener) {
        super();
        categoriesToMessages = Collections.emptyList();
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.category_list_row_item, parent, false);
        return new ViewHolder(view, onItemClickListener);
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
