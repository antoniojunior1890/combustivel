package com.devaj.antonio.combustivel.Activity;

import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.devaj.antonio.combustivel.DataBase.DAO.Implement.UsuarioDaoImpl;
import com.devaj.antonio.combustivel.Fragments.FragmentAtividades;
import com.devaj.antonio.combustivel.Fragments.FragmentControlCartRiscosEspecificos;
import com.devaj.antonio.combustivel.Fragments.FragmentControlCartSSMA;
import com.devaj.antonio.combustivel.Fragments.FragmentDBC;
import com.devaj.antonio.combustivel.Fragments.FragmentDBCAvaliado;
import com.devaj.antonio.combustivel.Fragments.FragmentEscalaOperador;
import com.devaj.antonio.combustivel.Fragments.FragmentEscalaTurma;
import com.devaj.antonio.combustivel.Fragments.FragmentExibirCards;
import com.devaj.antonio.combustivel.Fragments.FragmentInformacoes;
import com.devaj.antonio.combustivel.Fragments.FragmentKaizen;
import com.devaj.antonio.combustivel.Fragments.FragmentRegrasOuro;
import com.devaj.antonio.combustivel.Fragments.FragmentCanalCombustivel;
import com.devaj.antonio.combustivel.Fragments.FragmentRiscosEspecificos;
import com.devaj.antonio.combustivel.R;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FragmentManager fragmentManager;
    private Fragment fragment = null;
    FragmentTransaction fragmentTransaction;
    private static final String TAG = "COMBUSTIVEL";
    public UsuarioDaoImpl usuarioDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        Log.i(TAG, "Nome: "+ServicoVerifica.class.getName());

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        fragment = new FragmentInformacoes();
//        fragment = new FragmentKaizen();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container_wrapper, fragment,"home").commit();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if(getIntent().getBooleanExtra ("SAIR",false)){
            finish();
        }else {
            usuarioDao = new UsuarioDaoImpl(this);
//            Log.i(TAG, "TAM : " + usuarioDao.pegarTamanho());
            if (usuarioDao.pegarTamanho() == 0) {

                startActivity(new Intent(this, LoginActivity.class));
            }else {

//                if(isServiceRunning(ServicoVerifica.class.getName())){
////                    startService(new Intent(getBaseContext(), ServicoVerifica.class));
//                stopService(new Intent(getBaseContext(), ServicoVerifica.class));
//                }


                View headerView = navigationView.getHeaderView(0);

                TextView usuarioTxt = headerView.findViewById(R.id.nomeUsuario);
                TextView sairTxt = headerView.findViewById(R.id.sairUsuario);
                Button usuarioImagem = headerView.findViewById(R.id.letraUsuario);
//
//                usuarioImagem.setImageResource(R.drawable.dia_folga);


                usuarioTxt.setText(usuarioDao.buscarUsuario().getNome());
                String letra = (String) usuarioTxt.getText();
                usuarioImagem.setText(String.valueOf(letra.charAt(0)));
                sairTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog alertDialog;

                        new AlertDialog.Builder(MainActivity.this).setTitle("Sair.")
                            .setMessage("Desejar realizar logout?")
                            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    usuarioDao.apagarTabela();
                                    usuarioDao.criarTabela();
                                    finish();
//                                    stopService(new Intent(getBaseContext(), ServicoVerifica.class));
//                                    startActivity(new Intent(getApplication(), LoginActivity.class));
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    Toast.makeText(getApplication(), "Usuário apagado.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Log.i(TAG, "não");
                                }
                            }).show();

//                        alertDialog = builder.create();
//                        alertDialog.show();


                    }
                });
            }
            if(getIntent().getBooleanExtra ("ATIVIDADES",false)){
                Log.i(TAG, "Chamou atividades");

                fragment = new FragmentAtividades();

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container_wrapper, fragment).addToBackStack(null).commit();

                drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
    }


    @Override
    public void onBackPressed() {
        List<Fragment> fragments = fragmentManager.getFragments();
        Fragment visivel = fragments.get(0);
//        Log.i(TAG, ""+visivel.getTag());

//        android.app.Fragment visivel = getFragmentManager().findFragmentByTag("home");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);


        if(visivel.getTag() != null && visivel.getTag().equals("home") ){
            finish();
        }else{
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_container_wrapper, new FragmentInformacoes(), "home").commit();
        }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        boolean flagInfo = false;

        if (id == R.id.nav_informe) {
            flagInfo = true;
            fragment = new FragmentInformacoes();
        } else if (id == R.id.nav_kaizens) {
            fragment = new FragmentKaizen();
        } else if (id == R.id.nav_dbc) {
            fragment = new FragmentDBCAvaliado();
        } else if (id == R.id.nav_escala_turma) {
            fragment = new FragmentEscalaTurma();
        } else if (id == R.id.nav_escala_operador) {
            fragment = new FragmentEscalaOperador();
        } else if (id == R.id.nav_atividades) {
            fragment = new FragmentAtividades();
        } else if (id == R.id.nav_regra_ouro) {
            fragment = new FragmentRegrasOuro();
        }else if(id == R.id.nav_riscos_específicos){
            fragment = new FragmentExibirCards("RE");
//            fragment = new FragmentControlCartRiscosEspecificos();
        }else if (id == R.id.nav_ssma) {
//            fragment = new FragmentControlCartSSMA();
            fragment = new FragmentExibirCards("SSMA");
        } else if (id == R.id.nav_canal_combustivel) {
            fragment = new FragmentCanalCombustivel();
        }




        fragmentTransaction = fragmentManager.beginTransaction();
        if(flagInfo){
            fragmentTransaction.replace(R.id.main_container_wrapper, fragment,"home").commit();
        }else {
            fragmentTransaction.replace(R.id.main_container_wrapper, fragment).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean isServiceRunning(String servicoClassName){
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);

        for(int i = 0; i < services.size(); i++){
            Log.i(TAG, "serv n: "+i+" class name : "+services.get(i).service.getClassName());
            if(services.get(i).service.getClassName().compareTo(servicoClassName)==0){
                return true;
            }
        }
        return false;
    }
}