package resourceenvironment.structure;

import java.util.Objects;

import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentFactory;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.SchedulingPolicy;

import shared.structure.ProcessingResource;
import shared.structure.SchedulingPolicies;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification
 * ProcessingResourceSpecification}.
 *
 * @author Florian Krone
 * @see org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification
 */
public class ProcessingResourceSpecificationCreator {
    private double mttr;
    private double mttf;
    private int numberOfReplicas;
    private boolean isRequiredByContainer = false;
    private SchedulingPolicy schedulingPolicy;
    private ProcessingResourceType processingResourceType;
    private final ResourceEnvironmentCreator resourceCreator;
    private PCMRandomVariable processingRateVariable;

    public ProcessingResourceSpecificationCreator(final ResourceEnvironmentCreator resourceCreator) {
        this.resourceCreator = resourceCreator;
    }

    /**
     * Defines the Mttr of the
     * {@link org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification
     * ProcessingResourceSpecification}.
     *
     * @param mttr
     * @return this <code>ProcessingResourceSpecification</code>
     * @see org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification
     */
    public ProcessingResourceSpecificationCreator withMttr(final double mttr) {
        this.mttr = mttr;
        return this;
    }

    /**
     * Defines the Mttf of the
     * {@link org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification
     * ProcessingResourceSpecification}.
     *
     * @param mttf
     * @return this <code>ProcessingResourceSpecification</code>
     * @see org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification
     */
    public ProcessingResourceSpecificationCreator withMttf(final double mttf) {
        this.mttf = mttf;
        return this;
    }

    /**
     * Defines the number of replicas of the
     * {@link org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification
     * ProcessingResourceSpecification}.
     *
     * @param numberOfReplicas
     * @return this <code>ProcessingResourceSpecification</code>
     * @see org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification
     */
    public ProcessingResourceSpecificationCreator withNumberOfReplicas(final int numberOfReplicas) {
        this.numberOfReplicas = numberOfReplicas;
        return this;
    }

    /**
     * Sets the
     * {@link org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification
     * ProcessingResourceSpecification} to be required by the
     * {@link org.palladiosimulator.pcm.resourceenvironment.ResourceContainer
     * ResourceContainer}.
     *
     * @return this <code>ProcessingResourceSpecification</code>
     * @see org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification
     */
    public ProcessingResourceSpecificationCreator isRequiredByContainer() {
        isRequiredByContainer = true;
        return this;
    }

    /**
     * Defines the scheduling policy of the
     * {@link org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification
     * ProcessingResourceSpecification}.
     *
     * @param policy
     * @return this <code>ProcessingResourceSpecification</code>
     * @see org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification
     */
    public ProcessingResourceSpecificationCreator withSchedulingPolicy(final SchedulingPolicies policy) {
        schedulingPolicy = resourceCreator.getSchedulingPolicy(policy);
        return this;
    }

    /**
     * Defines the processing resource of the
     * {@link org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification
     * ProcessingResourceSpecification}.
     *
     * @param resource
     * @return this <code>ProcessingResourceSpecification</code>
     * @see org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification
     */
    public ProcessingResourceSpecificationCreator withProcessingResourceType(final ProcessingResource resource) {
        processingResourceType = resourceCreator.getProcessingResource(resource);
        return this;
    }

    /**
     * Defines the processing rate of the
     * {@link org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification
     * ProcessingResourceSpecification}.
     *
     * @param processingRate
     * @return this <code>ProcessingResourceSpecification</code>
     * @see org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification
     */
    public ProcessingResourceSpecificationCreator withProcessingRate(final String processingRate) {
        Objects.requireNonNull(processingRate, "The given processingRate must not be null");
        final PCMRandomVariable randomVariable = CoreFactory.eINSTANCE.createPCMRandomVariable();
        randomVariable.setSpecification(processingRate);
        processingRateVariable = randomVariable;
        return this;
    }

    /**
     * Turns the ProcessingResourceSpecification in the making into a finished
     * {@link org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification
     * ProcessingResourceSpecification}.
     *
     * @return the finished ProcessingResourceSpecification
     */
    public ProcessingResourceSpecification build() {
        final ProcessingResourceSpecification prs = ResourceenvironmentFactory.eINSTANCE
                .createProcessingResourceSpecification();
        prs.setMTTR(mttr);
        prs.setMTTF(mttf);
        prs.setNumberOfReplicas(numberOfReplicas);
        prs.setRequiredByContainer(isRequiredByContainer);
        prs.setSchedulingPolicy(schedulingPolicy);
        prs.setActiveResourceType_ActiveResourceSpecification(processingResourceType);
        prs.setProcessingRate_ProcessingResourceSpecification(processingRateVariable);

        return prs;
    }
}
