/*
 * Copyright (C) 2009-2022 Lightbend Inc. <https://www.lightbend.com>
 */

package org.apache.pekko.http.scaladsl.model.headers

import org.apache.pekko
import pekko.http.impl.util.{ Renderable, Rendering, SingletonValueRenderable }
import pekko.http.javadsl.{ model => jm }

sealed trait ContentDispositionType extends Renderable with jm.headers.ContentDispositionType

object ContentDispositionTypes {
  protected abstract class Predefined extends ContentDispositionType with SingletonValueRenderable {
    def name: String = value
  }

  case object inline extends Predefined
  case object attachment extends Predefined
  case object `form-data` extends Predefined
  final case class Ext(name: String) extends ContentDispositionType {
    def render[R <: Rendering](r: R): r.type = r ~~ name
  }
}