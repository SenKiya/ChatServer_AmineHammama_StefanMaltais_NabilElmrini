package com.chat.serveur;
public class Invitation {
    private String alias1;
    private String alias2;

    // Constructeur
    public Invitation(String alias1, String alias2) {
        this.alias1 = alias1;
        this.alias2 = alias2;
    }

    // M�thodes getters et setters
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