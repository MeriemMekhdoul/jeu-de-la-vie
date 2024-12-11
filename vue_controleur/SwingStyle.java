package vue_controleur;

import javax.swing.*;
import java.awt.*;

public class SwingStyle {
    public static void applyButtonStyle(JButton button) {
        button.setFocusable(false);
        button.setBackground(new Color(173, 216, 230)); // Couleur normale
        button.setFont(new Font("Poppins", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        // Ajouter un effet hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(135, 206, 250)); // Couleur au survol
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(173, 216, 230)); // Couleur normale
            }
        });
    }

    public static void applyPanelStyle(JPanel panel) {
        panel.setBackground(new Color(245, 245, 245));
    }

    public static void applyLabelStyle(JLabel label) {
        label.setFont(new Font("Poppins", Font.PLAIN, 12));
    }
}

