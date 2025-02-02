package src;



import java.util.*;

public class Generateur {
    private int taille;
    private char[] symboles;
    private Random random = new Random();

    public Generateur(int taille, char[] symboles) {
        this.taille = taille;
        this.symboles = symboles;
    }

    // Génère une grille complète en remplissant avec backtracking + aléatoire
    public Sudoku genererGrilleComplete() {
        Sudoku sudoku = new Sudoku(taille, symboles);
        remplirGrille(sudoku, 0, 0);
        return sudoku;
    }

    private boolean remplirGrille(Sudoku sudoku, int ligne, int colonne) {
        if (ligne == taille) return true;
        if (colonne == taille) return remplirGrille(sudoku, ligne + 1, 0);

        List<Character> valeurs = new ArrayList<>(Arrays.asList(toCharacterArray(symboles)));
        Collections.shuffle(valeurs);

        for (char valeur : valeurs) {
            if (estPlacementValide(sudoku, ligne, colonne, valeur)) {
                sudoku.setCase(ligne, colonne, valeur);
                if (remplirGrille(sudoku, ligne, colonne + 1)) return true;
                sudoku.setCase(ligne, colonne, Sudoku.VIDE);
            }
        }
        return false;
    }

    private boolean estPlacementValide(Sudoku sudoku, int ligne, int colonne, char valeur) {
        return !sudoku.estDansLigne(ligne, valeur) &&
                !sudoku.estDansColonne(colonne, valeur) &&
                !sudoku.estDansBloc(ligne, colonne, valeur);
    }

    // Génère une ou plusieurs grilles jouables à partir d'une grille complète
    public List<Sudoku> genererGrillesJouables(int nombreGrilles, int difficulte) {
        Sudoku grilleComplete = genererGrilleComplete();
        List<Sudoku> grillesJouables = new ArrayList<>();

        for (int i = 0; i < nombreGrilles; i++) {
            Sudoku grilleJouable = genererGrilleJouable(grilleComplete, difficulte);
            grillesJouables.add(grilleJouable);
        }
        return grillesJouables;
    }

    private Sudoku genererGrilleJouable(Sudoku grilleComplete, int difficulte) {
        Sudoku sudoku = new Sudoku(taille, symboles);
        copierGrille(grilleComplete, sudoku);
        int casesASupprimer = difficulte * (taille * taille) / 10;

        List<int[]> positions = new ArrayList<>();
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                positions.add(new int[]{i, j});
            }
        }
        Collections.shuffle(positions);

        for (int i = 0; i < casesASupprimer; i++) {
            int[] pos = positions.get(i);
            char temp = sudoku.getCase(pos[0], pos[1]);
            sudoku.setCase(pos[0], pos[1], Sudoku.VIDE);

            if (!aSolutionUnique(sudoku)) {
                sudoku.setCase(pos[0], pos[1], temp);
            }
        }

        return sudoku;
    }

    private boolean aSolutionUnique(Sudoku sudoku) {
        Solveur solveur = new Solveur(sudoku);
        return solveur.compterSolutions() == 1;
    }

    private void copierGrille(Sudoku source, Sudoku destination) {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                destination.setCase(i, j, source.getCase(i, j));
            }
        }
    }

    private Character[] toCharacterArray(char[] array) {
        Character[] charArray = new Character[array.length];
        for (int i = 0; i < array.length; i++) {
            charArray[i] = array[i];
        }
        return charArray;
    }
}
