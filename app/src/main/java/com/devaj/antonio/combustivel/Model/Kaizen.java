package com.devaj.antonio.combustivel.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by antonio on 12/08/17.
 */
public class Kaizen implements Serializable{
    private final String codKaizen;
    private final String descricaoKaizen;
    private final Date dataKaizen;
    private final String imagemKaizen;

    public Kaizen(String codKaizen, String descricaoKaizen, Date dataKaizen, String imagemKaizen) {
        this.codKaizen = codKaizen;
        this.descricaoKaizen = descricaoKaizen;
        this.dataKaizen = dataKaizen;
        this.imagemKaizen = imagemKaizen;
    }

    public String getCodKaizen() {
        return codKaizen;
    }

    public String getDescricaoKaizen() {
        return descricaoKaizen;
    }

    public Date getDataKaizen() {
        return dataKaizen;
    }

    public String getImagemKaizen() {
        return imagemKaizen;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Kaizen)) {
            return false;
        }
        final Kaizen other = (Kaizen) obj;
        return this.getCodKaizen().equals(other.getCodKaizen());
    }

    @Override
    public String toString() {
        return "id: "+ this.codKaizen
                + ", descricao: "+ this.descricaoKaizen
                + ", data: "+ this.dataKaizen
                + ", imagem: "+ this.imagemKaizen;
    }
}