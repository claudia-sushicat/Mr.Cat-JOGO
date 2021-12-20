package Melhorias;

import processing.core.PImage;

public abstract class Melhoria {

    private int exploitsPorSegundo;
    private String descricao;
    private int quantidade = 0;
    private int custoCompra;

    private String foto;

    public Melhoria(int exploitsPorSegundo, String descricao,  int custoCompra, String foto) {
        this.exploitsPorSegundo = exploitsPorSegundo;
        this.descricao = descricao;
        this.custoCompra = custoCompra;
        this.foto = foto;
    }

    public void compra(){
        this.quantidade += 1;
    }

    public int getExploitsPorSegundo() {
        return exploitsPorSegundo;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public int getCustoCompra() {
        return custoCompra;
    }

    public String getFoto() {
        return foto;
    }
}
