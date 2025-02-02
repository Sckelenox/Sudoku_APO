package src;

import src.SudokuController;
import src.Sudoku;
import src.view.SudokuView;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuer = true;

        while (continuer) {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Mode Texte");
            System.out.println("2. Mode Graphique");
            System.out.println("3. Quitter");
            System.out.print("Choisissez une option : ");

            int choix = scanner.nextInt();

            switch (choix) {
                case 1 -> {
                    Interface interfaceTexte = new Interface();
                    interfaceTexte.lancer(); // Mode texte
                }
                case 2 -> {
                    lancerModeGraphique(); // Mode graphique (sans grille préalable)
                    continuer = false;
                }
                case 3 -> continuer = false;
                default -> System.out.println("Option invalide.");
            }
        }

        System.out.println("👋 Programme terminé !");
    }

    private static void lancerModeGraphique() {
        int taille = 9; // Par défaut, on crée une grille 9x9
        char[] symboles = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};

        Sudoku sudoku = new Sudoku(taille, symboles); // Grille vide
        SudokuView view = new SudokuView(taille);
        new SudokuController(sudoku, view);
    }
}
