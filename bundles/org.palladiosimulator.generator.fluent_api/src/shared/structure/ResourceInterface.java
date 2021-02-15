package shared.structure;

/**
 * Represents built-in resource interfaces available in the resource repository, i.e.
 * '<em><b>CPU</b></em>', '<em><b>HDD</b></em>'.
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

    private final String resourceName;

    ResourceInterface(final String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceName() {
        return resourceName;
    }
}