package de.hdmstuttgart.java_learn_app.ui.challenge;

public interface IChallenge {

    /**
     * Check whether or not the submitted answer is right.
     * True: call onChallengeSolved()
     * false: call onChallengeFailed()
     */
    void onChallengeCheck();

    /**
     * If the challenge has been solved correctly, give the user
     * their rewards (exp, coins)
     */
    void onChallengeSolved();

    /**
     * If the challenge has not been solved correctly, increase
     * the failCount of the current session and either show them
     * the right answer (QuizChallenge) or let them try again (CodeChallenge)
     */
    void onChallengeFailed();

    /**
     * Add click listener to the button that will load the next challenge, so the user
     * can progress further.
     */
    void onChallengeNext();

}
