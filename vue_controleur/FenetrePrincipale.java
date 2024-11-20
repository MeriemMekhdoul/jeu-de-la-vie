package vue_controleur;


import modele.Environnement;
import modele.Simulateur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

import javax.swing.border.Border;

public class FenetrePrincipale extends JFrame implements Observer {

    private JPanel[][] tab;
    private Simulateur sm;
    Environnement env;
    public FenetrePrincipale(Environnement _env, Simulateur _sm) {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        env = _env;
        sm = _sm;
        build();
        requestFocusOnWindow();
        addKeyBoardListener();
    }

    public void addKeyBoardListener() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_SPACE: {
                        System.out.println("clicked on SPACE");
                        sm.modeManuel();
                        break;
                    }
                    case KeyEvent.VK_X: {
                        System.out.println("clicked on X");
                        sm.switchMode();
                        break;
                    }
                    case KeyEvent.VK_S: {
                        System.out.println("clicked on S");
                        sm.stop();
                        break;
                    }
                }
            }
        });
    }

    public void build() {

        setTitle("Jeu de la Vie");
        setSize(600, 500);

        // Panneau principal
        JPanel pan = new JPanel(new BorderLayout());


        // Panneau central
        JComponent pan1 = new JPanel (new GridLayout(env.getSizeX(),env.getSizeY()));
        tab = new JPanel[env.getSizeX()][env.getSizeY()];


        Border blackline = BorderFactory.createLineBorder(Color.black,1);
        pan1.setBorder(blackline);
        for(int i = 0; i<env.getSizeX();i++){
            for (int j = 0; j < env.getSizeY(); j++) {
                tab[i][j] = new JPanel();
                if (env.getState(i, j)) {
                    tab[i][j].setBackground(Color.BLACK);
                } else {
                    tab[i][j].setBackground(Color.WHITE);
                }
                pan1.add(tab[i][j]);
            }
        }

        // Panneau pour les boutons
        JPanel pan2 = new JPanel(new FlowLayout());
        pan2.add(new JButton("b1"));
        pan2.add(new JTextField("jt1"));

        pan.add(pan1, BorderLayout.CENTER);
        pan.add(pan2, BorderLayout.EAST);

        setContentPane(pan);

        // Ajout Menu
        JMenuBar jm = new JMenuBar();
        JMenu m = new JMenu("Fichier");
        JMenuItem mi = new JMenuItem("Charger");
        m.add(mi);
        jm.add(m);
        setJMenuBar(jm);
    }



    @Override
    public void update(Observable o, Object arg) {
        // rafraîchissement de la vue
        for(int i = 0; i<env.getSizeX();i++){
            for (int j = 0; j < env.getSizeY(); j++) {
                if (env.getState(i, j)) {
                    tab[i][j].setBackground(Color.BLACK);
                } else {
                    tab[i][j].setBackground(Color.WHITE);
                }
            }
        }
    }


    public void requestFocusOnWindow() {
        this.setFocusable(true);       // Rend la fenêtre éligible au focus
        this.requestFocusInWindow();  // Demande le focus
    }
}
