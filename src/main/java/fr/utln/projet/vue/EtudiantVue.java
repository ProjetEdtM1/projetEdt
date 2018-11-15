package fr.utln.projet.vue;
import fr.utln.projet.controleur.ControleurEtudiant;
import fr.utln.projet.modele.EtudiantListModel;
import fr.utln.projet.modele.ModeleEtudiant;
import fr.utln.projet.utilisateur.Etudiant;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Nom de classe : EtudiantVue
 *
 * Description   : Partie visible de mon interface permet de voir le formulaire d'ajout d'étudiant
 *
 * Version       : 2.1
 *
 * Date          : 14/11/2018
 *
 * Copyright     : CLAIN Cyril
 */

public class EtudiantVue extends JFrame  {


    private final ModeleEtudiant modeleEtudiant;
    private final ControleurEtudiant controleurEtudiant;

    private static EtudiantListModel etudiantListModel;

    private final JPanel etudiantSuppressionPanel = new JPanel(new GridBagLayout());
    private final JPanel etudiantAjoutPanel = new JPanel(new GridBagLayout());

    private final JList<Etudiant> etudiantSuppressionJList;
    private final JButton supprimerEtudiantJButton = new JButton("Supprimer Etudiant");

    private final JButton ajoutOkEtudiantJButton = new JButton(" ok");
    private final JButton ajoutcancelEtudiantJButton = new JButton("cancel Etudiant");

    private final JLabel groupetudlabel = new JLabel(" Groupe :");;
    private final JLabel numetudlabel = new JLabel("Num etud :");
    private final JLabel nometudlabel = new JLabel("Nom :");
    private final JLabel prenometudlabel = new JLabel(" Prénom :");
    private final JLabel mdpEtudiantlabel = new JLabel("mdp");

    private final JTextField numetud;
    private final JTextField nometud;
    private final JTextField prenometud;
    private final JTextField groupetud;
    private final JPasswordField mdpEtudiant;

    private static JPanel etudiantDetailPanel = new JPanel(new GridBagLayout());
    private final JButton modifierEtudiantJBouton = new JButton("valider");
    private final JButton cancelModifierEtudiantJButton = new JButton("annuler ");

    private final JComboBox<Etudiant> etudiantDetailJComboBox;

    private static JTextField numEtudDetailTextField;// = new JTextField();
    private static JTextField nomEtudDetailTextField;// = new JTextField();
    private static JTextField prenomEtudDetailTextField;// = new JTextField();
    private static JTextField groupeEtudDetailTextField;// = new JTextField();

    private final JLabel numEtudDetaillabel = new JLabel(" num etud :");;
    private final JLabel nomEtudDetaillabel = new JLabel("Nom :");
    private final JLabel prenomEtudDetaillabel = new JLabel("prenom :");
    private final JLabel groupeEtudDetaillabel = new JLabel(" groupe :");


    public EtudiantVue(ModeleEtudiant modeleEtudiant) throws HeadlessException {

        super("supression etudiants");

        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) dimension.getHeight();
        int width = (int) dimension.getWidth();
        setSize(width / 2, height / 2);

        this.modeleEtudiant = modeleEtudiant;
        this.controleurEtudiant = new ControleurEtudiant(this,modeleEtudiant);
        this.etudiantListModel = new EtudiantListModel(modeleEtudiant.getEtudiant());


        modeleEtudiant.addObserver(etudiantListModel);

