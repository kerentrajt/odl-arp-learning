/*
 * Copyright (c) 2016 KEL inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.arplearn;

import java.math.BigInteger;

import org.opendaylight.arplearn.util.DBUtils;
import org.opendaylight.arplearn.util.FlowUtils;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.genius.mdsalutil.interfaces.IMdsalApiManager;

public class ArpManager {

	private final DataBroker dataBroker;
	private final IMdsalApiManager mdsalApiManager;

	public ArpManager(final DataBroker dataBroker, //
			final IMdsalApiManager mdsalApiManager) {
		this.dataBroker = dataBroker;
		this.mdsalApiManager = mdsalApiManager;
	}

	public void start() {

	}

	public void learn(BigInteger dpnId, short ofport, String mac, String ip) {
		DBUtils.store(dataBroker, dpnId, ofport, mac, ip);
		FlowUtils.createArpResponseFlow(mdsalApiManager, dpnId, ofport, mac, ip);
	}

}
