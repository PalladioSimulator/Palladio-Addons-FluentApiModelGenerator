package org.palladiosimulator.generator.fluent.usagemodel.factory;

import static org.junit.Assert.*;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;
import org.palladiosimulator.generator.fluent.repository.factory.FluentRepositoryFactory;
import org.palladiosimulator.generator.fluent.repository.structure.internals.Primitive;
import org.palladiosimulator.generator.fluent.shared.util.ModelSaver;
import org.palladiosimulator.generator.fluent.system.factory.FluentSystemFactory;
import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.parameter.VariableCharacterisationType;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.system.System;
import org.palladiosimulator.pcm.usagemodel.AbstractUserAction;
import org.palladiosimulator.pcm.usagemodel.Branch;
import org.palladiosimulator.pcm.usagemodel.BranchTransition;
import org.palladiosimulator.pcm.usagemodel.ClosedWorkload;
import org.palladiosimulator.pcm.usagemodel.Delay;
import org.palladiosimulator.pcm.usagemodel.EntryLevelSystemCall;
import org.palladiosimulator.pcm.usagemodel.Loop;
import org.palladiosimulator.pcm.usagemodel.OpenWorkload;
import org.palladiosimulator.pcm.usagemodel.Start;
import org.palladiosimulator.pcm.usagemodel.Stop;
import org.palladiosimulator.pcm.usagemodel.UsageModel;
import org.palladiosimulator.pcm.usagemodel.UsagemodelFactory;
import org.palladiosimulator.pcm.usagemodel.Workload;




@SuppressWarnings("static-method")
public class FluentUsageModelFactoryTest {
    FluentUsageModelFactory create;

  //------------------   Util ------------------ 
    
    private void setUp() {
        this.create = new FluentUsageModelFactory();
    }
    
    private static Repository createSimplifiedMediaStoreRepository() {
      //copied from org.palladiosimulator.generator.fluent.repository.examples, added OperationSignature for testing
        FluentRepositoryFactory create = new FluentRepositoryFactory();
        Repository repo = create.newRepository().withName("SimplifiedMediaStore Repository")
            .addToRepository(create.newOperationInterface().withName("IAudioDB")
                    .withOperationSignature(create.newOperationSignature().withName("opSignatureAudioDB")))
            .addToRepository(create.newOperationInterface().withName("ISound"))
            .addToRepository(create.newOperationInterface().withName("MediaStoreInterface"))
            .addToRepository(create.newOperationInterface().withName("GUIInterface")
                    .withOperationSignature(create.newOperationSignature().withName("opSignatureGUIInterface")))
            .addToRepository(create.newOperationInterface().withName("AudioDBInterface"))
            .addToRepository(create.newBasicComponent().withName("AudioDB")
                    .provides(create.fetchOfOperationInterface("AudioDBInterface"), "ProvidesAudioDBInterface"))
            .addToRepository(create.newBasicComponent().withName("DBAdapter")
                    .requires(create.fetchOfOperationInterface("AudioDBInterface"), "RequiresAudioDBInterface")
                    .provides(create.fetchOfOperationInterface("IAudioDB"), "ProvidesIAudioDB"))
            .addToRepository(create.newBasicComponent().withName("DigitalWatermarking")
                    .provides(create.fetchOfOperationInterface("ISound"), "ProvidesISound"))
            .addToRepository(create.newBasicComponent().withName("MediaStore")
                    .requires(create.fetchOfOperationInterface("ISound"), "RequiresISound")
                    .requires(create.fetchOfOperationInterface("IAudioDB"), "RequiresIAudioDB").provides(
                            create.fetchOfOperationInterface("MediaStoreInterface"), "ProvidesMediaStoreInterface"))
            .addToRepository(create.newBasicComponent().withName("WebGUI")
                    .requires(create.fetchOfOperationInterface("MediaStoreInterface"),
                            "RequiresMediaStoreInterface")
                    .provides(create.fetchOfOperationInterface("GUIInterface"), "ProvidesGUIInterface"))
            .createRepositoryNow();
        ModelSaver.saveRepository(repo, "./simplifiedMediaStore", false);
    return repo;
    }
    
