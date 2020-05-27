const CSS_COLOR_NAMES = [
    'AliceBlue',
    'AntiqueWhite',
    'Aqua',
    'Aquamarine',
    'Azure',
    'Beige',
    'Bisque',
    'Black',
    'BlanchedAlmond',
    'Blue',
    'BlueViolet',
    'Brown',
    'BurlyWood',
    'CadetBlue',
    'Chartreuse',
    'Chocolate',
    'Coral',
    'CornflowerBlue',
    'Cornsilk',
    'Crimson',
    'Cyan',
    'DarkBlue',
    'DarkCyan',
    'DarkGoldenRod',
    'DarkGray',
    'DarkGrey',
    'DarkGreen',
    'DarkKhaki',
    'DarkMagenta',
    'DarkOliveGreen',
    'DarkOrange',
    'DarkOrchid',
    'DarkRed',
    'DarkSalmon',
    'DarkSeaGreen',
    'DarkSlateBlue',
    'DarkSlateGray',
    'DarkSlateGrey',
    'DarkTurquoise',
    'DarkViolet',
    'DeepPink',
    'DeepSkyBlue',
    'DimGray',
    'DimGrey',
    'DodgerBlue',
    'FireBrick',
    'FloralWhite',
    'ForestGreen',
    'Fuchsia',
    'Gainsboro',
    'GhostWhite',
    'Gold',
    'GoldenRod',
    'Gray',
    'Grey',
    'Green',
    'GreenYellow',
    'HoneyDew',
    'HotPink',
    'IndianRed',
    'Indigo',
    'Ivory',
    'Khaki',
    'Lavender',
    'LavenderBlush',
    'LawnGreen',
    'LemonChiffon',
    'LightBlue',
    'LightCoral',
    'LightCyan',
    'LightGoldenRodYellow',
    'LightGray',
    'LightGrey',
    'LightGreen',
    'LightPink',
    'LightSalmon',
    'LightSeaGreen',
    'LightSkyBlue',
    'LightSlateGray',
    'LightSlateGrey',
    'LightSteelBlue',
    'LightYellow',
    'Lime',
    'LimeGreen',
    'Linen',
    'Magenta',
    'Maroon',
    'MediumAquaMarine',
    'MediumBlue',
    'MediumOrchid',
    'MediumPurple',
    'MediumSeaGreen',
    'MediumSlateBlue',
    'MediumSpringGreen',
    'MediumTurquoise',
    'MediumVioletRed',
    'MidnightBlue',
    'MintCream',
    'MistyRose',
    'Moccasin',
    'NavajoWhite',
    'Navy',
    'OldLace',
    'Olive',
    'OliveDrab',
    'Orange',
    'OrangeRed',
    'Orchid',
    'PaleGoldenRod',
    'PaleGreen',
    'PaleTurquoise',
    'PaleVioletRed',
    'PapayaWhip',
    'PeachPuff',
    'Peru',
    'Pink',
    'Plum',
    'PowderBlue',
    'Purple',
    'RebeccaPurple',
    'Red',
    'RosyBrown',
    'RoyalBlue',
    'SaddleBrown',
    'Salmon',
    'SandyBrown',
    'SeaGreen',
    'SeaShell',
    'Sienna',
    'Silver',
    'SkyBlue',
    'SlateBlue',
    'SlateGray',
    'SlateGrey',
    'Snow',
    'SpringGreen',
    'SteelBlue',
    'Tan',
    'Teal',
    'Thistle',
    'Tomato',
    'Turquoise',
    'Violet',
    'Wheat',
    'White',
    'WhiteSmoke',
    'Yellow',
    'YellowGreen',
]

function createProduct(req) {
    const requestType = req.body.type
    // Creates a product and then adds it to the product set
    if (requestType == 'product') {
        const {
            id: productId, // Display name of the product
            name: productDisplayName, // Category of the product
            category: productCategory, // Id of the product set
            setId: productSetId, // Description of the product
            description: productDesc, // Color label of the product
            color, // Style label of the product
            style, // Gets body of request
        } = req.body
        // checks whether the category given by user is valid

        if (
            !['apparel', 'homegoods', 'toys', 'apparel-v2', 'homegoods-v2', 'toys-v2', 'packagedgoods-v1'].includes(
                productCategory,
            )
        ) {
            return { statusCode: 400, statusText: 'NON_VALID' }
        }
        // checks whether the style given by user is valid
        if (!['women', 'men', 'children', 'home', 'general', 'packaged'].includes(style)) {
            return { statusCode: 400, statusText: 'NON_VALID' }
        }
        if (!CSS_COLOR_NAMES.map(v => v.toLowerCase()).includes(color.toLowerCase())) {
            return { statusCode: 400, statusText: 'NON_VALID' }
        }
    }
}
function updateProduct(req) {
    // Gets the body of the request
    const productId = req.body.id
    const { key } = req.body
    const { value } = req.body

    if (key.toLowerCase() == 'style' && !['women', 'men', 'children', 'home', 'general', 'packaged'].includes(value)) {
        return { statusCode: 400, statusText: 'NON_VALID' }
    }
    if (key.toLowerCase() == 'color' && !CSS_COLOR_NAMES.map(v => v.toLowerCase()).includes(value.toLowerCase())) {
        return { statusCode: 400, statusText: 'NON_VALID' }
    }
}
const nonValidCategoryRequest = {
    method: 'POST',
    body: {
        id: '50',
        name: 'test_dress',
        category: 'software',
        type: 'product',
        setId: 'cmpe_352_test',
        description: 'Test dress',
        color: 'black',
        style: 'women',
    },
}

const nonValidStyleRequest = {
    method: 'POST',
    body: {
        id: '',
        name: 'test_dress',
        category: 'apparel',
        type: 'product',
        setId: 'cmpe_352_test',
        description: 'Test dress',
        color: 'black',
        style: 'woman',
    },
}
const nonValidColorRequest = {
    method: 'POST',
    body: {
        id: '22',
        name: 'test_dress',
        category: 'apparel',
        type: 'product',
        setId: 'cmpe_352_test',
        description: 'Test dress',
        color: 'blacks',
        style: 'woman',
    },
}
const nonValidUpdateColorRequest = {
    method: 'PATCH',
    body: {
        id: '1',
        key: 'color',
        value: 'REDS',
    },
}

const nonValidUpdateStyleRequest = {
    method: 'PATCH',
    body: {
        id: '1',
        key: 'style',
        value: 'woman',
    },
}

test('It should not create product with non valid category.', () => {
    const output = createProduct(nonValidCategoryRequest)
    expect(output.statusCode).toBe(400)
    expect(output.statusText).toBe('NON_VALID')
})
test('It should not create product with non valid style.', () => {
    const output = createProduct(nonValidStyleRequest)
    expect(output.statusCode).toBe(400)
    expect(output.statusText).toEqual('NON_VALID')
})
test('It should not create product with non valid color.', () => {
    const output = createProduct(nonValidColorRequest)
    expect(output.statusCode).toBe(400)
    expect(output.statusText).toEqual('NON_VALID')
})

test('It should not create product with non valid color when the key is color.', () => {
    const output = updateProduct(nonValidUpdateColorRequest)
    expect(output.statusCode).toBe(400)
    expect(output.statusText).toBe('NON_VALID')
})
test('It should not create product with non valid style when the key is style.', () => {
    const output = updateProduct(nonValidUpdateStyleRequest)
    expect(output.statusCode).toBe(400)
    expect(output.statusText).toBe('NON_VALID')
})
