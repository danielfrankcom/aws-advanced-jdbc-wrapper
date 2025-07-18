/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package software.amazon.SpringBootHikariDsqlExample;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableConfigurationProperties(DsqlProperties.class)
public class DataSourceConfiguration {

    private final DsqlProperties dsqlProperties;

    public DataSourceConfiguration(DsqlProperties dsqlProperties) {
        this.dsqlProperties = dsqlProperties;
    }

    @Bean
    @Primary
    public DataSourceProperties dataSourceProperties() {
        String jdbcUrl = String.format(
            "jdbc:aws-wrapper:postgresql://%s.dsql.%s.on.aws:5432/%s",
            dsqlProperties.getClusterId(),
            dsqlProperties.getRegion(),
            dsqlProperties.getDatabase()
        );

        DataSourceProperties properties = new DataSourceProperties();
        properties.setUrl(jdbcUrl);
        properties.setUsername(dsqlProperties.getUsername());
        properties.setDriverClassName("software.amazon.jdbc.Driver");

        return properties;
    }
}
