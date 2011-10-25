package simpleguestbook

class User {

    String name
    String password
    String role = "user"
    String email

    static hasMany = [entries:GuestbookEntry]

    def beforeInsert = {
        password = password.encodeAsSHA1()
    }

    static constraints = {
        name(blank: false, size: 3..15, unique: true)
        password(blank: false, password: true)
        role(inList: ["admin", "user"])
        email(blank: false)
    }
}
