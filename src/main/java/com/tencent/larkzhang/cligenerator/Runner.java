package com.tencent.larkzhang.cligenerator;

import cn.hutool.core.io.FileUtil;
import com.tencent.larkzhang.cligenerator.CliProperties.CommandModel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.stereotype.Component;

/**
 * @author larkzhang
 */
@Component
public class Runner {

    private final CliProperties cliProperties;

    private final VelocityEngine velocityEngine;

    private final VelocityContext ctx = new VelocityContext();

    private final String baseDir =
            (System.getProperty("user.dir") + File.separator + "output").replaceAll("/+", "/");

    public Runner(CliProperties cliProperties, VelocityEngine velocityEngine) {
        this.cliProperties = cliProperties;
        this.velocityEngine = velocityEngine;
    }

    @PostConstruct
    public void generateCode() {

        initVelocityEngine();
        setCliProperties();
        prepareDir();
        render();

    }

    private void initVelocityEngine() {
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();
    }

    private void setCliProperties() {
        ctx.put("name", cliProperties.getName());
        ctx.put("version", cliProperties.getVersion());
        ctx.put("description", cliProperties.getDescription());
        ctx.put("author", cliProperties.getAuthor());
        ctx.put("repo", cliProperties.getRepo());
        ctx.put("license", cliProperties.getLicense());
        ctx.put("bin", cliProperties.getBin());
        ctx.put("commands", cliProperties.getCommands());
        ctx.put("options", cliProperties.getOptions());
    }

    private void prepareDir() {
        System.out.println("node-cli项目生成位置: " + baseDir);
        FileUtil.mkdir(baseDir);
        FileUtil.mkdir(baseDir + File.separator + "bin");
        FileUtil.mkdir(baseDir + File.separator + "lib");
    }

    private void render() {

        renderTemplate("templates/cli.js.vm", "bin/cli.js");
        List<CommandModel> commands = cliProperties.getCommands();
        for (CommandModel command : commands) {
            renderTemplate("templates/lib.js.vm", "lib/" + command.getName() + ".js");
        }
        renderTemplate("templates/package.json.vm", "package.json");

    }

    private void renderTemplate(String tpName, String dstFile) {

        Template t = velocityEngine.getTemplate(tpName, "UTF-8");
        StringWriter sw = new StringWriter();
        t.merge(ctx, sw);

        File dst = new File(baseDir + File.separator + dstFile);

        try {
            FileWriter fileWriter = new FileWriter(dst);
            fileWriter.write(sw.toString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
