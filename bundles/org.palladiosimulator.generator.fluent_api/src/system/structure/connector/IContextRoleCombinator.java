package system.structure.connector;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;

public interface IContextRoleCombinator<T, U> {
    public U combineContextAndRole(AssemblyContext context, T role);
}
