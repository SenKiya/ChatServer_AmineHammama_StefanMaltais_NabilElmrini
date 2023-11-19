package com.echecs.pieces;

import com.echecs.PartieEchecs;
import com.echecs.Position;

public class King extends Piece {
    public King(char couleur) {
        super(couleur);
    }

    @Override
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
         PartieEchecs partieEchecs = new PartieEchecs();
        if (partieEchecs.deplace(pos1, pos2)) {
            if (pos1.estVoisineDe(pos2)) {
                return true;
            }

        }
        return false;
    }
}