    private System createSimplifiedMediaStoreSystem() {
        //copied from org.palladiosimulator.generator.fluent.system.examples, changed where to get Respository
        FluentSystemFactory create = new FluentSystemFactory();
        System system = create.newSystem().withName("SimplifiedMediaStore System")
                //.addRepository(ModelLoader.loadRepository("./simplifiedMediaStore.repository"))
                .addRepository(createSimplifiedMediaStoreRepository())
                .addToSystem(
                        create.newAssemblyContext().withName("AudioDB Component").withEncapsulatedComponent("AudioDB"))
                .addToSystem(create.newAssemblyContext().withName("DBAdapter Component")
                        .withEncapsulatedComponent("DBAdapter"))
                .addToSystem(create.newAssemblyContext().withName("DigitalWatermarking Component")
                        .withEncapsulatedComponent("DigitalWatermarking"))
                .addToSystem(create.newAssemblyContext().withName("MediaStore Component")
                        .withEncapsulatedComponent("MediaStore"))
                .addToSystem(
                        create.newAssemblyContext().withName("WebGUI Component").withEncapsulatedComponent("WebGUI"))
                .addToSystem(create.newAssemblyConnector().withName("AudioDBInterfaceConnector")
                        .withProvidingAssemblyContext("AudioDB Component")
                        .withOperationProvidedRole("ProvidesAudioDBInterface")
                        .withRequiringAssemblyContext("DBAdapter Component")
                        .withOperationRequiredRole("RequiresAudioDBInterface"))
                .addToSystem(create.newAssemblyConnector().withName("IAudioDB Connector")
                        .withProvidingAssemblyContext("DBAdapter Component")
                        .withOperationProvidedRole("ProvidesIAudioDB")
                        .withRequiringAssemblyContext("MediaStore Component")
                        .withOperationRequiredRole("RequiresIAudioDB"))
                .addToSystem(create.newAssemblyConnector().withName("ISound Connector")
                        .withProvidingAssemblyContext("DigitalWatermarking Component")
                        .withOperationProvidedRole("ProvidesISound")
                        .withRequiringAssemblyContext("MediaStore Component")
                        .withOperationRequiredRole("RequiresISound"))
                .addToSystem(create.newAssemblyConnector().withName("MediaStoreInterface Connector")
                        .withProvidingAssemblyContext("MediaStore Component")
                        .withOperationProvidedRole("ProvidesMediaStoreInterface")
                        .withRequiringAssemblyContext("WebGUI Component")
                        .withOperationRequiredRole("RequiresMediaStoreInterface"))
                .addToSystem(create.newOperationProvidedRole().withName("SystemProvidesGUIInterface")
                        .withProvidedInterface("GUIInterface"))
                .addToSystem(create.newProvidedDelegationConnectorCreator().withName("GUIInterface Connector")
                        .withOuterProvidedRole("SystemProvidesGUIInterface").withProvidingContext("WebGUI Component")
                        .withOperationProvidedRole("ProvidesGUIInterface"))
                .createSystemNow();
        ModelSaver.saveSystem(system, "./simplifiedMediaStore", false);
        return system;
    }
    
    private void printXML(UsageModel usgModel, String name) {
        //ModelSaver.saveUsageModel(usgModel, name, true);
        ModelSaver.saveUsageModel(usgModel, name, false);
    }
    
