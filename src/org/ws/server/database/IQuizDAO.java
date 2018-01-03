package org.ws.server.database;

import org.ws.communication.job.Question;

import java.util.List;
import java.util.Map;

public interface IQuizDAO {


    public List<Long> getQuizes();
    public List<Question> getQuiz(Long quizId);
    public Map<Long,Long> getCorrectAnswers(Long quizId);
    public boolean persistScore(Long userId,Long quizId,Integer score);
    public Map<Long,Long> getUserScores(Long userId);

}
