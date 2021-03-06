package com.robotoworks.mechanoid.sqlite.generator;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.robotoworks.mechanoid.common.util.Strings;
import com.robotoworks.mechanoid.common.xtext.generator.MechanoidOutputConfigurationProvider;
import com.robotoworks.mechanoid.sqlite.generator.ActiveRecordGenerator;
import com.robotoworks.mechanoid.sqlite.generator.ContentProviderContractGenerator;
import com.robotoworks.mechanoid.sqlite.generator.ContentProviderGenerator;
import com.robotoworks.mechanoid.sqlite.generator.SqliteDatabaseSnapshotBuilder;
import com.robotoworks.mechanoid.sqlite.generator.SqliteMigrationGenerator;
import com.robotoworks.mechanoid.sqlite.generator.SqliteOpenHelperGenerator;
import com.robotoworks.mechanoid.sqlite.sqliteModel.CreateTableStatement;
import com.robotoworks.mechanoid.sqlite.sqliteModel.DDLStatement;
import com.robotoworks.mechanoid.sqlite.sqliteModel.DatabaseBlock;
import com.robotoworks.mechanoid.sqlite.sqliteModel.MigrationBlock;
import com.robotoworks.mechanoid.sqlite.sqliteModel.Model;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;

@SuppressWarnings("all")
public class SqliteModelGenerator implements IGenerator {
  @Inject
  private SqliteOpenHelperGenerator mOpenHelperGenerator;
  
  @Inject
  private ContentProviderContractGenerator mContentProviderContractGenerator;
  
  @Inject
  private Provider<SqliteDatabaseSnapshotBuilder> mDbSnapshotBuilderProvider;
  
  @Inject
  private ContentProviderGenerator mContentProviderGenerator;
  
  @Inject
  private Provider<SqliteMigrationGenerator> mMigrationGenerator;
  
  @Inject
  private ActiveRecordGenerator mActiveRecordGenerator;
  
