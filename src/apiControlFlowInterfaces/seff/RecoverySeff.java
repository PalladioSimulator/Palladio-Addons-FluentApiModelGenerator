package apiControlFlowInterfaces.seff;

import org.palladiosimulator.pcm.seff.seff_reliability.RecoveryActionBehaviour;

import repositoryStructure.components.seff.SeffCreator;
import repositoryStructure.datatypes.Failure;

public interface RecoverySeff {

	public StartSeff withSeffBehaviour();

	public SeffCreator withFailureType(Failure failure);

	public SeffCreator withAlternativeRecoveryBehaviour(RecoverySeff recoveryBehaviour);

	RecoveryActionBehaviour buildRecoveryBehaviour();
}
