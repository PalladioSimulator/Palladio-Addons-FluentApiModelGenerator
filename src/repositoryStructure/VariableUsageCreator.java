package repositoryStructure;

import org.palladiosimulator.pcm.PcmFactory;
import org.palladiosimulator.pcm.core.CoreFactory;
import org.palladiosimulator.pcm.core.PCMRandomVariable;
import org.palladiosimulator.pcm.parameter.ParameterFactory;
import org.palladiosimulator.pcm.parameter.VariableCharacterisation;
import org.palladiosimulator.pcm.parameter.VariableUsage;

public class VariableUsageCreator{
	
	
	void build() {
		VariableUsage varUsage = ParameterFactory.eINSTANCE.createVariableUsage();
		
		// Parameter -> set
		varUsage.getAssemblyContext__VariableUsage();
		varUsage.getCallAction__VariableUsage();
		varUsage.getCallReturnAction__VariableUsage();
		varUsage.getEntryLevelSystemCall_InputParameterUsage();
		varUsage.getEntryLevelSystemCall_OutputParameterUsage();
		varUsage.getSetVariableAction_VariableUsage();
		varUsage.getSpecifiedOutputParameterAbstraction_expectedExternalOutputs_VariableUsage();
		varUsage.getSynchronisationPoint_VariableUsage();
		varUsage.getUserData_VariableUsage();
		
		
		varUsage.getNamedReference__VariableUsage();
		//Lists -> add
		varUsage.getVariableCharacterisation_VariableUsage();
		
		VariableCharacterisation varChar = ParameterFactory.eINSTANCE.createVariableCharacterisation();
		varChar.getSpecification_VariableCharacterisation();
		varChar.getType();
		PCMRandomVariable r = CoreFactory.eINSTANCE.createPCMRandomVariable();
		r.getExpression();
		r.getSpecification();
		
//		withVariableCharacterisation(expression, specification);
	}
}