package Logic;


public class Table {

    private int number;
    private int numOfSeats;
    private boolean empty;

    public Table() {

    }

    public Table(int number, int numOfSeats) {
        this.number = number;
        this.numOfSeats = numOfSeats;
        this.empty = true;
    }

    public int getNumber() {
        return number;
    }

    public int getNumOfSeats() {
        return numOfSeats;
    }

    public boolean isEmpty(){
        return empty;
    }

    public void setIsEmpty(boolean isEmpty){
        this.empty = isEmpty;
    }
}
