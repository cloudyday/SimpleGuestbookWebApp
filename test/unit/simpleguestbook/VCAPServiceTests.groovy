package simpleguestbook

import grails.test.*

class VCAPServiceTests extends GrailsUnitTestCase {

    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSomething() {

        VCAPService vcapService = new VCAPService()
        def services = vcapService.getAvailableServices()

        def mysqlServices = services.get("mysql")
        if(mysqlServices.isEmpty()) {
            throw new Exception("No Mysql Services found in VCAP_SERVICES")
        }

//        def mysql = mysqlServices.get(mysqlServices.keySet().asList().get(0))
//
//        mysql.entrySet().each {
//            println it.key + " : " + it.value
//        }


        mysqlServices.each {
            println it.key
            it.each {
                println it
            }
        }

    }
}
