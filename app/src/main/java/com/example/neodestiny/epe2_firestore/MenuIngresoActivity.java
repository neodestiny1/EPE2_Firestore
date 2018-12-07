package com.example.neodestiny.epe2_firestore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MenuIngresoActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private AdView mAdView;

    Button btn_cliente, btn_mascota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ingreso);
        btn_cliente = findViewById(R.id.btn_cliente);
        btn_mascota = findViewById(R.id.btn_mascota);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void seleccionMenu(View view) {
        switch (view.getId()) {
            case R.id.btn_cliente:
                startActivity(new Intent(MenuIngresoActivity.this, MainActivity.class));
                break;
            case R.id.btn_mascota:
                startActivity(new Intent(MenuIngresoActivity.this, ProductoActivity.class));
                break;
            default:
                break;
        }
    }


}
