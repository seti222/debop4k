/*
 * Copyright (c) 2016. KESTI co, ltd
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

package debop4k.core.asyncs.kovenant.examples

import debop4k.core.AbstractCoreKotlinTest
import debop4k.core.asyncs.ready
import nl.komponents.kovenant.task
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DeferredTest : AbstractCoreKotlinTest() {

  @Test fun deferred() {
    var isCompleted = false
    val promise = task {
      log.debug("async task is starting...")
      Thread.sleep(1000L)
      log.debug("async task is completed")
      isCompleted = true
    }
    assertThat(isCompleted).isEqualTo(promise.isDone())
    // ready
    promise.ready()
    assertThat(isCompleted).isEqualTo(promise.isDone()).isTrue();
  }
}