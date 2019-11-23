package converter;

public class Pair<F, S> {
    private F first;
    private S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    @Override  //TESTING ONLY
    public String toString() {
        return "Pair + < " + first + ", " + second + " >";
    }
}