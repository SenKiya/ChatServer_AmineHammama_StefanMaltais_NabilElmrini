package com.chat.commun.evenement;

/**
 * Cette classe repr�sente un �v�nement. Un �v�nement est caract�ris� par sa
 * source (objet sur lequel s'est produit l'�v�nement) son type (String) et peut avoir des
 * arguments regroup�s dans une chaine de caract�res (String).
 *
 * @author Abdelmoum�ne Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class Evenement{
    private Object source;
    private final String type, argument;

    /**
     * Construit un �v�nement.
     *
     * @param source   La source de l'�v�nement
     * @param type     Le type de l'�v�nement
     * @param argument Chaine de caract�res contenant les arguments de l'�v�nement
     */
    public Evenement(Object source, String type, String argument) {
        this.source = source;
        this.type = type;
        this.argument = argument;
    }

    /**
     * Retourne la source de l'�v�nement
     *
     * @return Object source de l'�v�nement
     */
    public Object getSource() {
        return source;
    }

    /**
     * Retourne le type de l'�v�nement
     *
     * @return String type de l'�v�nement
     */
    public String getType() {
        return type;
    }

    /**
     * Retourne l'argument de l'�v�nement
     *
     * @return String argument de l'�v�nement
     */
    public String getArgument() {
        return argument;
    }
}
