package src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solveur {

    private Sudoku sudoku;
    private List<String> logs; // Liste pour stocker les logs

    public Solveur(Sudoku sudoku) {
        this.sudoku = sudoku;
        this.logs = new ArrayList<>();
    }

    // Méthode pour résoudre le Sudoku
    public boolean resoudre(boolean avecAffichage, boolean avecRèglesUniquement) {
        logs.clear(); // Réinitialise les logs
        logs.add("Début de la résolution...");

        appliquerContraintes();

        if (sudoku.estRempli()) {
            logs.add(" Sudoku résolu par les règles uniquement.");
            return true;
        }

        if (avecRèglesUniquement) {
            return appliquerRèglesUniquement(avecAffichage);
        }

        return backtracking(avecAffichage);
    }

    // Applique les contraintes de base
    public void appliquerContraintes() {
        logs.add("Application des contraintes de ligne, colonne et bloc...");
        appliquerContrainteLigne();
        appliquerContrainteColonne();
        appliquerContrainteBloc();
    }

    private boolean appliquerRèglesUniquement(boolean avecAffichage) {
        boolean changements = true;
        while (changements) {
            changements = false;

            changements |= appliquerContrainteLigne();
            changements |= appliquerContrainteColonne();
            changements |= appliquerContrainteBloc();

            if (avecAffichage) {
                sudoku.afficher();
                logs.add(" Application des règles, mise à jour de la grille.");
            }
        }

        logs.add("Résolution par les règles terminée.");
        return sudoku.estRempli();
    }

    // Applique les contraintes sur les lignes
    private boolean appliquerContrainteLigne() {
        boolean changements = false;
        for (int i = 0; i < sudoku.getTaille(); i++) {
            Set<Character> chiffresUtilises = new HashSet<>();
            for (int j = 0; j < sudoku.getTaille(); j++) {
                char valeur = sudoku.getCase(i, j);
                if (valeur != Sudoku.VIDE) {
                    chiffresUtilises.add(valeur);
                }
            }

            for (int j = 0; j < sudoku.getTaille(); j++) {
                if (sudoku.getCase(i, j) == Sudoku.VIDE) {
                    for (char c : chiffresUtilises) {
                        if (sudoku.supprimerPossibilite(i, j, c)) {
                            changements = true;
                        }
                    }
                }
            }
        }
        return changements;
    }

    // Applique les contraintes sur les colonnes
    private boolean appliquerContrainteColonne() {
        boolean changements = false;
        for (int j = 0; j < sudoku.getTaille(); j++) {
            Set<Character> chiffresUtilises = new HashSet<>();
            for (int i = 0; i < sudoku.getTaille(); i++) {
                char valeur = sudoku.getCase(i, j);
                if (valeur != Sudoku.VIDE) {
                    chiffresUtilises.add(valeur);
                }
            }

            for (int i = 0; i < sudoku.getTaille(); i++) {
                if (sudoku.getCase(i, j) == Sudoku.VIDE) {
                    for (char c : chiffresUtilises) {
                        if (sudoku.supprimerPossibilite(i, j, c)) {
                            changements = true;
                        }
                    }
                }
            }
        }
        return changements;
    }

    // Applique les contraintes sur les blocs
    private boolean appliquerContrainteBloc() {
        boolean changements = false;
        int tailleBloc = (int) Math.sqrt(sudoku.getTaille());
        for (int i = 0; i < sudoku.getTaille(); i++) {
            for (int j = 0; j < sudoku.getTaille(); j++) {
                int ligneBloc = (i / tailleBloc) * tailleBloc;
                int colBloc = (j / tailleBloc) * tailleBloc;
                Set<Character> chiffresUtilises = new HashSet<>();

                for (int m = ligneBloc; m < ligneBloc + tailleBloc; m++) {
                    for (int n = colBloc; n < colBloc + tailleBloc; n++) {
                        char valeur = sudoku.getCase(m, n);
                        if (valeur != Sudoku.VIDE) {
                            chiffresUtilises.add(valeur);
                        }
                    }
                }

                if (sudoku.getCase(i, j) == Sudoku.VIDE) {
                    for (char c : chiffresUtilises) {
                        if (sudoku.supprimerPossibilite(i, j, c)) {
                            changements = true;
                        }
                    }
                }
            }
        }
        return changements;
    }

    // Algorithme de Backtracking
    private boolean backtracking(boolean avecAffichage) {
        int[] caseVide = trouverCaseVide();
        if (caseVide == null) {
            logs.add("Sudoku résolu !");
            return true;
        }

        int ligne = caseVide[0];
        int colonne = caseVide[1];

        logs.add("Tentative de remplir la case (" + ligne + ", " + colonne + ")");

        for (char valeur : sudoku.getPossibilites(ligne, colonne)) {
            if (estValide(sudoku, ligne, colonne, valeur)) {
                sudoku.setCase(ligne, colonne, valeur);
                logs.add("Placé " + valeur + " à (" + ligne + ", " + colonne + ")");

                if (backtracking(avecAffichage)) {
                    return true;
                }

                sudoku.setCase(ligne, colonne, Sudoku.VIDE);
                logs.add("⏪ Retour en arrière, réinitialisation de (" + ligne + ", " + colonne + ")");
            }
        }
        return false;
    }

    // Méthodes auxiliaires
    private boolean estValide(Sudoku sudoku, int ligne, int colonne, char valeur) {
        return !estDansLigne(ligne, valeur) && !estDansColonne(colonne, valeur) && !estDansBloc(ligne, colonne, valeur);
    }

    private boolean estDansLigne(int ligne, char valeur) {
        for (int i = 0; i < sudoku.getTaille(); i++) {
            if (sudoku.getCase(ligne, i) == valeur) {
                return true;
            }
        }
        return false;
    }

    private boolean estDansColonne(int colonne, char valeur) {
        for (int i = 0; i < sudoku.getTaille(); i++) {
            if (sudoku.getCase(i, colonne) == valeur) {
                return true;
            }
        }
        return false;
    }

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

    private int[] trouverCaseVide() {
        for (int i = 0; i < sudoku.getTaille(); i++) {
            for (int j = 0; j < sudoku.getTaille(); j++) {
                if (sudoku.getCase(i, j) == Sudoku.VIDE) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    // Retourne les logs
    public List<String> getLogs() {
        return logs;
    }
    public int compterSolutions() {
        return compterSolutionsRecursive(sudoku, 0, 0);
    }

    private int compterSolutionsRecursive(Sudoku sudoku, int ligne, int col) {
        // Si nous avons atteint la fin de la grille (toutes les cases sont remplies)
        if (ligne == sudoku.getTaille()) {
            return 1;  // Une solution trouvée
        }

        // Si nous avons rempli une ligne, passons à la ligne suivante
        if (col == sudoku.getTaille()) {
            return compterSolutionsRecursive(sudoku, ligne + 1, 0);
        }

        // Si la case est déjà remplie, passez à la case suivante
        if (sudoku.getCase(ligne, col) != Sudoku.VIDE) {
            return compterSolutionsRecursive(sudoku, ligne, col + 1);
        }

        int totalSolutions = 0;

        // Essayer toutes les valeurs possibles dans la case vide
        for (char valeur = '1'; valeur <= '9'; valeur++) {
            if (estValide(sudoku, ligne, col, valeur)) {
                sudoku.setCase(ligne, col, valeur);  // Placer la valeur dans la case

                // Appel récursif pour la prochaine case
                totalSolutions += compterSolutionsRecursive(sudoku, ligne, col + 1);

                sudoku.setCase(ligne, col, Sudoku.VIDE);  // Retour en arrière (backtrack)
            }
        }

        return totalSolutions;
    }
}



