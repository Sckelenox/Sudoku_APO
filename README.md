# Projet Sudoku

## 1. Structure de l'archive

Les fichiers et dossiers du projet sont organisés comme suit :

- **`/src/main/java/projet_sudoku`** : Contient les classes Java principales.
- **`/Javadoc`** : Stocke la documentation générée.
- **`/Rapport/Diagrammes_UML`** : Contient les diagrammes UML du projet.
- **`/test`** : Contient les tests unitaires.

## 2. Méthodologie


###  Choix de l'architecture

Le projet suit une architecture modulaire avec les composants suivants :
- **Grille** : Représente le Sudoku et ses opérations associées.
- **Solveur** : Implémente les algorithmes de résolution.
- **Générateur** : Permet la création de nouvelles grilles.
- **Interface utilisateur** : Gestion de l'affichage et des interactions.
- **Gestion des fichiers** : Sauvegarde et chargement des grilles.


## 3. Implémentation

### 3.1 Algorithmes de résolution
Deux méthodes ont été utilisées pour résoudre les grilles de Sudoku :
- **Déduction logique** : Identification des valeurs uniques possibles dans la grille.
- **Backtracking** : Exploration systématique des solutions possibles pour remplir la grille.

### 3.2 Génération de grilles
L'algorithme de génération suit trois étapes :
1. **Création d'une grille complète** respectant les règles du Sudoku.
2. **Suppression progressive de valeurs** tout en garantissant l’unicité de la solution.
3. **Évaluation de la difficulté** en fonction du nombre de valeurs données.

## 4. Extensions optionnelles


### 4.1 Tests unitaires
Nous avons utilisé **JUnit 5** pour tester la fiabilité et la robustesse de nos classes.

### 4.2 Interface graphique
Une interface utilisateur a été développée avec **JavaFX** pour offrir une meilleure expérience utilisateur.

### 4.3 Sauvegarde et configuration
Les grilles peuvent être **sauvegardées et rechargées** au format **JSON** afin de conserver les parties en cours et faciliter l'échange des grilles.

---


