package com.example.thiag.contatos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{

    Button listar;
    Button inserir;
    Button remover;
    Button alterar;
    Button sincronizar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listar = findViewById(R.id.btnlistar);
        inserir = findViewById(R.id.btnInserir);
        remover = findViewById(R.id.btnDeletar);
        alterar = findViewById(R.id.btnAlterar);
        sincronizar = findViewById(R.id.btnSincronizar);

        listar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, ListaContatosLisActivity.class);
                startActivity(intent);
            }
        });

        inserir.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
                startActivity(intent);
            }
        });

        remover.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, ListaContatosRemActivity.class);
                startActivity(intent);
            }
        });

        alterar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, ListaContatosAltActivity.class);
                startActivity(intent);
            }
        });

        sincronizar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent (MainActivity.this, ListaHistoricoActivity.class);
                startActivity(intent);
            }
        });
    }

}
