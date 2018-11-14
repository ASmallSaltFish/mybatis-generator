### mybatis逆向工程，快速生成mapper.xml和pojo
* 使用环境：idea + maven

#### mybatis使用xml方式配置的逆向工程
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

4、idea中添加maven执行命令
![IMAGE](quiver-image-url/1654B59112862A2D4A059C7FD54F9A18.jpg =1105x631)

5、执行maven命令
![IMAGE](quiver-image-url/5865CEED7F8E14475B7074E4629696FD.jpg =447x270)


> 注意：配置文件中的各种路径，需要自己配置，如果需要满足一些个性化生成，自行百度对应配置generatorConfig.xml的内容；


#### 使用mybatis-plus代码生成工具
* com.my.GeneratorServiceEntity
> 修改该类数据库连接信息，生成的包路径，添加需要逆向生成的表信息即可，具体看代码配置，然后执行main方法即可；