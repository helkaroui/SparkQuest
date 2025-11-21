package org.github.helkaroui.sparkquest
package rdd

import org.apache.spark.{Dependency, OneToOneDependency, Partition, SparkContext, TaskContext}
import org.apache.spark.rdd.RDD

import scala.reflect.ClassTag

class QuestTableRDD[S: ClassTag](sc: SparkContext,
                                 val indexerRDD: QuestTableRDDIndexer[S],
                                 val deps: Seq[Dependency[_]]) extends RDD[S](sc, Seq(new OneToOneDependency(indexerRDD)) ++ deps) {
  override def compute(split: Partition, context: TaskContext): Iterator[S] = ???

  override protected def getPartitions: Array[Partition] = ???
}
