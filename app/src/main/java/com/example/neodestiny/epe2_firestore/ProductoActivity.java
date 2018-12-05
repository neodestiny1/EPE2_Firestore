package com.example.neodestiny.epe2_firestore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;

public class ProductoActivity extends AppCompatActivity {

    EditText et_nombreMascota, et_tipoAnimal, et_dueno;
    RadioButton rb_macho, rb_hembra;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
    }
}
