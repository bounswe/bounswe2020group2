from django.utils.crypto import pbkdf2, get_random_string
import hashlib
import base64

class Crypto():

    digest = hashlib.sha256
    salt_length = 32

    def getHashedPassword(self, password, salt):
        assert password is not None
        hash = pbkdf2(password, salt, 36000, digest= self.digest)
        hash = base64.b64encode(hash).decode('ascii').strip()
        return hash

    def getSalt(self):
        return get_random_string(self.salt_length)

    def get_random_string(self, length=None):
        if not length:
            length = self.salt_length
        return get_random_string(length)