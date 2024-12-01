package vue_controleur;


import modele.Environnement;
import modele.Position;
import modele.Simulateur;
import modele.MyColor;

import java.awt.*;
import java.io.IOException;
import java.math.*;

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
    boolean select =false;
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

    public Position selectCase(int I, int J) {

        tab[I][J].setBackground(MyColor.BLUE);
        Position p =new Position(I, J);
        System.out.println(p);
        return p;
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
                    tab[i][j].setBackground(MyColor.DARK_BLUE);
                } else {
                    tab[i][j].setBackground(MyColor.LIGHT_BLUE);
                }
                int I = i;
                int J = j;
                tab[i][j].addMouseListener(new MouseAdapter() { //TODO: sortir ce code en une fonction à part pour alléger cette méthode
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (!select) {
                            dessin = true;
                            switchCase(I, J);
                        }else {
                            Position p1;
                            Position p2;
                            p1 = selectCase(I, J);
                            p2 = selectCase(I, J);// -------------------------Tout Faux, deux print au lieu d'un (logique)
                        }

                    }
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        dessin=false;
                    }

                    @Override
                    public void mouseEntered (MouseEvent e){
                        if  (dessin && !select) {
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
        JButton resetButton = new JButton("Reset");
        SwingStyle.applyButtonStyle(resetButton);
        resetButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                sm.reset();
            }
        });

        JButton BlankButton = new JButton("Blank");
        BlankButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                sm.blank();
            }
        });
        SwingStyle.applyButtonStyle(BlankButton);

        JButton btn3 = new JButton("SauveZone");
        setSauvegardeButton(btn3);

        SwingStyle.applyButtonStyle(btn3);

        menuPanel.add(resetButton);
        menuPanel.add(Box.createVerticalStrut(10)); // Espace
        menuPanel.add(BlankButton);
        menuPanel.add(Box.createVerticalStrut(10)); // Espace
        menuPanel.add(btn3);
        menuPanel.add(Box.createVerticalStrut(20)); // Espace supplémentaire

        // Slider pour gérer la vitesse
        JSlider speedSlider = new JSlider(0, 100, 50); //TODO: mettre des constantes
        speedSlider.setFocusable(false);
        speedSlider.setMajorTickSpacing(25);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        menuPanel.add(new JLabel("Vitesse:"));
        menuPanel.add(speedSlider);
        menuPanel.add(Box.createVerticalStrut(20));

        speedSlider.addChangeListener(e -> {
            float simulationSpeed = speedSlider.getValue(); // Mettre à jour la vitesse

            sm.setSleepTime(sm.speedCurve(simulationSpeed));
            System.out.println("Vitesse de simulation : " + sm.speedCurve(simulationSpeed));
        });


        // Zone d'import avec une liste scrollable
        JPanel importPanel = new JPanel();
        SwingStyle.applyPanelStyle(importPanel);

        importPanel.setLayout(new BorderLayout());
        importPanel.setBorder(BorderFactory.createTitledBorder("Importer"));

        JButton importButton = new JButton("Importer");
        setImportButton(importButton);
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
            select=!select;
            System.out.println("ici");
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

    private void setSauvegardeButton(JButton button){
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    select=!select;
                    sm.sauvegarderEcran();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    private void setImportButton(JButton button){
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    sm.chargerEcran();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
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
