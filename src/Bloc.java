package src;
import java.util.HashSet;
import java.util.Set;

public class Bloc {
    private Set<Case> cases; // Ensemble des cases du bloc

    public Bloc() {
        this.cases = new HashSet<>();
    }

    public void ajouterCase(Case c) {
        cases.add(c);
    }

    public boolean contient(char symbole) {
        for (Case c : cases) {
            if (c.getValeur() == symbole) {
                return true;
            }
        }
        return false;
    }

    public Set<Character> getPossibilites() {
        Set<Character> possibilites = new HashSet<>();
        for (Case c : cases) {
            possibilites.addAll(c.getPossibilites());
        }
        return possibilites;
    }

public boolean estValide() {
    Set<Character> vus = new HashSet<>();
    for (Case c : cases) {
        char valeur = c.getValeur();
        if (valeur != '.' && !vus.add(valeur)) {
            return false; // Doublon dans le bloc
        }
    }
    return true;
}
}

