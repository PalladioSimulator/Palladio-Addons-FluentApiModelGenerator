package shared.structure;

import java.util.Objects;

import org.eclipse.emf.cdo.CDOObject;

/***
 * This class provides the general infrastructure of a Palladio Model Entity.
 *
 * @author Florian Krone
 *
 */
public abstract class Entity {

    protected String name;

    /**
     * Defines the <i>unique</i> name of this current entity. Once created entities are referenced
     * by this name. Only entities with a unique name can be fetched from the model.
     *
     * @param name
     * @return this current entity in the making
     */
    public Entity withName(final String name) {
        Objects.requireNonNull(name, "name must not be null");
        this.name = name;
        return this;
    }

    /**
     * Turns the entity in the making into the finished entity.
     * 
     * @return the finished entity
     */
    protected abstract CDOObject build();

}
