package org.palladiosimulator.generator.fluent_api.system.apiControlFlowInterfaces;

public interface ISystem extends ISystemAddition {

    /**
     * Defines the name of the org.palladiosimulator.generator.fluent_api.system.
     *
     * @param name
     * @return this org.palladiosimulator.generator.fluent_api.system
     */
    ISystem withName(String name);
}
