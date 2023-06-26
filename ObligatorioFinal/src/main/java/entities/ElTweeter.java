package entities;


import exceptions.HashTagAlreadyExists;
import exceptions.HashTagDoesNotExist;
import exceptions.UserAlreadyExists;
import exceptions.UserDoesNotExist;
import tads.BinarySearchTree.MyBST;
import tads.BinarySearchTree.MyBSTImpl;
import tads.BinarySearchTree.NodeBST;
import tads.exceptions.NodeAlreadyExists;
import tads.linkedlist.MyLinkedListImpl;
import tads.linkedlist.MyList;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

public class ElTweeter implements Runnable {

    private MyList<User> users = new MyLinkedListImpl();
    private MyList<Tweet> tweets = new MyLinkedListImpl<>();
    private MyList<HashTag> hashTags = new MyLinkedListImpl<>();
    private MyList<Driver> drivers = new MyLinkedListImpl<>();
    private MyList<String> userNames = new MyLinkedListImpl<>();
    private MyList<String> hashtagsText = new MyLinkedListImpl<>();


    public MyList<String> getUserNames() {
        return userNames;
    }
    public MyList<String> getHashtagsText() {
        return hashtagsText;
    }
    public HashTag getHashTag(String text) throws HashTagDoesNotExist {
        HashTag hashTag = new HashTag(0, text);
        if (hashTags.contains(hashTag)) {
            return hashTags.get(hashTags.indexOf(hashTag));
        }
        return null;
    }
    public User getUser(String nombre) throws UserDoesNotExist {
        User user =  new User(0, nombre, "", "0");
        if (users.contains(user)) {
            return users.get(users.indexOf(user));
        }
        return null;
    }



