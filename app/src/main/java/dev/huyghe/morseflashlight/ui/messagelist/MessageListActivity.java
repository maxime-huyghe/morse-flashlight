package dev.huyghe.morseflashlight.ui.messagelist;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import dev.huyghe.morseflashlight.databinding.ActivityMessageListBinding;
import dev.huyghe.morseflashlight.domain.FlashlightService;
import dev.huyghe.morseflashlight.ui.MessageViewModel;

@AndroidEntryPoint
public class MessageListActivity extends AppCompatActivity {
    private static final String TAG = MessageListActivity.class.getSimpleName();

    @Inject
    FlashlightService flashlightService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMessageListBinding binding = ActivityMessageListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MessageViewModel messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);

        binding.morseInputButtonFlash.setOnClickListener(view -> {
            String s = binding.morseInputEdit.getText().toString();
            messageViewModel.flashMessage(s);
        });

        // Message list setup
        MessageListAdapter messageListAdapter = new MessageListAdapter(view -> {
            int position = binding.messageListRv.getChildLayoutPosition(view);
            String message = Objects.requireNonNull(messageViewModel.getAllMessages().getValue()).get(position).getContent();
            messageViewModel.flashMessage(message);
            binding.morseInputEdit.setText(message);
        });
        binding.messageListRv.setAdapter(messageListAdapter);
        binding.messageListRv.setLayoutManager(new LinearLayoutManager(this));
        messageViewModel
                .getAllMessages()
                .observe(this, messageListAdapter::update);
    }
}
