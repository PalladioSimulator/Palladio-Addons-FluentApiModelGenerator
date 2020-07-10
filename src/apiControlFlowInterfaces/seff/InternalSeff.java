package apiControlFlowInterfaces.seff;

import org.palladiosimulator.pcm.seff.ForkedBehaviour;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour;

import repositoryStructure.components.seff.StartActionCreator;

public interface InternalSeff {

	public StartActionCreator withStartAction();

	ResourceDemandingInternalBehaviour buildInternalBehaviour();

	ResourceDemandingBehaviour buildBehaviour();

	ForkedBehaviour buildForkedBehaviour();
}