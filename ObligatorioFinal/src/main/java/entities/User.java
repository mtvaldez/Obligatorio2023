package entities;

import tads.linkedlist.MyLinkedListImpl;
import tads.linkedlist.MyList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.Integer.parseInt;

public class User {

    private long id;
    private String name;
    private MyList<Tweet> tweets = new MyLinkedListImpl<>();;
    private boolean isVerified;
    private int favorites;

    public User(long id, String name, String  isVerified, String favorites) {
        this.id = id;
        this.name = name;
        if (isVerified.equals("TRUE")) {
            this.isVerified = true;
        } else {
            this.isVerified = false;
        }
        try {
            if (favorites != null){
                this.favorites = (int) Double.parseDouble(favorites); //parseInt(favorites);
            }
        } catch (NumberFormatException e) {
            this.favorites = 0;
        }
    }


    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public MyList<Tweet> getTweets() {
        return tweets;
    }
    public boolean isVerified() {
        return isVerified;
    }
    public void setVerified(boolean verified) {
        isVerified = verified;
    }
    public int getFavorites() {
        return favorites;
    }
    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }


    @Override
    public boolean equals(Object obj) {
        boolean iguales = false;
        if (this.getClass() == obj.getClass()) {
            if (this.name.equals (((User) obj).name)) {
                iguales = true;
            }
        }
        return iguales;
    }

}
