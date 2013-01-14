/*
 * Generated by Robotoworks Mechanoid
 */
package com.robotoworks.examples.recipes.content.actions;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.net.Uri;
import com.robotoworks.mechanoid.content.ContentProviderActions;
import com.robotoworks.mechanoid.content.MechanoidContentProvider;
import com.robotoworks.mechanoid.sqlite.SQuery;
import com.robotoworks.mechanoid.sqlite.ActiveRecord;
import java.util.ArrayList;
import java.util.List;
import com.robotoworks.mechanoid.util.Closeables;

import com.robotoworks.examples.recipes.content.RecipesRecord;
import com.robotoworks.examples.recipes.content.AbstractRecipesDBOpenHelper.Tables;
import com.robotoworks.examples.recipes.content.RecipesDBContract.Recipes;
			
public abstract class AbstractRecipesByIdActions extends ContentProviderActions {
	@Override
	public int delete(MechanoidContentProvider provider, Uri uri, String selection, String[] selectionArgs){
		
		
		final SQLiteDatabase db = provider.getOpenHelper().getWritableDatabase();
		
		int affected = SQuery.newQuery()
		.expr(Recipes._ID, SQuery.Op.EQ, uri.getPathSegments().get(1))
		.append(selection, selectionArgs)
		.delete(db, Tables.RECIPES);
		
		return affected;
	}
	
	@Override
	public Uri insert(MechanoidContentProvider provider, Uri uri, ContentValues values){
		return null; // Cannot insert on _id
	}
	
	@Override
	public int update(MechanoidContentProvider provider, Uri uri, ContentValues values, String selection, String[] selectionArgs){
		final SQLiteDatabase db = provider.getOpenHelper().getWritableDatabase();
		
		int affected = SQuery.newQuery()
		.expr(Recipes._ID, SQuery.Op.EQ, uri.getPathSegments().get(1))
		.append(selection, selectionArgs)
		.update(db, Tables.RECIPES, values);
		
		return affected;
	}

	@Override
	public Cursor query(MechanoidContentProvider provider, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder){
		final SQLiteDatabase db = provider.getOpenHelper().getWritableDatabase();
		
		return SQuery.newQuery()
		.expr(Recipes._ID, SQuery.Op.EQ, uri.getPathSegments().get(1))
		.append(selection, selectionArgs)
		.query(db, Tables.RECIPES, projection, sortOrder);
	}
	
	@Override
    public int bulkInsert(MechanoidContentProvider provider, Uri uri, ContentValues[] values) {

    	final SQLiteDatabase db = provider.getOpenHelper().getWritableDatabase();
    	
    	int numValues = values.length;

    	try {
    	
	    	db.beginTransaction();
	    	
	        for (int i = 0; i < numValues; i++) {
	        	db.insertOrThrow(Tables.RECIPES, null, values[i]);
	        }
			
			db.setTransactionSuccessful();

			
    	} finally {
    		db.endTransaction();
    	}
    	
    	return numValues;
    }
    
	@SuppressWarnings("unchecked")
	@Override
	public <T extends ActiveRecord> List<T> selectRecords(MechanoidContentProvider provider, Uri uri, SQuery sQuery, String sortOrder) {
		final SQLiteDatabase db = provider.getOpenHelper().getWritableDatabase();
		
		Cursor c = null;
		
		ArrayList<T> items = new ArrayList<T>();
		
		try {
			c = db.query(Tables.RECIPES, RecipesRecord.PROJECTION, sQuery.toString(), sQuery.getArgsArray(), null, null, sortOrder);
		    
		    while(c.moveToNext()) {
		        items.add((T)RecipesRecord.fromCursor(c));
	        }
	    } finally {
	        Closeables.closeSilently(c);
	    }
	    
	    return items;
	}
}
