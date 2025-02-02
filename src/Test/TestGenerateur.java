package src.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.Generateur;
import src.Sudoku;
import src.Solveur;

import java.util.ArrayList;
import java.util.List;

public class TestGenerateur {

    private Generateur generateur;
    private char[] symboles;

    @BeforeEach
    public void setup() {
        symboles = new char[] {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
        generateur = new Generateur(9, symboles);  // Grille de 9x9
    }

    // Test de la génération d'une grille complète
    @Test
    public void testGenererGrilleComplete() {
        Sudoku grilleComplete = generateur.genererGrilleComplete();
        assertNotNull(grilleComplete, "La grille complète ne doit pas être nulle.");

        // Vérifier qu'il n'y a pas de cases vides dans la grille complète
        for (int i = 0; i < grilleComplete.getTaille(); i++) {
            for (int j = 0; j < grilleComplete.getTaille(); j++) {
                assertNotEquals(Sudoku.VIDE, grilleComplete.getCase(i, j), "Il ne doit pas y avoir de cases vides.");
            }
        }
    }

    // Test de la génération d'une grille jouable
    @Test
    public void testGenererGrilleJouable() {
        Sudoku grilleComplete = generateur.genererGrilleComplete();
        Sudoku grilleJouable = generateur.genererGrilleJouable(grilleComplete, 5);  // Difficulté 5

        assertNotNull(grilleJouable, "La grille jouable ne doit pas être nulle.");

        boolean casesVides = false;
        for (int i = 0; i < grilleJouable.getTaille(); i++) {
            for (int j = 0; j < grilleJouable.getTaille(); j++) {
                if (grilleJouable.getCase(i, j) == Sudoku.VIDE) {
                    casesVides = true;
                    break;
                }
            }
        }
        assertTrue(casesVides, "La grille jouable doit contenir des cases vides.");

        Solveur solveur = new Solveur(grilleJouable);
        int solutions = solveur.compterSolutions();
        assertEquals(1, solutions, "La grille jouable doit avoir une seule solution.");
    }

    // Test pour générer plusieurs grilles jouables
    @Test
    public void testGenererGrillesJouables() {
        // Cette méthode "genererGrillesJouables" n'existe pas actuellement dans la classe Generateur.
        // Vous devrez soit la créer, soit ajuster ce test en fonction de l'API existante.

        // Par exemple, si vous voulez générer plusieurs grilles jouables, vous pouvez utiliser un code similaire à ce qui suit:
        List<Sudoku> grillesJouables = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Sudoku grilleComplete = generateur.genererGrilleComplete();
            Sudoku grilleJouable = generateur.genererGrilleJouable(grilleComplete, 3); // Difficulté 3
            grillesJouables.add(grilleJouable);
        }

        assertEquals(5, grillesJouables.size(), "Le nombre de grilles jouables générées ne correspond pas.");

        for (Sudoku grilleJouable : grillesJouables) {
            Solveur solveur = new Solveur(grilleJouable);
            int solutions = solveur.compterSolutions();
            assertEquals(1, solutions, "Chaque grille jouable doit avoir une seule solution.");
        }
    }



}

