package com.robotoworks.mechanoid.ops.generator


import static extension com.robotoworks.mechanoid.common.util.Strings.*
import static extension com.robotoworks.mechanoid.ops.generator.Extensions.*
import com.robotoworks.mechanoid.ops.opServiceModel.Model
import com.robotoworks.mechanoid.ops.opServiceModel.Operation

class OperationRegistryGenerator {
		def CharSequence generate(Model model) '''		
			�var svc = model.service�
			/*
			 * Generated by Robotoworks Mechanoid
			 */
			package �model.packageName�;

			import java.util.HashMap;
			
			import com.robotoworks.mechanoid.ops.Operation;
			
			public abstract class Abstract�svc.name.pascalize�OperationRegistry {
				
				private HashMap<String, Class<? extends Operation>> mOperations;
				
				public Abstract�svc.name.pascalize�OperationRegistry() {
					
					mOperations = new HashMap<String, Class<? extends Operation>>();
					
					registerOperations();
				}
				
				protected void registerOperations(){
					�FOR op : svc.ops�
					registerOperation(Abstract�op.name.pascalize�Operation.ACTION_�op.name.underscore.toUpperCase�, �op.name.pascalize�Operation.class);
					�ENDFOR�
				}
				
				protected void registerOperation(String action, Class<? extends Operation> clazz) {
					mOperations.put(action, clazz);
				}
			
				public Class<? extends Operation> getOperation(String action) {
					return mOperations.get(action);
				}
			}
			'''
			
		def CharSequence generateStub(Model model) '''
			�var svc = model.service�
			/*
			 * Generated by Robotoworks Mechanoid
			 */
			package �model.packageName�;
			
			public class �svc.name.pascalize�OperationRegistry extends Abstract�svc.name.pascalize�OperationRegistry {
				
			}
			
		'''
}