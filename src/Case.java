package src;
import java.util.HashSet;
import java.util.Set;

public class Case {
    private char valeur;
    private Set<Character> possibilites;

    public Case(char[] symboles) {
        this.valeur = '.'; // Case vide
        this.possibilites = new HashSet<>();
        for (char symbole : symboles) {
            this.possibilites.add(symbole);
        }
    }

    public char getValeur() {
        return valeur;
    }

    public void setValeur(char valeur) {
        this.valeur = valeur;
        possibilites.clear(); // Plus de possibilités une fois une valeur assignée
    }

    public boolean estVide() {
        return valeur == '.';
    }

    public Set<Character> getPossibilites() {
        return possibilites;
    }
}
