/*
 * Copyright (c) 2016. Sunghyouk Bae <sunghyouk.bae@gmail.com>
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

package debop4k.core.lazyseq

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * LazySeqSortedTest
 * @author sunghyouk.bae@gmail.com
 */
class LazySeqSortedTest : AbstractLazySeqTest() {

  @Test
  fun testSortEmptySeq() {
    assertThat(emptySeq.sorted()).isEmpty()
  }

  @Test
  fun testSortSingleSeq() {
    val single = lazySeqOf(17)
    assertThat(single.sorted()).isEqualTo(lazySeqOf(17))
  }

  @Test
  fun testSortFixedSeq() {
    val fixed = lazySeqOf(17, 3, 15, 9, 4)
    assertThat(fixed.sorted()).isEqualTo(lazySeqOf(3, 4, 9, 15, 17))
  }

  @Test
  fun testLazyFiniteSeq() {
    val lazy = lazy()
    log.debug("lazy={}", lazy.mkString(lazy = false))
    assertThat(lazy.sorted()).isEqualTo(lazySeqOf(expectedList.sorted()))
  }

  @Test
  fun testSortStringSeq() {
    val fixed = lazySeqOf("ab", "c", "", "ghjkl", "def")
    val sorted = fixed.sorted { s1, s2 -> s1.length - s2.length }

    assertThat(sorted).isEqualTo(lazySeqOf("", "c", "ab", "def", "ghjkl"))
  }
}