import java.awt.image.BufferedImage;

public class Karte {
    private Farbe farbe;
    private String wert;
    private BufferedImage bild;

    public Karte(Farbe farbe, String wert, BufferedImage bild) {
        this.farbe = farbe;
        this.wert = wert;
        this.bild = bild;
    }

    public Farbe getFarbe() {
        return farbe;
    }

    public String getWert() {
        return wert;
    }

    public BufferedImage getBild() {
        return bild;
    }

    public void setBild(BufferedImage bild) {
        this.bild = bild;
    }

    @Override
    public String toString() {
        return wert + " " + farbe;
    }
}
