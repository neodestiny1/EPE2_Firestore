package com.example.neodestiny.epe2_firestore;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.neodestiny.epe2_firestore.model.Cliente;
import com.example.neodestiny.epe2_firestore.model.Producto;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductoActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Producto productoSelected;

    private List<Producto> listProducto = new ArrayList<Producto>();
    ArrayAdapter<Producto> arrayAdapterProducto;
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
        inicializarFirebase();
        listarDatos();

        lv_lista2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                productoSelected=(Producto) parent.getItemAtPosition(position);
                et_nombreMascota.setText(productoSelected.getNombre());
                et_tipoAnimal.setText(productoSelected.getTipoAnimal());
                et_dueno.setText(productoSelected.getNombreDueno());
                if(productoSelected.getSexo().equals("Macho"))
                {
                    rb_macho.setChecked(true);
                }
                else
                {
                    rb_hembra.setChecked(true);
                }
            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void listarDatos() {
        databaseReference.child("Producto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listProducto.clear();
                for(DataSnapshot objSnaptshot: dataSnapshot.getChildren())
                {
                    Producto p = objSnaptshot.getValue(Producto.class);
                    listProducto.add(p);

                    arrayAdapterProducto = new ArrayAdapter<Producto>(ProductoActivity.this, android.R.layout.simple_list_item_1, listProducto);
                    lv_lista2.setAdapter(arrayAdapterProducto);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String nombre = et_nombreMascota.getText().toString();
        String tipoAnimal = et_tipoAnimal.getText().toString();
        String dueño = et_dueno.getText().toString();
        String sexo="";
        if(rb_macho.isChecked())
        {
            sexo="Macho";
        }
        else
        {
            sexo="Hembra";
        }

        
        return true;
    }

}
