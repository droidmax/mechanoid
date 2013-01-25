package com.robotoworks.mechanoid.sqlite.generator;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.robotoworks.mechanoid.common.util.Strings;
import com.robotoworks.mechanoid.sqlite.generator.SqliteDatabaseStatementGenerator;
import com.robotoworks.mechanoid.sqlite.sqliteModel.CreateTableStatement;
import com.robotoworks.mechanoid.sqlite.sqliteModel.CreateTriggerStatement;
import com.robotoworks.mechanoid.sqlite.sqliteModel.CreateViewStatement;
import com.robotoworks.mechanoid.sqlite.sqliteModel.DDLStatement;
import com.robotoworks.mechanoid.sqlite.sqliteModel.DatabaseBlock;
import com.robotoworks.mechanoid.sqlite.sqliteModel.MigrationBlock;
import com.robotoworks.mechanoid.sqlite.sqliteModel.Model;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class SqliteOpenHelperGenerator {
  @Inject
  private SqliteDatabaseStatementGenerator _sqliteDatabaseStatementGenerator;
  
  public CharSequence generate(final Model model, final MigrationBlock snapshot) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("/*");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* Generated by Robotoworks Mechanoid");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("package ");
    String _packageName = model.getPackageName();
    _builder.append(_packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import android.content.Context;");
    _builder.newLine();
    _builder.append("import android.database.sqlite.SQLiteDatabase;");
    _builder.newLine();
    _builder.append("import com.robotoworks.mechanoid.sqlite.MechanoidSQLiteOpenHelper;");
    _builder.newLine();
    _builder.append("import com.robotoworks.mechanoid.sqlite.SQLiteMigration;");
    _builder.newLine();
    _builder.newLine();
    {
      DatabaseBlock _database = model.getDatabase();
      EList<MigrationBlock> _migrations = _database.getMigrations();
      int _size = _migrations.size();
      boolean _greaterThan = (_size > 1);
      if (_greaterThan) {
        int version = 1;
        _builder.newLineIfNotEmpty();
        {
          DatabaseBlock _database_1 = model.getDatabase();
          EList<MigrationBlock> _migrations_1 = _database_1.getMigrations();
          Iterable<MigrationBlock> _drop = IterableExtensions.<MigrationBlock>drop(_migrations_1, 1);
          for(final MigrationBlock migration : _drop) {
            _builder.append("import ");
            String _packageName_1 = model.getPackageName();
            _builder.append(_packageName_1, "");
            _builder.append(".migrations.Default");
            DatabaseBlock _database_2 = model.getDatabase();
            String _name = _database_2.getName();
            String _pascalize = Strings.pascalize(_name);
            _builder.append(_pascalize, "");
            _builder.append("MigrationV");
            int _plus = (version + 1);
            int _version = version = _plus;
            _builder.append(_version, "");
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.newLine();
    _builder.append("public abstract class Abstract");
    DatabaseBlock _database_3 = model.getDatabase();
    String _name_1 = _database_3.getName();
    String _pascalize_1 = Strings.pascalize(_name_1);
    _builder.append(_pascalize_1, "");
    _builder.append("OpenHelper extends MechanoidSQLiteOpenHelper {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("public static final String DATABASE_NAME = \"");
    DatabaseBlock _database_4 = model.getDatabase();
    String _name_2 = _database_4.getName();
    _builder.append(_name_2, "	");
    _builder.append(".db\";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public static final int VERSION = ");
    DatabaseBlock _database_5 = model.getDatabase();
    EList<MigrationBlock> _migrations_2 = _database_5.getMigrations();
    int _size_1 = _migrations_2.size();
    _builder.append(_size_1, "	");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public interface Tables {");
    _builder.newLine();
    {
      EList<DDLStatement> _statements = snapshot.getStatements();
      Iterable<CreateTableStatement> _filter = Iterables.<CreateTableStatement>filter(_statements, CreateTableStatement.class);
      for(final CreateTableStatement table : _filter) {
        _builder.append("\t\t");
        _builder.append("String ");
        String _name_3 = table.getName();
        String _underscore = Strings.underscore(_name_3);
        String _upperCase = _underscore.toUpperCase();
        _builder.append(_upperCase, "		");
        _builder.append(" = \"");
        String _name_4 = table.getName();
        _builder.append(_name_4, "		");
        _builder.append("\";");
        _builder.newLineIfNotEmpty();
      }
    }
    {
      EList<DDLStatement> _statements_1 = snapshot.getStatements();
      Iterable<CreateViewStatement> _filter_1 = Iterables.<CreateViewStatement>filter(_statements_1, CreateViewStatement.class);
      for(final CreateViewStatement view : _filter_1) {
        _builder.append("\t\t");
        _builder.append("String ");
        String _name_5 = view.getName();
        String _underscore_1 = Strings.underscore(_name_5);
        String _upperCase_1 = _underscore_1.toUpperCase();
        _builder.append(_upperCase_1, "		");
        _builder.append(" = \"");
        String _name_6 = view.getName();
        _builder.append(_name_6, "		");
        _builder.append("\";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public Abstract");
    DatabaseBlock _database_6 = model.getDatabase();
    String _name_7 = _database_6.getName();
    String _pascalize_2 = Strings.pascalize(_name_7);
    _builder.append(_pascalize_2, "	");
    _builder.append("OpenHelper(Context context) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("super(context, DATABASE_NAME, null, VERSION);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public void onCreate(SQLiteDatabase db) {");
    _builder.newLine();
    _builder.append("\t\t");
    EList<DDLStatement> _statements_2 = snapshot.getStatements();
    final Function1<DDLStatement,Boolean> _function = new Function1<DDLStatement,Boolean>() {
        public Boolean apply(final DDLStatement it) {
          return Boolean.valueOf((it instanceof CreateTableStatement));
        }
      };
    Iterable<DDLStatement> _filter_2 = IterableExtensions.<DDLStatement>filter(_statements_2, _function);
    CharSequence _generateStatements = this._sqliteDatabaseStatementGenerator.generateStatements(_filter_2);
    _builder.append(_generateStatements, "		");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    EList<DDLStatement> _statements_3 = snapshot.getStatements();
    final Function1<DDLStatement,Boolean> _function_1 = new Function1<DDLStatement,Boolean>() {
        public Boolean apply(final DDLStatement it) {
          return Boolean.valueOf((it instanceof CreateViewStatement));
        }
      };
    Iterable<DDLStatement> _filter_3 = IterableExtensions.<DDLStatement>filter(_statements_3, _function_1);
    CharSequence _generateStatements_1 = this._sqliteDatabaseStatementGenerator.generateStatements(_filter_3);
    _builder.append(_generateStatements_1, "		");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    EList<DDLStatement> _statements_4 = snapshot.getStatements();
    final Function1<DDLStatement,Boolean> _function_2 = new Function1<DDLStatement,Boolean>() {
        public Boolean apply(final DDLStatement it) {
          return Boolean.valueOf((it instanceof CreateTriggerStatement));
        }
      };
    Iterable<DDLStatement> _filter_4 = IterableExtensions.<DDLStatement>filter(_statements_4, _function_2);
    CharSequence _generateStatements_2 = this._sqliteDatabaseStatementGenerator.generateStatements(_filter_4);
    _builder.append(_generateStatements_2, "		");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("protected SQLiteMigration createMigration(int version) {");
    _builder.newLine();
    {
      DatabaseBlock _database_7 = model.getDatabase();
      EList<MigrationBlock> _migrations_3 = _database_7.getMigrations();
      int _size_2 = _migrations_3.size();
      boolean _greaterThan_1 = (_size_2 > 1);
      if (_greaterThan_1) {
        _builder.append("\t\t");
        int version_1 = 1;
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("switch(version) {");
        _builder.newLine();
        {
          DatabaseBlock _database_8 = model.getDatabase();
          EList<MigrationBlock> _migrations_4 = _database_8.getMigrations();
          Iterable<MigrationBlock> _drop_1 = IterableExtensions.<MigrationBlock>drop(_migrations_4, 1);
          for(final MigrationBlock migration_1 : _drop_1) {
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("case ");
            int _plus_1 = (version_1 + 1);
            int _version_1 = version_1 = _plus_1;
            _builder.append(_version_1, "			");
            _builder.append(":");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("return create");
            DatabaseBlock _database_9 = model.getDatabase();
            String _name_8 = _database_9.getName();
            String _pascalize_3 = Strings.pascalize(_name_8);
            _builder.append(_pascalize_3, "				");
            _builder.append("MigrationV");
            _builder.append(version_1, "				");
            _builder.append("();");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t\t");
        _builder.append("\t");
        _builder.append("default:");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("\t\t");
        _builder.append("throw new IllegalStateException(\"No migration for version \" + version);");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("}");
        _builder.newLine();
      } else {
        _builder.append("\t\t");
        _builder.append("throw new IllegalStateException(\"No migrations for any version\");");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    {
      DatabaseBlock _database_10 = model.getDatabase();
      EList<MigrationBlock> _migrations_5 = _database_10.getMigrations();
      int _size_3 = _migrations_5.size();
      boolean _greaterThan_2 = (_size_3 > 1);
      if (_greaterThan_2) {
        _builder.append("\t");
        int version_2 = 1;
        _builder.newLineIfNotEmpty();
        {
          DatabaseBlock _database_11 = model.getDatabase();
          EList<MigrationBlock> _migrations_6 = _database_11.getMigrations();
          Iterable<MigrationBlock> _drop_2 = IterableExtensions.<MigrationBlock>drop(_migrations_6, 1);
          for(final MigrationBlock migration_2 : _drop_2) {
            _builder.append("\t");
            _builder.append("protected SQLiteMigration create");
            DatabaseBlock _database_12 = model.getDatabase();
            String _name_9 = _database_12.getName();
            String _pascalize_4 = Strings.pascalize(_name_9);
            _builder.append(_pascalize_4, "	");
            _builder.append("MigrationV");
            int _plus_2 = (version_2 + 1);
            int _version_2 = version_2 = _plus_2;
            _builder.append(_version_2, "	");
            _builder.append("() {");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("return new Default");
            DatabaseBlock _database_13 = model.getDatabase();
            String _name_10 = _database_13.getName();
            String _pascalize_5 = Strings.pascalize(_name_10);
            _builder.append(_pascalize_5, "		");
            _builder.append("MigrationV");
            _builder.append(version_2, "		");
            _builder.append("();");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("}");
            _builder.newLine();
          }
        }
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence generateStub(final Model model, final MigrationBlock snapshot) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("/*");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("* Generated by Robotoworks Mechanoid");
    _builder.newLine();
    _builder.append(" ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("package ");
    String _packageName = model.getPackageName();
    _builder.append(_packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import android.content.Context;");
    _builder.newLine();
    _builder.append("import ");
    String _packageName_1 = model.getPackageName();
    _builder.append(_packageName_1, "");
    _builder.append(".Abstract");
    DatabaseBlock _database = model.getDatabase();
    String _name = _database.getName();
    String _pascalize = Strings.pascalize(_name);
    _builder.append(_pascalize, "");
    _builder.append("OpenHelper;");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.newLine();
    _builder.append("public class ");
    DatabaseBlock _database_1 = model.getDatabase();
    String _name_1 = _database_1.getName();
    String _pascalize_1 = Strings.pascalize(_name_1);
    _builder.append(_pascalize_1, "");
    _builder.append("OpenHelper extends Abstract");
    DatabaseBlock _database_2 = model.getDatabase();
    String _name_2 = _database_2.getName();
    String _pascalize_2 = Strings.pascalize(_name_2);
    _builder.append(_pascalize_2, "");
    _builder.append("OpenHelper {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("public ");
    DatabaseBlock _database_3 = model.getDatabase();
    String _name_3 = _database_3.getName();
    String _pascalize_3 = Strings.pascalize(_name_3);
    _builder.append(_pascalize_3, "	");
    _builder.append("OpenHelper(Context context) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("super(context);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
}