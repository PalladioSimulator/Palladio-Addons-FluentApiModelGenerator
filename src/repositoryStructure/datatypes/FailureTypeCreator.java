package repositoryStructure.datatypes;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.reliability.InternalFailureOccurrenceDescription;
import org.palladiosimulator.pcm.reliability.ReliabilityFactory;
import org.palladiosimulator.pcm.reliability.ResourceTimeoutFailureType;
import org.palladiosimulator.pcm.reliability.SoftwareInducedFailureType;
import org.palladiosimulator.pcm.repository.PassiveResource;

import apiControlFlowInterfaces.SoftwareFailureType.SoftwareInducedFailure;
import apiControlFlowInterfaces.SoftwareFailureType.ResourceTimeoutFailure;
import repositoryStructure.Entity;
import repositoryStructure.RepositoryCreator;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.reliability.FailureType FailureType}. It is
 * used to create the '<em><b>FailureType</b></em>' object step-by-step, i.e.
 * '<em><b>FailureTypeCreator</b></em>' objects are of intermediate state.
 * 
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.reliability.FailureType
 * @see apiControlFlowInterfaces.SoftwareFailureType.ResourceTimeoutFailure
 * @see apiControlFlowInterfaces.SoftwareFailureType.SoftwareInducedFailure
 */
public class FailureTypeCreator extends Entity implements ResourceTimeoutFailure, SoftwareInducedFailure {

	private PassiveResource passiveResource;
	private List<InternalFailureOccurrenceDescription> internalFailureOccurrenceDescriptions;

	public FailureTypeCreator(RepositoryCreator repo) {
		this.repository = repo;
		this.internalFailureOccurrenceDescriptions = new ArrayList<>();
	}

	@Override
	public FailureTypeCreator withName(String name) {
		return (FailureTypeCreator) super.withName(name);
	}

	public FailureTypeCreator withPassiveResource(PassiveResource passiveResource) {
		this.passiveResource = passiveResource;
		return this;
	}

	public FailureTypeCreator withInternalFailureOccurrenceDescription(double failureProbability) {
		InternalFailureOccurrenceDescription internal = ReliabilityFactory.eINSTANCE
				.createInternalFailureOccurrenceDescription();
		internal.setFailureProbability(failureProbability);
		internalFailureOccurrenceDescriptions.add(internal);
		return this;
	}

	@Override
	protected FailureType build() {
		return this.buildSoftwareInducedFailure();
	}

	public ResourceTimeoutFailureType buildResourceTimeoutFailure() {
		ResourceTimeoutFailureType timeout = ReliabilityFactory.eINSTANCE.createResourceTimeoutFailureType();
		timeout.setPassiveResource__ResourceTimeoutFailureType(this.passiveResource);
		timeout.getInternalFailureOccurrenceDescriptions__SoftwareInducedFailureType()
				.addAll(internalFailureOccurrenceDescriptions);
		return timeout;
	}

	@Override
	public SoftwareInducedFailureType buildSoftwareInducedFailure() {
		SoftwareInducedFailureType s = ReliabilityFactory.eINSTANCE.createSoftwareInducedFailureType();
		s.getInternalFailureOccurrenceDescriptions__SoftwareInducedFailureType()
				.addAll(internalFailureOccurrenceDescriptions);
		return s;
	}

}
