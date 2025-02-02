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
