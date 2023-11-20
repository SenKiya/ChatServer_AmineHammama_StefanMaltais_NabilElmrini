package com.echecs;

import com.echecs.pieces.*;
import com.echecs.util.EchecsUtil;

import java.util.Iterator;

import java.util.Vector;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Représente une partie de jeu d'échecs. Orcheste le déroulement d'une partie :
 * déplacement des pièces, vérification d'échec, d'échec et mat,...
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class    PartieEchecs {
    /**
     * Grille du jeu d'échecs. La ligne 0 de la grille correspond à la ligne
     * 8 de l'échiquier. La colonne 0 de la grille correspond à la colonne a
     * de l'échiquier.
     */
    private Piece[][] echiquier;

    private String aliasJoueur1, aliasJoueur2;
    private char couleurJoueur1, couleurJoueur2;

    /**
     * La couleur de celui à qui c'est le tour de jouer (n ou b).
     */
    private char tour = 'b'; //Les blancs commencent toujours
    private Vector<Position> positions;
    private Iterator<Position> iterateur;
    private EtatPartieEchecs etat = new EtatPartieEchecs();
    /**
     * Crée un échiquier de jeu d'échecs avec les pièces dans leurs positions
     * initiales de début de partie.
     * Répartit au hasard les couleurs n et b entre les 2 joueurs.
     */
    public PartieEchecs() {
        positions =new Vector<Position>();
        iterateur = positions.iterator();
        echiquier = new Piece[8][8];
        //Placement des pièces :
        echiquier[0][0] = new Rook('n');
        echiquier[0][1] = new Horse('n');
        echiquier[0][2] = new Bishop('n');
        echiquier[0][3] = new Queen('n');
        echiquier[0][4] = new King('n');
        echiquier[0][5] = new Bishop('n');
        echiquier[0][6] = new Horse('n');
        echiquier[0][7] = new Rook('n');

        for (int i = 0; i < 8; i++) {
            echiquier[1][i] = new Pawn('n');
        }

        echiquier[7][0] = new Rook('b');
        echiquier[7][1] = new Horse('b');
        echiquier[7][2] = new Bishop('b');
        echiquier[7][3] = new Queen('b');
        echiquier[7][4] = new King('b');
        echiquier[7][5] = new Bishop('b');
        echiquier[7][6] = new Horse('b');
        echiquier[7][7] = new Rook('b');

        for (int i = 0; i < 8; i++) {
            echiquier[6][i] = new Pawn('b');

        }


    }

    /**
     * Change la main du jeu (de n à b ou de b à n).
     */
    public void changerTour() {
        if (tour == 'b')
            tour = 'n';
        else
            tour = 'b';
    }

    /**
     * Tente de déplacer une pièce d'une position à une autre sur l'échiquier.
     * Le déplacement peut échouer pour plusieurs raisons, selon les règles du
     * jeu d'échecs. Par exemples :
     * Une des positions n'existe pas;
     * Il n'y a pas de pièce à la position initiale;
     * La pièce de la position initiale ne peut pas faire le mouvement;
     * Le déplacement met en échec le roi de la même couleur que la pièce.
     *
     * @param initiale Position la position initiale
     * @param finale   Position la position finale
     * @return boolean true, si le déplacement a été effectué avec succès, false sinon
     */
    public boolean deplace(Position initiale, Position finale) {
        Piece a = echiquier[EchecsUtil.indiceLigne(initiale)][EchecsUtil.indiceColonne(initiale)];
        Piece b = echiquier[EchecsUtil.indiceLigne(finale)][EchecsUtil.indiceColonne(finale)];
        if (a.equals(b)) {
            return false;
        }
        if (a.equals(null)) {
            return false;
        }
        if (EchecsUtil.indiceLigne(initiale) > 8 || EchecsUtil.indiceLigne(initiale) < 0 || EchecsUtil.indiceLigne(finale) > 8 || EchecsUtil.indiceLigne(finale) < 0) {
            return false;
        }
        if (tour != (a.getCouleur())) {
            return false;
        }
        if(b!=null){
            if (b.getCouleur() == 'b' || b.getCouleur() == 'n') {
                if (b.getCouleur() == a.getCouleur()) {
                    return false;
                }
            }
        }
        if(!a.peutSeDeplacer(initiale,finale,echiquier)){
            return false;
        }
        if (deplacerTempVerifEchec(initiale, finale)){
            return false;
        }
        return true;
    }

    /**
     * Vérifie si un roi est en échec et, si oui, retourne sa couleur sous forme
     * d'un caractère n ou b.
     * Si la couleur du roi en échec est la même que celle de la dernière pièce
     * déplacée, le dernier déplacement doit être annulé.
     * Les 2 rois peuvent être en échec en même temps. Dans ce cas, la méthode doit
     * retourner la couleur de la pièce qui a été déplacée en dernier car ce
     * déplacement doit être annulé.
     *
     * @return char Le caractère n, si le roi noir est en échec, le caractère b,
     * si le roi blanc est en échec, tout autre caractère, sinon.
     */
    public char estEnEchec() {
        // creer une liste chainee avec toutes les pieces; looper pour verifier si on peut appeler
        //    deplacer(position de la piece iterer, position du roi opposee) si on peut, return echec
        Position roiPosb = null;
        Position roiPosn = null;
        Position piecePos;
        char roicouleur;
        char result = 'x';

        boolean b = false;
        boolean n = false;
        Piece p;
        positions.clear();
        for (int i = 0; i < echiquier.length; i++) {
            for (int j = 0; j < echiquier[i].length; j++) {
                if (echiquier[i][j] != null) {
                    if(echiquier[i][j] instanceof Pawn && (i == 0 || i == 7)){
                        echiquier[i][j] = new Queen(echiquier[i][j].getCouleur());
                    }
                    if (echiquier[i][j] instanceof King && echiquier[i][j].getCouleur() == 'b') {
                        roiPosb = new Position(EchecsUtil.getColonne((byte) j), EchecsUtil.getLigne((byte) i));
                    } else if (echiquier[i][j] instanceof King && echiquier[i][j].getCouleur() == 'n') {
                        roiPosn = new Position(EchecsUtil.getColonne((byte) j), EchecsUtil.getLigne((byte) i));
                    } else {
                        positions.add(new Position(EchecsUtil.getColonne((byte) j), EchecsUtil.getLigne((byte) i)));
                    }

                }
            }
        }


        for (int m = 0; m<(positions.size()); m++) {
            Position temp = positions.get(m);
            p = echiquier[EchecsUtil.indiceLigne(temp)][EchecsUtil.indiceColonne(temp)];
            if (p.peutSeDeplacer(temp, roiPosn, echiquier) && p.getCouleur()!='n') {
                n = true;

            } else if (p.peutSeDeplacer(temp, roiPosb, echiquier) && p.getCouleur()!='b') {
                System.out.println(p.getClass());
                b = true;
            }
        }

        if (b && n) {
            result = tour;
        } else if (b) {
            result = 'b';
        } else if (n) {
            result = 'n';
        }

        return result;
    }
    public boolean deplacerTempVerifEchec(Position pos1, Position pos2){
        Piece b=echiquier[EchecsUtil.indiceLigne(pos2)][EchecsUtil.indiceColonne(pos2)];
        echiquier[EchecsUtil.indiceLigne(pos2)][EchecsUtil.indiceColonne(pos2)]=echiquier[EchecsUtil.indiceLigne(pos1)][EchecsUtil.indiceColonne(pos1)];
        echiquier[EchecsUtil.indiceLigne(pos1)][EchecsUtil.indiceColonne(pos1)] =null;
        if(estEnEchec()==tour){
            echiquier[EchecsUtil.indiceLigne(pos1)][EchecsUtil.indiceColonne(pos1)]=echiquier[EchecsUtil.indiceLigne(pos2)][EchecsUtil.indiceColonne(pos2)];
            echiquier[EchecsUtil.indiceLigne(pos2)][EchecsUtil.indiceColonne(pos2)] =b;
            return true;
        }
        echiquier[EchecsUtil.indiceLigne(pos1)][EchecsUtil.indiceColonne(pos1)]=echiquier[EchecsUtil.indiceLigne(pos2)][EchecsUtil.indiceColonne(pos2)];
        echiquier[EchecsUtil.indiceLigne(pos2)][EchecsUtil.indiceColonne(pos2)] =b;
        return false;
    }
    public boolean deplacer(Position pos1, Position pos2){
        if(deplace(pos1,pos2)){
            echiquier[(int)EchecsUtil.indiceLigne(pos2)][(int)EchecsUtil.indiceColonne(pos2)]=echiquier[EchecsUtil.indiceLigne(pos1)][EchecsUtil.indiceColonne(pos1)];
            echiquier[EchecsUtil.indiceLigne(pos1)][EchecsUtil.indiceColonne(pos1)] =null;
            char echec = estEnEchec();
            if(echec!='x'){
                checkmate(echec);
            }
            changerTour();
            etat.setEtatEchiquier(updateBoard());
            return true;
        }
        return false;
    }
    public boolean checkmate(char echec){
        char res=echec;
        Position roiEchec;
        iterateur = positions.iterator();
        boolean there = true;
        if(res!='x'){
            for (int m = 0; m<positions.size(); m++){
                Position temp=positions.get(m);
                if (echiquier[EchecsUtil.indiceLigne(temp)][EchecsUtil.indiceColonne(temp)].getCouleur()==res){
                    for(int i=0;i<8;i++){
                        for(int j=0;j<8;j++){
                            Piece b=echiquier[i][j];
                            Position ite = new Position((char)('a'+j), (byte)(8-i));
                            if(deplace(temp,ite)) {
                                echiquier[i][j] = echiquier[EchecsUtil.indiceLigne(temp)][EchecsUtil.indiceColonne(temp)];
                                echiquier[EchecsUtil.indiceLigne(temp)][EchecsUtil.indiceColonne(temp)] = null;
                                if (estEnEchec() != res) {
                                    echiquier[EchecsUtil.indiceLigne(temp)][EchecsUtil.indiceColonne(temp)] = echiquier[i][j];
                                    echiquier[i][j] = b;
                                    positions.clear();
                                    return false;
                                }else {
                                    echiquier[EchecsUtil.indiceLigne(temp)][EchecsUtil.indiceColonne(temp)] = echiquier[i][j];
                                    echiquier[i][j] = b;
                                }
                            }
                        }
                    }
                }
            }
        }


        positions.clear();
        return true;
    }

    private char[][] updateBoard(){
        char[][] temp =new char[8][8];
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++) {
                if (echiquier[i][j] instanceof King && echiquier[i][j].getCouleur() == 'n') {
                    temp[i][j] = 'r';

                }
                if (echiquier[i][j] instanceof King && echiquier[i][j].getCouleur() == 'b') {
                    temp[i][j] = 'R';

                }

                if (echiquier[i][j] instanceof Queen && echiquier[i][j].getCouleur() == 'n') {
                    temp[i][j] = 'd';

                }
                if (echiquier[i][j] instanceof Queen && echiquier[i][j].getCouleur() == 'b') {
                    temp[i][j] = 'D';

                }
                if (echiquier[i][j] instanceof Rook && echiquier[i][j].getCouleur() == 'n') {
                    temp[i][j] = 't';

                }
                if (echiquier[i][j] instanceof Rook && echiquier[i][j].getCouleur() == 'b') {
                    temp[i][j] = 'T';

                }
                if (echiquier[i][j] instanceof Horse && echiquier[i][j].getCouleur() == 'n') {
                    temp[i][j] = 'c';

                }
                if (echiquier[i][j] instanceof Horse && echiquier[i][j].getCouleur() == 'b') {
                    temp[i][j] = 'C';
                }
                if (echiquier[i][j] instanceof Bishop && echiquier[i][j].getCouleur() == 'n') {
                    temp[i][j] = 'f';
                }
                if (echiquier[i][j] instanceof Bishop && echiquier[i][j].getCouleur() == 'b') {
                    temp[i][j]='F';
                }
                if (echiquier[i][j] instanceof Pawn && echiquier[i][j].getCouleur() == 'n') {
                    temp[i][j]='p';
                }
                if (echiquier[i][j] instanceof Pawn && echiquier[i][j].getCouleur() == 'b') {
                    temp[i][j]='P';
                }
                if (echiquier[i][j]==null) {
                    temp[i][j]=' ';
                }
            }
        }
        return temp;
    }
    /**
     * Retourne la couleur n ou b du joueur qui a la main.
     *
     * @return char la couleur du joueur à qui c'est le tour de jouer.
     */
    public char getTour () {
        return tour;
    }
    /**
     * Retourne l'alias du premier joueur.
     * @return String alias du premier joueur.
     */
    public String getAliasJoueur1 () {
        return aliasJoueur1;
    }
    /**
     * Modifie l'alias du premier joueur.
     * @param aliasJoueur1 String nouvel alias du premier joueur.
     */
    public void setAliasJoueur1 (String aliasJoueur1){
        this.aliasJoueur1 = aliasJoueur1;
    }
    /**
     * Retourne l'alias du deuxième joueur.
     * @return String alias du deuxième joueur.
     */
    public String getAliasJoueur2 () {
        return aliasJoueur2;
    }
    /**
     * Modifie l'alias du deuxième joueur.
     * @param aliasJoueur2 String nouvel alias du deuxième joueur.
     */
    public void setAliasJoueur2 (String aliasJoueur2){
        this.aliasJoueur2 = aliasJoueur2;
    }
    /**
     * Retourne la couleur n ou b du premier joueur.
     * @return char couleur du premier joueur.
     */
    public char getCouleurJoueur1 () {
        return couleurJoueur1;
    }
    /**
     * Retourne la couleur n ou b du deuxième joueur.
     * @return char couleur du deuxième joueur.
     */
    public void setCouleurJoueur1 (char couleurJoueur1){
        this.couleurJoueur1 = couleurJoueur1;
    }


    public char getCouleurJoueur2 () {
        return couleurJoueur2;
    }

    public void setCouleurJoueur2 (char couleurJoueur2){
        this.couleurJoueur2 = couleurJoueur2;
    }
    public EtatPartieEchecs getEtat() {
        return etat;
    }



}

