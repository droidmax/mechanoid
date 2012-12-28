package com.robotoworks.mechanoid.sqlite.generator

import com.robotoworks.mechanoid.sqlite.sqliteModel.CreateTableStatement
import com.robotoworks.mechanoid.sqlite.sqliteModel.CreateViewStatement
import com.robotoworks.mechanoid.sqlite.sqliteModel.MigrationBlock
import com.robotoworks.mechanoid.sqlite.sqliteModel.Model
import com.robotoworks.mechanoid.sqlite.sqliteModel.ResultColumnAll
import com.robotoworks.mechanoid.sqlite.sqliteModel.ResultColumnExpression

import static extension com.robotoworks.mechanoid.sqlite.generator.Extensions.*
import static extension com.robotoworks.mechanoid.common.util.Strings.*
import com.robotoworks.mechanoid.sqlite.sqliteModel.ActionStatement

class ContentProviderContractGenerator {
		def CharSequence generate(Model model, MigrationBlock snapshot) { 
			'''
			/*
			 * Generated by Robotoworks Mechanoid
			 */
			package �model.packageName�;
			
			import android.content.ContentValues;
			import android.net.Uri;
			import android.provider.BaseColumns;
			import android.content.ContentResolver;
			import com.robotoworks.mechanoid.sqlite.SQuery;
			import com.robotoworks.mechanoid.Mechanoid;
			
			public class �model.database.name.pascalize�Contract  {
			    public static final String CONTENT_AUTHORITY = "�model.packageName�.�model.database.name.toLowerCase�";
			
			    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
			
				�FOR tbl : snapshot.statements.filter(typeof(CreateTableStatement))�
				interface �tbl.name.pascalize�Columns {
					�FOR col : tbl.columnDefs.filter([!name.equals("_id")])�
					String �col.name.underscore.toUpperCase� = "�col.name�";
					�ENDFOR�
				}
				
				�ENDFOR�

				�FOR vw :  snapshot.statements.filter(typeof(CreateViewStatement))�
				interface �vw.name.pascalize�Columns {
					�FOR col : vw.selectStatement.coreStatements.get(0).resultColumns�
					�generateInterfaceMemberForResultColumn(col)�
					�ENDFOR�
				}
				
				�ENDFOR�
						
				�FOR tbl : snapshot.statements.filter(typeof(CreateTableStatement))�
				/**
				 * <p>Column definitions and helper methods to work with the �tbl.name.pascalize� table.</p>
				 */
				public static class �tbl.name.pascalize� implements �tbl.name.pascalize�Columns�IF tbl.hasAndroidPrimaryKey�, BaseColumns�ENDIF� {
				    public static final Uri CONTENT_URI = 
							BASE_CONTENT_URI.buildUpon().appendPath("�tbl.name.toLowerCase�").build();
				
					/**
					 * <p>The content type for a cursor that contains many �tbl.name.pascalize� table rows.</p>
					 */
				    public static final String CONTENT_TYPE =
				            "vnd.android.cursor.dir/vnd.�model.database.name.toLowerCase�.�tbl.name�";
					/**
					 * <p>The content type for a cursor that contains a single �tbl.name.pascalize� table row.</p>
					 */
				    public static final String ITEM_CONTENT_TYPE =
				            "vnd.android.cursor.item/vnd.�model.database.name.toLowerCase�.�tbl.name�";
				
					/**
					 * <p>Builds a Uri with appended id for a row in the �tbl.name.pascalize� table, 
					 * eg:- content://�model.packageName�.�model.database.name.toLowerCase�/�tbl.name.toLowerCase�/123.</p>
					 */
				    public static Uri buildUriWithId(long id) {
				        return CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
				    }
				
					public static ContentValues createContentValues(�createMethodArgsFromColumns(tbl)�) {
						ContentValues values = new ContentValues();
						�FOR col : tbl.columnDefs.filter([!name.equals("_id")])�
						values.put(�tbl.name.pascalize�.�col.name.underscore.toUpperCase�, �col.name.camelize�);
						�ENDFOR�
						return values;
					}
					
					�var insertArgs = createMethodArgsFromColumns(tbl)�
					public static Uri insert(ContentResolver contentResolver�IF insertArgs != null || insertArgs.length > 0�, �insertArgs��ENDIF�) {
						ContentValues values = createContentValues(
						�FOR col : tbl.columnDefs.filter([!name.equals("_id")]) SEPARATOR ", "�
							�col.name.camelize�
						�ENDFOR�
						);
						return contentResolver.insert(CONTENT_URI, values);
					}
					
					public static int delete(ContentResolver contentResolver) {
						return contentResolver.delete(CONTENT_URI, null, null);
					}
					
					public static int delete(ContentResolver contentResolver, String where, String[] selectionArgs) {
						return contentResolver.delete(CONTENT_URI, where, selectionArgs);
					}
					
					
					
					/**
					 * <p>Create a new Builder for �tbl.name.pascalize�</p>
					 */
					public static Builder newBuilder() {
						return new Builder();
					}
					
					/**
					 * <p>Build and execute insert or update statements for �tbl.name.pascalize�.</p>
					 */
					public static class Builder {
						private ContentValues mValues = new ContentValues();
						
						�FOR col : tbl.columnDefs.filter([!name.equals("_id")])�
						public Builder set�col.name.pascalize�(�col.type.toJavaTypeName� value) {
							mValues.put(�tbl.name.pascalize�.�col.name.underscore.toUpperCase�, value);
							return this;
						}
						�ENDFOR�
						
						/**
						 * <p>Insert into �tbl.name.pascalize� with the values set on this builder.</p>
						 */								
						public Uri insert() {
							ContentResolver resolver = Mechanoid.getContentResolver();
							return resolver.insert(CONTENT_URI, mValues);
						}
						
						/**
						 * <p>Update �tbl.name.pascalize� with the given query</p>
						 */						
						public int update(SQuery query) {
							ContentResolver resolver = Mechanoid.getContentResolver();
							return resolver.update(CONTENT_URI, mValues, query.toString(), query.getArgsArray());
						}
						
						�IF tbl.hasAndroidPrimaryKey�
						/**
						 * <p>Update �tbl.name.pascalize� with the given id</p>
						 */
						public int update(long id) {
							ContentResolver resolver = Mechanoid.getContentResolver();
							return resolver.update(CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build(), mValues, null, null);
						}
						
						�ENDIF�
						/**
						 * <p>Get ContentValues built so far by this builder for �tbl.name.pascalize�.</p>
						 */						
						public ContentValues getValues() {
							return mValues;
						}
					}
				}
				
				�ENDFOR�
			
				�FOR vw : snapshot.statements.filter(typeof(CreateViewStatement))�
				public static class �vw.name.pascalize� implements �vw.name.pascalize�Columns�IF vw.hasAndroidPrimaryKey�, BaseColumns�ENDIF� {
				    public static final Uri CONTENT_URI = 
							BASE_CONTENT_URI.buildUpon().appendPath("�vw.name�").build();
				
				    public static final String CONTENT_TYPE =
				            "vnd.android.cursor.dir/vnd.�model.database.name.toLowerCase�.�vw.name�";
					�IF vw.hasAndroidPrimaryKey�
					public static final String ITEM_CONTENT_TYPE =
						"vnd.android.cursor.item/vnd.�model.database.name.toLowerCase�.�vw.name�";
					�ENDIF�
				}

				�ENDFOR�
				
				�IF model.database.config != null�
				�FOR action : model.database.config.statements.filter([it instanceof ActionStatement])�
				�var stmt = action as ActionStatement�
				public static class �action.name.pascalize� {
				    public static final Uri CONTENT_URI = 
							BASE_CONTENT_URI.buildUpon().appendPath("�stmt.path�").build();
				
				    public static final String CONTENT_TYPE =
				            "vnd.android.cursor.dir/vnd.�model.database.name.toLowerCase�.�action.name�";
				}

				�ENDFOR�
				�ENDIF�
			
				private �model.database.name.pascalize�Contract(){}
			}
			'''
	}
	
	def createMethodArgsFromColumns(CreateTableStatement tbl) {
		'''�FOR col : tbl.columnDefs.filter([!name.equals("_id")]) SEPARATOR ", "��col.type.toJavaTypeName()� �col.name.camelize��ENDFOR�'''
	}
	
	def dispatch generateInterfaceMemberForResultColumn(ResultColumnAll column) { 
	}
	
	def dispatch generateInterfaceMemberForResultColumn(ResultColumnExpression column) { 
		'''
		�IF column.alias != null && !column.alias.equals("") && !column.alias.equals("_id")�
		String �column.alias.underscore.toUpperCase� = "�column.alias�";
		�ENDIF�
		'''		
	}
	
}