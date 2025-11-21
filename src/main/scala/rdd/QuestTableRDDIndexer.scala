package org.github.helkaroui.sparkquest
package rdd

import org.apache.spark.{Dependency, Partition, SparkContext, TaskContext}
import org.apache.spark.rdd.RDD

import scala.reflect.ClassTag

class QuestTableRDDIndexer[S: ClassTag](sc: SparkContext,
                                        val deps: Seq[Dependency[_]])
  extends RDD[Array[Byte]](sc, deps) {


  override def compute(split: Partition, context: TaskContext): Iterator[Array[Byte]] = {
    val searchRDDPartition = split.asInstanceOf[QuestTablePartitionIndex[S]]


    ???
  }

  override protected def getPartitions: Array[Partition] = ???
}
