
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

def validate_review_request(request):
    product_id = request.data["product_id"]
    vendor_id = request.data["vendor_id"]
    rating = request.data["rating"]
    comment = request.data["comment"]

    if product_id is None and vendor_id is None:
        return (False, "Both product and vendor are null.")
    elif rating is None or rating < 0 or rating > 5:
        return (False, "Invalid or empty rating")
    elif comment.__len__() > 400:
        return (False, "Comment cannot be longer than 400 characters")
    else:
        return (True, "Success")


def validate_product_add_request(data):
    name = data["name"]
    price = data["price"]
    stock_amount = data["stock_amount"]
    short_description = data["short_description"]
    long_description = data["long_description"]
    discount = data["discount"]
    brand = data.get("brand_id")
    subcategory_id = data["subcategory_id"]
    
    if name is None or name.__len__() < 3:
        return (False, "Invalid product name")
    elif price <= 0:
        return (False, "Price cannot be zero or negative")
    elif stock_amount <=0:
        return (False, "Stock amount cannot be zero or negative")
    elif short_description.__len__() < 16:
        return (False, "Short description must be at least 16 characters long.")
    elif long_description.__len__() < 64:
        return (False, "Long description must be at least 64 characters long.")
    elif discount is not None and (discount > 1 or discount < 0):
        return (False, "Discount must be between 0 and 1.")
    else:
        return(True, "")