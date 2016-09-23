package com.example.daniel.tesisapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.daniel.tesisapp.beans.Notificacion;
import com.example.daniel.tesisapp.beans.NotificacionDAO;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener  {

    private Button btnBuscarEmpresa,btnMapa,btnBuscarViaje,btnViajesActuales;
    private SignInButton btnGoogle;
    private ImageButton btnComentario;
    private int PERMISSION_CODE_1= 23;
    private GoogleApiClient apiClient;
    private static final int RC_SIGN_IN = 1001;
    private ProgressDialog asd = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 23)
        {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                requestpermisions();
            }
        }

        btnBuscarViaje = (Button) findViewById(R.id.btnBuscarViaje);
        btnBuscarEmpresa = (Button) findViewById(R.id.btnBuscarEmpresa);
        btnComentario = (ImageButton) findViewById(R.id.ibtnComentario);
        btnMapa = (Button) findViewById(R.id.btnMapa);
        btnViajesActuales = (Button) findViewById(R.id.btnViajesActuales);
        btnGoogle = (SignInButton)findViewById(R.id.btngoogle);


        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();

        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, null)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        btnGoogle.setSize(SignInButton.SIZE_STANDARD);
        btnGoogle.setColorScheme(SignInButton.COLOR_LIGHT);
        btnGoogle.setScopes(gso.getScopeArray());
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(apiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });




        try {
            ListView listView = new ListView(this);
            listView = (ListView)findViewById(R.id.listCom);
            NotificacionDAO noti = new NotificacionDAO(this);
            ArrayList<Notificacion> notis = noti.listado();
            int count= notis.size();
            int c = 0;
            String alerta="";
            Notificacion notificacion;
            ArrayList<String> comentariosHora = new ArrayList<String>();
            while(c<count) {
                notificacion = notis.get(c);
                alerta = notificacion.getHora()+" - "+notificacion.getComentario();
                comentariosHora.add(alerta);
                c++;
            }
            ArrayAdapter<String> adapterAlerts= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, comentariosHora);
            listView.setAdapter(adapterAlerts);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        btnBuscarViaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BuscarViajeActivity.class);
                startActivity(intent);
            }
        });


        btnBuscarEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,BuscarEmpresaActivity.class);
                startActivity(intent);
            }
        });

        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViajesActualesActivity.class);
                startActivity(intent);
            }
        });

        btnComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Ubicacion.class);
                startActivity(intent);
            }
        });

        btnViajesActuales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListaViajesActualesActivity.class);
                startActivity(intent);
            }
        });
    }

    public void requestpermisions() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_CODE_1);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result =
                    Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            handleSignInResult(result);
        }
    }


    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "Error de conexion!", Toast.LENGTH_SHORT).show();
        Log.e("GoogleSignIn", "OnConnectionFailed: " + connectionResult);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            //Usuario logueado --> Mostramos sus datos
            GoogleSignInAccount acct = result.getSignInAccount();
            Toast.makeText(this,acct.getDisplayName()+" "+acct.getEmail(), Toast.LENGTH_SHORT).show();
        } else {
            //Usuario no logueado --> Lo mostramos como "Desconectado"
            Toast.makeText(this,"Desconectado", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(apiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void showProgressDialog() {
        if (asd == null) {
            asd = new ProgressDialog(this);
            asd.setMessage("Silent SignI-In");
            asd.setIndeterminate(true);
        }
        asd.show();
    }

    private void hideProgressDialog() {
        if (asd != null && asd.isShowing()) {
            asd.hide();
        }
    }

}
