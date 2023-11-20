package com.echecs.pieces;

import com.echecs.PartieEchecs;
import com.echecs.Position;
import com.echecs.util.EchecsUtil;

public class Bishop extends Piece{
    public Bishop(char couleur) {
        super(couleur);
    }

    @Override
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        byte ligne1 = EchecsUtil.indiceLigne(pos1);
        byte colonne1 = EchecsUtil.indiceColonne(pos1);
        byte ligne2 = EchecsUtil.indiceLigne(pos2);
        byte colonne2 = EchecsUtil.indiceColonne(pos2);

        PartieEchecs partieEchecs = new PartieEchecs();
            if(pos1.estSurLaMemeDiagonaleQue(pos2)){
                if(ligne1<ligne2 && colonne1<colonne2){
                    int j=ligne2;
                    for(int i = colonne2; i>colonne1;i--){
                        if(echiquier[j][i]!=null){
                            return false;
                        }
                        j++;
                    }
                }
                else if(ligne1<ligne2 && colonne1>colonne2){
                    int j=ligne2;
                    for(int i = colonne2; i<colonne1;i++){
                        if(echiquier[j][i]!=null){
                            return false;
                        }
                        j++;
                    }


                }
                else if(ligne1>ligne2 && colonne1<colonne2){
                    int j=ligne2;
                    for(int i = colonne2; i>colonne1;i--){
                        if(echiquier[j][i]!=null){
                            return false;
                        }
                        j--;
                    }
                }
                else if(ligne1>ligne2 && colonne1>colonne2){
                    int j=ligne2;
                    for(int i = colonne2; i<colonne1;i++){
                        if(echiquier[j][i]!=null){
                            return false;
                        }
                        j--;
                    }
                }
                if(echiquier[ligne2][colonne2]==null){
                    return true;
                }else if(echiquier[ligne1][colonne1].getCouleur()!=echiquier[ligne2][colonne2].getCouleur()){
                    return true;
                }
            }




        return false;
    }
}
