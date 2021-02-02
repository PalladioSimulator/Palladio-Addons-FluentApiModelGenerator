package shared.validate;

import org.eclipse.emf.ecore.EObject;

public interface IModelValidator {
	void validate(EObject eObject, String name);
}
