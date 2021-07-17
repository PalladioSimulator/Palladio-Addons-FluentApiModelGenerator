package org.palladiosimulator.generator.fluent.shared.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.palladiosimulator.generator.fluent.component.repositoryStructure.RepositoryCreator;
import org.palladiosimulator.generator.fluent.component.repositoryStructure.RepositoryEntity;
import org.palladiosimulator.generator.fluent.exceptions.IllegalArgumentException;
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
public class VariableUsageCreator extends RepositoryEntity {

    private AbstractNamedReference reference;
    private final List<VariableCharacterisation> variableCharacterisations;

    public VariableUsageCreator(final RepositoryCreator repo) {
       //TODO: see if it is needed later, removed while moving to fluent.shared.components repository = repo;
        variableCharacterisations = new ArrayList<>();
    }
    
    public VariableUsageCreator() {
         variableCharacterisations = new ArrayList<>();
     }


    @Override
    public VariableUsageCreator withName(final String name) {
        return (VariableUsageCreator) super.withName(name);
    }

    /**
     * Creates a {@link org.palladiosimulator.pcm.parameter.VariableCharacterisation
     * VariableCharacterisation} and adds it to the '<em><b>VariableUsage</b></em>'.
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
     * @param specificationStochasticExpression value of the characterization
     * @param type                              of the characterization
     * @return the variable usage in the making
     * @see org.palladiosimulator.pcm.parameter.VariableCharacterisation
     * @see org.palladiosimulator.pcm.parameter.VariableUsage
     */
    public VariableUsageCreator withVariableCharacterisation(final String specificationStochasticExpression,
            final VariableCharacterisationType type) {
        IllegalArgumentException.throwIfNull(specificationStochasticExpression,
                "specification_stochasticExpression must not be null");
        IllegalArgumentException.throwIfNull(type, "type must not be null");
        final VariableCharacterisation varchar = ParameterFactory.eINSTANCE.createVariableCharacterisation();
        final PCMRandomVariable rand = CoreFactory.eINSTANCE.createPCMRandomVariable();
        rand.setSpecification(specificationStochasticExpression);
        varchar.setSpecification_VariableCharacterisation(rand);
        varchar.setType(type);
        variableCharacterisations.add(varchar);

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
    public VariableUsageCreator withVariableReference(final String reference) {
        IllegalArgumentException.throwIfNull(reference, "reference must not be null");
        final VariableReference variableReference = StoexFactory.eINSTANCE.createVariableReference();
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
    public VariableUsageCreator withNamespaceReference(final String reference, final String... innerReferences) {
        IllegalArgumentException.throwIfNull(reference, "reference must not be null");
        if ((innerReferences != null) && (innerReferences.length > 0)) {
            for (final String element : innerReferences) {
                IllegalArgumentException.throwIfNull(element, "inner references must not be null");
            }
        }

        if ((innerReferences != null) && (innerReferences.length > 0)) {
            final String string = innerReferences[innerReferences.length - 1];
            final VariableReference variableReference = StoexFactory.eINSTANCE.createVariableReference();
            variableReference.setReferenceName(string);
            final List<String> asList = new LinkedList<>(Arrays.asList(innerReferences));
            asList.remove(asList.size() - 1);
            asList.add(0, reference);
            this.reference = rec(variableReference, asList);
        } else {
            final NamespaceReference namespaceReference = StoexFactory.eINSTANCE.createNamespaceReference();
            namespaceReference.setReferenceName(reference);
            this.reference = namespaceReference;
        }

        return this;
    }

    private AbstractNamedReference rec(final AbstractNamedReference ref, final List<String> refs) {
        if (refs.isEmpty()) {
            return ref;
        }
        final String string = refs.get(refs.size() - 1);
        final NamespaceReference namespaceReference = StoexFactory.eINSTANCE.createNamespaceReference();
        namespaceReference.setReferenceName(string);
        namespaceReference.setInnerReference_NamespaceReference(ref);
        refs.remove(refs.size() - 1);
        return rec(namespaceReference, refs);

    }

    @Override
    public VariableUsage build() {
        final VariableUsage varUsage = ParameterFactory.eINSTANCE.createVariableUsage();

        if (reference != null) {
            varUsage.setNamedReference__VariableUsage(reference);
        }

        varUsage.getVariableCharacterisation_VariableUsage().addAll(variableCharacterisations);

        return varUsage;
    }
}
