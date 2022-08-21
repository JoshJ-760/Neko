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

        //Button Switches to a random sprite
        viewHolder.taskButton.setOnClickListener((v) -> {
            NekoSprites randomState = Utility.getRandomItem(NekoSprites.values());
            viewHolder.nekoStateTextView.setText(randomState.toString());
            viewHolder.nekoView.setSprite(randomState);
        });

        //updates the scale (physical size) of Neko when the user drags the bar
        viewHolder.scaleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float scale = (float)progress/10f;

                viewHolder.nekoView.setScale(scale);
                viewHolder.scaleTextView.setText(String.valueOf((float)seekBar.getProgress()/10f));
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
        TextView scaleTextView;
        SeekBar scaleSeekBar;
        Button taskButton;
        TextView nekoStateTextView;
        NekoView nekoView;

        ViewHolder() {
            scaleTextView = findViewById(R.id.scale_text);
            scaleSeekBar = findViewById(R.id.scale_slider);
            taskButton = findViewById(R.id.taskButton);
            nekoStateTextView = findViewById(R.id.enumName);
            nekoView = findViewById(R.id.nekoView);

            //set initial values of scale seekbar
            scaleSeekBar.setProgress((int)(nekoView.getScale()*10f));
            scaleTextView.setText(String.valueOf((float) scaleSeekBar.getProgress()/10f));
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