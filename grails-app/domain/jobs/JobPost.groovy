package jobs

class JobPost {

    Job job
    Application application
    EmploymentType employmentType

    String postStart
    String postEnd
    String salaryRange
    Boolean active
    JobPost previousJobPost

    static mapping = {
        active defaultValue: true
    }

    static hasOne = EmploymentType

    static constraints = {
        salaryRange nullable: true
        previousJobPost nullable: true
        application nullable: true


        // start(validator: { val, obj -> val < end })  // make sure start date is not after end date
    }

    static belongsTo = [
            job:Job
            ]

    static hasMany = Application

    String toString() {"$job"}
}
