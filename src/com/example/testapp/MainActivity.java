package com.example.testapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;



public class MainActivity extends Activity
{

    CharSequence[] items = { "Google", "Apple", "Microsoft" };
    boolean[] itemsChecked = new boolean [items.length];
    ProgressDialog progressDialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    // Method to start the service 
    public void startService(View view) 
    { 
        startService(new Intent(getBaseContext(), MyService.class)); 
    } 
    
    // Method to stop the service 
    public void stopService(View view) 
    { 
        stopService(new Intent(getBaseContext(), MyService.class)); 
    }
    
    public void onClick(View v)
    {
        showDialog(0);
    }

    public void onClick2(View v)
    {
      //---show the dialog---
      final ProgressDialog dialog = ProgressDialog.show(this, "Bezig met iets","Even geduld a.u.b.",true);
      new Thread(new Runnable()
      {
       public void run()
       {
          try {
             //---simulate doing something lengthy---
             Thread.sleep(4000);
              //---dismiss the dialog---
             dialog.dismiss();
          } catch (InterruptedException e) {
               e.printStackTrace();
             }
       }
       }).start();
    }  
    
    public void onClick3(View v) {
        showDialog(1);
        progressDialog.setProgress(0);
        new Thread(new Runnable(){
            public void run(){
                for (int i=1; i<=15; i++) {
                    try {
                        //---simulate doing something lengthy---
                        Thread.sleep(1000);
                        //---update the dialog---
                        progressDialog.incrementProgressBy((int)(100/15));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                progressDialog.dismiss();
            }
        }).start();
    }

    @Override
    protected Dialog onCreateDialog(int id) 
    {
        switch (id) {
        case 0:
            Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.ic_launcher);
            builder.setTitle("Dit is een dialog met simpele tekst...");
            builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(getBaseContext(),
                        "OK clicked!", Toast.LENGTH_SHORT).show();
                    }
                }
            );
            builder.setNegativeButton("Cancel",
               new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    Toast.makeText(getBaseContext(),
                    "Cancel clicked!", Toast.LENGTH_SHORT).show();
                    }
                }
            );
            builder.setMultiChoiceItems(items, itemsChecked,
               new DialogInterface.OnMultiChoiceClickListener() {
                 public void onClick(DialogInterface dialog,
                    int which, boolean isChecked) {
                    Toast.makeText(getBaseContext(),
                    items[which] + (isChecked ? " checked!":" unchecked!"),
                    Toast.LENGTH_SHORT).show();
                 }
                }
            );
            return builder.create();
        //integer voor showDialog(int x)    
        case 1:
            progressDialog = new ProgressDialog(this);
            progressDialog.setIcon(R.drawable.ic_launcher);
            progressDialog.setTitle("Downloading files...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", 
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                    int whichButton)
                    {
                        Toast.makeText(getBaseContext(),
                                "OK clicked!", Toast.LENGTH_SHORT).show();
                    }
            });
            progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", 
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                        int whichButton)
                    {
                        Toast.makeText(getBaseContext(),
                               "Cancel clicked!", Toast.LENGTH_SHORT).show();
                    }
            });
            return progressDialog;
        }        
       return null;
   }
}
