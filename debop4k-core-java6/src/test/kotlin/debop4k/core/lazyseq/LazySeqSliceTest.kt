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
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

class LazySeqSliceTest : AbstractLazySeqTest() {

  @Test
  fun testSliceEmptySeq() {
    assertThat(emptyLazySeq<Int>().slice(10, 10)).isEmpty()
    assertThat(emptyLazySeq<Int>().slice(10, 20)).isEmpty()
    assertThat(emptyLazySeq<Int>().slice(0, 0)).isEmpty()
    assertThat(emptyLazySeq<Int>().slice(0, 10)).isEmpty()

    assertThatThrownBy {
      emptyLazySeq<Int>().slice(10, 9)
    }.isInstanceOf(IllegalArgumentException::class.java)
  }

  @Test
  fun testSliceFixedSeq() {
    val fixed = lazySeqOf(1, 2)

    assertThatThrownBy {
      fixed.slice(10, 9)
    }.isInstanceOf(IllegalArgumentException::class.java)

    assertThatThrownBy {
      fixed.slice(-10, 9)
    }.isInstanceOf(IllegalArgumentException::class.java)
  }

  @Test
  fun testSliceInfiniteSeq() {
    val numbers = LazySeq.numbers(0)

    assertThatThrownBy {
      numbers.slice(-10, 9)
    }.isInstanceOf(IllegalArgumentException::class.java)

    val sub = numbers.slice(3, 7)
    assertThat(sub).isEqualTo(lazySeqOf(3, 4, 5, 6))
  }

  @Test
  fun testSliceNonEmptyFixedSeq() {
    val fixed = lazySeqOf(1, 2, 3, 4, 5, 6)
    val sub = fixed.slice(2, 4)
    assertThat(sub).isEqualTo(lazySeqOf(3, 4))
  }

  @Test
  fun testAdjustLengthWhenEndIndexPastEndOfStream() {
    val fixed = lazySeqOf(1, 2, 3, 4, 5, 6, 7)

    assertThat(fixed.slice(4, 100)).isEqualTo(lazySeqOf(5, 6, 7))
    assertThat(fixed.slice(0, 4)).isEqualTo(lazySeqOf(1, 2, 3, 4))
    assertThat(fixed.slice(0, 100)).isEqualTo(lazySeqOf(1, 2, 3, 4, 5, 6, 7))

    assertThat(fixed.slice(100, 200)).isEmpty()
  }
}