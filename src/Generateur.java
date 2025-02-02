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

    public Sudoku genererGrilleComplete() {
        Sudoku sudoku = new Sudoku(taille, symboles);
        remplirGrille(sudoku, 0, 0);
        sudoku.afficher(); // Afficher la grille après l'avoir remplie
        return sudoku;
    }

    private boolean remplirGrille(Sudoku sudoku, int ligne, int colonne) {
        // Si nous avons atteint la fin de la grille
        if (colonne == taille) {
            return remplirGrille(sudoku, ligne + 1, 0); // Passer à la ligne suivante
        }
        if (ligne == taille) {
            // Vérifier qu'il n'y a pas de case vide à la fin
            for (int i = 0; i < taille; i++) {
                for (int j = 0; j < taille; j++) {
                    if (sudoku.getCase(i, j) == Sudoku.VIDE) {
                        return false;
                    }
                }
            }
            return true; // Si toutes les cases sont remplies, retournez vrai
        }

        // Liste des valeurs possibles pour cette case
        List<Character> valeurs = new ArrayList<>();
        for (char c : symboles) {
            valeurs.add(c);
        }
        Collections.shuffle(valeurs); // Mélange les valeurs pour ajouter de la randomisation

        // Essayer de remplir la case avec les valeurs possibles
        for (char valeur : valeurs) {
            if (estPlacementValide(sudoku, ligne, colonne, valeur)) {
                sudoku.setCase(ligne, colonne, valeur); // Placer la valeur
                if (remplirGrille(sudoku, ligne, colonne + 1)) {
                    return true; // Récursion : continuer à remplir la grille
                }
                sudoku.setCase(ligne, colonne, Sudoku.VIDE); // Si on échoue, on effectue un backtrack
            }
        }

        return false; // Si aucune valeur n'est valide, retournez false
    }


    private boolean estPlacementValide(Sudoku sudoku, int ligne, int colonne, char valeur) {
        return !sudoku.estDansLigne(ligne, valeur) &&
                !sudoku.estDansColonne(colonne, valeur) &&
                !sudoku.estDansBloc(ligne, colonne, valeur);
    }


    // Génère plusieurs grilles jouables à partir d'une grille complète
    public List<Sudoku> genererGrillesJouables(int nombreGrilles, int difficulte) {
        List<Sudoku> grillesJouables = new ArrayList<>();

        for (int i = 0; i < nombreGrilles; i++) {
            Sudoku grilleComplete = genererGrilleComplete();
            Sudoku grilleJouable = genererGrilleJouable(grilleComplete, difficulte);
            grillesJouables.add(grilleJouable);
        }

        return grillesJouables;
    }

    // Génère une grille jouable à partir d'une grille complète
    public Sudoku genererGrilleJouable(Sudoku grilleComplete, int difficulte) {
        Sudoku sudoku = new Sudoku(taille, symboles);
        copierGrille(grilleComplete, sudoku);
        int casesASupprimer = (difficulte * taille * taille) / 10;

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
