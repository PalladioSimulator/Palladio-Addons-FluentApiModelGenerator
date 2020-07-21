package repositoryStructure.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.parameter.ParameterFactory;
import org.palladiosimulator.pcm.parameter.VariableCharacterisation;
import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;
import org.palladiosimulator.pcm.parameter.VariableUsage;

import de.uka.ipd.sdq.stoex.AbstractNamedReference;
import de.uka.ipd.sdq.stoex.NamespaceReference;
import de.uka.ipd.sdq.stoex.StoexFactory;
import de.uka.ipd.sdq.stoex.VariableReference;
import repositoryStructure.Entity;
import repositoryStructure.RepositoryCreator;

/**
 * This class constructs a
 * {@link org.palladiosimulator.pcm.parameter.VariableUsage VariableUsage}. It
 * is used to create the '<em><b>VariableUsage</b></em>' object step-by-step,
 * i.e. '<em><b>VariableUsageCreator</b></em>' objects are of intermediate
 * state.
 * 
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.parameter.VariableUsage
 */
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

	/**
	 * Creates a {@link org.palladiosimulator.pcm.parameter.VariableCharacterisation
	 * VariableCharacterisation} and adds it to the '<em><b>VariableUsage</b></em>'.
	 * 
	 * <p>
	 * Variable characterizations store performance critical meta-information on a
	 * variable. For example, if a variable's value is used in a long running loop,
	 * the value of the variable is performance critical. <br>
	 * Variable characterizations contain a <code>type</code>
	 * ({@link org.palladiosimulator.pcm.parameter.VariableCharacterisationType
	 * VariableCharacterisationType}), which tells what kind of meta-information is
	 * stored and a {@link org.palladiosimulator.pcm.core.PCMRandomVariable
	 * PCMRandomVariable} for storing the value of the characterization.
	 * </p>
	 * <p>
	 * The stochastic Expression <code>specification_stochasticExpression</code>
	 * specifies the value of the characterization as a PCMRandomVariable. Possible
	 * values for the <code>type</code> are '<em><b>STRUCTURE</b></em>',
	 * '<em><b>NUMBER_OF_ELEMENTS</b></em>', '<em><b>BYTESIZE</b></em>',
	 * '<em><b>TYPE</b></em>', '<em><b>VALUE</b></em>'.
	 * 
	 * @param specification_stochasticExpression value of the characterization
	 * @param type                               of the characterization
	 * @return the variable usage in the making
	 * @see org.palladiosimulator.pcm.parameter.VariableCharacterisation
	 * @see org.palladiosimulator.pcm.parameter.VariableUsage
	 */
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

	/**
	 * Creates a {@link de.uka.ipd.sdq.stoex.VariableReference VariableReference}
	 * and adds it to the '<em><b>VariableUsage</b></em>'. A variable usage can have
	 * either a variable reference or a namespace reference that can contain inner
	 * references.
	 * <p>
	 * The <code>reference</code> refers to the name of the characterized variable
	 * as a namedReference association.<br>
	 * Note that it was an explicit design decision to refer to variable names
	 * instead of the actual variables (i.e., by referring to Parameter class).
	 * <p>
	 * 
	 * @param reference the name of the characterized variable
	 * @return the variable usage in the making
	 * @see de.uka.ipd.sdq.stoex.AbstractNamedReference
	 */
	public VariableUsageCreator withVariableReference(String reference) {
		VariableReference variableReference = StoexFactory.eINSTANCE.createVariableReference();
		variableReference.setReferenceName(reference);
		this.reference = variableReference;
		return this;
	}

	/**
	 * Creates a {@link de.uka.ipd.sdq.stoex.NamespaceReference NamespaceReference}
	 * and adds it to the '<em><b>VariableUsage</b></em>'. A variable usage can have
	 * either a variable reference or a namespace reference that can contain inner
	 * references.
	 * <p>
	 * The <code>reference</code> refers to the namespace of the characterized
	 * variable. The <code>innerReferences</code> are optional further namespace
	 * references, though the last innerRefernce will always be a
	 * {@link de.uka.ipd.sdq.stoex.VariableReference VariableReference}. Combined
	 * they act as a namedReference association.<br>
	 * Note that it was an explicit design decision to refer to variable names
	 * instead of the actual variables (i.e., by referring to Parameter class).
	 * <p>
	 * 
	 * @param reference       namespace reference of the characterized variable
	 * @param innerReferences further namespace references and a variable reference
	 *                        of the characterized variable
	 * @return the variable usage in the making
	 * @see de.uka.ipd.sdq.stoex.AbstractNamedReference
	 */
	public VariableUsageCreator withNamespaceReference(String reference, String... innerReferences) {
		if (innerReferences != null && innerReferences.length > 0) {
			String string = innerReferences[innerReferences.length-1];
			VariableReference variableReference = StoexFactory.eINSTANCE.createVariableReference();
			variableReference.setReferenceName(string);
			List<String> asList = new LinkedList<String>(Arrays.asList(innerReferences));
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
	public VariableUsage build() {
		VariableUsage varUsage = ParameterFactory.eINSTANCE.createVariableUsage();

		if (this.reference != null) {
			varUsage.setNamedReference__VariableUsage(reference);
		}

		varUsage.getVariableCharacterisation_VariableUsage().addAll(variableCharacterisations);

		// TODO: sind über die GUI nicht setzbar, und in der Dokumentation nicht weiter
		// erwähnt. muss das gemacht werden?
//		EntryLevelSystemCall a = varUsage.getEntryLevelSystemCall_InputParameterUsage();
//		EntryLevelSystemCall b = varUsage.getEntryLevelSystemCall_OutputParameterUsage();
//		SpecifiedOutputParameterAbstraction c = varUsage
//				.getSpecifiedOutputParameterAbstraction_expectedExternalOutputs_VariableUsage();
//		UserData d = varUsage.getUserData_VariableUsage();
		return varUsage;
	}
}