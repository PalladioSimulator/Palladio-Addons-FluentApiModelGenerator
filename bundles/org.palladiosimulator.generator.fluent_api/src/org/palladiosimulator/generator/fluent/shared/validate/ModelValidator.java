package org.palladiosimulator.generator.fluent.shared.validate;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.Diagnostician;

public class ModelValidator implements IModelValidator {
    private final Logger logger;

    public ModelValidator(final Logger logger) {
        this.logger = logger;
    }

    /**
     * Validates an EObject using a Diagnostician. If the validation is not ok, the
     * result is printed to the console. A name can be specified to enhance the
     * console output.
     *
     * @param eObject the object to validate
     * @param name    the name of the object
     * @return true if the validation was ok, false otherwise.
     * @see org.eclipse.emf.ecore.util.Diagnostician
     */
    @Override
    public boolean validate(final EObject eObject, final String name) {
        final Diagnostic diagnostic = Diagnostician.INSTANCE.validate(eObject);
        switch (diagnostic.getSeverity()) {
        case Diagnostic.OK:
            return true;
        case Diagnostic.INFO:
            logResult(diagnostic, name, Level.INFO);
            return true;
        case Diagnostic.WARNING:
            logResult(diagnostic, name, Level.WARNING);
            return false;
        case Diagnostic.ERROR:
            logResult(diagnostic, name, Level.SEVERE);
            return false;
        case Diagnostic.CANCEL:
            logger.severe("Validation was canceled");
            return false;
        default:
            logger.severe("Validation returned unexpected result");
            return false;
        }
    }

    private void logResult(final Diagnostic diagnostic, final String name, final Level logLevel) {
        final StringBuilder builder = new StringBuilder();
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
