/*
 * Copyright (c) 2016 KEL inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.arplearn.packet;

import java.math.BigInteger;

import org.opendaylight.arplearn.ArpManager;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingListener;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketReceived;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArpPacketListener implements PacketProcessingListener {
	private final PacketProcessingService pktService;
	private final ArpManager arpManager;

	private static final Logger LOG = LoggerFactory.getLogger(ArpPacketListener.class);

	public ArpPacketListener(//
			final ArpManager arpManager, //
			final PacketProcessingService pktService) {
		this.arpManager = arpManager;
		this.pktService = pktService;
	}

	@Override
	public void onPacketReceived(PacketReceived notification) {
		BigInteger dpn = BigInteger.ZERO;
		short ofport = 0;
		String mac = "";
		String ip = "";
		arpManager.learn(dpn, ofport, mac, ip);
	}

}
