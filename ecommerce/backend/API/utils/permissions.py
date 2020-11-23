from rest_framework.permissions import BasePermission
from .roles import Role

class IsAuthenticated(BasePermission):
    def has_permission(self, request, view):
        return request.user is not None and request.user.role != 0

class IsCustomerUser(BasePermission):
    def has_permission(self, request, view):
        return bool(request.user.role == Role.CUSTOMER.value)

class IsVendorUser(BasePermission):
    def has_permission(self, request, view):
        return bool(request.user.role == Role.VENDOR.value)

class IsAdminUser(BasePermission):
    def has_permission(self, request, view):
        return bool(request.user.role == Role.ADMINISTRATOR.value)

class AllowAnonymous(BasePermission):
    def has_permission(self, request, view):
        return True