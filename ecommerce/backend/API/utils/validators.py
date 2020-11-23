
def validate_register_request(request):
    username = request.data["username"]
    firstname = request.data["firstname"]
    lastname = request.data["lastname"]
    email = request.data["email"]
    password = request.data["password"]

    if username is None or username.__len__() < 6:
        return (False, "Username field is invalid")
    elif firstname is None or firstname.__len__() < 2:
        return (False, "First name field is invalid")
    elif lastname is None or lastname.__len__() < 2:
        return (False, "Last name field is invalid")
    elif email is None or email.__len__() < 12:
        #add regex here
        return (False, "Email field is invalid")
    elif password is None or password.__len__() < 8:
        return (False, "Password field must be at least 8 characters long")
    else:
        return(True, "")
    