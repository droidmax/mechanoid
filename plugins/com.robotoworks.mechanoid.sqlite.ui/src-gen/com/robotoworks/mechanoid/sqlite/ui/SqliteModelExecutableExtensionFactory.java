/*
 * generated by Xtext
 */
package com.robotoworks.mechanoid.sqlite.ui;

import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;

import com.google.inject.Injector;

import com.robotoworks.mechanoid.sqlite.ui.internal.SqliteModelActivator;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class SqliteModelExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return SqliteModelActivator.getInstance().getBundle();
	}
	
	@Override
	protected Injector getInjector() {
		return SqliteModelActivator.getInstance().getInjector(SqliteModelActivator.COM_ROBOTOWORKS_MECHANOID_SQLITE_SQLITEMODEL);
	}
	
}
