package dev.huyghe.morseflashlight.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import dagger.hilt.android.AndroidEntryPoint;
import dev.huyghe.morseflashlight.databinding.ActivityMainBinding;
import dev.huyghe.morseflashlight.domain.FlashlightController;
import dev.huyghe.morseflashlight.R;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FlashlightController flashLightController = new FlashlightController(this);

        binding.mainButtonToggleFlashlight.setOnClickListener(view -> flashLightController.toggleFlashLight());

        binding.mainButtonToggleStrobe.setOnClickListener(view -> flashLightController.toggleStrobe());

        binding.mainButtonToggleSos.setOnClickListener(view -> flashLightController.toggleSOS());

        SeekBar seek_flashing_speed = binding.mainSeekFlashingSpeed;
        seek_flashing_speed.setProgress(flashLightController.getFlashingSpeed());
        seek_flashing_speed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                flashLightController.setFlashingSpeed(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        binding.mainButtonFlashMorseMessage.setOnClickListener(view -> {
            flashLightController.turnOffCompletely();
            startActivity(new Intent(this, MorseInputActivity.class));
        });

        Log.d(TAG, "Finished setting up MainActivity listeners");
    }
}