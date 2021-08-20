package org.palladiosimulator.generator.fluent.shared.validate;

import org.eclipse.emf.ecore.EObject;

public interface IModelValidator {
    /**
     * Validates an EObject. A name can be specified to improve a potential diagnostic in a specific
     * implementation.
     *
     * @param eObject
     *            the object to validate
     * @param name
     *            the name of the object
     * @return true if the validation was ok, false otherwise.
     */
    boolean validate(EObject eObject, String name);
}
