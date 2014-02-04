package fr.um2.orders;


/**
 * 
 * @author Mojdeh
 *	ORder Type
 */
public enum OrderType {
	/**
	 * Will be ignored
	 */
	UNKNOWN, 			
	/**
	 * To request/response location
	 */
	LOCATION,
	RESPONSE_LOCATION,
	/**
	 *  For Notification
	 */
	NOTIFICATION,
	/**
	 * For BLOQUE
	 */
	KILL,
	/**
	 * For DESINSTALLER
	 */
	UINSTALL,
}
