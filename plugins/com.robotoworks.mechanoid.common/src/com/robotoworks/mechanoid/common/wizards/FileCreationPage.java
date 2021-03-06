package com.robotoworks.mechanoid.common.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

class FileCreationPage extends WizardNewFileCreationPage {
	
	private String extension;

	public FileCreationPage(IStructuredSelection selection, String extension) {
		super("setFileName", selection);
		this.extension = extension;
		
		setTitle("Create Database (*." + extension + ") File");
		setFileName("untitled." + extension);
	}
	
	 @Override
	protected boolean validatePage() {
		if(!super.validatePage()) {
			return false;
		}
		if(getFileName().endsWith(extension)) {
			setErrorMessage(null);
			return true;
		}
		
		setErrorMessage("Create a valid (*." + extension + ") file");
		
		return false;
	}
}