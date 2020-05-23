const filter = (request,response) => new Promise(
 async resolve => { 
   // Imports the Google Cloud client library
   const vision = require('@google-cloud/vision');

   // Creates a client
   const client = new vision.ProductSearchClient();

   // Google cloud project id and region name
   const projectId = 'brilliant-era-276800';
   const location = 'europe-west1';

   // takes product set id and category name from the request url
   var str = request.url;
   var n = str.indexOf("=");
   var n1 = str.indexOf("&");
   var first = str.substring(n+1,n1);
   var n2 = str.indexOf("=", n+1);
   var second = str.substring(n2+1,str.length)
   const productSetId = first;   
   const catName = second;   
   
   // gets the product set path
   const productSetPath = client.productSetPath(
     projectId,
     location,
     productSetId
   );
   
   // HTML text
   var mytext = '<h1> Products in ' + catName + ' category </h1>';  
   // function for listing products in a product set
   client.listProductsInProductSet({name: productSetPath})
    .then(responses => {
      const productsOfSet = responses[0];
      // check whether there is a product in the product set
      if(productsOfSet.length==0) {
        response.on("finish", resolve);
        response.send({"text": "empty set"})
        return;
      }
      var counter = 0;
      for (const product of productsOfSet) {
           // adding each product of the set in that category to the HTML text as a list element
           if(product.productCategory===catName) {
            counter++;
            mytext += '<li> Product name: ' + product.name + ', '
            mytext += 'Product id: ' + product.name.split('/').pop() + ', '
            mytext += 'Product display name: ' + product.displayName + ', '
            mytext += 'Product description: ' + product.description + ', '
            mytext += 'Product category: ' + product.productCategory + ', '
       
            if (product.productLabels.length) {
             mytext += "Product labels: ";  
             product.productLabels.forEach(productLabel => {
             mytext += productLabel.key + ": " + productLabel.value + ", "
             }); 
            } 
            mytext += "</li>"; 
          }
      } 
     // checks whether there is a product in that category  
     if(counter==0) {
      response.on("finish", resolve);
      response.send({"text": "there is no product in this category"})
      return;
     }    
     // sends response text to HTML page
     if(mytext!=='<h1> Products in ' + catName + ' category </h1>') {
      response.on("finish", resolve);
      response.send('<p>' +mytext +'</p>');
     } 

   }).catch(err => {
    // shows error message  
    console.error(err);
   });
 })

 module.exports = filter;

