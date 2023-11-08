package QuizGame.Kaidpods;

import QuizGame.Question;
import QuizGame.Questions.MutlipleChoiceQuestion;
import QuizGame.Questions.TextQuestion;
import QuizGame.Questions.TrueFalseQuestion;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Database {
    private Connection conn = null;

    public void connect() {
        String connectionUrl =
                "jdbc:mysql://localhost:3306/questions?serverTimezone=UTC";
        try {
            //uses default user id and password. Not a great idea!!
            conn = DriverManager.getConnection(connectionUrl, "root", "");
        } catch (
                SQLException e) {
            // handle the exception
            System.err.println("oops!" + e.getMessage());
        }
    }
    public void disconnect() {
        try {
            // remember to close the connection
            conn.close();
        } catch (
                SQLException e) {
            // handle the exception
            System.err.println("oops!" + e.getMessage());
        }
    }

    public List<Question> getAllRows() throws SQLException {
        String sqlSelectAllPersons = "SELECT * FROM questions";

        PreparedStatement ps = conn.prepareStatement(sqlSelectAllPersons);
        // executeQueries is used for queries
        ResultSet rs = ps.executeQuery();

        List<Question> quizQuestions = new ArrayList<>();
        // Output all returned data to the console
        while (rs.next()){
            int typeOfQ = rs.getInt("type");
            String quest = rs.getString("question");
            String ans = rs.getString("answer");
            int pts = rs.getInt("value");

            switch (typeOfQ) {
                case 1 -> {
                    //Textual Question
                    Question newQ = new TextQuestion(quest, ans, pts);
                    quizQuestions.add(newQ);
                }
                case 2 -> {
                    //True/False Question
                    boolean answer = Boolean.parseBoolean(ans);
                    Question newTF = new TrueFalseQuestion(quest, answer, pts);
                    quizQuestions.add(newTF);
                }
                case 3 -> {
                    //Multiple-choice Question
                    String[] choices = rs.getString("multiAnswers").split(";");
                    Question newMQ = new MutlipleChoiceQuestion(quest, ans, choices, pts);
                    quizQuestions.add(newMQ);
                }
                default -> System.err.println("Question type not recognized.");
            }

            // Debug statement to check what was loaded in
            //System.out.println("Q:" + quest);
        }
        Collections.shuffle(quizQuestions);
        return quizQuestions;
    }
}
