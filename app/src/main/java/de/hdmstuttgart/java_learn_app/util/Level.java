package de.hdmstuttgart.java_learn_app.util;

public enum Level {

    BEGINNER("BEGINNER" ,0 , 500),
    STUDENT("STUDENT" ,501, 1500),
    BACHELOR("BACHELOR GRADUATE" ,1501 , 3000),
    JUNIOR("JUNIOR DEVELOPER" ,3001, 5000),
    SENIOR("SENIOR DEVELOPER" ,5001, 10000),
    LEAD("LEAD DEVELOPER",10000, Integer.MAX_VALUE);

    private final String TITLE;
    private final int EXP_START, EXP_END;

    Level(String title, int exp_start, int exp_end) {
        TITLE = title;
        EXP_START = exp_start;
        EXP_END = exp_end;
    }

    public static Level getLevel(int experience){
        for (Level level: Level.values()) {
            if (level.EXP_START <= experience && level.EXP_END >= experience){
                return level;
            }
        }
        return null;
    }

    public int getProgressPercentage(int experience){
        int totalLevelExp = EXP_END - EXP_START;
        int totalUserExp = experience - EXP_START;

        return (int) (((double) totalUserExp / (double) totalLevelExp)*100);
    }

    public String getTitle() {
        return TITLE;
    }

    public int getEXP_END() {
        return EXP_END;
    }
}
