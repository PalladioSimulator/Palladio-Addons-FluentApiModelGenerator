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
     * Validates an EObject using a Diagnostician. If the validation is not ok, the result is
     * printed to the console. A name can be specified to enhance the console output.
     *
     * @param eObject
     *            the object to validate
     * @param name
     *            the name of the object
     * @return true if the validation was ok, false otherwise.
     * @see org.eclipse.emf.ecore.util.Diagnostician
     */
    @Override
    public boolean validate(final EObject eObject, final String name) {
        final Diagnostic diagnostic = Diagnostician.INSTANCE.validate(eObject);
        return switch (diagnostic.getSeverity()) {
            case Diagnostic.OK -> true;
            case Diagnostic.INFO -> {
                this.logResult(diagnostic, name, Level.INFO);
                yield true;
            }
            case Diagnostic.WARNING -> {
                this.logResult(diagnostic, name, Level.WARNING);
                yield false;
            }
            case Diagnostic.ERROR -> {
                this.logResult(diagnostic, name, Level.SEVERE);
                yield false;
            }
            case Diagnostic.CANCEL -> {
                this.logger.severe("Validation was canceled");
                yield false;
            }
            default -> {
                this.logger.severe("Validation returned unexpected result");
                yield false;
            }
        };
    }

    private void logResult(final Diagnostic diagnostic, final String name, final Level logLevel) {
        final StringBuilder builder = new StringBuilder();
        if (name == null) {
            builder.append(diagnostic.getMessage());
        } else {
            builder.append("Validation for model \"");
            builder.append(name);
            builder.append("\"");
        }
        builder.append(":\n");
        diagnostic.getChildren()
            .forEach(x -> builder.append(x.toString() + "\n"));
        this.logger.log(logLevel, builder.toString()
            .trim());
    }
}
