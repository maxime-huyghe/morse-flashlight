package dev.huyghe.morseflashlight.ui.messagelist;

import android.annotation.SuppressLint;
import android.database.Observable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import dev.huyghe.morseflashlight.R;
import dev.huyghe.morseflashlight.data.Message;

/**
 * Provides
 */
public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tv;

        public ViewHolder(View view) {
            super(view);
            tv = view.findViewById(R.id.message_list_row_item_tv);
        }
    }

    List<Message> messages;
    View.OnClickListener onItemClickListener;

    public MessageListAdapter(View.OnClickListener onItemClickListener) {
        super();
        messages = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.activity_message_list_row_item, parent, false);
        view.setOnClickListener(onItemClickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv.setText(messages.get(position).getContent());
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
}
