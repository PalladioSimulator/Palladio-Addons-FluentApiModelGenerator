package system.structure.qosAnnotations;

import java.util.ArrayList;
import java.util.Collection;

import org.palladiosimulator.pcm.qosannotations.QoSAnnotations;
import org.palladiosimulator.pcm.qosannotations.QosannotationsFactory;
import org.palladiosimulator.pcm.qosannotations.SpecifiedOutputParameterAbstraction;
import org.palladiosimulator.pcm.qosannotations.SpecifiedQoSAnnotation;

import system.structure.SystemCreator;
import system.structure.SystemEntity;

public class QoSAnnotationsCreator extends SystemEntity {
    private final Collection<SpecifiedOutputParameterAbstraction> outputParameterAbstractions = new ArrayList<>();
    private final Collection<SpecifiedQoSAnnotation> qoSAnnotations = new ArrayList<>();

    public QoSAnnotationsCreator(final SystemCreator systemCreator) {
        this.system = systemCreator;
    }

    public QoSAnnotationsCreator withQoSAnnotation(final SpecifiedQoSAnnotation annotation) {
        this.qoSAnnotations.add(annotation);
        return this;
    }

    public QoSAnnotationsCreator withQoSAnnotations(final Collection<SpecifiedQoSAnnotation> annotations) {
        this.qoSAnnotations.addAll(annotations);
        return this;
    }

    public QoSAnnotationsCreator withOutputParameterAbstraction(
            final SpecifiedOutputParameterAbstraction outputParameterAbstraction) {
        this.outputParameterAbstractions.add(outputParameterAbstraction);
        return this;
    }

    public QoSAnnotationsCreator withOutputParameterAbstractions(
            final Collection<SpecifiedOutputParameterAbstraction> outputParameterAbstractions) {
        this.outputParameterAbstractions.addAll(outputParameterAbstractions);
        return this;
    }

    @Override
    public QoSAnnotations build() {
        final QoSAnnotations annotations = QosannotationsFactory.eINSTANCE.createQoSAnnotations();
        if (this.name != null) {
            annotations.setEntityName(this.name);
        }
        annotations.getSpecifiedOutputParameterAbstractions_QoSAnnotations()
            .addAll(this.outputParameterAbstractions);
        annotations.getSpecifiedQoSAnnotations_QoSAnnotations()
            .addAll(this.qoSAnnotations);
        return annotations;
    }

    @Override
    public QoSAnnotationsCreator withName(final String name) {
        return (QoSAnnotationsCreator) super.withName(name);
    }

}