package org.palladiosimulator.generator.fluent.resourceenvironment.api;

public interface IResourceEnvironment extends IResourceEnvironmentAddition {

    /**
     * Defines the name of the resource environment.
     *
     * @param name
     * @return this resource environment
     */
    IResourceEnvironment withName(String name);
}
