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

package debop4k.core.compress

import debop4k.core.collections.emptyByteArray
import debop4k.core.collections.isNullOrEmpty
import org.xerial.snappy.Snappy

/**
 * Snappy 압축 라이브러리를 이용한 압축기
 * @author sunghyouk.bae@gmail.com
 */
class SnappyCompressor : Compressor {

  /**
   * 압축
   */
  override fun compress(input: ByteArray?): ByteArray {
    if (input.isNullOrEmpty)
      return emptyByteArray

    return Snappy.compress(input)
  }

  /**
   * 압축 복원
   */
  override fun decompress(input: ByteArray?): ByteArray {
    if (input.isNullOrEmpty)
      return emptyByteArray

    return Snappy.uncompress(input)
  }

}