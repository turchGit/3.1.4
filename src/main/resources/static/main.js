$('#editModal').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget) // Button that triggered the modal

    var userId = button.data('userid')
    var userFirstName = button.data('userfirstname')
    var userSecondName = button.data('usersecondname')
    var userAge = button.data('userage')
    var userLogin = button.data('userlogin')
    var userPassword = button.data('userpassword')
    var userRole = button.data('userroles')

    // Extract info from data-* attributes
    // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
    // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
    var modal = $(this)
    modal.find('.modal-body #editId').val(userId)
    modal.find('.modal-body #editFirstName').val(userFirstName)
    modal.find('.modal-body #editSecondName').val(userSecondName)
    modal.find('.modal-body #editAge').val(userAge)
    modal.find('.modal-body #editLogin').val(userLogin)
    //modal.find('.modal-body #editRoles').val(userRole)





})
$('#deleteModal').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget) // Button that triggered the modal

    var userId = button.data('userid')
    var userFirstName = button.data('userfirstname')
    var userSecondName = button.data('usersecondname')
    var userAge = button.data('userage')
    var userLogin = button.data('userlogin')
    var userPassword = button.data('userpassword')
    var userRole = button.data('userroles')

    // Extract info from data-* attributes
    // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
    // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
    var modal = $(this)
    modal.find('.modal-body #editId').val(userId)
    modal.find('.modal-body #editFirstName').val(userFirstName)
    modal.find('.modal-body #editSecondName').val(userSecondName)
    modal.find('.modal-body #editAge').val(userAge)
    modal.find('.modal-body #editLogin').val(userLogin)
    //modal.find('.modal-body #editRoles').val(userRole)





})