package com.example.brian.progproj;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.mvc.imagepicker.ImagePicker;

import java.util.ArrayList;

public class AddFarmActivity extends AppCompatActivity {

    Bitmap icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_farm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImagePicker.setMinQuality(600, 600);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        icon = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
        CircularImageView view = (CircularImageView) findViewById(R.id.imageButton);
        if(icon != null)
            view.setImageBitmap(icon);
    }

    public void onSelectImage(View view){
        ImagePicker.pickImage(this, "Select your image:");
    }
    public void onClick(View view){
        final AddFarmActivity activity = this;

        EditText textbox = (EditText)findViewById(R.id.editText);
        if(textbox.getText().length() == 0){
            Snackbar.make(findViewById(android.R.id.content), "Please Fill Out All Fields", Snackbar.LENGTH_LONG).setDuration(1800).show();
            return;
        }
        if(icon == null){
            Snackbar.make(findViewById(android.R.id.content), "Please select an image", Snackbar.LENGTH_LONG).setDuration(1800).show();
            return;
        }
        TinyDB db = new TinyDB(this.getApplicationContext());
        if(db.getString(textbox.getText().toString()) != null){
            Snackbar.make(findViewById(android.R.id.content), "A Farm with this name already exists!", Snackbar.LENGTH_LONG).setDuration(1800).show();
            return;
        }
        ArrayList<String> farms = new ArrayList<>();
        if(db.getListString("Farms") != null){
            farms = db.getListString("Farms");
        }
        RippleInstance r = new RippleInstance(textbox.getText().toString(), Bitmap.createScaledBitmap(icon, 128,  icon.getHeight()/(icon.getWidth()/128), false));
        String farmName = r.toString();
        farms.add(farmName);
        db.putListString("Farms", farms);
        setResult(RESULT_OK);
        finish();
    }

}
