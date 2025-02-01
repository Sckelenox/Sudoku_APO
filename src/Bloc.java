package src;

import java.util.HashSet;
import java.util.Set;

public class Bloc {
    private int taille; // La taille du Sudoku (par exemple, 9 pour un 9x9)
    private int indice; // L'indice du bloc (par exemple, 0 pour le premier bloc 3x3)
    private Set<Character> valeurs; // Ensemble des valeurs présentes dans le bloc

    public Bloc(int taille, int indice) {
        this.taille = taille;
        this.indice = indice;
        this.valeurs = new HashSet<>();
    }

    // Ajouter une valeur dans le bloc
    public boolean ajouterValeur(char valeur) {
        if (valeurs.contains(valeur)) {
            return false; // La valeur existe déjà dans le bloc
        }
        valeurs.add(valeur);
        return true;
    }

    // Vérifier si une valeur est présente dans le bloc
    public boolean contientValeur(char valeur) {
        return valeurs.contains(valeur);
    }
}

