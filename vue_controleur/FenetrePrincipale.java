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
        SwingStyle.applyPanelStyle(pan);


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
                tab[i][j].addMouseListener(new MouseAdapter() { //TODO: sortir ce code en une fonction à part pour alléger cette méthode
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

        pan.add(grid, BorderLayout.CENTER);

        // Barre verticale (VBox) à droite
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createTitledBorder("Menu"));

        // Ajout des boutons
        JButton btn1 = new JButton("Reset");
        SwingStyle.applyButtonStyle(btn1);
        btn1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                sm.reset();
            }
        });

        JButton btn2 = new JButton("Blank");
        btn2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                sm.blank();
            }
        });
        SwingStyle.applyButtonStyle(btn2);

        JButton btn3 = new JButton("Draw");
        btn1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO: faire une option draw
            }
        });

        SwingStyle.applyButtonStyle(btn3);

        menuPanel.add(btn1);
        menuPanel.add(Box.createVerticalStrut(10)); // Espace
        menuPanel.add(btn2);
        menuPanel.add(Box.createVerticalStrut(10)); // Espace
        menuPanel.add(btn3);
        menuPanel.add(Box.createVerticalStrut(20)); // Espace supplémentaire

        // Slider pour gérer la vitesse
        JSlider speedSlider = new JSlider(0, 2000, Simulateur.simulationSpeed); //TODO: mettre des constantes
        speedSlider.setFocusable(false);
        speedSlider.setMajorTickSpacing(500);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        menuPanel.add(new JLabel("Vitesse:"));
        menuPanel.add(speedSlider);
        menuPanel.add(Box.createVerticalStrut(20));

        speedSlider.addChangeListener(e -> {
            int simulationSpeed = speedSlider.getValue(); // Mettre à jour la vitesse
            sm.setSleepTime(simulationSpeed);
            System.out.println("Vitesse de simulation : " + simulationSpeed);
        });


        // Zone d'import avec une liste scrollable
        JPanel importPanel = new JPanel();
        SwingStyle.applyPanelStyle(importPanel);

        importPanel.setLayout(new BorderLayout());
        importPanel.setBorder(BorderFactory.createTitledBorder("Importer"));

        JButton importButton = new JButton("Importer");
        SwingStyle.applyButtonStyle(importButton);

        JList<String> itemList = new JList<>(new String[]{"Élément 1", "Élément 2", "Élément 3"});
        itemList.setFocusable(false);

        JScrollPane scrollPane = new JScrollPane(itemList);

        importPanel.add(importButton, BorderLayout.NORTH);
        importPanel.add(scrollPane, BorderLayout.CENTER);

        menuPanel.add(importPanel);

        // Ajouter le panneau du menu à droite
        pan.add(menuPanel, BorderLayout.EAST);

        // Barre de menu en haut
        JMenuBar jm = new JMenuBar();
        JMenu menu = new JMenu("Fichier");

        // Ajout des éléments "Sauvegarder" et "Charger"
        JMenuItem saveItem = new JMenuItem("Sauvegarder");
        JMenuItem loadItem = new JMenuItem("Charger");
        menu.add(saveItem);
        menu.add(loadItem);

        saveItem.addActionListener(e -> {
            System.out.println("Sauvegarde...");
            grid.requestFocusInWindow(); // Revenir sur la grille
        });

        loadItem.addActionListener(e -> {
            System.out.println("Chargement...");
            grid.requestFocusInWindow(); // Revenir sur la grille
        });

        jm.add(menu);
        setJMenuBar(jm);

        // Définir le contenu principal
        setContentPane(pan);
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
