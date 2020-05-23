const getProductCatalog = (req, res) =>
  new Promise(async (resolve) => {
    const axios = require("axios");
    // Imports the Google Cloud client library
    const vision = require("@google-cloud/vision");
    // Creates a client
    const client = new vision.ProductSearchClient();
    // Id of the getflix in google cloud storage
    const projectId = "brilliant-era-276800";
    // Location of the getflix in google cloud storage
    const location = "europe-west1";
    // Method of the request
    const method = req.method;

    switch (method) {
      case 'POST': {
        const requestType = req.body.type;
          // Creates a product and then adds it to the product set
          if (requestType=='product') {
            const productId = req.body.id;
            // Display name of the product
            const productDisplayName = req.body.name;
            // Category of the product
            const productCategory = req.body.category;
            // Id of the product set
            const productSetId = req.body.setId;
            // Description of the product
            const productDesc = req.body.description;
            // Color label of the product
            const color = req.body.color;
            // Style label of the product
            const style = req.body.style;
            // Resource path that represents Google Cloud Platform location.
            const locationPath = client.locationPath(projectId, location);
            // Initializes the product object
            const product = {
              displayName: productDisplayName,
              productCategory: productCategory,
              description: productDesc,
              productLabels: [
              {
                key: "color",
                value: color, 
              }, {
                key: "style",
                value: style,    
              }],
            };
             // Initializes the request object
                const createRequest = {
                  parent: locationPath,
                  product: product,
                  productId: productId,
                };
                // Creates the product via 'createProduct' method of the Google Product Search API
                    client.createProduct(createRequest).catch(err => {
                        res.status = 400;
                        res.statusText = 'PossibleErrors\n'+
                        'display_name is missing or longer than 4096 characters\n' +
                        'description is longer than 4096 characters\n'+
                        'product_category is missing or invalid'
                        return res;
                      });
                    // Determines the path of the product via 'productPath' method of the Google Product Search API
                    const productPath = client.productPath(projectId, location, productId);

                    /* Determines the path of the product set the product will be added
                        via 'productPath' method of the Google Product Search API*/
                    const productSetPath = client.productSetPath(
                    projectId,
                    location,
                    productSetId
                    );
                    
                    const addRequest = {
                    name: productSetPath,
                    product: productPath,
                    };

                    // Async function call to Google Product Search API in order to add product to the set
                    await client.addProductToProductSet(addRequest).catch(err => {
                        res.data = {}
                        res.statusCode = 400;
                        res.statusText = "The product or the product set does not exist.";
                        return  res;

                    });
                    // Initializes both status message and status code of the succesful response
                    res.statusText = 'Product is added to the product set.'
                    res.status = 200;
                    res.on("finish", resolve);
                    return  res;
                }
                      // Creates product set
              else if(requestType=='productSet'){
                      // Gets body of the request
                      const productSetId = req.body.id;
                      const productSetDisplayName = req.body.name;      
                      // Resource path that represents Google Cloud Platform location.
                      const locationPath = client.locationPath(projectId, location);
                      // Initializes the product set object
                      const productSet = {
                          displayName: productSetDisplayName,
                      };
                      // Determines the request sended to the Google Product Search API
                      const request = {
                          parent: locationPath,
                          productSet: productSet,
                          productSetId: productSetId,
                      };
                      // Async function call to the Google Product Search API via it's function namely 'createProductSet'
                     const [createdProductSet] = await client.createProductSet(request).catch(err => {
                      res.statusCode = 400;
                      res.statusText ='Display name is missing, or is longer than 4096 characters.';
                      return res;
                    });
                      // Initializes both status message and status code of the succesful response
                      res.statusCode = 200;
                      res.statusText = 'Product set is created.';
                      res.on("finish", resolve);
                      return  res;
              }
        
        
        
        
        
        
        
        
        // create product set function call

        // add reference image function call
        
      }
      case 'PATCH': {
        // update product function call
      
      }
      case 'DELETE': {
        // delete product function call

        // delete product set function call
      }  
        
    }
  });
module.exports = getProductCatalog;
