import java.util.Stack;

public class AblageStapel {
    private Stack<Karte> stapel;

    public AblageStapel() {
        stapel = new Stack<>();
    }

    public void ablegen(Karte karte) {
        stapel.push(karte);
    }

    public Karte obersteKarte() {
        return stapel.isEmpty() ? null : stapel.peek();
    }

    public boolean istLeer() {
        return stapel.isEmpty();
    }
}
