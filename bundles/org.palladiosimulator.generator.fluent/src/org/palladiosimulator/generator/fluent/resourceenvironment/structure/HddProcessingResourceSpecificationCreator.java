package org.palladiosimulator.generator.fluent.resourceenvironment.structure;

import java.util.Objects;

import org.palladiosimulator.generator.fluent.shared.structure.ProcessingResource;
import org.palladiosimulator.generator.fluent.shared.structure.SchedulingPolicies;
import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.resourceenvironment.HDDProcessingResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentFactory;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.SchedulingPolicy;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.resourceenvironment.HDDProcessingResourceSpecification
 * HddProcessingResourceSpecification}.
 *
 * @author Florian Krone
 * @see org.palladiosimulator.pcm.resourceenvironment.HDDProcessingResourceSpecification
 */
public class HddProcessingResourceSpecificationCreator {
    private double mttr;
    private double mttf;
    private int numberOfReplicas;
    private boolean isRequiredByContainer = false;
    private SchedulingPolicy schedulingPolicy;
    private ProcessingResourceType processingResourceType;
    private final ResourceEnvironmentCreator resourceCreator;
    private PCMRandomVariable processingRateVariable;
    private PCMRandomVariable writeProcessingRate;
    private PCMRandomVariable readProcessingRate;

    public HddProcessingResourceSpecificationCreator(final ResourceEnvironmentCreator resourceCreator) {
        this.resourceCreator = resourceCreator;
    }

    /**
     * Defines the Mttr of the
     * {@link org.palladiosimulator.pcm.resourceenvironment.HDDProcessingResourceSpecification
     * HDDProcessingResourceSpecification}.
     *
     * @param mttr
     * @return this <code>HddProcessingResourceSpecification</code>
     * @see org.palladiosimulator.pcm.resourceenvironment.HDDProcessingResourceSpecification
     * @see org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification
     */
    public HddProcessingResourceSpecificationCreator withMttr(final double mttr) {
        this.mttr = mttr;
        return this;
    }

    /**
     * Defines the Mttf of the
     * {@link org.palladiosimulator.pcm.resourceenvironment.HDDProcessingResourceSpecification
     * HDDProcessingResourceSpecification}.
     *
     * @param mttf
     * @return this <code>HddProcessingResourceSpecification</code>
     * @see org.palladiosimulator.pcm.resourceenvironment.HDDProcessingResourceSpecification
     * @see org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification
     */
    public HddProcessingResourceSpecificationCreator withMttf(final double mttf) {
        this.mttf = mttf;
        return this;
    }

    /**
     * Defines the number of replicas of the
     * {@link org.palladiosimulator.pcm.resourceenvironment.HDDProcessingResourceSpecification
     * HDDProcessingResourceSpecification}.
     *
     * @param numberOfReplicas
     * @return this <code>HddProcessingResourceSpecification</code>
     * @see org.palladiosimulator.pcm.resourceenvironment.HDDProcessingResourceSpecification
     * @see org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification
     */
    public HddProcessingResourceSpecificationCreator withNumberOfReplicas(final int numberOfReplicas) {
        this.numberOfReplicas = numberOfReplicas;
        return this;
    }

    /**
     * Sets the
     * {@link org.palladiosimulator.pcm.resourceenvironment.HDDProcessingResourceSpecification
     * HDDProcessingResourceSpecification} to be required by the
     * {@link org.palladiosimulator.pcm.resourceenvironment.ResourceContainer
     * ResourceContainer}.
     *
     * @return this <code>HddProcessingResourceSpecification</code>
     * @see org.palladiosimulator.pcm.resourceenvironment.HDDProcessingResourceSpecification
     * @see org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification
     */
    public HddProcessingResourceSpecificationCreator isRequiredByContainer() {
        isRequiredByContainer = true;
        return this;
    }

    /**
     * Defines the scheduling policy of the
     * {@link org.palladiosimulator.pcm.resourceenvironment.HDDProcessingResourceSpecification
     * HDDProcessingResourceSpecification}.
     *
     * @param policy
     * @return this <code>HddProcessingResourceSpecification</code>
     * @see org.palladiosimulator.pcm.resourceenvironment.HDDProcessingResourceSpecification
     * @see org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification
     */
    public HddProcessingResourceSpecificationCreator withSchedulingPolicy(final SchedulingPolicies policy) {
        schedulingPolicy = resourceCreator.getSchedulingPolicy(policy);
        return this;
    }