    //TODO: compare of created model with original one
    private boolean compareEMF(UsageModel origin, UsageModel test) {
        boolean result = false;
        Resource orig = origin.eResource();
        Resource tst = test.eResource();

        EObject rootO = orig.getContents().get(0);
        EObject rootT = tst.getContents().get(0);
        
       //IComparisonScope scope = new DefaultComparisonScope(branchResourceSet, baseResourceSet, null);
       // Comparison comparison = EMFCompare.builder().build().compare(scope);
        
        /* Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
        
        
        ResourceSet resourceSet1 = new ResourceSetImpl();
        ResourceSet resourceSet2 = new ResourceSetImpl();

        resourceSet1.getResource(origin, true);
        resourceSet2.getResource(test, true);
          private void bob() {
        ResourceSet resourceSet = new ResourceSetImpl();
        Map extensionMap = (Map) resourceSet.getResourceFactoryRegistry()
            .getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
        try {

      Region region01 = StatemachineFactoryImpl.eINSTANCE.createRegion();
      addResourceToModel(resourceSet, region01, "st1.xmi");
      State state01 = StatemachineFactoryImpl.eINSTANCE.createState();
      state01.setName("aaaa");
      region01.getState().add(state01);
      if (state01.eResource() == null) {
        System.out.println("state01 NOT contained in resource!");
        return;
      }

      Region region02 = StatemachineFactoryImpl.eINSTANCE.createRegion();
      addResourceToModel(resourceSet, region02, "st2.xmi");
      State state02 = StatemachineFactoryImpl.eINSTANCE.createState();
      state02.setName("bbbb");
      region02.getState().add(state02);
      if (state02.eResource() == null) {
        System.out.println("state02 NOT contained in resource!");
        return;
      }

      final MatchModel match = MatchService.doMatch(region01, region02,
          Collections.<String, Object> emptyMap());
      final DiffModel diff = DiffService.doDiff(match, false);
      final List<DiffElement> differences = new ArrayList<DiffElement>(
          diff.getOwnedElements());
      MergeService.merge(differences, true);

      // Prints the results
      addResourceToModel(resourceSet, match, "match.xmi");
      addResourceToModel(resourceSet, diff, "diff.xmi");

      if (match.eResource() != null)
        System.out.println(ModelUtils.serialize(match)); // Throws an
                                                         // exception!
      if (diff.eResource() != null)
        System.out.println(ModelUtils.serialize(diff));

    } catch (final InterruptedException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private void addResourceToModel(ResourceSet resourceSet, EObject obj,
      String path) {
    Resource res = resourceSet.createResource(URI.createURI(path));
    res.getContents().add(obj);
  }
      
     State state02 = StatemachineFactoryImpl.eINSTANCE.createState();
state02.setName("bbbb");
region02.getState().add(state02);
if (state02.eResource() == null) {
    System.out.println("state02 NOT contained in resource!");
    return;
}
     Model1 targetModel = EcoreUtil.copy(model1);
addResourceToModel(targetModel) // assign the copied model to a resource
MatchModel match = MatchService.doMatch(targetModel, model2,
                    Collections.<String, Object> emptyMap());
DiffModel diff = DiffService.doDiff(match, false);
EList<DiffElement> differences = diff.getDifferences();
for (DiffElement diffElement : differences) {
    MergeService.merge(diffElement, true);
}

   public static EObject loadYourModel(String path) {
    //Initialzie Models
    YourPackage.eINSTANCE.eClass();

    //register your xmi resources
    final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
    final Map<String, Object> m = reg.getExtensionToFactoryMap();
    //put all your different ecore file suffixes in the map; suffix = YourPackage.eNAME
    m.put(YourPackage.eNAME, new XMIResourceFactoryImpl());
    //you can put all different package names here

    //Create a new Resource set to store the EObjects from the file
    ResourceSet resSet = new ResourceSetImpl();

    //get the resource of your ecore file
    Resource resource = resSet.getResource(URI.createURI(path), true);
    //Get the first element = root of your model hierachy
    EObject root = resource.getContents().get(0);
    return root;
}
    public void compare() {
    URI uri1 = URI.createFileURI("E:/eclipse-dsl-workspace/edu.ustb.lesley.register/src/test/base.xmi");
    URI uri2 = URI.createFileURI("E:/eclipse-dsl-workspace/edu.ustb.lesley.register/src/test/branch1.xmi");

    Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());

    ResourceSet baseResourceSet = new ResourceSetImpl();
    ResourceSet branchResourceSet = new ResourceSetImpl();
    baseResourceSet.getPackageRegistry().put("https://edu/ustb/lesley/register", RegisterPackage.eINSTANCE);
    baseResourceSet.getPackageRegistry().put("https://edu/ustb/lesley/register", RegisterPackage.eINSTANCE);

    baseResourceSet.getResource(uri1, true);
    branchResourceSet.getResource(uri2, true);

    IComparisonScope scope = new DefaultComparisonScope(branchResourceSet, baseResourceSet, null);
    Comparison comparison = EMFCompare.builder().build().compare(scope);
    
    List<Diff> differences = comparison.getDifferences();
    for(Diff diff : differences) {
        System.out.println(diff.toString());
    }
    
    // Let's merge every single diff
    IMerger.Registry mergerRegistry = IMerger.RegistryImpl.createStandaloneInstance();
    IBatchMerger merger = new BatchMerger(mergerRegistry);
    merger.copyAllLeftToRight(differences, new BasicMonitor());
    
    // check that models are equal after batch merging
    Comparison assertionComparison = EMFCompare.builder().build().compare(scope);
    EList<Diff> assertionDifferences = assertionComparison.getDifferences();
    System.out.println("after batch merging: " + assertionDifferences.size());
    assertEquals(0, assertionDifferences.size());
}*/


        
        return result;
    }


       
  //------------------   TESTS ------------------ 
    
