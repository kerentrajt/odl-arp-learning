/*
 * Copyright (c) 2016 KEL inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.arplearn.node;

import java.math.BigInteger;

import org.opendaylight.arplearn.util.FlowUtils;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.genius.datastoreutils.AsyncDataTreeChangeListenerBase;
import org.opendaylight.genius.mdsalutil.interfaces.IMdsalApiManager;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.NetworkTopology;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.network.topology.Topology;
import org.opendaylight.yang.gen.v1.urn.tbd.params.xml.ns.yang.network.topology.rev131021.network.topology.topology.Node;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;

public class NodeListener extends AsyncDataTreeChangeListenerBase<Node, NodeListener> {

	private DataBroker dataBroker;
	private IMdsalApiManager mdsalApiManager;

	public NodeListener(DataBroker dataBroker, IMdsalApiManager mdsalApiManager) {
		super(Node.class, NodeListener.class);
		this.dataBroker = dataBroker;
		this.mdsalApiManager = mdsalApiManager;
	}

	public void start() {
		registerListener(LogicalDatastoreType.CONFIGURATION, dataBroker);
	}

	@Override
	protected InstanceIdentifier<Node> getWildCardPath() {
		return InstanceIdentifier.builder(NetworkTopology.class)//
				.child(Topology.class)//
				.child(Node.class)//
				.build();
	}

	@Override
	protected void remove(InstanceIdentifier<Node> key, Node dataObjectModification) {
	}

	@Override
	protected void update(InstanceIdentifier<Node> key, Node dataObjectModificationBefore,
			Node dataObjectModificationAfter) {

	}

	@Override
	protected void add(InstanceIdentifier<Node> key, Node dataObjectModification) {
		BigInteger dpnId = BigInteger.ZERO;
		FlowUtils.createDefaultFlows(mdsalApiManager, dpnId);
	}

	@Override
	protected NodeListener getDataTreeChangeListener() {
		return this;
	}

}
