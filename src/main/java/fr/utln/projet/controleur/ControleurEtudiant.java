package fr.utln.projet.controleur;

import fr.utln.projet.modele.ModeleEtudiant;
import fr.utln.projet.utilisateur.Etudiant;
import fr.utln.projet.vue.EtudiantVue;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.sql.SQLException;

/*
 * Nom de classe : ControleurEtudiant
 *
 * Description   : Controlleur Etudiant (MVC) previens le modele si il y a une action sur la vue
 *
 * Version       : 1.1
 *
 * Date          : 12/11/2018
 *
 * Copyright     : CLAIN Cyril
 */

public class ControleurEtudiant {
    private EtudiantVue etudiantVue;
    private ModeleEtudiant modeleEtudiant;

    private Document numNouvelEtudiantModel = new PlainDocument();
    private Document nomNouvelEtudiantModel = new PlainDocument();
    private Document prenomNouvelEtudiantModel = new PlainDocument();
    private Document groupeNouvelEtudiantModel = new PlainDocument();
    private Document mdpNouvelEtudiantModel = new PlainDocument();

    private Document numEtudiantModel = new PlainDocument();
    private Document nomEtudiantModel = new PlainDocument();
    private Document prenomEtudiantModel = new PlainDocument();
    private Document groupeEtudiantModel = new PlainDocument();
    private Document mdpEtudiantModel = new PlainDocument();

    public Document getMdpNouvelEtudiantModel() {
        return mdpNouvelEtudiantModel;
    }


    public ControleurEtudiant(final EtudiantVue ajoutetudiantVue, ModeleEtudiant modeleEtudiant) {
        this.etudiantVue = ajoutetudiantVue;
        this.modeleEtudiant = modeleEtudiant;

        DocumentListener ecouteurChangementTexte = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            // test si les champs sont vide ou non pour rendre le bouton clickable
            @Override
            public void changedUpdate(DocumentEvent e) {
                if ((nomNouvelEtudiantModel.getLength() == 0)|| (prenomNouvelEtudiantModel.getLength() == 0) || (groupeNouvelEtudiantModel.getLength() == 0) || (numNouvelEtudiantModel.getLength() == 0) || (mdpNouvelEtudiantModel.getLength() == 0))
                    etudiantVue.setCreationEtudiant(false);
                else
                    etudiantVue.setCreationEtudiant(true);

                if ((nomEtudiantModel.getLength() == 0)|| (prenomEtudiantModel.getLength() == 0) || (groupeEtudiantModel.getLength() == 0) || (numEtudiantModel.getLength() == 0) || (mdpEtudiantModel.getLength() == 0)){
                    etudiantVue.setModificationEtudiant(false);                }
                else
                    etudiantVue.setModificationEtudiant(true);


            }
        };
        numNouvelEtudiantModel.addDocumentListener(ecouteurChangementTexte);
        nomNouvelEtudiantModel.addDocumentListener(ecouteurChangementTexte);
        prenomNouvelEtudiantModel.addDocumentListener(ecouteurChangementTexte);
        groupeNouvelEtudiantModel.addDocumentListener(ecouteurChangementTexte);
        mdpNouvelEtudiantModel.addDocumentListener(ecouteurChangementTexte);

        numEtudiantModel.addDocumentListener(ecouteurChangementTexte);
        nomEtudiantModel.addDocumentListener(ecouteurChangementTexte);
        prenomEtudiantModel.addDocumentListener(ecouteurChangementTexte);
        groupeEtudiantModel.addDocumentListener(ecouteurChangementTexte);
        mdpEtudiantModel.addDocumentListener(ecouteurChangementTexte);



    }

    /**
     * Methode d'appel d'ajout etudiant du modele
     *
     * @author      CLAIN Cyril
     */

    public void ajouterEtudiant() {
        try {
            System.out.println(numNouvelEtudiantModel.getText(0, numNouvelEtudiantModel.getLength()));

            modeleEtudiant.ajouterEtudiant(
                    numNouvelEtudiantModel.getText(0, numNouvelEtudiantModel.getLength()),
                    nomNouvelEtudiantModel.getText(0, nomNouvelEtudiantModel.getLength()),
                    prenomNouvelEtudiantModel.getText(0, prenomNouvelEtudiantModel.getLength()),
                    mdpNouvelEtudiantModel.getText(0, mdpNouvelEtudiantModel.getLength()),
                    groupeNouvelEtudiantModel.getText(0, groupeNouvelEtudiantModel.getLength())

            );
            numNouvelEtudiantModel.remove(0, numNouvelEtudiantModel.getLength());
            nomNouvelEtudiantModel.remove(0, nomNouvelEtudiantModel.getLength());
            prenomNouvelEtudiantModel.remove(0, prenomNouvelEtudiantModel.getLength());
            mdpNouvelEtudiantModel.remove(0, mdpNouvelEtudiantModel.getLength());
            groupeNouvelEtudiantModel.remove(0, groupeNouvelEtudiantModel.getLength());
        }
        catch (BadLocationException e){
            System.out.println("erreur dans controleur");
            e.printStackTrace();
        }
    }


    /**
     * Methode d'appel de suppression etudiant du modele
     *
     * @param etudiant
     *
     * @author      CLAIN Cyril
     */

    public void deleteEtudiant(Etudiant etudiant){
        try{
            modeleEtudiant.deleteEtudiant(etudiant);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode qui annule la saisie de text
     *
     * @author      CLAIN Cyril
     */

    public void cancelEtudiant() {
        try {
            numNouvelEtudiantModel.remove(0, numNouvelEtudiantModel.getLength());
            nomNouvelEtudiantModel.remove(0, nomNouvelEtudiantModel.getLength());
            prenomNouvelEtudiantModel.remove(0, prenomNouvelEtudiantModel.getLength());
            mdpNouvelEtudiantModel.remove(0, mdpNouvelEtudiantModel.getLength());
            groupeNouvelEtudiantModel.remove(0, groupeNouvelEtudiantModel.getLength());

        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void modifierEtudiant() {
        try {
            System.out.println(numEtudiantModel.getLength());
            System.out.println(nomEtudiantModel.getLength());
            System.out.println(prenomEtudiantModel.getLength());
            System.out.println(groupeEtudiantModel.getLength());

            modeleEtudiant.modifiereEtudiant(
                    numEtudiantModel.getText(0, numEtudiantModel.getLength()),
                    nomEtudiantModel.getText(0, nomEtudiantModel.getLength()),
                    prenomEtudiantModel.getText(0, prenomEtudiantModel.getLength()),
                    groupeEtudiantModel.getText(0, groupeEtudiantModel.getLength())

                    );
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

    }

    public Document getNumNouvelEtudiantModel() {
        return numNouvelEtudiantModel;
    }

    public Document getNomNouvelEtudiantModel() {
        return nomNouvelEtudiantModel;
    }

    public Document getPrenomNouvelEtudiantModel() {
        return prenomNouvelEtudiantModel;
    }

    public Document getGroupeNouvelEtudiantModel() {
        return groupeNouvelEtudiantModel;
    }

    public Document getNumEtudiantModel() {
        return numEtudiantModel;
    }

    public Document getNomEtudiantModel() {
        return nomEtudiantModel;
    }

    public Document getPrenomEtudiantModel() {
        return prenomEtudiantModel;
    }

    public Document getGroupeEtudiantModel() {
        return groupeEtudiantModel;
    }

    public Document getMdpEtudiantModel() { return mdpEtudiantModel;
    }
}