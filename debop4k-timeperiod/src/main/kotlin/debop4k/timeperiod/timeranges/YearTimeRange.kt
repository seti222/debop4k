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

package debop4k.timeperiod.timeranges

import debop4k.core.kodatimes.now
import debop4k.timeperiod.*
import debop4k.timeperiod.utils.*
import org.joda.time.DateTime

/**
 * Created by debop
 */
open class YearTimeRange @JvmOverloads constructor(val year: Int,
                                                   val yearCount: Int = 1,
                                                   calendar: ITimeCalendar = DefaultTimeCalendar)
: YearCalendarTimeRange(relativeYearPeriod(year, yearCount),
                        calendar) {

  constructor() : this(now().year)

  @JvmOverloads
  constructor(m: DateTime,
              yearCount: Int = 1,
              calendar: ITimeCalendar = DefaultTimeCalendar)
  : this(m.year, yearCount, calendar)


  fun halfyearSequence(): Sequence<HalfyearRange>
      = halfyearRangeSequence(start, yearCount * HalfyearsPerYear, calendar)

  fun halfyears(): List<HalfyearRange> = halfyearSequence().toList()

  fun quarterSequence(): Sequence<QuarterRange>
      = quarterRangeSequence(start, yearCount * QuartersPerYear, calendar)

  fun quarters(): List<QuarterRange> = quarterSequence().toList()

  fun monthSequence(): Sequence<MonthRange>
      = monthRangeSequence(start, yearCount * MonthsPerYear, calendar)

  fun months(): List<MonthRange> = monthSequence().toList()

  fun daySequence(): Sequence<DayRange> {
    return monthSequence().flatMap(MonthRange::daySequence).asSequence()
  }

  fun days(): List<DayRange> = daySequence().toList()

  fun hourSequence(): Sequence<HourRange> {
    return daySequence().flatMap(DayRange::hourSequence).asSequence()
  }

  fun hours(): List<HourRange> = days().flatMap(DayRange::hours)

  fun minuteSequence(): Sequence<MinuteRange> {
    return hourSequence().flatMap(HourRange::minuteSequence).asSequence()
  }

  fun minutes(): List<MinuteRange> = hours().flatMap(HourRange::minutes)

}

