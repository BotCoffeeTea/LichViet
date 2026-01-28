// lib/storage/lunar_storage.dart
import 'dart:convert';
import 'package:sembast/sembast.dart';
import 'package:sembast/sembast_io.dart' show databaseFactoryIo;
import 'package:sembast_web/sembast_web.dart' show databaseFactoryWeb;
import '../models/lunar.dart';

class LunarStorage {
  final DatabaseFactory _dbFactory;
  final String dbName;
  Database? _db;
  final StoreRef<int, Map<String, dynamic>> _store = intMapStoreFactory.store('lunarEvents');

  LunarStorage._(this._dbFactory, {this.dbName = 'lunar_db'});

  static Future<LunarStorage> create({String dbName = 'lunar_db'}) async {
    final factory = identical(1, 1.0) ? databaseFactoryIo : databaseFactoryWeb;
    // Above line is pseudo; in your app choose factory based on platform.
    return LunarStorage._(factory, dbName: dbName);
  }

  Future<void> open() async {
    _db ??= await _dbFactory.openDatabase(dbName);
  }

  Future<int> saveLunarEvent({
    required DateTime solarDate,
    required Lunar lunar,
    Map<String, dynamic>? meta,
  }) async {
    await open();
    final key = _jdKey(solarDate);
    final json = {
      'solar': {
        'y': solarDate.year,
        'm': solarDate.month,
        'd': solarDate.day,
      },
      'lunar': lunar.toJson(),
      'meta': meta ?? {},
      'createdAt': DateTime.now().toIso8601String(),
    };
    // Use epoch dayNumber as key
    return await _store.record(key).put(_db!, json).then((v) => key);
  }

  Future<Map<String, dynamic>?> getLunarEventFor(DateTime solarDate) async {
    await open();
    final key = _jdKey(solarDate);
    final rec = await _store.record(key).get(_db!);
    return rec;
  }

  Future<List<Map<String, dynamic>>> getAllEvents() async {
    await open();
    final result = await _store.find(_db!);
    return result.map((r) => r.value).toList();
  }

  int _jdKey(DateTime d) {
    // Use the JDN from service for stable integer key
    final jd = LunarCalendarService._jdFromDate(d.day, d.month, d.year);
    return jd;
  }
}
