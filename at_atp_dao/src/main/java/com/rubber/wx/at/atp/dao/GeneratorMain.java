package com.rubber.wx.at.atp.dao;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * @author luffyu
 * Created on 2022/5/14
 */
public class GeneratorMain {

    public static void main(String[] args) {
        create("t_task_info");
    }


    public static void create(String... tables) {
        AutoGenerator mpg = new AutoGenerator();
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("at_atp_dao/src/main/java");
        gc.setAuthor("luffyu");
        gc.setOpen(false);
        gc.setFileOverride(false);


        mpg.setGlobalConfig(gc);
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://127.0.0.1:3306/at_tennis?useUnicode=true;characterEncoding=utf-8");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("root");
        mpg.setDataSource(dataSourceConfig);
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.rubber.wx.at.atp.dao");
        pc.setXml("mapper");
        pc.setEntity("entity");
        pc.setService("dal");
        pc.setServiceImpl("dal.impl");


        mpg.setPackageInfo(pc);
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);


        strategy.setEntityLombokModel(true);
        strategy.setInclude(tables);
        strategy.setVersionFieldName("Fversion");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(new String[]{"t_"});
        strategy.setFieldPrefix("F");

        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
