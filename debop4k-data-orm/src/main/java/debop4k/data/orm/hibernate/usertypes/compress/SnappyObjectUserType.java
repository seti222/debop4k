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

package debop4k.data.orm.hibernate.usertypes.compress;

import debop4k.core.compress.Compressor;
import debop4k.core.compress.Compressx;

/**
 * {@link debop4k.core.compress.SnappyCompressor} 를 이용하여 객체를 압축하여 바이트 배열로 저장합니다.
 *
 * @author sunghyouk.bae@gmail.com
 * @since 2015. 8. 26.
 */
public class SnappyObjectUserType extends CompressedObjectUserType {

  @Override
  public Compressor compressor() {
    return Compressx.getSNAPPY();
  }
}
