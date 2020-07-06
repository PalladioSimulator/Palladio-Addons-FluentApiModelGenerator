package apiControlFlowInterfaces;

import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;

import repositoryStructure.components.BasicComponentCreator;
import repositoryStructure.components.CompositeComponentCreator;

public interface VariableUsageCreation {

	public interface Basic extends VariableUsageCreation {

		public Basic withVariableCharacterisation(String specification_stochasticExpression,
				VariableCharacterisationType type);

		public Basic withNamedReference(String reference);

		public BasicComponentCreator now1();
	}

	public interface Composite extends VariableUsageCreation {

		public Composite withVariableCharacterisation(String specification_stochasticExpression,
				VariableCharacterisationType type);

		public Composite withNamedReference(String reference);

		public CompositeComponentCreator now2();

	}

}
