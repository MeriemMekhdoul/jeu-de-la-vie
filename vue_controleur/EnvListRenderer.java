package vue_controleur;

import modele.Environnement;
import modele.MyColor;

import javax.swing.*;
import java.awt.*;

public class EnvListRenderer extends JPanel implements ListCellRenderer<Environnement> {

    @Override
    public Component getListCellRendererComponent(
            JList<? extends Environnement> list,
            Environnement env,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {

        // Réinitialisation
        this.removeAll();
        this.setLayout(new GridLayout(env.getSizeX(), env.getSizeY()));

        // Configuration des cellules
        for (int i = 0; i < env.getSizeX(); i++) {
            for (int j = 0; j < env.getSizeY(); j++) {
                this.add(createCell(env.getState(i, j)));
            }
        }

        // Espacement entre les éléments (bordure vide)
        this.setBorder(BorderFactory.createEmptyBorder());

        // Gérer la sélection visuelle
        if (isSelected) {
            this.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        } else {
            this.setBorder(BorderFactory.createEmptyBorder());
        }

        return this;
    }

    /**
     * Crée une cellule JPanel avec un état donné.
     */
    private JPanel createCell(boolean isActive) {
        JPanel cell = new JPanel();
        cell.setBackground(isActive ? MyColor.DARK_BLUE : MyColor.LIGHT_BLUE);
        return cell;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(150, 150); // Taille fixe pour chaque environnement
    }
}
