package shared.validate;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.Diagnostician;


public class ModelValidator implements IModelValidator {
	private Logger logger;
	
	public ModelValidator( Logger logger) {
		this.logger = logger;
	}
	
	public void validate(EObject eObject, String name) {
		Diagnostic diagnostic = Diagnostician.INSTANCE.validate(eObject);
		switch (diagnostic.getSeverity()) {
		case Diagnostic.OK: break;
		case Diagnostic.INFO: logResult(diagnostic, name, Level.INFO); break;
		case Diagnostic.WARNING: logResult(diagnostic, name, Level.WARNING); break;
		case Diagnostic.ERROR: logResult(diagnostic, name, Level.SEVERE); break;
		case Diagnostic.CANCEL: logger.severe("Validation was canceled"); break;
		default: logger.severe("Validation returned unexpected result"); break;
		}
	}
	
	private void logResult(Diagnostic diagnostic, String name, Level logLevel) {
		StringBuilder builder = new StringBuilder();
		if (name != null) {
			builder.append("Validation for model \"");
			builder.append(name);
			builder.append("\"");
		} else {
			builder.append(diagnostic.getMessage());
		}
		builder.append(":\n");
		diagnostic.getChildren().forEach(x -> builder.append(x.toString() + "\n"));
		logger.log(logLevel, builder.toString().trim());
	}
}
