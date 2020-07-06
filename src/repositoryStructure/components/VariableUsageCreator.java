package repositoryStructure.components;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.parameter.ParameterFactory;
import org.palladiosimulator.pcm.parameter.VariableCharacterisation;
import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;
import org.palladiosimulator.pcm.parameter.VariableUsage;

import apiControlFlowInterfaces.VariableUsageCreation.Basic;
import apiControlFlowInterfaces.VariableUsageCreation.Composite;
import de.uka.ipd.sdq.stoex.AbstractNamedReference;
import repositoryStructure.Entity;
import repositoryStructure.RepositoryCreator;

public class VariableUsageCreator extends Entity implements Basic, Composite {

	private String reference;
	private List<VariableCharacterisation> variableCharacterisations;
	private BasicComponentCreator correspondingBasicComponent;
	private CompositeComponentCreator correspondingCompositeComponent;

	public VariableUsageCreator(BasicComponentCreator component, RepositoryCreator repo) {
		this.repository = repo;
		this.variableCharacterisations = new ArrayList<>();
		this.correspondingBasicComponent = component;
	}

	public VariableUsageCreator(CompositeComponentCreator component, RepositoryCreator repo) {
		this.repository = repo;
		this.variableCharacterisations = new ArrayList<>();
		this.correspondingCompositeComponent = component;
	}

	@Override
	public VariableUsageCreator withName(String name) {
		return (VariableUsageCreator) super.withName(name);
	}

	@Override
	public VariableUsageCreator withVariableCharacterisation(String specification_stochasticExpression,
			VariableCharacterisationType type) {
		VariableCharacterisation varchar = ParameterFactory.eINSTANCE.createVariableCharacterisation();
		if (specification_stochasticExpression != null) {
			PCMRandomVariable rand = CoreFactory.eINSTANCE.createPCMRandomVariable();
			rand.setSpecification(specification_stochasticExpression);
			varchar.setSpecification_VariableCharacterisation(rand);
		}
		if (type != null)
			varchar.setType(type);
		this.variableCharacterisations.add(varchar);

		return this;
	}

	@Override
	public VariableUsageCreator withNamedReference(String reference) {
		this.reference = reference;
		return this;
	}

	@Override
	public BasicComponentCreator now1() {
		VariableUsage varUsage = this.build();
		correspondingBasicComponent.addVariableUsage(varUsage);
		return this.correspondingBasicComponent;
	}

	@Override
	public CompositeComponentCreator now2() {
		VariableUsage varUsage = this.build();
		correspondingCompositeComponent.addVariableUsage(varUsage);
		return this.correspondingCompositeComponent;
	}

	@Override
	protected VariableUsage build() {
		VariableUsage varUsage = ParameterFactory.eINSTANCE.createVariableUsage();
		// TODO: this throws an exception coz ref is null
//		if(this.reference != null) {
//		AbstractNamedReference ref = varUsage.getNamedReference__VariableUsage();
//		ref.setReferenceName(reference);
//		}
		varUsage.getVariableCharacterisation_VariableUsage().addAll(variableCharacterisations);
		return varUsage;
	}
}