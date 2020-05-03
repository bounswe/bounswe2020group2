import re
import wget
import urllib
import os
import sys

url_regex = re.compile(r"http[s]?://(?:[a-zA-Z]|[0-9]|[$-_@.&+]|[!*\(\),]|(?:%[0-9a-fA-F][0-9a-fA-F]))+")

mapper = {}

_from = sys.argv[1]
_to = sys.argv[2]

content = ""
with open(_from) as file:
    content = file.read()
    matches = re.findall(url_regex, content)
    for ix, match in enumerate(matches):
        try:
            old_url = match
            if old_url[-1] == ')':
                old_url = old_url[:-1]
            clean_url = match
            clean_url = clean_url.replace('github.com/', 'raw.githubusercontent.com/')
            clean_url = clean_url.replace('blob/', '')
            if clean_url[-1] == ')':
                clean_url = clean_url[:-1]
            if not(clean_url.endswith('png') or clean_url.endswith('jpg')):
                continue

            ext = clean_url.split('.')[-1]
        

            unquoted = urllib.parse.unquote(clean_url)
            name = unquoted.split('/')[-1]
            filename = f'./images/{ix}.{ext}'

            mapper[old_url] = filename

            print(clean_url)
            if os.path.exists(filename):
                continue

            print(f'begin: {clean_url} :end')
            urllib.request.urlretrieve(clean_url, filename)
            print()
        except Exception as ex:
            print('error: ', ex)
            break

print(mapper)


for old, new in mapper.items():
    print('replacing', old, new)
    content = content.replace(old, new)

print(content)

with open(_to, 'w+') as file:
    file.write(content)

