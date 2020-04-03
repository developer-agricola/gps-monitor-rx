/*
 * Copyright (c) OSGi Alliance (2005, 2013). All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cl.gps.monitor.gateway.gps.api.event;


/**
 * Defines standard names for {@code EventHandler} properties.
 * 
 * @author $Id: d002775d379d8da871d588ce6a6591e9089c3d09 $
 */
public interface EventConstants {

	/**
	 * Service registration property specifying the {@code Event} topics of
	 * interest to an Event Handler service.
	 * <p>
	 * Event handlers SHOULD be registered with this property. Each value of
	 * this property is a string that describe the topics in which the handler
	 * is interested. An asterisk ('*') may be used as a trailing wildcard.
	 * Event Handlers which do not have a value for this property must not
	 * receive events. More precisely, the value of each string must conform to
	 * the following grammar:
	 * 
	 * <pre>
	 *  topic-description := '*' | topic ( '/*' )?
	 *  topic := token ( '/' token )*
	 * </pre>
	 * 
	 * <p>
	 * The value of this property must be of type {@code String},
	 * {@code String[]}, or {@code Collection<String>}.
	 * 
	 * @see Event
	 */
	public static final String	EVENT_TOPIC					= "event.topics";


	/**
	 * The forwarded event object. Used when rebroadcasting an event that was
	 * sent via some other event mechanism. The type of the value for this event
	 * property is {@code Object}.
	 */
	public static final String	EVENT						= "event";

	/**
	 * An exception or error. The type of the value for this event property is
	 * {@code Throwable}.
	 */
	public static final String	EXCEPTION					= "exception";

	/**
	 * The name of the exception type. Must be equal to the name of the class of
	 * the exception in the event property {@link #EXCEPTION}. The type of the
	 * value for this event property is {@code String}.
	 * 
	 * @since 1.1
	 */
	public static final String	EXCEPTION_CLASS				= "exception.class";

	/**
	 * The exception message. Must be equal to the result of calling
	 * {@code getMessage()} on the exception in the event property
	 * {@link #EXCEPTION}. The type of the value for this event property is
	 * {@code String}.
	 */
	public static final String	EXCEPTION_MESSAGE			= "exception.message";

	/**
	 * A human-readable message that is usually not localized. The type of the
	 * value for this event property is {@code String}.
	 */
	public static final String	MESSAGE						= "message";

	/**
	 * The time when the event occurred, as reported by
	 * {@code System.currentTimeMillis()}. The type of the value for this event
	 * property is {@code Long}.
	 */
	public static final String	TIMESTAMP					= "timestamp";

	/**
	 * This constant was released with an incorrectly spelled name. It has been
	 * replaced by {@link #EXCEPTION_CLASS}
	 * 
	 * @deprecated As of 1.1, replaced by EXCEPTION_CLASS
	 */
	public static final String	EXECPTION_CLASS				= "exception.class";
}
