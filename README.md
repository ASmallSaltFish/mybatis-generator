### mybatis逆向工程，快速生成mapper.xml和pojo
* 使用环境：idea + maven

1、新建maven项目，pom.xml中添加插件
```$xslt
<!-- mybatis逆向工程 -->
<plugin>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-maven-plugin</artifactId>
    <version>1.3.2</version>
    <configuration>
        <!--配置文件的位置-->
        <configurationFile>src/main/resources/generatorConfig.xml</configurationFile>
        <verbose>true</verbose>
        <overwrite>true</overwrite>
    </configuration>
</plugin>
```

2、在resource目录中添加配置generatorConfig.xml文件
```$xslt
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>
    <properties resource="generator-db.properties"/>
    <classPathEntry location="${jdbc.driverLocation}" />
    <context id="context1" targetRuntime="MyBatis3">

        <commentGenerator>
            <!-- 去除自动生成的注释 -->
            <property name="suppressAllComments" value="true" />
        </commentGenerator>

        <!-- 数据库连接配置 -->
        <jdbcConnection driverClass="${jdbc.driverClass}"
                        connectionURL="${jdbc.connectionURL}"
                        userId="${jdbc.userId}"
                        password="${jdbc.password}" />

        <!--jdbcConnection driverClass="com.mysql.jdbc.Driver"
                        connectionURL="jdbc:mysql://localhost:3306/test"
                        userId="root"
                        password="mysql" /-->

        <!-- 非必需，类型处理器，在数据库类型和java类型之间的转换控制-->
        <javaTypeResolver>
            <property name="forceBigDecimals" value="false"/>
        </javaTypeResolver>

        <!--配置生成的实体包
            targetPackage：生成的实体包位置，默认存放在src目录下
            targetProject：目标工程名
         -->
        <javaModelGenerator targetPackage="com.my.entity"
                            targetProject="src/main/java" />

        <!-- 实体包对应映射文件位置及名称，默认存放在src目录下 -->
        <sqlMapGenerator targetPackage="com.my.mapper" targetProject="src/main/java" />

        <!-- 配置表
            schema：不用填写
            tableName: 表名
            enableCountByExample、enableSelectByExample、enableDeleteByExample、enableUpdateByExample、selectByExampleQueryId：
            去除自动生成的例子
        -->
        <table schema="" tableName="t_bw_user" domainObjectName="User" enableCountByExample="false" enableSelectByExample="true"
               enableDeleteByExample="false" enableUpdateByExample="true" selectByExampleQueryId="true" >
        </table>
    </context>
</generatorConfiguration>
```

3、resource文件中添加generator-db.properties
```$xslt
#配置数据库驱动包路径（mysql）
jdbc.driverLocation=src/main/webapp/WEB-INF/lib/mysql-connector-java-5.1.28.jar
jdbc.driverClass=com.mysql.jdbc.Driver
jdbc.connectionURL=jdbc:mysql://localhost:3306/blueWhale
jdbc.userId=root
jdbc.password=12345678
```

4、idea中添加maven执行命令 <br>
   Edit Configurations -> maven，配置命令：mybatis-generator:generate -e

5、执行maven命令


> 注意：配置文件中的各种路径，需要自己配置，如果需要满足一些个性化生成，自行百度对应配置generatorConfig.xml的内容；
