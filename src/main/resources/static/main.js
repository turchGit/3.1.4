const isAdmin = document.forms.namedItem("params-for-js").elements.namedItem("isAdmin").value
const userId = document.forms.namedItem("params-for-js").elements.namedItem("userId").value
const userPage = document.getElementById("user-page")
const adminPage = document.getElementById("admin-page")
const userPageButton = document.getElementById("open-user-page")
const adminPageButton = document.getElementById("open-admin-page")
userDataTableUpdate()
if (isAdmin) {
    updateTable()
} else {
    userPage.style.display = "block";
    userPageButton.style.color = "white"
    userPageButton.style.backgroundColor = "rgb(0,123,255)"
    userPageButton.style.borderRadius = "4px"
}

userPageButton.addEventListener('click',function (e){
    e.preventDefault()
    adminPage.style.display = "none";
    userPage.style.display = "block";
    adminPageButton.style.border = "none"
    adminPageButton.style.color = "rgb(0,123,255)"
    adminPageButton.style.backgroundColor = "white"
    userPageButton.style.color = "white"
    userPageButton.style.backgroundColor = "rgb(0,123,255)"
    userPageButton.style.borderRadius = "4px"
})

adminPageButton.addEventListener('click',function (e){
    e.preventDefault()
    userPage.style.display = "none";
    adminPage.style.display = "block";
    userPageButton.style.border = "none"
    userPageButton.style.color = "rgb(0,123,255)"
    userPageButton.style.backgroundColor = "white"
    adminPageButton.style.color = "white"
    adminPageButton.style.backgroundColor = "rgb(0,123,255)"
    adminPageButton.style.borderRadius = "4px"
})

async function getUser(userId){
    const response = await fetch("http://localhost:8080/users/"+userId)
    return response.json()
}

function addUser(firstName,secondName,age,login, password,role){
    fetch("http://localhost:8080/users", {
        method: 'POST',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify({
            "firstName": firstName,
            "secondName": secondName,
            "age": age,
            "login": login,
            "password": password,
            "role":role,
            "active":true

        })
    }).then(() => updateTable());
    const tab = document.getElementById("all-users-tab")
    tab.click();
}

function deleteUser(userId){
    const element = document.getElementById(userId)
        fetch("http://localhost:8080/users/"+userId, {
            method: 'DELETE',

        })
            .then(() => element.innerHTML="");
}

function updateUser(user){
    fetch("http://localhost:8080/users", {
        method: 'PUT',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify({
            "id":user.id,
            "firstName": user.firstName,
            "secondName": user.secondName,
            "age": user.age,
            "login": user.login,
            "password": user.password,
            "role":user.role,
            "active":true

        })
    }).then(() => {
        updateTable()
        userDataTableUpdate()

    });
}
const addButton = document.getElementById("add-user-button")
addButton.addEventListener('click',function (e){
    e.preventDefault()
    const form = document.forms.namedItem("add-form")
    const firstName = form.elements.namedItem("firstName").value
    const secondName = form.elements.namedItem("secondName").value
    const age = form.elements.namedItem("age").value
    const login = form.elements.namedItem("login").value
    const password = form.elements.namedItem("password").value
    const role = form.elements.namedItem("role").value
    addUser(firstName,secondName,age,login,password,role)
    form.elements.namedItem("firstName").value = "";
    form.elements.namedItem("secondName").value = "";
    form.elements.namedItem("age").value = "";
    form.elements.namedItem("login").value = "";
    form.elements.namedItem("password").value = "";
    form.elements.namedItem("role").value = "";

})

const editModal = $('#editModal')
editModal.on('show.bs.modal', function (event) {
    const button = $(event.relatedTarget) // Button that triggered the modal
    const userId = button.data('userid')

    getUser(userId).then(user => {
        const modal = $(this)

        modal.find('.modal-body #editId').val(user.id)
        modal.find('.modal-body #editFirstName').val(user.firstName)
        modal.find('.modal-body #editSecondName').val(user.secondName)
        modal.find('.modal-body #editAge').val(user.age)
        modal.find('.modal-body #editLogin').val(user.login)
        modal.find('.modal-body #editPassword').val("")

        modal.show()
    })
})

