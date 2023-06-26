import entities.ElTweeter;
import entities.csvReader;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        ElTweeter tuiterer = new ElTweeter();

        csvReader reader = new csvReader(tuiterer);
        reader.read();

        tuiterer.run();
    }

}
