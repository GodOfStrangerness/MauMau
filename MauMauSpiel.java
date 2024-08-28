import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MauMauSpiel {
    private List<Karte> spielerHand;
    private List<Karte> kiHand;
    private Talon talon;
    private AblageStapel ablageStapel;
    private RegelBase regeln;
    private ImportFilesWithMap imageMap;
    private SpielerHandChecker handChecker;
    private MauMauGUI gui;  // Referenz zur GUI

    public MauMauSpiel(RegelBase regeln, ImportFilesWithMap imageMap) {
        this.regeln = regeln;
        this.imageMap = imageMap;
        this.spielerHand = new ArrayList<>();
        this.kiHand = new ArrayList<>();
        this.talon = new Talon();
        this.ablageStapel = new AblageStapel();
        this.handChecker = new SpielerHandChecker(regeln, ablageStapel);

        // Initialisierung der Hände
        for (int i = 0; i < 5; i++) {
            spielerHand.add(ziehen());
            kiHand.add(ziehen());
        }
        ablageStapel.ablegen(ziehenZufaelligeKarte());
    }

    public void setGui(MauMauGUI gui) {
        this.gui = gui;  // Setzen der Referenz zur GUI
    }

    public List<Karte> getSpielerHand() {
        return spielerHand;
    }

    public List<Karte> getKiHand() {
        return kiHand;
    }

    public ImportFilesWithMap getImageMap() {
        return imageMap;
    }

    public AblageStapel getAblageStapel() {
        return ablageStapel;
    }

    public Karte ziehen() {
        Farbe farbe = Farbe.values()[(int) (Math.random() * Farbe.values().length)];
        String[] werte = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king", "ace"};
        String wert = werte[(int) (Math.random() * werte.length)];
        String key = wert + "_" + farbe.toString().toLowerCase();
        BufferedImage bild = imageMap.getImage(key);

        if (bild == null) {
            System.out.println("Bild nicht gefunden fuer Schluessel: " + key);
        } else {
            System.out.println("Bild geladen fuer Schluessel: " + key);
        }

        return new Karte(farbe, wert, bild);
    }

    private Karte ziehenZufaelligeKarte() {
        String[] keys = imageMap.imagesMap.keySet().toArray(new String[0]);
        String randomKey = keys[new Random().nextInt(keys.length)];
        BufferedImage bild = imageMap.getImage(randomKey);
        String[] parts = randomKey.split("_");
        String wert = parts[0];
        Farbe farbe = Farbe.valueOf(parts[1].toUpperCase());
        return new Karte(farbe, wert, bild);
    }

    public void spielerZug(int kartenIndex) {
        Karte gespielteKarte = spielerHand.get(kartenIndex);
        if (regeln.istZugErlaubt(gespielteKarte, ablageStapel.obersteKarte())) {
            spielerHand.remove(kartenIndex);
            ablageStapel.ablegen(gespielteKarte);
            imageMap.clearImage(gespielteKarte.getWert().toLowerCase() + "_" + gespielteKarte.getFarbe().toString().toLowerCase());
            gui.updateHands();  // Aktualisieren der Hände in der GUI
            gui.updateTopCard();  // Aktualisieren der obersten Karte in der GUI
        } else {
            System.out.println("Zug nicht erlaubt für: " + gespielteKarte);
        }
    }

    public void kiZug() {
        for (int i = 0; i < kiHand.size(); i++) {
            Karte karte = kiHand.get(i);
            if (regeln.istZugErlaubt(karte, ablageStapel.obersteKarte())) {
                kiHand.remove(i);
                ablageStapel.ablegen(karte);
                imageMap.clearImage(karte.getWert().toLowerCase() + "_" + karte.getFarbe().toString().toLowerCase());
                gui.updateHands();  // Aktualisieren der Hände in der GUI
                gui.updateTopCard();  // Aktualisieren der obersten Karte in der GUI
                return;
            }
        }
        kiHand.add(ziehen());
        gui.updateHands();  // Aktualisieren der Hände in der GUI
    }

    public List<Karte> getSpielbareKarten() {
        return handChecker.getSpielbareKarten(spielerHand);
    }

    public static void main(String[] args) {
        ImportFilesWithMap imageMap = new ImportFilesWithMap();
        imageMap.importFilesIntoMap();

        RegelBase regeln = new EisenacherRegeln();
        MauMauSpiel spiel = new MauMauSpiel(regeln, imageMap);

        // GUI starten
        javax.swing.SwingUtilities.invokeLater(() -> {
            MauMauGUI gui = new MauMauGUI(spiel);
            spiel.setGui(gui);  // Setzen der GUI-Referenz
            gui.setVisible(true);

            // Beispiel für die Überprüfung der spielbaren Karten
            List<Karte> spielbareKarten = spiel.getSpielbareKarten();
            if (spielbareKarten.isEmpty()) {
                spiel.spielerHand.add(spiel.ziehen());
                System.out.println("Ziehe Karte: " + spiel.spielerHand.get(spiel.spielerHand.size() - 1));
                System.out.println("Spielerhand: " + spiel.spielerHand);
                gui.updateHands();
            } else {
                System.out.println("Spielbare Karten: " + spielbareKarten);
                spiel.ablageStapel.ablegen(spielbareKarten.get(0));
                System.out.println("Oberste Karte auf dem Ablagestapel: " + spiel.ablageStapel.obersteKarte());
                gui.updateHands();
                gui.updateTopCard();
            }
        });
    }
}
