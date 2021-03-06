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
 */

@file:JvmName("Observablex")

package debop4k.reactive

import debop4k.core.collections.asList
import org.eclipse.collections.impl.list.mutable.primitive.*
import rx.*
import rx.Observable.OnSubscribe
import rx.functions.Func0
import rx.observables.BlockingObservable

fun <T> emptyObservable(): Observable<T> = Observable.empty()
fun <T> observable(body: OnSubscribe<T>): Observable<T> = Observable.create(body)
fun <T> observable(body: (s: Subscriber<in T>) -> Unit): Observable<T> {
  return Observable.create(body)
}

fun <T> deferredObservable(observableFactory: () -> Observable<T>): Observable<T> =
    Observable.defer(observableFactory)

fun <T> deferredObservable(observableFactory: Func0<Observable<T>>): Observable<T> =
    Observable.defer(observableFactory)

private fun <T> Iterator<T>.toIterable() = object : Iterable<T> {
  override fun iterator(): Iterator<T> = this@toIterable
}

fun <T> Iterable<T>.toObservable(): Observable<T> = Observable.from(this)
fun <T> Iterator<T>.toObservable(): Observable<T> = toIterable().toObservable()
fun <T> Sequence<T>.toObservable(): Observable<T> = Observable.from(object : Iterable<T> {
  override fun iterator(): Iterator<T> = this@toObservable.iterator()
})

fun BooleanArray.toObservable(): Observable<Boolean> = this.asList().toObservable()
fun ByteArray.toObservable(): Observable<Byte> = this.asList().toObservable()
fun ShortArray.toObservable(): Observable<Short> = this.asList().toObservable()
fun IntArray.toObservable(): Observable<Int> = this.asList().toObservable()
fun LongArray.toObservable(): Observable<Long> = this.asList().toObservable()
fun FloatArray.toObservable(): Observable<Float> = this.asList().toObservable()
fun DoubleArray.toObservable(): Observable<Double> = this.asList().toObservable()
fun <T> Array<out T>.toObservable(): Observable<T> = Observable.from(this)

fun BooleanArrayList.toObservable(): Observable<Boolean> = this.asList().toObservable()
fun ByteArrayList.toObservable(): Observable<Byte> = this.asList().toObservable()
fun ShortArrayList.toObservable(): Observable<Short> = this.asList().toObservable()
fun IntArrayList.toObservable(): Observable<Int> = this.asList().toObservable()
fun LongArrayList.toObservable(): Observable<Long> = this.asList().toObservable()
fun FloatArrayList.toObservable(): Observable<Float> = this.asList().toObservable()
fun DoubleArrayList.toObservable(): Observable<Double> = this.asList().toObservable()

fun IntProgression.toObservable(): Observable<Int> {
  if (step == 1 && last.toLong() - first < Integer.MAX_VALUE)
    return Observable.range(first, Math.max(0, last - first + 1))
  else
    return Observable.from(this@toObservable)
}

fun <T> T.toSingletonObservable(): Observable<T> = Observable.just(this)
fun <T> Throwable.toObservable(): Observable<T> = Observable.error(this)

fun <T> Iterable<Observable<out T>>.merge(): Observable<T> = Observable.merge(this.toObservable())
fun <T> Iterable<Observable<out T>>.mergeDelayError(): Observable<T> = Observable.mergeDelayError(this.toObservable())

inline fun <T, R> Observable<T>.fold(initial: R, crossinline body: (R, T) -> R): Observable<R> {
  return reduce(initial, { a, e -> body(a, e) })
}

fun <T> Observable<T>.onError(block: (Throwable) -> Unit): Observable<T> = doOnError(block)
fun <T> Observable<T>.firstOrNull(): Observable<T?> = this.firstOrDefault(null)
fun <T> BlockingObservable<T>.firstOrNull(): T = this.firstOrDefault(null)

fun <T> Observable<T>.onErrorReturnNull(): Observable<T?> = this.onErrorReturn({ null as? T })

inline fun <T, R> Observable<T>.lift(crossinline operator: (Subscriber<in R>) -> Subscriber<in T>): Observable<R> {
  return this.lift { operator(it!!) }
}

fun <T : Any> Observable<T?>.requireNoNulls(): Observable<T> {
  return this.map { it ?: throw NullPointerException("null element found in rx observable") }
}

fun <T : Any> Observable<T?>.filterNotNull(): Observable<T> = filter { it != null }.map { it as T }

fun <T> Observable<T>.withIndex(): Observable<IndexedValue<T>> {
  return this.zipWith(Observable.range(0, Int.MAX_VALUE)) { value: T, index: Int ->
    IndexedValue(index, value)
  }
}

inline fun <T, R> Observable<T>.flatMapSequence(crossinline body: (T) -> Sequence<R>): Observable<R> {
  return flatMap { body(it).toObservable() }
}

inline fun <T> Observable<T>.subscribeWith(body: FunctionSubscriberModifier<T>.() -> Unit): Subscription {
  val modifier = FunctionSubscriberModifier(subscriber<T>())
  modifier.body()
  return subscribe(modifier.subscriber)
}

fun <T> Observable<Observable<T>>.switchOnNext(): Observable<T> = Observable.switchOnNext(this)

@Suppress("UNCHECKED_CAST")
inline fun <T, R> List<Observable<T>>.combineLatest(crossinline combineFunction: (args: List<T>) -> R): Observable<R> {
  return Observable.combineLatest(this, { combineFunction(it.asList() as List<T>) })
}

@Suppress("UNCHECKED_CAST")
inline fun <T, R> List<Observable<T>>.zip(crossinline zipFunction: (args: List<T>) -> R): Observable<R> {
  return Observable.zip(this, { zipFunction(it.asList() as List<T>) })
}

/**
 * Java 수형으로 casting 하는 것이 여의치 않을 때에는 cast<Int>() 같이 사용하면 된다.
 */
inline fun <reified R : Any> Observable<*>.cast(): Observable<R> = cast(R::class.java)






