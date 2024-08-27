import java.util.Stack;

public class Talon {
    private Stack<Karte> stapel;

    public Talon() {
        stapel = new Stack<>();
    }

    public void hinzufuegen(Karte karte) {
        stapel.push(karte);
    }

    public Karte ziehen() {
        return stapel.isEmpty() ? null : stapel.pop();
    }

    public boolean istLeer() {
        return stapel.isEmpty();
    }
}
