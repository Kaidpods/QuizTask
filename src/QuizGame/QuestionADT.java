package QuizGame;

public interface QuestionADT {

    /** Shows the correct answer for the question
     *
     * @return Correct answer
     */
    public String getAnswer();

    /**
     * Returns the question
     *
     * @return Question going to be asked
     */
    public String getQuestion();

    /**
     * Checks to see if the given answer is the correct answer and ensures to turn the given answer into a lower case when comparing
     *
     * @param answer The users answer to the question
     *
     * @return True if the user answered the question correctly, else it returns false
     */
    public boolean isCorrect(String answer);

    /**
     * The number of points that the player would be rewarded for the current question
     *
     * @return Number of points to be given to the player for the correct answer
     */
    public int getValue();
}
