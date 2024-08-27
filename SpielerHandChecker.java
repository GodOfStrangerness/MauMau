import java.util.ArrayList;
import java.util.List;

public class SpielerHandChecker {
    private RegelBase regeln;
    private AblageStapel ablageStapel;

    public SpielerHandChecker(RegelBase regeln, AblageStapel ablageStapel) {
        this.regeln = regeln;
        this.ablageStapel = ablageStapel;
    }

    public List<Karte> getSpielbareKarten(List<Karte> spielerHand) {
        List<Karte> spielbareKarten = new ArrayList<>();
        Karte obersteKarte = ablageStapel.obersteKarte();

        for (Karte karte : spielerHand) {
            if (regeln.istZugErlaubt(karte, obersteKarte)) {
                spielbareKarten.add(karte);
            }
        }
        return spielbareKarten;
    }
}
