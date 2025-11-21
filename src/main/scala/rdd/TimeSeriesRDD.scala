package org.github.helkaroui.sparkquest
package rdd

import org.apache.spark.SparkContext

import scala.reflect.ClassTag


/**
 * A QuestDB RDD to hold information about the indexed time series
 *
 * @tparam Q
 * @author Hamza EL KAROUI
 */
trait TimeSeriesRDD[Q] {

  /**
   * Returns the number of rows in the time series
   */
  def count(): Long

  def count(query: String): Long

  /**
   * Save the current indexed RDD onto hdfs/S3
   * in order to be able to reload it later on.
   *
   * @param path Path on the spark file system (hdfs) to save on
   */
  def save(path: String): Unit


  def getNumPartitions: Int

}


object TimeSeriesRDD {

  /**
   * Reload an indexed RDD from spark FS.
   *
   * @param sc      current spark context
   * @param path    Path where the index were saved
   * @tparam T Type of beans or case class this RDD is binded to
   * @return Restored RDD
   */
  def load[T: ClassTag](sc: SparkContext,
                        path: String,
                       ): TimeSeriesRDD[T] = ???

}