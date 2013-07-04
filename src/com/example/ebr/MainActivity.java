package com.example.ebr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.artifex.mupdfdemo.MuPDFActivity;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {
	
	static {
	    System.loadLibrary("mupdf");
	  }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		//setContentView(R.layout.activity_main);
		CopyAssetsbrochure(); 
		
		
		File file = new File("/sdcard/divya.pdf"); 
		
		Uri uri = Uri.parse("/sdcard/divya.pdf");

		Intent intent = new Intent(getBaseContext(), MuPDFActivity.class);

		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		intent.setAction(Intent.ACTION_VIEW);

		intent.setData(uri);

		getBaseContext().startActivity(intent);

		/* Intent intent = new Intent(Intent.ACTION_VIEW);
		 intent.setDataAndType(Uri.fromFile(file),"application/pdf");
		 intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
		 try 
		 {
			 getBaseContext().startActivity(intent);
			 //Document document = new Document();
		     //PdfWriter.getInstance(document, new FileOutputStream(file));
		     //document.open();
             //getApplicationContext().startActivity(intent);
		 } 
		 catch (ActivityNotFoundException e) 
		 {
			 Log.e("Activity not ound", e.getMessage());
		 } 
	}
	
	private void CopyAssetsbrochure() {
        AssetManager assetManager = getAssets();
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Log.d("Test", "sdcard mounted and writable");
        }
        else
        {
        	Log.d("Test", "sdcard state: " + state);
        }
        String[] files = null;
        try 
        {
            files = assetManager.list("");
        } 
        catch (IOException e)
        {
            Log.e("tag", e.getMessage());
        }
        for(int i=0; i<files.length; i++)
        {
            String fStr = files[i];
            if(fStr.equalsIgnoreCase("divya.pdf"))
            {
                InputStream in = null;
                OutputStream out = null;
                try 
                {
                  in = assetManager.open(files[i]);
                  out = new FileOutputStream("/sdcard/" + files[i]);
                  copyFile(in, out);
                  in.close();
                  in = null;
                  out.flush();
                  out.close();
                  out = null;
                  break;
                } 
                catch(Exception e)
                {
                    Log.e("CopyAssests", e.getMessage());
                } 
            }
        }
    }

 private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
          out.write(buffer, 0, read);
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
