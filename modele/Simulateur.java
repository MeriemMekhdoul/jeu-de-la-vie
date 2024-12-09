package modele;

import java.io.*;

public class Simulateur {
    public static int simulationSpeed = 550; // Valeur par défaut
    private Environnement env;
    private Ordonnanceur ord;

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
        File directory = new File(System.getProperty("user.home") + "\\JeuDeLaVie");//TODO: maybe changer le chemin ?

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
        File file= new File(System.getProperty("user.home") + "\\JeuDeLaVie\\screenSauvegarde.bin");//TODO: mettre en constante
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
}
