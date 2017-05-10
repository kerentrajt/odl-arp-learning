/*
 * Copyright (c) 2016 KEL inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.arplearn.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opendaylight.genius.mdsalutil.ActionInfo;
import org.opendaylight.genius.mdsalutil.ActionType;
import org.opendaylight.genius.mdsalutil.InstructionInfo;
import org.opendaylight.genius.mdsalutil.InstructionType;
import org.opendaylight.genius.mdsalutil.MDSALUtil;
import org.opendaylight.genius.mdsalutil.MatchFieldType;
import org.opendaylight.genius.mdsalutil.MatchInfo;
import org.opendaylight.genius.mdsalutil.interfaces.IMdsalApiManager;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.Flow;

public class FlowUtils {

	public static void createArpResponseFlow(IMdsalApiManager mdsalApiManager, BigInteger dpnId, short ofport,
			String mac, String ip) {

	}

	public static void createDefaultFlows(IMdsalApiManager mdsalApiManager, BigInteger dpnId) {
		createDropFlow(mdsalApiManager, dpnId);
		createArpToControllerFlow(mdsalApiManager, dpnId);

	}

	private static void createDropFlow(IMdsalApiManager mdsalApiManager, BigInteger dpnId) {
		List<MatchInfo> matches = Collections.emptyList();
		List<InstructionInfo> instructions = new ArrayList<>();
		List<ActionInfo> actions = new ArrayList<>();
		actions.add(new ActionInfo(ActionType.drop_action, new String[] {}));
		instructions.add(new InstructionInfo(InstructionType.apply_actions, actions));
		installFlow(mdsalApiManager, dpnId, "DefaultDropFlow", "DefaultDropFlow", 1, matches, instructions);
	}

	private static void createArpToControllerFlow(IMdsalApiManager mdsalApiManager, BigInteger dpnId) {
		List<MatchInfo> matches = new ArrayList<>();
		matches.add(new MatchInfo(MatchFieldType.arp_op, new String[] {}));
		List<InstructionInfo> instructions = new ArrayList<>();
		List<ActionInfo> actions = new ArrayList<>();
		actions.add(new ActionInfo(ActionType.punt_to_controller, new String[] {}));
		instructions.add(new InstructionInfo(InstructionType.apply_actions, actions));
		installFlow(mdsalApiManager, dpnId, "DefaultArpFlow", "DefaultArpFlow", 1, matches, instructions);

	}

	public static void installFlow(final IMdsalApiManager mdSalManager,
			final BigInteger dpnId, final String flowId, final String flowName, final int priority,
			List<MatchInfo> matches, List<InstructionInfo> instructions) {

		final Flow flowEntity = MDSALUtil.buildFlow((short) 0, flowId, priority, flowName, 0, 0, BigInteger.ZERO,
				matches, instructions);
		mdSalManager.installFlow(dpnId, flowEntity);
	}

}
