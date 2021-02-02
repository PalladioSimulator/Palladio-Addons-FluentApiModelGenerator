package shared.validate;

import java.util.HashMap;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;

public class ModelValidator implements IModelValidator {
	private EValidator validator;
	private Logger logger;
	
	public ModelValidator(EValidator validator, Logger logger) {
		this.validator = validator;
		this.logger = logger;
	}
	
	public void validate(EObject eObject, String name) {
		BasicDiagnostic diagnosticChain = new BasicDiagnostic();
		if (!validator.validate(eObject, diagnosticChain, new HashMap<>())) {
			StringBuilder builder = new StringBuilder();
			builder.append("validation failed");
			if (name != null) {
				builder.append(" for model \"");
				builder.append(name);
				builder.append("\"");
			}
			if (!diagnosticChain.getChildren().isEmpty()) {
				builder.append(", reason:\n");
				diagnosticChain.getChildren().forEach(x -> builder.append(x.toString() + "\n"));
			}
			logger.severe(builder.toString().trim());
		}
	}
}