    @Test
    public void basicUsageModelClasses() {
        // Actual Model
        setUp();
        UsageModel usgModel = this.create.newUsageModel().createUsageModelNow();
        printXML(usgModel, "UsgModClasses");

        // Expected Model
        UsageModel expectedModel = UsagemodelFactory.eINSTANCE.createUsageModel();

        // Test
        assertEquals(expectedModel.toString(), usgModel.toString());
    }
   
    private System mediaStoreMockUp() {
        FluentSystemFactory sysFac = new FluentSystemFactory();
        FluentRepositoryFactory repoFac = new FluentRepositoryFactory();
        
        Repository repo = repoFac.newRepository().withName("defaultRepository")
                .addToRepository(repoFac.newCompositeDataType().withName("FileContent"))
                .addToRepository(repoFac.newCompositeDataType().withName("AudioCollectionRequest")
                        .withInnerDeclaration("Count", Primitive.INTEGER)
                        .withInnerDeclaration("Size", Primitive.INTEGER))
                .addToRepository(repoFac.newOperationInterface().withName("IFacade")
                        .withOperationSignature(repoFac.newOperationSignature().withName("upload")
                                .withParameter("file", repoFac.fetchOfDataType("FileContent"), null))
                        .withOperationSignature(repoFac.newOperationSignature().withName("getFileList"))
                        .withOperationSignature(repoFac.newOperationSignature().withName("register"))
                        .withOperationSignature(repoFac.newOperationSignature().withName("login"))
                        .withOperationSignature(repoFac.newOperationSignature().withName("download")
                                .withParameter("audioRequest", repoFac.fetchOfDataType("AudioCollectionRequest"), null)))
                .createRepositoryNow();
        
        System system = sysFac.newSystem().withName("defaultSystem")
                .addRepository(repo)                
                .addToSystem(sysFac.newOperationProvidedRole().withName("Provided_IWebGui").withProvidedInterface("IFacade"))                
                .createSystemNow();

        ModelSaver.saveRepository(repo, "realisticMediaStore", false);
        ModelSaver.saveSystem(system, "realisticMediaStore", false);
        return system;
    }
    
