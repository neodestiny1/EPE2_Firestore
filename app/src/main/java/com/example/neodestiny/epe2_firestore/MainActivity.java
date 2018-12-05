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
import android.widget.Toast;

import com.example.neodestiny.epe2_firestore.model.Cliente;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Cliente clienteSelected;

    private List<Cliente> listCliente = new ArrayList<Cliente>();
    ArrayAdapter<Cliente> arrayAdapterCliente;

    EditText et_rut, et_nombre, et_telefono, et_email;
    ListView lv_lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_rut = findViewById(R.id.et_rutCliente);
        et_nombre = findViewById(R.id.et_nombreCliente);
        et_telefono = findViewById(R.id.et_telefonoCliente);
        et_email = findViewById(R.id.et_correoCliente);
        lv_lista = findViewById(R.id.lv_lista);
        inicializarFirebase();
        listarDatos();

        lv_lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clienteSelected=(Cliente) parent.getItemAtPosition(position);
                et_rut.setText(clienteSelected.getRut());
                et_nombre.setText(clienteSelected.getNombre());
                et_telefono.setText(clienteSelected.getTelefono());
                et_email.setText(clienteSelected.getCorreo());
            }
        });

    }

    private void listarDatos() {
        databaseReference.child("Cliente").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            listCliente.clear();
            for(DataSnapshot objSnaptshot: dataSnapshot.getChildren())
            {
                Cliente c = objSnaptshot.getValue(Cliente.class);
                listCliente.add(c);

                arrayAdapterCliente = new ArrayAdapter<Cliente>(MainActivity.this, android.R.layout.simple_list_item_1, listCliente);
                lv_lista.setAdapter(arrayAdapterCliente);
            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String nombre = et_nombre.getText().toString();
        String rut = et_rut.getText().toString();
        String telefono = et_telefono.getText().toString();
        String email = et_email.getText().toString();

        switch (item.getItemId()) {
            case R.id.ic_add:
                if (nombre.equals("") || rut.equals("") || telefono.equals("") || email.equals("")) {
                    validacion();
                } else {
                    Cliente c = new Cliente();
                    c.setUid(UUID.randomUUID().toString());
                    c.setRut(rut);
                    c.setNombre(nombre);
                    c.setTelefono(telefono);
                    c.setCorreo(email);
                    databaseReference.child("Cliente").child(c.getUid()).setValue(c);
                    Toast.makeText(this, "Cliente agregado", Toast.LENGTH_SHORT).show();
                    limpiarCajas();
                }
                break;
            case R.id.ic_save:
                if (nombre.equals("") || rut.equals("") || telefono.equals("") || email.equals("")) {
                    validacion();
                } else {
                    Cliente c = new Cliente();
                    c.setUid(clienteSelected.getUid());
                    c.setRut(et_rut.getText().toString().trim());
                    c.setNombre(et_nombre.getText().toString().trim());
                    c.setTelefono(et_telefono.getText().toString().trim());
                    c.setCorreo(et_email.getText().toString().trim());
                    databaseReference.child("Cliente").child(c.getUid()).setValue(c);
                    Toast.makeText(this, "Cliente actualizado", Toast.LENGTH_SHORT).show();
                    limpiarCajas();
                }
                break;
            case R.id.ic_delete:
                if (nombre.equals("") || rut.equals("") || telefono.equals("") || email.equals("")) {
                    validacion();
                } else {
                    Cliente c = new Cliente();
                    c.setUid(clienteSelected.getUid());
                    databaseReference.child("Cliente").child(c.getUid()).removeValue();
                    Toast.makeText(this, "Cliente eliminado", Toast.LENGTH_SHORT).show();
                    limpiarCajas();
                }
                break;

            default:
                break;
        }
        return true;
    }

    private void limpiarCajas() {
        et_nombre.setText("");
        et_email.setText("");
        et_rut.setText("");
        et_telefono.setText("");
    }

    private void validacion() {
        String nombre = et_nombre.getText().toString();
        String rut = et_rut.getText().toString();
        String telefono = et_telefono.getText().toString();
        String email = et_email.getText().toString();
        if (rut.equals("")) {
            et_rut.setError("Requerido");
            et_rut.requestFocus();
        } else if (nombre.equals("")) {
            et_nombre.setError("Requerido");
            et_nombre.requestFocus();
        } else if (telefono.equals("")) {
            et_telefono.setError("Requerido");
            et_telefono.requestFocus();
        } else if (email.equals("")) {
            et_email.setError("Requerido");
            et_email.requestFocus();
        }
    }

}
