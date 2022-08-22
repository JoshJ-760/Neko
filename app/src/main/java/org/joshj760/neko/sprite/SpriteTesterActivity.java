package org.joshj760.neko.sprite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import org.joshj760.neko.R;
import org.joshj760.neko.utility.Utility;

public class SpriteTesterActivity extends AppCompatActivity {

    ViewHolder viewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprite_tester);

        viewHolder = new ViewHolder();

        //Button Switches to a random sprite
        viewHolder.taskButton.setOnClickListener((v) -> {
            NekoSprite randomState = Utility.getRandomItem(NekoSprite.values());
            viewHolder.nekoStateTextView.setText(randomState.toString());
            viewHolder.nekoSpriteView.setSprite(randomState);
        });

        //updates the scale (physical size) of Neko when the user drags the bar
        viewHolder.scaleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float scale = (float)progress/10f;

                viewHolder.nekoSpriteView.setScale(scale);
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
        NekoSpriteView nekoSpriteView;

        ViewHolder() {
            scaleTextView = findViewById(R.id.scale_text);
            scaleSeekBar = findViewById(R.id.scale_slider);
            taskButton = findViewById(R.id.taskButton);
            nekoStateTextView = findViewById(R.id.enumName);
            nekoSpriteView = findViewById(R.id.nekoView);

            //set initial values of scale seekbar
            scaleSeekBar.setProgress((int)(nekoSpriteView.getScale()*10f));
            scaleTextView.setText(String.valueOf((float) scaleSeekBar.getProgress()/10f));
        }
    }
}