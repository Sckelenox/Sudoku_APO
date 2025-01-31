package src;
import java.util.HashSet;
import java.util.Set;

public class Sudoku {
    private Case[][] grille;
    private Bloc[][] blocs;
    private int taille;
    private char[] symboles;

    public Sudoku(int taille, char[] symboles) {
        if (symboles.length != taille) {
            throw new IllegalArgumentException("Le nombre de symboles doit correspondre à la taille du Sudoku.");
        }
        this.taille = taille;
        this.symboles = symboles;
        this.grille = new Case[taille][taille];
        this.blocs = new Bloc[taille / (int) Math.sqrt(taille)][taille / (int) Math.sqrt(taille)];

        // Initialisation des blocs
        for (int i = 0; i < blocs.length; i++) {
            for (int j = 0; j < blocs.length; j++) {
                blocs[i][j] = new Bloc();
            }
        }

        // Initialisation de la grille et assignation aux blocs
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                grille[i][j] = new Case(symboles);
                int blocX = i / (int) Math.sqrt(taille);
                int blocY = j / (int) Math.sqrt(taille);
                blocs[blocX][blocY].ajouterCase(grille[i][j]);
            }
        }
    }
     
    public int getTaille() {
        return taille;
    }

    public Case getCase(int ligne, int colonne) {
        return grille[ligne][colonne];
    }

    public void afficher() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                System.out.print(grille[i][j].getValeur() + " ");
            }
            System.out.println();
        }
    }

    public Set<Character> getPossibilites(int ligne, int colonne) {
        if (!grille[ligne][colonne].estVide()) {
            return new HashSet<>();
        }

        Set<Character> possibilites = new HashSet<>();
        for (char symbole : symboles) {
            possibilites.add(symbole);
        }

        // Exclure les symboles présents dans la ligne et colonne
        for (int i = 0; i < taille; i++) {
            possibilites.remove(grille[ligne][i].getValeur());
            possibilites.remove(grille[i][colonne].getValeur());
        }

        // Exclure les symboles du bloc
        int blocX = ligne / (int) Math.sqrt(taille);
        int blocY = colonne / (int) Math.sqrt(taille);
        for (char symbole : blocs[blocX][blocY].getPossibilites()) {
            possibilites.remove(symbole);
        }

        return possibilites;
    }

    public boolean remplir() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (grille[i][j].estVide()) {
                    for (char symbole : getPossibilites(i, j)) {
                        grille[i][j].setValeur(symbole);
                        if (remplir()) {
                            return true;
                        }
                        grille[i][j].setValeur('.'); // Réinitialisation
                    }
                    return false;
                }
            }
        }
        return true;
    }
    public boolean estValide() {
        // Vérifier chaque ligne
        for (int i = 0; i < taille; i++) {
            if (!estLigneValide(i)) return false;
        }
    
        // Vérifier chaque colonne
        for (int j = 0; j < taille; j++) {
            if (!estColonneValide(j)) return false;
        }
    
        // Vérifier chaque bloc
        int sqrtTaille = (int) Math.sqrt(taille);
        for (int i = 0; i < sqrtTaille; i++) {
            for (int j = 0; j < sqrtTaille; j++) {
                if (!blocs[i][j].estValide()) return false;
            }
        }
    
        return true; // Si tout est valide
    }
    
    private boolean estLigneValide(int ligne) {
        Set<Character> vus = new HashSet<>();
        for (int j = 0; j < taille; j++) {
            char valeur = grille[ligne][j].getValeur();
            if (valeur != '.' && !vus.add(valeur)) {
                return false; // Doublon détecté
            }
        }
        return true;
    }
    
    private boolean estColonneValide(int colonne) {
        Set<Character> vus = new HashSet<>();
        for (int i = 0; i < taille; i++) {
            char valeur = grille[i][colonne].getValeur();
            if (valeur != '.' && !vus.add(valeur)) {
                return false; // Doublon détecté
            }
        }
        return true;
    }
    
}