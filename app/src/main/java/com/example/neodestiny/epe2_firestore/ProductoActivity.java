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
import android.widget.RadioGroup;
import android.widget.Toast;

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
import java.util.UUID;

public class ProductoActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Producto productoSelected;

    private List<Producto> listProducto = new ArrayList<Producto>();
    ArrayAdapter<Producto> arrayAdapterProducto;
    EditText et_nombreMascota, et_tipoAnimal, et_dueno;
    RadioButton rb_macho, rb_hembra;
    ListView lv_lista2;
    RadioGroup rb_grupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        et_nombreMascota = findViewById(R.id.et_nombreMascota);
        et_tipoAnimal = findViewById(R.id.et_tipoAnimal);
        et_dueno = findViewById(R.id.et_dueno);
        rb_macho = findViewById(R.id.rb_macho);
        rb_hembra = findViewById(R.id.rb_hembra);
        lv_lista2 = findViewById(R.id.lv_lista2);
        rb_grupo = findViewById(R.id.rb_grupo);
        inicializarFirebase();
        listarDatos();

        lv_lista2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                productoSelected = (Producto) parent.getItemAtPosition(position);
                et_nombreMascota.setText(productoSelected.getNombre());
                et_tipoAnimal.setText(productoSelected.getTipoAnimal());
                et_dueno.setText(productoSelected.getNombreDueno());
                if (productoSelected.getSexo().equals("Macho")) {
                    rb_macho.setChecked(true);
                } else {
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
        databaseReference.child("Mascota").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listProducto.clear();
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
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
        String sexo = "";
        if (rb_macho.isChecked())
            sexo = "Macho";
        else if (rb_hembra.isChecked())
            sexo = "Hembra";


        switch (item.getItemId()) {
            case R.id.ic_add:
                if (nombre.equals("") || tipoAnimal.equals("") || dueño.equals("") || sexo.equals("")) {
                    validaciones();
                } else {
                    Producto p = new Producto();
                    p.setUid(UUID.randomUUID().toString());
                    p.setNombre(nombre);
                    p.setTipoAnimal(tipoAnimal);
                    p.setNombreDueno(dueño);
                    p.setSexo(sexo);
                    databaseReference.child("Mascota").child(p.getUid()).setValue(p);
                    Toast.makeText(this, "Mascota Agregada", Toast.LENGTH_SHORT).show();
                    limpiarCajas();
                }
                break;
            case R.id.ic_save:
                if (nombre.equals("") || tipoAnimal.equals("") || dueño.equals("") || sexo.equals("")) {
                    validaciones();
                } else {
                    Producto p = new Producto();
                    p.setUid(UUID.randomUUID().toString());
                    p.setNombre(nombre);
                    p.setTipoAnimal(tipoAnimal);
                    p.setNombreDueno(dueño);
                    p.setSexo(sexo);
                    databaseReference.child("Mascota").child(p.getUid()).setValue(p);
                    Toast.makeText(this, "Mascota Actualizada", Toast.LENGTH_SHORT).show();
                    limpiarCajas();
                }
                break;
            case R.id.ic_delete:
                if (nombre.equals("") || tipoAnimal.equals("") || dueño.equals("") || sexo.equals("")) {
                    validaciones();
                } else {
                    Producto p = new Producto();
                    p.setUid(productoSelected.getUid());
                    databaseReference.child("Mascota").child(p.getUid()).removeValue();
                    Toast.makeText(this, "Mascota Eliminada", Toast.LENGTH_SHORT).show();
                    limpiarCajas();
                }
                break;
            default:
                break;
        }
        return true;
    }

    private void limpiarCajas() {
        et_nombreMascota.setText("");
        et_tipoAnimal.setText("");
        et_dueno.setText("");
        rb_macho.setChecked(false);
        rb_hembra.setChecked(false);

    }

    private void validaciones() {
        String nombre = et_nombreMascota.getText().toString();
        String tipoAnimal = et_tipoAnimal.getText().toString();
        String dueño = et_dueno.getText().toString();
        String sexo = "";

        if (rb_macho.isChecked()) {
            sexo = "Macho";
        } else if (rb_hembra.isChecked()) {
            sexo = "Hembra";
        }
        if (nombre.equals("")) {
            et_nombreMascota.setError("Requerido");
            et_nombreMascota.requestFocus();
        } else if (tipoAnimal.equals("")) {
            et_tipoAnimal.setError("Requerido");
            et_tipoAnimal.requestFocus();
        } else if (dueño.equals("")) {
            et_dueno.setError("Requerido");
            et_dueno.requestFocus();
        } else if (sexo.equals("")) {
            rb_macho.setError("Requerido");
            rb_hembra.setError("Requerido");
            rb_grupo.requestFocus();
        }
    }


}
