package application.model;

public class AddonPurchase {
    private final Hotel hotel;
    private String name;
    private int price;

    AddonPurchase(String name, int price, Hotel hotel) {
        this.name = name;
        this.price = price;
        this.hotel = hotel;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Hotel getHotel() {
        return hotel;
    }

    @Override
    public String toString() {
        return name + " (" + price + ",-)";
    }
}
