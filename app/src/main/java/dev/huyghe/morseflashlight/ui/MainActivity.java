package dev.huyghe.morseflashlight.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import dev.huyghe.morseflashlight.databinding.ActivityMainBinding;
import dev.huyghe.morseflashlight.domain.FlashlightService;
import dev.huyghe.morseflashlight.ui.morse.MorseActivity;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Inject
    FlashlightService flashlightService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.mainButtonToggleFlashlight.setOnClickListener(view -> flashlightService.toggleFlashLight());

        binding.mainButtonToggleStrobe.setOnClickListener(view -> flashlightService.toggleStrobe());

        binding.mainButtonToggleSos.setOnClickListener(view -> flashlightService.toggleSOS());

        SeekBar seek_flashing_speed = binding.mainSeekFlashingSpeed;
        seek_flashing_speed.setProgress(flashlightService.getFlashingSpeed());
        seek_flashing_speed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                flashlightService.setFlashingSpeed(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        binding.mainButtonFlashMorseMessage.setOnClickListener(view -> {
            flashlightService.turnOffCompletely();
            startActivity(new Intent(this, MorseActivity.class));
        });

        Log.d(TAG, "Finished setting up MainActivity listeners");
    }
}