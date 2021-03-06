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

package debop4k.data

import debop4k.config.database.DatabaseSetting
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.sql.SQLException
import javax.sql.DataSource

/**
 * @author sunghyouk.bae@gmail.com
 */
class DataSourcesKotlinTest : AbstractDataKotlinTest() {

  private fun connectionTest(ds: DataSource) {
    for (i in 0 until 50) {
      ds.connection.let { conn ->
        try {
          assertThat(conn).isNotNull()
          val ps = conn.prepareStatement("SELECT 1")
          try {
            assertThat(ps).isNotNull()
            assertThat(ps.execute()).isTrue()
          } finally {
            ps.close()
          }
        } catch(e: SQLException) {
          throw RuntimeException("connection error")
        } finally {
          conn.close()
        }
      }
    }
  }


  @Test fun `embedded h2`() {
    val ds = DataSources.ofEmbeddedH2()
    connectionTest(ds)
  }

  @Test fun `embedded hsqldb`() {
    val ds = DataSources.ofEmbeddedHSql()

    val conn = ds.connection
    try {
      assertThat(conn).isNotNull()
    } finally {
      conn.close()
    }
  }

  @Test fun `create datasource directly`() {
    val setting = DatabaseSetting(driverClass = JdbcDrivers.DRIVER_CLASS_H2,
                                  jdbcUrl = "jdbc:h2:mem:direct",
                                  username = "sa",
                                  maxPoolSize = 10,
                                  minIdleSize = 4)
    val ds = DataSources.of(setting)
    connectionTest(ds)
  }

  @Test fun `create mysql connection`() {
    val setting = DatabaseSetting(driverClass = JdbcDrivers.DRIVER_CLASS_MARIADB,
                                  jdbcUrl = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC",
                                  username = "root",
                                  password = "root")
    val ds = DataSources.of(setting)
    assertThat(ds).isNotNull()

    val conn = ds.connection
    try {
      assertThat(conn).isNotNull()
    } finally {
      conn.close()
    }
  }

}