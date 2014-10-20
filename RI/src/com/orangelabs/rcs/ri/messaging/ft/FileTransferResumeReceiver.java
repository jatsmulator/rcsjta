/*******************************************************************************
 * Software Name : RCS IMS Stack
 *
 * Copyright (C) 2010 France Telecom S.A.
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
 ******************************************************************************/

package com.orangelabs.rcs.ri.messaging.ft;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * File transfer resume receiver
 * 
 * @author YPLO6403
 *
 */
public class FileTransferResumeReceiver extends BroadcastReceiver {
	/**
	 * Action FT is resuming
	 */
	static final String ACTION_FT_RESUME = "FT_RESUME";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent receiverIntent = new Intent(context, FileTransferIntentService.class);
		receiverIntent.putExtras(intent);
		receiverIntent.setAction(ACTION_FT_RESUME);
		context.startService(receiverIntent);
	}
}
