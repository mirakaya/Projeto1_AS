package HCP.gui;

/**
 * All Possible colors for the interface elements
 * representing the entities in the simulation.
 */
public enum PatientColors {
    NONE(""),
    GRAY("Gray"),
    BLUE("Blue"),
    YELLOW("Yellow"),
    RED("Red");

    public final String color;

    PatientColors(String color) {
        this.color = color;
    }
}
