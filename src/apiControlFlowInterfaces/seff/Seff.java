package apiControlFlowInterfaces.seff;

import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.seff.ResourceDemandingBehaviour;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;

public interface Seff {

	/**
	 * Defines the signature of the service that this SEFF's behaviour is modeling.
	 * <p>
	 * An existing <code>signature</code> can be fetched from the repository using
	 * the factory, i.e. <code>create.fetchOfSignature(name)</code>.
	 * </p>
	 * 
	 * @param signature
	 * @return this SEFF in the making
	 * @see factory.FluentRepositoryFactory#fetchOfSignature(String)
	 */
	public Seff onSignature(Signature signature);

	/**
	 * Defines the SEFF Type ID. Default=1.
	 * 
	 * @param seffTypeID
	 * @return this SEFF in the making
	 * @see org.palladiosimulator.pcm.seff.ServiceEffectSpecification#getSeffTypeID
	 */
	public Seff withSeffTypeID(String seffTypeID);

	/**
	 * Adds <code>internalBehaviour</code> to this SEFF's internal behaviours.
	 * <p>
	 * It models the behaviour of a component service as a sequence of internal
	 * actions with resource demands, control flow constructs, and external calls.
	 * Therefore, the class contains a chain of AbstractActions.
	 * </p>
	 * 
	 * @param internalBehaviour
	 * @return this SEFF in the making
	 * @see org.palladiosimulator.pcm.seff.ResourceDemandingInternalBehaviour
	 */
	public Seff withInternalBehaviour(InternalSeff internalBehaviour);

	/**
	 * Specifies the body behaviour of this SEFF. Every body behaviour starts with a
	 * {@link apiControlFlowInterfaces.seff.StartSeff#withStartAction() start
	 * action} followed by an arbitrary amount of other
	 * {@link org.palladiosimulator.pcm.seff.AbstractAction actions}. The body
	 * behaviour always ends with a stop action and a finishing call on the method
	 * {@link apiControlFlowInterfaces.seff.StopSeff#createBehaviourNow()
	 * createBehaviourNow()}.
	 * 
	 * @return this SEFF's starting body behaviour
	 */
	public StartSeff withSeffBehaviour();

	ServiceEffectSpecification build();

	ResourceDemandingSEFF buildRDSeff();

	ResourceDemandingBehaviour buildBehaviour();
}
