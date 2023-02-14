/*
 * Copyright (C) 2009-2022 Lightbend Inc. <https://www.lightbend.com>
 */

package org.apache.pekko.http.scaladsl.model

import java.util.Locale
import org.apache.pekko.http.impl.util._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TurkishISpec extends AnyWordSpec with Matchers {
  "Model" should {
    "not suffer from turkish-i problem" in {
      val charsetCons = Class.forName("org.apache.pekko.http.scaladsl.model.HttpCharsets$").getDeclaredConstructor()
      charsetCons.setAccessible(true)

      val previousLocale = Locale.getDefault

      try {
        // recreate HttpCharsets in turkish locale
        Locale.setDefault(new Locale("tr", "TR"))

        val testString = "ISO-8859-1"
        // demonstrate difference between toRootLowerCase and toLowerCase(turkishLocale)
        (testString.toLowerCase should not).equal(testString.toRootLowerCase)

        val newCharsets = charsetCons.newInstance().asInstanceOf[HttpCharsets.type]
        newCharsets.getForKey("iso-8859-1") shouldEqual Some(newCharsets.`ISO-8859-1`)
      } finally {
        Locale.setDefault(previousLocale)
      }
    }
  }
}