    public ElTweeter() throws IOException {
//        String[] conduccionistas = {"Max Verstappen", "Sergio Pérez", "Charles Leclerc", "Carlos Sainz", "Lewis Hamilton", "George Russell", "Fernando Alonso", "Lance Stroll", "Lando Norris", "Oscar Piastri", "Pierre Gasly", "Esteban Ocon", "Nyck de Vries", "Yuki Tsunoda", "Alexander Albon", "Logan Sargeant", "Guanyu Zhou", "Valtteri Bottas", "Nico Hülkenberg", "Kevin Magnussen"};
//
//        String[] apelliodos = {"Verstappen", "Perez", "Leclerc", "Sainz", "Hamilton", "Russell", "Alonso", "Stroll",
//                "Norris", "Piastri", "Gasly", "Ocon", "de Vries", "Tsunoda", "Albon", "Sargeant", "Zhou", "Bottas",
//                "Hülkenberg", "Magnussen"};

        Reader reader = new FileReader("src/main/java/csv/drivers.txt");
        Scanner scanner = new Scanner(reader);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            Driver driver = new Driver(parts[0], parts[1]);
            drivers.add(driver);
        }
        reader.close();
    }


    public MyList<HashTag> getHashTags() {
        return hashTags;
    }



    public void postTweet(String content, String hashTag, User user, String date) throws HashTagDoesNotExist {
        Tweet newTweet = new Tweet((long) (tweets.size()+1), content, hashTag, user, date);
        user.getTweets().add(newTweet);

        if (hashTag.length() != 0) {
            MyList<String> hashtagspt = hashtagSpliter(hashTag);

            for (int i = 0; i < hashtagspt.size(); i++) {
                getHashTag(hashtagspt.get(i)).getTweets().add(newTweet);
            }
        }
        this.tweets.add(newTweet);
    }
    public void addUser(String username, String  isVerified, String favorites) throws UserAlreadyExists {
        User newUser = new User((long) (users.size()+1), username, isVerified, favorites);
        if (users.contains(newUser)){
            throw new UserAlreadyExists("User already exists");
        }
        this.users.add(newUser);
        this.getUserNames().add(username);

    }
    public void addHashTag(String text) throws HashTagAlreadyExists {
        HashTag newHashTag = new HashTag(this.hashTags.size()+1, text);
        if (hashTags.contains(newHashTag)){
            throw new HashTagAlreadyExists("HashTag already exists");
        }
        this.hashTags.add(newHashTag);
        this.getHashtagsText().add(text);
    }


    private int tweetsWithWordPrivate(String word,MyList<Tweet> tweetses){
        int count = 0;
        for (int i = 0; i < tweetses.size(); i++){
            if (tweetses.get(i).getContent().contains(word)){
                count++;
            }
        }
        return count;
    }


    // Consulta 1
    public MyList topMencionesPilots(int mes, int ano) {
        for (int i = 0; i < drivers.size(); i++) {
            drivers.get(i).count = 0;
        }
        MyBST<Integer,Driver>  enlistamealospibes = new MyBSTImpl(null);

        for (int i = 0; i < this.tweets.size(); i++) {

            if (this.tweets.get(i).getMonth() == mes && this.tweets.get(i).getYear() == ano) {

                for (int x = 0; x < drivers.size(); x++)
                    if (this.tweets.get(i).getContent().contains(drivers.get(x).nombre)  || this.tweets.get(i).getContent().contains(drivers.get(x).apellido)) {
                        this.drivers.get(x).count++;
                    }
            }
        }

        for (int i = 0; i < drivers.size(); i++) {
            if (drivers.get(i).count != 0) {
                try {
                    enlistamealospibes.insert(drivers.get(i).count, drivers.get(i));
                } catch (NodeAlreadyExists e) {
                    drivers.get(i).count++;
                    try {
                        enlistamealospibes.insert(drivers.get(i).count, drivers.get(i));
                    } catch (NodeAlreadyExists ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }

        MyList<NodeBST<Integer,Driver>> topDrivers = enlistamealospibes.inOrder();
        MyList<Driver> result = new MyLinkedListImpl<>();
        while (result.size()<10){
        NodeBST<Integer,Driver> temp = topDrivers.get(0);
        for (int i = 0; i < topDrivers.size(); i++) {
           if (temp.getKey() < topDrivers.get(i).getKey()){
               temp = topDrivers.get(i);
           }
        }
        topDrivers.remove(temp);
        result.add(temp.getData());
        }

        System.out.println("Los pilotos con más menciones en el mes "+mes+" del año "+ano+" son:");
        for (int i = 0; i < 10; i++) {
            Driver aux = result.get(i);
            System.out.println((i+1)+") "+aux.nombre +" "+aux.apellido + " con " + result.get(i).count+" menciones.");
        }
        return topDrivers;
    }


    //  Consulta 2
    public MyLinkedListImpl<User> topUsersByTweets() {
        MyLinkedListImpl<User> topUsers = new MyLinkedListImpl<>();
        MyLinkedListImpl<User> clone = this.users.clone();
        User temp = clone.get(0);
        while (topUsers.size() < 15 && clone.size() > 0){

            temp = clone.get(0);
           for (int i = 0; i < clone.size();i++){
               if (clone.get(i).getTweets().size() > temp.getTweets().size()){
                   temp = clone.get(i);
               }
           }
           topUsers.add(temp);
           clone.remove(temp);
        }

        System.out.println("Los usuarios con más tweets son:");
        for (int i = 0; i < topUsers.size(); i++) {
            System.out.println((i+1)+") "+ "'" + topUsers.get(i).getName() + "'" + "(Verification = "+topUsers.get(i).isVerified()+")"+ " con " + topUsers.get(i).getTweets().size() + " tweets.");
        }

        return topUsers;
    }


    // Consulta 3
    public int hashtagsUsadosEnUnDia(int YYYY, int MM, int DD) throws HashTagDoesNotExist {
        MyList<HashTag> hashtagsUsados = new MyLinkedListImpl<>();
        int count = 0;

        for (int i = 0; i < this.tweets.size(); i++) {

            if (this.tweets.get(i).getMonth() == MM && this.tweets.get(i).getYear() == YYYY && this.tweets.get(i).getDay() == DD) {
                MyList<String> hashtagsDelTuit = hashtagSpliter(this.tweets.get(i).getHashTag());
                for (int j = 0; j < hashtagsDelTuit.size(); j++) {
                    HashTag aux = getHashTag(hashtagsDelTuit.get(j));
                    if (!hashtagsUsados.contains(aux)){
                        hashtagsUsados.add(aux);
                        count++;
                    }
                }
            }
        }
        System.out.println("El dia "+DD+" del mes "+MM+" del año "+YYYY+" se usaron "+count+" hashtags distintos.");
        return count;
    }

    public MyList<String> hashtagSpliter(String input){
        String cleanedInput = input.substring(1, input.length() - 1);
        String[] individualStrings = cleanedInput.split(", ");
        for (int i = 0; i < individualStrings.length; i++) {
            individualStrings[i] = individualStrings[i].trim();
        }
        MyList<String> result = new MyLinkedListImpl<>();
        for (String str : individualStrings) {
            result.add(str);
        }
        return result;
    }


    // Consulta 4
    public HashTag hashTagMasUsadoEnUnDia(int YYYY, int MM, int DD) throws HashTagDoesNotExist {
        //MyList<HashTag> hashtagsUsadosClone = this.hashTags.clone();
        for (int i = 0; i < this.tweets.size(); i++) {

            if (this.tweets.get(i).getMonth() == MM && this.tweets.get(i).getYear() == YYYY && this.tweets.get(i).getDay() == DD) {

                MyList<String> hashtagsdeltuit = hashtagSpliter(tweets.get(i).getHashTag());

                for (int z=0;z<hashtagsdeltuit.size();z++){
                    this.getHashTag(hashtagsdeltuit.get(z)).setCount(this.getHashTag(hashtagsdeltuit.get(z)).getCount()+1);
//                    for (int j = 0; j < this.getHashTags().size(); j++) {
//                        HashTag temp = this.getHashTags().get(j);
//                        if (temp.getText().equals(hashtagsdeltuit.get(z))) {
//                           temp.setCount(temp.getCount() + 1);
//                        }
//                    }
                }
            }
        }
        getHashTag("'f1'").setCount(0);
        getHashTag("'F1'").setCount(0);
        HashTag temp = this.hashTags.get(0);
        for (int i = 0; i < this.hashTags.size(); i++) {

           if (this.hashTags.get(i).getCount() > temp.getCount()){
                temp = this.hashTags.get(i);
           }

        }

        System.out.println("El hashtag mas usado el dia "+DD+" del mes "+MM+" del año "+YYYY+" fue "+temp.getText()+" con "+temp.getCount()+" menciones.");

        for (int i=0; i < this.hashTags.size(); i++){
            this.hashTags.get(i).setCount(0);
        }
        return temp;
    }


    // Consulta 5
    public MyLinkedListImpl<User> topUsersByFavorite() {
        MyLinkedListImpl<User> topUsers = new MyLinkedListImpl<>();
        MyLinkedListImpl<User> clone = this.users.clone();
        while (topUsers.size() < 7 && clone.size() > 0){
            User temp = clone.get(0);
            for (int i = 0; i < clone.size();i++){
                if (clone.get(i).getFavorites() > temp.getFavorites()){
                    temp = clone.get(i);
                }
            }
            topUsers.add(temp);
            clone.remove(temp);
        }

        System.out.println("Los usuarios con más favoritos son:");
        for (int i = 0; i < topUsers.size(); i++) {
            System.out.println((i+1)+") "+topUsers.get(i).getName() + " con " + topUsers.get(i).getFavorites() + " favoritos.");
        }

        return topUsers;
    }


    // Consulta 6
    public int tweetsWithWord(String word) {
        int count = 0;
        for (int i = 0; i < tweets.size(); i++){
            if (tweets.get(i).getContent().contains(word)){
                count++;
            }
        }
        System.out.println("La palabra "+word+" se encontro en "+count+" tweets.");
        return count;
    }


    @Override
    public void run() {

        Boolean exit = false;

        while(!exit){
            System.out.println("Menu");
            System.out.println("Ingrese una opcion: ");
            System.out.println("1. Top 10 de pilotos con mas menciones en un mes y año dados");
            System.out.println("2. Top 15 de usuarios con mas tweets");
            System.out.println("3. Numero de hashtags usados en un dia");
            System.out.println("4. Hashtag mas usado en un dia dado");
            System.out.println("5. Top 7 de usuarios con mas favoritos");
            System.out.println("6. Numero de tweets que contienen una palabra especifica");
            System.out.println("7. Salir");
            System.out.println(" ");

            Scanner inputR = new Scanner(System.in);
            int option = inputR.nextInt();

            switch (option){
                case 1:
                    System.out.println("Ingrese el mes: ");
                    int month = inputR.nextInt();
                    System.out.println("Ingrese el año: ");
                    int year = inputR.nextInt();
                    topMencionesPilots(month, year);
                    System.out.println(" ");
                    break;
                case 2:
                    topUsersByTweets();
                    System.out.println(" ");
                    break;
                case 3:
                    System.out.println("Ingrese el año: ");
                    int year1 = inputR.nextInt();
                    System.out.println("Ingrese el mes: ");
                    int month1 = inputR.nextInt();
                    System.out.println("Ingrese el dia: ");
                    int day = inputR.nextInt();
                    try {
                        hashtagsUsadosEnUnDia(year1, month1, day);
                    } catch (HashTagDoesNotExist e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(" ");
                    break;
                case 4:
                    System.out.println("Ingrese el año: ");
                    int year2 = inputR.nextInt();
                    System.out.println("Ingrese el mes: ");
                    int month2 = inputR.nextInt();
                    System.out.println("Ingrese el dia: ");
                    int day1 = inputR.nextInt();
                    try {
                        hashTagMasUsadoEnUnDia(year2, month2, day1);
                    } catch (HashTagDoesNotExist e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(" ");
                    break;
                case 5:
                    topUsersByFavorite();
                    System.out.println(" ");
                    break;
                case 6:
                    System.out.println("Ingrese la palabra: ");
                    String word = inputR.next();
                    tweetsWithWord(word);
                    System.out.println(" ");
                    break;
                case 7:
                    exit = true;
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }
        }
    }
}
