package org.github.helkaroui.sparkquest

import io.questdb.cairo.security.AllowAllSecurityContext
import io.questdb.cairo.wal.ApplyWal2TableJob
import io.questdb.cairo.{CairoEngine, DefaultCairoConfiguration, GeoHashes, TableFlagResolver, TableNameRegistryRO}
import io.questdb.griffin.SqlExecutionContextImpl
import io.questdb.std.Os


object Main {
  def main(args: Array[String]): Unit = {

    val configuration = new DefaultCairoConfiguration("data_dir")
    val engine = new CairoEngine(configuration)

    val ctx = new SqlExecutionContextImpl(engine, 1)
      .`with`(AllowAllSecurityContext.INSTANCE, null)

    engine.execute("CREATE TABLE testTable (" +
      "a int, b byte, c short, d long, e float, g double, h date, " +
      "i symbol, j string, k boolean, l geohash(8c), ts timestamp" +
      ") TIMESTAMP(ts) PARTITION BY DAY WAL", ctx)


    val tableToken = engine.getTableTokenIfExists("testTable")
    val writer = engine.getWalWriter(tableToken)

    for (i <- 0 until 1000) {
      val row = writer.newRow(Os.currentTimeMicros)
      row.putInt(0, 123)
      row.putByte(1, 1111.toByte)
      row.putShort(2, 222.toShort)
      row.putLong(3, 333)
      row.putFloat(4, 4.44f)
      row.putDouble(5, 5.55)
      row.putDate(6, System.currentTimeMillis)
      row.putSym(7, "xyz")
      row.putStr(8, "abc")
      row.putBool(9, true)
      row.putGeoHash(10, GeoHashes.fromString("u33dr01d", 0, 8))
      row.append
    }

    // Start indexing
    val walApplyJob = new ApplyWal2TableJob(engine, 1)
    while (walApplyJob.run(0)){}

    // Query data
    val factory = engine.select("SELECT count(*) FROM testTable", ctx)
    val cursor = factory.getCursor(ctx)
    while (cursor.hasNext){
      val record = cursor.getRecord
      println(record.getLong(0))
    }


    engine.reloadTableNames()


    val tableNameRegistry = new TableNameRegistryRO(engine, new TableFlagResolver() {
      override def isPublic(tableName: CharSequence): Boolean = ???
      override def isSystem(tableName: CharSequence): Boolean = ???
    })



    tableNameRegistry.reload()
  }
}
