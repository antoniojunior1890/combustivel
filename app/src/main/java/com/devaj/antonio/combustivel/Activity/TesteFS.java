package com.devaj.antonio.combustivel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.devaj.antonio.combustivel.Model.Kaizen;
import com.devaj.antonio.combustivel.R;
import com.devaj.antonio.combustivel.Util.ImageOverlayView;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.stfalcon.frescoimageviewer.ImageViewer;

public class TesteFS extends AppCompatActivity {

    ImageOverlayView overlayView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_fs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
//        Log.i("TAG",intent.getStringExtra("imagem"));
//        Toast toast = makeText(this, intent.getStringExtra("imagem"), LENGTH_LONG);
//        toast.show();
        if(intent.getSerializableExtra("imagemKaizen") != null){
            Kaizen kaizen = (Kaizen) intent.getSerializableExtra("imagemKaizen");


            GenericDraweeHierarchyBuilder genericDraweeHierarchyBuilder = GenericDraweeHierarchyBuilder.newInstance(getResources())
                    .setFailureImage(R.drawable.sem_foto)
                    .setPlaceholderImage(R.drawable.loading);
            overlayView = new ImageOverlayView(this);

            String[] imagem = {kaizen.getImagemKaizen()};
            new ImageViewer.Builder(this, imagem)
                    .setStartPosition(0)
                    .setOverlayView(overlayView)
                    .setImageChangeListener(getImageChangerListener())
                    .setCustomDraweeHierarchyBuilder(genericDraweeHierarchyBuilder)
                    .show();

//            Picasso.get()
//                    .load(kaizen.getImagemKaizen())
//                    .placeholder (R.drawable.loading)
//                    .error (R.drawable.sem_foto)
//                    .into(photoView);

//            TextView textViewDescricao = findViewById(R.id.dummy_button);
//            textViewDescricao.setText(kaizen.getDescricaoKaizen());
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private ImageViewer.OnImageChangeListener getImageChangerListener(){
        return new ImageViewer.OnImageChangeListener() {
            @Override
            public void onImageChange(int position) {
//                overlayView.setShareText("TEST");
                overlayView.setDescription("TEST");
            }
        };
    }

}
