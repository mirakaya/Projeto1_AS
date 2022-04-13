package HCP.Enums;

import java.util.Random;

public enum PatientEvaluation {
    NONE,
    BLUE,
    YELLOW,
    RED;

    private final static Random generator = new Random();
    private final static PatientEvaluation[] values = PatientEvaluation.values();
    private final static int length = values.length;

    public static PatientEvaluation random() {
        return values[generator.nextInt(1, length)];
    }
}
