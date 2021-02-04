package resourceEvironment.structure;

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
	private ResourceEnvironmentCreator resourceCreator;
	private PCMRandomVariable processingRateVariable;
	private PCMRandomVariable writeProcessingRate;
	private PCMRandomVariable readProcessingRate;

	public HddProcessingResourceSpecificationCreator(ResourceEnvironmentCreator resourceCreator) {
		this.resourceCreator = resourceCreator;
	}
	
	public HddProcessingResourceSpecificationCreator withMttr(double mttr) {
		this.mttr = mttr;
		return this;
	}
	
	public HddProcessingResourceSpecificationCreator withMttf(double mttf) {
		this.mttf = mttf;
		return this;
	}
	
	public HddProcessingResourceSpecificationCreator withNumberOfReplicas(int numberOfReplicas) {
		this.numberOfReplicas = numberOfReplicas;
		return this;
	}
	
	public HddProcessingResourceSpecificationCreator isRequiredByContainer() {
		this.isRequiredByContainer = true;
		return this;
	}
	
	public HddProcessingResourceSpecificationCreator withSchedulingPolicy(SchedulingPolicies policy) {
		schedulingPolicy = this.resourceCreator.getSchedulingPolicy(policy);
		return this;
	}
	
	public HddProcessingResourceSpecificationCreator withProcessingResourceType(ProcessingResource resource) {
		this.processingResourceType = this.resourceCreator.getProcessingResource(resource);		
		return this;
	}
	
	public HddProcessingResourceSpecificationCreator withProcessingRate(String processingRate) {
		PCMRandomVariable randomVariable = CoreFactory.eINSTANCE.createPCMRandomVariable();
		randomVariable.setSpecification(processingRate);
		this.processingRateVariable = randomVariable;
		return this;
	}	
	
	public HddProcessingResourceSpecificationCreator withWriteProcessingRate(String writeProcessingRate) {
		PCMRandomVariable randomVariable = CoreFactory.eINSTANCE.createPCMRandomVariable();
		randomVariable.setSpecification(writeProcessingRate);
		this.writeProcessingRate = randomVariable;
		return this;
	}	
	
	public HddProcessingResourceSpecificationCreator withReadProcessingRate(String readProcessingRate) {
		PCMRandomVariable randomVariable = CoreFactory.eINSTANCE.createPCMRandomVariable();
		randomVariable.setSpecification(readProcessingRate);
		this.readProcessingRate = randomVariable;
		return this;
	}
	
	public HDDProcessingResourceSpecification build() {
		HDDProcessingResourceSpecification prs = ResourceenvironmentFactory.eINSTANCE.createHDDProcessingResourceSpecification();
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
