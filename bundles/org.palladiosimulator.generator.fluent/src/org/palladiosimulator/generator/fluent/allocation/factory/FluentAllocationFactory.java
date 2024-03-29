package org.palladiosimulator.generator.fluent.allocation.factory;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.palladiosimulator.generator.fluent.allocation.api.IAllocation;
import org.palladiosimulator.generator.fluent.allocation.structure.AllocationContextCreator;
import org.palladiosimulator.generator.fluent.allocation.structure.AllocationCreator;
import org.palladiosimulator.generator.fluent.shared.validate.IModelValidator;
import org.palladiosimulator.generator.fluent.shared.validate.ModelValidator;

/**
 * This class provides all the methods to create an
 * {@link org.palladiosimulator.pcm.allocation.Allocation Allocation} and create entities that are
 * added to this Allocation. Characteristics of the entities are specified by method chaining.<br>
 * <p>
 * Start creating an org.palladiosimulator.generator.fluent.allocation like this:<br>
 * <code>
 * FluentAllocationFactory create = new FluentAllocationFactory();<br>
 * Allocation org.palladiosimulator.generator.fluent.allocation = create.newAllocation()<br>
 * 		<p style=
"margin-left:40px">//define System and ResourceEnvironment and add AllocationContexts</p>
 * 		<p style="margin-left:40px">.createAllocationNow();</p>
 * </code>
 * </p>
 *
 * @author Florian Krone
 */
public class FluentAllocationFactory {
    private AllocationCreator allocationCreator;

    /**
     * Start the creation of an {@link org.palladiosimulator.pcm.allocation.Allocation Allocation}.
     *
     * @return the <code>Allocation</code> in the making
     * @see org.palladiosimulator.pcm.allocation.Allocation
     */
    public IAllocation newAllocation() {
        EcorePlugin.ExtensionProcessor.process(null);
        final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.setLevel(Level.ALL);
        final IModelValidator validator = new ModelValidator(logger);
        this.allocationCreator = new AllocationCreator(validator);
        return this.allocationCreator;
    }

    /**
     * Start the creation of an {@link org.palladiosimulator.pcm.allocation.AllocationContext
     * AllocationContext}.
     *
     * @return the <code>AllocationContext</code> in the making
     * @throws IllegalStateException
     *             if newAllocationContext() has not been called before
     * @see org.palladiosimulator.pcm.allocation.AllocationContext
     */
    public AllocationContextCreator newAllocationContext() throws IllegalStateException {
        if (this.allocationCreator == null) {
            throw new IllegalStateException("newAllocation() must have been called before");
        }
        return new AllocationContextCreator(this.allocationCreator);
    }
}
