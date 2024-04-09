package de.hdmstuttgart.java_learn_app.ui.challenge;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.LinkedList;
import java.util.List;

import de.hdmstuttgart.java_learn_app.database.ThreadExecutor;
import de.hdmstuttgart.java_learn_app.database.entity.CodeChallengeEntity;
import de.hdmstuttgart.java_learn_app.database.entity.QuizChallengeEntity;
import de.hdmstuttgart.java_learn_app.database.entity.SubtopicEntity;
import de.hdmstuttgart.java_learn_app.database.repository.ChallengeRepository;

public class ChallengeViewModel extends AndroidViewModel {

    private ChallengeRepository repository;

    private static final String TAG = "ChallengeViewModel";
    private LinkedList<CodeChallengeEntity> codeChallenges = new LinkedList<>();
    private LinkedList<QuizChallengeEntity> quizChallenges = new LinkedList<>();
    private SubtopicEntity subtopicOfChallenges;

    private int totalChallengeCount;
    private int totalFailCount;
    private int experience = 0;
    private int coins = 0;

    public ChallengeViewModel(@NonNull Application application, int subtopicId) {
        super(application);
        repository = new ChallengeRepository(application , new ThreadExecutor(), subtopicId);

        List<CodeChallengeEntity> codeChallengeList = repository.getAllCodeChallengesOfSubtopic();
        List<QuizChallengeEntity> quizChallengeList = repository.getAllQuizChallengesOfSubtopic();
        generateChallengeList(codeChallengeList, quizChallengeList);

        subtopicOfChallenges = repository.getSubtopicOfChallenges();

        totalChallengeCount += codeChallenges.size();
        totalChallengeCount += quizChallenges.size();
    }

    /**
     * Generate Linkedlist of Code and Quiz Challenges.
     * If all challenges have been completed, readd all challenges back, so the user can redo them.
     * If there is at least one challenge not completed, add all challenges that are not completed yet.
     *
     * @param codeList of all code Challenges.
     * @param quizList of all quiz Challenges.
     */
    private void generateChallengeList(List<CodeChallengeEntity> codeList, List <QuizChallengeEntity> quizList){
        if (allChallengesCompleted(codeList, quizList)){
            codeChallenges.addAll(codeList);
            quizChallenges.addAll(quizList);

        } else {
            for (CodeChallengeEntity codeChallenge: codeList) {
                if (!codeChallenge.isCompleted) codeChallenges.add(codeChallenge);
            }

            for (QuizChallengeEntity quizChallenge: quizList) {
                if (!quizChallenge.isCompleted) quizChallenges.add(quizChallenge);
            }
        }
    }

    /**
     * Check whether or not all challenges have been completed yet.
     *
     * @param codeList of all code Challenges.
     * @param quizList of all quiz Challenges.
     * @return if all challenges have been completed.
     */
    private boolean allChallengesCompleted(List<CodeChallengeEntity> codeList, List <QuizChallengeEntity> quizList){
        for (CodeChallengeEntity codeChallenge : codeList) {
            if (!codeChallenge.isCompleted) return false;
        }

        for (QuizChallengeEntity quizChallenge : quizList) {
            if (!quizChallenge.isCompleted) return false;
        }

        return true;
    }

    public void updateCodeChallenge(CodeChallengeEntity codeChallenge){
        repository.updateCodeChallenge(codeChallenge);
    }

    public void updateQuizChallenge(QuizChallengeEntity quizChallenge){
        repository.updateQuizChallenge(quizChallenge);
    }

    public void updateSubtopic(SubtopicEntity subtopicEntity){
        repository.updateSubtopic(subtopicEntity);
    }

    public void updateSubtopicCompleted(){
        if (!subtopicOfChallenges.isCompleted){
            subtopicOfChallenges.isCompleted = true;
            updateSubtopic(subtopicOfChallenges);
        } else {
            Log.d(TAG, "updateSubtopicCompleted: Subtopic already has been completed.");
        }
    }

   public CodeChallengeEntity getFirstCodeChallenge(){
        return codeChallenges.getFirst();
    }

    public QuizChallengeEntity getFirstQuizChallenge(){
        return quizChallenges.getFirst();
    }

    public void removeFirstCodeChallenge(){
        codeChallenges.removeFirst();
    }

    public void removeFirstQuizChallenge(){
        quizChallenges.removeFirst();
    }

    /**
     * Check which kind of Challenge is up next. [CODE, QUIZ, NONE]
     * @return enum challenge type.
     */
    public ChallengeType getNextChallengeType(){
        if (quizChallenges.size() == 0 && codeChallenges.size() == 0) return ChallengeType.END;
        if (quizChallenges.size() == 0 && codeChallenges.size() != 0) return ChallengeType.CODE;
        if (quizChallenges.size() != 0 && codeChallenges.size() == 0) return ChallengeType.QUIZ;
        if (quizChallenges.get(0).orderNumber > codeChallenges.get(0).orderNumber) return ChallengeType.CODE;
        return ChallengeType.QUIZ;
    }

    public void addExperience(int experience) {
        this.experience += experience;
        Log.d(TAG, "addExperience: " + experience);
    }

    public void addCoins(int coins) {
        this.coins += coins;
        Log.e(TAG, "addCoins: " + coins + " , current:" + this.coins);
    }

    public int getTotalChallengeCount() {
        return totalChallengeCount;
    }

    public int getCurrentChallengePosition(){
        return ((totalChallengeCount - codeChallenges.size()) - quizChallenges.size()) + 1;
    }

    public void increaseTotalFailCount() {
        this.totalFailCount += 1;
    }

    public int getTotalFailCount() {
        return totalFailCount;
    }

    public int getExperience() {
        return experience;
    }

    public int getCoins() {
        return coins;
    }
}
