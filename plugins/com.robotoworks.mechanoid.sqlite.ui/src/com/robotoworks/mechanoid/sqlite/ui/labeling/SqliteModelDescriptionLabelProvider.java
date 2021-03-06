/*
 * generated by Xtext
 */
package com.robotoworks.mechanoid.sqlite.ui.labeling;

import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.ui.label.DefaultDescriptionLabelProvider;

/**
 * Provides labels for a IEObjectDescriptions and IResourceDescriptions.
 * 
 * see
 * http://www.eclipse.org/Xtext/documentation/latest/xtext.html#labelProvider
 */
public class SqliteModelDescriptionLabelProvider extends
		DefaultDescriptionLabelProvider {

	/*
	 * //Labels and icons can be computed like this:
	 * 
	 * String text(IEObjectDescription ele) { return "my "+ele.getName(); }
	 * 
	 * String image(IEObjectDescription ele) { return ele.getEClass().getName()
	 * + ".gif"; }
	 */

	@Override
	public String image(IEObjectDescription ele) {
		String image = ele.getEClass().getName() + ".gif";

		return image;
	}
}