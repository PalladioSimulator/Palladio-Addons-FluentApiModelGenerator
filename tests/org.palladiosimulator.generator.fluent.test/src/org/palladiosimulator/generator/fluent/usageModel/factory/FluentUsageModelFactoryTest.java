package org.palladiosimulator.generator.fluent.usageModel.factory;

import static org.junit.Assert.*;

import org.junit.Test;
import org.palladiosimulator.generator.fluent.shared.util.ModelSaver;
import org.palladiosimulator.pcm.usagemodel.UsageModel;
import org.palladiosimulator.pcm.usagemodel.UsageScenario;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;

public class FluentUsageModelFactoryTest {
    FluentUsageModelFactory create;

    private void setUp() {
        create = new FluentUsageModelFactory();
    }

    private void printXML(UsageModel usgModel, String name) {
        //ModelSaver.saveUsageModel(usgModel, name, true);
       // ModelSaver.saveUsageModel(usgModel, name, false);
    }

    @Test
    public void basicUsageModel() {
        // Actual Model
        setUp();
        UsageModel usgModel = create.newUsageModel().createUsageModelNow();
        printXML(usgModel, "./UsgModelExample");

        // Expected Model
        UsageModel expectedModel = UsagemodelFactory.eINSTANCE.createUsageModel();

        // Test
        //TODO
        //assertEquals(expectedModel, usgModel); 
        assertEquals(expectedModel.toString(), usgModel.toString()); 

    }

    @Test
    public void basicUsageScenario() {
        // Actual Model
        setUp();
        UsageModel usgModel = create.newUsageModel().addToUsageModel(create.newUsageScenario()
                .addToUsageScenario(create.newOpenWorkload()).addToUsageScenario(create.newScenarioBehavior()))
                .createUsageModelNow();
        printXML(usgModel, "./UsgModExplUsageScenario");

        // Expected Model
        UsageModel expectedModel = UsagemodelFactory.eINSTANCE.createUsageModel();

        UsageScenario usgScen = UsagemodelFactory.eINSTANCE.createUsageScenario();
        usgScen.setWorkload_UsageScenario(UsagemodelFactory.eINSTANCE.createOpenWorkload());
        usgScen.setScenarioBehaviour_UsageScenario(UsagemodelFactory.eINSTANCE.createScenarioBehaviour());

        expectedModel.getUsageScenario_UsageModel().add(usgScen);

        // Test
        //TODO
        assertEquals(expectedModel.toString(), usgModel.toString());
    }

    // @Test
    public void basicUserData() {
        setUp();
        UsageModel usgModel = create.newUsageModel().addToUsageModel(create.newUserData()) // assemblyContext_userData
                .createUsageModelNow();
        ModelSaver.saveUsageModel(usgModel, "./UsgModExplUserData", true);
    }

}
