package shared.structure;

/**
 * Represents built-in resource interfaces available in the resource repository,
 * i.e. '<em><b>CPU</b></em>', '<em><b>HDD</b></em>'.
 * 
 * @author Louisa Lambrecht
 */
public enum ResourceInterface {
	/**
	 * CPU resource interface
	 */
	CPU("CpuInterface"),
	/**
	 * HDD resource interface
	 */
	HDD("HddInterface");
	
	public final String resourceName;

	ResourceInterface(String resourceName) {
		this.resourceName = resourceName;
	}
}