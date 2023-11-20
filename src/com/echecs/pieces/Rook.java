package com.echecs.pieces;

import com.echecs.PartieEchecs;
import com.echecs.Position;
import com.echecs.util.EchecsUtil;

public class Rook extends Piece {
    public Rook(char couleur) {
        super(couleur);
    }

    @Override
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        byte ligne1 = EchecsUtil.indiceLigne(pos1);
        byte colonne1 = EchecsUtil.indiceColonne(pos1);
        byte ligne2 = EchecsUtil.indiceLigne(pos2);
        byte colonne2 = EchecsUtil.indiceColonne(pos2);



        if (!pos1.estSurLaMemeColonneQue(pos2) && !pos1.estSurLaMemeLigneQue(pos2)) {
            return false;
        }


        if (pos1.estSurLaMemeColonneQue(pos2)) {
            if (ligne1 > ligne2) {
                for (int i = ligne2; i < ligne1; i++) {
                    if (echiquier[i][colonne1] != null) {
                        return false;
                    }

                }
            } else {
                for (int i = ligne2; i > ligne1; i--) {
                    if (echiquier[i][colonne1] != null) {
                        return false;
                    }

                }
            }

        } else if (pos1.estSurLaMemeLigneQue(pos2)) {
            if (colonne1 > colonne2) {
                for (int i = colonne2; i < colonne1; i++) {
                    if (echiquier[ligne1][i] != null) {
                        return false;
                    }

                }
            } else {
                for (int i = colonne2; i > colonne1; i--) {
                    if (echiquier[ligne1][i] != null) {
                        return false;
                    }

                }

            }
        }
        if(echiquier[ligne2][colonne2]==null){
            return true;
        }else if(echiquier[ligne1][colonne1].getCouleur()!=echiquier[ligne2][colonne2].getCouleur()){
            return true;
        }

        return false;
    }
}