    @Test
    public void mediaStoreRealistic() {
        //Building MediaStore3-Model: ms_base_usage_realistic.usagemodel and needed System and Repository for that
        setUp();
        UsageModel usgModel = this.create.addSystem(mediaStoreMockUp()).newUsageModel().addToUsageModel(
                this.create.newUsageScenario(
                        this.create.newScenarioBehavior().withName("RealisticUsageScenarioBehaviour")
                        .addToScenarioBehaviour(
                                this.create.newStartAction().withName("startUsage").withSuccessor(
                                this.create.newBranchAction().withName("isRegistered")
                                    .addToBranchAction(this.create.newBranchTransition(this.create.newScenarioBehavior().addToScenarioBehaviour(
                                            this.create.newEntryLevelSystemCall(this.create.fetchOffOperationRoleAndSignature("defaultSystem","Provided_IWebGui", "register")).withName("register"))
                                            .withName("needsToRegister")).withProbability(0.6))
                                    .addToBranchAction(this.create.newBranchTransition(this.create.newScenarioBehavior().withName("isAlreadyRegistered")
                                            ).withProbability(0.4)).withSuccessor(                                
                                this.create.newEntryLevelSystemCall(
                                        this.create.fetchOffOperationRoleAndSignature("defaultSystem","Provided_IWebGui","login"))
                                        .withName("login").withSuccessor(
                                this.create.newDelayAction("GammaMoments(3000,0.3)").withName("userDelayAfterLogin").withSuccessor(
                                this.create.newEntryLevelSystemCall(
                                        this.create.fetchOffOperationRoleAndSignature("defaultSystem","Provided_IWebGui","getFileList"))
                                        .withName("getFileList").withSuccessor(
                                this.create.newDelayAction("GammaMoments(6000,0.3)").withName("userDelayAfterGetFileList").withSuccessor(
                                this.create.newBranchAction().withName("downloadOrUpload")
                                    .addToBranchAction(this.create.newBranchTransition(this.create.newScenarioBehavior().withName("downloadCase")
                                            .addToScenarioBehaviour(this.create.newEntryLevelSystemCall(
                                                    this.create.fetchOffOperationRoleAndSignature("defaultSystem","Provided_IWebGui","download"))
                                                    .withName("download")
                                                    .addToEntryLevelSystemCallInput(this.create.newVariableUsage("audioRequest", "Size").withVariableCharacterisation("IntPMF[(38303999;0.16666667)(38304000;0.16666667)(40568000;0.16666667)(41544000;0.16666667)(48280000;0.16666666)(65000000;0.16666667)(88216000;0.16666666)]", VariableCharacterisationType.BYTESIZE))
                                                    .addToEntryLevelSystemCallInput(this.create.newVariableUsage("audioRequest", "Count").withVariableCharacterisation("2", VariableCharacterisationType.VALUE))
                                                    )).withProbability(0.8))
                                    .addToBranchAction(this.create.newBranchTransition(this.create.newScenarioBehavior().withName("uploadCase")
                                            .addToScenarioBehaviour(this.create.newEntryLevelSystemCall(
                                                    this.create.fetchOffOperationRoleAndSignature("defaultSystem","Provided_IWebGui","upload"))
                                                    .withName("upload").addToEntryLevelSystemCallInput(this.create.newVariableUsage("file").withVariableCharacterisation("IntPMF[(38303999;0.16666667)(38304000;0.16666667)(40568000;0.16666667)(41544000;0.16666667)(48280000;0.16666666)(65000000;0.16666667)(88216000;0.16666666)]", VariableCharacterisationType.BYTESIZE))
                                                    )).withProbability(0.2)).withSuccessor(
                                this.create.newStopAction().withName("stopUsage"))))))))),
                        
                        this.create.newOpenWorkload("Exp(0.00004)")).withName("RealisticUsageScenario"))               
                
                .createUsageModelNow();
        printXML(usgModel, "realisticMediaStore");
        
        //TODO: use "boolean compareEMF(UsageModel origin, UsageModel test)" to verify created model
    }
    

    //------------------   Usage Scenario ------------------ 
   
   @Test
    public void basicUsageScenario() {
    
        String name = "UsgScen";
        setUp();
        UsageModel usgModel = this.create.newUsageModel().addToUsageModel(this.create.newUsageScenario(this.create.newScenarioBehavior()
                ,this.create.newOpenWorkload("10"))
                .withName(name))
                .createUsageModelNow();
        
        printXML(usgModel, "UsgModUsageScenarioBasic");

        assertEquals(name, usgModel.getUsageScenario_UsageModel().get(0).getEntityName());
        assertNotNull(usgModel.getUsageScenario_UsageModel().get(0).getWorkload_UsageScenario());
        assertNotNull(usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario());
    }
    
    @Test
    public void usageScenarioWorkloadOpen() {
        String time = "10";
        setUp();
        
        UsageModel usgModel = this.create.newUsageModel().addToUsageModel(this.create.newUsageScenario(
                this.create.newScenarioBehavior()
                ,this.create.newOpenWorkload(time)))
                
                .createUsageModelNow();
        
        printXML(usgModel, "UsgModScenWorkloadOpen");

        Workload w = usgModel.getUsageScenario_UsageModel().get(0).getWorkload_UsageScenario();
        assertNotNull(w);
        OpenWorkload o = (OpenWorkload) w;
        assertEquals(time,o.getInterArrivalTime_OpenWorkload().getSpecification());
    }
    
