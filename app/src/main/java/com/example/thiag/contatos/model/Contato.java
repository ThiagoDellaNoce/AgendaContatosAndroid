package com.example.thiag.contatos.model;

import java.io.Serializable;

/**
 * Created by thiag on 12/11/2017.
 */

public class Contato implements Serializable
{
    private Long id;
    private String nome;
    private String telefone;
    private String sexo;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public String getTelefone()
    {
        return telefone;
    }

    public void setTelefone(String telefone)
    {
        this.telefone = telefone;
    }

    public String getSexo()
    {
        return sexo;
    }

    public void setSexo(String sexo)
    {
        this.sexo = sexo;
    }

    @Override
    public String toString()
    {
        return getId() + " - " + getNome() + " - " + getTelefone() + " - " + getSexo();
    }
}
