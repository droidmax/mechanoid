/*
 * generated by Xtext
 */
package com.robotoworks.mechanoid.ops.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.builder.IXtextBuilderParticipant;

import com.robotoworks.mechanoid.common.MechanoidBuilderParticipant;

/**
 * Use this class to register components to be used within the IDE.
 */
public class OpServiceModelUiModule extends com.robotoworks.mechanoid.ops.ui.AbstractOpServiceModelUiModule {
	public OpServiceModelUiModule(AbstractUIPlugin plugin) {
		super(plugin);
	}
	
	@Override
	public Class<? extends IXtextBuilderParticipant> bindIXtextBuilderParticipant() {
		return MechanoidBuilderParticipant.class;
	}
}
