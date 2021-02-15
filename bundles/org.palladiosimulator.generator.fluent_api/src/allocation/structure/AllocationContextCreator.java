package allocation.structure;

import org.palladiosimulator.pcm.allocation.AllocationContext;
import org.palladiosimulator.pcm.allocation.AllocationFactory;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.resourceenvironment.ResourceContainer;

/**
 * This class constructs an {@link org.palladiosimulator.pcm.allocation.AllocationContext
 * AllocationContext}. Either an {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
 * AssemblyContext} or an {@link org.palladiosimulator.pcm.core.composition.EventChannel
 * EventChannel} is allocated to a
 * {@link org.palladiosimulator.pcm.resourceenvironment.ResourceContainer ResourceContainer}.
 *
 * @author Florian Krone
 *
 * @see org.palladiosimulator.pcm.allocation.AllocationContext
 */
public class AllocationContextCreator extends AllocationEntity {
    private AssemblyContext assemblyContext;
    private EventChannel eventChannel;
    private ResourceContainer resourceContainer;

    public AllocationContextCreator(final AllocationCreator allocationCreator) {
        this.allocationCreator = allocationCreator;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} that is allocated to the
     * {@link org.palladiosimulator.pcm.resourceenvironment.ResourceContainer ResourceContainer}.
     *
     * @param context
     * @return this <code>AllocationContext</code>
     *
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     */
    public AllocationContextCreator withAssemblyContext(final AssemblyContext context) {
        this.assemblyContext = context;
        return this;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} that is allocated to the
     * {@link org.palladiosimulator.pcm.resourceenvironment.ResourceContainer ResourceContainer}.
     * The <code>System</code> used for the allocation is searched for an
     * <code>AssemblyContext</code> with the given name.
     *
     * @param name
     * @return this <code>AllocationContext</code>
     *
     * @see org.palladiosimulator.pcm.core.composition.AssemblyContext
     */
    public AllocationContextCreator withAssemblyContext(final String name) {
        return this.withAssemblyContext(this.allocationCreator.getAssemblyContextByName(name));
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.EventChannel EventChannel} that
     * is allocated to the {@link org.palladiosimulator.pcm.resourceenvironment.ResourceContainer
     * ResourceContainer}.
     *
     * @param context
     * @return this <code>AllocationContext</code>
     *
     * @see org.palladiosimulator.pcm.core.composition.EventChannel
     */
    public AllocationContextCreator withEventChannel(final EventChannel channel) {
        this.eventChannel = channel;
        return this;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.core.composition.EventChannel EventChannel} that
     * is allocated to the {@link org.palladiosimulator.pcm.resourceenvironment.ResourceContainer
     * ResourceContainer}. The <code>System</code> used for the allocation is searched for an
     * <code>EventChannel</code> with the given name.
     *
     * @param name
     * @return this <code>AllocationContext</code>
     *
     * @see org.palladiosimulator.pcm.core.composition.EventChannel
     */
    public AllocationContextCreator withEventChannel(final String name) {
        return this.withEventChannel(this.allocationCreator.getEventChannelByName(name));
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.resourceenvironment.ResourceContainer
     * ResourceContainer}, the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} or {@link org.palladiosimulator.pcm.core.composition.EventChannel
     * EventChannel} is allocated to.
     *
     * @param container
     * @return this <code>AllocationContext</code>
     *
     * @see org.palladiosimulator.pcm.resourceenvironment.ResourceContainer
     */
    public AllocationContextCreator withResourceContainer(final ResourceContainer container) {
        this.resourceContainer = container;
        return this;
    }

    /**
     * Defines the {@link org.palladiosimulator.pcm.resourceenvironment.ResourceContainer
     * ResourceContainer}, the {@link org.palladiosimulator.pcm.core.composition.AssemblyContext
     * AssemblyContext} or {@link org.palladiosimulator.pcm.core.composition.EventChannel
     * EventChannel} is allocated to. The <code>ResourceEnvironment</code> used for the allocation
     * is searched for a <code>ResourceContainer</code> with the given name.
     *
     * @param container
     * @return this <code>AllocationContext</code>
     *
     * @see org.palladiosimulator.pcm.resourceenvironment.ResourceContainer
     */
    public AllocationContextCreator withResourceContainer(final String name) {
        return this.withResourceContainer(this.allocationCreator.getResourceContainerByName(name));
    }

    @Override
    protected AllocationContext build() {
        final AllocationContext context = AllocationFactory.eINSTANCE.createAllocationContext();
        if (this.name != null) {
            context.setEntityName(this.name);
        }
        context.setAssemblyContext_AllocationContext(this.assemblyContext);
        context.setEventChannel__AllocationContext(this.eventChannel);
        context.setResourceContainer_AllocationContext(this.resourceContainer);
        return context;
    }

    @Override
    public AllocationContextCreator withName(final String name) {
        return (AllocationContextCreator) super.withName(name);
    }

}
