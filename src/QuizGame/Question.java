package QuizGame;

public abstract class Question implements QuestionADT {
    private String question;
    private String answer;

    private int value;

    public Question(String question, String answer, int value) {
        this.question = question;
        this.answer = answer.toLowerCase();
        this.value = value;
    }

    public String getQuestion() {
        return question + " (" + value + " pts) ?";
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isCorrect(String userSays) {
        //Checks to see if any answer was supplied
        if (userSays != null && userSays.length() > 0) {
            //Sets the users response to lowercase and compares
            return userSays.toLowerCase().equals(getAnswer());
        }
        return false;
    }

    public String toString() {
        return "Question{" + "question='" + question + '\'' + ", answer ='" + answer + '\'' + ", points=" + value + '}';
    }
}
