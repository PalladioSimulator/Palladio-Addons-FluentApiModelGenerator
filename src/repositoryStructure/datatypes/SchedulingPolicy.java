package repositoryStructure.datatypes;

/**
 * Represents built-in scheduling policies available in the resource repository,
 * i.e. '<em><b>PROCESSOR_SHARING</b></em>',
 * '<em><b>FIRST_COME_FIRST_SERVE</b></em>', '<em><b>DELAY</b></em>'.
 * 
 * @author Louisa Lambrecht
 */
public enum SchedulingPolicy {
	/**
	 * processor sharing scheduling policy
	 */
	PROCESSOR_SHARING,
	/**
	 * first come first serve scheduling policy
	 */
	FIRST_COME_FIRST_SERVE,
	/**
	 * delay scheduling policy
	 */
	DELAY;
}