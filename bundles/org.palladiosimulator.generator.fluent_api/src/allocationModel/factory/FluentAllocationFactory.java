package allocationModel.factory;


import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.palladiosimulator.pcm.allocation.Allocation;

import allocationModel.apiControlFlowInterfaces.IAllocation;
import allocationModel.structure.AllocationContextCreator;
import allocationModel.structure.AllocationCreator;
import shared.util.ModelLoader;
import shared.validate.IModelValidator;
import shared.validate.ModelValidator;

/**
 * This class provides all the methods to create an 
 * {@link org.palladiosimulator.pcm.allocation.Allocation Allocation} and create
 * Entities that are added to this Allocation. 
 * Characteristics of the entities are specified by method chaining.<br>
 * <p>
 * Start creating an allocation like this:<br>
 * <code>
 * FluentAllocationFactory create = new FluentAllocationFactory();<br>
 * Allocation allocation = create.newAllocation()<br>
 * 		<p style="margin-left:40px">//define System and ResourceEnvironment and add AllocationContexts</p>
 * 		<p style="margin-left:40px">.createAllocationNow();</p>
 * </code>
 * </p>
 * @author Florian Krone
 *
 */
public class FluentAllocationFactory {
	private AllocationCreator allocationCreator;
	
	/**
	 * Start the creation of an {@link org.palladiosimulator.pcm.allocation.Allocation Allocation}.
	 * 
	 * @return the <code>Allocation</code> in the making
	 * 
	 * @see org.palladiosimulator.pcm.allocation.Allocation
	 */
	public IAllocation newAllocation() {
		EcorePlugin.ExtensionProcessor.process(null);
		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		logger.setLevel(Level.ALL);
		IModelValidator validator = new ModelValidator(logger);
		allocationCreator = new AllocationCreator(validator);
		return allocationCreator;
	}
	
	/**
	 * Start the creation of an {@link org.palladiosimulator.pcm.allocation.AllocationContext AllocationContext}.
	 * 
	 * @return the <code>AllocationContext</code> in the making
	 * 
	 * @see org.palladiosimulator.pcm.allocation.AllocationContext
	 */
	public AllocationContextCreator newAllocationContext() {
		return new AllocationContextCreator(allocationCreator);
	}
}
