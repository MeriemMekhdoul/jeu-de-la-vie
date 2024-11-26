package modele;

public class Simulateur {
    public static int simulationSpeed = 100; // Valeur par défaut
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
}
