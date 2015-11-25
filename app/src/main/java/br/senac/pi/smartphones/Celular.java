package br.senac.pi.smartphones;

import android.database.sqlite.SQLiteDatabase;
import android.view.View;

/**
 * Created by Aluno on 20/11/2015.
 */
public class Celular {
    private long id;
    private String modelo;
    private String fabricante;
    private double preco;

    public Celular() {
    }

    public Celular(int id, String modelo, String fabricante, Double preco) {
        this.id = id;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.preco = preco;
    }




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }



}