    @Test
    public void usageScenarioWorkloadClosed() {
        String time = "10";
        int population = 100;
        setUp();
        
        UsageModel usgModel = this.create.newUsageModel().addToUsageModel(this.create.newUsageScenario(this.create.newScenarioBehavior(),
                        this.create.newClosedWorkload(time)
                        .withPopulation(population)))                
                .createUsageModelNow();
                
        printXML(usgModel, "UsgModScenWorkloadClosed");

        Workload w = usgModel.getUsageScenario_UsageModel().get(0).getWorkload_UsageScenario();
        assertNotNull(w);
        ClosedWorkload c = (ClosedWorkload) w;
        assertEquals(time,c.getThinkTime_ClosedWorkload().getSpecification());
        assertEquals(population, c.getPopulation());
    }
      
  //------------------   Usage Scenario  - Scenario Behaviour & Actions ------------------ 
    
    @Test
    public void usageScenarioBehaviour() {
        String name = "ScenBehaviour";
        int countAddedActions = 2;  //as it adds start and stop automatically
        
        setUp();
        
        UsageModel usgModel = this.create.newUsageModel().addToUsageModel(this.create.newUsageScenario(
                        this.create.newScenarioBehavior()
                        .withName(name)
                        ,this.create.newClosedWorkload("0")))
                
                .createUsageModelNow();
        printXML(usgModel, "UsgModScenBehavior");
 
        assertEquals(name, usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getEntityName());
        
        List<AbstractUserAction> list = usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour();
        assertFalse(list.isEmpty());
        assertEquals(countAddedActions, list.size());       
    }
   
  @Test
    public void usageScenarioBehavActions() {
        setUp();
        
        String operSigName = "opSignatureGUIInterface";
        String provRoleName = "SystemProvidesGUIInterface";
        int countAddedActions = 6;
               
        UsageModel usgModel = this.create.addSystem(createSimplifiedMediaStoreSystem())
                .newUsageModel().addToUsageModel(this.create.newUsageScenario(
                        this.create.newScenarioBehavior()  
                        .addToScenarioBehaviour(this.create.newStartAction()
                                .withSuccessor(this.create.newDelayAction("10")
                                .withSuccessor(this.create.newBranchAction()
                                .withSuccessor(this.create.newEntryLevelSystemCall(this.create.fetchOffOperationRoleAndSignature("SimplifiedMediaStore System",provRoleName, operSigName))
                                .withSuccessor(this.create.newLoopAction("1", this.create.newScenarioBehavior())
                                .withSuccessor(this.create.newStopAction())))
    ))),this.create.newOpenWorkload("0")))
                
                .createUsageModelNow();
 
        printXML(usgModel, "UsgModScenBehvActions");
        
        List<AbstractUserAction> list = usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour();
        assertFalse(list.isEmpty());
        assertEquals(countAddedActions, list.size());
    }
    
    @Test
    public void usageScenarioBehavActionList() {
        int countAddedActions = 4; //needs to be 4 as Start/Stop get added automatically
        String fstDelay = "FirstDelayAction";
        String sndDelay = "SecondDelayAction";
        
        setUp();
        
        UsageModel usgModel = this.create.newUsageModel().addToUsageModel(this.create.newUsageScenario(
                this.create.newScenarioBehavior().addToScenarioBehaviour(this.create.newDelayAction("1").withName(fstDelay).withSuccessor(this.create.newDelayAction("1").withName(sndDelay)))
                ,this.create.newOpenWorkload("10")))
                .createUsageModelNow();
                
         printXML(usgModel, "UsgModScenBehvActionList");
        
        List<AbstractUserAction> list = usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour();
        assertFalse(list.isEmpty());
        assertEquals(countAddedActions, list.size());  
        
        // Check order, Order is Start, DelayW/fstDelay, DelayW/sndDelay, Stop
        for (int i = 0; i < list.size(); i++) {
            AbstractUserAction act = list.get(i);
            if (act.getEntityName().equals(fstDelay)) {
                // Before Start, After sndDelay
                assertTrue(act.getPredecessor() instanceof Start);
                assertEquals(sndDelay,act.getSuccessor().getEntityName());
            } else if (act.getEntityName().equals(sndDelay)) {
                // Before fstDelay, After Stop
                assertTrue(act.getSuccessor() instanceof Stop);  
                assertEquals(fstDelay, act.getPredecessor().getEntityName());
            }
            //no need to check, if Action is Start or Stop as this is testen over the Delay Actions
        } 
        
    }

