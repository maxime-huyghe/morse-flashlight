package dev.huyghe.morseflashlight.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import dev.huyghe.morseflashlight.FlashlightController;
import dev.huyghe.morseflashlight.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FlashlightController flashLightController = new FlashlightController(this);

        Button btn_toggle_flashlight = findViewById(R.id.main_button_toggle_flashlight);
        btn_toggle_flashlight.setOnClickListener(view -> flashLightController.toggleFlashLight());

        Button btn_toggle_strobe = findViewById(R.id.main_button_toggle_strobe);
        btn_toggle_strobe.setOnClickListener(view -> flashLightController.toggleStrobe());

        Button btn_toggle_sos = findViewById(R.id.main_button_toggle_sos);
        btn_toggle_sos.setOnClickListener(view -> flashLightController.toggleSOS());

        SeekBar seek_flashing_speed = findViewById(R.id.main_seek_flashing_speed);
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

        findViewById(R.id.main_button_flash_morse_message).setOnClickListener(view -> {
            flashLightController.turnOffCompletely();
            startActivity(new Intent(this, MorseInputActivity.class));
        });

        Log.d(TAG, "Finished setting up MainActivity listeners");
    }
}