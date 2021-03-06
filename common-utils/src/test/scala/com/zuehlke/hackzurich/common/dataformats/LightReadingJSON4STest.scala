package com.zuehlke.hackzurich.common.dataformats

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

@RunWith(classOf[JUnitRunner])
class LightReadingJSON4STest extends FlatSpec with Matchers {

  "from" should "parse LightReading json with correct format" in {
    val rawJson = """{"date": "3.3.2016", "brightnes": 1.1}"""
    val json4s = SensorReadingJSON4SParser.parseWithJson4s(rawJson)
    val tuple = Tuple2("key123", json4s(0))

    val lightReading = LightReadingJSON4S.from(tuple)

    lightReading.get.sensortype should be ("Light")
    lightReading.get.deviceid should be ("key123")
    lightReading.get.date should be ("3.3.2016")
    lightReading.get.brightnes should be (1.1)
  }

  it should "be None for LightReading json missing field" in {
    val rawJson = """{"date": "3.3.2016"}"""
    val json4s = SensorReadingJSON4SParser.parseWithJson4s(rawJson)
    val tuple = Tuple2("key123", json4s(0))

    val lightReading = LightReadingJSON4S.from(tuple)

    lightReading should be (None)
  }

  it should "be None for LightReading json being in wrong format" in {
    val rawJson = """{"date": "3.3.2016", "brightnes": "notADouble"}"""
    val json4s = SensorReadingJSON4SParser.parseWithJson4s(rawJson)
    val tuple = Tuple2("key123", json4s(0))

    val lightReading = LightReadingJSON4S.from(tuple)

    lightReading should be (None)
  }
}