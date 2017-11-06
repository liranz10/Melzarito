package Logic;


public class Table {

    private int number;
    private int numOfSeats;
    private boolean empty;
    private boolean isClubMember;

    public Table() {

    }

    public Table(int number, int numOfSeats) {
        this.number = number;
        this.numOfSeats = numOfSeats;
        this.empty = true;
        this.isClubMember = false;
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

    public boolean isClubMember() {
        return isClubMember;
    }

    public void setClubMember(boolean clubMember) {
        isClubMember = clubMember;
    }

    public void setIsEmpty(boolean isEmpty){
        this.empty = isEmpty;
    }
}