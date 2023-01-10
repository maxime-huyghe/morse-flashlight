package dev.huyghe.morseflashlight.ui.morse;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayoutMediator;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import dev.huyghe.morseflashlight.data.Message;
import dev.huyghe.morseflashlight.databinding.ActivityMorseBinding;
import dev.huyghe.morseflashlight.domain.FlashlightService;
import dev.huyghe.morseflashlight.ui.MessageViewModel;
import dev.huyghe.morseflashlight.ui.morse.tabs.MorseTabAdapter;

@AndroidEntryPoint
public class MorseActivity extends AppCompatActivity {
    private static final String TAG = MorseActivity.class.getSimpleName();

    @Inject
    FlashlightService flashlightService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMorseBinding binding = ActivityMorseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MessageViewModel messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);

        // Custom message unlocking
        messageViewModel.isUnlocked().observe(this, unlocked -> {
            if (unlocked) {
                binding.morseInputButtonUnlockCustomMessages.setVisibility(View.GONE);
                binding.morseInputFlashForm.setVisibility(View.VISIBLE);
            } else {
                binding.morseInputButtonUnlockCustomMessages.setVisibility(View.VISIBLE);
                binding.morseInputFlashForm.setVisibility(View.GONE);
            }
        });
        binding.morseInputButtonUnlockCustomMessages.setOnClickListener(view -> {
            messageViewModel.unlock();
        });

        binding.morseInputButtonFlash.setOnClickListener(view -> {
            String s = binding.morseInputEdit.getText().toString();
            messageViewModel.flashAndSaveMessage(new Message(s));
        });

        messageViewModel.getCurrentMessage().observe(this, newMessage -> {
            binding.morseInputEdit.setText(newMessage.getContent());
            // By default, android moves the cursor to the beginning
            // when calling TextEdit#setText, so we put it back at the end.
            binding.morseInputEdit.setSelection(newMessage.getContent().length());
        });

        binding.morsePager.setAdapter(new MorseTabAdapter(this));

        new TabLayoutMediator(binding.morseTabLayout, binding.morsePager,
                (tab, position) -> tab.setText(MorseTabAdapter.getPageTitle(position))
        ).attach();
    }
}
