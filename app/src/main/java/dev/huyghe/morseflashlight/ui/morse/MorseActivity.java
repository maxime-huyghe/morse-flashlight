package dev.huyghe.morseflashlight.ui.morse;

import static android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import android.content.Intent;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.provider.Settings;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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
            BiometricPrompt.AuthenticationCallback callback = new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    messageViewModel.unlock();
                }
                @Override
                public void onAuthenticationError(int errorCode, CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    final Intent enrollIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
                    enrollIntent.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                            BIOMETRIC_STRONG | DEVICE_CREDENTIAL);
                    startActivity(enrollIntent);
                }
            };
            new BiometricPrompt.Builder(this)
                    .setTitle("Unlock your custom messages")
                    .setAllowedAuthenticators(
                            BIOMETRIC_STRONG
                                    | DEVICE_CREDENTIAL
                    )
                    .build()
                    .authenticate(new CancellationSignal(), ContextCompat.getMainExecutor(this), callback);
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
