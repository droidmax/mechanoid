/*
* generated by Xtext
*/
package com.robotoworks.mechanoid.net;

import org.eclipse.xtext.junit4.IInjectorProvider;

import com.google.inject.Injector;

public class NetModelUiInjectorProvider implements IInjectorProvider {
	
	public Injector getInjector() {
		return com.robotoworks.mechanoid.net.ui.internal.NetModelActivator.getInstance().getInjector("com.robotoworks.mechanoid.net.NetModel");
	}
	
}