const deleteButton = document.getElementById("delete-button")
deleteButton.addEventListener('click',function (e){
    e.preventDefault()
     const form = document.forms.namedItem("delete-form")
     const idOfDelete = form.elements.namedItem("id").value
    deleteUser(idOfDelete)
})

const editButton = document.getElementById("edit-button")
editButton.addEventListener('click',function (e){
    e.preventDefault()
    const form = document.forms.namedItem("edit-form")
    const idOfEdit = form.elements.namedItem("id").value
    const newFirstName = form.elements.namedItem("firstName").value
    const newSecondName = form.elements.namedItem("secondName").value
    const newAge = form.elements.namedItem("age").value
    const newLogin = form.elements.namedItem("login").value
    const newPassword = form.elements.namedItem("password").value
    const newRole = form.elements.namedItem("role").value
    getUser(idOfEdit).then(user => {
        user.firstName = newFirstName
        user.secondName = newSecondName
        user.age = newAge
        user.login = newLogin
        user.password = newPassword
        user.role = newRole
        updateUser(user)
    })
})

const deleteModal = $('#deleteModal');
deleteModal.on('show.bs.modal', function (event) {
    const button = $(event.relatedTarget) // Button that triggered the modal
    const userId = button.data('userid')

    getUser(userId).then(user => {
        const modal = $(this)
        modal.find('.modal-body #deleteId').val(user.id)
        modal.find('.modal-body #deleteFirstName').val(user.firstName)
        modal.find('.modal-body #deleteSecondName').val(user.secondName)
        modal.find('.modal-body #deleteAge').val(user.age)
        modal.find('.modal-body #deleteLogin').val(user.login)
    })
})

async function updateTable(){
    const response = await fetch("http://localhost:8080/users");
    const data = response.json();
    data.then(data => {
            let tableData = "";
            data.forEach(user => {
                tableData+="<tr id='"+user.id+"'>";
                tableData+="<td>"+user.id+"</td>"
                tableData+="<td>"+user.firstName+"</td>"
                tableData+="<td>"+user.secondName+"</td>"
                tableData+="<td>"+user.age+"</td>"
                tableData+="<td>"+user.login+"</td>"
                tableData+="<td>"+user.roles+"</td>"
                tableData+= "<td>"+"<button type='button' class='btn btn-primary' data-toggle='modal' data-target='#editModal' " +
                    "data-userId='" + user.id + "'" +
                    ">Edit</button></td>";
                tableData += "<td>" + "<button type='button' class='btn btn-danger' data-toggle='modal' data-target='#deleteModal'" +
                    " data-userId='" + user.id + "'" +
                    ">Delete</button></td>";

                tableData+="</tr>";
            })
            document.getElementById("dataTable").innerHTML = tableData;
        }
    )
}

async function userDataTableUpdate(){
    const response = await fetch("http://localhost:8080/users/"+userId);
    const user = response.json();
    user.then(user => {
        let tableData = "";
        tableData+="<tr id='"+user.id+"'>";
        tableData+="<td>"+user.id+"</td>"
        tableData+="<td>"+user.firstName+"</td>"
        tableData+="<td>"+user.secondName+"</td>"
        tableData+="<td>"+user.age+"</td>"
        tableData+="<td>"+user.login+"</td>"
        tableData+="<td>"+user.roles+"</td>"
        tableData+="</tr>";

        let userInfo = "<strong>"+user.login+"</strong> with roles:"
        user.roles.forEach(u => userInfo+=" "+u)
        userInfo+= "</strong>"

        document.getElementById("user-data-table").innerHTML = tableData;
        document.getElementById("user-info").innerHTML = userInfo
    })

}

