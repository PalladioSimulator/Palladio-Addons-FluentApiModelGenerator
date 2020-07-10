package repositoryStructure.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.parameter.ParameterFactory;
import org.palladiosimulator.pcm.parameter.VariableCharacterisation;
import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;
import org.palladiosimulator.pcm.parameter.VariableUsage;
import org.palladiosimulator.pcm.qosannotations.SpecifiedOutputParameterAbstraction;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.UserData;

import de.uka.ipd.sdq.stoex.AbstractNamedReference;
import de.uka.ipd.sdq.stoex.NamespaceReference;
import de.uka.ipd.sdq.stoex.StoexFactory;
import de.uka.ipd.sdq.stoex.VariableReference;
import repositoryStructure.Entity;
import repositoryStructure.RepositoryCreator;

public class VariableUsageCreator extends Entity {

	private AbstractNamedReference reference;
	private List<VariableCharacterisation> variableCharacterisations;

	public VariableUsageCreator(RepositoryCreator repo) {
		this.repository = repo;
		this.variableCharacterisations = new ArrayList<>();
	}

	@Override
	public VariableUsageCreator withName(String name) {
		return (VariableUsageCreator) super.withName(name);
	}

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

	public VariableUsageCreator withVariableReference(String reference) {
		VariableReference variableReference = StoexFactory.eINSTANCE.createVariableReference();
		variableReference.setReferenceName(reference);
		this.reference = variableReference;
		return this;
	}

	public VariableUsageCreator withNamespaceReference(String reference, String... innerReferences) {
		if (innerReferences != null && innerReferences.length > 0) {
			String string = innerReferences[innerReferences.length];
			VariableReference variableReference = StoexFactory.eINSTANCE.createVariableReference();
			variableReference.setReferenceName(string);
			List<String> asList = Arrays.asList(innerReferences);
			asList.remove(asList.size() - 1);
			asList.add(0, reference);
			this.reference = rec(variableReference, asList);
		} else {
			NamespaceReference namespaceReference = StoexFactory.eINSTANCE.createNamespaceReference();
			namespaceReference.setReferenceName(reference);
			this.reference = namespaceReference;
		}

		return this;
	}

	private AbstractNamedReference rec(AbstractNamedReference ref, List<String> refs) {
		if (refs.isEmpty())
			return ref;
		String string = refs.get(refs.size() - 1);
		NamespaceReference namespaceReference = StoexFactory.eINSTANCE.createNamespaceReference();
		namespaceReference.setReferenceName(string);
		namespaceReference.setInnerReference_NamespaceReference(ref);
		refs.remove(refs.size() - 1);
		return rec(namespaceReference, refs);

	}

	@Override
	protected VariableUsage build() {
		VariableUsage varUsage = ParameterFactory.eINSTANCE.createVariableUsage();

		if (this.reference != null) {
			varUsage.setNamedReference__VariableUsage(reference);
		}

		varUsage.getVariableCharacterisation_VariableUsage().addAll(variableCharacterisations);

		// TODO: sind über die GUI nicht setzbar, aber muss evtl gemacht werden
		EntryLevelSystemCall a = varUsage.getEntryLevelSystemCall_InputParameterUsage();
		EntryLevelSystemCall b = varUsage.getEntryLevelSystemCall_OutputParameterUsage();
		SpecifiedOutputParameterAbstraction c = varUsage
				.getSpecifiedOutputParameterAbstraction_expectedExternalOutputs_VariableUsage();
		UserData d = varUsage.getUserData_VariableUsage();
		return varUsage;
	}
}