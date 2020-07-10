package apiControlFlowInterfaces.seff;

import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;

public interface Seff extends SeffInterfaces{

	public Seff onSignature(Signature signature);

	public Seff withSeffTypeID(String seffTypeID);

	public Seff withInternalBehaviour(Internal internalBehaviour);

	public StartSeff withSeffBehaviour();
	
	ServiceEffectSpecification build();
}
