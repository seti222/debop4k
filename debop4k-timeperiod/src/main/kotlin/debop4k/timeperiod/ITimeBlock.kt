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

package debop4k.timeperiod

import debop4k.core.kodatimes.abs
import org.joda.time.DateTime
import org.joda.time.Duration

interface ITimeBlock : ITimePeriod {

  override var start: DateTime

  override var end: DateTime

  override var duration: Duration

  fun setup(ns: DateTime, nd: Duration)

  /** 시작 시점을 기준으로 기간을 설정하여, 종료 시각을 변경합니다 */
  fun durationFromStart(nd: Duration)

  /** 종료 시점을 기준으로 기간을 설정하여, 시작 시각을 변경합니다 */
  fun durationFromEnd(nd: Duration)

  /** offset 만큼 다음 Block */
  fun nextBlock(offset: Duration): ITimeBlock {
    return TimeBlock(end + offset.abs(), duration, readonly)
  }

  /** offset 만큼 이전 Block */
  fun prevBlock(offset: Duration): ITimeBlock {
    return TimeBlock(duration, start - offset.abs(), readonly)
  }
}