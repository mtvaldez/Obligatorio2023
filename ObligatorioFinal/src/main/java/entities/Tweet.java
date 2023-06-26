package entities;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Tweet {

    private long id;
    private String content;
    private String hashTag;
    private User user;
    private byte day;
    private byte month;
    private short year;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public Tweet(Long id, String content, String  hashTag, User user, String date) {
        this.id = id;
        this.content = content;
        this.hashTag = hashTag;
        this.user = user;
        try {
            LocalDateTime preDate = LocalDateTime.parse(date,formatter);
            this.day = (byte) preDate.getDayOfMonth();
            this.month = (byte) preDate.getMonthValue();
            this.year = (short) preDate.getYear();
        } catch (Exception e) {
            this.day = 1;
            this.month = 1;
            this.year = 2020;
        }
    }


    public long getId() {
        return id;
    }
    public String getContent() {
        return content;
    }
    public String getHashTag() {
        return hashTag;
    }
    public byte getDay() {
        return day;
    }
    public byte getMonth() {
        return month;
    }
    public short getYear() {
        return year;
    }


    @Override
    public boolean equals(Object obj) {
        boolean iguales = false;
        if (this.getClass() == obj.getClass()) {
            if (this.id == ((Tweet) obj).id){
                iguales = true;
            }
        }
        return iguales;
    }

}
