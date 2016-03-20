package com.example.josephmasison.doodle_434;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    DoodleView doodleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doodleView = (DoodleView) findViewById(R.id.doodle_view);

        Button bClear = (Button) findViewById(R.id.clear);
        bClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleView.clearPath();
            }
        });

        Button bRed = (Button) findViewById(R.id.red);
        bRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleView.red();
            }
        });

        Button bOrange = (Button) findViewById(R.id.orange);
        bOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleView.orange();
            }
        });

        Button bYellow = (Button) findViewById(R.id.yellow);
        bYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleView.yellow();
            }
        });

        Button bGreen = (Button) findViewById(R.id.green);
        bGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleView.green();
            }
        });

        Button bBlue = (Button) findViewById(R.id.blue);
        bBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleView.blue();
            }
        });

        Button bPurple = (Button) findViewById(R.id.purple);
        bPurple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doodleView.purple();
            }
        });

        SeekBar sbSize = (SeekBar) findViewById(R.id.size);
        sbSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                doodleView.setPaintSize(progress);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                //filler
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                //filler
            }
        });

        SeekBar sbOP = (SeekBar) findViewById(R.id.opacity);
        sbOP.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                doodleView.setPaintOp(progress);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                //filler
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                //filler
            }
        });

    }


    public static void addImageToGallery(final String filePath, final Context context) {

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filePath);

        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
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

        if (id == R.id.save) {
            doodleView.setDrawingCacheEnabled(true);
            Bitmap bitmap = doodleView.getDrawingCache();
            File root = Environment.getExternalStorageDirectory();
            String sig = getCurrentTimeStamp();
            File file = new File(root.getAbsolutePath()+"/img"+sig+".jpg");
            try
            {
                file.createNewFile();
                FileOutputStream ostream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                ostream.close();
                Log.i("TAG", "File Created");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            String filePath = file.getPath();
            Log.i("TAG", filePath);
            ContentValues values = new ContentValues();

            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.MediaColumns.DATA, filePath);

            getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            doodleView.setDrawingCacheEnabled(false);
            //System.exit(0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
}