package dev.huyghe.morseflashlight.ui.morse;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import dev.huyghe.morseflashlight.R;
import dev.huyghe.morseflashlight.data.Message;

/**
 * Provides
 */
public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {
    private static final String TAG = MessageListAdapter.class.getSimpleName();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tv;

        public ViewHolder(View view) {
            super(view);
            tv = view.findViewById(R.id.message_list_row_item_tv);
        }
    }

    List<Message> messages;
    @Nullable
    RecyclerView parentRecyclerView;
    final View.OnClickListener onItemClickListener;

    public MessageListAdapter(Consumer<Message> onMessageClicked) {
        super();
        messages = new ArrayList<>();
        this.onItemClickListener = itemView -> {
            assert parentRecyclerView != null : "null parentRecyclerView";
            int position = parentRecyclerView.getChildAdapterPosition(itemView);
            Message message = messages.get(position);
            onMessageClicked.accept(message);
        };
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        parentRecyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        parentRecyclerView = null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.message_list_row_item, parent, false);
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
