package repositoryStructure.datatypes;

import java.util.HashMap;
import java.util.Map;

import org.palladiosimulator.pcm.reliability.HardwareInducedFailureType;
import org.palladiosimulator.pcm.reliability.NetworkInducedFailureType;
import org.palladiosimulator.pcm.reliability.ReliabilityFactory;
import org.palladiosimulator.pcm.reliability.SoftwareInducedFailureType;
import org.palladiosimulator.pcm.resourcetype.CommunicationLinkResourceType;
import org.palladiosimulator.pcm.resourcetype.ProcessingResourceType;
import org.palladiosimulator.pcm.resourcetype.ResourcetypeFactory;

public class FailureType {
	
	private static Map<Failure, org.palladiosimulator.pcm.reliability.FailureType> failures = new HashMap<>();
	
	private static void init() {

		ReliabilityFactory fact = ReliabilityFactory.eINSTANCE;
		//TODO: set resources
		
		// CPU - Hardware
		HardwareInducedFailureType hft = fact.createHardwareInducedFailureType();
		hft.setId("_HlRi4UNsEd-z_dlODpG6uw");
		hft.setEntityName("HardwareInducedFailure (CPU)");
		ProcessingResourceType processingResourceType = ResourcetypeFactory.eINSTANCE.createProcessingResourceType();
//		processingResourceType = href="pathmap://PCM_MODELS/Palladio.resourcetype#_oro4gG3fEdy4YaaT-RYrLQ"
		hft.setProcessingResourceType__HardwareInducedFailureType(processingResourceType);
		failures.put(Failure.HARDWARE_CPU, hft);
		
		// Network
		NetworkInducedFailureType nft = fact.createNetworkInducedFailureType();
		nft.setId("_P0TZ10NsEd-z_dlODpG6uw");
		nft.setEntityName("NetworkInducedFailure (LAN)");
		CommunicationLinkResourceType clrt = ResourcetypeFactory.eINSTANCE.createCommunicationLinkResourceType();
		// href="pathmap://PCM_MODELS/Palladio.resourcetype#_o3sScH2AEdyH8uerKnHYug"/>
		nft.setCommunicationLinkResourceType__NetworkInducedFailureType(clrt);
		failures.put(Failure.NETWORK_LAN, nft);
		
		// HDD
		HardwareInducedFailureType hft2 = fact.createHardwareInducedFailureType();
		hft2.setId("_L-N5F0NsEd-z_dlODpG6uw");
		hft2.setEntityName("HardwareInducedFailure (HDD)");
		ProcessingResourceType processingResourceType2 = ResourcetypeFactory.eINSTANCE.createProcessingResourceType();
//		processingResourceType = href="pathmap://PCM_MODELS/Palladio.resourcetype#_BIjHoQ3KEdyouMqirZIhzQ"/>
		hft2.setProcessingResourceType__HardwareInducedFailureType(processingResourceType2);
		failures.put(Failure.HARDWARE_HDD, hft2);
		
		// DELAY
		HardwareInducedFailureType hft3 = fact.createHardwareInducedFailureType();
		hft3.setId("_41yAR0PGEd-ZmrACNr1Mew");
		hft3.setEntityName("HardwareInducedFailure (DELAY)");
		ProcessingResourceType processingResourceType3 = ResourcetypeFactory.eINSTANCE.createProcessingResourceType();
//		processingResourceType = href="pathmap://PCM_MODELS/Palladio.resourcetype#_nvHX4KkREdyEA_b89s7q9w"/>
		hft3.setProcessingResourceType__HardwareInducedFailureType(processingResourceType3);
		failures.put(Failure.HARDWARE_DELAY, hft3);
		
		// SOFTWARE
		SoftwareInducedFailureType sft = fact.createSoftwareInducedFailureType();
		sft.setId("_sLBREFf0EeW5Q6z-Ts8kIg");
		sft.setEntityName("SoftwareInducedFailure");
		failures.put(Failure.SOFTWARE, sft);
		
		//TODO: werden die gebraucht?
		fact.createExternalFailureOccurrenceDescription();
		fact.createInternalFailureOccurrenceDescription();
		fact.createResourceTimeoutFailureType();
	}
	
	public static org.palladiosimulator.pcm.reliability.FailureType getFailureType(Failure failure){
		if(failures.isEmpty())
			init();
		return failures.get(failure);
	}

}
