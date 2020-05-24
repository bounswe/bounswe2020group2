const { checkGetRedditData } = require('./getRedditDataModule')
const testResponse = require('./examplecoviddata.json')
describe('checkGetRedditData', () => {
    test('check if there are 10 posts', () => {
        const data = getPostData(testResponse)
        expect(testResponse.length).toBe(10)
    })
    test('check title extraction', () => {
        const data = getPostData(testResponse)
        expect(testResponse[0].title).toBe('Hello Ekrem')
    })
    test('check permalink extraction', () => {
        const data = getPostData(testResponse)
        expect(testResponse[0].permalink).toBe('/r/Turkey/comments/gijpwa/hello_ekrem/')
    })
})
