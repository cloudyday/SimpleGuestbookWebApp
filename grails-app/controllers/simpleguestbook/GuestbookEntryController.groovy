package simpleguestbook

class GuestbookEntryController {

    static allowedMethods = [create: ["GET", "POST"], list: "GET"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        def entries = GuestbookEntry.list([max: 10, sort: "date", order:"desc"])

        List<EntryToDisplay> entriesToDisplay = []

        entries.each {
            def entryToDisplay = new EntryToDisplay()
            entryToDisplay.text = it.text
            entryToDisplay.date = it.date
            if(it.user) {
                entryToDisplay.name = it.user.name
                entryToDisplay.email = it.user.email
            } else {
                entryToDisplay.name = it.anonName
                entryToDisplay.email = it.anonEmail
            }
            entriesToDisplay.add(entryToDisplay)
        }

        return [entries:entriesToDisplay]
    }

    def create = {
        def guestbookEntryInstance = new GuestbookEntry()

        if (request.method.equals("POST")) {
            params.date = new Date()

            if(session.user) { params.user = User.findByName(session.user)}

            if(!session.user && !params.anonName) {
                flash.message = "Error: you are not logged in, so you must provide a name"
                return [guestbookEntryInstance: guestbookEntryInstance]
            }

            params.text = params.text.encodeAsHTML()

            guestbookEntryInstance = new GuestbookEntry(params)
            if (guestbookEntryInstance.save()) {
                flash.message = "${message(code: 'default.created.message', args: [message(code: 'guestbookEntry.label', default: 'GuestbookEntry'), guestbookEntryInstance.id])}"
                redirect(action: "index")
            }
            else {
                return [guestbookEntryInstance: guestbookEntryInstance]
            }
        }

        guestbookEntryInstance.properties = params
        return [guestbookEntryInstance: guestbookEntryInstance]
    }


/*    def edit = {
        def guestbookEntryInstance = GuestbookEntry.get(params.id)
        if (!guestbookEntryInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'guestbookEntry.label', default: 'GuestbookEntry'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [guestbookEntryInstance: guestbookEntryInstance]
        }
    }

    def update = {
        def guestbookEntryInstance = GuestbookEntry.get(params.id)
        if (guestbookEntryInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (guestbookEntryInstance.version > version) {

                    guestbookEntryInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'guestbookEntry.label', default: 'GuestbookEntry')] as Object[], "Another user has updated this GuestbookEntry while you were editing")
                    render(view: "edit", model: [guestbookEntryInstance: guestbookEntryInstance])
                    return
                }
            }
            guestbookEntryInstance.properties = params
            if (!guestbookEntryInstance.hasErrors() && guestbookEntryInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'guestbookEntry.label', default: 'GuestbookEntry'), guestbookEntryInstance.id])}"
                redirect(action: "show", id: guestbookEntryInstance.id)
            }
            else {
                render(view: "edit", model: [guestbookEntryInstance: guestbookEntryInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'guestbookEntry.label', default: 'GuestbookEntry'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def guestbookEntryInstance = GuestbookEntry.get(params.id)
        if (guestbookEntryInstance) {
            try {
                guestbookEntryInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'guestbookEntry.label', default: 'GuestbookEntry'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'guestbookEntry.label', default: 'GuestbookEntry'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'guestbookEntry.label', default: 'GuestbookEntry'), params.id])}"
            redirect(action: "list")
        }
    }*/
}

class EntryToDisplay {

    String name
    String email
    String text
    Date date

}
