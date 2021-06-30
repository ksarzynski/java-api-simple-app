package gui;

import api.Retriever;
import com.mashape.unirest.http.exceptions.UnirestException;
import quiz.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Gui extends JFrame{

    private final static int[] populations = { 10000, 100000, 500000, 1000000, 2000000 };
    private final static String[] countryIds = {"PL", "AF", "AD", "DE", "GE", "FR", "BR", "GR", "GD"};
    private final static String[] currency = {"EUR", "USD", "CAD", "JPY", "PLN", "MKD"};

    Retriever retriever;
    Bundle bundle;

    int bundleChoice;

    JFrame frame;
    JMenu m1;
    JMenu m2;
    JMenuItem m11;
    JMenuItem m12;
    JMenuItem m21;
    JMenuItem m22;
    JButton accept;
    JButton checkAnswer;

    JLabel questionLabel;

    JComboBox<String> firstComboBox;
    JComboBox<String> secondComboBox;
    JComboBox<String> answersComboBox;

    JLabel answerLabel = new JLabel("");
    JLabel scoreLabel = new JLabel("");

    public String selectedFirstString;

    public String selectedSecondString;

    public String selectedAnswerString;

    Question q;
    int questionId = 1;

    public Gui() throws UnirestException, InterruptedException {

        init();
    }

    public void init() {

        retriever = new Retriever();
        bundle = new Bundle();

        frame = new JFrame("Geo Quiz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);

        // question managing
        q = new Question(questionId);
        JPanel questionPanel = new JPanel();
        questionLabel = new JLabel();

        // menu
        JMenuBar mb = new JMenuBar();

            // question menu
        m1 = new JMenu();
        m11 = new JMenuItem();
        m11.addActionListener(e -> {
            if(questionId < 3)
                questionId++;
            else
                questionId = 1;
            q = new Question(questionId);
            prepareQuestion(q, questionPanel, questionLabel);
            refresh();
        });
        m1.add(m11);
        mb.add(m1);

            // language menu
        m2 = new JMenu();
        m21 = new JMenuItem();
        m21.addActionListener(e -> {
            setMenuBundle(bundle.englishBundle);
            bundleChoice = 0;
            prepareQuestion(q, questionPanel, questionLabel);
            refresh();
        });
        m22 = new JMenuItem();
        m22.addActionListener(e -> {
            setMenuBundle(bundle.polishBundle);
            bundleChoice = 1;
            prepareQuestion(q, questionPanel, questionLabel);
            refresh();
        });
        m2.add(m21);
        m2.add(m22);
        mb.add(m2);

        bundleChoice = 0;


        // comboboxes
        firstComboBox = new JComboBox<>();

        secondComboBox = new JComboBox<>();

        answersComboBox = new JComboBox<>();

        JPanel backGroundPanel = new JPanel(new GridLayout(3, 1));

        JPanel emptyPanel1 = new JPanel();
        JPanel emptyPanel2 = new JPanel();

        accept = new JButton("accept");
        accept.addActionListener(e -> {
            try {
                showQuestion(q, questionPanel, questionLabel);
            } catch (InterruptedException | UnirestException interruptedException) {
                interruptedException.printStackTrace();
            }
        });

        emptyPanel2.add(accept);

        checkAnswer = new JButton("check my answer");
        emptyPanel2.add(checkAnswer);

        emptyPanel2.add(answerLabel);

        emptyPanel1.add(scoreLabel);

        // ? ?? ? ? ? ? ?? ?
        checkAnswer.addActionListener(e -> {
            answerLabel.setText(String.valueOf(q.getProperAnswer()));

            if(selectedAnswerString.equals(String.valueOf(q.getProperAnswer()))){

                if(bundleChoice == 0){
                    scoreLabel.setText(bundle.englishBundle.get("good"));
                }
                else{
                    scoreLabel.setText(bundle.polishBundle.get("good"));
                }
            }

            else{

                if(bundleChoice == 0){
                    scoreLabel.setText(bundle.englishBundle.get("bad"));
                }
                else{
                    scoreLabel.setText(bundle.polishBundle.get("bad"));
                }
            }

            emptyPanel2.repaint();
            refresh();
        });

        backGroundPanel.add(emptyPanel1);
        backGroundPanel.add(questionPanel);
        backGroundPanel.add(emptyPanel2);

        prepareQuestion(q, questionPanel, questionLabel);

        // setting initial language to english
        setMenuBundle(bundle.englishBundle);

        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, backGroundPanel);
        frame.setVisible(true);
    }

    public void setMenuBundle(HashMap<String, String> bundle) {

        m1.setText(bundle.get("menu"));
        m2.setText(bundle.get("language"));
        m11.setText(bundle.get("menuButton1"));
        m21.setText(bundle.get("language1"));
        m22.setText(bundle.get("language2"));
        accept.setText(bundle.get("accept"));
        checkAnswer.setText(bundle.get("check"));
    }

    public void prepareQuestion(Question question, JPanel panel, JLabel questionLabel){

        answerLabel.setText("");
        scoreLabel.setText("");

        if(bundleChoice == 0){
            questionLabel.setText(bundle.englishBundle.get(question.getBundleFieldName()));
        }
        else{
            questionLabel.setText(bundle.polishBundle.get(question.getBundleFieldName()));
        }

        panel.add(questionLabel);

        panel.remove(secondComboBox);
        answersComboBox.removeAllItems();

        firstComboBox.removeAllItems();
        secondComboBox.removeAllItems();
        answersComboBox.removeAllItems();

        switch(question.getQuestionId()){

            case 1:

                for(int i : populations){
                    firstComboBox.addItem(String.valueOf(i));
                }
                panel.add(firstComboBox);

                for(String i : countryIds){
                    secondComboBox.addItem(i);
                }
                panel.add(secondComboBox);

                if(firstComboBox.getItemListeners().length > 0)
                {
                    firstComboBox.removeItemListener(firstComboBox.getItemListeners()[0]);
                    System.out.println("usuwanie");
                }

                if(secondComboBox.getItemListeners().length > 0)
                    secondComboBox.removeItemListener(secondComboBox.getItemListeners()[0]);

                firstComboBox.addItemListener(new SuggestionComboBoxListener(this, "selectedFirstString"));
                secondComboBox.addItemListener(new SuggestionComboBoxListener(this, "selectedSecondString"));

                panel.add(answersComboBox);
                if(answersComboBox.getItemListeners().length > 0)
                {
                    answersComboBox.removeItemListener(answersComboBox.getItemListeners()[0]);
                    System.out.println("usuwanie");
                }

                answersComboBox.addItemListener(new SuggestionComboBoxListener(this, ""));

                break;

            case 2:

                for(int i : populations){
                    firstComboBox.addItem(String.valueOf(i));
                }
                panel.add(firstComboBox);

                if(firstComboBox.getItemListeners().length > 0)
                {
                    firstComboBox.removeItemListener(firstComboBox.getItemListeners()[0]);
                    System.out.println("usuwanie");
                }

                if(secondComboBox.getItemListeners().length > 0)
                    secondComboBox.removeItemListener(secondComboBox.getItemListeners()[0]);

                firstComboBox.addItemListener(new SuggestionComboBoxListener(this, "selectedFirstString"));
                secondComboBox.addItemListener(new SuggestionComboBoxListener(this, "selectedSecondString"));

                for(String i : countryIds){
                    secondComboBox.addItem(i);
                }
                panel.add(secondComboBox);

                panel.add(answersComboBox);

                break;

            case 3:

                for(String c : currency){
                    firstComboBox.addItem(c);
                }
                panel.add(firstComboBox);

                if(firstComboBox.getItemListeners().length > 0)
                    firstComboBox.removeItemListener(firstComboBox.getItemListeners()[0]);

                firstComboBox.addItemListener(new SuggestionComboBoxListener(this, "firstSelectedString"));

                panel.add(answersComboBox);
        }
    }

    public void showQuestion(Question question, JPanel panel, JLabel questionLabel) throws InterruptedException, UnirestException {

        switch(question.getQuestionId()){

            case 1:

                retriever.getCitiesWithPopulation(selectedSecondString,
                        Integer.parseInt(selectedFirstString));
                q.setProperAnswer(retriever.getAmount());

                ArrayList<Integer> temp = getRandomAnswersList(q.getProperAnswer());

                System.out.println(temp);

                answersComboBox.removeAllItems();

                for(Integer i : temp){
                    answersComboBox.addItem(i.toString());
                }

                refresh();

                break;

            case 2:

                retriever.getCitiesWithPopulation(selectedSecondString,
                        -1 * Integer.parseInt(selectedFirstString));
                q.setProperAnswer(retriever.getAmount());

                ArrayList<Integer> temp2 = getRandomAnswersList(q.getProperAnswer());

                answersComboBox.removeAllItems();

                for(Integer i : temp2){
                    answersComboBox.addItem(i.toString());
                }

                refresh();

                break;

            case 3:

                retriever.getCountriesWithCurrency(selectedFirstString);

                q.setProperAnswer(retriever.getAmount());

                temp = getRandomAnswersList(q.getProperAnswer());

                answersComboBox.removeAllItems();

                for(Integer i : temp){
                    answersComboBox.addItem(i.toString());
                }
                panel.add(answersComboBox);
        }
    }

    public void refresh(){

        frame.repaint();
    }

    public int getRandomNumber(int bound){

        Random random = new Random();
        return random.nextInt(bound);
    }

    public ArrayList<Integer> getRandomAnswersList(int answer){

        ArrayList<Integer> answers = new ArrayList<>();

        if(answer > 10){
            answers.add((int) (0.8 * answer));
            answers.add((int) (0.9 * answer));
            answers.add((int) (1.1 * answer));
            answers.add((int) (1.2 * answer));
            answers.add(getRandomNumber(answers.size()), answer);
        }

        else if (answer > 2){
            answers.add(answer - 2);
            answers.add(answer - 1);
            answers.add(answer + 1);
            answers.add(answer + 2);
            answers.add(getRandomNumber(answers.size()), answer);
        }

        else{
            answers.add(answer + 1);
            answers.add(answer + 2);
            answers.add(answer + 3);
            answers.add(answer + 4);
            answers.add(getRandomNumber(answers.size()), answer);
        }

        System.out.println("done");
        return answers;
    }

    public static final class SuggestionComboBoxListener implements ItemListener {
        Component parent;
        String string;
        public SuggestionComboBoxListener(Component parent, String string) {
            this.parent = parent;
            this.string = string;
        }
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                JComboBox cb = (JComboBox) e.getSource();
                if(string.equals("selectedFirstString"))
                    ((Gui) parent).selectedFirstString = (String) cb.getSelectedItem();
                else if(string.equals("selectedSecondString"))
                    ((Gui) parent).selectedSecondString = (String) cb.getSelectedItem();
                else
                    ((Gui) parent).selectedAnswerString = (String) cb.getSelectedItem();
            }
        }
    }

}
