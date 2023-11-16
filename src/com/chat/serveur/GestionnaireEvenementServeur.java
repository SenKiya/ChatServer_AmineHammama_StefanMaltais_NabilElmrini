package com.chat.serveur;

import com.chat.commun.evenement.Evenement;
import com.chat.commun.evenement.GestionnaireEvenement;
import com.chat.commun.net.Connexion;
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
        String msg, typeEvenement, aliasExpediteur, alias1, alias2;
        ServeurChat serveur = (ServeurChat) this.serveur;
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
                    int i;
                    alias1 = cnx.getAlias();
                    alias2 = evenement.getArgument();
                    boolean existe = false;
                    Invitation invitationExist = new Invitation(alias2, alias1);
                    Invitation invitationNonExist = new Invitation(alias1, alias2);
                    SalonPrive salonPrivePossible = new SalonPrive(alias2, alias1);
                    String inviteExiste1 = "vient d'accepter votre invitation à chatter en privé.\nVous pouvez maintenant communiquer entre vous deux en utilisant la commande : PRV "+alias1+  " message";
                    String inviteExiste2 = "Vous avez accepté une invitation à chatter en privé. \nVous pouvez maintenant communiquer entre vous deux en utilisant la commande : PRV "+alias2+  " message";
                    String inviteNonExistant = "vient de vous envoyer une invitation. \nPour accepter, veuillez écrire la commande suivante : JOIN "+alias1+" \nPour refuser, veuillez écrire la commande suivante : DECLINE "+alias1;
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
                        serveur.envoyerExpediteurAReceveur(inviteNonExistant, alias2, alias1);
                        invitations.add(invitationNonExist);
                    }

                    break;

                case "DECLINE":


                    break;
                case "HIST":
                    cnx.envoyer(serveur.historique());
                    break;

                default: //Renvoyer le texte recu convertit en majuscules :
                    msg = (evenement.getType() + " " + evenement.getArgument()).toUpperCase();
                    cnx.envoyer(msg);
            }
        }
    }

}