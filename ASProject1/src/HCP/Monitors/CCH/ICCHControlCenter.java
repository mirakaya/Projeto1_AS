package HCP.Monitors.CCH;

public interface ICCHControlCenter {
    /**
     * Method the call center calls to wait for
     * calls from patients that a certain area is now
     * available for use or that all patients have been
     * treated.
     */
    CCHCall nextCall();
}