    @Test
    public void usageScenarioBehavActionsDelay() {
        String name = "DelayAction";
        String time = "20";
        setUp();
        
        UsageModel usgModel = this.create.newUsageModel().addToUsageModel(this.create.newUsageScenario(
                this.create.newScenarioBehavior().addToScenarioBehaviour(this.create.newDelayAction(time).withName(name))
                , this.create.newClosedWorkload("0")))
                
                .createUsageModelNow();
        
        printXML(usgModel, "UsgModScenBehvActionsDelay");
        
        Delay act = null;
        List<AbstractUserAction> list = usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour();
        //to filter for the correct one and ignore Start/Stop which get added automatically
        for(int i = 0; i< list.size(); i++) {     
            AbstractUserAction action = list.get(i);
            if(action instanceof Delay) {                
                act = (Delay) action;}
        }    
        
        assertNotNull(act);
        assertEquals(name, act.getEntityName());
        assertEquals(time, act.getTimeSpecification_Delay().getSpecification());
    }

    @Test
    public void usageScenarioBehavActionsLoop() {
        String name = "LoopAction";
        String iteration = "20";
        String scenName = "ScenBeh";
        setUp();
        
        UsageModel usgModel = this.create.newUsageModel().addToUsageModel(this.create.newUsageScenario(
                this.create.newScenarioBehavior().addToScenarioBehaviour(
                        this.create.newLoopAction(iteration, this.create.newScenarioBehavior().withName(scenName)).withName(name))
                ,this.create.newClosedWorkload("0")))
                .createUsageModelNow();        
        
        
         printXML(usgModel, "UsgModScenBehvActionsLoop");
        
        Loop act = null;
        List<AbstractUserAction> list = usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour();
        //to filter for the correct one and ignore Start/Stop which get added automatically
        for(int i = 0; i< list.size(); i++) {     
            AbstractUserAction action = list.get(i);
            if(action instanceof Loop) {                
                act = (Loop) action;}
        }
        
        assertNotNull(act);
        assertEquals(name, act.getEntityName());
        assertEquals(iteration, act.getLoopIteration_Loop().getSpecification());
        assertEquals(scenName, act.getBodyBehaviour_Loop().getEntityName());
    }
  
    @Test
    public void usageScenarioBehavActionsBranch() {
        String name = "BranchAction";
        //Double prop = 0.6; 
        Double prop = 1.0; 
        String scenName = "ScenBeh";
        setUp();
        
        UsageModel usgModel = this.create.newUsageModel().addToUsageModel(this.create.newUsageScenario(
                this.create.newScenarioBehavior().addToScenarioBehaviour(
                        this.create.newBranchAction().withName(name).addToBranchAction(
                                this.create.newBranchTransition(this.create.newScenarioBehavior().withName(scenName)).withProbability(prop)))
                , this.create.newClosedWorkload("0")))
                .createUsageModelNow();
                
        printXML(usgModel, "UsgModScenBehvActionsBranch");
        
        Branch act = null;
        List<AbstractUserAction> list = usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour();
        //to filter for the correct one and ignore Start/Stop which get added automatically
        for(int i = 0; i< list.size(); i++) {     
            AbstractUserAction action = list.get(i);
            if(action instanceof Branch) {                
                act = (Branch) action;}
        }    
        
        assertNotNull(act);        
        assertEquals(name, act.getEntityName());
        BranchTransition trans = act.getBranchTransitions_Branch().get(0);
        assertEquals(prop,trans.getBranchProbability(),0.001);
        assertEquals(scenName,trans.getBranchedBehaviour_BranchTransition().getEntityName());
    }
 