        etudiantSuppressionJList = new JList<>(etudiantListModel);
        etudiantSuppressionJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                setSuppressionEtudiant((etudiantSuppressionJList.getSelectedValue() != null));

            }
        });

        supprimerEtudiantJButton.setEnabled(false);
        supprimerEtudiantJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controleurEtudiant.deleteEtudiant(etudiantSuppressionJList.getSelectedValue());
                setSuppressionEtudiant((etudiantSuppressionJList.getSelectedValue() == null));

            }
        });


        ajoutOkEtudiantJButton.setEnabled(false);
        ajoutOkEtudiantJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controleurEtudiant.ajouterEtudiant();
            }
        });

        ajoutcancelEtudiantJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controleurEtudiant.cancelEtudiant();
            }
        });

        ajoutcancelEtudiantJButton.setEnabled(false);

        // Champ d'ajout d'étudiant
        numetud = new JTextField(controleurEtudiant.getNumNouvelEtudiantModel(),"",10);
        nometud = new JTextField(controleurEtudiant.getNomNouvelEtudiantModel(),"",10);
        prenometud = new JTextField(controleurEtudiant.getPrenomNouvelEtudiantModel(),"",10);
        groupetud = new JTextField(controleurEtudiant.getGroupeNouvelEtudiantModel(),"",10);
        mdpEtudiant = new JPasswordField(controleurEtudiant.getMdpNouvelEtudiantModel(),"",10);


        // Champ de modification  d'étudiant
        numEtudDetailTextField = new JTextField(controleurEtudiant.getNumEtudiantModel(),"",10);
        nomEtudDetailTextField = new JTextField(controleurEtudiant.getNomEtudiantModel(),"",10);
        prenomEtudDetailTextField = new JTextField(controleurEtudiant.getPrenomEtudiantModel(),"",10);
        groupeEtudDetailTextField= new JTextField(controleurEtudiant.getGroupeEtudiantModel(),"",10);

        etudiantDetailJComboBox = new JComboBox<Etudiant>(etudiantListModel);

        etudiantDetailJComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                switch (e.getStateChange()) {
                    case ItemEvent.DESELECTED:
                        montrerDetail(null);
                        break;
                    case ItemEvent.SELECTED:
                        montrerDetail(etudiantDetailJComboBox.getItemAt(etudiantDetailJComboBox.getSelectedIndex()));
                        setModificationEtudiant(true);
                        break;
                }

            }
        });


        modifierEtudiantJBouton.setEnabled(false);
        modifierEtudiantJBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controleurEtudiant.modifierEtudiant();
            }
        });
        cancelModifierEtudiantJButton.setEnabled(false);


        /* bloque l'écriture dans les champs text me servira a afficher suelement */
        numEtudDetailTextField.setEnabled(false);
        nomEtudDetailTextField.setEnabled(false);
        prenomEtudDetailTextField.setEnabled(false);
        groupeEtudDetailTextField.setEnabled(false);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridBagConstraints c = new GridBagConstraints();

        //Le détail d'un Etudiant

        etudiantDetailPanel.setBorder(BorderFactory.createTitledBorder("Détails etudiant"));

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        etudiantDetailPanel.add(etudiantDetailJComboBox, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        etudiantDetailPanel.add(numEtudDetaillabel, c);

        c.gridx = 1;
        c.gridy = 1;
        etudiantDetailPanel.add(numEtudDetailTextField, c);

        c.gridx = 0;
        c.gridy = 2;
        etudiantDetailPanel.add(nomEtudDetaillabel, c);

        c.gridx = 1;
        c.gridy = 2;
        etudiantDetailPanel.add(nomEtudDetailTextField, c);

        c.gridx = 0;
        c.gridy = 3;
        etudiantDetailPanel.add(prenomEtudDetaillabel, c);

        c.gridx = 1;
        c.gridy = 3;
        etudiantDetailPanel.add(prenomEtudDetailTextField, c);

        c.gridx = 0;
        c.gridy = 4;
        etudiantDetailPanel.add(groupeEtudDetaillabel, c);

        c.gridx = 1;
        c.gridy = 4;
        etudiantDetailPanel.add(groupeEtudDetailTextField, c);

        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 1;
        etudiantDetailPanel.add(cancelModifierEtudiantJButton,c);

        c.gridy = 5;
        c.gridx = 0;
        etudiantDetailPanel.add(modifierEtudiantJBouton,c);


        // Ajout d'un etudiant
        etudiantAjoutPanel.setBorder(BorderFactory.createTitledBorder("Ajout"));

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        etudiantAjoutPanel.add(numetudlabel, c);

        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        etudiantAjoutPanel.add(numetud, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        etudiantAjoutPanel.add(nometudlabel, c);

        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        etudiantAjoutPanel.add(nometud, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        etudiantAjoutPanel.add(prenometudlabel, c);

        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        etudiantAjoutPanel.add(prenometud, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        etudiantAjoutPanel.add(groupetudlabel, c);

        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 1;
        etudiantAjoutPanel.add(groupetud, c);

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        etudiantAjoutPanel.add(mdpEtudiantlabel,c);

        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 1;
        etudiantAjoutPanel.add(mdpEtudiant,c);

        c.gridx = 0;
        c.gridy = 5;
        etudiantAjoutPanel.add(ajoutOkEtudiantJButton, c);

        c.gridx = 1;
        c.gridy = 5;
        etudiantAjoutPanel.add(ajoutcancelEtudiantJButton, c);

        // Suppression d'un etudiant
        etudiantSuppressionPanel.setBorder(BorderFactory.createTitledBorder("Suppression"));

        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.weighty = 0.8;
        c.gridx = 0;
        c.gridy = 2;
        etudiantSuppressionPanel.add(etudiantSuppressionJList, c);


        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridheight = 1;
        c.weighty = 0.2;
        c.gridx = 0;
        c.gridy = 1;
        etudiantSuppressionPanel.add(supprimerEtudiantJButton, c);

        //Ajout des panel de suppression et  d'ajout consultation
        getContentPane().setLayout(new GridBagLayout());


        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        getContentPane().add(etudiantSuppressionPanel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        getContentPane().add(etudiantAjoutPanel, c);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 2;
        c.gridy = 0;
        getContentPane().add(etudiantDetailPanel, c);



        setVisible(true);
    }

    public void setCreationEtudiant(boolean creationEtudiantok){
        ajoutOkEtudiantJButton.setEnabled(creationEtudiantok);
        ajoutcancelEtudiantJButton.setEnabled(creationEtudiantok);
    }

    public void setSuppressionEtudiant(boolean suppressionEtudiantok){
        supprimerEtudiantJButton.setEnabled(suppressionEtudiantok);

    }

    /**
     * methode qui permet la saisie de text dans le JTextfield pour modifier un étudiant
     *
     * @param modificationEtudiantok
     *
     * @autor CLAIN CYRIL
     */
    public void setModificationEtudiant(boolean modificationEtudiantok) {
        nomEtudDetailTextField.setEnabled(true);
        prenomEtudDetailTextField.setEnabled(true);
        groupeEtudDetailTextField.setEnabled(true);
        modifierEtudiantJBouton.setEnabled(true);
        cancelModifierEtudiantJButton.setEnabled(true);

    }

    public void montrerDetail(Etudiant etudiant){
        System.out.println("je rentre bien la ");
        if (etudiant == null){
            numEtudDetailTextField.setText("");
            nomEtudDetailTextField.setText("");
            prenomEtudDetailTextField.setText("");
            groupeEtudDetailTextField.setText("");
        }
        else{
            System.out.println("il suppose que etudiant != null ");
            System.out.println(etudiant.getGroupe());
            numEtudDetailTextField.setText(etudiant.getNumetud());
            numEtudDetailTextField= new JTextField(controleurEtudiant.getNumEtudiantModel(),etudiant.getNumetud(),10);

            nomEtudDetailTextField.setText(etudiant.getNom());
            nomEtudDetailTextField= new JTextField(controleurEtudiant.getNomEtudiantModel(),etudiant.getNom(),10);

            prenomEtudDetailTextField.setText(etudiant.getPrenom());
            prenomEtudDetailTextField= new JTextField(controleurEtudiant.getPrenomEtudiantModel(),etudiant.getPrenom(),10);

            groupeEtudDetailTextField.setText(etudiant.getGroupe());
            groupeEtudDetailTextField= new JTextField(controleurEtudiant.getGroupeEtudiantModel(),etudiant.getGroupe(),10);
        }
    }
}