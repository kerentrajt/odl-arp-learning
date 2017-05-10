/*
 * Copyright (c) 2016 KEL inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.arplearn.packet;

import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.UnknownHostException;
import java.util.Arrays;

import org.opendaylight.arplearn.ArpManager;
import org.opendaylight.controller.liblldp.NetUtils;
import org.opendaylight.controller.liblldp.Packet;
import org.opendaylight.controller.liblldp.PacketException;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.genius.mdsalutil.packet.ARP;
import org.opendaylight.genius.mdsalutil.packet.Ethernet;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.yang.types.rev130715.MacAddress;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingListener;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketReceived;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.util.NetUtil;

public class ArpPacketListener implements PacketProcessingListener{
	private final PacketProcessingService pktService;
	private final ArpManager arpManager;
	private final DataBroker dataBroker;
	private static final Logger LOG = LoggerFactory.getLogger(ArpPacketListener.class);

	public ArpPacketListener(//
			final DataBroker dataBroker, final ArpManager arpManager, //
			final PacketProcessingService pktService) {
		this.arpManager = arpManager;
		this.pktService = pktService;
		this.dataBroker = dataBroker;
	}

	@Override
	public void onPacketReceived(PacketReceived notification) {
		String[] split = notification.getIngress().getValue().toString().split(":");
		BigInteger dpn = new BigInteger(split[0]);
		short ofport = Short.parseShort(split[1]);
		Ethernet ep = getEthernet(notification);
		Packet payload = ep.getPayload();
		ARP arp = null;
		if (payload instanceof ARP) {
			arp = (ARP) payload;
			return;
		}
		String mac = getMacString(arp.getSenderHardwareAddress());
		String ip = getIPString(arp.getSenderProtocolAddress());
		arpManager.learn(dpn, ofport, mac, ip);
	}

	private String getIPString(byte[] b) {
		try {
			return Inet4Address.getByAddress(b).toString();
		} catch (UnknownHostException e) {
			return null;
		}
	}

	private String getMacString(byte[] sourceMACAddress) {
		StringBuilder sb = new StringBuilder();
		for (byte b : sourceMACAddress) {
			sb.append(Integer.toHexString(b)).append(":");
		}
		return sb.substring(0, sb.length() - 1);
	}

	private Ethernet getEthernet(PacketReceived notification) {
		byte[] payload = notification.getPayload();
		Ethernet ep = new Ethernet();
		try {
			ep.deserialize(payload, 0, payload.length * NetUtils.NumBitsInAByte);
		} catch (PacketException e) {
			e.printStackTrace();
		}
		return ep;
	}

}
