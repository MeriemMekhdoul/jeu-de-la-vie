package modele;

import java.awt.*;
import java.util.Scanner;

import static java.lang.Thread.*;

public class Ordonnanceur extends Thread {

    private long sleepTime;
    private Runnable runnable;
    private boolean exit = false;
    private boolean modeAuto = false;

    public Ordonnanceur(long _sleepTime, Runnable _runnable) {
        sleepTime = _sleepTime;
        runnable = _runnable;
    }

    public boolean getModeAuto(){
        return modeAuto;
    }
    public void setExit(boolean etat){
        exit = etat;
    }
    @Override
    public void run() {
        while (!exit) {
            if (modeAuto) {
                // Exécution en mode automatique
                runAlone();
            } else {
                // Mode manuel attend les entrées via FenetrePrincipale
                try {
                    Thread.sleep(100); // Petite pause pour éviter la surcharge CPU
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        System.out.println("Exécution terminée.");
        //TODO: exit le programme ? car maintenant on peut toujours relancer avec ESPACE..
    }

    public void runManuel() {
        modeAuto = false;
        runnable.run(); // Exécute une tâche unique
    }

    public void runAlone() {
        runnable.run();
        try {
            Thread.sleep(sleepTime); // Pause entre chaque exécution
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    public void setModeAuto(boolean etat) {
        modeAuto = etat;
    }
}
