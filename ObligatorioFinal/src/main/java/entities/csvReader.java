package entities;

import exceptions.HashTagAlreadyExists;
import exceptions.HashTagDoesNotExist;
import exceptions.UserAlreadyExists;
import exceptions.UserDoesNotExist;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import tads.linkedlist.MyList;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class csvReader {
    private final ElTweeter elTweeterer;

    public csvReader(ElTweeter elTweeterer) {
        this.elTweeterer = elTweeterer;
    }

    public void read() {

        try {
            Reader reader = Files.newBufferedReader(Paths.get("src/main/java/csv/f1_dataset_test.csv"));
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);

            for (CSVRecord record: records) {
            if (record.getRecordNumber() == 1) {continue;}

            // String name = record.get(1);   String favourites = record.get(7);  String verified = record.get(8);
            if (!elTweeterer.getUserNames().contains(record.get(1))) {
                this.elTweeterer.addUser(record.get(1), record.get(8), record.get(7));
            }

            // String hashtags = record.get(11);
            if (record.get(11).length() != 0) {
                MyList<String> hashTags = elTweeterer.hashtagSpliter(record.get(11));

                for (int j = 0; j < hashTags.size(); j++) {
                    if (!elTweeterer.getHashtagsText().contains(hashTags.get(j))) {
                        elTweeterer.addHashTag(hashTags.get(j));
                    }
                }

            }

            // String date = record.get(9);  String content = record.get(10);
            elTweeterer.postTweet(record.get(10), record.get(11), elTweeterer.getUser(record.get(1)), record.get(9));

            }

        } catch (IOException | UserAlreadyExists | HashTagAlreadyExists | HashTagDoesNotExist | UserDoesNotExist e) {
            e.printStackTrace();
        }

    }
}