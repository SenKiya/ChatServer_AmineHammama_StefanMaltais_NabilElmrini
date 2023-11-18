package com.chat.echecs.pieces;

import com.chat.echecs.PartieEchecs;
import com.chat.echecs.Position;
import com.chat.echecs.util.EchecsUtil;

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

        PartieEchecs partieEchecs = new PartieEchecs();
        if(partieEchecs.deplace(pos1,pos2)) {


            if (pos1.estSurLaMemeColonneQue(pos2) == (false) || pos1.estSurLaMemeLigneQue(pos2) == (false)) {
                return false;
            }


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

            }
            return true;
        }
        return false;
    }
}
