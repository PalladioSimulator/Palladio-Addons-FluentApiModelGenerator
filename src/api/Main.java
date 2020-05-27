package api;

import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RepositoryFactory r = RepositoryFactory.eINSTANCE;
		
		BasicComponent c = r.createBasicComponent();
		
	}

}
