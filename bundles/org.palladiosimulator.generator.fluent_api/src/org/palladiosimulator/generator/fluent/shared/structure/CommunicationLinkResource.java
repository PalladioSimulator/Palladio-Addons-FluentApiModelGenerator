package org.palladiosimulator.generator.fluent.shared.structure;

/**
 * Represents built-in communication link resources available in the resource
 * repository, i.e. '<em><b>LAN</b></em>'.
 *
 * @author Louisa Lambrecht
 */
public enum CommunicationLinkResource {
    /**
     * LAN communication link resource
     */
    LAN("LAN");

    private final String resourceName;

    CommunicationLinkResource(final String resourceName) {
        this.resourceName = resourceName;
    }

    /**
     * Gets the name of the resource
     *
     * @return the name
     */
    public String getResourceName() {
        return resourceName;
    }
}
