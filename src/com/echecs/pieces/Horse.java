package com.echecs.pieces;

import com.echecs.PartieEchecs;
import com.echecs.Position;
import com.echecs.util.EchecsUtil;

public class Horse extends Piece{
    public Horse(char couleur) {
        super(couleur);
    }

    @Override
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        byte ligne1 = EchecsUtil.indiceLigne(pos1);
        byte colonne1 = EchecsUtil.indiceColonne(pos1);
        byte ligne2 = EchecsUtil.indiceLigne(pos2);
        byte colonne2 = EchecsUtil.indiceColonne(pos2);
        PartieEchecs partieEchecs = new PartieEchecs();
        byte compCol=  (byte)Math.abs(colonne1-colonne2);
        byte compLig= (byte)Math.abs(ligne1-ligne2);
            if(compLig+compCol==3 && compLig>0 && compCol>0 ){
                if(echiquier[ligne2][colonne2]==null){
                    return true;
                }else if(echiquier[ligne1][colonne1].getCouleur()!=echiquier[ligne2][colonne2].getCouleur()){
                    return true;
                }
            }

        return false;
    }
}
