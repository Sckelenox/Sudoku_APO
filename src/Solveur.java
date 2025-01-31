package src;

import java.util.HashSet;
import java.util.Set;

public class Solveur {
    private Sudoku sudoku;

    public Solveur(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    // Méthode pour résoudre la grille
    public boolean resoudre() {
        // Appliquer d'abord les contraintes simples
        appliquerContraintes();

        // Utiliser le backtracking pour résoudre
        return resoudreBacktracking();
    }
     // Appliquer la règle des paires et triples : Si deux cases dans une ligne, une colonne ou un bloc ne peuvent contenir que deux symboles, 
// alors les autres cases de la même ligne, colonne ou bloc ne peuvent pas contenir ces symboles.
private boolean appliquerPairesEtTriples() {
    boolean change = false;

    // Vérifier les lignes, colonnes et blocs
    for (int i = 0; i < sudoku.getTaille(); i++) {
        // Vérifier les paires et triples dans chaque ligne
        if (verifierPairesEtTriplesDansLigne(i)) {
            change = true;
        }
        // Vérifier les paires et triples dans chaque colonne
        if (verifierPairesEtTriplesDansColonne(i)) {
            change = true;
        }
        // Vérifier les paires et triples dans chaque bloc
        if (verifierPairesEtTriplesDansBloc(i)) {
            change = true;
        }
    }

    return change;
}

// Vérifier les paires et triples dans une ligne
private boolean verifierPairesEtTriplesDansLigne(int ligne) {
    boolean change = false;
    Set<Character> possibles = new HashSet<>();

    // Récupérer toutes les possibilités pour la ligne
    for (int j = 0; j < sudoku.getTaille(); j++) {
        possibles.addAll(sudoku.getCase(ligne, j).getPossibilites());
    }

    // Trouver les paires et triples dans les possibilités
    for (int j1 = 0; j1 < sudoku.getTaille(); j1++) {
        for (int j2 = j1 + 1; j2 < sudoku.getTaille(); j2++) {
            // Si les deux cases peuvent contenir seulement 2 symboles
            Set<Character> intersection = new HashSet<>(sudoku.getCase(ligne, j1).getPossibilites());
            intersection.retainAll(sudoku.getCase(ligne, j2).getPossibilites());
            if (intersection.size() == 2) { // Paire trouvée
                // Supprimer ces symboles des autres cases de la ligne
                for (int j = 0; j < sudoku.getTaille(); j++) {
                    if (j != j1 && j != j2) {
                        for (char symbole : intersection) {
                            sudoku.getCase(ligne, j).getPossibilites().remove(symbole);
                            change = true;
                        }
                    }
                }
            }
        }
    }
    return change;
}

// Vérifier les paires et triples dans une colonne
private boolean verifierPairesEtTriplesDansColonne(int colonne) {
    boolean change = false;
    Set<Character> possibles = new HashSet<>();

    // Récupérer toutes les possibilités pour la colonne
    for (int i = 0; i < sudoku.getTaille(); i++) {
        possibles.addAll(sudoku.getCase(i, colonne).getPossibilites());
    }

    // Trouver les paires et triples dans les possibilités
    for (int i1 = 0; i1 < sudoku.getTaille(); i1++) {
        for (int i2 = i1 + 1; i2 < sudoku.getTaille(); i2++) {
            // Si les deux cases peuvent contenir seulement 2 symboles
            Set<Character> intersection = new HashSet<>(sudoku.getCase(i1, colonne).getPossibilites());
            intersection.retainAll(sudoku.getCase(i2, colonne).getPossibilites());
            if (intersection.size() == 2) { // Paire trouvée
                // Supprimer ces symboles des autres cases de la colonne
                for (int i = 0; i < sudoku.getTaille(); i++) {
                    if (i != i1 && i != i2) {
                        for (char symbole : intersection) {
                            sudoku.getCase(i, colonne).getPossibilites().remove(symbole);
                            change = true;
                        }
                    }
                }
            }
        }
    }
    return change;
}

// Vérifier les paires et triples dans un bloc
private boolean verifierPairesEtTriplesDansBloc(int indexBloc) {
    boolean change = false;
    int blocSize = (int) Math.sqrt(sudoku.getTaille());
    int startRow = (indexBloc / blocSize) * blocSize;
    int startCol = (indexBloc % blocSize) * blocSize;

    // Trouver les paires et triples dans le bloc
    for (int i1 = startRow; i1 < startRow + blocSize; i1++) {
        for (int j1 = startCol; j1 < startCol + blocSize; j1++) {
            for (int i2 = i1; i2 < startRow + blocSize; i2++) {
                for (int j2 = (i1 == i2) ? j1 + 1 : startCol; j2 < startCol + blocSize; j2++) {
                    Set<Character> intersection = new HashSet<>(sudoku.getCase(i1, j1).getPossibilites());
                    intersection.retainAll(sudoku.getCase(i2, j2).getPossibilites());
                    if (intersection.size() == 2) { // Paire trouvée
                        // Supprimer ces symboles des autres cases du bloc
                        for (int i = startRow; i < startRow + blocSize; i++) {
                            for (int j = startCol; j < startCol + blocSize; j++) {
                                if ((i != i1 || j != j1) && (i != i2 || j != j2)) {
                                    for (char symbole : intersection) {
                                        sudoku.getCase(i, j).getPossibilites().remove(symbole);
                                        change = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    return change;
}

    // Appliquer les règles de déduction simples
    public void appliquerContraintes() {
        boolean change = true;
        while (change) {
            change = false;
    
            // Appliquer les contraintes de ligne, colonne et bloc
            for (int i = 0; i < sudoku.getTaille(); i++) {
                for (int j = 0; j < sudoku.getTaille(); j++) {
                    Case c = sudoku.getCase(i, j);
                    if (c.estVide() && c.getPossibilites().size() == 1) {
                        c.setValeur(c.getPossibilites().iterator().next());
                        change = true; // Si un changement a eu lieu
                    }
                }
            }
    
            // Appliquer la règle du symbole unique (un symbole qui n'est possible que dans une case)
            if (!appliquerRègleSymboleUnique()) {
                change = true;
            }
    
            // Vérifier les paires et triples
            if (!appliquerPairesEtTriples()) {
                change = true;
            }
        }
    }
    
    // Règle du symbole unique : Si un symbole n'est possible que dans une seule case d'une ligne/colonne/bloc
    private boolean appliquerRègleSymboleUnique() {
        boolean change = false;
        for (int i = 0; i < sudoku.getTaille(); i++) {
            for (int j = 0; j < sudoku.getTaille(); j++) {
                Case c = sudoku.getCase(i, j);
                Set<Character> possibilities = c.getPossibilites();
                for (char symbole : possibilities) {
                    if (isUniqueInLine(i, j, symbole) || isUniqueInColumn(i, j, symbole) || isUniqueInBloc(i, j, symbole)) {
                        c.setValeur(symbole);
                        change = true;
                    }
                }
            }
        }
        return change;
    }
    
    // Vérifie si le symbole est unique dans la ligne
    private boolean isUniqueInLine(int ligne, int colonne, char symbole) {
        for (int i = 0; i < sudoku.getTaille(); i++) {
            if (i != colonne && sudoku.getCase(ligne, i).getPossibilites().contains(symbole)) {
                return false;
            }
        }
        return true;
    }
    
    // Vérifie si le symbole est unique dans la colonne
    private boolean isUniqueInColumn(int ligne, int colonne, char symbole) {
        for (int i = 0; i < sudoku.getTaille(); i++) {
            if (i != ligne && sudoku.getCase(i, colonne).getPossibilites().contains(symbole)) {
                return false;
            }
        }
        return true;
    }
    
    // Vérifie si le symbole est unique dans le bloc
    private boolean isUniqueInBloc(int ligne, int colonne, char symbole) {
        int blocSize = (int) Math.sqrt(sudoku.getTaille());
        int startRow = (ligne / blocSize) * blocSize;
        int startCol = (colonne / blocSize) * blocSize;
    
        for (int i = startRow; i < startRow + blocSize; i++) {
            for (int j = startCol; j < startCol + blocSize; j++) {
                if (i != ligne && j != colonne && sudoku.getCase(i, j).getPossibilites().contains(symbole)) {
                    return false;
                }
            }
        }
        return true;
    }
    private boolean resoudreBacktracking() {
        for (int i = 0; i < sudoku.getTaille(); i++) {
            for (int j = 0; j < sudoku.getTaille(); j++) {
                Case c = sudoku.getCase(i, j);
                if (c.estVide()) {
                    for (char valeur : c.getPossibilites()) {
                        c.setValeur(valeur);
    
                        // Appliquer les règles de déduction après chaque choix
                        appliquerContraintes();
    
                        if (resoudreBacktracking()) {
                            return true;
                        }
    
                        c.setValeur('.'); // Annuler la valeur si ce n'est pas la bonne
                    }
                    return false; // Si aucune valeur n'est possible
                }
            }
        }
        return true; // Grille résolue
    }
    
}
    