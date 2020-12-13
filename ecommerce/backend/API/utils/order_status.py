import enum

class OrderStatus(enum.Enum): 
    CANCELLED = -1
    ACCEPTED = 0
    AT_CARGO = 1
    DELIVERED = 2