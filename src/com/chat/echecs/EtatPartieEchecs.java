package com.chat.echecs;

public class EtatPartieEchecs {
    public char[][] etatEchiquier;

    public EtatPartieEchecs() {
        etatEchiquier= new char[8][8];
        //Placement des pièces :
        etatEchiquier[0][0] = 't';
        etatEchiquier[0][1] = 'c';
        etatEchiquier[0][2] = 'f';
        etatEchiquier[0][3] = 'd';
        etatEchiquier[0][4] = 'r';
        etatEchiquier[0][5] = 'f';
        etatEchiquier[0][6] = 'c';
        etatEchiquier[0][7] = 't';

        for(int i=0; i<8;i++){
            etatEchiquier[1][i]='p';
        }

        for(int i=0; i<6;i++){
            for(int j = 0; j<8; j++){
                etatEchiquier[i][j]=' ';
            }

        }

        etatEchiquier[7][0] = 'T';
        etatEchiquier[7][1] = 'C';
        etatEchiquier[7][2] = 'F';
        etatEchiquier[7][3] = 'D';
        etatEchiquier[7][4] = 'R';
        etatEchiquier[7][5] = 'F';
        etatEchiquier[7][6] = 'C';
        etatEchiquier[7][7] = 'T';

        for(int i=0; i<8;i++){
            etatEchiquier[6][i]='P';
        }


    }


    public char[][] getEtatEchiquier() {
        return etatEchiquier;
    }

    public void setEtatEchiquier(char[][] etatEchiquier) {
        this.etatEchiquier = etatEchiquier;
    }

    @Override
    public String toString() {
        String result="";
        for(int i = 0; i<etatEchiquier.length; i++){
            result+=(i+1);
            for(int j = 0; j<etatEchiquier[i].length; j++){
                result+=" ";
                if(etatEchiquier[i][j]!=' '){
                    result+=etatEchiquier[i][j];
                }
                else{
                    result+=".";
                }
            }
            result+="\n";
        }
        result+="  a b c d e f g h";
        return result;
    }
}
