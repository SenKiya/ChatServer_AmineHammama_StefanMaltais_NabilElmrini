package com.chat.serveur;

import com.chat.commun.evenement.Evenement;
import com.chat.commun.evenement.GestionnaireEvenement;
import com.chat.commun.net.Connexion;
import com.echecs.PartieEchecs;
import com.echecs.Position;

import java.util.ArrayList;

/**
 * Cette classe repr�sente un gestionnaire d'�v�nement d'un serveur. Lorsqu'un serveur re�oit un texte d'un client,
 * il cr�e un �v�nement � partir du texte re�u et alerte ce gestionnaire qui r�agit en g�rant l'�v�nement.
 *
 * @author Abdelmoum�ne Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class GestionnaireEvenementServeur implements GestionnaireEvenement {
    private Serveur serveur;
    ArrayList<Invitation> invitations = new ArrayList<>();
    ArrayList<SalonPrive> salonsPrives = new ArrayList<>();

    ArrayList<Invitation> invitationEchecs = new ArrayList<>();




    /**
     * Construit un gestionnaire d'�v�nements pour un serveur.
     *
     * @param serveur Serveur Le serveur pour lequel ce gestionnaire g�re des �v�nements
     */
    public GestionnaireEvenementServeur(Serveur serveur) {
        this.serveur = serveur;
    }

    /**
     * M�thode de gestion d'�v�nements. Cette m�thode contiendra le code qui g�re les r�ponses obtenues d'un client.
     *
     * @param evenement L'�v�nement � g�rer.
     */
    @Override
    public void traiter(Evenement evenement) {
        Object source = evenement.getSource();
        Connexion cnx;
        String msg, typeEvenement, aliasExpediteur, alias1, alias2, texte, msgImpo;
        ServeurChat serveur = (ServeurChat) this.serveur;
        int i;
        boolean existe = false;
        if (source instanceof Connexion) {
            cnx = (Connexion) source;
            System.out.println("SERVEUR-Recu : " + evenement.getType() + " " + evenement.getArgument());
            typeEvenement = evenement.getType();
            switch (typeEvenement) {
                case "EXIT": //Ferme la connexion avec le client qui a envoy� "EXIT":
                    cnx.envoyer("END");
                    serveur.enlever(cnx);
                    cnx.close();
                    break;
                case "LIST": //Envoie la liste des alias des personnes connect�es :
                    cnx.envoyer("LIST " + serveur.list());
                    break;

                //Ajoutez ici d�autres case pour g�rer d�autres commandes.
                case "MSG":
                    aliasExpediteur = cnx.getAlias();
                    msg = evenement.getArgument();
                    serveur.envoyerAtousSauf(msg, aliasExpediteur);
                    break;

                case "JOIN":

                    alias1 = cnx.getAlias();
                    alias2 = evenement.getArgument();
                    Invitation invitationExist = new Invitation(alias2, alias1);
                    Invitation invitationNonExist = new Invitation(alias1, alias2);
                    SalonPrive salonPrivePossible = new SalonPrive(alias2, alias1);
                    String inviteExiste1 = "vient d'accepter votre invitation à chatter en privé.\n\t\t\t Vous pouvez maintenant communiquer entre vous deux en utilisant la commande : PRV "+alias1+  " message";
                    String inviteExiste2 = "Vous avez accepté une invitation à chatter en privé. \n\t\t\t Vous pouvez maintenant communiquer entre vous deux en utilisant la commande : PRV "+alias2+  " message";
                    String inviteNonExistant1 = "vient de vous envoyer une invitation. \n\t\t\t Pour accepter, veuillez écrire la commande suivante : JOIN "+alias1+" \n\t\t\t Pour refuser, veuillez écrire la commande suivante : DECLINE "+alias1;
                    String inviteNonExistant2 = "Votre invitation à "+ alias2 + " a été envoyée";
                    if(!alias2.equals(alias1)){
                        for(i=0;i<invitations.size();i++) {
                            if(invitationExist.getAlias2().equals(invitations.get(i).getAlias2()) && invitationExist.getAlias1().equals(invitations.get(i).getAlias1())){
                                serveur.envoyerExpediteurAReceveur(inviteExiste1, alias2, alias1);
                                serveur.envoyerAUnePersonne(inviteExiste2, alias1);
                                salonsPrives.add(salonPrivePossible);
                                invitations.remove(i);
                                existe = true;
                            }

                        }
                        if(!existe) {
                            serveur.envoyerExpediteurAReceveur(inviteNonExistant1, alias2, alias1);
                            serveur.envoyerAUnePersonne(inviteNonExistant2, alias1);
                            invitations.add(invitationNonExist);
                        }
                    }

                    break;

                case "DECLINE":

                    alias1 = cnx.getAlias();
                    alias2 = evenement.getArgument();
                    Invitation invitationRefuse = new Invitation(alias2, alias1);
                    Invitation invitationAnnule = new Invitation(alias1, alias2);
                    String inviteRefuse1 = "Vous avez refusé l'invitation de "+alias2;
                    String inviteRefuse2 = " vient de refuser votre invitation";
                    String inviteAnnule1 = "Vous avez annulé l'invitation que vous avez envoyé à " + alias2;
                    String inviteAnnule2 = " vient d'annuler l'invitation";
                    String inviteInexistant = "Une invitation avec "+alias2+" n'existe pas";


                    for(i=0;i<invitations.size();i++) {
                        if(invitationRefuse.getAlias2().equals(invitations.get(i).getAlias2()) && invitationRefuse.getAlias1().equals(invitations.get(i).getAlias1())){
                            serveur.envoyerExpediteurAReceveur(inviteRefuse2, alias2, alias1);
                            serveur.envoyerAUnePersonne(inviteRefuse1, alias1);
                            invitations.remove(i);

                            existe = true;
                        } else if (invitationAnnule.getAlias2().equals(invitations.get(i).getAlias2()) && invitationAnnule.getAlias1().equals(invitations.get(i).getAlias1())){
                            serveur.envoyerExpediteurAReceveur(inviteAnnule2, alias2, alias1);
                            serveur.envoyerAUnePersonne(inviteAnnule1, alias1);
                            invitations.remove(i);
                            existe = true;
                        }

                    }
                    if(!existe) {
                        serveur.envoyerAUnePersonne(inviteInexistant, alias1);
                    }

                    break;

                case "INV":
                    alias1 = cnx.getAlias();

                    for(i=0;i<invitations.size();i++) {
                        if(alias1.equals(invitations.get(i).getAlias2()) ){

                            serveur.envoyerAUnePersonne(invitations.get(i).getAlias1()+"\n\t\t\t ", alias1);


                            existe = true;
                        } else if (alias1.equals(invitations.get(i).getAlias1())) {

                            serveur.envoyerAUnePersonne(invitations.get(i).getAlias2()+"\n\t\t\t ", alias1);
                            existe = true;
                        }

                    }

                    if(!existe) {
                        serveur.envoyerAUnePersonne("Vous n'avez aucune invitation en cours", alias1);
                    }
                    break;

                case "PRV":
                    alias1 = cnx.getAlias();
                    texte = evenement.getArgument();
                    String[] separe = texte.split(" ",2);
                    SalonPrive salonPrive1 = new SalonPrive(separe[0], alias1);
                    SalonPrive salonPrive2 = new SalonPrive(alias1, separe[0]);
                    msgImpo = "Le salon privé avec " +separe[0] + " n'existe pas";
                    for(i=0;i<salonsPrives.size();i++) {
                        if((salonPrive1.getAlias2().equals(salonsPrives.get(i).getAlias2()) && salonPrive1.getAlias1().equals(salonsPrives.get(i).getAlias1())) || (salonPrive2.getAlias2().equals(salonsPrives.get(i).getAlias2()) && salonPrive2.getAlias1().equals(salonsPrives.get(i).getAlias1()))){
                            serveur.envoyerExpediteurAReceveur(" : " + separe[1], separe[0], alias1);
                            serveur.envoyerAUnePersonne("Me : " + separe[1], alias1);

                            existe = true;
                        }

                    }

                    if(!existe) {
                        serveur.envoyerAUnePersonne(msgImpo, alias1);
                    }


                    break;

                case "CHESS":
                    alias1 = cnx.getAlias();
                    texte = evenement.getArgument();
                    String[] separermessage = texte.split(" ",2);
                    SalonPrive salonPriver1 = new SalonPrive(separermessage[0], alias1);
                    SalonPrive salonPriver2 = new SalonPrive(alias1, separermessage[0]);
                    int random = (int)Math.floor(Math.random() * (2 - 1 + 1) + 1);
                    boolean found = false;
                    boolean full = false;
                    for(i=0;i<salonsPrives.size();i++) {
                        if((salonsPrives.get(i).getAlias1().equals(alias1)||salonsPrives.get(i).getAlias2().equals(alias1)||salonsPrives.get(i).getAlias1().equals(separermessage[0])||salonsPrives.get(i).getAlias2().equals(separermessage[0])) && salonsPrives.get(i).getPartieEchecs()!=null){
                            full = true;
                        }
                    }

                    if(alias1!=separermessage[0] && !full){
                        for(i=0;i<salonsPrives.size();i++) {
                            if((salonPriver1.getAlias2().equals(salonsPrives.get(i).getAlias2()) && salonPriver1.getAlias1().equals(salonsPrives.get(i).getAlias1())) || (salonPriver2.getAlias2().equals(salonsPrives.get(i).getAlias2()) && salonPriver2.getAlias1().equals(salonsPrives.get(i).getAlias1()))){
                                for(int m = 0; m<invitationEchecs.size(); m++){

                                    if(invitationEchecs.get(m).getAlias1().equals(alias1)||invitationEchecs.get(m).getAlias1().equals(separermessage[0])&& invitationEchecs.get(m).getAlias2().equals(separermessage[0])||invitationEchecs.get(m).getAlias2().equals(alias1)){
                                        salonsPrives.get(i).setPartieEchecs(new PartieEchecs());
                                        salonsPrives.get(i).getPartieEchecs().setAliasJoueur1(alias1);
                                        salonsPrives.get(i).getPartieEchecs().setAliasJoueur2(separermessage[0]);
                                        if(random == 1) {
                                            serveur.envoyerAUnePersonne("CHESSOK b \n", alias1);
                                            serveur.envoyerAUnePersonne("CHESSOK n \n", separermessage[0]);
                                            salonsPrives.get(i).getPartieEchecs().setCouleurJoueur1('b');
                                            salonsPrives.get(i).getPartieEchecs().setCouleurJoueur2('n');
                                        } else if (random == 2) {
                                            serveur.envoyerAUnePersonne("CHESSOK n \n", alias1);
                                            serveur.envoyerAUnePersonne("CHESSOK b \n", separermessage[0]);
                                            salonsPrives.get(i).getPartieEchecs().setCouleurJoueur1('n');
                                            salonsPrives.get(i).getPartieEchecs().setCouleurJoueur2('b');
                                        }
                                        serveur.envoyerAUnePersonne(salonsPrives.get(i).getPartieEchecs().getEtat().toString(), salonsPrives.get(i).getAlias2());
                                        serveur.envoyerAUnePersonne(salonsPrives.get(i).getPartieEchecs().getEtat().toString(), salonsPrives.get(i).getAlias1());
                                        invitationEchecs.remove(m);
                                        found = true;
                                    }

                                }
                                if(!found){
                                    invitationEchecs.add(new Invitation(alias1, separermessage[0]));
                                }

                            }

                        }
                    }



                    break;

                case "ABANDON":
                    alias1 = cnx.getAlias();
                    for(int j = 0; j<salonsPrives.size(); j++) {
                        if (salonsPrives.get(j).getPartieEchecs() != null && (salonsPrives.get(j).getAlias1().equals(alias1) || salonsPrives.get(j).getAlias2().equals(alias1))) {
                            if (salonsPrives.get(j).getAlias1().equals(alias1)) {
                                serveur.envoyerAUnePersonne(salonsPrives.get(j).getAlias2() + " gagne!", salonsPrives.get(j).getAlias2());
                            } else {
                                serveur.envoyerAUnePersonne(salonsPrives.get(j).getAlias1() + " gagne!", salonsPrives.get(j).getAlias1());
                            }
                            salonsPrives.get(j).setPartieEchecs(null);
                        }

                    }


                    break;

                case "QUIT":

                    alias1 = cnx.getAlias();
                    alias2 = evenement.getArgument();
                    SalonPrive salonQuit1 = new SalonPrive(alias2, alias1);
                    SalonPrive salonQuit2 = new SalonPrive(alias1, alias2);
                    String msgQuit1 = " vient de quitter le salon privé";
                    String msgQuit2 = "Vous avez quitté le salon privé avec " + alias2;
                    msgImpo = "Le salon privé avec " +alias2 + " n'existe pas";
                    for(i=0;i<salonsPrives.size();i++) {
                        if((salonQuit1.getAlias2().equals(salonsPrives.get(i).getAlias2()) && salonQuit1.getAlias1().equals(salonsPrives.get(i).getAlias1())) || (salonQuit2.getAlias2().equals(salonsPrives.get(i).getAlias2()) && salonQuit2.getAlias1().equals(salonsPrives.get(i).getAlias1()))){
                            serveur.envoyerExpediteurAReceveur(msgQuit1, alias2, alias1);
                            serveur.envoyerAUnePersonne(msgQuit2, alias1);
                            salonsPrives.remove(i);

                            existe = true;
                        }

                    }
                    if(!existe) {
                        serveur.envoyerAUnePersonne(msgImpo, alias1);
                    }


                    break;




                case "HIST":
                    cnx.envoyer(serveur.historique());
                    break;

                case "MOVE":
                    alias1 = cnx.getAlias();
                    msg = evenement.getArgument();
                    char posI1 = '0', posF1 = '0';
                    byte posI2 = 0, posF2 = 0;


                    boolean possible;
                    if(msg.length()<5){
                        posI1 = msg.charAt(0);
                        posI2 = (byte) (Character.getNumericValue(msg.charAt(1)));
                        posF1 = msg.charAt(2);
                        posF2 = (byte) (Character.getNumericValue(msg.charAt(3)));
                        serveur.envoyerAUnePersonne(" "+posI1+posI2+posF1+posF2, alias1);


                        possible = true;

                    } else if(msg.length()<6 && (msg.substring(2,3).equals("-") || msg.substring(2,3).equals(" "))){

                        posI1 = msg.charAt(0);
                        posI2 = (byte) (Character.getNumericValue(msg.charAt(1)));
                        posF1 = msg.charAt(3);
                        posF2 = (byte) (Character.getNumericValue(msg.charAt(4)));
                        serveur.envoyerAUnePersonne(" "+posI1+posI2+posF1+posF2, alias1);

                        possible = true;

                    } else {

                        possible = false;

                    }

                    Position init = new Position(posI1, posI2);
                    Position finale = new Position(posF1, posF2);

                    for(i=0;i<salonsPrives.size();i++) {
                        if((alias1.equals(salonsPrives.get(i).getAlias2()) || (alias1.equals(salonsPrives.get(i).getAlias1())) && !salonsPrives.get(i).getPartieEchecs().equals(null))){
                            if (possible && salonsPrives.get(i).getPartieEchecs().deplacer(init, finale)){
                                serveur.envoyerAUnePersonne(msg, salonsPrives.get(i).getAlias2());
                                serveur.envoyerAUnePersonne(msg, salonsPrives.get(i).getAlias1());


                                serveur.envoyerAUnePersonne(salonsPrives.get(i).getPartieEchecs().getEtat().toString(), salonsPrives.get(i).getAlias2());
                                serveur.envoyerAUnePersonne(salonsPrives.get(i).getPartieEchecs().getEtat().toString(), salonsPrives.get(i).getAlias1());

                            } else {
                                serveur.envoyerAUnePersonne("INVALID", alias1);

                            }


                        }
                        if(salonsPrives.get(i).getPartieEchecs().estEnEchec() !='x' && !salonsPrives.get(i).getPartieEchecs().checkmate(salonsPrives.get(i).getPartieEchecs().estEnEchec())){

                            if(salonsPrives.get(i).getPartieEchecs().estEnEchec() == salonsPrives.get(i).getPartieEchecs().getCouleurJoueur1()) {
                                serveur.envoyerAUnePersonne("ECHEC " + salonsPrives.get(i).getPartieEchecs().getAliasJoueur1(), salonsPrives.get(i).getAlias2());
                                serveur.envoyerAUnePersonne("ECHEC " + salonsPrives.get(i).getPartieEchecs().getAliasJoueur1(), salonsPrives.get(i).getAlias1());
                            }
                            else if(salonsPrives.get(i).getPartieEchecs().estEnEchec() == salonsPrives.get(i).getPartieEchecs().getCouleurJoueur2()) {
                                serveur.envoyerAUnePersonne("ECHEC " + salonsPrives.get(i).getPartieEchecs().getAliasJoueur2(), salonsPrives.get(i).getAlias2());
                                serveur.envoyerAUnePersonne("ECHEC " + salonsPrives.get(i).getPartieEchecs().getAliasJoueur2(), salonsPrives.get(i).getAlias1());
                            }
                        }else if (salonsPrives.get(i).getPartieEchecs().estEnEchec() !='x' && salonsPrives.get(i).getPartieEchecs().checkmate(salonsPrives.get(i).getPartieEchecs().estEnEchec())) {

                            if(salonsPrives.get(i).getPartieEchecs().estEnEchec() == salonsPrives.get(i).getPartieEchecs().getCouleurJoueur1()) {
                                serveur.envoyerAUnePersonne("MAT " + salonsPrives.get(i).getPartieEchecs().getAliasJoueur2(), salonsPrives.get(i).getAlias2());
                                serveur.envoyerAUnePersonne("MAT " + salonsPrives.get(i).getPartieEchecs().getAliasJoueur2(), salonsPrives.get(i).getAlias1());
                                salonsPrives.get(i).setPartieEchecs(null);
                            }
                            else if(salonsPrives.get(i).getPartieEchecs().estEnEchec() == salonsPrives.get(i).getPartieEchecs().getCouleurJoueur2()) {
                                serveur.envoyerAUnePersonne("MAT " + salonsPrives.get(i).getPartieEchecs().getAliasJoueur1(), salonsPrives.get(i).getAlias2());
                                serveur.envoyerAUnePersonne("MAT " + salonsPrives.get(i).getPartieEchecs().getAliasJoueur1(), salonsPrives.get(i).getAlias1());
                                salonsPrives.get(i).setPartieEchecs(null);
                            }

                        }
                    }





                    break;



                default: //Renvoyer le texte recu convertit en majuscules :
                    msg = (evenement.getType() + " " + evenement.getArgument()).toUpperCase();
                    cnx.envoyer(msg);
            }
        }
    }

}