    @Test
    public void usageScenarioBehavActionsEntryLevelSystemCall() {
        String name = "EntryLevelSystemCall";
        int priority = 1;

        String operSigName = "opSignatureGUIInterface";
        String provRoleName = "SystemProvidesGUIInterface";
        
        setUp();
        UsageModel usgModel = this.create.addSystem(createSimplifiedMediaStoreSystem())
                .newUsageModel().addToUsageModel(
                this.create.newUsageScenario(
                        this.create.newScenarioBehavior().addToScenarioBehaviour(
                                this.create.newEntryLevelSystemCall(this.create.fetchOffOperationRoleAndSignature("SimplifiedMediaStore System",provRoleName, operSigName))
                                    .withName(name)
                                    .addToEntryLevelSystemCallInput(this.create.newVariableUsage("TestReferenz"))
                                    .addToEntryLevelSystemCallOutput(this.create.newVariableUsage("TestReferenz"))
                                    .withPriority(priority))
                        , this.create.newClosedWorkload("10")))
                .createUsageModelNow();
        
        printXML(usgModel, "UsgModScenBehvActionsEntryLevelSystemCall");
        
        EntryLevelSystemCall act = null;
        List<AbstractUserAction> list = usgModel.getUsageScenario_UsageModel().get(0).getScenarioBehaviour_UsageScenario().getActions_ScenarioBehaviour();
        //to filter for the correct one and ignore Start/Stop which get added automatically
        for(int i = 0; i< list.size(); i++) {     
            AbstractUserAction action = list.get(i);
            if(action instanceof EntryLevelSystemCall) {                
                act = (EntryLevelSystemCall) action;}
        }    
        
        assertNotNull(act);        
        assertEquals(name, act.getEntityName());
        assertEquals(priority, act.getPriority());
        assertEquals(operSigName, act.getOperationSignature__EntryLevelSystemCall().getEntityName());
        assertEquals(provRoleName, act.getProvidedRole_EntryLevelSystemCall().getEntityName());
        assertFalse(act.getInputParameterUsages_EntryLevelSystemCall().isEmpty());
        assertFalse(act.getOutputParameterUsages_EntryLevelSystemCall().isEmpty());        
    }
    
    //------------------   User Data ------------------ 
      
    @Test
    public void basicUserData() {        
        String assConName = "AudioDB Component"; //see createSimplifiedMediaStoreSystem() for an Assembly Context to test for
        
        setUp();
        
        UsageModel usgModel = this.create.addSystem(createSimplifiedMediaStoreSystem()).newUsageModel().addToUsageModel(
                this.create.newUserData(this.create.fetchOffAssemblyContextByName("SimplifiedMediaStore System", assConName)))
                .createUsageModelNow();
                
         printXML(usgModel, "UsgModUserDataBasic");   
        
        assertFalse(usgModel.getUserData_UsageModel().isEmpty());
    }    

    @Test
    public void usrDataVariableUsage() {
        String assConName = "AudioDB Component"; //see createSimplifiedMediaStoreSystem() for an Assembly Context to test for

        //Usage Model
        setUp(); 
        
        UsageModel usgModel = this.create.addSystem(createSimplifiedMediaStoreSystem()).newUsageModel().addToUsageModel(
                this.create.newUserData(this.create.fetchOffAssemblyContextByName("SimplifiedMediaStore System", assConName)).addToUserData(this.create.newVariableUsage("TestReferenz")))
                .createUsageModelNow();

        printXML(usgModel, "UsgModUserDataVarUsage"); 
        
        //Test
        assertFalse(usgModel.getUserData_UsageModel().get(0).getUserDataParameterUsages_UserData().isEmpty());
    }
    
    @Test
    public void usrDataAssemblyContext() {
        String assConName = "AudioDB Component"; //see createSimplifiedMediaStoreSystem() for an Assembly Context to test for

        setUp(); 
        
        UsageModel usgModel = this.create.addSystem(createSimplifiedMediaStoreSystem()).newUsageModel().addToUsageModel(
                this.create.newUserData(this.create.fetchOffAssemblyContextByName("SimplifiedMediaStore System", assConName)))
                .createUsageModelNow();
        
         printXML(usgModel, "UsgModUserDataAssembly"); 
        
        //Test
        AssemblyContext con = usgModel.getUserData_UsageModel().get(0).getAssemblyContext_userData();
        assertEquals(assConName, con.getEntityName());
    }

}
