package com.chat.echecs.pieces;

import com.echecs.Position;
import com.echecs.util.EchecsUtil;

public class Queen extends Piece {
    public Queen(char couleur) {
        super(couleur);
    }

    @Override
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        byte ligne1 = EchecsUtil.indiceLigne(pos1);
        byte colonne1 = EchecsUtil.indiceColonne(pos1);
        byte ligne2 = EchecsUtil.indiceLigne(pos2);
        byte colonne2 = EchecsUtil.indiceColonne(pos2);
        if (pos1.estSurLaMemeColonneQue(pos2)) {
            if (ligne1 > ligne2) {
                for (int i = ligne1; i < ligne2; i--) {
                    if (echiquier[i][colonne1] != null) {
                        return false;
                    }

                }
            } else {
                for (int i = ligne1; i > ligne2; i++) {
                    if (echiquier[i][colonne1] != null) {
                        return false;
                    }

                }
            }
        } else if (pos1.estSurLaMemeLigneQue(pos2)) {
            if (colonne1 > colonne2) {
                for (int i = colonne1; i < colonne2; i--) {
                    if (echiquier[ligne1][i] != null) {
                        return false;
                    }

                }
            } else {
                for (int i = colonne1; i > colonne2; i++) {
                    if (echiquier[ligne1][i] != null) {
                        return false;
                    }

                }

            }

        } else {return true;}


        if(pos1.estSurLaMemeDiagonaleQue(pos2)){
            if(ligne1<ligne2 && colonne1<colonne2){
                int j=ligne1;
                for(int i = colonne1; i<colonne2;i++){
                    if(echiquier[i][j]!=null){
                        return false;
                    }
                    j++;
                }
            }
            else if(ligne1<ligne2 && colonne1>colonne2){
                int j=ligne1;
                for(int i = colonne1; i>colonne2;i--){
                    if(echiquier[i][j]!=null){
                        return false;
                    }
                    j++;
                }


            }
            else if(ligne1>ligne2 && colonne1<colonne2){
                int j=ligne1;
                for(int i = colonne1; i<colonne2;i++){
                    if(echiquier[i][j]!=null){
                        return false;
                    }
                    j--;
                }
            }
            else if(ligne1>ligne2 && colonne1>colonne2){
                int j=ligne1;
                for(int i = colonne1; i>colonne2;i--){
                    if(echiquier[i][j]!=null){
                        return false;
                    }
                    j--;
                }
            }
            if(echiquier[colonne1][ligne1].getCouleur()!=echiquier[colonne2][ligne2].getCouleur()){
                return true;
            }
        }
    return false;
    }
}
