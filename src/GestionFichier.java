package src;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionFichier {

    // Méthode pour sauvegarder la grille, logs, et l'algorithme utilisé dans un fichier
    public static void sauvegarderGrilleDansFichier(Sudoku sudoku, String methodeResolution, List<String> logs, String cheminFichier) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cheminFichier))) {
            // Sauvegarde de la grille Sudoku
            writer.write("Grille Sudoku :\n");
            for (int i = 0; i < sudoku.getTaille(); i++) {
                for (int j = 0; j < sudoku.getTaille(); j++) {
                    writer.write(sudoku.getCase(i, j) + " ");
                }
                writer.write("\n");
            }

            // Sauvegarde de la méthode de résolution
            writer.write("\nMéthode de résolution : " + methodeResolution + "\n");

            // Sauvegarde des logs de la résolution
            writer.write("\nLogs de la résolution :\n");
            for (String log : logs) {
                writer.write(log + "\n");
            }

            // Si vous souhaitez sauvegarder les états intermédiaires de la grille (en ajoutant des logs spécifiques pour cela),
            // nous pouvons le faire ici. Si les logs ne contiennent pas d'états intermédiaires, cette partie peut être ignorée.
            writer.write("\nÉtats intermédiaires de la grille :\n");
            for (String log : logs) {  // Assurez-vous que les logs contiennent les états
                writer.write(log + "\n");
            }

            System.out.println("Résolution sauvegardée avec succès dans le fichier : " + cheminFichier);
        } catch (IOException e) {
            System.out.println(" Erreur lors de la sauvegarde de la grille : " + e.getMessage());
        }
    }

    // Méthode pour charger une grille, l'algorithme et les logs depuis un fichier
    public static Sudoku chargerGrilleDepuisFichier(String cheminFichier) {
        Sudoku sudoku = null;
        String methodeResolution = "";
        List<String> logs = new ArrayList<>();
        int i = 0;  // Initialisation de l'index de ligne

        try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            boolean isGrille = false, isLogs = false, isMethode = false;

            while ((ligne = reader.readLine()) != null) {
                if (ligne.equals("Grille Sudoku :")) {
                    isGrille = true;
                } else if (ligne.equals("Méthode de résolution :")) {
                    isGrille = false;
                    isMethode = true;
                } else if (ligne.equals("Logs de la résolution :")) {
                    isMethode = false;
                    isLogs = true;
                } else if (ligne.equals("États intermédiaires de la grille :")) {
                    isLogs = false;
                } else if (isGrille) {
                    // Lire la grille ligne par ligne
                    String[] valeurs = ligne.trim().split(" ");
                    int taille = valeurs.length; // La taille de la grille est déterminée par le nombre de colonnes dans une ligne
                    if (sudoku == null) {
                        sudoku = new Sudoku(taille, genererSymboles(taille)); // Création d'un objet Sudoku avec cette taille
                    }
                    for (int j = 0; j < taille; j++) {
                        // Remplir la grille avec les valeurs lues
                        sudoku.setCase(i, j, valeurs[j].charAt(0)); // Assurez-vous de traiter les cases correctement
                    }
                    i++; // Incrémenter la ligne
                } else if (isMethode) {
                    methodeResolution = ligne.trim();
                } else if (isLogs) {
                    logs.add(ligne.trim());
                }
            }

            if (sudoku != null) {
                System.out.println("Grille et données chargées avec succès !");
            } else {
                System.out.println("Erreur lors du chargement de la grille.");
            }

        } catch (IOException e) {
            System.out.println("Erreur lors du chargement du fichier : " + e.getMessage());
        }

        return sudoku; // Retourner la grille chargée
    }

    private static char[] genererSymboles(int taille) {
        char[] symboles = new char[taille];
        for (int i = 0; i < taille; i++) {
            symboles[i] = (char) ('1' + i);  // Utilisation des symboles de 1 à taille
        }
        return symboles;
    }
}
