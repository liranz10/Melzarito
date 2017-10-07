package Logic;


public class Table {

    private int number;
    private int numOfSeats;
    private boolean isEmpty; //--sapir: add this

    public Table(int number, int numOfSeats) {
        this.number = number;
        this.numOfSeats = numOfSeats;
        this.isEmpty = true;
    }

    public int getNumber() {
        return number;
    }

    public int getNumOfSeats() {
        return numOfSeats;
    }

    public boolean isEmpty(){
        return isEmpty;
    } //--sapir: add this

    public void setIsEmpty(boolean isEmpty){
        this.isEmpty = isEmpty;
    } //--sapir: add this
}
