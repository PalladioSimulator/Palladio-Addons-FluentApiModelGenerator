package allocation.structure;

import shared.structure.Entity;

/**
 * This class provides the general infrastructure of a Palladio Model Entity.
 * Every <code>AllocationEntity</code> belongs to an <code>Allocation</code> and
 * has a name.
 *
 * @author Florian Krone
 */
public abstract class AllocationEntity extends Entity {
    protected AllocationCreator allocationCreator;
}
