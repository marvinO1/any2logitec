package nca.any2logitec.api;

/**
 * Logic components have to implement to be used as an adapter to some information source.
 */
public interface Adapter {

	/**
	 * Produces the information. This function gets call periodically from the {@link LogitecHub}
	 * which acts as container.
	 */
	void produceInfo();
}
