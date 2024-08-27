import javax.swing.*;
import java.awt.*;
import java.util.List;

//FENSTER MAL PROBIEREN

public class MauMauGUI extends JFrame {
    private JPanel spielerHandPanel;
    private JPanel kiHandPanel;
    private JPanel topCardPanel;
    private MauMauSpiel spiel;

    public MauMauGUI(MauMauSpiel spiel) {
        this.spiel = spiel;
        setTitle("MauMau Spiel");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        spielerHandPanel = new JPanel();
        spielerHandPanel.setLayout(new FlowLayout());
        spielerHandPanel.setBackground(Color.DARK_GRAY);

        kiHandPanel = new JPanel();
        kiHandPanel.setLayout(new FlowLayout());
        kiHandPanel.setBackground(Color.DARK_GRAY);

        topCardPanel = new JPanel();
        topCardPanel.setBackground(Color.DARK_GRAY);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(26, 111, 47));
        GridBagConstraints gbc = new GridBagConstraints();

        // KI-Hand
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.NORTH;
        mainPanel.add(kiHandPanel, gbc);

        // Oberste Karte auf dem Stapel
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(topCardPanel, gbc);

        // Spielerhand
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.SOUTH;
        mainPanel.add(spielerHandPanel, gbc);

        add(mainPanel);
        updateHands();
        updateTopCard();
    }

    public void updateHands() {
        spielerHandPanel.removeAll();
        for (Karte karte : spiel.getSpielerHand()) {
            if (karte.getBild() == null) {
                String key = karte.getWert() + "_" + karte.getFarbe().toString().toLowerCase();
                karte.setBild(spiel.getImageMap().getImage(key));
                if (karte.getBild() == null) {
                    System.out.println("Failed to load image for key: " + key);
                }
            }
            KartePanel kartePanel = new KartePanel(karte);
            kartePanel.setPreferredSize(new Dimension(100, 150));
            spielerHandPanel.add(kartePanel);
        }

        kiHandPanel.removeAll();
        for (Karte karte : spiel.getKiHand()) {
            if (karte.getBild() == null) {
                String key = karte.getWert() + "_" + karte.getFarbe().toString().toLowerCase();
                karte.setBild(spiel.getImageMap().getImage(key));
                if (karte.getBild() == null) {
                    System.out.println("Failed to load image for key: " + key);
                }
            }
            KartePanel kartePanel = new KartePanel(karte);
            kartePanel.setPreferredSize(new Dimension(100, 150));
            kiHandPanel.add(kartePanel);
        }

        revalidate();
        repaint();
    }

    public void updateTopCard() {
        topCardPanel.removeAll(); // Entfernen Sie alle Komponenten aus dem Panel
        Karte topCard = spiel.getAblageStapel().obersteKarte();
        if (topCard != null) {
            if (topCard.getBild() == null) {
                String key = topCard.getWert() + "_" + topCard.getFarbe().toString().toLowerCase();
                topCard.setBild(spiel.getImageMap().getImage(key));
                if (topCard.getBild() == null) {
                    System.out.println("Failed to load image for key: " + key);
                }
            }
            KartePanel topCardKartePanel = new KartePanel(topCard); // Erstellen Sie ein neues Panel für die Karte
            topCardKartePanel.setPreferredSize(new Dimension(100, 150));
            topCardPanel.add(topCardKartePanel); // Fügen Sie das neue Panel hinzu
        }
        revalidate();
        repaint();
    }
}
