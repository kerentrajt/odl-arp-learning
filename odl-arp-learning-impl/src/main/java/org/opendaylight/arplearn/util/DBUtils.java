/*
 * Copyright (c) 2016 KEL inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.arplearn.util;

import java.math.BigInteger;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.genius.mdsalutil.MDSALUtil;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.odl.calculator.api.api.rev170507.OdlArpApi;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.odl.calculator.api.api.rev170507.odl.arp.api.IpToMac;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.odl.calculator.api.api.rev170507.odl.arp.api.IpToMacBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.odl.calculator.api.api.rev170507.odl.arp.api.IpToMacKey;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;

public class DBUtils {

	public static void store(DataBroker dataBroker, BigInteger dpnId, short ofport, String mac, String ip) {

		IpToMac dataObject = new IpToMacBuilder().setIpAddress(ip).setMac(mac).build();

		MDSALUtil.syncWrite(dataBroker, LogicalDatastoreType.CONFIGURATION, //
				InstanceIdentifier.builder(OdlArpApi.class)//
						.child(IpToMac.class, new IpToMacKey(ip))//
						.build(),
				dataObject);
	}

	/**
	 * ArrayList<String> parsedQuery = parseQuery(query); List<Query> list =
	 * parsedQuery.stream().map(x -> new QueryBuilder().setObject(x).build())
	 * .collect(Collectors.toList());
	 * 
	 * ParserApi dataObject = new ParserApiBuilder().setQuery(list).build();
	 * MDSALUtil.syncWrite(databroker, LogicalDatastoreType.CONFIGURATION,
	 * InstanceIdentifier.builder(ParserApi.class).build(), dataObject);
	 * InstanceIdentifier.builder(ElanDpnInterfaces.class)
	 * .child(ElanDpnInterfacesList.class, new
	 * ElanDpnInterfacesListKey(elanInstanceName)).build();
	 **/

}
