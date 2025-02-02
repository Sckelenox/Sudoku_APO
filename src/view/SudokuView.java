package src.view;

import src.Sudoku;

import javax.swing.*;
import java.awt.*;

public class SudokuView extends JFrame {
    private JTextField[][] cellules;  // pour représenter chaque case de la grille
    private JButton btnResoudre;
    private JButton btnEffacer;
    private JPanel grillePanel;

    public SudokuView(int taille) {
        super("Sudoku MVC");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLayout(new BorderLayout());

        // Créer la barre de menu
        JMenuBar menuBar = new JMenuBar();

        // Créer le menu "Options"
        JMenu menu = new JMenu("Options");

        JMenuItem entrerGrilleMenuItem = new JMenuItem("Entrer une grille");
        JMenuItem afficherGrilleMenuItem = new JMenuItem("Afficher la grille");
        JMenuItem resoudreSudokuMenuItem = new JMenuItem("Résoudre le Sudoku");
        JMenuItem genererGrilleMenuItem = new JMenuItem("Générer une grille");
        JMenuItem quitterMenuItem = new JMenuItem("Quitter");

        // Ajouter les options au menu
        menu.add(entrerGrilleMenuItem);
        menu.add(afficherGrilleMenuItem);
        menu.add(resoudreSudokuMenuItem);
        menu.add(genererGrilleMenuItem);
        menu.addSeparator();  // Séparateur pour une meilleure organisation
        menu.add(quitterMenuItem);

        menuBar.add(menu);
        setJMenuBar(menuBar);

        // Créer le panneau de la grille
        grillePanel = new JPanel(new GridLayout(taille, taille));
        cellules = new JTextField[taille][taille];
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                cellules[i][j] = new JTextField();
                cellules[i][j].setHorizontalAlignment(JTextField.CENTER);
                grillePanel.add(cellules[i][j]);
            }
        }
        add(grillePanel, BorderLayout.CENTER);

        // Créer le panneau des boutons
        JPanel boutonPanel = new JPanel();
        btnResoudre = new JButton("Résoudre");
        btnEffacer = new JButton("Effacer");
        boutonPanel.add(btnResoudre);
        boutonPanel.add(btnEffacer);
        add(boutonPanel, BorderLayout.SOUTH);

        setVisible(true);

        // Action des éléments du menu
        entrerGrilleMenuItem.addActionListener(e -> entrerGrille());
        afficherGrilleMenuItem.addActionListener(e -> afficherGrille());
        resoudreSudokuMenuItem.addActionListener(e -> resoudreSudoku());
        genererGrilleMenuItem.addActionListener(e -> genererGrille());
        quitterMenuItem.addActionListener(e -> System.exit(0));
    }

    // Méthode pour afficher un dialogue d'entrée de grille
    private void entrerGrille() {
        JTextField grilleInput = new JTextField(10);
        int option = JOptionPane.showConfirmDialog(this, grilleInput, "Entrez la grille sous forme de chaîne", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            // Traitement de la grille saisie (on peut la convertir en tableau)
            String grille = grilleInput.getText();
            System.out.println("Grille entrée: " + grille);
            // Mettre à jour la vue avec la grille saisie par l'utilisateur
        }
    }

    // Méthode pour afficher la grille (actuellement dans la vue)
    private void afficherGrille() {
        // Afficher la grille actuelle
        JOptionPane.showMessageDialog(this, "Affichage de la grille actuelle.");
    }

    // Méthode pour résoudre le Sudoku
    private void resoudreSudoku() {
        // Résoudre le sudoku ici
        JOptionPane.showMessageDialog(this, "Résolution en cours...");
    }

    // Méthode pour générer une nouvelle grille
    private void genererGrille() {
        // Générer une grille aléatoire ici
        JOptionPane.showMessageDialog(this, "Génération d'une nouvelle grille.");
    }

    // Méthode pour récupérer le contenu de la grille dans une structure adaptée
    public char[][] getGrilleSaisie() {
        int taille = cellules.length;
        char[][] grille = new char[taille][taille];
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                String texte = cellules[i][j].getText();
                grille[i][j] = (texte.isEmpty()) ? Sudoku.VIDE : texte.charAt(0);
            }
        }
        return grille;
    }

    // Méthode pour mettre à jour l'affichage de la grille à partir du modèle
    public void afficherGrille(char[][] grille) {
        int taille = cellules.length;
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                cellules[i][j].setText(String.valueOf(grille[i][j]));
            }
        }
    }

    public JButton getBtnResoudre() {
        return btnResoudre;
    }

    public JButton getBtnEffacer() {
        return btnEffacer;
    }
}
