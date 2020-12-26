from django.template.loader import render_to_string
from django.utils.encoding import force_bytes
from django.core.mail import EmailMessage
from django.conf import settings

from API.utils.jwttoken import generate_access_token, generate_mail_token

def email_send_verify(to_email, current_site, user):
    current_site = current_site.replace("http://","")
    if current_site[len(current_site)-1] == '/':
        current_site = current_site[0:len(current_site)-1]
    else:
        pass

    mail_subject = 'Activate your account.'
    message = render_to_string('acc_active_email.html', {
                'user': user,
                'domain': current_site,
                'uid': generate_mail_token(user.pk),
            })
    email = EmailMessage(
        mail_subject, message, to=[to_email]
    )
    email.send()

    return " Please check your mail to verify"
