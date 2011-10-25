package simpleguestbook

class GuestbookEntry {

    String text
    Date date
    String anonName
    String anonEmail

    static belongsTo = [user:User]

    static constraints = {
        text(blank: false)
        anonEmail(nullable: true, blank: false)
        anonName(nullable: true, blank: false, size:3..15)
        user(nullable: true)
    }
}
