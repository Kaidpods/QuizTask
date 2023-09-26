package QuizGame;

import QuizGame.Kaidpods.Person;

import java.util.Stack;

/**
 * Player class, takes note of the scores as well as the name of the player
 * @author Kaidpods
 */
public class Player extends Person {
    private Stack<Integer> previousScores = new Stack<>();

    public Player(String firstName, String surname) {
        super(firstName, surname);
    }

    public Player(String firstName, String surname, String scores) {
        this(firstName, surname);

        //Split the string by commas, parse to Integers and add to stack
        String[] csvScores = scores.split(",");
        for (String val : csvScores) {
            try {
                int iVal = Integer.parseInt(val);
            } catch (NumberFormatException nfe) {
                System.err.println("Problem parsing previous score information.");
            }
        }
    }

    public void recordScore(int score) {
        previousScores.add(score);
    }

    public int getLastScore() {
        try {
            return previousScores.peek();
        } catch (Exception e) {
            System.err.println("previousScores is empty, there are no scores to show");
            return 0;
        }

    }

    public int getHighestScore() {
        if (previousScores.size() > 0) {
            //clone the stack so we don't change the original
            Stack<Integer> working = (Stack<Integer>) previousScores.clone();
            //pop every element in the stack to findnew maximum score.
//use a find maximum algorithm to find the highest score
//use the working.pop() method to get a value from the stack.
            int maxValue = working.pop();
            int size = working.size();
            for (int i = 0; i < size; i++){
                if (working.peek() > maxValue){
                    maxValue = working.pop();
                }
                else {
                    working.pop();
                }
            }
            return maxValue;
        } else
            return 0;
    }
    @Override
    public String toString() {
        return "Player: " + super.getFullName() +
                " previousScores=" + previousScores;
    }
}

