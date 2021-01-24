from API.models import Product, ProductIndex
from API.serializers.product_serializer import ProductResponseSerializer

import string
import json
import requests

API_URL = "https://api.datamuse.com/words?ml="

# these are the stopwords of the NLTK library, taken from https://gist.github.com/sebleier/554280
with open('stopwords.txt', 'r', encoding='latin-1') as f:   
    stopwords = [line.strip() for line in f.readlines()]

# preprocess the sentence by applyin case-folding, punctutation removal and tokenization
def preprocess(sentence):
    global stopwords
    sentence = sentence.casefold() # case-folding
    sentence = sentence.translate(str.maketrans(string.punctuation, ' '*len(string.punctuation))) #punctuation removal
    sentence = sentence.split() #tokenization
    sentence = [word for word in sentence if not word in stopwords] # stopword removal
    return sentence

# converts the search-related fields into one preprocessed text
def get_indexed_text_of_product(product):
    data = ProductResponseSerializer(product).data
    text = " ".join(preprocess(" ".join([data['name'], data['long_description'],
        data['short_description'], data['category']['name'], data['subcategory']['name'],
            data['vendor']['name']])))
    return text

# gets similar queries from the Datamuse API
def get_similar_queries(query):
    processed_query = '+'.join(preprocess(query))
    # get the content of the API
    r = requests.get(API_URL+processed_query)
    results = r.content
    results = json.loads(results)
    # sort wrt to the highest score which means semantically more similar
    results = sorted(results, key= lambda x:x['score'], reverse=True)
    # return words of the top 10 results
    similar_queries = [d['word'] for d in results[:10]]
    return similar_queries

# conduct semantic search for the given query
def search(query):
    # queries are sorted wrt to decreasing semantic similarity
    similar_queries = get_similar_queries(query)
    product_indexes = ProductIndex.objects.all()
    filtered_ids_ranked = []
    # first found and added to the list objects are most semantically similar so will be at front of the list
    for query in similar_queries:
        filtered_ids_ranked.extend(product_indexes.filter(text__icontains=query).
            select_related('product').values_list('product_id', flat=True))
    # remove the duplicates from the back of the list, this approach preserves ranking order
    filtered_ids_ranked = [id for i, id in enumerate(filtered_ids_ranked) if i not in filtered_ids_ranked[i+1:]] 
    return Product.objects.filter(id__in=filtered_ids_ranked).filter(is_deleted=False)