package systemModel.structure;

import shared.structure.Entity;

/**
 * This class provides the general infrastructure of a Palladio Model Entity.
 * Every SystemEntity belongs to a system and has a name.
 * 
 * @author Florian Krone
 *
 */
public abstract class SystemEntity extends Entity {
	protected SystemCreator system;
}
