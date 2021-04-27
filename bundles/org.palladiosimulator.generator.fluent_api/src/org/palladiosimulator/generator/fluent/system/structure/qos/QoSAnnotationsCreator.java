package org.palladiosimulator.generator.fluent.system.structure.qos;

import java.util.ArrayList;
import java.util.Collection;

import org.palladiosimulator.generator.fluent.system.structure.SystemCreator;
import org.palladiosimulator.generator.fluent.system.structure.SystemEntity;
import org.palladiosimulator.pcm.qosannotations.QoSAnnotations;
import org.palladiosimulator.pcm.qosannotations.QosannotationsFactory;
import org.palladiosimulator.pcm.qosannotations.SpecifiedOutputParameterAbstraction;
import org.palladiosimulator.pcm.qosannotations.SpecifiedQoSAnnotation;

/**
 * This class constructs
 * {@link org.palladiosimulator.pcm.qosannotations.QoSAnnotations
 * QoSAnnotations}.
 *
 * @author Florian Krone
 * @see org.palladiosimulator.pcm.qosannotations.QoSAnnotations
 */
public class QoSAnnotationsCreator extends SystemEntity {
    private final Collection<SpecifiedOutputParameterAbstraction> outputParameterAbstractions = new ArrayList<>();
    private final Collection<SpecifiedQoSAnnotation> qoSAnnotations = new ArrayList<>();

    public QoSAnnotationsCreator(final SystemCreator systemCreator) {
        system = systemCreator;
    }

    /**
     * Adds a {@link org.palladiosimulator.pcm.qosannotations.SpecifiedQoSAnnotation
     * SpecifiedQoSAnnotation}.
     *
     * @param annotation
     * @return this annotations creator
     * @see org.palladiosimulator.pcm.qosannotations.SpecifiedQoSAnnotation
     */
    public QoSAnnotationsCreator addQoSAnnotation(final SpecifiedQoSAnnotation annotation) {
        qoSAnnotations.add(annotation);
        return this;
    }

    /**
     * Adds a collection of
     * {@link org.palladiosimulator.pcm.qosannotations.SpecifiedQoSAnnotation
     * SpecifiedQoSAnnotation}s.
     *
     * @param annotation
     * @return this annotations creator
     * @see org.palladiosimulator.pcm.qosannotations.SpecifiedQoSAnnotation
     */
    public QoSAnnotationsCreator addQoSAnnotations(final Collection<SpecifiedQoSAnnotation> annotations) {
        qoSAnnotations.addAll(annotations);
        return this;
    }

    /**
     * Adds a
     * {@link org.palladiosimulator.pcm.qosannotations.SpecifiedOutputParameterAbstraction
     * SpecifiedOutputParameterAbstraction}.
     *
     * @param annotation
     * @return this annotations creator
     * @see org.palladiosimulator.pcm.qosannotations.SpecifiedOutputParameterAbstraction
     */
    public QoSAnnotationsCreator addOutputParameterAbstraction(
            final SpecifiedOutputParameterAbstraction outputParameterAbstraction) {
        outputParameterAbstractions.add(outputParameterAbstraction);
        return this;
    }

    /**
     * Adds a collection of
     * {@link org.palladiosimulator.pcm.qosannotations.SpecifiedOutputParameterAbstraction
     * SpecifiedOutputParameterAbstraction}s.
     *
     * @param annotation
     * @return this annotations creator
     * @see org.palladiosimulator.pcm.qosannotations.SpecifiedOutputParameterAbstraction
     */
    public QoSAnnotationsCreator addOutputParameterAbstractions(
            final Collection<SpecifiedOutputParameterAbstraction> outputParameterAbstractions) {
        this.outputParameterAbstractions.addAll(outputParameterAbstractions);
        return this;
    }

    @Override
    public QoSAnnotations build() {
        final QoSAnnotations annotations = QosannotationsFactory.eINSTANCE.createQoSAnnotations();
        if (name != null) {
            annotations.setEntityName(name);
        }
        annotations.getSpecifiedOutputParameterAbstractions_QoSAnnotations().addAll(outputParameterAbstractions);
        annotations.getSpecifiedQoSAnnotations_QoSAnnotations().addAll(qoSAnnotations);
        return annotations;
    }

    @Override
    public QoSAnnotationsCreator withName(final String name) {
        return (QoSAnnotationsCreator) super.withName(name);
    }

}
