package systemModel.structure.qosAnnotations;

import java.util.ArrayList;
import java.util.Collection;

import org.palladiosimulator.pcm.qosannotations.QoSAnnotations;
import org.palladiosimulator.pcm.qosannotations.QosannotationsFactory;
import org.palladiosimulator.pcm.qosannotations.SpecifiedOutputParameterAbstraction;
import org.palladiosimulator.pcm.qosannotations.SpecifiedQoSAnnotation;

import systemModel.structure.SystemCreator;
import systemModel.structure.SystemEntity;

public class QoSAnnotationsCreator extends SystemEntity {
	private Collection<SpecifiedOutputParameterAbstraction> outputParameterAbstractions = new ArrayList<>();
	private Collection<SpecifiedQoSAnnotation> qoSAnnotations = new ArrayList<>();
	
	public QoSAnnotationsCreator(SystemCreator systemCreator) {
		this.system = systemCreator;
	}
	
	public QoSAnnotationsCreator withQoSAnnotation(SpecifiedQoSAnnotation annotation) {
		this.qoSAnnotations.add(annotation);
		return this;
	}
	
	public QoSAnnotationsCreator withQoSAnnotations(Collection<SpecifiedQoSAnnotation> annotations) {
		this.qoSAnnotations.addAll(annotations);
		return this;
	}
	
	public QoSAnnotationsCreator withOutputParameterAbstraction(SpecifiedOutputParameterAbstraction outputParameterAbstraction) {
		this.outputParameterAbstractions.add(outputParameterAbstraction);
		return this;
	}
	
	public QoSAnnotationsCreator withOutputParameterAbstractions(Collection<SpecifiedOutputParameterAbstraction> outputParameterAbstractions) {
		this.outputParameterAbstractions.addAll(outputParameterAbstractions);
		return this;
	}
	
	@Override
	public QoSAnnotations build() {
		QoSAnnotations annotations = QosannotationsFactory.eINSTANCE.createQoSAnnotations();
		if (this.name != null) {
			annotations.setEntityName(name);
		}
		annotations.getSpecifiedOutputParameterAbstractions_QoSAnnotations().addAll(outputParameterAbstractions);
		annotations.getSpecifiedQoSAnnotations_QoSAnnotations().addAll(qoSAnnotations);
		return annotations;
	}
	
	@Override
	public QoSAnnotationsCreator withName(String name) {
		return (QoSAnnotationsCreator) super.withName(name);
	}

}
