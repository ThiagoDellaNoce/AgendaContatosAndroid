package com.example.thiag.contatos;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.thiag.contatos.dao.ContatoDAO;
import com.example.thiag.contatos.model.Contato;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class FormularioActivity extends AppCompatActivity
{

    private EditText txtNome;
    private EditText txtTelefone;
    private EditText txtSexo;

    private Contato contato;

    final String caminhoDiretorio = Environment.getExternalStorageDirectory().getAbsolutePath() + "/arqs/";

    File diretorio;
    File fileExt;
    String diretorioApp;

    Context context;

    public Contato getContato()
    {
        contato.setNome(txtNome.getText().toString());
        contato.setTelefone(txtTelefone.getText().toString());
        contato.setSexo(txtSexo.getText().toString());

        return contato;
    }

    public void preencheFormulario(Contato contato)
    {
        txtNome.setText(contato.getNome());
        txtTelefone.setText(contato.getTelefone());
        txtSexo.setText(contato.getSexo());
        this.contato = contato;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            ActionBar bar = getSupportActionBar();
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#329F36")));
            bar.setTitle("Criar novo contato");
        }

        txtNome = findViewById(R.id.nome);
        txtTelefone = findViewById(R.id.telefone);
        txtSexo = findViewById(R.id.sexo);
        contato = new Contato();

        Intent intent = getIntent();
        Contato contato = (Contato) intent.getSerializableExtra("contato");

        if(contato != null)
        {
            preencheFormulario(contato);
        }

        context = this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }

        switch (item.getItemId())
        {
            case R.id.menu_formulario_ok :
                Contato contato = getContato();
                ContatoDAO dao = new ContatoDAO(this);

                if(contato.getId() != null)
                {
                    dao.altera(contato);
                    salvaOperacao("alterado", contato);
                }
                else
                {
                    dao.insere(contato);
                    salvaOperacao("inserido", contato);
                }


                dao.close();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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
            if (ContextCompat.checkSelfPermission(this,WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                // Verifica se já mostramos o alerta e o usuário negou na 1ª vez.
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                { }
                else
                {
                    // Solicita a permissão
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
                }
            }
            else
            {
            }
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

}
