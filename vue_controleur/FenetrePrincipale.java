package vue_controleur;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import modele.Environnement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

import javax.swing.border.Border;


/**
 *
 * @author frederic
 */
public class FenetrePrincipale extends JFrame implements Observer {

    private JPanel[][] tab;
    Environnement env;
    public FenetrePrincipale(Environnement _env) {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        env = _env;
        build();
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
        // raffraîchissement de la vue
        for(int i = 0; i<env.getSizeX();i++){
            for (int j = 0; j < env.getSizeY(); j++) {
                if (env.getState(i, j)) {

                    tab[i][j].setBackground(Color.BLACK);
                } else {
                    tab[i][j].setBackground(Color.WHITE);
                }

                System.out.println("hello");
            }

        }


    }
}
