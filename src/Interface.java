package src;

import src.Sudoku;

import java.util.Scanner;

public class Interface {
    private Scanner scanner = new Scanner(System.in);
    private Sudoku sudoku; // La grille stockée en mémoire

    public Interface() {
        this.sudoku = null;
    }

    public Sudoku lancer() {
        afficherMenu();
        return sudoku; // On retourne la grille pour la récupérer en mode graphique
    }

    public void afficherMenu() {
        boolean continuer = true;

        while (continuer) {
            System.out.println("\n===== MENU SUDOKU (Mode Texte) =====");
            System.out.println("1. Entrer une grille");
            System.out.println("2. Afficher la grille");
            System.out.println("3. Résoudre le Sudoku");
            System.out.println("4. Quitter le mode texte");
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
        System.out.println("Entrez la taille de la grille (ex: 4 pour une grille 4x4) :");
        int taille = scanner.nextInt();
        char[] symboles = new char[taille];

        for (int i = 0; i < taille; i++) {
            System.out.print("Entrez le symbole pour " + (i + 1) + " : ");
            symboles[i] = scanner.next().charAt(0);
        }

        sudoku = new Sudoku(taille, symboles);

        System.out.println("Entrez la grille ligne par ligne (utilisez '0' pour les cases vides) :");
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                char valeur = scanner.next().charAt(0);
                sudoku.setCase(i, j, valeur);
            }
        }

        System.out.println("✅ Grille enregistrée avec succès !");
    }

    public void afficherGrille() {
        if (sudoku == null) {
            System.out.println("❌ Aucune grille disponible !");
            return;
        }
        sudoku.afficher();
    }

    public void resoudreSudoku() {
        if (sudoku == null) {
            System.out.println("❌ Aucune grille disponible !");
            return;
        }

        System.out.println("\n✅ Résolution en cours...");
        ReglesDeduction regles = new ReglesDeduction();
        regles.appliquerToutesLesRegles(sudoku);

        if (sudoku.estRempli()) {
            System.out.println("\n✅ Sudoku résolu !");
        } else {
            System.out.println("\n❌ Sudoku non résolu uniquement par déduction.");
        }
        sudoku.afficher();
    }
}