  public void doGenerate(final Resource resource, final IFileSystemAccess fsa) {
    EList<EObject> _contents = resource.getContents();
    EObject _head = IterableExtensions.<EObject>head(_contents);
    Model model = ((Model) _head);
    SqliteDatabaseSnapshotBuilder _get = this.mDbSnapshotBuilderProvider.get();
    Model _build = _get.build(model);
    DatabaseBlock _database = _build.getDatabase();
    EList<MigrationBlock> _migrations = _database.getMigrations();
    final MigrationBlock snapshot = _migrations.get(0);
    String _packageName = model.getPackageName();
    DatabaseBlock _database_1 = model.getDatabase();
    String _name = _database_1.getName();
    String _pascalize = Strings.pascalize(_name);
    String _concat = "Abstract".concat(_pascalize);
    String _concat_1 = _concat.concat("OpenHelper");
    String _resolveFileName = Strings.resolveFileName(_packageName, _concat_1);
    CharSequence _generate = this.mOpenHelperGenerator.generate(model, snapshot);
    fsa.generateFile(_resolveFileName, _generate);
    String _packageName_1 = model.getPackageName();
    DatabaseBlock _database_2 = model.getDatabase();
    String _name_1 = _database_2.getName();
    String _pascalize_1 = Strings.pascalize(_name_1);
    String _concat_2 = _pascalize_1.concat("OpenHelper");
    String _resolveFileName_1 = Strings.resolveFileName(_packageName_1, _concat_2);
    CharSequence _generateStub = this.mOpenHelperGenerator.generateStub(model, snapshot);
    fsa.generateFile(_resolveFileName_1, 
      MechanoidOutputConfigurationProvider.DEFAULT_STUB_OUTPUT, _generateStub);
    String _packageName_2 = model.getPackageName();
    DatabaseBlock _database_3 = model.getDatabase();
    String _name_2 = _database_3.getName();
    String _pascalize_2 = Strings.pascalize(_name_2);
    String _concat_3 = _pascalize_2.concat("Contract");
    String _resolveFileName_2 = Strings.resolveFileName(_packageName_2, _concat_3);
    CharSequence _generate_1 = this.mContentProviderContractGenerator.generate(model, snapshot);
    fsa.generateFile(_resolveFileName_2, _generate_1);
    String _packageName_3 = model.getPackageName();
    DatabaseBlock _database_4 = model.getDatabase();
    String _name_3 = _database_4.getName();
    String _pascalize_3 = Strings.pascalize(_name_3);
    String _concat_4 = "Abstract".concat(_pascalize_3);
    String _concat_5 = _concat_4.concat("ContentProvider");
    String _resolveFileName_3 = Strings.resolveFileName(_packageName_3, _concat_5);
    CharSequence _generate_2 = this.mContentProviderGenerator.generate(model, snapshot);
    fsa.generateFile(_resolveFileName_3, _generate_2);
    String _packageName_4 = model.getPackageName();
    DatabaseBlock _database_5 = model.getDatabase();
    String _name_4 = _database_5.getName();
    String _pascalize_4 = Strings.pascalize(_name_4);
    String _concat_6 = _pascalize_4.concat("ContentProvider");
    String _resolveFileName_4 = Strings.resolveFileName(_packageName_4, _concat_6);
    CharSequence _generateStub_1 = this.mContentProviderGenerator.generateStub(model, snapshot);
    fsa.generateFile(_resolveFileName_4, 
      MechanoidOutputConfigurationProvider.DEFAULT_STUB_OUTPUT, _generateStub_1);
    EList<DDLStatement> _statements = snapshot.getStatements();
    Iterable<CreateTableStatement> _filter = Iterables.<CreateTableStatement>filter(_statements, CreateTableStatement.class);
    final Procedure1<CreateTableStatement> _function = new Procedure1<CreateTableStatement>() {
        public void apply(final CreateTableStatement statement) {
          SqliteModelGenerator.this.generateActiveRecordEntity(resource, fsa, ((CreateTableStatement) statement));
        }
      };
    IterableExtensions.<CreateTableStatement>forEach(_filter, _function);
    DatabaseBlock _database_6 = model.getDatabase();
    EList<MigrationBlock> _migrations_1 = _database_6.getMigrations();
    final Procedure2<MigrationBlock,Integer> _function_1 = new Procedure2<MigrationBlock,Integer>() {
        public void apply(final MigrationBlock item, final Integer index) {
          boolean _greaterThan = ((index).intValue() > 0);
          if (_greaterThan) {
            int _plus = ((index).intValue() + 1);
            SqliteModelGenerator.this.generateMigration(resource, fsa, item, _plus);
          }
        }
      };
    IterableExtensions.<MigrationBlock>forEach(_migrations_1, _function_1);
  }
  
  public void generateActiveRecordEntity(final Resource resource, final IFileSystemAccess fsa, final CreateTableStatement statement) {
    EList<EObject> _contents = resource.getContents();
    EObject _head = IterableExtensions.<EObject>head(_contents);
    Model model = ((Model) _head);
    String _packageName = model.getPackageName();
    String _name = statement.getName();
    String _pascalize = Strings.pascalize(_name);
    String _concat = _pascalize.concat("Record");
    String genFileName = Strings.resolveFileName(_packageName, _concat);
    CharSequence _generate = this.mActiveRecordGenerator.generate(model, statement);
    fsa.generateFile(genFileName, _generate);
  }
  
  public void generateMigration(final Resource resource, final IFileSystemAccess fsa, final MigrationBlock migration, final int version) {
    EList<EObject> _contents = resource.getContents();
    EObject _head = IterableExtensions.<EObject>head(_contents);
    Model model = ((Model) _head);
    String _packageName = model.getPackageName();
    String _concat = _packageName.concat(".migrations");
    DatabaseBlock _database = model.getDatabase();
    String _name = _database.getName();
    String _pascalize = Strings.pascalize(_name);
    String _concat_1 = "Default".concat(_pascalize);
    String _concat_2 = _concat_1.concat("MigrationV");
    String _valueOf = String.valueOf(version);
    String _concat_3 = _concat_2.concat(_valueOf);
    String genFileName = Strings.resolveFileName(_concat, _concat_3);
    SqliteMigrationGenerator generator = this.mMigrationGenerator.get();
    CharSequence _generate = generator.generate(model, migration, version);
    fsa.generateFile(genFileName, _generate);
  }
}
