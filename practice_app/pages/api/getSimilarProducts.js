const listSimilarProducts = (req,res) => new Promise(
  async resolve => { 
  // Imports the Google Cloud client library
  const vision = require('@google-cloud/vision');
  // Id of the getflix in Google Cloud Storage
  const projectId = 'brilliant-era-276800';
  // Location of the getflix in Google Cloud Storage
  const location = 'europe-west1';
  // Imports file system module to read images
  const fs = require('fs');
  // Creates a product client
  const productSearchClient = new vision.ProductSearchClient();
  // Creates an image client
  const imageAnnotatorClient = new vision.ImageAnnotatorClient();
  // Gets url of the current page
  var requestURL=req.url;
  // Parses url into query parameters
  var initial = requestURL.indexOf('?');
  initial = initial +1;
  requestURL= requestURL.substring(initial);
  requestURL = requestURL.split('&');
  var queryParams= {};
requestURL.forEach(pair => {
    var keyValue = pair.split('=');
    if(keyValue[1].includes("%3D")) {
      keyValue[1] = keyValue[1].split("%3D").join("=");
    }
    if(keyValue[1].includes("%3A")) {
      keyValue[1] = keyValue[1].split("%3A").join(":");
    }
    if(keyValue[1].includes("%2F")) {
      keyValue[1] = keyValue[1].split("%2F").join("/");
    }
    if(keyValue[1].includes("%5C")) {
      keyValue[1] = keyValue[1].split("%5C").join("\\");
    }
    queryParams[keyValue[0]]= keyValue[1];
    
})
const productSetId = queryParams["setId"];
const productCategory = queryParams["category"];
const filePath = queryParams["filePath"];
const filter = queryParams["filter"];
// Determines the path of the product set via 'productSetPath' function of Google Product Search API
const productSetPath = productSearchClient.productSetPath(
  projectId,
  location,
  productSetId
);
// The image data as base64-encoded text
const content = fs.readFileSync(filePath, 'base64');
// Initializes the request object with the corresponding form.
const request = {
  image :{content:content},
  features: [{type: 'PRODUCT_SEARCH'}],
   imageContext: {
  productSearchParams: {
  productSet: productSetPath,
  productCategories: [productCategory],
  filter: filter,
},

 },
}; 

  // Async function call to image client via it's 'batchAnnotateImages' function
  const [response] = await imageAnnotatorClient.batchAnnotateImages({
    requests: [request],
    })
    .catch(err => {
        res.statusCode = 400;
        res.statusText = 'The size of page is greater than 100 or less than 1.'
        return res;
    })
    var results = response['responses'][0]['productSearchResults'];
    // Checks whether results is empty or not
    if(!results){
        res.statusCode = 404;
        res.statusText = 'Results cannot be found.'
        res.send('There is no result.');
        return res;
    }
    // Contains all products similar to image given by end user
    results = results ['results'];
    var temp = "";
    results.forEach(result => {
      temp+= '<h2>Product id:' + result['product'].name.split('/').pop(-1) + '</h2>';
      temp += '<li>Product name: '+ result['product'].displayName + '</li>';
      temp += '<li>Product description: '+ result['product'].description + '</li>';
      temp += '<li>Product category: '+ result['product'].productCategory + '</li>';
      temp += '<li>Product similarity score: '+ result.score + '</li>';
       

    });
    // Succesful response
    res.statusCode = 200;
    res.statusText = 'Similar products can be determined.'
    res.on("finish", resolve);
    res.send('<p>' +temp + '</p>');
    return res;
  })
  module.exports = listSimilarProducts;
