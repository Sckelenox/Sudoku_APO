package src;

import java.util.List;
import java.util.Scanner;

public class Interface {
    private Scanner scanner = new Scanner(System.in);
    private Sudoku sudoku; // La grille stockée en mémoire
    private Solveur solveur; // Le solveur pour résoudre le sudoku
    private Generateur generateur;

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
            System.out.println("2. Charger une grille depuis un fichier");
            System.out.println("3. Choisir la méthode de résolution");
            System.out.println("4. Afficher la grille");
            System.out.println("5. Résoudre le Sudoku");
            System.out.println("6. Sauvegarder la résolution dans un fichier");
            System.out.println("7. Générer une grille à résoudre");
            System.out.println("8. Quitter le mode texte");
            System.out.print("Choisissez une option : ");

            int choix = scanner.nextInt();
            switch (choix) {
                case 1 -> entrerGrille();
                case 2 -> chargerDepuisFichier();
                case 3 -> choisirMethodeResolution();
                case 4 -> afficherGrille();
                case 5 -> resoudreSudoku();
                case 6 -> sauvegarderResolution();
                case 7 -> genererGrille();
                case 8 -> continuer = false;
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

        System.out.println(" Grille enregistrée avec succès !");
        solveur = new Solveur(sudoku); // Instantiation du solveur avec la grille
    }

    public void chargerDepuisFichier() {
        System.out.println("Entrez le chemin du fichier de la grille : ");
        String chemin = scanner.next();
        sudoku = GestionFichier.chargerGrilleDepuisFichier(chemin);
        if (sudoku != null) {
            solveur = new Solveur(sudoku);
            System.out.println("Grille chargée avec succès !");
        } else {
            System.out.println(" Erreur lors du chargement de la grille.");
        }
    }

    public void afficherGrille() {
        if (sudoku == null) {
            System.out.println(" Aucune grille disponible !");
            return;
        }
        sudoku.afficher();
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

    public void resoudreSudoku() {
        if (sudoku == null) {
            System.out.println(" Aucune grille disponible !");
            return;
        }

        System.out.println("\n Résolution en cours...");
        solveur.resoudre(true, true); // Résolution avec affichage et règles uniquement

        if (sudoku.estRempli()) {
            System.out.println("\n Sudoku résolu !");
        } else {
            System.out.println("\n Sudoku non résolu uniquement par déduction.");
        }
        sudoku.afficher();
    }

    public void resoudreAvecRegles() {
        if (sudoku == null) {
            System.out.println(" Aucune grille disponible !");
            return;
        }

        solveur.resoudre(true, true); // Résolution par règles uniquement
        sudoku.afficher();
    }

    public void resoudreAvecBacktracking() {
        if (sudoku == null) {
            System.out.println(" Aucune grille disponible !");
            return;
        }

        solveur.resoudre(true, false); // Résolution par backtracking uniquement
        sudoku.afficher();
    }

    public void resoudreMixte() {
        if (sudoku == null) {
            System.out.println(" Aucune grille disponible !");
            return;
        }

        solveur.resoudre(true, true); // Règles en premier
        if (!sudoku.estRempli()) {
            solveur.resoudre(true, false); // Puis backtracking si besoin
        }
        sudoku.afficher();
    }

    public void genererGrille() {
        System.out.println("Sélectionnez le niveau de difficulté (1 = Facile, 2 = Moyen, 3 = Difficile) :");
        int niveau = scanner.nextInt();

        Generateur generateur = new Generateur(sudoku.getTaille(), genererSymboles(sudoku.getTaille()));

        Sudoku grilleComplete = generateur.genererGrilleComplete();
        Sudoku grilleJouable = generateur.genererGrilleJouable(grilleComplete, niveau);

        sudoku = grilleJouable;

        System.out.println(" Grille générée avec succès !");
        afficherGrille();
    }

    public void sauvegarderResolution() {
        if (sudoku == null) {
            System.out.println(" Aucune grille disponible à sauvegarder !");
            return;
        }

        System.out.println("Entrez le nom du fichier pour sauvegarder la résolution : ");
        String fichier = scanner.next();

        // Sauvegarder la grille, la méthode de résolution et les logs dans le fichier
        GestionFichier.sauvegarderGrilleDansFichier(sudoku, "REGLES+BACKTRACKING", solveur.getLogs(), fichier);
    }




    private char[] genererSymboles(int taille) {
        char[] symboles = new char[taille];
        for (int i = 0; i < taille; i++) {
            symboles[i] = (char) ('1' + i);  // Utiliser les symboles de 1 à taille
        }
        return symboles;
    }
}
