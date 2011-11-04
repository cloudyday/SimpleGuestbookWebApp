package simpleguestbook
import grails.converters.JSON
class VCAPService {

    static transactional = true

    def serviceMethod(request) {
        //request is the specific information of VCAP_SERVICE eg mysql username
        //information variables
        def mysql                //VCAP_SERVICE MYSQL Info
        def redis                //VCAP_SERVICE REDIS Info
        def mongo                //VCAP_SERVICE Mongo Info
        def hyper                //VCAP_SERVICE hyper/default for original settings Info

        //create test VCAP_Servce
        //String VCAP_SERVICES = System.getenv('VCAP_SERVICES')
                  String VCAP_SERVICES= "{'hyper': [{'name':'hyber', 'label':'redis-2.2','plan':'free', 'credentials':{'url':'jdbc:hsqldb:mem:devDB','driverClassName': 'org.hsqldb.jdbcDriver', 'username': 'sa','password':''}}],'redis-2.2':[{'name':'redis-1d8e28a',  'label':'redis-2.2','plan':'free',  'credentials':{'node_id':'redis_node_3','hostname':'172.30.48.42','port':5004,'password':'1463d9d0-4e35-4f2e-be2f-01dc5536f183','name':'redis-1a69a915-6522-496c-93d5-1271d2b3118e'}}],'mongodb-1.8':[{'name':'mongodb-3854dbe','label':'mongodb-1.8','plan':'free','credentials':{'hostname':'172.30.48.63','port':25003,'username':'b6877670-da98-4124-85ca-84357f042797','password':'f53e6a4b-f4b8-497d-ac81-43cb22cf1e88','name':'mongodb-9dda2cfb-9672-4d58-8786-98c3abcb21ec','db':'db'}}],'mysql-5.1':[{'name':'mysql-497b12e','label':'mysql-5.1','plan':'free','credentials':{'node_id':'mysql_node_8','hostname':'172.30.48.27','port':3306,'password':'p3rO5K5lRZaEU','name':'d887d4f5664f64dde86f3ce42c6333962','user':'umuPIJ8IzSKVA'}}]}"

        try{

        def servicesMap = JSON.parse(VCAP_SERVICES)//.(VCAP_SERVICES)

            servicesMap.each { key, services ->

            if (key.startsWith('mysql')) {
                 println("4")
               for (service in services) {
                   //insert mysql information
                       mysql =[name:"$service.name", hostname:"$service.credentials.hostname", port:"$service.credentials.port", user:"$service.credentials.user", password:"$service.credentials.password"]
               }
            }
              else if (key.startsWith('redis')) {
             for (service in services) {
                 //insert redis information
                 redis =[name:"$service.credentials.name", hostname:"$service.credentials.hostname", port:"$service.credentials.port",  password:"$service.credentials.password"]
             }
            }
            else if (key.startsWith('mongo')) {
             for (service in services) {
                 //inser mongo information
                 mongo =[username:"$service.credentials.username", name:"$service.name", hostname:"$service.credentials.hostname", port:"$service.credentials.port",db:"$service.credentials.db", password:"$service.credentials.password"]
                 }
            }
            else if (key.startsWith('hyper')) {
             for (service in services) {
                 //inser mongo information
                 hyper =[url:"$service.credentials.url", driverClassName:"$service.credentials.driverClassName", username:"$service.username", password:"$service.credentials.password"]
                 }
            }

            }
        }
          catch (e) {
                    print "Error occured  parsing VCAP_Service: $e.message"
            }

               def result=[mysql.get(request), redis.get(request), mongo.get(request), hyper.get(request)]
        return  result
    }
}
