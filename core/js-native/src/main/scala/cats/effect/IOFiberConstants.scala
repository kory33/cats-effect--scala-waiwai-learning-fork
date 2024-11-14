/*
 * Copyright 2020-2023 Typelevel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cats.effect

import org.typelevel.scalaccompat.annotation._

// defined in Java for the JVM, Scala for ScalaJS (where object field access is faster)
private object IOFiberConstants {

  final val MaxStackDepth = 512

  // continuation ids (should all be inlined)
  final val MapK = 0 // 「次にやらないといけなかったのは Map の関数適用です」 「IO.map(##, f)」
  final val FlatMapK = 1 // 「次にやらないといけなかったのは FlatMap の関数適用です」 「IO.flatMap(##, f)」
  final val CancelationLoopK = 2
  final val RunTerminusK = 3 // 「プログラム終端です」「HALT」
  final val EvalOnK = 4
  final val HandleErrorWithK = 5
  final val OnCancelK = 6
  final val UncancelableK = 7
  final val UnmaskK = 8
  final val AttemptK = 9 // 「.attempt 中だったので、成功してたら Right に入れててください」「IO.attempt(##)」

  // resume ids
  // 「前回の run の最後に何が理由で Fiber の実行が止まったのか」を示す
  final val ExecR = 0 // 「前回」が無い状態 (FiberIO#run が最初に実行されるまでしか使われない)
  final val AsyncContinueSuccessfulR = 1
  final val AsyncContinueFailedR = 2
  final val AsyncContinueCanceledR = 3
  final val AsyncContinueCanceledWithFinalizerR = 4
  final val BlockingR = 5
  // 「前回」の最後に Cede によって Fiber の実行が止まりリスケジューリングされた
  // Cede は IO[Unit] だったので、実行を開始するときに IO.unit を curIO として再開したい
  final val CedeR = 6
  // 「前回」の最後に Fiber 自身が「飽きた」、もしくは、EvalOn によって ExecutionContext が変わった
  final val AutoCedeR = 7
  final val DoneR = 8

  @nowarn212
  @inline def isVirtualThread(t: Thread): Boolean = false
}
