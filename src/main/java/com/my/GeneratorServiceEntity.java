package generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.DB2TypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;

/**
 * 使用mybatis-plus代码代码生成器，
 * 生成service、serviceImpl、entity、mapper包以及对应文件，按照表名生成（下划线转化为驼峰命名法）
 *
 * @author qinyupeng
 * @since 2018-11-13 19:35:04
 */
public class GeneratorServiceEntity {

    //设置生成文件的存放路径（该路径下面就是包名所在文件夹）
    private String OUT_DIR = "src/main/java";

    //设置生成的父级别包名
    private static String BASE_PACKAGE = "com.my";

    //user -> UserService, 设置成true: user -> IUserService
    private static boolean serviceNameStartWithI = false;

    //需要代码生成的数据库表名
    private static String[] tableNames = {"T_ROLE"};

    //将表名前缀移除后，实体类名再使用驼峰命名法命名
    private static String removeTbPrefix = "T_";


    /**
     * 直接运行，可以自动生成符合mybatis-plus规范的代码
     */
    public static void main(String[] args) {
        new GeneratorServiceEntity().generateByTables(serviceNameStartWithI, BASE_PACKAGE, removeTbPrefix, tableNames);
    }


    private void generateByTables(boolean serviceNameStartWithI, String packageName, String tbPrefix, String... tableNames) {
        GlobalConfig config = new GlobalConfig();
        String dbUrl = "jdbc:db2://192.168.43.203:50000/SAMPLE";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.DB2)
                .setUrl(dbUrl)
                .setUsername("db2admin")
                .setPassword("db2admin")
                //自定义数据库类型转换
                .setTypeConvert(new DB2TypeConvert())
                .setDriverName("com.ibm.db2.jcc.DB2Driver");

        StrategyConfig strategyConfig = new StrategyConfig();
        //设置标表前缀（在生成文件中会对应去除该表前缀）
        strategyConfig.setTablePrefix(tbPrefix);
        strategyConfig.setCapitalMode(true)
                .setEntityLombokModel(false)
                .setDbColumnUnderline(false)
                .setNaming(NamingStrategy.underline_to_camel)
                .setRestControllerStyle(true)
                //是否生成实体时，生成字段注解
                .entityTableFieldAnnotationEnable(true)
                //修改替换成你需要的表名，多个表名传数组
                .setInclude(tableNames);

        config.setActiveRecord(false)
                .setAuthor("auto generator")
                .setOutputDir(OUT_DIR)
                .setFileOverride(true);

        if (!serviceNameStartWithI) {
            config.setServiceName("%sService");
        }

        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(new PackageConfig()
                        .setParent(packageName)
                        .setEntity("entity")
                        .setServiceImpl("service")
                        .setServiceImpl("service.impl")
                        .setMapper("mapper")
                        //该生成xml目录会被删除
                        .setXml("mapper.xml")
                        //生成的web目录会被删除（目前不需要自动生成controller文件）
                        .setController("controller")
                ).execute();

        //生成的xml目录和mapper.xml文件无法配置不生成，这里直接移除生成的mapper/xml目录中的映射文件（不需要的文件）
        String xmlDirPath = packageToFilePath(packageName);
        if (xmlDirPath == null) {
            return;
        }

        File xmlDir = new File(OUT_DIR + "/" + xmlDirPath + "mapper/xml");
        System.out.println("==>>删除的目录：" + xmlDir.getAbsolutePath());
        removeDir(xmlDir);
    }

    private void removeDir(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                file.delete();
            }

            dir.delete();
        }
    }

    //将包名转化为文件夹路径名
    private String packageToFilePath(String packageName) {
        if (packageName.contains(".")) {
            String[] dirs = packageName.split("\\.");
            String dirPath = "";
            int length = dirs.length;
            int index = 0;
            while (length > 0) {
                dirPath += dirs[index++] + "/";
                length--;
            }

            return dirPath;
        }

        return null;
    }
}