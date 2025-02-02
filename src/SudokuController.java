package src;

import src.Sudoku;
import src.Solveur;
import src.view.SudokuView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuController {
    private Sudoku model;
    private Solveur solveur;
    private SudokuView view;

    public SudokuController(Sudoku model, SudokuView view) {
        this.model = model;
        this.view = view;
        // Créez le solveur avec le modèle
        this.solveur = new Solveur(model);
        initController();
    }

    private void initController() {
        // Lorsqu'on clique sur "Résoudre"
        view.getBtnResoudre().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mettre à jour le modèle avec la grille saisie par l'utilisateur
                char[][] grilleSaisie = view.getGrilleSaisie();
                int taille = model.getTaille();
                for (int i = 0; i < taille; i++) {
                    for (int j = 0; j < taille; j++) {
                        model.setCase(i, j, grilleSaisie[i][j]);
                    }
                }
                // Tenter de résoudre le sudoku (par exemple, uniquement par déduction ou backtracking)
                boolean resolu = solveur.resoudre(false, false);

                if (resolu) {
                    JOptionPane.showMessageDialog(view, "✅ Sudoku résolu !");
                } else {
                    JOptionPane.showMessageDialog(view, "❌ Impossible de résoudre ce Sudoku.");
                }
                // Mettre à jour l'affichage de la grille
                view.afficherGrille(getModelGrille());
            }
        });

        // Lorsqu'on clique sur "Effacer"
        view.getBtnEffacer().addActionListener(e -> {
            // Effacer la grille de la vue et réinitialiser le modèle
            int taille = model.getTaille();
            char[][] vide = new char[taille][taille];
            for (int i = 0; i < taille; i++) {
                for (int j = 0; j < taille; j++) {
                    vide[i][j] = Sudoku.VIDE;
                    model.setCase(i, j, Sudoku.VIDE);
                }
            }
            view.afficherGrille(vide);
        });
    }

    // Méthode d'aide pour récupérer la grille actuelle du modèle
    private char[][] getModelGrille() {
        int taille = model.getTaille();
        char[][] grille = new char[taille][taille];
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                grille[i][j] = model.getCase(i, j);
            }
        }
        return grille;
    }
}
