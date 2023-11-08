package QuizGame.Questions;

import QuizGame.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MutlipleChoiceQuestion extends Question {
    protected List<String> options = new ArrayList<>();

    private final int correctIndex;

    public MutlipleChoiceQuestion(String question, String answer, String[] options, int points) {
        super(question, answer, points);

        //Populate the list with all options and the correct answer, then finally randomises the list
        for (String choice : options) {
            this.options.add(choice);
        }
        this.options.add(answer);
        Collections.shuffle(this.options);

        //find index of correct answer and store for later
        this.correctIndex = this.options.indexOf(answer);
    }

    @Override
    public String toString() {
        return "MultipleChoiceQuestion{" +
                super.toString() +
                "options=" + options +
                "correct index=" + correctIndex +
                '}';
    }

    @Override
    public String getQuestion() {
        StringBuilder output = new StringBuilder(super.getQuestion() + "\n");

        for (int i = 0; i < this.options.size(); i++) {
            output.append(i + 1).append(": ");
            output.append(options.get(i)).append("\n");
        }

        output.append("(Answer 1,2,3,4)");

        return output.toString();
    }

    @Override
    public boolean isCorrect(String userSays) {
        boolean isCorrect = false;
        try {
            //Change user answer to an int and subtract 1 as index starts at value 0
            int userIndex = Integer.parseInt(userSays) - 1;
            if (correctIndex == userIndex) {
                isCorrect = true;
            }
        } catch (Exception e) {
            isCorrect = false;
        }
        return isCorrect;
    }
}
