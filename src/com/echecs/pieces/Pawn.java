package com.echecs.pieces;

import com.echecs.PartieEchecs;
import com.echecs.Position;
import com.echecs.util.EchecsUtil;

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
        if(!pos1.estVoisineDe(pos2)){
            return false;
        }


        if(pos1.estSurLaMemeDiagonaleQue(pos2)) {
            if (ligne1 < ligne2 && colonne1 < colonne2) {
                int j = ligne1 + 1;
                for (int i = colonne1 + 1; i < colonne2 + 1; i++) {
                    if (echiquier[j][i] != null) {
                        if (echiquier[ligne1][colonne1].getCouleur() != echiquier[j][i].getCouleur() && i == colonne2) {
                            return true;
                        }
                        return false;
                    }
                    j++;
                }
            } else if (ligne1 < ligne2 && colonne1 > colonne2) {
                int j = ligne1 + 1;
                for (int i = colonne1 - 1; i > colonne2 - 1; i--) {
                    if (echiquier[j][i] != null) {
                        if (echiquier[ligne1][colonne1].getCouleur() != echiquier[j][i].getCouleur() && i == colonne2) {
                            return true;
                        }
                        return false;
                    }
                    j++;
                }


            } else if (ligne1 > ligne2 && colonne1 < colonne2) {
                int j = ligne1 - 1;
                for (int i = colonne1 + 1; i < colonne2 + 1; i++) {
                    if (echiquier[j][i] != null) {
                        if (echiquier[ligne1][colonne1].getCouleur() != echiquier[j][i].getCouleur() && i == colonne2) {
                            return true;
                        }
                        return false;
                    }
                    j--;
                }
            } else if (ligne1 > ligne2 && colonne1 > colonne2) {
                int j = ligne1 - 1;
                for (int i = colonne1 - 1; i > colonne2 - 1; i--) {
                    if (echiquier[j][i] != null) {
                        if (echiquier[ligne1][colonne1].getCouleur() != echiquier[j][i].getCouleur() && i == colonne2) {
                            return true;
                        }
                        return false;
                    }
                    j--;
                }
            }
        }
        if(pos1.estSurLaMemeColonneQue(pos2)&&pos1.estVoisineDe(pos2)){
            if(ligne1>ligne2 && echiquier[ligne1][colonne1].getCouleur()=='b')
            {
                return true;

            }else if(ligne2>ligne1 && echiquier[ligne1][colonne1].getCouleur()=='n')
            {
                return true;

            }
        }



        return false;
    }
}