package resourceenvironment.structure;

import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.resourceenvironment.HDDProcessingResourceSpecification;
import org.palladiosimulator.pcm.resourceenvironment.ResourceenvironmentFactory;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.SchedulingPolicy;

import shared.structure.ProcessingResource;
import shared.structure.SchedulingPolicies;

public class HddProcessingResourceSpecificationCreator extends ResourceEntity {
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

    public HddProcessingResourceSpecificationCreator withMttr(final double mttr) {
        this.mttr = mttr;
        return this;
    }

    public HddProcessingResourceSpecificationCreator withMttf(final double mttf) {
        this.mttf = mttf;
        return this;
    }

    public HddProcessingResourceSpecificationCreator withNumberOfReplicas(final int numberOfReplicas) {
        this.numberOfReplicas = numberOfReplicas;
        return this;
    }

    public HddProcessingResourceSpecificationCreator isRequiredByContainer() {
        this.isRequiredByContainer = true;
        return this;
    }

    public HddProcessingResourceSpecificationCreator withSchedulingPolicy(final SchedulingPolicies policy) {
        this.schedulingPolicy = this.resourceCreator.getSchedulingPolicy(policy);
        return this;
    }

    public HddProcessingResourceSpecificationCreator withProcessingResourceType(final ProcessingResource resource) {
        this.processingResourceType = this.resourceCreator.getProcessingResource(resource);
        return this;
    }

    public HddProcessingResourceSpecificationCreator withProcessingRate(final String processingRate) {
        final PCMRandomVariable randomVariable = CoreFactory.eINSTANCE.createPCMRandomVariable();
        randomVariable.setSpecification(processingRate);
        this.processingRateVariable = randomVariable;
        return this;
    }

    public HddProcessingResourceSpecificationCreator withWriteProcessingRate(final String writeProcessingRate) {
        final PCMRandomVariable randomVariable = CoreFactory.eINSTANCE.createPCMRandomVariable();
        randomVariable.setSpecification(writeProcessingRate);
        this.writeProcessingRate = randomVariable;
        return this;
    }

    public HddProcessingResourceSpecificationCreator withReadProcessingRate(final String readProcessingRate) {
        final PCMRandomVariable randomVariable = CoreFactory.eINSTANCE.createPCMRandomVariable();
        randomVariable.setSpecification(readProcessingRate);
        this.readProcessingRate = randomVariable;
        return this;
    }

    @Override
    public HDDProcessingResourceSpecification build() {
        final HDDProcessingResourceSpecification prs = ResourceenvironmentFactory.eINSTANCE
            .createHDDProcessingResourceSpecification();
        prs.setMTTR(this.mttr);
        prs.setMTTF(this.mttf);
        prs.setNumberOfReplicas(this.numberOfReplicas);
        prs.setRequiredByContainer(this.isRequiredByContainer);
        prs.setSchedulingPolicy(this.schedulingPolicy);
        prs.setActiveResourceType_ActiveResourceSpecification(this.processingResourceType);
        prs.setProcessingRate_ProcessingResourceSpecification(this.processingRateVariable);
        prs.setWriteProcessingRate(this.writeProcessingRate);
        prs.setReadProcessingRate(this.readProcessingRate);

        return prs;
    }
}
