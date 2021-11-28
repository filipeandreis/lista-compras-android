package ftec.listadecompras.model;

import java.io.Serializable;
import java.text.DateFormat;

public class Listas implements Serializable {
    private Integer id;
    private String nomeLista;
    private String data;

    @Override
    public String toString() {
        return nomeLista;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeLista() {
        return nomeLista;
    }

    public void setNomeLista(String nomeLista) {
        this.nomeLista = nomeLista;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
