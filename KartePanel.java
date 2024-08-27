import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class KartePanel extends JPanel {
    private Karte karte;

    public KartePanel(Karte karte) {
        this.karte = karte;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (karte != null && karte.getBild() != null) {
            g.drawImage(karte.getBild(), 0, 0, getWidth(), getHeight(), null);
        }
    }

    public void setKarte(Karte karte) {
        this.karte = karte;
        repaint();
    }
}
