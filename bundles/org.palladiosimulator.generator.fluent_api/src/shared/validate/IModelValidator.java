package shared.validate;

import org.eclipse.emf.ecore.EObject;

public interface IModelValidator {
    boolean validate(EObject eObject, String name);
}
