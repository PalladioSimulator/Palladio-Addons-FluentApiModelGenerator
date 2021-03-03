package shared.structure;

/**
 * Represents the built in scheduling policies, i.e. '<em><b>Process Sharing</b></em>,
 * '<em><b>FCFS</b></em>, '<em><b>Delay</b></em>.
 * 
 * @author Florian Krone
 *
 */
public enum SchedulingPolicies {
    /**
     * Process sharing scheduling policy
     */
    PROCESS_SHARING("Processor Sharing"),
    /**
     * First come first serve scheduling policy
     */
    FIRST_COME_FIRST_SERVE("First-Come-First-Serve"),
    /**
     * Delay scheduling policy
     */
    DELAY("Delay");

    private final String policyName;

    SchedulingPolicies(final String policyName) {
        this.policyName = policyName;
    }

    /**
     * Gets the name of the policy
     * 
     * @return the name
     */
    public String getPolicyName() {
        return policyName;
    }
}
