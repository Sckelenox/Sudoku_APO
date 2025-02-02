package src;

import java.util.HashSet;
import java.util.Set;

public class Solveur {

    private Sudoku sudoku;

    public Solveur(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    // Méthode pour résoudre le Sudoku
    public boolean resoudre(boolean avecAffichage, boolean avecRèglesUniquement) {
        // Applique les contraintes initiales
        appliquerContraintes();

        // Si la grille est déjà remplie, renvoie true
        if (sudoku.estRempli()) {
            System.out.println("✅ Sudoku déjà résolu par les règles.");
            return true;
        }

        // Si on ne veut utiliser que les règles, on applique les règles et vérifie si la grille est résolue
        if (avecRèglesUniquement) {
            return appliquerRèglesUniquement(avecAffichage);
        }

        // Sinon, on utilise le backtracking
        return backtracking(avecAffichage);
    }

    // Applique les contraintes de base : lignes, colonnes et blocs
    public void appliquerContraintes() {
        appliquerContrainteLigne();
        appliquerContrainteColonne();
        appliquerContrainteBloc();
    }

    // Applique les règles de base uniquement, sans backtracking
    private boolean appliquerRèglesUniquement(boolean avecAffichage) {
        boolean changements = true;
        while (changements) {
            changements = false;

            // Appliquer les contraintes à chaque itération et vérifier s'il y a des changements
            changements |= appliquerContrainteLigne();
            changements |= appliquerContrainteColonne();
            changements |= appliquerContrainteBloc();

            // Affichage des étapes si demandé
            if (avecAffichage) {
                sudoku.afficher();
                System.out.println("🔄 Application des règles, mise à jour de la grille.");
            }
        }

        // Retourne true si le Sudoku est résolu après application des règles
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

            // Parcourt de nouveau la ligne pour supprimer les possibilités
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

            // Parcourt de nouveau la colonne pour supprimer les possibilités
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
                        if (sudoku.supprimerPossibilite(i, j, c)) {
                            changements = true;
                        }
                    }
                }
            }
        }
        return changements;
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

        // Log de l'état actuel
        if (avecAffichage) {
            System.out.println("Tentative de remplir la case (" + ligne + ", " + colonne + ")");
            System.out.println("Possibilités : " + sudoku.getPossibilites(ligne, colonne));
        }

        // Essaye chaque possibilité
        for (char valeur : sudoku.getPossibilites(ligne, colonne)) {
            if (estValide(ligne, colonne, valeur)) {
                sudoku.setCase(ligne, colonne, valeur);

                // Affichage si demandé
                if (avecAffichage) {
                    System.out.println("Placé " + valeur + " à (" + ligne + ", " + colonne + ")");
                    sudoku.afficher();
                }

                // Résolution récursive
                if (backtracking(avecAffichage)) {
                    return true;
                }

                // Si ça échoue, réinitialise la case
                sudoku.setCase(ligne, colonne, Sudoku.VIDE);
                if (avecAffichage) {
                    System.out.println("Retour sur trace, réinitialisation de la case (" + ligne + ", " + colonne + ")");
                }
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

    // Compte le nombre de solutions possibles (utile pour vérifier si un Sudoku est bien posé)
    public int compterSolutions() {
        int[] count = {0}; // Utilisation d'un tableau pour passer par référence
        compterSolutionsBacktrack(0, 0, count);
        return count[0];
    }

    // Backtracking pour compter les solutions possibles
    private void compterSolutionsBacktrack(int ligne, int colonne, int[] count) {
        if (ligne == sudoku.getTaille()) {
            count[0]++;
            return;
        }

        if (colonne == sudoku.getTaille()) {
            compterSolutionsBacktrack(ligne + 1, 0, count);
            return;
        }

        if (sudoku.getCase(ligne, colonne) != Sudoku.VIDE) {
            compterSolutionsBacktrack(ligne, colonne + 1, count);
            return;
        }

        for (char valeur : sudoku.getPossibilites(ligne, colonne)) {
            if (!estValide(ligne, colonne, valeur)) {
                continue;
            }

            sudoku.setCase(ligne, colonne, valeur);
            compterSolutionsBacktrack(ligne, colonne + 1, count);
            sudoku.setCase(ligne, colonne, Sudoku.VIDE); // Annule la tentative

            if (count[0] > 1) { // Si plus d'une solution trouvée, on arrête
                return;
            }
        }
    }
}



