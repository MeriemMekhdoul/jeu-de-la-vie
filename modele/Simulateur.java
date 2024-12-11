package modele;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Simulateur {
    public static int simulationSpeed = 550; // Valeur par défaut
    private Environnement env;
    private Ordonnanceur ord;
    public final static String DIRECTORY_PATH = System.getProperty("user.home") + "\\JeuDeLaVie"; //TODO: maybe changer le chemin ?

    public Simulateur(Environnement _env, Ordonnanceur _ord){
        env = _env;
        ord = _ord;
    }

    public void modeManuel() {
        if (ord.getModeAuto()) {
            ord.setModeAuto(false); // Désactive le mode automatique
        }
        ord.runManuel(); // Exécute une tâche manuelle
    }

    public int speedCurve(float x){
        return (int) ( Math.pow( 101-x, 2 ) / 5 + 50 );
    }

    public void switchMode() {
        if (ord.getModeAuto()) {
            ord.setModeAuto(false); // Passe du mode automatique au manuel
        } else {
            ord.setModeAuto(true);
        }
    }
    public void reset() {
        env.rndState();
        modeManuel();
    }
    public void blank() {
        env.blankState();
        modeManuel();
    }

    public void previous() {
        env.prevState();
    }


    public void stop() {
        System.out.println("stopping the execution");
        ord.setExit(true); // Arrête l'exécution
    }


    public long getSleepTime() {//TODO: useless
        return ord.getSleepTime();
    }

    public void setSleepTime(long sleepTime) {
        ord.setSleepTime(sleepTime);
    }

    public void sauvegarderEcran(Position p1, Position p2) throws IOException {
        //sauvegarder l'etat actuel de la grille
        // Chemin du répertoire
        File directory = new File(DIRECTORY_PATH);

        // Vérifie si le répertoire existe, sinon le crée
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Répertoire créé : " + directory.getAbsolutePath());
            } else {
                throw new IOException("Impossible de créer le répertoire : " + directory.getAbsolutePath());
            }
        }

        // Création du fichier
        File file = new File(directory,"screenSauvegarde.bin");
        if (file.createNewFile()) {
            System.out.println("Fichier créé : " + file.getAbsolutePath());
        } else {
            System.out.println("Le fichier existe déjà : " + file.getAbsolutePath());
        }

        if (file.canWrite()){
            try {
                ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(file));
                Environnement sEnv = env.getSousEnv(p1,p2);
                o.writeObject(sEnv);
                o.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Environnement chargerEcran() throws IOException {
        File file= new File(DIRECTORY_PATH + "\\screenSauvegarde.bin");
        Environnement en = null;

        if(file.exists()) {
            if (file.canRead()) {
                try {
                    ObjectInputStream o = new ObjectInputStream(new FileInputStream(file));
                    Object e = o.readObject();
                    if (e instanceof Environnement) {
                        en = (Environnement) e;
                        // Affichage du sous-environnement
                        System.out.println("Sous-environnement extrait:");
                        for (int i = 0; i < en.getSizeX(); i++) {
                            for (int j = 0; j < en.getSizeY(); j++) {
                                System.out.print(en.getCase(i, j).getState() ? "1 " : "0 ");
                            }
                            System.out.println();
                        }
                    }
                    o.close();
                } catch (ClassNotFoundException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else throw new IOException("Le fichier n'existe pas : " + file.getAbsolutePath());

        return en;
    }

    public void setSousEnv(Environnement sEnv, Position p){
        env.setSousEnv(sEnv, p);
    }

    //TODO: enlever les messages de debug
    public List<Environnement> chargerEnvironnements() {
        List<Environnement> environnements = new ArrayList<>();

        // Définir le chemin du répertoire des motifs
        String homePath = DIRECTORY_PATH + "\\motifs"; // Supposons que 'motifs' est un dossier à créer
        File directory = new File(homePath);

        // Vérifier si le dossier existe
        System.out.println("Vérification du dossier: " + homePath);
        if (directory.exists() && directory.isDirectory()) {
            System.out.println("Le dossier existe et contient des fichiers.");

            // Parcourir les fichiers du dossier
            File[] files = directory.listFiles();
            if (files != null) {
                System.out.println("Nombre de fichiers trouvés: " + files.length);
                for (File file : files) {
                    if (file.isFile()) {
                        // Charger chaque environnement depuis le fichier
                        System.out.println("Chargement du fichier: " + file.getName());
                        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                            Environnement env = (Environnement) ois.readObject();
                            System.out.println("Environnement chargé depuis le fichier: " + file.getName());

                            // Affichage du sous-environnement pour debug
                            System.out.println("Sous-environnement extrait de " + file.getName() + ":");
                            for (int i = 0; i < env.getSizeX(); i++) {
                                for (int j = 0; j < env.getSizeY(); j++) {
                                    System.out.print(env.getCase(i, j).getState() ? "1 " : "0 ");
                                }
                                System.out.println();
                            }

                            // Ajouter l'environnement à la liste
                            environnements.add(env);
                        } catch (IOException e) {
                            System.err.println("Erreur lors de la lecture du fichier " + file.getName());
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            System.err.println("Classe introuvable lors du chargement de " + file.getName());
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Le fichier n'est pas valide : " + file.getName());
                    }
                }
            } else {
                System.out.println("Aucun fichier trouvé dans le dossier.");
            }
        } else {
            // Le dossier n'existe pas ou n'est pas un répertoire valide
            System.out.println("Le dossier n'existe pas ou n'est pas un répertoire valide. Je vais créer le dossier.");
            boolean created = directory.mkdirs(); // Crée le dossier "motifs"
            if (created) {
                System.out.println("Le dossier 'motifs' a été créé avec succès.");
            } else {
                System.out.println("Erreur lors de la création du dossier 'motifs'.");
            }
        }

        // Afficher la liste des environnements chargés
        System.out.println("Environnements chargés: " + environnements.size());

        return environnements;
    }
}
