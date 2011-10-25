package simpleguestbook

class UserController {

    static allowedMethods = [register: ["GET","POST"], login: ["GET", "POST"], logout: "GET"]

    def index = {
        redirect(action: "register", params: params)
    }

    def register = {
        if (request.method.equals("POST")) {
            def userInstance = new User(params)
            if (userInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])}"
                redirect(action: "show", id: userInstance.id)
            }
            else {
                return [userInstance: userInstance]
            }
        }
    }

    def login = {
        if (request.method.equals("POST")) {
            authenticate()
            return
        }

        if (session.user) {
            flash.message = "You are already logged in as $session.user!"
            redirect(controller: "guestbookEntry")
        }
    }

    def logout = {
        def user = User.findByName(session.user)
        flash.message = "Goodbye ${user?.name} !"
        session.user = null
        redirect(controller: "guestbookEntry")
    }

    def authenticate() {
        def user = User.findByNameAndPassword(params.name, params.password.encodeAsSHA1())

        if (user) {
            session.user = user.name
            flash.message = "Hello $user.name !"

            redirect(controller: "guestbookEntry")

        } else {
            flash.message = "Sorry, $params.name - Please try again!"
            def userInstance = new User(name: params.name)
            render(view: "login", model:[userInstance:userInstance])
        }
    }


/*    def show = {
        def userInstance = User.get(params.id)
        if (!userInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
            redirect(action: "list")
        }
        else {
            [userInstance: userInstance]
        }
    }

    def edit = {
        def userInstance = User.get(params.id)
        if (!userInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [userInstance: userInstance]
        }
    }

    def update = {
        def userInstance = User.get(params.id)
        if (userInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (userInstance.version > version) {

                    userInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'user.label', default: 'User')] as Object[], "Another user has updated this User while you were editing")
                    render(view: "edit", model: [userInstance: userInstance])
                    return
                }
            }
            userInstance.properties = params
            if (!userInstance.hasErrors() && userInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])}"
                redirect(action: "show", id: userInstance.id)
            }
            else {
                render(view: "edit", model: [userInstance: userInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
            redirect(action: "list")
        }
    }*/
}