    /**
     * Defines the processing resource of the
     * {@link org.palladiosimulator.pcm.resourceenvironment.HDDProcessingResourceSpecification
     * HDDProcessingResourceSpecification}.
     *
     * @param resource
     * @return this <code>HddProcessingResourceSpecification</code>
     * @see org.palladiosimulator.pcm.resourceenvironment.HDDProcessingResourceSpecification
     * @see org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification
     */
    public HddProcessingResourceSpecificationCreator withProcessingResourceType(final ProcessingResource resource) {
        processingResourceType = resourceCreator.getProcessingResource(resource);
        return this;
    }

    /**
     * Defines the processing rate of the
     * {@link org.palladiosimulator.pcm.resourceenvironment.HDDProcessingResourceSpecification
     * HDDProcessingResourceSpecification}.
     *
     * @param processingRate
     * @return this <code>HddProcessingResourceSpecification</code>
     * @see org.palladiosimulator.pcm.resourceenvironment.HDDProcessingResourceSpecification
     * @see org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification
     */
    public HddProcessingResourceSpecificationCreator withProcessingRate(final String processingRate) {
        Objects.requireNonNull(processingRate, "The given processingRate must not be null");
        final PCMRandomVariable randomVariable = CoreFactory.eINSTANCE.createPCMRandomVariable();
        randomVariable.setSpecification(processingRate);
        processingRateVariable = randomVariable;
        return this;
    }

    /**
     * Defines the write processing rate of the
     * {@link org.palladiosimulator.pcm.resourceenvironment.HDDProcessingResourceSpecification
     * HDDProcessingResourceSpecification}.
     *
     * @param writeProcessingRate
     * @return this <code>HddProcessingResourceSpecification</code>
     * @see org.palladiosimulator.pcm.resourceenvironment.HDDProcessingResourceSpecification
     */
    public HddProcessingResourceSpecificationCreator withWriteProcessingRate(final String writeProcessingRate) {
        Objects.requireNonNull(writeProcessingRate, "The given writeProcessingRate must not be null");
        final PCMRandomVariable randomVariable = CoreFactory.eINSTANCE.createPCMRandomVariable();
        randomVariable.setSpecification(writeProcessingRate);
        this.writeProcessingRate = randomVariable;
        return this;
    }

    /**
     * Defines the read processing rate of the
     * {@link org.palladiosimulator.pcm.resourceenvironment.HDDProcessingResourceSpecification
     * HDDProcessingResourceSpecification}.
     *
     * @param readProcessingRate
     * @return this <code>HddProcessingResourceSpecification</code>
     * @see org.palladiosimulator.pcm.resourceenvironment.HDDProcessingResourceSpecification
     */
    public HddProcessingResourceSpecificationCreator withReadProcessingRate(final String readProcessingRate) {
        Objects.requireNonNull(readProcessingRate, "The given readProcessingRate must not be null");
        final PCMRandomVariable randomVariable = CoreFactory.eINSTANCE.createPCMRandomVariable();
        randomVariable.setSpecification(readProcessingRate);
        this.readProcessingRate = randomVariable;
        return this;
    }

    /**
     * Turns the HDDProcessingResourceSpecification in the making into a finished
     * {@link org.palladiosimulator.pcm.resourceenvironment.HDDProcessingResourceSpecification
     * HDDProcessingResourceSpecification}.
     *
     * @return the finished HDDProcessingResourceSpecification
     */
    public HDDProcessingResourceSpecification build() {
        final HDDProcessingResourceSpecification prs = ResourceenvironmentFactory.eINSTANCE
                .createHDDProcessingResourceSpecification();
        prs.setMTTR(mttr);
        prs.setMTTF(mttf);
        prs.setNumberOfReplicas(numberOfReplicas);
        prs.setRequiredByContainer(isRequiredByContainer);
        prs.setSchedulingPolicy(schedulingPolicy);
        prs.setActiveResourceType_ActiveResourceSpecification(processingResourceType);
        prs.setProcessingRate_ProcessingResourceSpecification(processingRateVariable);
        prs.setWriteProcessingRate(writeProcessingRate);
        prs.setReadProcessingRate(readProcessingRate);

        return prs;
    }
}
