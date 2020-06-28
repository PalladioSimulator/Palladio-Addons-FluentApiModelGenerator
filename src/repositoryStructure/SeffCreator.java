package repositoryStructure;

import org.eclipse.emf.common.util.EList;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import org.palladiosimulator.pcm.seff.AbstractAction;
import org.palladiosimulator.pcm.seff.InternalAction;
import org.palladiosimulator.pcm.seff.SeffFactory;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;
import org.palladiosimulator.pcm.seff.StartAction;

import apiControlFlowInterfaces.Action;
import apiControlFlowInterfaces.Follow;

public class SeffCreator extends Entity implements Follow, Action {

//	private Graph<Entity> graph;
	private StartAction start;
	private AbstractAction current;
	
	@Override
	public ServiceEffectSpecification build() {

		BasicComponent b = RepositoryFactory.eINSTANCE.createBasicComponent();
		EList<ServiceEffectSpecification> seff = b.getServiceEffectSpecifications__BasicComponent();

		ServiceEffectSpecification s;
//		Signature describedService = s.getDescribedService__SEFF();
//		String seffTypeID = s.getSeffTypeID();

		SeffFactory sf = SeffFactory.eINSTANCE;

		StartAction start = sf.createStartAction();

		return null;

	}

	@Override
	public Follow withStartAction() {
		//TODO:
		StartAction start = SeffFactory.eINSTANCE.createStartAction();
		this.start = start;
		this.current = start;
		
		start.getInfrastructureCall__Action();
		start.getPredecessor_AbstractAction();
		start.getResourceCall__Action();
		start.getResourceDemand_Action();
		start.getResourceDemandingBehaviour_AbstractAction();
		start.getSuccessor_AbstractAction();
		
		return this;
	}

	@Override
	public Follow internalAction() {
		// TODO Auto-generated method stub
		InternalAction action = SeffFactory.eINSTANCE.createInternalAction();
		action.getPredecessor_AbstractAction();
		action.getSuccessor_AbstractAction();
		action.getInternalFailureOccurrenceDescriptions__InternalAction();
		
		return this;
	}

	public Follow externalCallAction() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Follow emitEventAction() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Follow setVariableAction() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Follow acquireAction() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Follow releaseAction() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Follow loopAction() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Follow collectionIteratorAction() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Follow branchAction() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Follow forkAction() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Follow recoveryAction() {
		// TODO Auto-generated method stub
		return this;
	}

	public SeffCreator stopAction() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Action followedBy() {
		// TODO Auto-generated method stub
		return this;
	}
}
