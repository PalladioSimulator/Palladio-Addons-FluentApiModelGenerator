package org.palladiosimulator.generator.fluent.component.repositoryStructure.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.generator.fluent.component.repositoryStructure.RepositoryCreator;
import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;
import org.palladiosimulator.pcm.repository.InfrastructureInterface;
import org.palladiosimulator.pcm.repository.InfrastructureSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

/**
 * This class constructs an
 * {@link org.palladiosimulator.pcm.repository.InfrastructureInterface
 * InfrastructureInterface}. It is used to create the
 * '<em><b>InfrastructureInterface</b></em>' object step-by-step, i.e.
 * '<em><b>InfrastructureInterfaceCreator</b></em>' objects are of intermediate
 * state.
 *
 * @author Louisa Lambrecht
 * @see org.palladiosimulator.pcm.repository.InfrastructureInterface
 */
public class InfrastructureInterfaceCreator extends Interface {

    private final List<InfrastructureSignature> signatures;

    public InfrastructureInterfaceCreator(final RepositoryCreator repo) {
        repository = repo;
        signatures = new ArrayList<>();
    }

    @Override
    public InfrastructureInterfaceCreator withName(final String name) {
        return (InfrastructureInterfaceCreator) super.withName(name);
    }

    // @Override
    // public InfrastructureInterfaceCreator withId(String id) {
    // return (InfrastructureInterfaceCreator) super.withId(id);
    // }

    // parent Interfaces
    @Override
    public InfrastructureInterfaceCreator conforms(final org.palladiosimulator.pcm.repository.Interface interfce) {
        return (InfrastructureInterfaceCreator) super.conforms(interfce);
    }

    @Override
    public InfrastructureInterfaceCreator withRequiredCharacterisation(final Parameter parameter,
            final VariableCharacterisationType type) {
        return (InfrastructureInterfaceCreator) super.withRequiredCharacterisation(parameter, type);
    }

    /**
     * Adds the <code>signature</code> to this interface's list of signatures. The
     * <code>signature</code> can be created using the
     * org.palladiosimulator.generator.fluent.component.factory, i.e.
     * <code>create.newInfrastructureSignature()</code>.
     *
     * @param signature
     * @return this infrastructure interface in the making
     * @see org.palladiosimulator.generator.fluent.component.factory.FluentRepositoryFactory#newInfrastructureSignature()
     */
    public InfrastructureInterfaceCreator withInfrastructureSignature(final InfrastructureSignatureCreator signature) {
        final InfrastructureSignature build = signature.build();
        repository.addSignature(build);
        signatures.add(build);
        return this;
    }

    @Override
    public InfrastructureInterface build() {
        final InfrastructureInterface interfce = RepositoryFactory.eINSTANCE.createInfrastructureInterface();

        if (name != null) {
            interfce.setEntityName(name);
            // if (id != null)
            // interfce.setId(id);
        }

        interfce.getInfrastructureSignatures__InfrastructureInterface().addAll(signatures);

        interfce.getParentInterfaces__Interface().addAll(parentInterfaces);
        interfce.getRequiredCharacterisations().addAll(requiredCharacterisations);

        return interfce;
    }

    protected void addInfrastructureSignatures(final InfrastructureSignature signature) {
        signatures.add(signature);
    }

}
