package shared.structure;

public enum SchedulingPolicies {
    PROCESS_SHARING("Processor Sharing"), FIRST_COME_FIRST_SERVE("First-Come-First-Serve"), DELAY("Delay");

    private final String policyName;

    SchedulingPolicies(final String policyName) {
        this.policyName = policyName;
    }

    public String getPolicyName() {
        return policyName;
    }
}
