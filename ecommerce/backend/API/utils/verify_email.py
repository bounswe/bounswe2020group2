from django.template.loader import render_to_string
from django.utils.encoding import force_bytes
from django.core.mail import EmailMessage

from API.utils.jwttoken import generate_access_token, generate_mail_token

def email_send_verify(to_email, current_site, user):
    mail_subject = 'Activate your account.'
    message = render_to_string('acc_active_email.html', {
                'user': user,
                'domain': current_site.domain,
                'uid': generate_mail_token(user.pk),
            })
    email = EmailMessage(
        mail_subject, message, to=[to_email]
    )
    email.send()