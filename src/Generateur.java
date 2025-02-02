package src;

import java.util.*;

public class Generateur {
    private int taille;
    private char[] symboles;
    private Random random = new Random();

    public Generateur(int taille, char[] symboles) {
        this.taille = taille;
        this.symboles = symboles;
    }

    // Méthode pour générer une grille complète et valide
    public Sudoku genererGrilleComplete() {
        Sudoku sudoku = new Sudoku(taille, symboles);
        if (remplirGrille(sudoku, 0, 0)) {
            sudoku.afficher(); // Afficher la grille après l'avoir remplie
            return sudoku;
        } else {
            System.out.println("Échec de la génération d'une grille complète.");
            return null;
        }
    }

    // Méthode récursive pour remplir la grille de manière valide
    private boolean remplirGrille(Sudoku sudoku, int ligne, int colonne) {
        // Si nous avons atteint la fin de la grille (dernière colonne de la dernière ligne)
        if (colonne == taille) {
            return remplirGrille(sudoku, ligne + 1, 0); // Passer à la ligne suivante
        }
        if (ligne == taille) {
            return true; // La grille est complète
        }

        // Liste des valeurs possibles pour cette case
        List<Character> valeurs = new ArrayList<>();
        for (char c : symboles) {
            if (!sudoku.estDansLigne(ligne, c) && !sudoku.estDansColonne(colonne, c) && !sudoku.estDansBloc(ligne, colonne, c)) {
                valeurs.add(c);
            }
        }
        Collections.shuffle(valeurs);  // Mélange des valeurs pour la randomisation

        for (char valeur : valeurs) {
            if (!sudoku.estDansLigne(ligne, valeur) && !sudoku.estDansColonne(colonne, valeur) && !sudoku.estDansBloc(ligne, colonne, valeur)) {
                sudoku.setCase(ligne, colonne, valeur);
                sudoku.updatePossibilites(ligne, colonne, valeur);
                if (remplirGrille(sudoku, ligne, colonne + 1)) {
                    return true;
                }
                sudoku.restorePossibilites(ligne, colonne, valeur);
                sudoku.setCase(ligne, colonne, Sudoku.VIDE); // Annuler si nécessaire
            }
        }
        return false;  // Aucun placement valide trouvé
    }

    // Méthode pour générer une grille jouable à partir d'une grille complète
    public Sudoku genererGrilleJouable(Sudoku grilleComplete, int difficulte) {
        Sudoku sudoku = new Sudoku(taille, symboles);
        copierGrille(grilleComplete, sudoku); // Copier la grille complète dans une nouvelle grille
        int casesASupprimer = (difficulte * taille * taille) / 10;

        // Liste des positions des cases à vider
        List<int[]> positions = new ArrayList<>();
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                positions.add(new int[]{i, j});
            }
        }
        Collections.shuffle(positions); // Mélange des positions à vider

        int suppressionsEffectuees = 0;
        for (int[] pos : positions) {
            if (suppressionsEffectuees >= casesASupprimer) break;

            char temp = sudoku.getCase(pos[0], pos[1]);
            sudoku.setCase(pos[0], pos[1], Sudoku.VIDE);

            if (aSolutionUnique(sudoku)) {
                suppressionsEffectuees++;
            } else {
                sudoku.setCase(pos[0], pos[1], temp); // Annule si pas de solution unique
            }
        }

        return sudoku;
    }

    // Vérifie si la grille a une seule solution
    private boolean aSolutionUnique(Sudoku sudoku) {
        Solveur solveur = new Solveur(sudoku);
        return solveur.compterSolutions() == 1; // Vérifie qu'il y a une seule solution possible
    }

    // Copie la grille source vers la grille destination
    private void copierGrille(Sudoku source, Sudoku destination) {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                destination.setCase(i, j, source.getCase(i, j));
            }
        }
    }
}

