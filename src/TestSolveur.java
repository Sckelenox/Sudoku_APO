package src;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestSolveur {

    @Test
    public void testSudokuResolu4x4() {
        char[] symboles = {'1', '2', '3', '4'};
        Sudoku sudoku = new Sudoku(4, symboles);
        Solveur solveur = new Solveur(sudoku);

        System.out.println("Grille avant résolution (testSudokuResolu4x4) :");
        sudoku.afficher(); // Affiche avant la résolution

        boolean resolu = solveur.resoudre(true);

        System.out.println("Grille après résolution (testSudokuResolu4x4) :");
        sudoku.afficher(); // Affiche après la résolution

        assertTrue(resolu, "Le solveur doit résoudre un Sudoku 4x4 vide.");
        assertTrue(sudoku.estRempli(), "La grille doit être complètement remplie.");
    }

    @Test
    public void testSudokuInvalide() {
        char[] symboles = {'1', '2', '3', '4'};
        Sudoku sudoku = new Sudoku(4, symboles);
        sudoku.setCase(0, 0, '1');
        sudoku.setCase(0, 1, '1'); // Duplication interdite

        Solveur solveur = new Solveur(sudoku);

        System.out.println("Grille avant vérification (testSudokuInvalide) :");
        sudoku.afficher(); // Affiche avant la vérification

        assertFalse(sudoku.estValide(), "Le Sudoku ne doit pas être valide.");
    }

    @Test
    public void testSudokuPartiellementRempli() {
        char[] symboles = {'1', '2', '3', '4'};
        Sudoku sudoku = new Sudoku(4, symboles);

        sudoku.setCase(0, 0, '1');
        sudoku.setCase(1, 1, '2');
        sudoku.setCase(2, 2, '3');
        sudoku.setCase(3, 3, '4');

        Solveur solveur = new Solveur(sudoku);

        System.out.println("Grille avant résolution (testSudokuPartiellementRempli) :");
        sudoku.afficher(); // Affiche avant la résolution

        boolean resolu = solveur.resoudre(true);

        System.out.println("Grille après résolution (testSudokuPartiellementRempli) :");
        sudoku.afficher(); // Affiche après la résolution

        assertTrue(resolu, "Le solveur doit réussir à compléter la grille.");
        assertTrue(sudoku.estRempli(), "Toutes les cases doivent être remplies.");
    }

    @Test
    public void testBacktrackingNecessaire() {
        char[] symboles = {'1', '2', '3', '4'};
        Sudoku sudoku = new Sudoku(4, symboles);

        sudoku.setCase(0, 0, '1');
        sudoku.setCase(1, 1, '2');
        sudoku.setCase(2, 2, '3');
        sudoku.setCase(3, 3, '4');

        Solveur solveur = new Solveur(sudoku);

        System.out.println("Grille avant backtracking (testBacktrackingNecessaire) :");
        sudoku.afficher(); // Affiche avant le backtracking

        boolean resolu = solveur.resoudre(true);

        System.out.println("Grille après backtracking (testBacktrackingNecessaire) :");
        sudoku.afficher(); // Affiche après le backtracking

        assertTrue(resolu, "Le solveur doit utiliser le backtracking si nécessaire.");
    }

    @Test
    public void testApplicationContraintes() {
        char[] symboles = {'1', '2', '3', '4'};
        Sudoku sudoku = new Sudoku(4, symboles);

        sudoku.setCase(0, 0, '1');
        sudoku.setCase(1, 1, '2');
        sudoku.setCase(2, 2, '3');
        sudoku.setCase(3, 3, '4');

        Solveur solveur = new Solveur(sudoku);

        System.out.println("Grille avant application des contraintes (testApplicationContraintes) :");
        sudoku.afficher(); // Affiche avant l'application des contraintes

        solveur.appliquerContraintes();

        System.out.println("Grille après application des contraintes (testApplicationContraintes) :");
        sudoku.afficher(); // Affiche après l'application des contraintes

        assertFalse(sudoku.estRempli(), "La grille ne doit pas être remplie uniquement avec les contraintes.");
    }
}

