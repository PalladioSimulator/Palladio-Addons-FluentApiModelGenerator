package org.palladiosimulator.generator.fluent_api.system.structure;

import org.palladiosimulator.generator.fluent_api.shared.structure.Entity;

/**
 * This class provides the general infrastructure of a Palladio Model Entity.
 * Every SystemEntity belongs to a org.palladiosimulator.generator.fluent_api.system and has a name.
 *
 * @author Florian Krone
 */
public abstract class SystemEntity extends Entity {
    protected SystemCreator system;
}
