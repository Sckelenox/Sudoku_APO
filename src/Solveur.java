package src;

import java.util.HashSet;
import java.util.Set;

public class Solveur {

    private Sudoku sudoku;

    public Solveur(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    // Méthode pour résoudre le Sudoku
    public boolean resoudre(boolean avecAffichage) {
        // Applique les contraintes initiales
        appliquerContraintes();

        // Si la grille est déjà remplie, renvoie true
        if (sudoku.estRempli()) {
            return true;
        }

        // Sinon, commence à essayer de remplir la grille
        return backtracking(avecAffichage);
    }

    // Applique les contraintes de base : lignes, colonnes et blocs
    public void appliquerContraintes() {
        appliquerContrainteLigne();
        appliquerContrainteColonne();
        appliquerContrainteBloc();
    }

    // Applique les contraintes sur les lignes
    private void appliquerContrainteLigne() {
        for (int i = 0; i < sudoku.getTaille(); i++) {
            Set<Character> chiffresUtilises = new HashSet<>();
            for (int j = 0; j < sudoku.getTaille(); j++) {
                char valeur = sudoku.getCase(i, j);
                if (valeur != Sudoku.VIDE) {
                    chiffresUtilises.add(valeur);
                }
            }

            // Parcourt de nouveau la ligne pour supprimer les possibilités
            for (int j = 0; j < sudoku.getTaille(); j++) {
                if (sudoku.getCase(i, j) == Sudoku.VIDE) {
                    for (char c : chiffresUtilises) {
                        sudoku.supprimerPossibilite(i, j, c);
                    }
                }
            }
        }
    }

    // Applique les contraintes sur les colonnes
    private void appliquerContrainteColonne() {
        for (int j = 0; j < sudoku.getTaille(); j++) {
            Set<Character> chiffresUtilises = new HashSet<>();
            for (int i = 0; i < sudoku.getTaille(); i++) {
                char valeur = sudoku.getCase(i, j);
                if (valeur != Sudoku.VIDE) {
                    chiffresUtilises.add(valeur);
                }
            }

            // Parcourt de nouveau la colonne pour supprimer les possibilités
            for (int i = 0; i < sudoku.getTaille(); i++) {
                if (sudoku.getCase(i, j) == Sudoku.VIDE) {
                    for (char c : chiffresUtilises) {
                        sudoku.supprimerPossibilite(i, j, c);
                    }
                }
            }
        }
    }

    // Applique les contraintes sur les blocs
    private void appliquerContrainteBloc() {
        int tailleBloc = (int) Math.sqrt(sudoku.getTaille());
        for (int i = 0; i < sudoku.getTaille(); i++) {
            for (int j = 0; j < sudoku.getTaille(); j++) {
                int ligneBloc = (i / tailleBloc) * tailleBloc;
                int colBloc = (j / tailleBloc) * tailleBloc;
                Set<Character> chiffresUtilises = new HashSet<>();

                // Vérifie les chiffres dans le bloc
                for (int m = ligneBloc; m < ligneBloc + tailleBloc; m++) {
                    for (int n = colBloc; n < colBloc + tailleBloc; n++) {
                        char valeur = sudoku.getCase(m, n);
                        if (valeur != Sudoku.VIDE) {
                            chiffresUtilises.add(valeur);
                        }
                    }
                }

                // Supprime les possibilités dans le bloc
                if (sudoku.getCase(i, j) == Sudoku.VIDE) {
                    for (char c : chiffresUtilises) {
                        sudoku.supprimerPossibilite(i, j, c);
                    }
                }
            }
        }
    }

    // Algorithme de Backtracking pour résoudre le Sudoku
    private boolean backtracking(boolean avecAffichage) {
        // Trouve la première case vide
        int[] caseVide = trouverCaseVide();
        if (caseVide == null) {
            return true; // Si aucune case vide, la grille est résolue
        }

        int ligne = caseVide[0];
        int colonne = caseVide[1];

        // Essaye chaque possibilité
        for (char valeur : sudoku.getPossibilites(ligne, colonne)) {
            if (estValide(ligne, colonne, valeur)) {
                sudoku.setCase(ligne, colonne, valeur);

                // Affichage si demandé
                if (avecAffichage) {
                    System.out.println("Tentative de remplissage (" + ligne + "," + colonne + ") avec " + valeur);
                    sudoku.afficher();
                }

                // Résolution récursive
                if (backtracking(avecAffichage)) {
                    return true;
                }

                // Si ça échoue, réinitialise la case
                sudoku.setCase(ligne, colonne, Sudoku.VIDE);
            }
        }
        return false; // Retourne false si aucune solution n'est trouvée
    }

    // Vérifie si une valeur est valide à un emplacement donné
    private boolean estValide(int ligne, int colonne, char valeur) {
        return !estDansLigne(ligne, valeur) && !estDansColonne(colonne, valeur) && !estDansBloc(ligne, colonne, valeur);
    }

    // Vérifie si une valeur existe déjà dans la ligne
    private boolean estDansLigne(int ligne, char valeur) {
        for (int i = 0; i < sudoku.getTaille(); i++) {
            if (sudoku.getCase(ligne, i) == valeur) {
                return true;
            }
        }
        return false;
    }

    // Vérifie si une valeur existe déjà dans la colonne
    private boolean estDansColonne(int colonne, char valeur) {
        for (int i = 0; i < sudoku.getTaille(); i++) {
            if (sudoku.getCase(i, colonne) == valeur) {
                return true;
            }
        }
        return false;
    }

    // Vérifie si une valeur existe dans le bloc
    private boolean estDansBloc(int ligne, int colonne, char valeur) {
        int tailleBloc = (int) Math.sqrt(sudoku.getTaille());
        int ligneBloc = (ligne / tailleBloc) * tailleBloc;
        int colBloc = (colonne / tailleBloc) * tailleBloc;
        for (int i = ligneBloc; i < ligneBloc + tailleBloc; i++) {
            for (int j = colBloc; j < colBloc + tailleBloc; j++) {
                if (sudoku.getCase(i, j) == valeur) {
                    return true;
                }
            }
        }
        return false;
    }

    // Trouve la première case vide
    private int[] trouverCaseVide() {
        for (int i = 0; i < sudoku.getTaille(); i++) {
            for (int j = 0; j < sudoku.getTaille(); j++) {
                if (sudoku.getCase(i, j) == Sudoku.VIDE) {
                    return new int[] {i, j};
                }
            }
        }
        return null; // Si aucune case vide n'est trouvée
    }
}


