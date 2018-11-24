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
import java.util.List;

/*
 * Nom de classe : ControleurEtudiant
 *
 * Description   : Controlleur Etudiant (MVC) previens le modele si il y a une action sur la vue
 *
 * Version       : 1.2
 *
 * Date          : 16/11/2018
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




    public ControleurEtudiant(final EtudiantVue ajoutetudiantVue, ModeleEtudiant modeleEtudiant) {
        this.etudiantVue = ajoutetudiantVue;
        this.modeleEtudiant = modeleEtudiant;

        DocumentListener ecouteurChangementTexteEtudiant = new DocumentListener() {
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

                boolean conditionDAjout;
                boolean conditiondeModification;
                conditionDAjout = ((nomNouvelEtudiantModel.getLength() == 0)|| (prenomNouvelEtudiantModel.getLength() == 0) || (groupeNouvelEtudiantModel.getLength() == 0) ||(numNouvelEtudiantModel.getLength() == 0) || (mdpNouvelEtudiantModel.getLength() == 0));
                conditiondeModification = ((prenomEtudiantModel.getLength() == 0)|| (prenomEtudiantModel.getLength() == 0));

                if (conditionDAjout)
                    etudiantVue.setCreationEtudiant(false);
                else if (!conditionDAjout)
                    etudiantVue.setCreationEtudiant(true);

                if (conditiondeModification)
                 {
                    etudiantVue.setModificationEtudiant(false);
                 }
                else if (!conditiondeModification){
                    etudiantVue.setModificationEtudiant(true);

                }
            }

        };
        numNouvelEtudiantModel.addDocumentListener(ecouteurChangementTexteEtudiant);
        nomNouvelEtudiantModel.addDocumentListener(ecouteurChangementTexteEtudiant);
        prenomNouvelEtudiantModel.addDocumentListener(ecouteurChangementTexteEtudiant);
        groupeNouvelEtudiantModel.addDocumentListener(ecouteurChangementTexteEtudiant);
        mdpNouvelEtudiantModel.addDocumentListener(ecouteurChangementTexteEtudiant);

        numEtudiantModel.addDocumentListener(ecouteurChangementTexteEtudiant);
        nomEtudiantModel.addDocumentListener(ecouteurChangementTexteEtudiant);
        prenomEtudiantModel.addDocumentListener(ecouteurChangementTexteEtudiant);
        groupeEtudiantModel.addDocumentListener(ecouteurChangementTexteEtudiant);
        mdpEtudiantModel.addDocumentListener(ecouteurChangementTexteEtudiant);



    }

    /**
     * Methode d'appel d'ajout etudiant du modele
     *
     * @author      CLAIN Cyril
     */

    public Boolean persisteEtudiant() {
        Boolean bool = false;
        try {
            bool =  modeleEtudiant.persisteEtudiant(
                        numNouvelEtudiantModel.getText(0, numNouvelEtudiantModel.getLength()),
                        nomNouvelEtudiantModel.getText(0, nomNouvelEtudiantModel.getLength()),
                        prenomNouvelEtudiantModel.getText(0, prenomNouvelEtudiantModel.getLength()),
                        mdpNouvelEtudiantModel.getText(0, mdpNouvelEtudiantModel.getLength()),
                        groupeNouvelEtudiantModel.getText(0, groupeNouvelEtudiantModel.getLength())

            );
            if (bool) cancelEtudiant();
        }
        catch (BadLocationException e){
            System.out.println("erreur dans controleur");
            e.printStackTrace();
        }
        return bool;
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

    /**
     *
     * Methode qui modifie un etudiant dans la list<Etudiant>
     *
     * @Aauthor CLAIN Cyril
     */
    public void modifierEtudiant() {
        try {

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

    public Document getMdpNouvelEtudiantModel() {
        return mdpNouvelEtudiantModel;
    }

    public List<String> getListGroupe() {
        return(modeleEtudiant.getListGroupe());
    }
    public List<Etudiant> getListEtudiant() {return(modeleEtudiant.getEtudiant());}

    public boolean numetudDansBase() {
        try {
            return(modeleEtudiant.numetudDansBase(numNouvelEtudiantModel.getText(0,numNouvelEtudiantModel.getLength())));
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        return false;
    }
}