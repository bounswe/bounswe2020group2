from rest_framework.permissions import BasePermission
from .roles import Role

class IsVendorUser(BasePermission):
    def has_permission(self, request, view):
        return bool(request.user.role == Role.VENDOR.value)

class IsAdminUser(BasePermission):
    def has_permission(self, request, view):
        return bool(request.user.role == Role.ADMINISTRATOR.value)

class AllowAnonymous(BasePermission):
    def has_permission(self, request, view):
        return True