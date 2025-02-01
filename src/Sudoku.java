package src;

import java.util.HashSet;
import java.util.Set;

public class Sudoku {

    public static final char VIDE = '0'; // Représente une case vide
    private char[][] grille; // La grille du Sudoku
    private Set<Character>[][] possibilites; // Liste des possibilités pour chaque case
    private int taille; // Taille de la grille (par exemple 9x9 ou 4x4)
    private char[] symboles; // Symboles utilisés (par exemple '1', '2', '3', '4' pour une grille 4x4)
    private Bloc[][] blocs; // Les blocs du Sudoku

    public Sudoku(int taille, char[] symboles) {
        this.taille = taille;
        this.symboles = symboles;
        this.grille = new char[taille][taille];
        this.possibilites = new Set[taille][taille];
        this.blocs = new Bloc[taille / (int) Math.sqrt(taille)][taille / (int) Math.sqrt(taille)];

        // Initialisation de la grille avec des cases vides
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                this.grille[i][j] = VIDE;
                this.possibilites[i][j] = new HashSet<>();
                for (char c : symboles) {
                    this.possibilites[i][j].add(c);
                }
            }
        }

        // Initialisation des blocs
        int blocIndex = 0;
        for (int i = 0; i < taille / (int) Math.sqrt(taille); i++) {
            for (int j = 0; j < taille / (int) Math.sqrt(taille); j++) {
                blocs[i][j] = new Bloc(taille, blocIndex++);
            }
        }
    }

    // Méthode pour afficher la grille
    public void afficher() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                System.out.print(grille[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Méthode pour vérifier si le Sudoku est rempli
    public boolean estRempli() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (grille[i][j] == VIDE) {
                    return false;
                }
            }
        }
        return true;
    }

    // Méthode pour vérifier si le Sudoku est valide (pas de doublons dans les lignes, colonnes et blocs)
    public boolean estValide() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                char valeur = grille[i][j];
                if (valeur != VIDE) {
                    // Vérifie ligne, colonne et bloc
                    if (estDansLigne(i, valeur) || estDansColonne(j, valeur) || estDansBloc(i, j, valeur)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // Méthodes pour vérifier les contraintes de ligne, colonne et bloc
    private boolean estDansLigne(int ligne, char valeur) {
        for (int i = 0; i < taille; i++) {
            if (grille[ligne][i] == valeur) {
                return true;
            }
        }
        return false;
    }

    private boolean estDansColonne(int colonne, char valeur) {
        for (int i = 0; i < taille; i++) {
            if (grille[i][colonne] == valeur) {
                return true;
            }
        }
        return false;
    }

    private boolean estDansBloc(int ligne, int colonne, char valeur) {
        int tailleBloc = (int) Math.sqrt(taille);
        int ligneBloc = (ligne / tailleBloc) * tailleBloc;
        int colBloc = (colonne / tailleBloc) * tailleBloc;
        return blocs[ligneBloc / tailleBloc][colBloc / tailleBloc].contientValeur(valeur);
    }

    // Méthode pour définir une valeur dans la grille
    public void setCase(int ligne, int colonne, char valeur) {
        grille[ligne][colonne] = valeur;
        blocs[ligne / (int) Math.sqrt(taille)][colonne / (int) Math.sqrt(taille)].ajouterValeur(valeur);
    }

    // Méthode pour récupérer les possibilités d'une case
    public Set<Character> getPossibilites(int ligne, int colonne) {
        return possibilites[ligne][colonne];
    }

    // Méthode pour supprimer une possibilité pour une case
    public void supprimerPossibilite(int ligne, int colonne, char valeur) {
        possibilites[ligne][colonne].remove(valeur);
    }

    // Accesseur à la taille de la grille
    public int getTaille() {
        return taille;
    }

    // Méthode pour vérifier si la grille est valide selon les règles du Sudoku
    public boolean verifier() {
        // Il est valide si chaque ligne, colonne et bloc ne contiennent pas de doublons
        return estValide();
    }

    public char getCase(int i, int j) {
        return grille[i][j];
    }
}
