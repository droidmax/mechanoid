/*
 * generated by Xtext
 */
package com.robotoworks.mechanoid.sharedprefs.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.builder.IXtextBuilderParticipant;

import com.robotoworks.mechanoid.common.MechanoidBuilderParticipant;

/**
 * Use this class to register components to be used within the IDE.
 */
public class SharedPreferencesModelUiModule extends com.robotoworks.mechanoid.sharedprefs.ui.AbstractSharedPreferencesModelUiModule {
	public SharedPreferencesModelUiModule(AbstractUIPlugin plugin) {
		super(plugin);
	}
	
	@Override
	public Class<? extends IXtextBuilderParticipant> bindIXtextBuilderParticipant() {
		return MechanoidBuilderParticipant.class;
	}	
}
