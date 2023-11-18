package com.chat.echecs.pieces;

import com.chat.echecs.PartieEchecs;
import com.chat.echecs.Position;
import com.chat.echecs.util.EchecsUtil;

public class Pawn extends Piece {
    public Pawn(char couleur) {
        super(couleur);
    }

    @Override
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        byte ligne1 = EchecsUtil.indiceLigne(pos1);
        byte colonne1 = EchecsUtil.indiceColonne(pos1);
        byte ligne2 = EchecsUtil.indiceLigne(pos2);
        byte colonne2 = EchecsUtil.indiceColonne(pos2);

        PartieEchecs partieEchecs = new PartieEchecs();
        if(partieEchecs.deplace(pos1,pos2)){
            if(pos1.estSurLaMemeDiagonaleQue(pos2)){
                if(ligne1<ligne2 && colonne1<colonne2){
                    for(int i = colonne1; i<colonne2;i++){
                        for(int j = ligne1; j<ligne2;j++){
                            if(echiquier[i][j]!=null){
                                return false;
                            }
                        }
                    }
                }
                else if(ligne1<ligne2 && colonne1>colonne2){
                    for(int i = colonne1; i>colonne2;i--){
                        for(int j = ligne1; j<ligne2;j++){
                            if(echiquier[i][j]!=null){
                                return false;
                            }
                        }
                    }

                }
                else if(ligne1>ligne2 && colonne1<colonne2){
                    for(int i = colonne1; i<colonne2;i++){
                        for(int j = ligne1; j>ligne2;j--){
                            if(echiquier[i][j]!=null){
                                return false;
                            }
                        }
                    }

                }
                else if(ligne1>ligne2 && colonne1>colonne2){
                    for(int i = colonne1; i>colonne2;i--){
                        for(int j = ligne1; j>ligne2;j--){
                            if(echiquier[i][j]!=null){
                                return false;
                            }
                        }
                    }

                }
            }
        }



        return false;
    }
}