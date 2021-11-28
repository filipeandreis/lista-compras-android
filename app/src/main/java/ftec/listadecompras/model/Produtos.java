package ftec.listadecompras.model;

import java.io.Serializable;

public class Produtos implements Serializable {

    private Integer id;
    private Integer listaId;
    private String nomeProduto;
    private int quantidade;

    @Override
    public String toString() {
        return nomeProduto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getListaId() {
        return listaId;
    }

    public void setListaId(Integer id) {
        this.listaId = id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
