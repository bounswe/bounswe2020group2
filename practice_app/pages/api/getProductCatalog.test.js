const getProductSet = async(req) =>
{
        const axios = require('axios');
        // Imports the Google Cloud client library
        const vision = require('@google-cloud/vision');
        // Creates a client
        const client = new vision.ProductSearchClient();
        // Id of the getflix in google cloud storage
        const projectId = 'brilliant-era-276800';
        // Location of the getflix in google cloud storage
        const location = 'europe-west1';
        // Method of the request
        const method = req.method;
        var res = {};

        switch(method) {

            case 'POST':
                {
                    const requestType = req.body.type;
                // Creates a product and then adds it to the product set
                if (requestType=='product')
                {
                    // Gets body of request
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
                            }
                        ],
                    };

                    // Initializes the request object
                    const createRequest = {
                    parent: locationPath,
                    product: product,
                    productId: productId,
                    };
                    // Creates the product via 'createProduct' method of the Google Product Search API
                        client.createProduct(createRequest).catch(err => {
                            throw new Error('NON_VALID')
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
                            return new Error('NOT_FOUND')

                        });
                        // Initializes both status message and status code of the succesful response
                        res.statusText = 'Product is added to the product set.'
                        res.status = 200;
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
                            throw new Error('Display name is missing, or is longer than 4096 characters.')
                                        });
                            // Initializes both status message and status code of the succesful response
                            res.status = 200;
                            res.statusText = 'Product set is created.';
                            return  res;
                    } 
                    // Creates corresponding image of the product
                    else if(requestType == 'refImage'){
                        // Gets body of the request
                        const productId = req.body.id;
                        // Determines the path of the product via 'productPath' function of Google Product Search API
                        const formattedParent = client.productPath(projectId, location , productId);
                        // Initializes the object representing image
                        const referenceImage = {
                            // Uri of the image in the Google Cloud Storage
                            uri : req.body.uri,
                            name : formattedParent
                        }
                        // Initializes the id of the image from that of the product
                        const referenceImageId = productId;
                        // Creates request object
                        const request = {
                        parent: formattedParent,
                        referenceImage: referenceImage,
                        referenceImageId: referenceImageId,
                        };
                        // Calls the product client of the Google Product Search API via 'createReferenceImage'
                        client.createReferenceImage(request)
                        .then(responses =>
                            {
                            // Succesful response
                            res.status = 200;
                            res.statusText = 'The reference image is created.'
                            return  res;

                            }
                            )
                            throw new Error('Display name is missing, or is longer than 4096 characters.')


                        
                    }   
            }
    // Updates labels of the product existing in any one of the product set.
    case 'PATCH':
        {
            // Gets the body of the request
            const productId = req.body.id;
            const key = req.body.key;
            const value =req.body.value;
            // Resource path that represents Google Cloud Platform location.
            const productPath = client.productPath(projectId, location, productId);
            // Product object in the form of the Google Product Search API
            const product = {
            name: productPath,
            productLabels: [
                {
                key: key,
                value: value,
                },
            ],
            };
            // Initializes the product's field which will be updated
            const updateMask = {
            paths: ['product_labels'],
            };
            // Update request form corresponding function of Google Product Search API
            const request = {
            product: product,
            updateMask: updateMask,
            };
            // Async function call to Google Search API via it's function namely 'updateProduct'
            const [updatedProduct] = await client.updateProduct(request)
            .catch(
                err => {
                    // Two types of possible errors, which are NOT_FOUND and NOT_VALID
                    if(err =='NOT_FOUND'){
                        res.status = 400;
                        res.statusText = 'The product does not exist.';
                    }
                    else{
                        res.status = 400;
                        res.statusText ='display_name or the description is missing from the request or longer than 4096 characters or' +
                        'category of product does already exist.';     
                    }
                    return res;
                }
            );
            // Succesful response
            res.status = 200;
            res.statusText = `${updatedProduct.name} is updated.`;
            return  res;
        } 
        // Handles delete requests
        case 'DELETE':{
            const requestType = req.body.type;
            // Deletes the product
            if (requestType =='product'){
                // Gets id of the product from the body of the request
                const productId = req.body.id;
                // Resource path that represents Google Cloud Platform location.
                const formattedName = client.productPath(projectId, location, productId);
                // Calls deleteProduct function of Google Product Search API
                client.deleteProduct({name: formattedName}).catch(err => {
                    res.status = 404;
                    res.statusText = 'The product does not exist.';
                    return res;
                });
                // Succesful response
                res.status = 200;
                res.statusText = 'The product is deleted.';
                return  res;
            }
            // Deletes the product set
            else if(requestType =='productSet'){
                // Gets id of the product set from the body of the request
                const productSetId = req.body.setId;
                // Resource path that represents Google Cloud Platform location.
                const formattedName = client.productSetPath(projectId, location, productSetId);
                // Calls deleteProductSet function of Google Product Search API
                client.deleteProductSet({name: formattedName}).catch(err => {
                    res.status = 404;
                    res.statusText = 'The product set does not exist.';
                    return res;
                });
                // Succesful response
                res.status = 200;
                res.statusText = 'The product is deleted.';
                return  res;
            }
        }
        }
    }

module.exports = getProductSet;   










describe('It tests create product function.', () => {
        const req = {
            method: 'POST',
            body : {
            id: '2',
            name: 'test_dress',
            category : 'apparel',
            type : 'product',
            setId : 'cmpe_352_test',
            description : 'Test dress',
            color: 'black',
            style: 'women'
            }
        };

        it('It should create new product.',async()=>{
            await  expect(getProductSet(req))
            .resolves
            .toEqual({"status": 200, "statusText": "Product is added to the product set."})
        });
        it('It should not create new product with same id.',async()=>{
            await  expect(getProductSet(req))
            .rejects
        });
        

    });

    describe('It tests the create product set function.', () => {
        const req= {
            method: 'POST',
            body : {
                id: 'cmpe_352_testnewmm',
                name: 'cmpe_352_test_products',
                type: 'productSet'
            }
        };

        it('It should create new product set',async()=>{
            await  expect(getProductSet(req))
            .resolves
            .toEqual({"status": 200, "statusText": "Product set is created."})
        }); 

        it('It should not create new product set with same id.',async()=>{
            await  expect(getProductSet(req))
            .rejects
        });
    });
