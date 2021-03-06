package jobs

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
@Secured("hasRole('ROLE_HR')")
class DocumentController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def springSecurityService

    @Secured("hasAnyRole('ROLE_HR','ROLE_USER')")
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        User user = springSecurityService.currentUser
        Role hr = Role.findById(2)
        def documentInstanceList = user.hasRole(hr)? Document.findAll() : Document.findAllByUser(user)

        /*documentInstanceList.each { documentInstance ->
            documentInstance.body = documentInstance?.body?.substring(0, 400);

        }*/

        [documentInstanceCount: Document.count(), documentInstanceList: documentInstanceList]
    }

    @Secured("hasAnyRole('ROLE_HR','ROLE_USER')")
    def show(Document documentInstance) {
        respond documentInstance
    }

    @Secured("hasAnyRole('ROLE_HR','ROLE_USER')")
    def create() {
        User user = User.get(params.userId)
        DocumentType documentType = Document.findByTitle(params.documentType)
        respond new Document(params)
    }

    @Secured("hasAnyRole('ROLE_HR','ROLE_USER')")
    def save(Document documentInstance) {
        if (documentInstance == null) {
            notFound()
            return
        }

        if (documentInstance.hasErrors()) {
            respond documentInstance.errors, view: 'create'
            return
        }

        documentInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'document.label', default: 'Document'), documentInstance.id])
                redirect documentInstance
            }
            '*' { respond documentInstance, [status: CREATED] }
        }
    }

    @Secured("hasAnyRole('ROLE_HR','ROLE_USER')")
    def edit(Document documentInstance) {
        respond documentInstance
    }

    @Secured("hasAnyRole('ROLE_HR','ROLE_USER')")
    def update(Document documentInstance) {
        if (documentInstance == null) {
            notFound()
            return
        }

        if (documentInstance.hasErrors()) {
            respond documentInstance.errors, view: 'edit'
            return
        }

        documentInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Document.label', default: 'Document'), documentInstance.id])
                redirect documentInstance
            }
            '*' { respond documentInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Document documentInstance) {

        if (documentInstance == null) {
            notFound()
            return
        }

        documentInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Document.label', default: 'Document'), documentInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'document.label', default: 'Document'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

