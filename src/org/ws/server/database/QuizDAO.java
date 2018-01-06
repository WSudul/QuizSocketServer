package org.ws.server.database;

import org.ws.communication.job.Question;
import org.ws.communication.job.Result;
import org.ws.server.database.IQuizDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

public class QuizDAO implements IQuizDAO {
    private final static Logger logger = Logger.getLogger(QuizDAO.class.getName());
    private Connection connection;
    private Statement st;

    public QuizDAO(Connection connection) {
        this.connection=connection;
        st=createStatement(this.connection);
    }


    @Override
    public List<Long> getQuizes() {
        String[] columns =new String[]{"id"};
        String[] from={"quiz"};
        String condition="active IS TRUE";

        executeUpdate(st,new QueryBuilder()
                .columns(Arrays.asList(columns))
                .from(Arrays.asList(from))
                .where(condition)
                .BuildQuery());
        //#TODO implement
        return null;
    }

    @Override
    public Optional<List<Question>> getQuiz(Long quizId) {
        String[] columns =new String[]{"id,text,answers.text"};
        String[] from={"question,answers"};
        String condition="WHERE quiz_id"+quizId;
        String joinCondition="id=answers.question_id";

        executeUpdate(st,new QueryBuilder()
                .columns(Arrays.asList(columns))
                .from(Arrays.asList(from))
                .join(JoinType.LEFT,"answers",joinCondition)
                .where(condition)
                .BuildQuery());



        //#TODO implement
        return null;

    }


    @Override
    public Optional<Map<Long, Long>> getCorrectAnswers(Long quizId) {
        return null;
    }

    @Override
    public boolean persistAnswer(String user, Long quizId, Long questionId, Long answerId) {
        return false;
    }

    @Override
    public Optional<Map<Long, Long>> getUserScores(String userId) {
        return null;
    }

    @Override
    public Optional<List<Result>> getUserAnswers(String user,Long quizId) {
        return null;
    }


    private int executeUpdate(Statement s, String sql) {
        try {
            return s.executeUpdate(sql);
        } catch (SQLException e) {
            logger.warning("sql statement: " + sql + " could not be executed " + e.getMessage() + " Error Code: " +
                    e.getErrorCode());
            return -1;
        }

    }



    private Statement createStatement(Connection connection) {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            logger.warning(
                    "Exception thrown when creating Statement " + e.getMessage() + " Error Code: " + e.getErrorCode());
            return null;
        }

    }

}
