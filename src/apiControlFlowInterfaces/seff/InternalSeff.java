package apiControlFlowInterfaces.seff;

import org.palladiosimulator.pcm.seff.ForkedBehaviour;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;

import repositoryStructure.components.seff.StartActionCreator;

public interface InternalSeff {

	/**
	 * Creates a {@link org.palladiosimulator.pcm.seff.StartAction StartAction}.
	 * 
	 * @return the start action in the making
	 */
	public StartActionCreator withStartAction();

	ResourceDemandingInternalBehaviour buildInternalBehaviour();

	ResourceDemandingBehaviour buildBehaviour();

	ForkedBehaviour buildForkedBehaviour();
}