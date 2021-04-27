package org.palladiosimulator.generator.fluent_api.component.repositoryStructure.internals;

/**
 * Represents built-in failure types available in the repository, i.e.
 * '<em><b>HARDWARE_CPU</b></em>', '<em><b>HARDWARE_HDD</b></em>',
 * '<em><b>HARDWARE_DELAY</b></em>', '<em><b>NETWORK_LAN</b></em>',
 * '<em><b>SOFTWARE</b></em>'.
 *
 * @author Louisa Lambrecht
 */
public enum Failure {
    /**
     * hardware induced failure type (CPU)
     */
    HARDWARE_CPU,
    /**
     * hardware induced failure type (HDD)
     */
    HARDWARE_HDD,
    /**
     * hardware induced failure type (DELAY)
     */
    HARDWARE_DELAY,
    /**
     * network induced failure type (LAN)
     */
    NETWORK_LAN,
    /**
     * software induced failure type
     */
    SOFTWARE
}
