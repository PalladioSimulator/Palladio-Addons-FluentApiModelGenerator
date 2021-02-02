package resourceEvironment.structure;

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
	private ResourceEnvironmentCreator resourceCreator;
	private PCMRandomVariable processingRateVariable;

	public ProcessingResourceSpecificationCreator(ResourceEnvironmentCreator resourceCreator) {
		this.resourceCreator = resourceCreator;
	}
	
	public ProcessingResourceSpecificationCreator withMttr(double mttr) {
		this.mttr = mttr;
		return this;
	}
	
	public ProcessingResourceSpecificationCreator withMttf(double mttf) {
		this.mttf = mttf;
		return this;
	}
	
	public ProcessingResourceSpecificationCreator withNumberOfReplicas(int numberOfReplicas) {
		this.numberOfReplicas = numberOfReplicas;
		return this;
	}
	
	public ProcessingResourceSpecificationCreator isRequiredByContainer() {
		this.isRequiredByContainer = true;
		return this;
	}
	
	public ProcessingResourceSpecificationCreator withSchedulingPolicy(SchedulingPolicies policy) {
		schedulingPolicy = this.resourceCreator.getSchedulingPolicy(policy);
		return this;
	}
	
	public ProcessingResourceSpecificationCreator withProcessingResourceType(ProcessingResource resource) {
		this.processingResourceType = this.resourceCreator.getProcessingResource(resource);		
		return this;
	}
	
	public ProcessingResourceSpecificationCreator withProcessingRate(String processingRate) {
		PCMRandomVariable randomVariable = CoreFactory.eINSTANCE.createPCMRandomVariable();
		randomVariable.setSpecification(processingRate);
		this.processingRateVariable = randomVariable;
		return this;
	}
	
	public ProcessingResourceSpecification build() {
		ProcessingResourceSpecification prs = ResourceenvironmentFactory.eINSTANCE.createProcessingResourceSpecification();
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
