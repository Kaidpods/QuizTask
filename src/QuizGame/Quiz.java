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

    protected int totalValue = 0;

    public List<Question> quizQuestions = new ArrayList<>();

    File myObj = new File("questions.csv");
    File myObjBackup = new File("QuizTask/questions.csv");

    //Throws an exception only if file cant be found
    public Quiz () throws FileNotFoundException {

        //Reads in file from a csv
        read(qType, questions, answers, value, myObj);
        //Checks how many instances of the questions there is and loops to create then
        int numQ = questions.size();
        for(int i = 0; i < numQ; i++){
            switch (qType.get(i)){
                case 1 ->{
                    Question newQ =  new Question(qType.get(i),questions.get(i), answers.get(i), value.get(i));
                    quizQuestions.add(newQ);
                }
                case 2 ->{
                    TrueFalseQuestion newTFQ =  new TrueFalseQuestion(qType.get(i),questions.get(i), Boolean.parseBoolean(answers.get(i)), value.get(i));
                    quizQuestions.add(newTFQ);
                }
                case 3 ->{
                    String[] falseAnswers;
                    MutlipleChoiceQuestion newMQ =  new MutlipleChoiceQuestion(qType.get(i),questions.get(i), answers.get(i), options.get(i), value.get(i));
                    quizQuestions.add(newMQ);
                }
            }

        }
        Collections.shuffle(quizQuestions);
    }
    public void start (){
            boolean rerunTest = true;
            while (rerunTest){
            rerunTest = false;
            Scanner input = new Scanner(System.in);

            //Gets user's name
            System.out.println("What is your name?");
            String name = input.nextLine();

            //Ensures input isn't empty
            if (name.length() < 2) {
                name = DEFAULT_NAME;
            }

            System.out.println("Welcome " + name + " to our QuizGame.Quiz!");

            //Asks questions and keeps the score of them

            int total = 0;
            for (int i = 0; i < quizQuestions.size(); i++) {
                System.out.print("Q" + (i + 1) + ": ");
                total = total + askQuestion(quizQuestions.get(i));
            }
            System.out.println(name + " you scored " + total + "/" + getTotalValue());
            int dialogButton = JOptionPane.showConfirmDialog (null, "Do you want to redo the quiz?", "Rerun quiz?", JOptionPane.YES_NO_OPTION);

            if(dialogButton == JOptionPane.YES_OPTION) {
            rerunTest = true;}
            else {
            System.exit(0);}
        }
    }

    public int askQuestion (Question q){

        Scanner input = new Scanner(System.in);
        int score = 0;

        System.out.println(q.getQuestion());

        //Gets user input and assumes it would be String

        String answer = input.nextLine();

        //If user entered nothing then it results in a zero
        switch(q.getqType()){
            case 1, 2 ->{
        if (answer.length() < 1){
            System.out.println("No answer supplied, 0 points rewarded.");
        } else {

                    if (q.isCorrect(answer)){
                        System.out.println(answer + " is the correct answer, added " + q.getValue() +" to score.");
                        score = q.getValue();
                    } else {
                        System.out.println(answer + " is the wrong answer, 0 points awarded.");
                    }
                }
            }
            case 3 ->{
                    while(answer.matches(".*[a-z].*")){
                        System.out.println("Please only use a number");
                        answer = input.nextLine();
                    }
                if (q.isCorrect(answer)){
                    System.out.println(answer + " is the correct answer, added " + q.getValue() +" to score.");
                    score = q.getValue();
                } else {
                    System.out.println(answer + " is the wrong answer, 0 points awarded.");
                }
                    /*for (int i = 0; i < options.size(); i++){
                        for (int j = 0; j < options.get(i).size(); j++){
                            if (q.getAnswer().toLowerCase() == options.get(i).get(j).toLowerCase()){
                                if (Integer.parseInt(answer) == j){
                                    System.out.println(q.getAnswer() + " is the correct answer, added " + q.getValue() +" to score.");
                                    score = q.getValue();
                                }
                            }
                        }
                    }

*/
            }
        }
        return score;
    }
    public void read(ArrayList<Integer> row1, ArrayList<String> row2, ArrayList<String> row3, ArrayList<Integer> row4, File file) throws FileNotFoundException {
        //Reads each line of file into 3 arrayLists, 1 for each row
        int index = -1;
        String line = "";
        String splitBy = ",";
        String splitFalse = ";";


        row1.clear();
        row2.clear();
        row3.clear();
        row4.clear();

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
                row1.add(Integer.valueOf(temp[0]));
                row2.add(temp[1]);
                row3.add(temp[2]);
                row4.add(Integer.parseInt(temp[3]));
                if (row1.get(index).equals(3)){
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
            if (file == myObjBackup){
                throw new FileNotFoundException("File could not be found anywhere") ;
            }else {
                read(qType,questions, answers, value, myObjBackup);
            }

        } catch (IOException ioe) {
            System.err.println("Exception thrown: " + ioe.getMessage());
        }
        //Adds the read text to the relevant variables
        for (int i = 0; i < row1.size(); i++){
            qType = row1;
            questions = row2;
            answers = row3;
            value = row4;
        }
    }

    public int getTotalValue (){
        int total = 0;
        for (int i = 0; i < value.size(); i++){
            total += value.get(i);
        }
        return total;
    }
    public static void main(String[] args) throws FileNotFoundException {
        //Calls the quiz constructor
        Quiz myQuiz = new Quiz();

        //Starts the quiz
        myQuiz.start();
    }
}