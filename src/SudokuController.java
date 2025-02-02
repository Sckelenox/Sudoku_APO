package src;

import src.view.SudokuView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuController {
    private Sudoku model;
    private Solveur solveur;
    private SudokuView view;
    private Generateur generateur;


    public SudokuController(Sudoku model, SudokuView view) {
        this.model = model;
        this.view = view;
        this.solveur = new Solveur(model);
        initController();
    }

    private void initController() {
        // Lorsqu'on clique sur "Résoudre"
        view.getBtnResoudre().addActionListener(e -> {
            // Mettre à jour le modèle avec la grille saisie par l'utilisateur
            char[][] grilleSaisie = view.getGrilleSaisie();
            int taille = model.getTaille();
            for (int i = 0; i < taille; i++) {
                for (int j = 0; j < taille; j++) {
                    model.setCase(i, j, grilleSaisie[i][j]);
                }
            }

            // Résoudre le Sudoku
            boolean resolu = solveur.resoudre(false, false); // Appelez la méthode appropriée pour résoudre

            if (resolu) {
                JOptionPane.showMessageDialog(view, "✅ Sudoku résolu !");
            } else {
                JOptionPane.showMessageDialog(view, "❌ Impossible de résoudre ce Sudoku.");
            }

            // Mettre à jour l'affichage de la grille
            view.afficherGrille(getModelGrille()); // Assurez-vous que cette méthode met à jour la vue avec la grille résolue
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

        // Lorsqu'on clique sur "Générer"
        view.getBtnGenerer().addActionListener(e -> {
            // Récupérer le niveau de difficulté
            int niveau = view.getDifficulteSelectionnee();

            // Créer une instance du générateur pour générer une grille complète
            Generateur generateur = new Generateur(model.getTaille(), genererSymboles(model.getTaille()));

            // Générer la grille complète (résolue)
            Sudoku grilleComplete = generateur.genererGrilleComplete();

            // Générer une grille jouable en fonction du niveau de difficulté
            Sudoku grilleJouable = generateur.genererGrilleJouable(grilleComplete, niveau);

            // Assigner la grille jouable générée au modèle
            model = grilleJouable;

            // Afficher la grille générée dans la vue
            view.afficherGrille(getModelGrille());

            JOptionPane.showMessageDialog(view, "✅ Grille générée avec succès !");
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

    // Méthode pour générer les symboles (les chiffres à utiliser pour remplir la grille)
    private char[] genererSymboles(int taille) {
        char[] symboles = new char[taille];
        for (int i = 0; i < taille; i++) {
            symboles[i] = (char) ('1' + i);  // Utiliser les symboles de 1 à taille
        }
        return symboles;
    }
}
