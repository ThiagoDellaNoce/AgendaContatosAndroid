package com.example.thiag.contatos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.thiag.contatos.dao.ContatoDAO;
import com.example.thiag.contatos.model.Contato;

import java.io.Serializable;
import java.util.List;

public class ListaContatosAltActivity extends AppCompatActivity
{
    private ListView listaContatos;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contatos);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            ActionBar bar = getSupportActionBar();
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#02CBC2")));
            bar.setTitle("Alterar um contato");
        }

        listaContatos = (ListView) findViewById(R.id.lista_contatos);

        listaContatos.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                Contato contato = (Contato) listaContatos.getItemAtPosition(position);
                Intent intent = new Intent(ListaContatosAltActivity.this, FormularioAlteracaoActivity.class);
                intent.putExtra("contato", (Serializable) contato);
                startActivity(intent);
            }
        });

        context = this;

        registerForContextMenu(listaContatos);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        carregaLista();
    }

    private void carregaLista()
    {
        ContatoDAO dao = new ContatoDAO(this);
        List<Contato> contatos = dao.buscaContatos();
        dao.close();

        ArrayAdapter<Contato> adapter = new ArrayAdapter<Contato>(this, android.R.layout.simple_list_item_1, contatos);
        listaContatos.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}


