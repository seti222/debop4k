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

package debop4k.batch.config;

import debop4k.data.spring.boot.autoconfigure.HikariDataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * BatchJobConfiguration
 *
 * @author sunghyouk.bae@gmail.com
 */
@Configuration
@EnableAutoConfiguration(exclude = {FlywayAutoConfiguration.class})
@Import({
            BatchDataSourceConfiguration.class,
            BatchInfrastructureConfiguration.class
        })
public class BatchJobConfiguration extends HikariDataSourceAutoConfiguration {
}
