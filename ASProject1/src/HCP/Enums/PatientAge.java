package HCP.Enums;

public enum PatientAge {
    CHILD("C", "C"),
    ADULT("A", "A");

    public final String loggerString;
    public final String hcpGuiString;


    PatientAge(String loggerString, String hcpGuiString) {
        this.loggerString = loggerString;
        this.hcpGuiString = hcpGuiString;
    }
}
