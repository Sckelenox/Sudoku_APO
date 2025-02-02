package src.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SudokuView extends JFrame {
    private final int taille;
    private JTextField[][] cellule;
    private JButton btnResoudre, btnEffacer, btnGenerer;
    private JComboBox<String> comboBoxDifficulte;
    private JComboBox<String> comboBoxMethodeResolution;

    public SudokuView(int taille) {
        this.taille = taille;
        this.cellule = new JTextField[taille][taille];
        setLayout(new BorderLayout());

        // Crée un panneau pour la grille de Sudoku
        JPanel panelGrille = new JPanel();
        panelGrille.setLayout(new GridLayout(taille, taille)); // Grid pour la grille de Sudoku
        panelGrille.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Bordure pour la grille

        // Création des cellules (JTextField pour chaque case)
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                cellule[i][j] = new JTextField(2);
                cellule[i][j].setHorizontalAlignment(JTextField.CENTER);
                cellule[i][j].setFont(new Font("Arial", Font.PLAIN, 20)); // Amélioration de la police
                cellule[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Bordure autour de chaque case
                panelGrille.add(cellule[i][j]);
            }
        }

        // Panneau de boutons avec un meilleur alignement et espacement
        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new GridLayout(1, 5, 10, 0)); // Espace entre les boutons
        panelButtons.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Bordure autour des boutons

        btnResoudre = new JButton("Résoudre");
        btnResoudre.setFont(new Font("Arial", Font.BOLD, 14)); // Amélioration de la taille du texte
        btnEffacer = new JButton("Effacer");
        btnEffacer.setFont(new Font("Arial", Font.BOLD, 14)); // Amélioration de la taille du texte

        // ComboBox pour choisir la difficulté
        String[] difficulteOptions = {"Facile", "Moyen", "Difficile"};
        comboBoxDifficulte = new JComboBox<>(difficulteOptions);
        comboBoxDifficulte.setFont(new Font("Arial", Font.PLAIN, 14));

        // ComboBox pour choisir la méthode de résolution
        String[] methodeOptions = {"Règles uniquement", "Backtracking", "Mixte"};
        comboBoxMethodeResolution = new JComboBox<>(methodeOptions);
        comboBoxMethodeResolution.setFont(new Font("Arial", Font.PLAIN, 14));

        // Bouton pour générer une grille
        btnGenerer = new JButton("Générer");
        btnGenerer.setFont(new Font("Arial", Font.BOLD, 14)); // Amélioration de la taille du texte

        panelButtons.add(btnResoudre);
        panelButtons.add(btnEffacer);
        panelButtons.add(comboBoxDifficulte);
        panelButtons.add(comboBoxMethodeResolution);
        panelButtons.add(btnGenerer);

        // Ajout des panneaux à la fenêtre
        add(panelGrille, BorderLayout.CENTER); // La grille de Sudoku
        add(panelButtons, BorderLayout.SOUTH); // Les boutons en bas

        setTitle("Jeu de Sudoku");
        setSize(600, 600); // Agrandir la fenêtre pour plus de confort
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrer la fenêtre
        setVisible(true);
    }

    // Récupère la grille saisie par l'utilisateur (en supposant que le texte dans chaque champ soit le chiffre)
    public char[][] getGrilleSaisie() {
        char[][] grilleSaisie = new char[taille][taille];
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                String text = cellule[i][j].getText().trim();  // Récupérer le texte saisi
                grilleSaisie[i][j] = text.isEmpty() ? '0' : text.charAt(0);  // Si vide, c'est un '0'
            }
        }
        return grilleSaisie;
    }

    // Affiche la grille à l'écran (mise à jour de la vue)
    public void afficherGrille(char[][] grille) {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                cellule[i][j].setText(String.valueOf(grille[i][j]));
            }
        }
    }

    // Récupérer la difficulté sélectionnée par l'utilisateur
    public int getDifficulteSelectionnee() {
        String selected = (String) comboBoxDifficulte.getSelectedItem();
        switch (selected) {
            case "Facile": return 1;
            case "Moyen": return 2;
            case "Difficile": return 3;
            default: return 1;
        }
    }

    // Récupérer la méthode de résolution choisie
    public String getMethodeResolutionSelectionnee() {
        return (String) comboBoxMethodeResolution.getSelectedItem();
    }

    // Méthodes pour obtenir les boutons
    public JButton getBtnResoudre() {
        return btnResoudre;
    }

    public JButton getBtnEffacer() {
        return btnEffacer;
    }

    public JButton getBtnGenerer() {
        return btnGenerer;
    }
}
