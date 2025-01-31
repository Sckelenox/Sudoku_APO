package src;
public class TestSolveur {

    public static void main(String[] args) {
        // Définition des symboles
        char[] symboles = {'1', '2', '3', '4'}; // Sudoku 4x4 avec chiffres 1 à 4
        
        // Création d'un Sudoku de taille 4x4
        Sudoku sudoku = new Sudoku(4, symboles);
        
        // Remplir partiellement la grille pour créer un puzzle à résoudre
        sudoku.getCase(0, 0).setValeur('1'); // Case (0, 0) = 1
        sudoku.getCase(0, 1).setValeur('2'); // Case (0, 1) = 2
        sudoku.getCase(1, 0).setValeur('3'); // Case (1, 0) = 3
        sudoku.getCase(1, 1).setValeur('4'); // Case (1, 1) = 4
        
        // Affichage avant résolution
        System.out.println("Grille avant résolution :");
        sudoku.afficher();
        
        // Utilisation du solveur pour résoudre le Sudoku
        Solveur solveur = new Solveur(sudoku);
        boolean resolu = solveur.resoudre();
        
        // Affichage après résolution
        if (resolu) {
            System.out.println("\nGrille résolue :");
            sudoku.afficher();
        } else {
            System.out.println("\nImpossible de résoudre cette grille.");
        }
    }
}