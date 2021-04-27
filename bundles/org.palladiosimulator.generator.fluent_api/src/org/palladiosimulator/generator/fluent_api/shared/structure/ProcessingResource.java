package org.palladiosimulator.generator.fluent_api.shared.structure;

/**
 * Represents built-in processing resources available in the resource
 * repository, i.e. '<em><b>CPU</b></em>', '<em><b>HDD</b></em>',
 * '<em><b>DELAY</b></em>'.
 *
 * @author Louisa Lambrecht
 */
public enum ProcessingResource {
    /**
     * CPU processing resource
     */
    CPU("CPU"),
    /**
     * HDD processing resource
     */
    HDD("HDD"),
    /**
     * delay processing resource
     */
    DELAY("DELAY");

    private final String resourceName;

    ProcessingResource(final String resourceName) {
        this.resourceName = resourceName;
    }

    /**
     * Gets the name of the resource.
     *
     * @return the name
     */
    public String getResourceName() {
        return resourceName;
    }
}
