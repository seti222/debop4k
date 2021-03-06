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

package debop4k.data.orm.springdata.examples.caching;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CachingConfiguration.class})
@Transactional
@Slf4j
public class CachingTest {

  @Inject UserRepository repository;
  @Inject CacheManager cacheManager;

  @Test
  public void userCachingByUsername() throws Exception {

    User dave = repository.save(new User("Dave"));
    assertThat(dave).isNotNull();
    assertThat(dave.getId()).isNotNull();

    log.debug("Dave={}", dave);

    User loaded = repository.findByUsername("Dave");
    assertThat(loaded).isEqualTo(dave);

    // 캐시에 저장할 시간을 준다.
    Thread.sleep(500);

    Cache cache = cacheManager.getCache("users");
    Cache.ValueWrapper wrapper = cache.get("username:Dave");

    assertThat(wrapper).isNotNull();
    assertThat(wrapper.get()).isEqualTo(dave);

    loaded = repository.findById(dave.getId());
    assertThat(loaded).isEqualTo(dave);

    // 캐시에 저장할 시간을 준다.
    Thread.sleep(500);

    wrapper = cache.get("userId:" + dave.getId());

    assertThat(wrapper).isNotNull();
    assertThat(wrapper.get()).isEqualTo(dave);
  }
}
