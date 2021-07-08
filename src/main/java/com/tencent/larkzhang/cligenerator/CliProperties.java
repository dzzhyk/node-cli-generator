package com.tencent.larkzhang.cligenerator;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author larkzhang
 */
@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "generator.cli")
public class CliProperties {

    private String name;
    private String version;
    private String description;
    private String author;
    private String repo;
    private String license;
    private String[] bin;
    private List<CommandModel> commands;
    private List<OptionModel> options;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class CommandModel {

        private String name;
        private String description;
        private String action;
        private List<OptionModel> options;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class OptionModel {

        private String flags;
        private String description;
        private String callback;
        private String value;
    }
}
