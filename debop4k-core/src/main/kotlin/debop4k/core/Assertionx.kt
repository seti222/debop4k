/*
 * Copyright (c) 2016. Sunghyouk Bae <sunghyouk.bae@gmail.com>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

@file:JvmName("Assertionx")

package debop4k.core

import java.lang.IllegalArgumentException

infix inline fun <T> T.firstNotNull(factory: () -> T): T = this ?: factory() //if (this != null) this else factory()

infix inline fun <T> T.verifiedBy(verifier: (T) -> Unit): T {
  verifier(this)
  return this
}

infix inline fun <T> T.verifiedWith(verify: T.() -> Unit): T {
  this.verify()
  return this
}

inline fun <T> T.assertBy(assertion: (T) -> Unit): T {
  assertion(this)
  return this
}

inline fun <T> T.assertWith(assert: T.() -> Unit): T {
  this.assert()
  return this
}


fun Number.shouldBePositive(name: String) {
  if (this.toDouble() <= 0)
    throw IllegalArgumentException("$name should be positive number. number=$this")
}

fun String?.shouldNotBeNullOrEmpty(name: String) {
  if (this.isNullOrEmpty())
    throw IllegalArgumentException("$name should not be null or empty")
}

fun String?.shouldNotBeNullOrBlank(name: String) {
  if (this.isNullOrBlank())
    throw IllegalArgumentException("$name should not be null or blank")
}


fun String?.nullIfBlank(): String? = if (this.isNullOrBlank()) null else this
fun String?.nullIfEmpty(): String? = if (this.isNullOrEmpty()) null else this