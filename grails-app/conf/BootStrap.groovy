import org.springframework.context.ApplicationContext
import org.codehaus.groovy.grails.orm.hibernate.ConfigurableLocalSessionFactoryBean
import org.apache.commons.dbcp.BasicDataSource

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

        if (dataSourceBean) {
            def updatedValues = [:]

            updatedValues.url = "jdbc:hsqldb:mem:devDB"
            updatedValues.driverClassName = "org.hsqldb.jdbcDriver"
            updatedValues.username = "sa"
            updatedValues.password = ""

            dataSourceBean.driverClassName = updatedValues.driverClassName
            dataSourceBean.url = updatedValues.url
            dataSourceBean.username = updatedValues.userName
            dataSourceBean.password = updatedValues.password

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
