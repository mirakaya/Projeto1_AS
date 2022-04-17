package HCP.Monitors.CCH;

import HCP.Enums.CallCenterCall;
import HCP.Enums.PatientAge;

public class Call {
    public static final Call EXIT_CALL = new Call(CallCenterCall.EXIT);
    private final CallCenterCall type;
    private final PatientAge optionalAge;

    public Call(CallCenterCall type) {
        this.type = type;
        this.optionalAge = null;
    }

    public Call(CallCenterCall type, PatientAge age) {
        this.type = type;
        this.optionalAge = age;
    }

    public CallCenterCall getType() {
        return type;
    }

    public PatientAge getAge() {
        return optionalAge;
    }
}
