package org.palladiosimulator.generator.fluent.resourceenvironment.structure;

import org.palladiosimulator.generator.fluent.shared.structure.Entity;

/**
 * This class provides the general infrastructure of a Palladio Model Entity.
 * Every <code>ResourceEntity</code> belongs to a
 * <code>ResourceEnvironment</code> and has a name.
 *
 * @author Florian Krone
 */
public abstract class ResourceEntity extends Entity {
    protected ResourceEnvironmentCreator resourceCreator;
}
