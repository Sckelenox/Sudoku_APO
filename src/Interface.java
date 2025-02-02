package src;

import java.util.Scanner;

public class Interface {
    private Scanner scanner = new Scanner(System.in);
    private Sudoku sudoku; // La grille stockée en mémoire
    private Solveur solveur; // Le solveur pour résoudre le sudoku

    public Interface() {
        this.sudoku = null;
        this.solveur = null;
    }

    public Sudoku lancer() {
        afficherMenu();
        return sudoku; // On retourne la grille pour la récupérer en mode graphique
    }

    public void afficherMenu() {
        boolean continuer = true;

        while (continuer) {
            System.out.println("\n===== MENU SUDOKU (Mode Texte) =====");
            System.out.println("1. Entrer une grille à résoudre");
            System.out.println("2. Choisir la méthode de résolution");
            System.out.println("3. Afficher la grille");
            System.out.println("4. Résoudre le Sudoku");
            System.out.println("5. Générer une grille à résoudre");
            System.out.println("6. Quitter le mode texte");
            System.out.print("Choisissez une option : ");

            int choix = scanner.nextInt();
            switch (choix) {
                case 1 -> entrerGrille();
                case 2 -> choisirMethodeResolution();
                case 3 -> afficherGrille();
                case 4 -> resoudreSudoku();
                case 5 -> genererGrille();
                case 6 -> continuer = false;
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
                if (valeur == '0') {
                    sudoku.setCase(i, j, Sudoku.VIDE); // Cases vides
                } else {
                    sudoku.setCase(i, j, valeur);
                }
            }
        }

        System.out.println("✅ Grille enregistrée avec succès !");
        solveur = new Solveur(sudoku); // Instantiation du solveur avec la grille
    }

    public void choisirMethodeResolution() {
        System.out.println("\n===== Choisir la méthode de résolution =====");
        System.out.println("1. Résolution par règles uniquement");
        System.out.println("2. Résolution par retour sur trace (backtracking)");
        System.out.println("3. Résolution mixte (règles + retour sur trace)");
        System.out.print("Choisissez une méthode de résolution : ");

        int choix = scanner.nextInt();
        switch (choix) {
            case 1 -> resoudreAvecRegles();
            case 2 -> resoudreAvecBacktracking();
            case 3 -> resoudreMixte();
            default -> System.out.println("Option invalide.");
        }
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
        solveur.resoudre(true, true); // Résolution avec affichage et règles uniquement

        if (sudoku.estRempli()) {
            System.out.println("\n✅ Sudoku résolu !");
        } else {
            System.out.println("\n❌ Sudoku non résolu uniquement par déduction.");
        }
        sudoku.afficher();
    }

    public void resoudreAvecRegles() {
        if (sudoku == null) {
            System.out.println("❌ Aucune grille disponible !");
            return;
        }

        System.out.println("\n=== Résolution par règles uniquement ===");
        solveur.resoudre(true, true); // Résolution par règles uniquement

        if (sudoku.estRempli()) {
            System.out.println("\n✅ Sudoku résolu avec les règles !");
        } else {
            System.out.println("\n❌ Sudoku non résolu uniquement par déduction.");
        }
        sudoku.afficher();
    }

    public void resoudreAvecBacktracking() {
        if (sudoku == null) {
            System.out.println("❌ Aucune grille disponible !");
            return;
        }

        System.out.println("\n=== Résolution par backtracking ===");
        solveur.resoudre(true, false); // Résolution par backtracking uniquement

        if (sudoku.estRempli()) {
            System.out.println("\n✅ Sudoku résolu avec backtracking !");
        } else {
            System.out.println("\n❌ Sudoku non résolu par backtracking.");
        }
        sudoku.afficher();
    }

    public void resoudreMixte() {
        if (sudoku == null) {
            System.out.println("❌ Aucune grille disponible !");
            return;
        }

        System.out.println("\n=== Résolution mixte (règles + backtracking) ===");
        solveur.resoudre(true, true); // Résolution par règles d'abord
        if (!sudoku.estRempli()) {
            solveur.resoudre(true, false); // Backtracking si la grille n'est pas résolue
        }
    }

    public void genererGrille() {
        System.out.println("Sélectionnez le niveau de difficulté (1 = Facile, 2 = Moyen, 3 = Difficile) :");
        int niveau = scanner.nextInt();

        // Générer une grille complète (résolue)
        Sudoku nouvelleGrille = genererGrilleComplete(sudoku.getTaille());

        // Rendre la grille résoluble selon le niveau de difficulté
        retirerChiffres(nouvelleGrille, niveau);

        sudoku = nouvelleGrille;  // Remplacer la grille actuelle par la nouvelle générée
        System.out.println("✅ Grille générée avec succès !");
        afficherGrille();  // Afficher la grille générée
    }

    private Sudoku genererGrilleComplete(int taille) {
        Sudoku sudokuComplete = new Sudoku(taille, genererSymboles(taille));
        Solveur solveur = new Solveur(sudokuComplete);
        solveur.resoudre(false, true);  // Résoudre la grille en utilisant les règles uniquement
        return sudokuComplete;
    }

    private void retirerChiffres(Sudoku sudoku, int niveau) {
        int casesARetirer = 0;

        switch (niveau) {
            case 1 -> casesARetirer = (int) (sudoku.getTaille() * sudoku.getTaille() * 0.3);  // Facile - 30% des cases
            case 2 -> casesARetirer = (int) (sudoku.getTaille() * sudoku.getTaille() * 0.5);  // Moyen - 50% des cases
            case 3 -> casesARetirer = (int) (sudoku.getTaille() * sudoku.getTaille() * 0.7);  // Difficile - 70% des cases
            default -> System.out.println("Niveau invalide. Utilisation du niveau moyen.");
        }

        int casesRetirees = 0;
        while (casesRetirees < casesARetirer) {
            int ligne = (int) (Math.random() * sudoku.getTaille());
            int colonne = (int) (Math.random() * sudoku.getTaille());

            if (sudoku.getCase(ligne, colonne) != Sudoku.VIDE) {
                sudoku.setCase(ligne, colonne, Sudoku.VIDE);  // Retirer le chiffre
                casesRetirees++;
            }
        }
    }

    private char[] genererSymboles(int taille) {
        char[] symboles = new char[taille];
        for (int i = 0; i < taille; i++) {
            symboles[i] = (char) ('1' + i);  // Utiliser les symboles de 1 à taille
        }
        return symboles;
    }
}
