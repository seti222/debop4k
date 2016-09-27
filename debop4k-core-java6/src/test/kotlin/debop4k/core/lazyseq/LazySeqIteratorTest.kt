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

import com.nhaarman.mockito_kotlin.*
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.*

/**
 * LazySeqIteratorTest
 * @author sunghyouk.bae@gmail.com
 */
class LazySeqIteratorTest : AbstractLazySeqTest() {

  private val supplierMock = mock<() -> LazySeq<Int>>()
  private val consumeMock = mock<(Char) -> Unit>()

  @Test
  fun testEmptyIteratorForEmptySeq() {
    val iterator = emptyLazySeq<Any>().iterator()
    assertThat(iterator.hasNext()).isFalse()
  }

  @Test
  fun testAllowCallingHasNextContinuouslyOnEmptyIterator() {
    val iterator = emptyLazySeq<Any>().iterator()
    iterator.hasNext()
    assertThat(iterator.hasNext()).isFalse()
  }

  @Test
  fun testAllowAdvancingIteratorForSingleElementSeq() {
    val iterator = lazySeqOf("a").iterator()
    assertThat(iterator.next()).isEqualTo("a")
    assertThat(iterator.hasNext()).isFalse()

    Assertions.assertThatThrownBy {
      iterator.next()
    }.isInstanceOf(NoSuchElementException::class.java)
  }

  @Test
  fun testNonEmptyIteratorForFixedSeq() {
    val iterator = lazySeqOf("a", "b", "c").iterator()

    assertThat(iterator.hasNext()).isTrue()
    assertThat(iterator.next()).isEqualTo("a")
    assertThat(iterator.hasNext()).isTrue()
    assertThat(iterator.next()).isEqualTo("b")
    assertThat(iterator.hasNext()).isTrue()
    assertThat(iterator.next()).isEqualTo("c")

    assertThat(iterator.hasNext()).isFalse()
  }

  @Test
  fun testAllowTraversingWithoutCallingHasNext() {
    val iterator = lazySeqOf("a", "b", "c").iterator()

    assertThat(iterator.next()).isEqualTo("a")
    assertThat(iterator.next()).isEqualTo("b")
    assertThat(iterator.next()).isEqualTo("c")

    assertThat(iterator.hasNext()).isFalse()
  }

  @Test
  fun testAllowCallingHasNextMultipleTimes() {
    val iterator = lazySeqOf("a", "b", "c").iterator()


    assertThat(iterator.hasNext()).isTrue()
    assertThat(iterator.hasNext()).isTrue()
    assertThat(iterator.next()).isEqualTo("a")
    assertThat(iterator.next()).isEqualTo("b")
    assertThat(iterator.hasNext()).isTrue()
    assertThat(iterator.hasNext()).isTrue()
    assertThat(iterator.next()).isEqualTo("c")

    assertThat(iterator.hasNext()).isFalse()
  }

  @Test
  fun testAllowTraversingLazyButFiniteSeq() {

    val iterator = lazy().iterator()

    val list = expectedList
    list.forEach { expected ->
      assertThat(iterator.hasNext()).isTrue()
      assertThat(iterator.next()).isEqualTo(expected)
    }

    assertThat(iterator.hasNext()).isFalse()
  }

  @Test
  fun testAllowTraversingLazyButFiniteSeqWithoutCallingHasNext() {

    val iterator = lazy().iterator()

    val list = expectedList
    list.forEach { expected ->
      assertThat(iterator.next()).isEqualTo(expected)
    }

    assertThat(iterator.hasNext()).isFalse()
  }

  @Test
  fun testNotEvaluateTailWhenCreatingIterator() {
    val lazy = LazySeq.cons(1, supplierMock)
    lazy.iterator()
    verifyZeroInteractions(supplierMock)
  }

  @Test
  fun testEvaluateTailOnlyOnceWhenAdvancingIterator() {
    val lazy = LazySeq.cons(1, supplierMock)
    lazy.iterator().next()
    verify(supplierMock, times(1)).invoke()
  }

  @Test
  fun testCreateIteratorForInfiniteSeq() {
    val naturals = LazySeq.numbers(1)

    val iterator = naturals.iterator()

    assertThat(iterator.hasNext()).isTrue()
    assertThat(iterator.next()).isEqualTo(1)

    assertThat(iterator.hasNext()).isTrue()
    assertThat(iterator.next()).isEqualTo(2)

    assertThat(iterator.hasNext()).isTrue()
    assertThat(iterator.next()).isEqualTo(3)

    assertThat(iterator.next()).isEqualTo(4)
    assertThat(iterator.next()).isEqualTo(5)
  }

  @Test
  fun testCallConsumerForEachElementOfIteratorForFiniteSeq() {
    val iterator = lazySeqOf('a', 'b', 'c').iterator()

    iterator.forEach(consumeMock)

    verify(consumeMock).invoke('a')
    verify(consumeMock).invoke('b')
    verify(consumeMock).invoke('c')
    verifyNoMoreInteractions(consumeMock)
  }

  @Test
  fun testAllowJava5ForEachIterator() {
    val fixed = lazySeqOf('a', 'b', 'c')

    for (c in fixed) {
      consumeMock.invoke(c)
    }

    verify(consumeMock).invoke('a')
    verify(consumeMock).invoke('b')
    verify(consumeMock).invoke('c')
    verifyNoMoreInteractions(consumeMock)
  }
}