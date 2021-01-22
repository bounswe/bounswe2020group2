import enum

class OrderStatus(enum.Enum): 
    CANCELLED = -1
    ACCEPTED = 0
    AT_CARGO = 1
    DELIVERED = 2

def order_status_to_str(order_status):
    if order_status == -1:
        return 'cancelled'
    if order_status == 0:
        return 'accepted'
    if order_status == 1:
        return 'at_cargo'
    if order_status == 2:
        return 'delivered'
    return ''