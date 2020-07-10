package apiControlFlowInterfaces.seff;

import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;

public interface Seff {

	public Seff onSignature(Signature signature);

	public Seff withSeffTypeID(String seffTypeID);

	public Seff withInternalBehaviour(InternalSeff internalBehaviour);

	public StartSeff withSeffBehaviour();
	
	ServiceEffectSpecification build();
}
