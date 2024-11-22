package vue_controleur;


import modele.Environnement;
import modele.Simulateur;
import modele.MyColor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

import javax.swing.border.Border;

public class FenetrePrincipale extends JFrame implements Observer {
    boolean dessin =false;
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
                        //System.out.println("clicked on SPACE");
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
                    case KeyEvent.VK_R: {
                        System.out.println("clicked on R");
                        sm.reset();
                        break;
                    }
                    case KeyEvent.VK_B: {
                        System.out.println("clicked on R");
                        sm.blank();
                        break;
                    }
                    case KeyEvent.VK_H: {
                        System.out.println("clicked on H");
                        System.out.println("Helper for the keybinds");
                        System.out.println("Spacebar: Switches to Manual and goes to the next state");
                        System.out.println("X: Switch between Manual and Auto mode");
                        System.out.println("S: Stops the execution of the program (Supposedly)");
                        System.out.println("R: Resets all cells to a random state");
                        System.out.println("B: Blanks all cells");
                        break;
                    }
                }
            }
        });
    }
    public void switchCase(int I, int J) {
        env.getCase(I, J).switchState();
        if (env.getState(I, J)) {
            tab[I][J].setBackground(MyColor.PURPLE);
        } else {
            tab[I][J].setBackground(MyColor.GOLD);
        }
    }

    public void build() {

        setTitle("Jeu de la Vie");
        setSize(600, 500);

        // Panneau principal
        JPanel pan = new JPanel(new BorderLayout());


        // Panneau central
        JComponent grid = new JPanel (new GridLayout(env.getSizeX(),env.getSizeY()));
        tab = new JPanel[env.getSizeX()][env.getSizeY()];


        Border blackline = BorderFactory.createLineBorder(Color.black,1);
        grid.setBorder(blackline);
        for(int i = 0; i<env.getSizeX();i++){
            for (int j = 0; j < env.getSizeY(); j++) {
                tab[i][j] = new JPanel();
                if (env.getState(i, j)) {
                    tab[i][j].setBackground(Color.DARK_GRAY);
                } else {
                    tab[i][j].setBackground(Color.LIGHT_GRAY);
                }
                int I = i;
                int J = j;
                tab[i][j].addMouseListener(new MouseAdapter() {
                    private Color background;

                    @Override
                    public void mousePressed(MouseEvent e) {
                        dessin=true;
                        switchCase(I, J);
                    }
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        dessin=false;
                    }

                    @Override
                    public void mouseEntered (MouseEvent e){
                        if  (dessin) {
                            switchCase(I, J);
                        }

                    }

                });

                grid.add(tab[i][j]);
            }
        }


        // Panneau pour les boutons
        JPanel pan2 = new JPanel(new FlowLayout());
        pan2.add(new JButton("b1"));
        pan2.add(new JTextField("jt1"));

        pan.add(grid, BorderLayout.CENTER);
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
                    tab[i][j].setBackground(MyColor.DARK_BLUE);
                } else {
                    tab[i][j].setBackground(MyColor.LIGHT_BLUE);
                }
            }
        }
    }


    public void requestFocusOnWindow() {
        this.setFocusable(true);       // Rend la fenêtre éligible au focus
        this.requestFocusInWindow();  // Demande le focus
    }
}
