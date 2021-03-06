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

@file:JvmName("Backoffx")

package debop4k.core.retry.backoff

/** 기본 증가자 */
const val DEFAULT_MULTIPLIER = 0.1

/** 최소 지연 시간 (milliseconds) */
const val DEFAULT_MIN_DELAY_MILLIS = 100L

/** 최대 지연 시간 (milliseconds) */
const val DEFAULT_MAX_DELAY_MILLIS = 10000L

/** 기본 Backoff 시간 (milliseconds) */
const val DEFAULT_PERIOD_MILLIS = 1000L

/** 기본 Random Backoff 시간 (milliseconds) */
const val DEFAULT_RANDOM_RANGE_MILLIS = 100L

/** 기본 Backoff 인스턴스 [FixedIntervalBackoff] */
@JvmField val DEFAULT_BACKOFF: Backoff = FixedIntervalBackoff()
