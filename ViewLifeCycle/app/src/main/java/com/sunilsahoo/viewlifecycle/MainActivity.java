package com.sunilsahoo.viewlifecycle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private LinearLayout mainContainer;
    private CustomView customView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CustomView", "=== ADD VIEW ===");
        setContentView(R.layout.activity_main);
        mainContainer = findViewById(R.id.mainContainer);
        customView = findViewById(R.id.customview);
    }

    public void updateView(View view) {
        if (view.getId() == R.id.customview) {
            Log.d("CustomView", "=== REMOVE VIEW ===");
            mainContainer.removeAllViews();
        } else {
            Log.d("CustomView", "=== UPDATE VIEW ===");
            customView.setText("Child Updated : " + System.currentTimeMillis());
        }
    }

}
