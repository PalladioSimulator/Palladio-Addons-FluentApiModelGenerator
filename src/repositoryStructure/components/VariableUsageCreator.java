package repositoryStructure.components;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.parameter.ParameterFactory;
import org.palladiosimulator.pcm.parameter.VariableCharacterisation;
import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;
import org.palladiosimulator.pcm.parameter.VariableUsage;

import de.uka.ipd.sdq.stoex.AbstractNamedReference;
import repositoryStructure.Entity;
import repositoryStructure.RepositoryCreator;

public class VariableUsageCreator extends Entity{
	
	private String reference;
	private List<VariableCharacterisation> variableCharacterisations;
	private BasicComponentCreator correspondingBasicComponent;
	
	public VariableUsageCreator(BasicComponentCreator component, RepositoryCreator repo) {
		this.repository = repo;
		this.variableCharacterisations = new ArrayList<>();
		this.correspondingBasicComponent = component;
	}
	
	public VariableUsageCreator withVariableCharacterisation(String specification_stochasticExpression, VariableCharacterisationType type) {
		VariableCharacterisation varchar = ParameterFactory.eINSTANCE.createVariableCharacterisation();
		PCMRandomVariable rand = CoreFactory.eINSTANCE.createPCMRandomVariable();
		rand.setSpecification(specification_stochasticExpression);
		varchar.setSpecification_VariableCharacterisation(rand);
		varchar.setType(type);
		this.variableCharacterisations.add(varchar);
		
		return this;
	}
	
	public BasicComponentCreator withNamedReference(String reference) {
		//TODO: über Interfaces regeln, dass auch für CompositeComponent tut
		this.reference = reference;
		VariableUsage varUsage = this.build();
		correspondingBasicComponent.addVariableUsage(varUsage);
		return this.correspondingBasicComponent;
		
	}
	
	@Override
	public VariableUsage build() {
		VariableUsage varUsage = ParameterFactory.eINSTANCE.createVariableUsage();
		AbstractNamedReference ref = varUsage.getNamedReference__VariableUsage();
		ref.setReferenceName(reference);
		varUsage.getVariableCharacterisation_VariableUsage().addAll(variableCharacterisations);
		return varUsage;
	}
}