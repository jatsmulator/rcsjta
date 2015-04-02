/*
 * Copyright (C) 2015 Sony Mobile Communications Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gsma.services.rcs;

import android.text.TextUtils;

/**
 * Rcs unsupported operation exception.
 * <p>
 * Thrown when a method of the service API is called that is not supported (ie does not make sense
 * within the scope of the use case) like trying to call pauseTransfer() on a non pausable file
 * transfer that does not support that etc.
 * </p>
 */
public class RcsUnsupportedOperationException extends UnsupportedOperationException {

    /**
     * Special Delimiter that will be appended while trasmitting server exceptions over AIDL layer.
     */
    private static final char DELIMITER_PIPE = '|';

    static final long serialVersionUID = 1L;

    /**
     * Constructor
     * 
     * @param message Error message obtained either from a constant string or through e.getMessage()
     */
    public RcsUnsupportedOperationException(String message) {
        super(message);
    }

    /**
     * Constructor
     * 
     * @param message Error message obtained either from a constant string or through e.getMessage()
     * @param cause the cause (which is saved for later retrieval by the Throwable.getCause()
     *            method). (A null value is permitted, and indicates that the cause is nonexistent
     *            or unknown.)
     */
    public RcsUnsupportedOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Asserts {@link RcsUnsupportedOperationException}
     * <p>
     * An utility method that will translate the Server side exception to client specific exception
     * by parsing exception message which will have a special formatted exception message with a
     * pre-defined delimiter.
     * </p>
     * 
     * @param e Exception
     * @throws RcsUnsupportedOperationException
     */
    public static void assertException(Exception e) throws RcsUnsupportedOperationException {
        if (isIntendedException(e, RcsUnsupportedOperationException.class)) {
            throw new RcsUnsupportedOperationException(extractServerException(e), e);
        }
    }

    /**
     * Checks if the exception is one of the intended server side exception that has been thrown
     * over the AIDL layer.
     * 
     * @param e Exception
     * @param clazz Class
     * @return true if exception getMessage() starts with clazz getName()
     */
    private static boolean isIntendedException(Exception e, Class clazz) {
        final String message = e.getMessage();
        return (!TextUtils.isEmpty(message) && message.startsWith(clazz.getName()));
    }

    /**
     * Extracts server side exception message thrown over the AIDL layer after parsing it based on
     * {@link DELIMITER_PIPE}
     * 
     * @param e Exception
     * @return Server exception message
     */
    private static String extractServerException(Exception e) {
        final String message = e.getMessage();
        final int delimiterIndex = message.indexOf(DELIMITER_PIPE);
        if (delimiterIndex > 0 && delimiterIndex < message.length() - 1) {
            return message.substring(delimiterIndex + 1);
        }
        return message;
    }
}
