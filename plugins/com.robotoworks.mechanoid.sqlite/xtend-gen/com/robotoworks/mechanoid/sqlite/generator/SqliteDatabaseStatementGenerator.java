package com.robotoworks.mechanoid.sqlite.generator;

import com.google.inject.Inject;
import com.robotoworks.mechanoid.sqlite.sqliteModel.DDLStatement;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.resource.SaveOptions.Builder;
import org.eclipse.xtext.serializer.ISerializer;

@SuppressWarnings("all")
public class SqliteDatabaseStatementGenerator {
  @Inject
  private ISerializer _iSerializer;
  
  private SaveOptions saveOptions;
  
  public SqliteDatabaseStatementGenerator() {
    Builder _newBuilder = SaveOptions.newBuilder();
    Builder _noValidation = _newBuilder.noValidation();
    SaveOptions _options = _noValidation.getOptions();
    this.saveOptions = _options;
  }
  
  public CharSequence generateStatements(final Iterable<DDLStatement> statements) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final DDLStatement stmt : statements) {
        _builder.append("db.execSQL(");
        _builder.newLine();
        {
          String _serialize = this._iSerializer.serialize(stmt, this.saveOptions);
          String _trim = _serialize.trim();
          String[] _split = _trim.split("\\r?\\n");
          boolean _hasElements = false;
          for(final String line : _split) {
            if (!_hasElements) {
              _hasElements = true;
            } else {
              _builder.appendImmediate(" +", "	");
            }
            _builder.append("\t");
            _builder.append("\"");
            String _trim_1 = line.trim();
            String _replaceAll = _trim_1.replaceAll("\\\"", "\\\\\"");
            _builder.append(_replaceAll, "	");
            _builder.append(" \"");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append(");");
        _builder.newLine();
        _builder.newLine();
      }
    }
    return _builder;
  }
}
