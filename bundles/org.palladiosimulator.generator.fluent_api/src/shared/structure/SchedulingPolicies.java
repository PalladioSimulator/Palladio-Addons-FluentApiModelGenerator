package shared.structure;

public enum SchedulingPolicies {
	PROCESS_SHARING("Processor Sharing"),
	FIRST_COME_FIRST_SERVE("First-Come-First-Serve"),
	DELAY("Delay");

	public final String policyName;
	
	SchedulingPolicies(String policyName) {
		this.policyName = policyName;
	}
}
