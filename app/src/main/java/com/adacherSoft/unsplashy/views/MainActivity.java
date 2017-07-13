package com.adacherSoft.unsplashy.views;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.adacherSoft.unsplashy.R;
import com.adacherSoft.unsplashy.background.GetDataSplash;
import com.adacherSoft.unsplashy.models.Unsplash;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    private List<Unsplash> example = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new Test().execute();

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
        if (id == R.id.action_help) {

            new AlertDialog.Builder(this)
                    .setTitle("Ayuda")
                    .setMessage("Deja presionada la foto para descargarla")
                    .show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class Test extends GetDataSplash {

        @Override
        protected void onPostExecute(List<Unsplash> unsplashes) {
            super.onPostExecute(unsplashes);

            Log.d("Test", String.valueOf(unsplashes));

        }
    }


}
