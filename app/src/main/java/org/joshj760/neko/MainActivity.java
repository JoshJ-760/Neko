package org.joshj760.neko;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collection;

public class MainActivity extends AppCompatActivity {

    ViewHolder viewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewHolder = new ViewHolder();

        viewHolder.taskButton.setOnClickListener((v) -> {
            NekoVisualState randomState = Utility.getRandomItem(NekoVisualState.values());
            viewHolder.showingState.setText(randomState.toString());
            viewHolder.nekoView.setSprite(randomState);
        });
    }

    private class ViewHolder {
        Button taskButton;
        TextView showingState;
        NekoView nekoView;

        ViewHolder() {
            taskButton = findViewById(R.id.taskButton);
            showingState = findViewById(R.id.enumName);
            nekoView = findViewById(R.id.nekoView);
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