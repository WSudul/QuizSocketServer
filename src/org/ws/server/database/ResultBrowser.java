package org.ws.server.database;

import org.ws.communication.job.Question;
import org.ws.communication.job.Result;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ResultBrowser extends DaoBase implements IResultBrowser {
    private final static Logger logger = Logger.getLogger(ResultBrowser.class.getName());
    private String usedSchema;
    private Statement st;
    private IQuizDAO quizDAO;

    public ResultBrowser(Connection connection,String usedSchema) {
        setDbConnection(connection);
        quizDAO=new QuizDAO(connection,usedSchema);
    }

    @Override
    public List<Long> getQuizIds() {
            return  quizDAO.getQuizList();

    }
    @Override
    public Map<Long, List<Long>> getQuizQuestionIds(List<Long> quizIds) {

        Map<Long,List<Long>> quizQuestionsMap=new HashMap<>();

        for(Long id:quizIds)
        {
            Optional<List<Question>> result = quizDAO.getQuiz(id);
            if(result.isPresent()){
                List<Long> questionIds= result.get().stream().map(Question::getId).collect(Collectors.toList());
                quizQuestionsMap.put(id,questionIds);
            }
        }

        return quizQuestionsMap;
    }

    @Override
    public Map<Long, List<Long>> getAnswersCounts(List<Long> questionIds) {
        System.out.println("getUserAnswers");
        Optional<List<Result>> quizResults=null ; //#TODO fix
        if (!quizResults.isPresent())
            return null;

        Set<Long> questionListId = new HashSet<>();
        for (Result result : quizResults.get())
            questionListId.add(result.getQuestionId());

        List<String> columns = Arrays.asList(",answer_id");
        List<String> from = Arrays.asList("results");
        String condition = "WHERE question.id IS IN(" + questionIds.stream()
                .map(id -> id.toString())
                .collect(Collectors.joining(",")) + ")";

        String sql = new QueryBuilder()
                .select(columns)
                .from(from)
                .where(condition)
                .BuildQuery();

        ResultSet results = executeQuery(st, sql);

        Map<Long, List<Long>> questionAnswers = new HashMap<>();


        try {
            while (results.next()) {

                Long questionId = results.getLong("question_id");
                if (!questionAnswers.containsKey(questionId)) {
                    List<Long> answers = new ArrayList<>();
                    questionAnswers.put(questionId, answers);
                }
                Long userAnswer = results.getLong("answer_id");
                if (!questionAnswers.get(questionId).contains(userAnswer))
                    questionAnswers.get(questionId).add(userAnswer);

            }

            for (Result result : quizResults.get()) {
                result.setProvidedAnswer(questionAnswers.get(result.getQuestionId()));
            }
        } catch (SQLException e) {
            logger.warning("Query failed:" + e.getMessage());
            return null;
        }


        return null;





    }

    @Override
    protected boolean initialize() {

        this.st =createStatement(this.getDbConnection());

        return selectSchema(this.usedSchema, st);
    }


    private ResultSet executeQuery(Statement s, String sql) {
        try {
            return s.executeQuery(sql);
        } catch (SQLException e) {
            logger.warning(
                    "Query ws not executed! " + e.getMessage() + " Error Code: " + e.getErrorCode());
        }
        return null;
    }
}
