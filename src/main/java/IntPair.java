public class IntPair {
    private int first, second;

    public IntPair(int first, int second) {
        this.first = first;
        this.second = second;
    }

    public IntPair() {
        this.first = 0;
        this.second = 0;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getSecond() {
        return second;
    }

    public int getFirst() {
        return first;
    }
}
