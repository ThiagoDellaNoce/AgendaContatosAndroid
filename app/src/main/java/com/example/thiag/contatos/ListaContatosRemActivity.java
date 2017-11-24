package com.example.thiag.contatos;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thiag.contatos.dao.ContatoDAO;
import com.example.thiag.contatos.model.Contato;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ListaContatosRemActivity extends AppCompatActivity
{
    final String caminhoDiretorio = Environment.getExternalStorageDirectory().getAbsolutePath() + "/arqs/";

    File diretorio;
    File fileExt;
    String diretorioApp;

    Context context = this;
    private ListView listaContatos;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contatos);

        // add back arrow to toolbar
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            ActionBar bar = getSupportActionBar();
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E83F6F")));
            bar.setTitle("Remover um contato");
        }

        listaContatos = (ListView) findViewById(R.id.lista_contatos);

        listaContatos.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setMessage("Você tem certeza que deseja remover este contato?");

                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Contato contato = (Contato) listaContatos.getItemAtPosition(position);
                        ContatoDAO dao = new ContatoDAO(ListaContatosRemActivity.this);
                        salvaOperacao("deletado", contato);
                        dao.deleta(contato);
                        dao.close();

                        carregaLista();
                        Toast.makeText(context, "Contato removido com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Não", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Toast.makeText(ListaContatosRemActivity.this, "Cancelado com sucesso.", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

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

    private void salvaOperacao(String tipo, Contato contato)
    {
        String operacoes;

        // define o diretório
        diretorioApp = caminhoDiretorio;
        diretorio = new File(diretorioApp);
        diretorio.mkdirs();

        operacoes = mostraHistorico() + "Contato " + tipo + ": \" " + contato + " -o0o-\n";

        // define o arquivo
        fileExt = new File(diretorioApp, getString(R.string.arqName));
        fileExt.getParentFile().mkdir();

        // salva
        FileOutputStream fosExt;
        try
        {
            fosExt = new FileOutputStream(fileExt);
            fosExt.write(operacoes.getBytes());
            fosExt.close();
        }
        catch (IOException e)
        {
            // Se não possui permissão
            if (ContextCompat.checkSelfPermission(this,WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // Verifica se já mostramos o alerta e o usuário negou na 1ª vez.
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                }
                else
                {
                    // Solicita a permissão
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
                }
            }
            else
            { }
        }
    }

    private String mostraHistorico()
    {
        File arq;
        String texto = "";
        String lstrlinha;

        try
        {
            arq = new File(caminhoDiretorio, getString(R.string.arqName));
            BufferedReader br = new BufferedReader(new FileReader(arq));

            while ((lstrlinha = br.readLine()) != null)
            {
                if (!texto.equals(""))
                {
                    texto = texto + "\n";
                }
                texto = texto + lstrlinha;
            }
        }
        catch (Exception e)
        {
            texto = "";
        }

        return texto;
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