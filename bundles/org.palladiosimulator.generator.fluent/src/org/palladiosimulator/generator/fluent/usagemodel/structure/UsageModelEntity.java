package org.palladiosimulator.generator.fluent.usagemodel.structure;

import org.palladiosimulator.generator.fluent.shared.structure.Entity;

/**
 * This class provides the general infrastructure of a Palladio Model Entity.
 * Every UsageModelEntity belongs to a usage model.
 *
 * @author Eva-Maria Neumann
 */
public abstract class UsageModelEntity extends Entity {
    protected UsageModelCreator usageModelCreator;
}
