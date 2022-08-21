package org.joshj760.neko;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class TesterActivity extends AppCompatActivity {

    ViewHolder viewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tester);

        viewHolder = new ViewHolder();

        viewHolder.taskButton.setOnClickListener((v) -> {
            NekoVisualState randomState = Utility.getRandomItem(NekoVisualState.values());
            viewHolder.showingState.setText(randomState.toString());
            viewHolder.nekoView.setSprite(randomState);
        });

        viewHolder.scaleSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float scale = (float)progress/10f;

                viewHolder.nekoView.setScale(scale);
                viewHolder.scaleText.setText(String.valueOf((float)seekBar.getProgress()/10f));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //intentionally blank
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //intentionally blank
            }
        });
    }

    private class ViewHolder {
        TextView scaleText;
        SeekBar scaleSlider;
        Button taskButton;
        TextView showingState;
        NekoView nekoView;

        ViewHolder() {
            scaleText = findViewById(R.id.scale_text);
            scaleSlider = findViewById(R.id.scale_slider);
            taskButton = findViewById(R.id.taskButton);
            showingState = findViewById(R.id.enumName);
            nekoView = findViewById(R.id.nekoView);

            scaleText.setText(String.valueOf((float)scaleSlider.getProgress()/10f));
        }
    }

    static class Utility {
        static <T> T getRandomItem(T[] items) {
            return items[randomInt(items.length)];
        }

        static int randomInt(int maxExclusive) {
            return (int)(Math.random() * ((double)maxExclusive));
        }
    }
}