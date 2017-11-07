package Logic;


import java.util.Date;

public class ClubMember {
    private int id;
    private String name;
    private String address;
    private int phoneNumber;

    public ClubMember(int id, String name, String address, int phoneNumber) {
        this.id = id;
        this.name = name;
        this.address = address;

        this.phoneNumber = phoneNumber;
    }

    public ClubMember() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
