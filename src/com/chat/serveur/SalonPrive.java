package com.chat.serveur;
import com.chat.echecs.PartieEchecs;
public class SalonPrive {
    private String alias1;
    private String alias2;
    private PartieEchecs partieEchecs;

    public PartieEchecs getPartieEchecs() {
        return partieEchecs;
    }

    public void setPartieEchecs(PartieEchecs partieEchecs) {
        this.partieEchecs = partieEchecs;
    }

    // Constructeur
    public SalonPrive(String alias1, String alias2) {
        this.alias1 = alias1;
        this.alias2 = alias2;
        this.partieEchecs = null;
    }

    // Méthodes getters et setters
    public String getAlias1() {
        return alias1;
    }

    public void setAlias1(String alias1) {
        this.alias1 = alias1;
    }

    public String getAlias2() {
        return alias2;
    }

    public void setAlias2(String alias2) {
        this.alias2 = alias2;
    }


}