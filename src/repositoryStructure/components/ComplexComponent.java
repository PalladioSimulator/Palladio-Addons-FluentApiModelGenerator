package repositoryStructure.components;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.Connector;
import org.palladiosimulator.pcm.core.composition.EventChannel;
import org.palladiosimulator.pcm.core.composition.ResourceRequiredDelegationConnector;

public abstract class ComplexComponent extends Component {

	protected List<AssemblyContext> assemblyContexts = new ArrayList<>();
	protected List<Connector> connectors = new ArrayList<>();
	protected List<EventChannel> eventChannels = new ArrayList<>();
	protected List<ResourceRequiredDelegationConnector> delegationConnectors = new ArrayList<>();
	
	//TODO: die Methoden dafür hier; override in den anderen
}
