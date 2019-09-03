package com.aymar.muhikira;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView image;
    Button img_btn;
    Button classify_btn;
    TextView display_result;
    TextRecognizer textRecognizer;
    Bitmap myBitmap;
    int now = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img_btn = (Button)findViewById(R.id.btn_Img);
        classify_btn = (Button)findViewById(R.id.btn_recognise);
        image = (ImageView)findViewById(R.id.Img_Capture);
        display_result = (TextView)findViewById(R.id.textBox);
        textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        img_btn.setOnClickListener(this);
        classify_btn.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        myBitmap = (Bitmap)data.getExtras().get("data");
        image.setImageBitmap(myBitmap);
    }

    @Override
    public void onClick(View v){
        if(v.getId()==R.id.btn_Img){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,0);
        }
        else if(v.getId()==R.id.btn_recognise){
            Frame imgFrame = new Frame.Builder().setBitmap(myBitmap).build();
            String imgText = "";
            SparseArray<TextBlock> textBlocks = textRecognizer.detect(imgFrame);
            for(int i = 0; i < textBlocks.size();i++){
                TextBlock textBlock = textBlocks.get(textBlocks.keyAt(i));
                imgText += textBlock.getValue();
                imgText += "\n";
            }
            display_result.setText(imgText);
        }
    }
}
