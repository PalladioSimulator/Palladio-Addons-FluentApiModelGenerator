package org.palladiosimulator.generator.fluent.system.structure.connector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;

/**
 * TODO
 */
public interface IContextRoleCombinator<T, U> {
    U combineContextAndRole(AssemblyContext context, T role);
}
