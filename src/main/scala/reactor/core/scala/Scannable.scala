package reactor.core.scala

import reactor.core.Scannable.Attr
import reactor.core.{Scannable => JScannable}

import scala.collection.JavaConverters._
import scala.language.implicitConversions

/**
  * Created by winarto on 17/6/17.
  */
trait Scannable {
  def jScannable: JScannable

  def actuals(): Stream[_ <: Scannable] = jScannable.actuals().iterator().asScala.map(js => js: Scannable).toStream

  def inners(): Stream[_ <: Scannable] = jScannable.inners().iterator().asScala.map(js => js: Scannable).toStream

  def isScanAvailable: Boolean = jScannable.isScanAvailable

  /**
    * Check this [[Scannable]] and its [[Scannable.parents()]] for a name an return the
    * first one that is reachable.
    *
    * @return the name of the first parent that has one defined (including this scannable)
    */
  def name: String = jScannable.name()

  /**
    * Check this [[Scannable]] and its [[Scannable.parents()]] for a name an return the
    * first one that is reachable.
    *
    * @return the name of the first parent that has one defined (including this scannable)
    */
  def operatorName: String = jScannable.operatorName()

  /**
    * Return a [[Stream]] navigating the [[org.reactivestreams.Subscription]]
    * chain (upward).
    *
    * @return a [[Stream]] navigating the [[org.reactivestreams.Subscription]]
    *                   chain (upward)
    */
  def parents: Stream[_ <: Scannable] = jScannable.parents().iterator().asScala.map(js => js: Scannable).toStream

  /**
    * This method is used internally by components to define their key-value mappings
    * in a single place. Although it is ignoring the generic type of the [[Attr]] key,
    * implementors should take care to return values of the correct type, and return
    * [[None]] if no specific value is available.
    * <p>
    * For public consumption of attributes, prefer using [[Scannable.scan(Attr)]], which will
    * return a typed value and fall back to the key's default if the component didn't
    * define any mapping.
    *
    * @param key a { @link Attr} to resolve for the component.
    * @return the value associated to the key for that specific component, or null if none.
    */
  def scanUnsafe(key: Attr[_]) = Option(jScannable.scanUnsafe(key))

  /**
    * Introspect a component's specific state [[Attr attribute]], returning an
    * associated value specific to that component, or the default value associated with
    * the key, or null if the attribute doesn't make sense for that particular component
    * and has no sensible default.
    *
    * @param key a [[Attr]] to resolve for the component.
    * @return a [[Some value]] associated to the key or [[None]] if unmatched or unresolved
    *
    */
  def scan[T](key: Attr[T]): Option[T] = Option(jScannable.scan(key))

  /**
    * Introspect a component's specific state [[Attr attribute]]. If there's no
    * specific value in the component for that key, fall back to returning the
    * provided non null default.
    *
    * @param key a [[Attr]] to resolve for the component.
    * @param defaultValue a fallback value if key resolve to { @literal null}
    * @return a value associated to the key or the provided default if unmatched or unresolved
    */
  def scanOrDefault[T](key: Attr[T], defaultValue: T): T = jScannable.scanOrDefault(key, defaultValue)

  /**
    * Visit this [[Scannable]] and its [[Scannable.parents()]] and stream all the
    * observed tags
    *
    * @return the stream of tags for this [[Scannable]] and its parents
    */
  def tags: Stream[(String, String)] = jScannable.tags().iterator().asScala.map(publisher.tupleTwo2ScalaTuple2).toStream
}

object Scannable {
//  def from(any: AnyRef): Scannable = new Scannable {
//    override def jScannable: JScannable = JScannable.from(any)
//  }

  def from(any: Option[AnyRef]): Scannable = {
    any match {
      case None => JScannable.from(None.orNull)
      case Some(s: Scannable) => s.jScannable
      case _ => JScannable.from(new Object())
    }
  }

  implicit def JScannable2Scannable(js: JScannable): Scannable = new Scannable {
    override def jScannable: JScannable = js
  }
}