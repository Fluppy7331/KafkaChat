package org.example;

public class MyColor {
    private int red;
    private int green;
    private int blue;
    private String name;

    public MyColor(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public static MyColor generateColor() {
        int red = (int) (Math.random() * 256);
        int green = (int) (Math.random() * 256);
        int blue = (int) (Math.random() * 256);
        return new MyColor(red, green, blue);
    }
    @Override
    public String toString() {
        return "rgb(" + red + ", " + green + ", " + blue + ")";
    }
}

