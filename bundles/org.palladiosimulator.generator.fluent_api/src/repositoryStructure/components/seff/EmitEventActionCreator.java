package repositoryStructure.components.seff;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.repository.EventType;
import org.palladiosimulator.pcm.repository.SourceRole;
import org.palladiosimulator.pcm.seff.EmitEventAction;
import org.palladiosimulator.pcm.seff.SeffFactory;

import repositoryStructure.components.VariableUsageCreator;

/**
 * This class constructs an
 * {@link org.palladiosimulator.pcm.seff.EmitEventAction EmitEventAction}. It is
 * used to create the '<em><b>EmitEventAction</b></em>' object step-by-step,
 * i.e. '<em><b>EmitEventActionCreator</b></em>' objects are of intermediate
 * state.
 * 
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.seff.EmitEventAction
 */
public class EmitEventActionCreator extends SeffAction {

	private EventType eventType;
	private SourceRole requiredRole;
	private List<VariableUsage> inputVariableUsages;

	protected EmitEventActionCreator(SeffCreator seff) {
		this.seff = seff;
		this.inputVariableUsages = new ArrayList<>();
	}

	@Override
	public EmitEventActionCreator withName(String name) {
		return (EmitEventActionCreator) super.withName(name);
	}

	/**
	 * Specifies the <code>eventType</code> that is emitted by this action.
	 * <p>
	 * An existing <code>eventType</code> can be fetched from the repository using
	 * the factory, i.e. <code>create.fetchOfEventType(name)</code>.
	 * </p>
	 * 
	 * @param eventType
	 * @return this emit event action in the making
	 * @see factory.FluentRepositoryFactory#fetchOfEventType(String)
	 */
	public EmitEventActionCreator withEventType(EventType eventType) {
		Objects.requireNonNull(eventType, "eventType must not be null");
		this.eventType = eventType;
		return this;
	}

	/**
	 * Specifies the <code>sourceRole</code> that is triggered by this action.
	 * <p>
	 * An existing <code>sourceRole</code> can be fetched from the repository using
	 * the factory, i.e. <code>create.fetchOfSourceRole(name)</code>.
	 * </p>
	 * 
	 * @param sourceRole
	 * @return this emit event action in the making
	 * @see factory.FluentRepositoryFactory#fetchOfSourceRole(String)
	 */
	public EmitEventActionCreator withSourceRole(SourceRole sourceRole) {
		Objects.requireNonNull(sourceRole, "sourceRole must not be null");
		this.requiredRole = sourceRole;
		return this;
	}

	/**
	 * Adds the <code>variableUsage</code> to this action's list of input variable
	 * usages.
	 * 
	 * @param variableUsage
	 * @return this emit event action in the making
	 * @see factory.FluentRepositoryFactory#newVariableUsage()
	 */
	public EmitEventActionCreator withInputVariableUsage(VariableUsageCreator variableUsage) {
		Objects.requireNonNull(variableUsage, "variableUsage must not be null");
		this.inputVariableUsages.add(variableUsage.build());
		return this;
	}

	@Override
	protected EmitEventAction build() {
		EmitEventAction action = SeffFactory.eINSTANCE.createEmitEventAction();
		if (eventType != null)
			action.setEventType__EmitEventAction(eventType);
		if (requiredRole != null)
			action.setSourceRole__EmitEventAction(requiredRole);
		action.getInputVariableUsages__CallAction().addAll(inputVariableUsages);
		return action;
	}
}
