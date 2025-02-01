package src;

import java.util.HashSet;
import java.util.Set;

public class Case {
    private char valeur;
    private Set<Character> possibilites;
    private char[] symboles; // Pour recréer l'ensemble complet en cas de besoin

    public Case(char[] symboles) {
        this.valeur = '.'; // Case vide
        this.symboles = symboles;
        this.possibilites = new HashSet<>();
        for (char symbole : symboles) {
            this.possibilites.add(symbole);
        }
    }

    // Constructeur de clonage
    public Case(Case autre) {
        this.valeur = autre.valeur;
        this.symboles = autre.symboles; // On suppose que ce tableau ne change pas
        this.possibilites = new HashSet<>(autre.possibilites);
    }

    public char getValeur() {
        return valeur;
    }

    public void setValeur(char valeur) {
        this.valeur = valeur;
        possibilites.clear(); // La case est fixée, plus de possibilités
    }

    // Méthode pour retirer une possibilité
    public void retirerPossibilite(char val) {
        possibilites.remove(val);
    }

    public boolean estVide() {
        return valeur == '.';
    }

    public Set<Character> getPossibilites() {
        return possibilites;
    }
}
