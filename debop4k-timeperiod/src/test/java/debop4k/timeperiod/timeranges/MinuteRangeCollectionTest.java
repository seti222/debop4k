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

package debop4k.timeperiod.timeranges;

import debop4k.timeperiod.AbstractTimePeriodTest;
import debop4k.timeperiod.TimeCalendar;
import debop4k.timeperiod.utils.Times;
import org.joda.time.DateTime;
import org.junit.Test;
import org.slf4j.Logger;

import java.util.List;

import static debop4k.core.kodatimes.KodaTimex.trimToSecond;
import static org.assertj.core.api.Assertions.assertThat;

public class MinuteRangeCollectionTest extends AbstractTimePeriodTest {

  private static final Logger log = org.slf4j.LoggerFactory.getLogger(MinuteRangeCollectionTest.class);

  @Test
  public void singleMinutes() {
    DateTime now = Times.now();
    MinuteRangeCollection minutes = new MinuteRangeCollection(now, 1, TimeCalendar.EMPTY_OFFSET);

    DateTime startTime = trimToSecond(now);
    DateTime endTime = trimToSecond(now).plusMinutes(1);

    assertThat(minutes.getMinuteCount()).isEqualTo(1);

    assertThat(minutes.getStart()).isEqualTo(startTime);
    assertThat(minutes.getEnd()).isEqualTo(endTime);

    List<MinuteRange> mins = minutes.minutes();

    assertThat(mins.size()).isEqualTo(1);
    assertThat(mins.get(0).getStart()).isEqualTo(startTime);
    assertThat(mins.get(0).getEnd()).isEqualTo(endTime);
  }

  @Test
  public void calendarMinutes() {
    DateTime now = Times.now();

    for (int m = 1; m < 97; m += 5) {
      MinuteRangeCollection minutes = new MinuteRangeCollection(now, m);

      DateTime startTime = trimToSecond(now);
      DateTime endTime = trimToSecond(now).plusMinutes(m).plus(minutes.getCalendar().getEndOffset());

      assertThat(minutes.getMinuteCount()).isEqualTo(m);
      assertThat(minutes.getStart()).isEqualTo(startTime);
      assertThat(minutes.getEnd()).isEqualTo(endTime);

      List<MinuteRange> items = minutes.minutes();

      for (int i = 0; i < m; i++) {
        assertThat(items.get(i).getStart()).isEqualTo(startTime.plusMinutes(i));
        assertThat(items.get(i).getUnmappedStart()).isEqualTo(startTime.plusMinutes(i));

        assertThat(items.get(i).getEnd()).isEqualTo(minutes.getCalendar().mapEnd(startTime.plusMinutes(i + 1)));
        assertThat(items.get(i).getUnmappedEnd()).isEqualTo(startTime.plusMinutes(i + 1));
      }
    }
  }

  @Test
  public void minutesTest() {

    final int[] minuteCounts = new int[]{1, 24, 48, 64, 128};
    final DateTime now = Times.now();

    for (int minuteCount : minuteCounts) {
      final MinuteRangeCollection minuteRanges = new MinuteRangeCollection(now, minuteCount);
      final DateTime startTime = trimToSecond(now).plus(minuteRanges.getCalendar().getStartOffset());
      final DateTime endTime = startTime.plusMinutes(minuteCount).plus(minuteRanges.getCalendar().getEndOffset());

      assertThat(minuteRanges.getStart()).isEqualTo(startTime);
      assertThat(minuteRanges.getEnd()).isEqualTo(endTime);
      assertThat(minuteRanges.getMinuteCount()).isEqualTo(minuteCount);

      final List<MinuteRange> items = minuteRanges.minutes();
      assertThat(items.size()).isEqualTo(minuteCount);

      for (int m = 0; m < minuteCount; m++) {
        assertThat(items.get(m).getStart()).isEqualTo(startTime.plusMinutes(m));
        assertThat(items.get(m).getEnd()).isEqualTo(minuteRanges.getCalendar().mapEnd(startTime.plusMinutes(m + 1)));
        assertThat(items.get(m).getUnmappedEnd()).isEqualTo(startTime.plusMinutes(m + 1));
      }
    }
  }
}
