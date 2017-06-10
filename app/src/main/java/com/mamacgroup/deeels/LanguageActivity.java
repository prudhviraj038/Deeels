package com.mamacgroup.deeels;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


public class LanguageActivity extends Activity {
    LinearLayout arabic,english;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Settings.forceRTLIfSupported(this);
        setContentView(R.layout.language_activity);
        arabic=(LinearLayout)findViewById(R.id.arabic_ll);
        english=(LinearLayout)findViewById(R.id.english_ll);
        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.set_user_language(LanguageActivity.this, "en");
                Settings.set_isfirsttime(LanguageActivity.this, "en");
                Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("type","normal");
                startActivity(intent);
            }
        });
        arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.set_user_language(LanguageActivity.this, "ar");
                Settings.set_isfirsttime(LanguageActivity.this, "ar");
                Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("type","normal");
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
