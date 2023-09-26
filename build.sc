// SPDX-License-Identifier: Apache-2.0
// SPDX-FileCopyrightText: 2022 Jiuyang Liu <liu@jiuyang.me>

import mill._
import mill.scalalib._
import mill.scalalib.scalafmt._
import $file.common

object v {
  val scala = "2.13.12"
  val scalajs = "1.14.0"
  object jvm {
    val oslib = ivy"com.lihaoyi::os-lib:0.9.1"
  }
  object js {
  }
}

trait RVDecoderDBJVMModule extends common.RVDecoderDBJVMModule with ScalafmtModule {
  override def millSourcePath: os.Path = os.pwd / "rvdecoderdb"
  override def allSources: T[Seq[PathRef]] = T(super.allSources() ++ Some(PathRef(millSourcePath / "jvm")))
  def scalaVersion = T(v.scala)
  def osLibIvy = v.jvm.oslib
}

trait RVDecoderDBJSModule extends common.RVDecoderDBJSModule with ScalafmtModule {
  override def millSourcePath: os.Path = os.pwd / "rvdecoderdb"
  def scalaVersion = T(v.scala)
  def scalaJSVersion = T(v.scalajs)
}

object rvdecoderdb extends Module { m =>
  object jvm extends RVDecoderDBJVMModule
  object js extends RVDecoderDBJSModule
}

trait RVDecoderDBJVMTestModule extends common.RVDecoderDBJVMTestModule with ScalafmtModule {
  override def millSourcePath = os.pwd / "rvdecoderdbtest"
  def dut = rvdecoderdb.jvm
  def scalaVersion = T(v.scala)
}

trait RVDecoderDBJSTestModule extends common.RVDecoderDBTestJSModule with ScalafmtModule {
  override def millSourcePath = os.pwd / "rvdecoderdbtest"
  def dut = rvdecoderdb.js
  def scalaVersion = T(v.scala)
  def scalaJSVersion = T(v.scalajs)
}


object rvdecoderdbtest extends Module { m =>
  object jvm extends RVDecoderDBJVMTestModule
  object js extends RVDecoderDBJSTestModule
}
