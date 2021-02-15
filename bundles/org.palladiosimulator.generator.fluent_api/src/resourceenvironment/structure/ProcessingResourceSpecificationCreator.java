package resourceenvironment.structure;

import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.resourceenvironment.ProcessingResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentFactory;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.SchedulingPolicy;

import shared.structure.ProcessingResource;
import shared.structure.SchedulingPolicies;

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

    public ProcessingResourceSpecificationCreator withMttr(final double mttr) {
        this.mttr = mttr;
        return this;
    }

    public ProcessingResourceSpecificationCreator withMttf(final double mttf) {
        this.mttf = mttf;
        return this;
    }

    public ProcessingResourceSpecificationCreator withNumberOfReplicas(final int numberOfReplicas) {
        this.numberOfReplicas = numberOfReplicas;
        return this;
    }

    public ProcessingResourceSpecificationCreator isRequiredByContainer() {
        this.isRequiredByContainer = true;
        return this;
    }

    public ProcessingResourceSpecificationCreator withSchedulingPolicy(final SchedulingPolicies policy) {
        this.schedulingPolicy = this.resourceCreator.getSchedulingPolicy(policy);
        return this;
    }

    public ProcessingResourceSpecificationCreator withProcessingResourceType(final ProcessingResource resource) {
        this.processingResourceType = this.resourceCreator.getProcessingResource(resource);
        return this;
    }

    public ProcessingResourceSpecificationCreator withProcessingRate(final String processingRate) {
        final PCMRandomVariable randomVariable = CoreFactory.eINSTANCE.createPCMRandomVariable();
        randomVariable.setSpecification(processingRate);
        this.processingRateVariable = randomVariable;
        return this;
    }

    public ProcessingResourceSpecification build() {
        final ProcessingResourceSpecification prs = ResourceenvironmentFactory.eINSTANCE
            .createProcessingResourceSpecification();
        prs.setMTTR(this.mttr);
        prs.setMTTF(this.mttf);
        prs.setNumberOfReplicas(this.numberOfReplicas);
        prs.setRequiredByContainer(this.isRequiredByContainer);
        prs.setSchedulingPolicy(this.schedulingPolicy);
        prs.setActiveResourceType_ActiveResourceSpecification(this.processingResourceType);
        prs.setProcessingRate_ProcessingResourceSpecification(this.processingRateVariable);

        return prs;
    }
}
