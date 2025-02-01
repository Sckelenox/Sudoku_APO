package src;

import java.util.Scanner;

public class Interface {
    private Scanner scanner = new Scanner(System.in);

    public void afficherMenu() {
        boolean continuer = true;

        while (continuer) {
            System.out.println("\n===== MENU SUDOKU =====");
            System.out.println("1. Entrer une grille");
            System.out.println("2. Afficher la grille");
            System.out.println("3. Résoudre le Sudoku");
            System.out.println("4. Quitter");
            System.out.print("Choisissez une option : ");

            int choix = scanner.nextInt();
            switch (choix) {
                case 1 -> entrerGrille();
                case 2 -> afficherGrille();
                case 3 -> resoudreSudoku();
                case 4 -> continuer = false;
                default -> System.out.println("Option invalide.");
            }
        }
    }

    public void entrerGrille() {
        System.out.println("Entrez la taille de la grille (par exemple 4 pour une grille 4x4) :");
        int taille = scanner.nextInt();
        char[] symboles = new char[taille];

        // Entrer les symboles utilisés pour le sudoku
        for (int i = 0; i < taille; i++) {
            System.out.print("Entrez le symbole pour le numéro " + (i + 1) + " : ");
            symboles[i] = scanner.next().charAt(0);
        }

        Sudoku sudoku = new Sudoku(taille, symboles);

        System.out.println("Entrez la grille ligne par ligne (utilisez '0' pour les cases vides) :");
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                char valeur = scanner.next().charAt(0);
                sudoku.setCase(i, j, valeur);
            }
        }

        // Vous pouvez éventuellement valider la grille avant de l'accepter
        if (!sudoku.estValide()) {
            System.out.println("La grille entrée est invalide (doublons détectés).");
        } else {
            System.out.println("Grille entrée avec succès.");
        }
    }

    public void afficherGrille() {
        Sudoku sudoku = creerSudoku();
        sudoku.afficher();
    }

    public void resoudreSudoku() {
        Sudoku sudoku = creerSudoku(); // Remplacer par la grille entrée par l'utilisateur
        Solveur solveur = new Solveur(sudoku);

        System.out.println("\n1. Règles seulement\n2. Backtracking\n3. Mix des deux");
        System.out.print("Choisissez la méthode : ");
        int choix = scanner.nextInt();
        boolean backtracking = choix == 2 || choix == 3;

        // Affichage des actions pendant la résolution
        if (solveur.resoudre(backtracking)) {
            System.out.println("\n✅ Grille résolue :");
        } else {
            System.out.println("❌ Impossible de résoudre cette grille.");
        }
        sudoku.afficher();
    }

    public Sudoku creerSudoku() {
        // Cette méthode peut soit être utilisée pour la création d'une grille par défaut
        // Soit vous pouvez l'adapter pour retourner la grille saisie précédemment
        // Pour le moment, elle renvoie une grille vide 4x4 avec quelques valeurs par défaut.

        char[] symboles = {'1', '2', '3', '4'};
        Sudoku sudoku = new Sudoku(4, symboles);
        sudoku.setCase(0, 0, '1');
        sudoku.setCase(1, 1, '2');
        sudoku.setCase(2, 2, '3');
        sudoku.setCase(3, 3, '4');
        return sudoku;
    }
}
