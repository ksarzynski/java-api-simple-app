package gui;

import java.util.HashMap;

public class Bundle {

    HashMap<String, String> polishBundle;
    HashMap<String, String> englishBundle;

    public Bundle() {

        // menu
        polishBundle = new HashMap<>();
        englishBundle = new HashMap<>();

        polishBundle.put("menu", "Pytanie");
        englishBundle.put("menu", "Question");

        polishBundle.put("language", "Język");
        englishBundle.put("language", "Language");

        polishBundle.put("language1", "angielski");
        englishBundle.put("language1", "english");

        polishBundle.put("language2", "polski");
        englishBundle.put("language2", "polish");

        polishBundle.put("menuButton1", "Następny typ pytania");
        englishBundle.put("menuButton1", "Next type of question");

        polishBundle.put("menuButton2", "Następne pytanie tego typu");
        englishBundle.put("menuButton2", "Next question of that type");

        polishBundle.put("accept", "zaakceptuj");
        englishBundle.put("accept", "accept");

        polishBundle.put("check", "sprawdź");
        englishBundle.put("check", "check");

        // first question
        polishBundle.put("question1", "Ile miast w podanym kraju ma więcej mieszkańcow niż podano");
        englishBundle.put("question1", "How many cities in given country have more citizens than given amount");

        // second question
        polishBundle.put("question2", "Ile miast w podanym kraju ma mniej mieszkańcow niż podano");
        englishBundle.put("question2", "How many cities in given country have less citizens than given amount");

        // third question
        polishBundle.put("question3", "Ile krajów używa tej waluty?");
        englishBundle.put("question3", "How many countries use this currency?");

        // answers
        polishBundle.put("good", "Gratulacje, dobra odpowiedź!");
        englishBundle.put("good", "Congrats, good answer!");

        polishBundle.put("bad", "Zła odpowiedź!!!");
        englishBundle.put("bad", "Wrong answer!!!");
    }
}
