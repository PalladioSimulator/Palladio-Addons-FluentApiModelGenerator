package component.apiControlFlowInterfaces;

import org.palladiosimulator.pcm.reliability.FailureType;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.Repository;

import component.repositoryStructure.components.Component;
import component.repositoryStructure.interfaces.Interface;
import component.repositoryStructure.types.CompositeDataTypeCreator;
import component.repositoryStructure.types.ExceptionTypeCreator;
import component.repositoryStructure.types.ResourceTimeoutFailureTypeCreator;

public interface RepoAddition {

    /**
     * Adds the <code>collectionDataType</code> to the list of data types provided
     * by this repository.
     * <p>
     * The <code>collectionDataType</code> represents a collection data type, e.g. a
     * list, array, set of items of the particular type.<br>
     * The <code>collectionDataType</code> can be created using the
     * component.factory, i.e. <code>create.newCollectionDataType()</code>
     * </p>
     *
     * @param collectionDataType
     * @return this repository, now containing the <code>collectionDataType</code>
     * @see component.factory.FluentRepositoryFactory#newCollectionDataType(String,
     *      component.repositoryStructure.datatypes.Primitive)
     * @see component.factory.FluentRepositoryFactory#newCollectionDataType(String,
     *      org.palladiosimulator.pcm.repository.DataType)
     * @see org.palladiosimulator.pcm.repository.CollectionDataType
     */
    RepoAddition addToRepository(CollectionDataType collectionDataType);

    /**
     * Adds the <code>compositeDataType</code> to the list of data types provided by
     * this repository.
     * <p>
     * The <code>compositeDataType</code> represents a complex data type containing
     * other data types. This construct is common in higher programming languages as
     * record, struct, or class.<br>
     * The <code>compositeDataType</code> can be created using the
     * component.factory, i.e. <code>create.newCompositeDataType()</code>
     * </p>
     *
     * @param compositeDataType
     * @return this repository, now containing the <code>compositeDataType</code>
     * @see component.factory.FluentRepositoryFactory#newCompositeDataType(String,
     *      org.palladiosimulator.pcm.repository.CompositeDataType...)
     * @see org.palladiosimulator.pcm.repository.CompositeDataType
     */
    RepoAddition addToRepository(CompositeDataTypeCreator compositeDataType);

    /**
     * Adds the <code>failureType</code> to the list of failure types provided by
     * this repository.
     *
     * @param failureType
     * @return this repository, now containing the <code>failureType</code>
     * @see component.factory.FluentRepositoryFactory#newHardwareInducedFailureType(String,
     *      component.repositoryStructure.datatypes.ProcessingResource)
     * @see component.factory.FluentRepositoryFactory#newNetworkInducedFailureType(String,
     *      component.repositoryStructure.datatypes.CommunicationLinkResource)
     * @see component.factory.FluentRepositoryFactory#newSoftwareInducedFailureType(String)
     * @see component.factory.FluentRepositoryFactory#newResourceTimeoutFailureType(String)
     * @see org.palladiosimulator.pcm.reliability.FailureType
     */
    RepoAddition addToRepository(FailureType failureType);

    /**
     * Adds the <code>failureType</code> to the list of failure types provided by
     * this repository.
     *
     * @param failureType
     * @return this repository, now containing the <code>failureType</code>
     * @see component.factory.FluentRepositoryFactory#newHardwareInducedFailureType(String,
     *      component.repositoryStructure.datatypes.ProcessingResource)
     * @see component.factory.FluentRepositoryFactory#newNetworkInducedFailureType(String,
     *      component.repositoryStructure.datatypes.CommunicationLinkResource)
     * @see component.factory.FluentRepositoryFactory#newSoftwareInducedFailureType(String)
     * @see component.factory.FluentRepositoryFactory#newResourceTimeoutFailureType(String)
     * @see org.palladiosimulator.pcm.reliability.FailureType
     */
    RepoAddition addToRepository(ResourceTimeoutFailureTypeCreator failureType);

    /**
     * Adds the <code>exceptionType</code> to the list of exception types provided
     * by this repository.
     *
     * @param exceptionType
     * @return this repository, now containing the <code>exceptionType</code>
     * @see component.factory.FluentRepositoryFactory#newExceptionType()
     * @see org.palladiosimulator.pcm.repository.ExceptionType
     */
    RepoAddition addToRepository(ExceptionTypeCreator exceptionType);

    /**
     * Adds the <code>component</code> to the list of components stored in the
     * repository.
     * <p>
     * Components are atomic building blocks of a software architecture. There are 5
     * different types of components: '<em><b>BasicComponent</b></em>',
     * '<em><b>CompositeComponent</b></em>', '<em><b>SubSystem</b></em>',
     * '<em><b>CompleteComponentType</b></em>',
     * '<em><b>ProvidesComponentType</b></em>'.<br>
     * The <code>component</code> can be created using the component.factory, e.g.
     * <code>create.newBasicComponent()</code> etc.
     * </p>
     *
     * @param component
     * @return this repository, now containing the <code>component</code>
     * @see component.factory.FluentRepositoryFactory#newBasicComponent()
     * @see component.factory.FluentRepositoryFactory#newCompositeComponent()
     * @see component.factory.FluentRepositoryFactory#newSubSystem()
     * @see component.factory.FluentRepositoryFactory#newCompleteComponentType()
     * @see component.factory.FluentRepositoryFactory#newProvidesComponentType()
     * @see org.palladiosimulator.pcm.repository.RepositoryComponent
     */
    RepoAddition addToRepository(Component component);

    /**
     * Adds the <code>interfce</code> to the list of interfaces stored in the
     * repository.
     * <p>
     * Interfaces are modeled as a set of signatures representing services provided
     * or required by a component. There are 3 different types of interfaces:
     * '<em><b>OperationInterface</b></em>', '<em><b>EventGroup</b></em>',
     * '<em><b>InfrastructureInterface</b></em>'.<br>
     * The <code>interfce</code> can be created using the component.factory, e.g.
     * <code>create.newOperationInterface()</code> etc.
     * </p>
     *
     * @param interfce
     * @return this repository, now containing the <code>interfce</code>
     * @see component.factory.FluentRepositoryFactory#newOperationInterface()
     * @see component.factory.FluentRepositoryFactory#newEventGroup()
     * @see component.factory.FluentRepositoryFactory#newInfrastructureInterface()
     * @see org.palladiosimulator.pcm.repository.Interface
     */
    RepoAddition addToRepository(Interface interfce);

    /**
     * Turns this repository-in-the-making into a
     * Palladio-'<em><b>Repository</b></em>' object.
     *
     * @return the final repository object
     * @see org.palladiosimulator.pcm.repository.Repository
     */
    Repository createRepositoryNow();
}
