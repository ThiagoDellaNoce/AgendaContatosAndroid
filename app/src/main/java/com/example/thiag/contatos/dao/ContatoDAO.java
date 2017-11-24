package com.example.thiag.contatos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.thiag.contatos.model.Contato;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thiag on 12/11/2017.
 */

public class ContatoDAO extends SQLiteOpenHelper
{

    public ContatoDAO(Context context)
    {
        super(context, "Contatos", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String sql = "CREATE TABLE Contatos (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, telefone TEXT, sexo TEXT)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        String sql = "DROP TABLE IF EXISTS Contatos";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public void insere(Contato contato)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoContato(contato);

        db.insert("Contatos", null, dados);
    }

    @NonNull
    private ContentValues pegaDadosDoContato(Contato contato)
    {
        ContentValues dados = new ContentValues();
        dados.put("nome", contato.getNome());
        dados.put("telefone", contato.getTelefone());
        dados.put("sexo", contato.getSexo());
        return dados;
    }

    public List<Contato> buscaContatos()
    {
        String sql = "SELECT * from Contatos;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Contato> contatos = new ArrayList<>();

        while(c.moveToNext())
        {
            Contato contato = new Contato();
            contato.setId(c.getLong(c.getColumnIndex("id")));
            contato.setNome(c.getString(c.getColumnIndex("nome")));
            contato.setTelefone(c.getString(c.getColumnIndex("telefone")));
            contato.setSexo(c.getString(c.getColumnIndex("sexo")));
            contatos.add(contato);
        }
        c.close();
        return contatos;
    }

    public void deleta(Contato contato)
    {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {contato.getId().toString()};
        db.delete("Contatos", "id = ?", params);
    }

    public void altera(Contato contato)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoContato(contato);

        String[] params = {contato.getId().toString()};
        db.update("Contatos", dados, "id = ?", params);
    }
}
