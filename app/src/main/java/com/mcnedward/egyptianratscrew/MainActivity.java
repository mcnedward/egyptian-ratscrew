package com.mcnedward.egyptianratscrew;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.mcnedward.egyptianratscrew.model.GameTable;
import com.mcnedward.egyptianratscrew.view.GameSurface;

public class MainActivity extends AppCompatActivity {

    private GameTable mGameTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGameTable = new GameTable(this);

        LinearLayout layout = (LinearLayout) findViewById(R.id.mainLayout);
        layout.addView(new GameSurface(this, mGameTable));

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
        if (id == R.id.action_reset) {
            mGameTable.reset();
        }

        return super.onOptionsItemSelected(item);
    }
}
