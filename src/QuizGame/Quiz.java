package QuizGame;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class Quiz {

    protected String DEFAULT_NAME = "Anon";
    protected ArrayList<ArrayList<String>> options = new ArrayList<>();
    protected ArrayList<Integer> qType = new ArrayList<>();

    protected ArrayList<String> questions = new ArrayList<>();

    protected ArrayList<String> answers = new ArrayList<>();

    protected ArrayList<Integer> value = new ArrayList<>();

    public List<Question> quizQuestions = new ArrayList<>();

    File myFile = new File("questions.csv");
    File myFileBackup = new File("QuizTask/questions.csv");

    //Throws an exception only if file cant be found
    public Quiz() {

        //Reads in file from a csv
        read(myFile);
        //Checks how many instances of the questions there is and loops to create then
        int numQ = questions.size();
        for (int i = 0; i < numQ; i++) {
            switch (qType.get(i)) {
                case 1 -> {
                    Question newQ = new Question(questions.get(i), answers.get(i), value.get(i));
                    quizQuestions.add(newQ);
                }
                case 2 -> {
                    TrueFalseQuestion newTFQ = new TrueFalseQuestion( questions.get(i), Boolean.parseBoolean(answers.get(i)), value.get(i));
                    quizQuestions.add(newTFQ);
                }
                case 3 -> {
                    MutlipleChoiceQuestion newMQ = new MutlipleChoiceQuestion( questions.get(i), answers.get(i), options.get(i), value.get(i));
                    quizQuestions.add(newMQ);
                }
            }

        }
        Collections.shuffle(quizQuestions);
    }

    public void start() {
        boolean rerunTest = true;
        while (rerunTest) {
            rerunTest = false;
            Scanner input = new Scanner(System.in);

            //Gets user's name
            System.out.println("What is your name?");
            String name = input.nextLine();

            //Ensures input isn't empty
            if (name.length() < 2) {
                name = DEFAULT_NAME;
            }

            System.out.println("Welcome " + name + " to our Quiz!");

            //Asks questions and keeps the score of them

            int total = 0;
            for (int i = 0; i < quizQuestions.size(); i++) {
                System.out.print("Q" + (i + 1) + ": ");
                total = total + askQuestion(quizQuestions.get(i));
            }
            System.out.println(name + " you scored " + total + "/" + getTotalValue());
            int dialogButton = JOptionPane.showConfirmDialog(null, "Do you want to redo the quiz?", "Rerun quiz?", JOptionPane.YES_NO_OPTION);

            if (dialogButton == JOptionPane.YES_OPTION) {
                rerunTest = true;
            } else {
                System.exit(0);
            }
        }
    }

    public int askQuestion(Question q) {

        Scanner input = new Scanner(System.in);
        int score = 0;

        System.out.println(q.getQuestion());

        //Gets user input and assumes it would be String

        String answer = input.nextLine();

        //If user entered nothing then it results in a zero

                if (answer.length() < 1) {
                    System.out.println("No answer supplied, 0 points rewarded.");
                } else {

                    if (q.isCorrect(answer)) {
                        System.out.println(answer + " is the correct answer, added " + q.getValue() + " to score.");
                        score = q.getValue();
                    } else {
                        System.out.println(answer + " is the wrong answer, 0 points awarded.");
                    }
                }
        return score;
    }

    public void read( File file) {
        //Reads each line of file into 3 arrayLists, 1 for each row
        int index = -1;
        String line;
        String splitBy = ",";
        String splitFalse = ";";


        qType.clear();
        questions.clear();
        answers.clear();
        value.clear();

        /* For example
            QuizGame.QuestionTypes.Question,Answer,3 would be split into
            (QuizGame.QuestionTypes.Question) (Answer) (3)
         */


        try {
            //Reads in the file predefined by myObj
            BufferedReader myBuffer = new BufferedReader(new FileReader(file));
            //While there is a line to read
            while ((line = myBuffer.readLine()) != null) {
                index++;
                options.add(new ArrayList<>());
                //Splits at each ',' and is stored into a temp array
                String[] temp = (line.split(splitBy));
                //Added into each row based on the part of the line it split
                qType.add(Integer.valueOf(temp[0]));
                questions.add(temp[1]);
                answers.add(temp[2]);
                value.add(Integer.parseInt(temp[3]));
                if (qType.get(index).equals(3)) {
                    String[] tempFalse = (line.split(splitFalse));

                    for (int i = 1; i < tempFalse.length; i++) {
                        options.get(index).add(tempFalse[i]);
                    }
                }


            }
            myBuffer.close();
            //Catches errors
        } catch (FileNotFoundException fnfe) {
            //Checks if the file cant be found even with the backup location
            if (file == myFileBackup) {
                System.err.println("File could not be found anywhere");
                System.err.println("CSV should be placed where a 'src' folder is visible");
                System.exit(1);
            } else {
                System.err.println("Couldn't find the file, searching the backup location");
                read(myFileBackup);
            }

        } catch (IOException ioe) {
            System.err.println("Exception thrown: " + ioe.getMessage());
        }
    }

    public int getTotalValue() {
        int total = 0;
        for (Integer integer : value) {
            total += integer;
        }
        return total;
    }

    public static void main(String[] args) {
        //Calls the quiz constructor
        Quiz myQuiz = new Quiz();

        //Starts the quiz
        myQuiz.start();
    }
}