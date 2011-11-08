package simpleguestbook

import grails.converters.JSON

class VCAPService {

    static transactional = false

    def getAvailableServices() {

        String VCAP_SERVICES = null

        //VCAP_SERVICES = "{'mysql-5.1':[{'name':'mysql-914bf','label':'mysql-5.1','plan':'free','tags':['mysql','mysql-5.1','relational'],'credentials':{'name':'dc680c687b2744aa1874506c7c52fe0e0','hostname':'217.160.94.115','host':'217.160.94.115','port':3306,'user':'uv7KjkXsrmJy4','username':'uv7KjkXsrmJy4','password':'puH51m6WWj3wB'}},{'name':'mysql123345','label':'mysql-5.1','plan':'free','tags':['mysql','mysql-5.1','relational'],'credentials':{'name':'d744db6dba9754fa88958ddfa045fce38','hostname':'217.160.94.114','host':'217.160.94.114','port':3306,'user':'utdHzeuefcrT0','username':'utdHzeuefcrT0','password':'p7bkvdFLo5awk'}}]}"

        VCAP_SERVICES = System.getenv('VCAP_SERVICES')

        if (VCAP_SERVICES == null || VCAP_SERVICES.equals("")) {
            throw new Exception("Could not read VCAP_SERVICES")
        }

        def mysql = [:]
        def redis = [:]
        def mongo = [:]

        try {

            def servicesMap = JSON.parse(VCAP_SERVICES)//.(VCAP_SERVICES)

            servicesMap.each { key, services ->

                if (key.startsWith('mysql')) {

                    services.each {
                        def mysqlService = [hostname: it.credentials.hostname, port: it.credentials.port, user: it.credentials.user, password: it.credentials.password]
                        mysql.put(it.name, mysqlService)
                    }
                }
                else if (key.startsWith('redis')) {

                    for (service in services) {
                        def redisService = [hostname: "$service.credentials.hostname", port: "$service.credentials.port", name: "$service.credentials.name", password: "$service.credentials.password"]
                        redis.put(service.name, redisService)
                    }
                }
                else if (key.startsWith('mongo')) {

                    for (service in services) {
                        def mongoService = [username: "$service.credentials.username", hostname: "$service.credentials.hostname", port: "$service.credentials.port", db: "$service.credentials.db", password: "$service.credentials.password", name: service.credentials.name]
                        mongo.put(service.name, mongoService)
                    }
                }

            }
        }
        catch (e) {
            print "Error occured  parsing VCAP_Service: $e.message"
        }


        def result = ["mysql":mysql, "redis":redis, "mongo":mongo]
        return result
    }
}
