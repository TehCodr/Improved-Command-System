/**  
 * UnactivatedManagerException.java
 * 
 * @section LICENSE
 * 
 * Permission is hereby granted, free of charge, to
 * any person obtaining a copy of this software and
 * associated documentation files (the "Software"),
 * to deal in the Software without restriction,
 * including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */

package com.tehcodr.util;

/**
 * The exception thrown when you register a command with an Un-activate()'ed CommandManager.
 * 
 * @author Omri Barak, TehCodr
 * @version 1.0
 */
public class UnactivatedManagerException extends Exception {
	/**
	 * Value for serialization/deserialization only.
	 */
	private static final long serialVersionUID = -4195847360622840710L;
	
	/**
	 * Exception initializer.
	 */
	public UnactivatedManagerException() {
		super();
	}
	
	/**
	 * Exception initializer, with a message rundown.
	 * @param message
	 */
	public UnactivatedManagerException(String message) {
		super(message);
	}
	
	/**
	 * Exception initializer, with a message rundown, and a reason.
	 * @param message
	 * @param cause
	 */
	public UnactivatedManagerException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Exception initializer, with a reason.
	 * @param cause
	 */
	public UnactivatedManagerException(Throwable cause) {
		super(cause);
	}
}
