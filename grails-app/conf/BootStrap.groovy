import org.springframework.context.ApplicationContext
import org.codehaus.groovy.grails.orm.hibernate.ConfigurableLocalSessionFactoryBean
import org.apache.commons.dbcp.BasicDataSource
import simpleguestbook.VCAPService

class BootStrap {

    def grailsApplication

    def init = { servletContext ->

        def beanFactory = grailsApplication.mainContext.autowireCapableBeanFactory

        // get the (already created) dataSource bean
        def dataSourceBean = null
        if (beanFactory.containsBean('dataSourceUnproxied')) {
            dataSourceBean = beanFactory.getBean('dataSourceUnproxied')
        }
        else if (beanFactory.containsBean('dataSource')) {
            dataSourceBean = beanFactory.getBean('dataSource')
        }

        def services = new VCAPService().getAvailableServices()

        def mysqlServices = services.get("mysql")
        if(mysqlServices.isEmpty()) {
            throw new Exception("No Mysql Services found in VCAP_SERVICES")
        }


        def mysql = mysqlServices.get(mysqlServices.keySet().asList().get(0))


        if (dataSourceBean) {

            dataSourceBean.driverClassName = "com.mysql.jdbc.Driver"
            dataSourceBean.url = mysql.get("hostname")+":"+mysql.get("port")
            dataSourceBean.username = mysql.get("user")
            dataSourceBean.password = mysql.get("password")

            // get hibernate session factory (the factory, not the beans created from it)
            def sessionFactory = beanFactory.getBean("&sessionFactory")
            //beanFactory.beanDefinitionNames.each {println it}

            // set the hibernate properties
            sessionFactory.dataSource = dataSourceBean
            sessionFactory.hibernateProperties = ["hibernate.hbm2ddl.auto": "create-drop",
                    "hibernate.show_sql": true]

            // make changes
            sessionFactory.updateDatabaseSchema()
        }

    }


    def destroy = {
    }
}
