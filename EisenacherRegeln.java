public class EisenacherRegeln extends RegelBase {
    @Override
    public boolean istZugErlaubt(Karte gespielteKarte, Karte obersteKarte) {
        return gespielteKarte.getFarbe().equals(obersteKarte.getFarbe()) ||
            gespielteKarte.getWert().equals(obersteKarte.getWert());
    }
}
