package com.example.neodestiny.epe2_firestore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.neodestiny.epe2_firestore.model.Cliente;
import com.example.neodestiny.epe2_firestore.model.Producto;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProductoActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Producto productoSelected;

    private List<Producto> listCliente = new ArrayList<Producto>();
    ArrayAdapter<Producto> arrayAdapterCliente;
    EditText et_nombreMascota, et_tipoAnimal, et_dueno;
    RadioButton rb_macho, rb_hembra;
    ListView lv_lista2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        et_nombreMascota=findViewById(R.id.et_nombreMascota);
        et_tipoAnimal=findViewById(R.id.et_tipoAnimal);
        et_dueno=findViewById(R.id.et_dueno);
        rb_macho=findViewById(R.id.rb_macho);
        rb_hembra=findViewById(R.id.rb_hembra);
        lv_lista2=findViewById(R.id.lv_lista2);
    }


}
