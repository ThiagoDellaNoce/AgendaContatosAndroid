package com.example.thiag.contatos;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ListaHistoricoActivity extends AppCompatActivity
{

    final String caminhoDiretorio = Environment.getExternalStorageDirectory().getAbsolutePath() + "/arqs/";
    TextView dados;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_historico);

        // add back arrow to toolbar
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            ActionBar bar = getSupportActionBar();
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffbf00")));
            bar.setTitle("Dados sincronizados");
        }

        dados = (TextView) findViewById(R.id.txtDados);

        File arq;
        String lstrlinha;

        try
        {
            arq = new File(caminhoDiretorio, getString(R.string.arqName));
            BufferedReader br = new BufferedReader(new FileReader(arq));

            while ((lstrlinha = br.readLine()) != null)
            {
                if (!lstrlinha.equals(""))
                {
                    dados.setText("\n");
                }

                String[] dadosAux = lstrlinha.split("-o0o-");

                for (int i = 0; i < dadosAux.length; i++)
                {
                    dados.append(dadosAux[i]);
                    dados.append("\n");
                }
            }
        }
        catch (Exception e)
        {
            dados.setText(R.string.semhistorico);
        }
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
