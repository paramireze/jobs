<%@ page import="jobs.Document; jobs.Application" %>

<input type="hidden" name="jobPost" value="${applicationInstance.jobPost.id}" />
<input type="hidden" name="user" value="${user.id}" />
<div class="form-group">
    <label for="inputName" class="col-sm-2 control-label">Name</label>
    <div class="col-sm-10">
        <input type="text" disabled class="form-control" value="${user}" id="inputName" placeholder="Name">
    </div>
</div>
<div class="form-group">
    <label for="inputEmail" class="col-sm-2 control-label">Email</label>
    <div class="col-sm-10">
        <input type="email" disabled class="form-control" id="inputEmail" value="${user.email}" placeholder="Email">
    </div>
</div>
<div class="form-group">
    <label for="inputResume" class="col-sm-2 control-label">Resume</label>
    <div class="col-sm-10">
        <g:select name="resume"
                  from="${resumes}"
                    id="inputResume"
                  value="${applicationInstance?.resume?.id}"
                  optionKey="id"
                  noSelection="['':'Choose a Saved Resume']"
                    />
        <g:link controller="document" action="create" params="[userId: user.id, documentType: 'resume']">Add Resume</g:link>
    </div>
</div>
<div class="form-group">
    <label for="inputCoverLetter" class="col-sm-2 control-label">Cover Letter</label>
    <div class="col-sm-10">
        <g:select name="coverLetter"
                  from="${coverLetters}"
                    id="inputCoverLetter"
                  value="${applicationInstance?.coverLetter?.id}"
                  optionKey="id"
                  noSelection="['':'Choose a Saved Coverletter']"/>
        <g:link controller="document" action="create" params="[userId: user.id, documentType: 'coverLetter']">Add Cover Letter</g:link>
    </div>
</div>
<div class="form-group">
    <label for="body" class="col-sm-2 control-label">Free Text</label>
    <div class="col-sm-10">
        <ckeditor:editor name="freeText" height="400px" width="80%">
            ${applicationInstance?.freeText}
        </ckeditor:editor>
    </div>
</div>
