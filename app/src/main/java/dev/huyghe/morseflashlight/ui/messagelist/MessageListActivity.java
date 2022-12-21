package dev.huyghe.morseflashlight.ui.messagelist;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import dev.huyghe.morseflashlight.data.Message;
import dev.huyghe.morseflashlight.databinding.ActivityMessageListBinding;
import dev.huyghe.morseflashlight.domain.FlashlightService;
import dev.huyghe.morseflashlight.domain.MorseCharacter;
import dev.huyghe.morseflashlight.ui.MessageViewModel;

@AndroidEntryPoint
public class MessageListActivity extends AppCompatActivity {
    private static final String TAG = "MorseInputActivity";

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
            List<MorseCharacter> morseCharacters = new ArrayList<>();
            for (int i = 0; i < s.length(); ++i) {
                morseCharacters.add(MorseCharacter.newFromChar(s.charAt(i)));
            }
            try {
                flashlightService.flashMorseString(
                        morseCharacters,
                        index -> Log.d(TAG, "received char " + morseCharacters.get(index).getCharacter())
                );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            binding.morseInputEdit.setText("");
            messageViewModel.onMessageFlashed(s);
        });

        // Message list setup
        MessageListAdapter messageListAdapter = new MessageListAdapter();
        binding.messageListRv.setAdapter(messageListAdapter);
        binding.messageListRv.setLayoutManager(new LinearLayoutManager(this));
        messageViewModel
                .getAllMessages()
                .observe(this, messageListAdapter::update);
    }
}
