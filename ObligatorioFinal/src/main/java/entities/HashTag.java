package entities;

import tads.linkedlist.MyLinkedListImpl;
import tads.linkedlist.MyList;

public class HashTag {

    private int count = 0;
    private long id;
    private String text;
    private MyList<Tweet> tweets;


    public HashTag(long id, String text) {
        this.id = id;
        this.text = text;
        this.tweets = new MyLinkedListImpl<>();
    }


    public long getId() {
        return id;
    }
    public String getText() {
        return text;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public MyList<Tweet> getTweets() {
        return tweets;
    }


    @Override
    public boolean equals(Object obj) {
        boolean iguales = false;
        if (this.getClass() == obj.getClass()) {
            if (this.text.equals (((HashTag) obj).text)) {
                iguales = true;
            }
        }
        return iguales;    }
}
