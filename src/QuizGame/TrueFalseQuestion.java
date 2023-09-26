package QuizGame;

public class TrueFalseQuestion extends Question{
    public TrueFalseQuestion (String question, Boolean answer, Integer value) {
        super(question, answer.toString(), value);
    }
    @Override
    public String getQuestion() {
        return super.getQuestion() + "\n(Answer true or false)";
    }
}
