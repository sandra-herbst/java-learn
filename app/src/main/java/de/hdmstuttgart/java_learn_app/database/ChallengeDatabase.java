package de.hdmstuttgart.java_learn_app.database;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.concurrent.Executor;

import de.hdmstuttgart.java_learn_app.database.dao.CodeChallengeDao;
import de.hdmstuttgart.java_learn_app.database.dao.QuizChallengeDao;
import de.hdmstuttgart.java_learn_app.database.dao.RewardDao;
import de.hdmstuttgart.java_learn_app.database.dao.SubtopicDao;
import de.hdmstuttgart.java_learn_app.database.dao.TopicDao;
import de.hdmstuttgart.java_learn_app.database.entity.CodeChallengeEntity;
import de.hdmstuttgart.java_learn_app.database.entity.QuizChallengeEntity;
import de.hdmstuttgart.java_learn_app.database.entity.RewardEntity;
import de.hdmstuttgart.java_learn_app.database.entity.SubtopicEntity;
import de.hdmstuttgart.java_learn_app.database.entity.TopicEntity;

@Database(entities = {TopicEntity.class, SubtopicEntity.class, CodeChallengeEntity.class, QuizChallengeEntity.class, RewardEntity.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class ChallengeDatabase extends RoomDatabase {

    public abstract CodeChallengeDao codeChallengeDao();
    public abstract QuizChallengeDao quizChallengeDao();
    public abstract SubtopicDao subtopicDao();
    public abstract TopicDao topicDao();
    
    public abstract RewardDao rewardDao();

    private static  ChallengeDatabase INSTANCE;

    public static synchronized ChallengeDatabase getDBInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, ChallengeDatabase.class, "logicaeDB")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(roomCallback)
                    .build();
        }
        return INSTANCE;
    }

    /**
     * Callback for populating the database if the application has been opened for the first time.
     */
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new ThreadExecutor().execute(() -> {

                INSTANCE.topicDao().insert(new TopicEntity(0,"Introduction", "An introduction to the programming language java and its datatypes.", ""));
                INSTANCE.topicDao().insert(new TopicEntity(1,"Topic 2", "This is topic 2", ""));
                INSTANCE.topicDao().insert(new TopicEntity(2,"Topic 3", "This is topic 3", ""));
                INSTANCE.topicDao().insert(new TopicEntity(3,"Topic 4", "This is topic 4", ""));
                INSTANCE.topicDao().insert(new TopicEntity(4,"Topic 5", "This is topic 5", ""));
                INSTANCE.topicDao().insert(new TopicEntity(5,"Topic 6", "This is topic 6", ""));

                INSTANCE.subtopicDao().insert(new SubtopicEntity(0, 0, "Variables and Datatypes", "", false));
                INSTANCE.subtopicDao().insert(new SubtopicEntity(1, 1, "Subtopic 2", "", false));
                INSTANCE.subtopicDao().insert(new SubtopicEntity(2, 2, "Subtopic 3", "", false));
                INSTANCE.subtopicDao().insert(new SubtopicEntity(3, 3, "Subtopic 4", "", false));
                INSTANCE.subtopicDao().insert(new SubtopicEntity(4, 3, "Subtopic 5", "", false));
                INSTANCE.subtopicDao().insert(new SubtopicEntity(5, 4, "Subtopic 6", "", false));
                INSTANCE.subtopicDao().insert(new SubtopicEntity(6, 5, "Subtopic 7", "", false));
                INSTANCE.subtopicDao().insert(new SubtopicEntity(7, 6, "Subtopic 8", "", false));

                INSTANCE.codeChallengeDao().insert(new CodeChallengeEntity(0, 0, 0,"Introduction to Java",
                        "Java is a popular programming language, created in 1995. Java works on different platforms and " +
                        "due to javas object orientation it is a very good language to start out with. In Java, every file has the .java extension. The name of the file also determines the name of the class of your " +
                        "application. That means if your file is called \"Challenge.java\", then the file has to declare the class like so:\n\n" +
                        "public class Challenge {\n" +
                        "...\n" +
                        "}\n\n" +
                        "For now, you don't have to understand the rest of the code, it will be explained as we go along your programming journey." +
                        "Firstly, lets try to give the application some data! For example, you can save the current experience of a player. In the area below, type in:\n\n" +
                        "int experience = 5;\nreturn experience;\n\n" +
                        "With that, we declare a variable named experience of datatype number and set it to 5. Lastly, we return the value. We will discuss the importance of \"return\" in a later topic!"
                        , "5", "calculate", "int", "public", 2, false));

                INSTANCE.codeChallengeDao().insert(new CodeChallengeEntity(1, 0, 2,"Code Challenge 2", "Description of code challenge 2, result 7", "7", "calculate", "int", "private", 10, false));
                INSTANCE.codeChallengeDao().insert(new CodeChallengeEntity(2, 0, 4,"Code Challenge 3", "Description of code challenge 3, result 9", "9", "calculate", "int", "private", 20, false));
                INSTANCE.codeChallengeDao().insert(new CodeChallengeEntity(3, 1, 0,"Code Challenge 4", "Description of code challenge 4, result 10", "10", "calculate", "int", "private", 5, false));
                INSTANCE.codeChallengeDao().insert(new CodeChallengeEntity(4, 1, 2,"Code Challenge 5", "Description of code challenge 5, result 2", "2", "calculate", "int", "private", 11, false));
                INSTANCE.codeChallengeDao().insert(new CodeChallengeEntity(5, 2, 1,"Code Challenge 6", "Description of code challenge 6, result 1", "1", "calculate", "int", "private", 2, false));

                ArrayList<String> answers = new ArrayList<>();
                answers.add("a");
                answers.add("b");
                answers.add("c");
                answers.add("d");

                INSTANCE.quizChallengeDao().insert(new QuizChallengeEntity(0, 0, 1,"Quiz Challenge 1", "Description of quiz challenge 1, result a", 0, answers, 2, false));
                INSTANCE.quizChallengeDao().insert(new QuizChallengeEntity(1, 0, 3,"Quiz Challenge 2", "Description of quiz challenge 2, result b", 1, answers, 4, false));
                INSTANCE.quizChallengeDao().insert(new QuizChallengeEntity(2, 1, 1,"Quiz Challenge 3", "Description of quiz challenge 3, result b", 1, answers, 10, false));
                INSTANCE.quizChallengeDao().insert(new QuizChallengeEntity(3, 1, 3,"Quiz Challenge 4", "Description of quiz challenge 4, result c", 2, answers, 11, false));
                INSTANCE.quizChallengeDao().insert(new QuizChallengeEntity(4, 2, 0,"Quiz Challenge 5", "Description of quiz challenge 5, result d", 3, answers, 4, false));
                INSTANCE.quizChallengeDao().insert(new QuizChallengeEntity(5, 2, 3,"Quiz Challenge 6", "Description of quiz challenge 6, result d", 3, answers, 5, false));

                INSTANCE.rewardDao().insert(new RewardEntity(0, "Reward 1", "", "Description of reward 1", 100, false));
                INSTANCE.rewardDao().insert(new RewardEntity(1, "Reward 2", "", "Description of reward 2", 10, false));
                INSTANCE.rewardDao().insert(new RewardEntity(2, "Reward 3", "", "Description of reward 3", 6, false));
                INSTANCE.rewardDao().insert(new RewardEntity(3, "Reward 4", "", "Description of reward 4", 300, false));
                INSTANCE.rewardDao().insert(new RewardEntity(4, "Reward 5", "", "Description of reward 5", 700, false));
                INSTANCE.rewardDao().insert(new RewardEntity(5, "Reward 6", "", "Description of reward 6", 999, false));
            });

            Log.d("ChallengeDatabase", "onCreate: Database has been created for the first time");
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            Log.d("ChallengeDatabase", "onCreate: Database has been opened");
        }
    };
}
