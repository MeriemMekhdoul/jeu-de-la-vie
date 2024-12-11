# LIFAPOO Jeu de la Vie G06

## Etudiants

MEKHDOUL Meriem - p2310195
RODRIGUEZ Adam - p2109294

## Jeu de la vie

Cette application simule le **Jeu de la Vie** de Conway dans le cadre du cours de programmation orientée objet (LIFAPOO). 
Le projet propose une interface interactive permettant de placer des formes prédéfinies, de simuler l'évolution des cellules selon les règles du jeu, et d'expérimenter avec des configurations variées.


## Fonctionnalités

- Simulation des règles classiques du Jeu de la Vie :
    - **Survie** : une cellule reste vivante si elle a 2 ou 3 voisins vivants.
    - **Naissance** : une cellule morte devient vivante si elle a exactement 3 voisins vivants.
    - **Mort** : dans les autres cas, la cellule meurt ou reste morte.

- Interface graphique interactive :
    - Changer l'état des cellules manuellement.
    - Placement des motifs prédéfinis.
    - Dessin de motifs personnalisés.

- Contrôle de la simulation :
    - Lancer, mettre en pause et réinitialiser.
    - Contrôle de la vitesse d’évolution.


## Installation

### Prérequis
- Java Development Kit (JDK) version 8 ou supérieure.
- Un environnement de développement intégré (IDE) tel que IntelliJ IDEA, Eclipse ou VS Code (optionnel).

-> Clonez le dépôt du projet\
-> Compilez le projet\
-> Lancez l'application


## Organisation du code 

### Dossiers principaux

- **`Main.java`** : Point d'entrée principal du programme.
- **`modele/`** : Gestion des données et des règles du jeu.
- **`vue_controleur/`** : Gestion des interactions utilisateur.
    - `Simulateur.java` : Le controleur général.
    - `SwingStyle.java` : Classe de style pour les éléments graphique.
    - `FenetrePrincipale.java` : La vue principale (grille du jeu).
- **`data/`** : Dossier pour les motifs prédéfinis.
- **`ecran/`** : Dossier pour la sauvegarde des états de la grille.


### Autres fichiers

- **`README.md`** : Documentation du projet.
