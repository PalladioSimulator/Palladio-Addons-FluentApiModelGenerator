package apiControlFlowInterfaces;

import org.palladiosimulator.pcm.reliability.ResourceTimeoutFailureType;
import org.palladiosimulator.pcm.reliability.SoftwareInducedFailureType;
import org.palladiosimulator.pcm.repository.PassiveResource;

public interface SoftwareFailureType {

	public interface SoftwareInducedFailure extends SoftwareFailureType {

		/**
		 * TODO
		 * @param name
		 * @return
		 */
		public SoftwareInducedFailure withName(String name);

		/**
		 * TODO
		 * @param failureProbability
		 * @return
		 */
		public SoftwareInducedFailure withInternalFailureOccurrenceDescription(double failureProbability);

		SoftwareInducedFailureType buildSoftwareInducedFailure();

	}

	public interface ResourceTimeoutFailure extends SoftwareFailureType {

		/**
		 * TODO
		 * @param name
		 * @return
		 */
		public ResourceTimeoutFailure withName(String name);

		/**
		 * TODO: wird hier wirklich ne fertige passive resource übergeben?
		 * @param passiveResource
		 * @return
		 */
		public ResourceTimeoutFailure withPassiveResource(PassiveResource passiveResource);

		/**
		 * TODO
		 * @param failureProbability
		 * @return
		 */
		public ResourceTimeoutFailure withInternalFailureOccurrenceDescription(double failureProbability);

		ResourceTimeoutFailureType buildResourceTimeoutFailure();
	}

}