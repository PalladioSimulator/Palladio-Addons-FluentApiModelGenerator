package resourceEvironment.structure;

import org.palladiosimulator.pcm.resourceenvironment.LinkingResource;

public class LinkingResourceCreator extends ResourceEntity {

	public LinkingResourceCreator(ResourceEnvironmentCreator resourceEnvironmentCreator) {
		this.resourceCreator = resourceEnvironmentCreator;
	}
	
	@Override
	public LinkingResourceCreator withName(String name) {
		return (LinkingResourceCreator) super.withName(name);
	}
	
	@Override
	public LinkingResource build() {
		// TODO Auto-generated method stub
		return null;
	}

}
