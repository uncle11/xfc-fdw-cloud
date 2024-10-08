package com.xfc.common.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Scanner;

/**
 * @version 1.0
 * @description: TODO
 * @author: chenss
 * @date 2022-10-11 16:14
 */
public class CodeGenerator {

    // 生成的代码放到哪个工程中

      private static String PROJECT_NAME="xfc-dict";
    // 数据库名称
    private static String DATABASE_NAME = "xfc_dict";

    // 子包名
    private static String MODULE_NAME = "dict";

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/"+DATABASE_NAME+"?useUnicode=true&characterEncoding=utf8&useSSL=false");
//        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUrl("jdbc:postgresql://127.0.0.1:5432/"+DATABASE_NAME);
        dsc.setDriverName("org.postgresql.Driver");
        dsc.setUsername("postgres");
        dsc.setPassword("postgres");
        mpg.setDataSource(dsc);

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir") + "/";
        gc.setOutputDir(projectPath + PROJECT_NAME +"/src/main/java");
        // 分布式id
        gc.setIdType(IdType.ASSIGN_ID);
        gc.setAuthor("xfc");
        //覆盖现有的
        gc.setFileOverride(false);
        //是否生成后打开
        gc.setOpen(false);
        gc.setDateType(DateType.ONLY_DATE);
        //实体属性 Swagger2 注解
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        //父包名
        pc.setParent("com.xfc");
        pc.setController(MODULE_NAME+".controller");
        pc.setService(MODULE_NAME+".service");
        pc.setServiceImpl(MODULE_NAME+".service.impl");
        pc.setMapper(MODULE_NAME+".mapper");
        pc.setXml(MODULE_NAME+".mapper.xml");
        pc.setEntity(MODULE_NAME+".entities");
        mpg.setPackageInfo(pc);


        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //使用lombok
        strategy.setEntityLombokModel(true);
        // 实体类的实现接口Serializable
        strategy.setEntitySerialVersionUID(true);
        // @RestController
        strategy.setRestControllerStyle(true);
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        // 去掉表前缀
//        strategy.setTablePrefix("workflow_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }


    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }
}
