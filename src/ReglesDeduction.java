package src;

import java.util.Set;

public class ReglesDeduction {
    private boolean activerCaseUnique = true;
    private boolean activerBlocUnique = true;
    private boolean activerLigneColonneUnique = true;

    // Méthode pour activer/désactiver une règle
    public void setActiverCaseUnique(boolean activer) {
        activerCaseUnique = activer;
    }

    public void setActiverBlocUnique(boolean activer) {
        activerBlocUnique = activer;
    }

    public void setActiverLigneColonneUnique(boolean activer) {
        activerLigneColonneUnique = activer;
    }

    public void appliquerToutesLesRegles(Sudoku sudoku) {
        boolean modification;
        do {
            modification = false;
            if (activerCaseUnique) {
                modification |= appliquerCaseUnique(sudoku);
            }
            if (activerBlocUnique) {
                modification |= appliquerBlocUnique(sudoku);
            }
            if (activerLigneColonneUnique) {
                modification |= appliquerLigneColonneUnique(sudoku);
            }
        } while (modification);
    }

    private boolean appliquerCaseUnique(Sudoku sudoku) {
        boolean modif = false;
        int taille = sudoku.getTaille();
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (sudoku.getCase(i, j) == Sudoku.VIDE) {
                    Set<Character> possibilites = sudoku.getPossibilites(i, j);
                    if (possibilites.size() == 1) {
                        sudoku.setCase(i, j, possibilites.iterator().next());
                        modif = true;
                    }
                }
            }
        }
        return modif;
    }

    private boolean appliquerBlocUnique(Sudoku sudoku) {
        boolean modif = false;
        int taille = sudoku.getTaille();
        int tailleBloc = (int) Math.sqrt(taille);
        for (int blocLigne = 0; blocLigne < taille; blocLigne += tailleBloc) {
            for (int blocCol = 0; blocCol < taille; blocCol += tailleBloc) {
                modif |= appliquerRegleBloc(sudoku, blocLigne, blocCol, tailleBloc);
            }
        }
        return modif;
    }

    private boolean appliquerRegleBloc(Sudoku sudoku, int ligneDebut, int colonneDebut, int tailleBloc) {
        boolean modif = false;
        for (int i = ligneDebut; i < ligneDebut + tailleBloc; i++) {
            for (int j = colonneDebut; j < colonneDebut + tailleBloc; j++) {
                if (sudoku.getCase(i, j) == Sudoku.VIDE) {
                    Set<Character> possibilites = sudoku.getPossibilites(i, j);
                    if (possibilites.size() == 1) {
                        sudoku.setCase(i, j, possibilites.iterator().next());
                        modif = true;
                    }
                }
            }
        }
        return modif;
    }

    private boolean appliquerLigneColonneUnique(Sudoku sudoku) {
        boolean modif = false;
        int taille = sudoku.getTaille();
        for (int i = 0; i < taille; i++) {
            modif |= appliquerRegleLigne(sudoku, i);
            modif |= appliquerRegleColonne(sudoku, i);
        }
        return modif;
    }

    private boolean appliquerRegleLigne(Sudoku sudoku, int ligne) {
        boolean modif = false;
        int taille = sudoku.getTaille();
        for (int j = 0; j < taille; j++) {
            if (sudoku.getCase(ligne, j) == Sudoku.VIDE) {
                Set<Character> possibilites = sudoku.getPossibilites(ligne, j);
                if (possibilites.size() == 1) {
                    sudoku.setCase(ligne, j, possibilites.iterator().next());
                    modif = true;
                }
            }
        }
        return modif;
    }

    private boolean appliquerRegleColonne(Sudoku sudoku, int colonne) {
        boolean modif = false;
        int taille = sudoku.getTaille();
        for (int i = 0; i < taille; i++) {
            if (sudoku.getCase(i, colonne) == Sudoku.VIDE) {
                Set<Character> possibilites = sudoku.getPossibilites(i, colonne);
                if (possibilites.size() == 1) {
                    sudoku.setCase(i, colonne, possibilites.iterator().next());
                    modif = true;
                }
            }
        }
        return modif;
    }
}
